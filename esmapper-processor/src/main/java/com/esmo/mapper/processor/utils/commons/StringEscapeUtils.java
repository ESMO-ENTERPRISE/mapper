package com.esmo.mapper.processor.utils.commons;

import com.esmo.mapper.processor.utils.commons.translate.*;
import com.esmo.mapper.processor.utils.commons.translate.CharSequenceTranslator;
import com.esmo.mapper.processor.utils.commons.translate.EntityArrays;
import com.esmo.mapper.processor.utils.commons.translate.JavaUnicodeEscaper;
import com.esmo.mapper.processor.utils.commons.translate.LookupTranslator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/*
    copy from appache commons:
    <dependency>
        <groupId>org.apache.commons</groupId>
        <artifactId>commons-text</artifactId>
        <version>1.9</version>
    </dependency>
*/
abstract public class StringEscapeUtils {
    private StringEscapeUtils() {}
    public static final CharSequenceTranslator ESCAPE_JAVA;
    static {
        Map<CharSequence, CharSequence> unescapeJavaMap = new HashMap();
        unescapeJavaMap.put("\"", "\\\"");
        unescapeJavaMap.put("\\", "\\\\");
        ESCAPE_JAVA = new AggregateTranslator(new CharSequenceTranslator[]{new LookupTranslator(Collections.unmodifiableMap(unescapeJavaMap)), new LookupTranslator(EntityArrays.JAVA_CTRL_CHARS_ESCAPE), JavaUnicodeEscaper.outsideOf(32, 127)});
    }

    public static final String escapeJava(String input) {
        return ESCAPE_JAVA.translate(input);
    }

}
