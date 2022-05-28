package com.cc.utils;

import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;

/**
 * 处理sql工具
 *
 * @author cc
 */
public class CcSql {

    /**
     * 日志服务
     */
    private static final Logger log = LoggerFactory.getLogger(CcSql.class);

    /**
     * 获取对应配置文件的sql内容
     *
     * @param subFolder
     * @param fileName
     * @param sqlId
     * @return
     */
    public static String getSqlText(String subFolder, String fileName, String sqlId) {
        if (CcString.isEmpty(sqlId)) return null;
        //处理传入文件名后缀
        if (!fileName.contains(".xml")) {
            fileName += ".xml";
        }
        String sqlText = null;
        String classPath = subFolder + CcFile.fileSeparator() + fileName;
        InputStream inputStream = null;
        try {
            //判断当前配置文件是否存在
            classPath = classPath.replace("\\", "/");
            Resource resource = new ClassPathResource(classPath);
            if (resource.exists()) {
                String fullFileName = resource.getURL().getPath();
                fullFileName = URLDecoder.decode(fullFileName, "UTF-8");
                inputStream = new FileInputStream(fullFileName);
                if (inputStream == null) {
                    return null;
                }
                String configText = IOUtils.toString(inputStream, "UTF-8");
                if (CcString.isEmpty(configText)) return null;
                // 读取xml文件，得到Document对象
                Document document = DocumentHelper.parseText(configText);
                if (document == null) return null;
                // 获取根元素
                Element root = (Element) document.selectSingleNode("/sqls/sql[@id='" + sqlId + "']");
                if (root == null) return null;
                sqlText = root.selectSingleNode("script").asXML().trim();
                /**
                 * Element root = document.selectSingleNode("/sqls/sql[@id='"+sqlId+"']")
                 * root.selectSingleNode("script")
                 * root.selectSingleNode("script").asXML().trim()
                 */
            }
        } catch (Exception e) {
            log.error("获取sql语句内容异常", e);
        }
        finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sqlText;

    }
}
