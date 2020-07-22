package com.xudod.gen_code.common;

import java.io.File;
import java.io.IOException;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

public class FreemarkerUtils {
    
    /**
     * Freemarker 模板环境配置
     * @return
     * @throws IOException
     */
    public static Configuration initFreemarkerConfiguration(String filePath) {
        Configuration cfg = null;
        try {
            cfg = new Configuration(Configuration.VERSION_2_3_23);
            cfg.setDirectoryForTemplateLoading(new File(filePath));
//            cfg.setDirectoryForTemplateLoading(new File(PROJECT_PATH + TEMPLATE_FILE_PATH));
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        } catch (IOException e) {
            throw new RuntimeException("Freemarker 模板环境初始化异常!", e);
        }
        return cfg;
    }
}
