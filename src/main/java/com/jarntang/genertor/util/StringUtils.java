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
    ConvertToCamelCaseMacro convert = new ConvertToCamelCaseMacro();
    return convert.convertString(string).toString();
  }

}
