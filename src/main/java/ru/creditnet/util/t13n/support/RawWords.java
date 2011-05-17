package ru.creditnet.util.t13n.support;

/**
 * @author astepachev
 * @deprecated Use {@link ru.creditnet.util.t13n.support.SearchExpression} instead
 */
@SuppressWarnings({"deprecation"})
public class RawWords {

    SearchExpression.Node node = SearchExpression.w("");

    public RawWords() {
    }

    public RawWords(CharSequence... strings) {
        node = SearchExpression.and(strings);
    }

    public RawWords(String value) {
        node = SearchExpression.and(value);
    }

    public RawWords and(RawWords value) {
        node = SearchExpression.and(node, value.node);
        return this;
    }

    public RawWords and(CharSequence... strings) {
        node = SearchExpression.and(node, SearchExpression.or(strings));
        return this;
    }

    public String getValue() {
        return node.optimize().getValue();
    }

    public void setValue(String value) {
        node = SearchExpression.and(value);
    }

    public String toString() {
        return node.toString();
    }
}