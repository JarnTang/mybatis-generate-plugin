package com.jarntang.genertor.util;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import java.io.StringWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * the freemarker util.
 *
 * @author qinmu
 * @since 16/8/29 14:27
 */
public class FreemarkerUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(FreemarkerUtils.class);

  /**
   * 通过freemarker渲染模板.
   *
   * @param templateName 模板名称
   * @param model 渲染数据
   * @return 渲染后的内容
   */
  public static String rendering(String templateName, Object model) {
    Configuration config = new Configuration(Configuration.VERSION_2_3_23);
    config.setDefaultEncoding("UTF-8");
    config.setTemplateLoader(new ClassTemplateLoader(FreemarkerUtils.class, "/template"));
    config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    try {
      StringWriter writer = new StringWriter();
      Template template = config.getTemplate(templateName);
      template.process(model, writer);
      return writer.toString();
    } catch (Exception e) {
      System.out.println(e);
      LOGGER.error("生成freemarker文件异常。", e);
    }
    return null;
  }

}
