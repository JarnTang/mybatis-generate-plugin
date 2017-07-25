package com.jarntang.genertor.util;

import com.intellij.openapi.application.PathManager;
import com.jarntang.genertor.DataStorage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * configuration util.
 *
 * @author qinmu
 * @since 2017/07/20 15:57
 */
public class ConfigurationUtils {

  private static final String TEMP_FILE_NAME = "/mybatis_generator_plugin.properties";
  private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationUtils.class);

  private ConfigurationUtils() {
  }

  public static DataStorage.Configuration loadConfiguration() {
    String tempPath = PathManager.getTempPath();
    String configurationFilePath = tempPath + TEMP_FILE_NAME;
    File file = new File(configurationFilePath);
    if (!file.exists()) {
      return null;
    }
    try (FileInputStream inputStream = new FileInputStream(file)) {
      Properties properties = new Properties();
      properties.load(inputStream);
      return DataStorage.Configuration.builder()
          .userName(properties.getProperty("userName"))
          .password(properties.getProperty("password"))
          .outputDir(properties.getProperty("outputDir"))
          .database(properties.getProperty("db"))
          .address(properties.getProperty("ip"))
          .port(properties.getProperty("port")).build();
    } catch (Exception e) {
      LOGGER.error("load properties error.", e);
    }
    return null;
  }

  public static void saveConfiguration(DataStorage.Configuration configuration) {
    try {
      String tempPath = PathManager.getTempPath();
      String configurationFilePath = tempPath + TEMP_FILE_NAME;
      Properties properties = new Properties();
      properties.setProperty("ip", configuration.getAddress());
      properties.setProperty("userName", configuration.getUserName());
      properties.setProperty("password", configuration.getPassword());
      properties.setProperty("port", configuration.getPort());
      properties.setProperty("db", configuration.getDatabase());
      properties.setProperty("outputDir", configuration.getOutputDir());
      FileOutputStream outputStream = new FileOutputStream(new File(configurationFilePath));
      properties.store(outputStream, null);
    } catch (Exception e) {
      LOGGER.error("store configuration error", e);
    }
  }

}
