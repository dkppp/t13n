package ru.creditnet.util.t13n;

import org.apache.log4j.Logger;
import org.junit.Test;
import ru.creditnet.util.t13n.lang.Ru;
import ru.creditnet.util.t13n.support.SearchExpression;

import static org.junit.Assert.assertEquals;

/**
 * @author astepachev
 */
public class RegressionEngineTest {

    private final Logger logger = Logger.getLogger(RegressionEngineTest.class);

    @Test
    public void testCharRules() {
        SearchExpression.Node what
                = SearchExpression.EMPTY.and("STOYLENSKIY GORNO-OBOGATITELNYY");
        String expected = "((стоыленскиы|стоыленский|стоыленскии|стоылэнскиы|стоылэнский|стоылэнскии|стойленскиы|стойленский|стойленскии|стойлэнскиы|стойлэнский|стойлэнскии|стоиленскиы|стоиленский|стоиленскии|стоилэнскиы|стоилэнский|стоилэнскии|стоуыленскиы|стоуыленский|стоуыленскии|стоуылэнскиы|стоуылэнский|стоуылэнскии|стоуйленскиы|стоуйленский|стоуйленскии|стоуйлэнскиы|стоуйлэнский|стоуйлэнскии|стоуиленскиы|стоуиленский|стоуиленскии|стоуилэнскиы|стоуилэнский|стоуилэнскии)&(горно|горноу|гоурно|гоурноу)&(обогатителныы|обогатителный|обогатителныи|обогатителнйы|обогатителнйй|обогатителнйи|обогатителниы|обогатителний|обогатителнии|обогатитэлныы|обогатитэлный|обогатитэлныи|обогатитэлнйы|обогатитэлнйй|обогатитэлнйи|обогатитэлниы|обогатитэлний|обогатитэлнии|обоугатителныы|обоугатителный|обоугатителныи|обоугатителнйы|обоугатителнйй|обоугатителнйи|обоугатителниы|обоугатителний|обоугатителнии|обоугатитэлныы|обоугатитэлный|обоугатитэлныи|обоугатитэлнйы|обоугатитэлнйй|обоугатитэлнйи|обоугатитэлниы|обоугатитэлний|обоугатитэлнии|оубогатителныы|оубогатителный|оубогатителныи|оубогатителнйы|оубогатителнйй|оубогатителнйи|оубогатителниы|оубогатителний|оубогатителнии|оубогатитэлныы|оубогатитэлный|оубогатитэлныи|оубогатитэлнйы|оубогатитэлнйй|оубогатитэлнйи|оубогатитэлниы|оубогатитэлний|оубогатитэлнии|оубоугатителныы|оубоугатителный|оубоугатителныи|оубоугатителнйы|оубоугатителнйй|оубоугатителнйи|оубоугатителниы|оубоугатителний|оубоугатителнии|оубоугатитэлныы|оубоугатитэлный|оубоугатитэлныи|оубоугатитэлнйы|оубоугатитэлнйй|оубоугатитэлнйи|оубоугатитэлниы|оубоугатитэлний|оубоугатитэлнии))";

        final Engine engine = new Engine(Ru.DATA);
        final SearchExpression.Node detransliterated = engine.detransliterate(what);
        assertEquals(what.getValue(),expected, detransliterated.getValue());
    }

    @Test
    public void testYY() {
        SearchExpression.Node what
                = SearchExpression.EMPTY.and("VOLZHSKIY TRUBNYY ZAVOD");
        String expected = "((волжскиы|волжский|волжскии|воулжскиы|воулжский|воулжскии)&(трюбныы|трюбный|трюбныи|трюбнйы|трюбнйй|трюбнйи|трюбниы|трюбний|трюбнии|трубныы|трубный|трубныи|трубнйы|трубнйй|трубнйи|трубниы|трубний|трубнии)&(завод|завоуд))";

        final Engine engine = new Engine(Ru.DATA);
        final SearchExpression.Node detransliterated = engine.detransliterate(what);
        assertEquals(what.getValue(),expected, detransliterated.getValue());

        assertEquals("((name:\"волжскиы\" OR name:\"волжский\" OR name:\"волжскии\" OR name:\"воулжскиы\" OR name:\"воулжский\" OR name:\"воулжскии\") AND (name:\"трюбныы\" OR name:\"трюбный\" OR name:\"трюбныи\" OR name:\"трюбнйы\" OR name:\"трюбнйй\" OR name:\"трюбнйи\" OR name:\"трюбниы\" OR name:\"трюбний\" OR name:\"трюбнии\" OR name:\"трубныы\" OR name:\"трубный\" OR name:\"трубныи\" OR name:\"трубнйы\" OR name:\"трубнйй\" OR name:\"трубнйи\" OR name:\"трубниы\" OR name:\"трубний\" OR name:\"трубнии\") AND (name:\"завод\" OR name:\"завоуд\"))", detransliterated.map(new SearchExpression.ValueVisitor() {
            public String visit(String value) {
                return "name:\"" + value + "\"";
            }
        }).getValue(new SearchExpression.WordOpProvider()));
    }


}