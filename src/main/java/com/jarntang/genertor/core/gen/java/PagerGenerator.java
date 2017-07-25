package com.jarntang.genertor.core.gen.java;

import com.jarntang.genertor.core.model.ClassInfo;
import lombok.Builder;
import lombok.Data;

/**
 * pager class generator.
 *
 * @author qinmu
 * @since 2017/07/21 18:28
 */
public class PagerGenerator extends AbstractJavaFileGenerator {

  public PagerGenerator() {
    super("pager-template.flt");
  }

  @Override
  public String getPackageSuffix() {
    return PackageSuffix.PAGER.getSuffix();
  }

  @Override
  public Object buildData(ClassInfo classInfo, String basePackageName, String tableName) {
    return new PagerModel(classInfo.getAuthor(), classInfo.getCreateTime(),
        pagerClassBasePackage(basePackageName));
  }

  @Override
  String getJavaFileFullPackageName(String tableName, String basePackageName) {
    return pagerClassFullPackage(basePackageName);
  }

  @Data
  @Builder
  public static class PagerModel {

    private String author;
    private String createTime;
    private String packageName;
  }

}
