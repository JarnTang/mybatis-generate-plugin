package com.jarntang.genertor.core.gen.java;

import com.jarntang.genertor.core.model.ClassInfo;
import com.jarntang.genertor.core.model.ClassInfo.FieldInfo;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * query model generator.
 *
 * @author qinmu
 * @since 2017/07/21 18:33
 */
public class QueryModelGenerator extends AbstractJavaFileGenerator {

  public QueryModelGenerator() {
    super("query-model-template.flt");
  }

  @Override
  public String getPackageSuffix() {
    return PackageSuffix.QUERY.getSuffix();
  }

  @Override
  public Object buildData(ClassInfo classInfo, String basePackageName, String tableName) {
    return QueryModel.builder()
        .author(classInfo.getAuthor())
        .createTime(classInfo.getCreateTime())
        .tableName(tableName)
        .fields(classInfo.getFields())
        .packageName(queryClassBasePackage(basePackageName))
        .build();
  }

  @Override
  String getJavaFileFullPackageName(String tableName, String basePackageName) {
    return queryClassFullPackage(basePackageName, tableName);
  }

  @Data
  @Builder
  public static class QueryModel {

    private String author;
    private String tableName;
    private String createTime;
    private String packageName;
    private List<FieldInfo> fields;
  }

}
