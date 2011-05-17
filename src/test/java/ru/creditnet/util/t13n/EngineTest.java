package ru.creditnet.util.t13n;

import org.apache.log4j.Logger;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import ru.creditnet.util.t13n.lang.LangData;
import ru.creditnet.util.t13n.lang.Ru;
import ru.creditnet.util.t13n.rules.CharRule;
import ru.creditnet.util.t13n.rules.Rule;
import ru.creditnet.util.t13n.rules.StringRule;
import ru.creditnet.util.t13n.rules.TrRule;
import ru.creditnet.util.t13n.support.SearchExpression;

/**
 * @author astepachev
 */
public class EngineTest {

    private final Logger logger = Logger.getLogger(EngineTest.class);

    private LangData makeLandData(final Rule[] n2a, final Rule[] a2n) {
        return new LangData() {

            public Rule[] getN2A() {
                return n2a;
            }

            public Rule[] getA2N() {
                return a2n;
            }
        };
    }

    private final static Rule[] EMPTY = new Rule[0];

    @Test
    public void testCharRules() {
        String what = "bcbc";
        String[] expected = {
                "бцбц", "бцбк", "бкбц", "бкбк"
        };

        final Rule[] rules = {
                new CharRule('c', 'ц', 'к'),
                new CharRule('b', 'б'),
        };

        final Engine engine = new Engine(makeLandData(EMPTY, rules));
        final CharSequence[] sequences = engine.detransliterate(what);
        printSequences("done", sequences);
        assertArrayEquals(what, expected, sequences);
    }

    @Test
    public void testStringRules() {
        String what = "iaiu";
        String[] expected = {
                "яю"
        };

        final Rule[] rules = {
                new StringRule("ia", "я"),
                new StringRule("iu", "ю"),
        };

        final Engine engine = new Engine(makeLandData(EMPTY, rules));
        final CharSequence[] sequences = engine.detransliterate(what);
        printSequences("done", sequences);
        assertArrayEquals(what, expected, sequences);
    }

    @Test
    public void testTrRules() {
        String what = "iaiu";
        String[] expected = {
                "иаиу"
        };

        final Rule[] rules = {
                new TrRule("ia", "иа"),
                new TrRule("iu", "иу"),
        };

        final Engine engine = new Engine(makeLandData(EMPTY, rules));
        final CharSequence[] sequences = engine.detransliterate(what);
        printSequences("done", sequences);
        assertArrayEquals(what, expected, sequences);
    }

    @Test
    public void testMixRules() {
        String what = "iaiush";
        String[] expected = {
                "яюш", "яиуш", "иаюш", "иаиуш"
        };

        final Rule[] rules = {
                new StringRule("ia", "я", "иа"),
                new StringRule("iu", "ю", "иу"),
                new StringRule("sh", "ш"),
                new StringRule("s", "с"),
                new StringRule("h", "х"),
        };

        final Engine engine = new Engine(makeLandData(EMPTY, rules));
        final CharSequence[] sequences = engine.detransliterate(what);
        printSequences("done", sequences);
        assertArrayEquals(what, expected, sequences);
    }

