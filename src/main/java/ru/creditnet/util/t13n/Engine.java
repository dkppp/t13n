package ru.creditnet.util.t13n;

import org.jetbrains.annotations.NotNull;
import ru.creditnet.util.t13n.lang.LangData;
import ru.creditnet.util.t13n.rules.Rule;
import ru.creditnet.util.t13n.support.SearchExpression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author astepachev
 */
public class Engine {
    public static final CharSequence[] EMPTY = new CharSequence[0];

    private final LangData _rules;
    private final Pattern _split = Pattern.compile("([\\s,;])");

    public Engine(LangData rules) {
        _rules = rules;
    }

    public SearchExpression.Node detransliterate(SearchExpression.Node node) {
        return node.transform(new SearchExpression.WordTransformer() {
            public SearchExpression.Node transform(SearchExpression.Word node) {
                final CharSequence[] variants = apply(_rules.getA2N(), "", node.getValue(), "", false);
                if (variants.length == 1)
                    return SearchExpression.w(variants[0]);
                else
                    return SearchExpression.or(variants);
            }
        });
    }

    public CharSequence[] detransliterate(CharSequence what) {
        final CharSequence[] strings = _split.split(what, 10);
        List<CharSequence> result = new ArrayList<CharSequence>();
        for (CharSequence string : strings) {
            result.addAll(Arrays.asList(apply(_rules.getA2N(), "", string, "", false)));
        }
        return result.toArray(new CharSequence[result.size()]);
    }

    public CharSequence transliterate(String what) {
        if (what == null)
            return null;
        if (what.length() == 0)
            return "";
        return new StringBuffer(apply(_rules.getN2A(), "", what, "", false)[0]).toString();
    }

    public CharSequence transliterate(CharSequence what) {
        if (what == null)
            return null;
        if (what.length() == 0)
            return "";
        return apply(_rules.getN2A(), "", what, "", false)[0];
    }

    private CharSequence[] apply
            (Rule[] rules, CharSequence prev, CharSequence what, CharSequence next, boolean isSingle) {
        if (what.length() < 1)
            return Engine.EMPTY;
        CharSequence[] sr = null;
        for (Rule rule : rules) {
            @NotNull final Split split = rule.split(what);
            if (split != Split.NONE) {
                final CharSequence[] rights =
                        (split.getRight().length() > 0) ?
                                apply(rules,
                                        concat(prev, split.getLeft(), split.getMatch()),
                                        split.getRight(),
                                        next,
                                        isSingle)
                                : Engine.EMPTY;
                final CharSequence[] lefts =
                        (split.getLeft().length() > 0) ?
                                apply(rules,
                                        prev,
                                        split.getLeft(),
                                        concat(split.getMatch(), split.getRight(), next),
                                        isSingle)
                                : Engine.EMPTY;

                sr = join(join(lefts, split.getReplace()), rights);
                break;
            }
        }

        if (sr == null)
            sr = new CharSequence[]{what};
        if (isSingle)
            sr = new CharSequence[]{sr[0]};
        return sr;
    }

    private CharSequence[] join
            (CharSequence[] lefts, CharSequence[] rights) {
        if (lefts.length == 0)
            return rights;
        if (rights.length == 0)
            return lefts;
        ArrayList<CharSequence> seq = new ArrayList<CharSequence>();
        for (CharSequence left : lefts) {
            for (CharSequence right : rights) {
                final String s = join(left, right);
                seq.add(s);
            }
        }
        return seq.toArray(new CharSequence[seq.size()]);
    }

    private String join(CharSequence left, CharSequence right) {
        final StringBuffer buffer = new StringBuffer(left);
        buffer.append(right);
        return buffer.toString();
    }

    private static CharSequence concat
            (CharSequence... chseq) {
        final StringBuffer buffer = new StringBuffer();
        for (CharSequence charSequence : chseq) {
            buffer.append(charSequence);
        }
        return buffer.toString();
    }
}
