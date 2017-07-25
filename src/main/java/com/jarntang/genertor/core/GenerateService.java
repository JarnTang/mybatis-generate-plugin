package com.jarntang.genertor.core;

import com.jarntang.genertor.DataStorage.Configuration;
import com.jarntang.genertor.Env;
import com.jarntang.genertor.core.gen.java.DaoGenerator;
import com.jarntang.genertor.core.gen.Generator;
import com.jarntang.genertor.core.gen.xml.MapperGenerator;
import com.jarntang.genertor.core.gen.java.ModelGenerator;
import com.jarntang.genertor.core.gen.java.PagerGenerator;
import com.jarntang.genertor.core.gen.java.QueryModelGenerator;
import com.jarntang.genertor.core.gen.java.RepositoryGenerator;
import com.jarntang.genertor.core.model.CodeInfo;
import com.jarntang.genertor.core.model.Context;
import com.jarntang.genertor.core.model.TableInfo;
import com.jarntang.genertor.util.DbUtils;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.ToString;

/**
 * code gen service.
 *
 * @author qinmu
 * @since 2017/07/19 17:20
 */
public class GenerateService {

  private static Set<Generator> generatorSet = new HashSet<>();

  static {
    generatorSet.add(new DaoGenerator());
    generatorSet.add(new MapperGenerator());
    generatorSet.add(new ModelGenerator());
    generatorSet.add(new PagerGenerator());
    generatorSet.add(new QueryModelGenerator());
    generatorSet.add(new RepositoryGenerator());
  }


  public Set<CodeInfo> generate(Configuration input, Set<String> filterTableNames) {
    List<TableInfo> tableList = DbUtils
        .getAllTableInfo(input.getDbProtocolAddress(), input.getUserName(), input.getPassword());

    if (filterTableNames != null && !filterTableNames.isEmpty()) {
      tableList = tableList.stream().filter(t -> filterTableNames.contains(t.getName()))
          .collect(Collectors.toList());
    }

    Context context = new Context();
    String outputDir = input.getOutputDir();
    ProjectDirectory directory = getProjectDirectory(outputDir, Env.project.getBasePath());
    context.setBasePackage(directory.getBasePackage());
    context.setJavaFileDirectory(directory.getJavaFileDirectory());
    context.setResourcesFileDirectory(directory.getResourceFileDirectory());

    return generate(context, tableList);
  }

  ProjectDirectory getProjectDirectory(String outputDir, String projectBasePath) {
    ProjectDirectory directory = new ProjectDirectory();

    String packagePath = outputDir.replace(projectBasePath, "");

    String javaSourcePath = "src/main/java";
    String resourcePath = "src/main/resources";
    if (packagePath.contains(javaSourcePath)) {
      int javaSourceIndex = packagePath.indexOf(javaSourcePath);
      int beginIndex = javaSourceIndex + javaSourcePath.length();
      String javaDirectory = projectBasePath + packagePath.substring(0, beginIndex);
      packagePath = packagePath.substring(beginIndex);
      if (packagePath.startsWith("/")) {
        packagePath = packagePath.substring(1);
      }
      directory.setJavaFileDirectory(javaDirectory);
      directory.setBasePackage(packagePath.replace("/", "."));
      directory.setResourceFileDirectory(
          outputDir.substring(0, outputDir.indexOf(javaSourcePath)) + "/" + resourcePath);
    } else {
      directory.setBasePackage("");
      directory.setJavaFileDirectory(outputDir);
      directory.setResourceFileDirectory(outputDir + "/" + resourcePath);
    }
    return directory;
  }

  Set<CodeInfo> generate(Context context, List<TableInfo> tableList) {
    Set<CodeInfo> codes = new HashSet<>();
    for (TableInfo table : tableList) {
      Set<CodeInfo> codeList = generatorSet.stream()
          .map(generator -> generator.generate(context, table))
          .collect(Collectors.toSet());
      codes.addAll(codeList);
    }
    return codes;
  }

  @Data
  @ToString
  static class ProjectDirectory {

    String basePackage;
    String javaFileDirectory;
    String resourceFileDirectory;
  }

}
