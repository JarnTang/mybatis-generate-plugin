package com.jarntang.genertor.core.gen.xml;

import com.jarntang.genertor.core.model.TableInfo;
import com.jarntang.genertor.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

import static com.jarntang.genertor.util.StringUtils.camelCaseName;

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
  public Object buildData(TableInfo tableInfo, String basePackageName) {
    String tableName = tableInfo.getName();
    String className = StringUtils.underLineToUpperName(tableName);
    return MapperModel.builder()
        .tableName(tableName)
        .resultMapName(className + "Map")
        .resultMapType(entityClassFullPackage(basePackageName, className))
        .daoClassPackage(daoClassFullPackage(basePackageName, className))
        .fields(tableInfo.getColumns().stream().map(c -> new MapField(c.getName(), camelCaseName(c.getName())))
                .collect(Collectors.toList()))
            .fieldNames(tableInfo.getColumns().stream().map(TableInfo.ColumnInfo::getName).collect(Collectors.toList()))
        .build();
  }

  @Data
  @Builder
  public static class MapperModel {

    private String tableName;
    private List<MapField> fields;
    private String resultMapName;
    private List<String> fieldNames;
    private String resultMapType;
    private String daoClassPackage;

  }

  @Data
  @AllArgsConstructor
  public static class MapField{

    String dbColumn;
    String javaField;
  }

}
