package com.jarntang.genertor.core.gen.xml;

import com.intellij.psi.codeStyle.NameUtil;
import com.jarntang.genertor.core.gen.AbstractGenerator;
import com.jarntang.genertor.core.model.Context;
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

  @Override
  public String getFilePath(Context context, String tableName, String basePackageName) {
    String join = StringUtils.join(NameUtil.nameToWords(tableName), "-").toLowerCase();
    return context.getResourcesFileDirectory() + "/mapper/" + join + "-mapper.xml";
  }
}
