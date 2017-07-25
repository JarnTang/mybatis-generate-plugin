package com.jarntang.genertor.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * java code info.
 *
 * @author qinmu
 * @since 2017/07/20 16:57
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode
public class CodeInfo {

  String javaCode;
  String fileName;

}
