package ru.creditnet.util.t13n.support;

import static junit.framework.Assert.assertEquals;
import org.junit.Test;

/**
 * @author astepachev
 */
@SuppressWarnings({"deprecation"})
public class RawWordsTest {
    @Test
    public void testRawWodsCases() {

        final String[][] cases = new String[][]{
                {new RawWords().and("a").and("b").and("c").getValue(), "(a&b&c)"},
                {new RawWords().and("a", "b", "c").getValue(), "(a|b|c)"},
                {new RawWords().and("a", "b", "c").and(new RawWords().and("d", "c")).getValue(), "((a|b|c)&(d|c))"},
                {new RawWords("").getValue(), ""}
        };

        for (String[] aCase : cases) {
            assertEquals(aCase[0], aCase[1], aCase[0].replaceAll("\\s+", ""));
        }
    }

    @Test
    public void testMaskSymbols() {
        final String[][] cases = new String[][]{
                {new RawWords().and("&", "d", ")").and(new RawWords().and("&")).getValue(), "d"},
                {new RawWords().and("&", "(", ")").and(new RawWords().and("d")).getValue(), "d"},
                {new RawWords().and("&", "(\t", ")").and(new RawWords().and("d")).getValue(), "d"}
        };

        for (String[] aCase : cases) {
            assertEquals(aCase[0], aCase[1], aCase[0].replaceAll("\\s+", ""));
        }

    }
}
