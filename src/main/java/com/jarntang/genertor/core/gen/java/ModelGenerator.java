package com.jarntang.genertor.core.gen.java;

import static com.jarntang.genertor.util.SerialVersionUIDUtils.computeSerialVersionUID;

import com.jarntang.genertor.core.model.ClassInfo;
import com.jarntang.genertor.core.model.ClassInfo.FieldInfo;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * model class generator.
 *
 * @author qinmu
 * @since 2017/07/21 18:20
 */
public class ModelGenerator extends AbstractJavaFileGenerator {

  public ModelGenerator() {
    super("model-template.flt");
  }

  @Override
  public String getPackageSuffix() {
    return PackageSuffix.MODEL.getSuffix();
  }

  @Override
  public Object buildData(ClassInfo classInfo, String basePackageName, String tableName) {
    return EntityModel.builder()
        .tableName(tableName)
        .author(classInfo.getAuthor())
        .fields(classInfo.getFields())
        .createTime(classInfo.getCreateTime())
        .packageName(entityBasePackage(basePackageName))
        .serialVersionUID(computeSerialVersionUID(tableName, classInfo.getFields()) + "L")
        .build();
  }

  @Override
  String getJavaFileFullPackageName(String tableName, String basePackageName) {
    return entityClassFullPackage(basePackageName, tableName);
  }

  @Data
  @Builder
  public static class EntityModel {

    private String author;
    private String tableName;
    private String createTime;
    private String packageName;
    private String serialVersionUID;
    private List<FieldInfo> fields;

  }

}
