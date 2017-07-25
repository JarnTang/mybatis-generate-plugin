package com.jarntang.genertor.core.gen.xml;

import com.jarntang.genertor.core.model.ClassInfo;
import com.jarntang.genertor.core.model.ClassInfo.FieldInfo;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;

/**
 * mybatis mapper generator.
 *
 * @author qinmu
 * @since 2017/07/20 18:47
 */
public class MapperGenerator extends AbstractMapperFileGenerator {

  public MapperGenerator() {
    super("mapper-template.flt");
  }

  @Override
  public String getPackageSuffix() {
    return null;
  }

  @Override
  public Object buildData(ClassInfo classInfo, String basePackageName, String tableName) {
    return MapperModel.builder()
        .tableName(tableName)
        .resultMapName(tableName + "Map")
        .resultMapType(entityClassFullPackage(basePackageName, tableName))
        .daoClassPackage(daoClassFullPackage(basePackageName, tableName))
        .fields(classInfo.getFields().stream().map(FieldInfo::getName).collect(Collectors.toList()))
        .build();
  }

  @Data
  @Builder
  public static class MapperModel {

    private String tableName;
    private List<String> fields;
    private String resultMapName;
    private String resultMapType;
    private String daoClassPackage;

  }

}
