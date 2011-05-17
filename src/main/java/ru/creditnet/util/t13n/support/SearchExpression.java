package ru.creditnet.util.t13n.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author astepachev
 */
public class SearchExpression {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchExpression.class);

    private static final char OP_AND = '&';
    private static final char OP_OR = '|';
    private static Pattern patt = Pattern.compile("[\\(\\)\\|&\"\\s\\-]+");

    public static final Node EMPTY = new Word("");

    public static final OpProvider BOOLEAN_OP_PROVIDER = new BooleanOpProvider();
    public static final OpProvider WORD_OP_PROVIDER = new WordOpProvider();

    /**
     * Value visitor, can change value to new one.
     */
    public interface ValueVisitor {
        String visit(String value);
    }

    public interface OpProvider {
        public String getOpValue(char op);
    }

    public static class BooleanOpProvider implements OpProvider{

        public String getOpValue(char op) {
            switch(op) {
                case OP_AND:
                    return "&";
                case OP_OR:
                    return "|";
                default:
                    throw new IllegalArgumentException("Unknown op "+op);
            }
        }
    }

    public static class WordOpProvider implements OpProvider{

        public String getOpValue(char op) {
            switch(op) {
                case OP_AND:
                    return " AND ";
                case OP_OR:
                    return " OR ";
                default:
                    throw new IllegalArgumentException("Unknown op "+op);
            }
        }
    }

    /**
     * Transform node to new one. Can return null if node should be deleted.
     */
    public interface WordTransformer {
        Node transform(Word node);
    }

    public static abstract class Node {
        abstract public String getValue();

        abstract public String getValue(OpProvider opProvider);

        abstract public Node optimize();

        abstract public boolean isEmpty();

        abstract public Node map(ValueVisitor visitor);

        abstract public Node transform(WordTransformer transfromer);

        public Node and(Node node) {
            return SearchExpression.and(this, node);
        }

        public Node and(CharSequence... strings) {
            return SearchExpression.and(this, SearchExpression.and(strings));
        }

        public Node and(String ... strings) {
            return SearchExpression.and(this, SearchExpression.and(strings));
        }

        public Node or(CharSequence... strings) {
            return SearchExpression.or(this, SearchExpression.or(strings));
        }

        public Node or(String ... strings) {
            return SearchExpression.or(this, SearchExpression.or(strings));
        }

    }

    public static final class Word extends Node {
        String value;

        public Word(String value) {
            this.value = patt.matcher(value).replaceAll(" ").trim();
        }

        public String getValue(OpProvider opProvider) {
            return value;
        }

        public String getValue() {
            return value;
        }

        public Node optimize() {
            return isEmpty() ? EMPTY : this;
        }

        public boolean isEmpty() {
            return value.length() == 0;
        }

        public Node map(ValueVisitor visitor) {
            value = visitor.visit(value);
            return this;
        }

        public Node transform(WordTransformer transfromer) {
            return transfromer.transform(this);
        }

        @Override
        public String toString() {
            return value;
        }
    }

    public static final class Op extends Node {
        char op;
        List<Node> values = new ArrayList<Node>();

        public String getValue() {
            return getValue(BOOLEAN_OP_PROVIDER);
        }

        public String getValue(OpProvider opProvider) {
            if (isEmpty())
                return "";

            StringBuffer sb = new StringBuffer();
            int idx = 0;
            for (Node value : values) {
                if (value.isEmpty())
                    continue;
                if (idx > 0) {
                    sb.append(opProvider.getOpValue(op));
                }
                sb.append(value.getValue(opProvider));
                idx++;
            }
            if (idx > 0) {
                sb.insert(0, '(');
                sb.append(')');
            }
            return sb.toString();
        }

        public Node optimize() {
            LOGGER.debug("Before optimisation {}", this.toString());

            List<Node> newList = new ArrayList<Node>();
            for (final Node node : values) {
                final Node optimized = node.optimize();
                if (optimized != EMPTY) {
                    if (optimized instanceof Op && ((Op) optimized).op == this.op)
                        newList.addAll(((Op) optimized).values);
                    else
                        newList.add(optimized);
                }
            }
            values = newList;
            LOGGER.debug("After optimisation {}", this.toString());
            switch (values.size()) {
                case 0:
                    return EMPTY;
                case 1:
                    return values.get(0);
                default:
                    return this;
            }
        }

        public boolean isEmpty() {
            for (Node value : values) {
                if (!value.isEmpty())
                    return false;
            }
            return true;
        }

        public Node map(ValueVisitor visitor) {
            for (Node value : values) {
                value.map(visitor);
            }
            return this;
        }

        public Node transform(WordTransformer transfromer) {
            List<Node> nodes = new ArrayList<Node>();
            for (Node value : values) {
                final Node v = value.transform(transfromer);
                if (v != null)
                    nodes.add(v);
            }
            values = nodes;
            switch(values.size()) {
                case 0:
                    return EMPTY;
                case 1:
                    return values.get(0);
                default:
                    break;
            }
            return this.optimize();
        }

        Op(char op, List<Node> values) {
            this.op = op;
            this.values = values;
        }

        Op(char op) {
            this.op = op;
        }

        @Override
        public String toString() {
            return "{" +
                    op +
                    " " + values +
                    "} ";
        }
    }

    private static Node op(char op, String... values) {
        if (values.length == 0)
            return w("");
        final Op node = new Op(op);
        for (String value : values) {
            addNodes(node, value);
        }
        return node;
    }

    private static Node op(char op, CharSequence... values) {
        if (values.length == 0)
            return w("");
        final Op node = new Op(op);
        for (CharSequence value : values) {
            addNodes(node, value);
        }
        return node;
    }

    private static void addNodes(Op node, CharSequence value) {
        for (String s : patt.split(value)) {
            final Word word = new Word(s);
            if (word.isEmpty())
                continue;
            LOGGER.debug("add: {} + {}", node.values, word);
            node.values.add(word);
        }
    }


    private static Node op(char op, Node... values) {
        final Op node = new Op(op);
        LOGGER.debug("add: {} + {}", node, Arrays.toString(values));
        Collections.addAll(node.values, values);
        return node;
    }

    public static Node and(CharSequence... values) {
        return op(OP_AND, values);
    }

    public static Node and(String... values) {
        return op(OP_AND, values);
    }

    public static Node and(Node... values) {
        return op(OP_AND, values);
    }

    public static Node or(CharSequence... values) {
        return op(OP_OR, values);
    }

    public static Node or(String... values) {
        return op(OP_OR, values);
    }

    public static Node or(Node... values) {
        return op(OP_OR, values);
    }

    public static Node w(String value) {
        return new Word(value);
    }

    public static Node w(CharSequence value) {
        return new Word(new StringBuilder(value).toString());
    }

    public static Node e() {
        return EMPTY;
    }
}
