package ru.creditnet.util.t13n;

import java.util.Arrays;

/**
 * @author astepachev
 */
public class Split {

    public static final Split NONE = new Split("", "", new CharSequence[0], "");

    private final CharSequence _match;
    private final CharSequence[] _replace;
    private final CharSequence _left;
    private final CharSequence _right;

    public Split(CharSequence left, CharSequence match, CharSequence[] replace, CharSequence right) {
        _left = left;
        _right = right;
        _replace = replace;
        _match = match;
    }

    public CharSequence[] getReplace() {
        return _replace;
    }

    public CharSequence getLeft() {
        return _left;
    }

    public CharSequence getRight() {
        return _right;
    }

    public CharSequence getMatch() {
        return _match;
    }


    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("Split");
        sb.append("{_match=").append(_match);
        sb.append(", _replace=").append(_replace == null ? "null" : Arrays.asList(_replace).toString());
        sb.append(", _left=").append(_left);
        sb.append(", _right=").append(_right);
        sb.append('}');
        return sb.toString();
    }
}