package ru.creditnet.util.t13n.rules;

import org.jetbrains.annotations.NotNull;
import ru.creditnet.util.t13n.Split;

/**
 * @author astepachev
 */
public class CharRule implements Rule {
    private final char _ch;
    private final CharSequence[] _rch;

    public CharRule(char ch, final char... rch) {
        _ch = ch;
        _rch = new CharSequence[rch.length];
        for (int i = 0; i < rch.length; i++) {
            _rch[i] = String.valueOf(Character.toLowerCase(rch[i]));
        }
    }

    public CharRule(char ch, final String... strs) {
        this._ch = ch;
        _rch = new CharSequence[strs.length];
        for (int i = 0; i < strs.length; i++)
            _rch[i] = strs[i].toLowerCase();
    }

    @NotNull
    public Split split(@NotNull CharSequence seq) {
        for (int i = 0; i < seq.length(); i++) {
            if (Character.toLowerCase(seq.charAt(i)) == _ch) {
                return
                        new Split(
                                seq.subSequence(0, i),
                                String.valueOf(_ch),
                                _rch,
                                seq.subSequence(i + 1, seq.length()));
            }
        }
        return Split.NONE;
    }
}
