package com.jarntang.genertor.core.gen.java;

import com.jarntang.genertor.core.model.ClassInfo;
import lombok.Builder;
import lombok.Data;

/**
 * dao generator.
 *
 * @author qinmu
 * @since 2017/07/07 17:49
 */
public class DaoGenerator extends AbstractJavaFileGenerator {

  public DaoGenerator() {
    super("dao-template.flt");
  }

  @Override
  public Object buildData(ClassInfo classInfo, String basePackageName, String tableName) {
    return DaoModel.builder()
        .entityName(tableName)
        .author(classInfo.getAuthor())
        .daoClassName(tableName + "Dao")
        .createTime(classInfo.getCreateTime())
        .packageName(daoBasePackage(basePackageName))
        .entityPackage(entityClassFullPackage(basePackageName, tableName))
        .queryEntityPackage(queryClassFullPackage(basePackageName, tableName))
        .pagerEntityPackage(pagerClassFullPackage(basePackageName))
        .build();
  }

  @Override
  public String getPackageSuffix() {
    return PackageSuffix.DAO.getSuffix();
  }

  @Override
  String getJavaFileFullPackageName(String tableName, String basePackageName) {
    return daoClassFullPackage(basePackageName, tableName);
  }

  @Data
  @Builder
  public static class DaoModel {

    String author;
    String createTime;
    String entityName;
    String daoClassName;
    String packageName;
    String entityPackage;
    String queryEntityPackage;
    String pagerEntityPackage;
  }

}
