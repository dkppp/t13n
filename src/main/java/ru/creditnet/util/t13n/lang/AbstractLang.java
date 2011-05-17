package ru.creditnet.util.t13n.lang;

import org.apache.commons.collections15.MultiMap;
import org.apache.commons.collections15.multimap.MultiHashMap;
import ru.creditnet.util.t13n.rules.CharRule;
import ru.creditnet.util.t13n.rules.Rule;
import ru.creditnet.util.t13n.rules.StringRule;
import ru.creditnet.util.t13n.rules.TrRule;

import java.util.*;

/**
 * @author astepachev
 */
public abstract class AbstractLang {

    /**
     * Create LangData from passed data
     *
     * @param map   { {native_a, native_b, ...}, { ascii_abc, ... } }
     * @param tr    { native , ascii} , entries should be the same size
     * @param plain { {native , ascii}, ... }
     * @return langdata
     */
    protected static LangData makeLangData(String[][][] map, String[][] tr, String[][] plain) {

        List<String> a2nOrder = new ArrayList<String>();
        MultiMap<String, String> a2n = new MultiHashMap<String, String>();

        for (String[][] maps : map) {
            for (String a : maps[1]) {
                a2nOrder.add(a);
                a2n.putAll(a, Arrays.asList(maps[0]));
            }
        }

        final List<Rule> a2nRules = convertToRules(a2nOrder, a2n);

        for (String[] strings : tr) {
            a2nRules.add(new TrRule(strings[1], strings[0]));
        }

        List<String> n2aOrder = new ArrayList<String>();
        Map<String, String> n2a = new HashMap<String, String>();

        for (String[] pair : plain) {
            if (!n2a.containsKey(pair[0])) {
                n2aOrder.add(pair[0]);
                n2a.put(pair[0], pair[1]);
            }
        }
        final List<Rule> n2aRules = convertToRules(n2aOrder, n2a);

        return new LangData() {

            public Rule[] getN2A() {
                return n2aRules.toArray(new Rule[n2aRules.size()]);
            }

            public Rule[] getA2N() {
                return a2nRules.toArray(new Rule[a2nRules.size()]);
            }
        };
    }

    private static List<Rule> convertToRules(List<String> order, Map<String, String> map) {
        List<Rule> rules = new ArrayList<Rule>();
        for (String key : order) {
            String v = map.get(key);
            rules = addRule(rules, key, v);
        }
        return rules;
    }

    private static List<Rule> convertToRules(List<String> order, MultiMap<String, String> multiMap) {
        List<Rule> rules = new ArrayList<Rule>();
        for (String key : order) {
            final Collection<String> stringCollection = multiMap.get(key);
            addRule(rules, key, stringCollection.toArray(new String[stringCollection.size()]));
        }
        return rules;
    }

    private static List<Rule> addRule(List<Rule> rules, String key, String... v) {
        switch (key.length()) {
            case 1:
                rules.add(new CharRule(key.charAt(0), v));
                break;
            default:
                rules.add(new StringRule(key, v));
        }
        return rules;
    }
}
