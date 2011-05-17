package ru.creditnet.util.t13n.lang;

/**
 * @author astepachev
 */
public class Ru extends AbstractLang {

    /* { {native_a, native_b, ...}, { ascii_abc, ... } } */
    private static final String[][][] MAP = {
            {{"ж"}, {"zh"}},
            {{"ш"}, {"sh", "sch"}},
            {{"щ"}, {"shch", "sht"}},
            {{"ю"}, {"ju", "yu", "u"}},
            {{"ч"}, {"ch"}},
            {{"иа", "ия", "я"}, {"ia", "ja", "ya"}},
            {{"ы"}, {"y"}},
            {{"й"}, {"y", "j"}},
            {{"в"}, {"v", "w"}},
            {{"ц"}, {"ts", "c"}},
            {{"кх"}, {"kh"}},
            {{"х"}, {"kh", "ch", "h"}},
            {{"к"}, {"k", "c", "ch"}},
            {{"ф"}, {"f", "ph"}},
            {{"ь"}, {"'",}},
            {{"ай", "аи", "й"}, {"ai"}},

            {{"е"}, {"e",}},
            {{"э"}, {"e",}},
            {{"я"}, {"ya",}},
            {{"и"}, {"i", "y"}},
            {{"у"}, {"u", "oo"}},
            {{"о", "оу"}, {"o"}},
            {{"ай"}, {"aj", "ai"}},
            {{"ия", "я"}, {"ia"}},
            {{"ку", "кью"}, {"q"}}
    };

    private static final String[][] PLAIN = {
            {"ое", "oye"},
            {"aе", "aye"},
            {"уе", "uye"},
            {"ие", "iye"},
            {"эе", "eye"},
            {"ье", "ye"},
            {"ъа", "yа"},
            {"ьы", "y"},
            {"ьи", "yi"},
            {"ьо", "yo"},
            {"ьу", "yu"},
            {"ый", "y"},
            {"ий", "iy"},
            {"и", "i"},
            {"е", "e"},
            {"ё", "yo"},
            {"а", "a"},
            {"б", "b"},
            {"в", "v"},
            {"г", "g"},
            {"д", "d"},
            {"ж", "zh"},
            {"з", "z"},
            {"й", "y"},
            {"к", "k"},
            {"л", "l"},
            {"м", "m"},
            {"н", "n"},
            {"о", "o"},
            {"п", "p"},
            {"р", "r"},
            {"с", "s"},
            {"т", "t"},
            {"у", "u"},
            {"ф", "f"},
            {"х", "kh"},
            {"ц", "ts"},
            {"ч", "ch"},
            {"ш", "sh"},
            {"щ", "shch"},
            {"ъ", ""},
            {"ы", "y"},
            {"ь", ""},
            {"э", "e"},
            {"ю", "yu"},
            {"я", "ya"},
    };

    /* { native , ascii} , entries should be the same size */
    private static final String[][] TR = {
            {"абгдзлмнпрстэ", "abgdzlmnprste"}
    };

    public static final LangData DATA = makeLangData(MAP, TR, PLAIN);
}
