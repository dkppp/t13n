package ru.creditnet.util.t13n.support;

import java.util.ArrayList;
import java.util.List;

/**
 * @author astepachev
 */
public class WordUtils {

    public static String capitalizeWord(String what) {
        return new StringBuffer(capitalize(what)).toString();
    }

    public static CharSequence capitalize(CharSequence charSequence) {
        final StringBuffer buffer = new StringBuffer();
        buffer.append(Character.toUpperCase(charSequence.charAt(0)));
        if (charSequence.length() > 0) {
            buffer.append(charSequence.subSequence(1, charSequence.length()));
        }
        return buffer.toString();
    }

    public static CharSequence[] capitalize(CharSequence[] sequences) {
        List<CharSequence> sbs = new ArrayList<CharSequence>();
        for (CharSequence chSeq : sequences) {
            sbs.add(capitalize(chSeq));
        }
        return sbs.toArray(new CharSequence[sbs.size()]);
    }
}
