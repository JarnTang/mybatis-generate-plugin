package com.jarntang.genertor.util;

import com.intellij.codeInsight.template.macro.ConvertToCamelCaseMacro;

/**
 * todo
 *
 * @author qinmu
 * @since 2017/07/24 14:56
 */
public class StringUtils {

    public static String camelCaseName(String string) {
        if (string == null) {
            return null;
        }
        ConvertToCamelCaseMacro convert = new ConvertToCamelCaseMacro();
        return convert.convertString(string).toString();
    }

    public static String underLineToUpperName(String string) {
        String str = camelCaseName(string);
        if (str == null || "".equals(str.trim())) {
            return str;
        }
        char[] chars = str.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return new String(chars);
    }

}
