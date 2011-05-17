package ru.creditnet.util.t13n.rules;

import org.jetbrains.annotations.NotNull;
import ru.creditnet.util.t13n.Split;

/**
 * @author astepachev
 */
public class TrRule implements Rule {

    private final char[] _from;
    private final char[] _to;

    public TrRule(String from, String to) {
        assert from.length() == to.length();
        _from = from.toLowerCase().toCharArray();
        _to = to.toLowerCase().toCharArray();
    }

    @NotNull
    public Split split(@NotNull CharSequence seq) {
        for (int i = 0; i < seq.length(); i++)
            for (int m = 0; m < _from.length; m++) {
                final char c = Character.toLowerCase(seq.charAt(i));
                if (_from[m] == c)
                    return new Split(
                            seq.subSequence(0, i),
                            String.valueOf(c),
                            new CharSequence[]{String.valueOf(_to[m])},
                            seq.subSequence(i + 1, seq.length()));
            }
        return Split.NONE;
    }
}
