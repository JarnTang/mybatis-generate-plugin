package com.jarntang.genertor.core.model;

import lombok.Data;

/**
 * context data.
 *
 * @author qinmu
 * @since 2017/07/07 17:47
 */
@Data
public class Context {

  String basePackage;
  String javaFileDirectory;
  String resourcesFileDirectory;

}
