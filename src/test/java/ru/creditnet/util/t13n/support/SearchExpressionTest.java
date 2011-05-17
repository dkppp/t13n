package ru.creditnet.util.t13n.support;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author astepachev
 */
@SuppressWarnings({"deprecation"})
public class SearchExpressionTest extends TestCase {
       @Test
    public void testRawWodsCases() {

        final String[][] cases = new String[][]{
                {SearchExpression.w("a").and("b").and("c").optimize().getValue(), "(a&b&c)"},
                {SearchExpression.e().or("a", "b", "c").optimize().getValue(), "(a|b|c)"},
                {SearchExpression.e().or("a", "b", "c").and(SearchExpression.e().or("d", "c")).optimize().getValue(), "((a|b|c)&(d|c))"},
                {SearchExpression.e().optimize().getValue(), ""}
        };

        for (String[] aCase : cases) {
            assertEquals(aCase[0], aCase[1], aCase[0].replaceAll("\\s+", ""));
        }
    }

    @Test
    public void testMaskSymbols() {
        final String[][] cases = new String[][]{
                {SearchExpression.e().and("&", "d", ")").and(SearchExpression.e().and("&")).optimize().getValue(), "d"},
                {SearchExpression.e().and("&", "(", ")").and(SearchExpression.e().and("d")).optimize().getValue(), "d"},
                {SearchExpression.e().and("&", "(\t", ")").and(SearchExpression.e().and("d")).optimize().getValue(), "d"}
        };

        for (String[] aCase : cases) {
            assertEquals(aCase[0], aCase[1], aCase[0].replaceAll("\\s+", ""));
        }

    }

    public void testMapValueVisitor() {
        final SearchExpression.Node expr = SearchExpression.w("h").and("a", "b").or("c").and("u", "v");
        final AtomicInteger cnt = new AtomicInteger();
        expr.map(new SearchExpression.ValueVisitor() {
            public String visit(String value) {
                return String.format("V%d", cnt.incrementAndGet());
            }
        });
        assertEquals(6, cnt.get());
        assertEquals("(((V1&V2&V3)|V4)&V5&V6)", expr.optimize().getValue());
    }


    public void testTransformVisitor() {
        final SearchExpression.Node expr = SearchExpression.w("h").and("a", "b").or("c").and("u", "v");
        final AtomicInteger cnt = new AtomicInteger();
        expr.transform(new SearchExpression.WordTransformer() {
            public SearchExpression.Node transform(SearchExpression.Word node) {

                return node.or(String.format("V%d", cnt.incrementAndGet()));
            }
        });
        assertEquals(6, cnt.get());
        assertEquals("((((h|V1)&(a|V2)&(b|V3))|c|V4)&(u|V5)&(v|V6))", expr.optimize().getValue());
    }

    public void testOpProvider() {
        final SearchExpression.Node expr = SearchExpression.w("b\"c\"").and("a", "u\"f\"").or("d").and("e", "v");
        assertEquals("(((b c AND a AND u AND f) OR d) AND e AND v)", expr.optimize().getValue(new SearchExpression.WordOpProvider()));
        assertEquals("(((aa:\"b c\" AND aa:\"a\" AND aa:\"u\" AND aa:\"f\") OR aa:\"d\") AND aa:\"e\" AND aa:\"v\")",
                expr.map(new SearchExpression.ValueVisitor() {
            public String visit(String value) {
                return "aa:\"" + value + "\"";
            }
        }).getValue(new SearchExpression.WordOpProvider()));
    }
}
