package com.jarntang.genertor.core.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * class info.
 *
 * @author qinmu
 * @since 2017/07/07 17:42
 */
@Data
public class ClassInfo {

  private String author;
  private String createTime;
  private String packageName;
  private String className;
  private String comment;
  private List<FieldInfo> fields;

  @Data
  @AllArgsConstructor
  public static class FieldInfo {

    private String name;
    private String type;
    private Class typeClass;
    private String comment;

  }
}
