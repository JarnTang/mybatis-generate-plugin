package com.jarntang.genertor.core.gen.java;

import static com.jarntang.genertor.util.StringUtils.camelCaseName;

import com.jarntang.genertor.core.model.ClassInfo;
import lombok.Builder;
import lombok.Data;

/**
 * repository class generator.
 *
 * @author qinmu
 * @since 2017/07/21 18:39
 */
public class RepositoryGenerator extends AbstractJavaFileGenerator {

  public RepositoryGenerator() {
    super("repository-template.flt");
  }

  @Override
  public String getPackageSuffix() {
    return PackageSuffix.REPOSITORY.getSuffix();
  }

  @Override
  public Object buildData(ClassInfo classInfo, String basePackageName, String tableName) {
    return RepositoryModel.builder()
        .tableName(tableName)
        .author(classInfo.getAuthor())
        .createTime(classInfo.getCreateTime())
        .daoClassName(daoClassName(tableName))
        .className(repositoryClassName(tableName))
        .daoVariable(camelCaseName(daoClassName(tableName)))
        .packageName(repositoryClassBasePackage(basePackageName))
        .daoClassFullPackage(daoClassFullPackage(basePackageName, tableName))
        .modelClassFullPackage(entityClassFullPackage(basePackageName, tableName))
        .queryClassFullPackage(queryClassFullPackage(basePackageName, tableName))
        .build();
  }

  @Override
  String getJavaFileFullPackageName(String tableName, String basePackageName) {
    return repositoryClassFullPackage(basePackageName, tableName);
  }

  @Data
  @Builder
  public static class RepositoryModel {

    private String author;
    private String className;
    private String tableName;
    private String createTime;
    private String packageName;
    private String daoClassName;
    private String daoVariable;
    private String daoClassFullPackage;
    private String modelClassFullPackage;
    private String queryClassFullPackage;
  }

}
