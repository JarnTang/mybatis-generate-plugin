package com.jarntang.genertor.ui;

import com.jarntang.genertor.DataStorage.Configuration;
import com.jarntang.genertor.core.GenerateService;
import com.jarntang.genertor.core.model.CodeInfo;
import com.jarntang.genertor.util.FileUtils;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

/**
 * select table model.
 *
 * @author qinmu
 * @since 2017/07/20 16:51
 */
public class SelectTableModel {

  Set<CodeInfo> genCode(Configuration dbConfig, Set<String> selectTableNames) {
    GenerateService service = new GenerateService();
    return service.generate(dbConfig, selectTableNames);
  }

  boolean exist(String file) {
    return Paths.get(file).toFile().exists();
  }

  void writeToFile(CodeInfo code) {
    FileUtils.write(code.getFileName(), code.getJavaCode());
  }

}
