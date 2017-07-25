package com.jarntang.genertor.core.gen.java;

import static java.io.File.separator;

import com.jarntang.genertor.core.gen.AbstractGenerator;
import com.jarntang.genertor.core.model.Context;
import org.apache.commons.lang3.StringUtils;

/**
 * abstract java file generator.
 *
 * @author qinmu
 * @since 2017/07/25 13:41
 */
public abstract class AbstractJavaFileGenerator extends AbstractGenerator {

  public AbstractJavaFileGenerator(String templateFileName) {
    super(templateFileName);
  }

  abstract String getJavaFileFullPackageName(String tableName, String basePackageName);

  public String getFilePath(Context context, String tableName, String basePackageName) {
    String fullPackageName = getJavaFileFullPackageName(tableName, basePackageName);

    String fullPath = StringUtils.replace(fullPackageName, ".", "/");

    return context.getJavaFileDirectory() + separator + fullPath + ".java";
  }


}