    @Test
    public void testRuRules() {
        String[] what = {
                "iaiu",
                "abcdefghijk",
                "lmnopqrstuvwxyz",
                "iauzh",
                "schchsch",
                "chaikovskiy",
                "permskaia",
                "antracit",
                "juzhno-sahalinsk",
                "cimlianskij",
                "astrakhanskiy",
                "astrahanskiy",
                "astrakhanskaia",
                "krasnoyarskiy",
                "dorozhnyy"
        };
        String[][] expected = {
                {"иаию", "иаиу", "ияию", "ияиу", "яию", "яиу", "ияию", "ияиу", "яию", "яиу",},
                {"абцдефгхийк", "абцдэфгхийк", "абкдефгхийк", "абкдэфгхийк",},
                {"лмнопкурстюввxыз", "лмнопкурстюввxйз", "лмнопкурстюввxиз", "лмнопкурстуввxыз", "лмнопкурстуввxйз", "лмнопкурстуввxиз", "лмнопкьюрстюввxыз", "лмнопкьюрстюввxйз", "лмнопкьюрстюввxиз", "лмнопкьюрстуввxыз", "лмнопкьюрстуввxйз", "лмнопкьюрстуввxиз", "лмноупкурстюввxыз", "лмноупкурстюввxйз", "лмноупкурстюввxиз", "лмноупкурстуввxыз", "лмноупкурстуввxйз", "лмноупкурстуввxиз", "лмноупкьюрстюввxыз", "лмноупкьюрстюввxйз", "лмноупкьюрстюввxиз", "лмноупкьюрстуввxыз", "лмноупкьюрстуввxйз", "лмноупкьюрстуввxиз",},
                {"иаюж", "иауж", "ияюж", "ияуж", "яюж", "яуж", "ияюж", "ияуж", "яюж", "яуж",},
                {"шчш", "шхш", "шкш"},
                {"чайковскиы", "чайковский", "чайковскии", "чайкоувскиы", "чайкоувский", "чайкоувскии", "чаиковскиы", "чаиковский", "чаиковскии", "чаикоувскиы", "чаикоувский", "чаикоувскии", "чйковскиы", "чйковский", "чйковскии", "чйкоувскиы", "чйкоувский", "чйкоувскии", "чайковскиы", "чайковский", "чайковскии", "чайкоувскиы", "чайкоувский", "чайкоувскии", "хайковскиы", "хайковский", "хайковскии", "хайкоувскиы", "хайкоувский", "хайкоувскии", "хаиковскиы", "хаиковский", "хаиковскии", "хаикоувскиы", "хаикоувский", "хаикоувскии", "хйковскиы", "хйковский", "хйковскии", "хйкоувскиы", "хйкоувский", "хйкоувскии", "хайковскиы", "хайковский", "хайковскии", "хайкоувскиы", "хайкоувский", "хайкоувскии", "кайковскиы", "кайковский", "кайковскии", "кайкоувскиы", "кайкоувский", "кайкоувскии", "каиковскиы", "каиковский", "каиковскии", "каикоувскиы", "каикоувский", "каикоувскии", "кйковскиы", "кйковский", "кйковскии", "кйкоувскиы", "кйкоувский", "кйкоувскии", "кайковскиы", "кайковский", "кайковскии", "кайкоувскиы", "кайкоувский", "кайкоувскии",},
                {"пермскаиа", "пермскаия", "пермская", "пермскаия", "пермская", "пэрмскаиа", "пэрмскаия", "пэрмская", "пэрмскаия", "пэрмская", },
                {"антрацит", "антракит",},
                {"южно-сахалинск", "южноу-сахалинск"},
                {"цимлианский", "цимлиянский", "цимлянский", "цимлиянский", "цимлянский", "кимлианский", "кимлиянский", "кимлянский", "кимлиянский", "кимлянский",},
                {"астракханскиы", "астракханский", "астракханскии", "астраханскиы", "астраханский", "астраханскии",},
                {"астраханскиы", "астраханский", "астраханскии",},
                {"астракханскаиа", "астракханскаия", "астракханская", "астракханскаия", "астракханская", "астраханскаиа", "астраханскаия", "астраханская", "астраханскаия", "астраханская",},
                {"красноиарскиы", "красноиарский", "красноиарскии", "красноиярскиы", "красноиярский", "красноиярскии", "красноярскиы", "красноярский", "красноярскии", "красноярскиы", "красноярский", "красноярскии", "красноуиарскиы", "красноуиарский", "красноуиарскии", "красноуиярскиы", "красноуиярский", "красноуиярскии", "красноуярскиы", "красноуярский", "красноуярскии", "красноуярскиы", "красноуярский", "красноуярскии"},
                {"дорожныы", "дорожный", "дорожныи", "дорожнйы", "дорожнйй", "дорожнйи", "дорожниы", "дорожний", "дорожнии", "дороужныы", "дороужный", "дороужныи", "дороужнйы", "дороужнйй", "дороужнйи", "дороужниы", "дороужний", "дороужнии", "доурожныы", "доурожный", "доурожныи", "доурожнйы", "доурожнйй", "доурожнйи", "доурожниы", "доурожний", "доурожнии", "доуроужныы", "доуроужный", "доуроужныи", "доуроужнйы", "доуроужнйй", "доуроужнйи", "доуроужниы", "доуроужний", "доуроужнии",}
        };

        final Engine engine = new Engine(Ru.DATA);
        for (int i = 0; i < what.length; i++) {
            final CharSequence[] sequences = engine.detransliterate(what[i]);
            printSequences("done", sequences);
            assertArrayEquals(what[i], expected[i], sequences);
        }
    }

    @Test
    public void testRuTransliterate() {
        String[] what = {
                "чайковский",
                "царское",
                "великий",
                "раздольное",
        };
        String[] expected = {
                "chaykovskiy",
                "tsarskoye",
                "velikiy",
                "razdolnoye"
        };

        final Engine engine = new Engine(Ru.DATA);
        for (int i = 0; i < what.length; i++) {
            final CharSequence sequence = engine.transliterate(what[i]);
            printSequences("done", sequence);
            assertEquals(what[i], expected[i], sequence);
        }

    }

    @Test
    public void testEmpty() {
        String what = "";
        CharSequence[] expected = Engine.EMPTY;

        final Rule[] rules = {
                new CharRule('c', 'ц', 'к'),
                new CharRule('b', 'б'),
        };

        final Engine engine = new Engine(makeLandData(EMPTY, rules));
        final CharSequence[] sequences = engine.detransliterate(what);
        printSequences("done", sequences);
        assertArrayEquals(what, expected, sequences);
    }

    @Test
    public void testSearchExpressions() {
        String [][] what = new String[][]{
                {"iaiu", "abcdefghijk"},
                {"a", "b"},
                {"ia", "b"},
                {"b", "ia"},
                {"&", "ia "},
        };

        String [] expected = new String[] {
                "((иаию|иаиу|ияию|ияиу|яию|яиу|ияию|ияиу|яию|яиу)&(абцдефгхийк|абцдэфгхийк|абкдефгхийк|абкдэфгхийк))",
                "(а&б)",
                "((иа|ия|я|ия|я)&б)",
                "(б&(иа|ия|я|ия|я))",
                "(иа|ия|я|ия|я)"
        };

        final Engine engine = new Engine(Ru.DATA);

        for (int i =0; i < what.length; i++) {
            assertEquals(expected[i], engine.detransliterate(SearchExpression.and(what[i])).optimize().getValue());
        }
    }

    private void printSequences(String msg, CharSequence... sequences) {
        if (!logger.isDebugEnabled())
            return;

        StringBuilder sb = new StringBuilder();
        sb.append(msg).append("[");
        for (CharSequence sequence : sequences) {
            sb.append("\"").append(sequence).append("\", ");
        }
        sb.append("]");
        logger.debug(sb.toString());
    }


}
