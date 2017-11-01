package com.jarntang.genertor.core.gen.xml;

import com.intellij.psi.codeStyle.NameUtil;
import com.jarntang.genertor.core.gen.AbstractGenerator;
import com.jarntang.genertor.core.model.ClassInfo;
import com.jarntang.genertor.core.model.CodeInfo;
import com.jarntang.genertor.core.model.Context;
import com.jarntang.genertor.core.model.TableInfo;
import com.jarntang.genertor.util.FreemarkerUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * abstract mapper file generator.
 *
 * @author qinmu
 * @since 2017/07/25 14:11
 */
public abstract class AbstractMapperFileGenerator extends AbstractGenerator {

  public AbstractMapperFileGenerator(String templateFileName) {
    super(templateFileName);
  }

  abstract Object buildData(TableInfo tableInfo, String basePackageName);

  /**
   * 生成代码.
   *
   * @param context 上下文信息
   * @return 模板代码
   */
  public CodeInfo generate(Context context, TableInfo tableInfo) {
    String fileName = getTemplateFileName();
    Object data = buildData(tableInfo, context.getBasePackage());
    String code = FreemarkerUtils.rendering(fileName, data);
    ClassInfo classInfo = buildJavaClass(context, tableInfo);
    String javaPath = getFilePath(context, classInfo.getClassName(), context.getBasePackage());

    return new CodeInfo(code, javaPath);
  }

  @Override
  public String getFilePath(Context context, String tableName, String basePackageName) {
    String join = StringUtils.join(NameUtil.nameToWords(tableName), "-").toLowerCase();
    return context.getResourcesFileDirectory() + "/mapper/" + join + "-mapper.xml";
  }
}
