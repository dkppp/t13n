package ru.creditnet.util.t13n.rules;

import org.jetbrains.annotations.NotNull;
import ru.creditnet.util.t13n.Split;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author astepachev
 */
public class StringRule implements Rule {

    private final char[] _ch;
    private final Pattern _pattern;
    private final CharSequence[] _rch;

    public StringRule(String str, final String... strs) {
        _ch = str.toLowerCase().toCharArray();
        _rch = new CharSequence[strs.length];
        for (int i = 0; i < strs.length; i++)
            _rch[i] = strs[i].toLowerCase();
        _pattern = Pattern.compile(Pattern.quote(str.toLowerCase()), Pattern.CASE_INSENSITIVE);
    }

    @NotNull
    public Split split(@NotNull CharSequence seq) {
        if (seq.length() < _ch.length)
            return Split.NONE;
        final Matcher matcher = _pattern.matcher(seq);
        if (matcher.find()) {
            return
                    new Split(
                            seq.subSequence(0, matcher.start()),
                            String.valueOf(_ch),
                            _rch,
                            seq.subSequence(matcher.end(), seq.length()));
        }
        return Split.NONE;
    }
}
