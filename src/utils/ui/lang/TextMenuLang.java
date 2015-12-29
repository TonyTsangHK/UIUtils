package utils.ui.lang;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import utils.file.FileUtil;
import utils.json.parser.JsonParser;

public final class TextMenuLang {
    public static final String DEFAULT_LOCALE = "en_US";

    private static JsonParser jsonParser = JsonParser.getInstance();

    private TextMenuLang() {}

    private static final Map<String, Map<String, String>> langMap = new HashMap<String, Map<String, String>>();

    public static Map<String, String> getLangMap(String locale) {
        if (langMap.containsKey(locale)) {
            return langMap.get(locale);
        } else {
            Map<String, String> map = getLangMap(
                TextMenuLang.class.getResourceAsStream("lang_" + locale + ".json")
            );

            if (map == null) {
                map = getLangMap(
                    TextMenuLang.class.getResourceAsStream("lang_" + DEFAULT_LOCALE + ".json")
                );
            }

            if (map != null) {
                langMap.put(locale, map);
            }

            return map;
        }
    }

    private static Map<String, String> getLangMap(InputStream stream) {
        if (stream != null) {
            return jsonParser.parseMap(FileUtil.getFileContent(stream, "UTF-8"));
        } else {
            return null;
        }
    }

    public static String getLangValue(String locale, String key) {
        return getLangMap(locale).get(key);
    }
}