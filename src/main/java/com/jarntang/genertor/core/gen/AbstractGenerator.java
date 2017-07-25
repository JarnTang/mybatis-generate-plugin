package com.jarntang.genertor.core.gen;

import static com.jarntang.genertor.util.TypeUtils.sqlType2JavaType;
import static java.util.Comparator.comparingInt;

import com.jarntang.genertor.core.model.ClassInfo;
import com.jarntang.genertor.core.model.ClassInfo.FieldInfo;
import com.jarntang.genertor.core.model.CodeInfo;
import com.jarntang.genertor.core.model.Context;
import com.jarntang.genertor.core.model.TableInfo;
import com.jarntang.genertor.core.model.TableInfo.ColumnInfo;
import com.jarntang.genertor.util.FreemarkerUtils;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * abstract generator.
 *
 * @author qinmu
 * @since 2017/07/07 17:46
 */
@AllArgsConstructor
public abstract class AbstractGenerator extends PakcageNameGenerator implements Generator {

  @Getter
  private String templateFileName;

  public abstract String getPackageSuffix();

  public abstract String getFilePath(Context context, String tableName, String basePackageName);

  public abstract Object buildData(ClassInfo classInfo, String basePackageName, String tableName);

  /**
   * 生成代码.
   *
   * @param context 上下文信息
   * @return 模板代码
   */
  public CodeInfo generate(Context context, TableInfo tableInfo) {
    String fileName = getTemplateFileName();
    ClassInfo classInfo = buildJavaClass(context, tableInfo);
    Object data = buildData(classInfo, context.getBasePackage(), tableInfo.getName());
    String code = FreemarkerUtils.rendering(fileName, data);

    String javaPath = getFilePath(context, tableInfo.getName(), context.getBasePackage());

    return new CodeInfo(code, javaPath);
  }


  ClassInfo buildJavaClass(Context context, TableInfo tableInfo) {
    ClassInfo classInfo = new ClassInfo();
    classInfo.setClassName(tableInfo.getName());
    classInfo.setComment(tableInfo.getComment());
    classInfo.setAuthor(System.getProperty("user.name"));
    classInfo.setCreateTime(new SimpleDateFormat("yyyy/MM/dd HH:ss").format(new java.util.Date()));
    classInfo.setPackageName(context.getBasePackage() + "." + getPackageSuffix());
    classInfo.setFields(createFields(tableInfo.getColumns()));
    return classInfo;
  }

  private List<FieldInfo> createFields(List<ColumnInfo> columns) {
    List<FieldInfo> fields = new ArrayList<>();
    if (columns != null) {
      for (ColumnInfo column : columns) {
        Class type = sqlType2JavaType(column.getType());
        fields
            .add(new FieldInfo(column.getName(), type.getSimpleName(), type, column.getComment()));
      }
    }

    return fields.stream()
        .sorted(comparingInt(f -> f.getName().length()))
        .collect(Collectors.toList());
  }


  public enum PackageSuffix {
    MODEL("model"), DAO("dao"), QUERY("query"), PAGER("pager"), REPOSITORY("repository");
    private String suffix;

    PackageSuffix(String suffix) {
      this.suffix = suffix;
    }

    public String getSuffix() {
      return suffix;
    }
  }

}
