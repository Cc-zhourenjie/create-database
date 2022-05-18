package com.cc.utils;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.Iterator;

/**
 * 获取当前项目配置文件
 *
 * @author zrj
 */
public class CcSettingConfig {

    private Config config;

    //全部基础节点
    private Iterator iteratorAll = null;


    public CcSettingConfig(Iterator iterator) {
        this.config = new Config();
        this.iteratorAll = iterator;
    }

    public static CcSettingConfig buildXML(String subFolder, String fileName) {
        //获取当前项目路径
        String classPath = Thread.currentThread().getContextClassLoader().getResource("").toString();
        classPath = classPath.substring(classPath.indexOf("/") + 1);
        if (!fileName.contains(".xml")) {
            fileName += ".xml";
        }
        classPath += subFolder + File.separator + fileName;
        File file = new File(classPath);
        //判断当前配置文件是否存在
        if (file.exists()) {
            try {
                // 创建SAXReader对象，用于读取xml文件
                SAXReader reader = new SAXReader();
                // 读取xml文件，得到Document对象
                Document document = reader.read(file);
                /**
                * Element root = document.selectSingleNode("/sqls/sql[@id='"+sqlId+"']")
                * root.selectSingleNode("script")
                * root.selectSingleNode("script").asXML().trim()
                */
                // 获取根元素
                Element root = document.getRootElement();
                return new CcSettingConfig(root.elementIterator());
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
        return new CcSettingConfig(null);
    }


    public String getValue() {
        return config.getValue(this.iteratorAll);
    }

    public String getText(String name) {
        return config.getText(this.iteratorAll, name);
    }


    private static class Config {

        private String getValue(Iterator iterator) {
            return "测试";
        }


        private String getText(Iterator iterator, String name) {
            if (iterator != null) {
                while (iterator.hasNext()) {
                    // 取出元素
                    Element ele = (Element) iterator.next();
                    // 获取name的属性
                    Attribute attribute = ele.attribute("name");
                    if (CcString.equal(name, attribute.getValue())) {
                        return ele.getText();
                    }
                }

            }
            return "";
        }


    }


    //获取当前项目路径
//        String classPath = Thread.currentThread().getContextClassLoader().getResource("").toString();
//        classPath = classPath.substring(classPath.indexOf("/") + 1);
//        if (!fileName.contains(".xml")) {
//            fileName += ".xml";
//        }
//        classPath += subFolder + "/" + fileName;
//        File file = new File(classPath);
//        if (file.exists()) {
//            SAXReader reader = new SAXReader();
//            Document document = reader.read(file);
//            Element root = document.getRootElement();
//            Iterator iterator = root.elementIterator();
//            while (iterator.hasNext()) {
//                // 取出元素
//                Element e = (Element) iterator.next();
//                System.out.println(e.getName());
//                // 获取id属性
//                Attribute id = e.attribute("name");
//                System.out.println(id.getName() + "=" + id.getValue());
//                //获取内容
//                String text = e.getText();
    // 获取子元素
//                Element name = e.element("name");
//                Element course = e.element("course");
//                Element score = e.element("score");
//                System.out.println(name.getName() + "=" + name.getStringValue());
//                System.out.println(course.getName() + "=" + course.getText());
//                System.out.println(score.getName() + "=" + score.getText());
//                System.out.println("------");


//            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
//            Document document = documentBuilder.parse(file);
//            Element root = document.getDocumentElement();
//            Node node = root.getChildNodes().item(0);
//            if (node instanceof Element) {
//                Element element = (Element) node;
////                    element.getAttribute();
//            }

//            System.out.println();


}

