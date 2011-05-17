package ru.creditnet.util.t13n.support;

import static junit.framework.Assert.assertEquals;
import org.junit.Test;

/**
 * @author astepachev
 */
public class WordUtilsTest {
    @Test
    public void testCapitalize() {

        String[][] cases = new String[][]{
                {"что-то", "Что-то"}
        };
        for (String[] aCase : cases) {
            assertEquals(aCase[1], WordUtils.capitalizeWord(aCase[0]));
        }
    }
}
