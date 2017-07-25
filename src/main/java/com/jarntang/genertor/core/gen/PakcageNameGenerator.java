package com.jarntang.genertor.core.gen;

import static org.apache.commons.lang3.StringUtils.removeEnd;
import static org.apache.commons.lang3.StringUtils.removeStart;

import com.jarntang.genertor.core.gen.AbstractGenerator.PackageSuffix;
import org.jetbrains.annotations.NotNull;

/**
 * java package name generator.
 *
 * @author qinmu
 * @since 2017/07/25 15:51
 */
public class PakcageNameGenerator {

  public String daoClassName(String tableName) {
    return tableName + "Dao";
  }

  public String entityBasePackage(String basePackageName) {
    String result = basePackageName + "." + PackageSuffix.MODEL.getSuffix();
    return formatPackage(result);
  }

  public String entityClassFullPackage(String basePackageName, String tableName) {
    String result = basePackageName + "." + PackageSuffix.MODEL.getSuffix() + "." + tableName;
    return formatPackage(result);
  }

  public String daoBasePackage(String basePackageName) {
    String result = basePackageName + "." + PackageSuffix.DAO.getSuffix();
    return formatPackage(result);
  }

  public String daoClassFullPackage(String basePackageName, String tableName) {
    String result =
        basePackageName + "." + PackageSuffix.DAO.getSuffix() + "." + daoClassName(tableName);
    return formatPackage(result);
  }

  public String queryClassBasePackage(String basePackageName) {
    String result = basePackageName + "." + PackageSuffix.QUERY.getSuffix();
    return formatPackage(result);
  }

  public String queryClassFullPackage(String basePackageName, String tableName) {
    String result = basePackageName + "." + PackageSuffix.QUERY.getSuffix() + "." + queryClassName(
        tableName);
    return formatPackage(result);
  }

  @NotNull
  public String queryClassName(String tableName) {
    return tableName + "Query";
  }

  public String pagerClassBasePackage(String basePackageName) {
    String result = basePackageName + "." + PackageSuffix.PAGER.getSuffix();
    return formatPackage(result);
  }

  public String pagerClassFullPackage(String basePackageName) {
    String result = basePackageName + "." + PackageSuffix.PAGER.getSuffix() + ".Pager";
    return formatPackage(result);
  }

  public String repositoryClassBasePackage(String basePackageName) {
    String result = basePackageName + "." + PackageSuffix.REPOSITORY.getSuffix();
    return formatPackage(result);
  }

  @NotNull
  public String repositoryClassName(String tableName) {
    return tableName + "Repository";
  }

  public String repositoryClassFullPackage(String basePackageName, String tableName) {
    String result = basePackageName + "." + PackageSuffix.REPOSITORY.getSuffix()
        + "." + repositoryClassName(tableName);

    return formatPackage(result);
  }

  private String formatPackage(String packageName) {
    String result = removeStart(packageName, ".");
    result = removeEnd(result, ".");
    return result;
  }

}
