package com.jarntang.genertor.core.gen.java;

import static java.io.File.separator;

import com.jarntang.genertor.core.gen.AbstractGenerator;
import com.jarntang.genertor.core.model.ClassInfo;
import com.jarntang.genertor.core.model.CodeInfo;
import com.jarntang.genertor.core.model.Context;
import com.jarntang.genertor.core.model.TableInfo;
import com.jarntang.genertor.util.FreemarkerUtils;
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

  abstract Object buildData(ClassInfo classInfo, String basePackageName, String tableName);

  /**
   * 生成代码.
   *
   * @param context 上下文信息
   * @return 模板代码
   */
  public CodeInfo generate(Context context, TableInfo tableInfo) {
    String fileName = getTemplateFileName();
    ClassInfo classInfo = buildJavaClass(context, tableInfo);
    Object data = buildData(classInfo, context.getBasePackage(), classInfo.getClassName());
    String code = FreemarkerUtils.rendering(fileName, data);

    String javaPath = getFilePath(context, classInfo.getClassName(), context.getBasePackage());

    return new CodeInfo(code, javaPath);
  }

  public String getFilePath(Context context, String tableName, String basePackageName) {
    String fullPackageName = getJavaFileFullPackageName(tableName, basePackageName);

    String fullPath = StringUtils.replace(fullPackageName, ".", "/");

    return context.getJavaFileDirectory() + separator + fullPath + ".java";
  }


}
