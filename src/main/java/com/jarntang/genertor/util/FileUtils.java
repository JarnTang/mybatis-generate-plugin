package com.jarntang.genertor.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;

/**
 * file utils.
 *
 * @author Qinmu
 * @since 2016/10/25 18:03
 */
public class FileUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);

  public static void write(String path, String content) {
    FileWriter writer = null;
    try {
      File file = new File(path);
      if (!file.exists()) {
        File parentFile = file.getParentFile();
        if (parentFile != null) {
          parentFile.mkdirs();
        }
        file.createNewFile();
      }
      writer = new FileWriter(file);
      writer.write(content);
    } catch (Exception e) {
      LOGGER.error("write file error,path=" + path, e);
    } finally {
      try {
        if (writer != null) {
          writer.close();
        }
      } catch (Exception e) {
        LOGGER.error("close file error,path=" + path, e);
      }
    }
  }

}
