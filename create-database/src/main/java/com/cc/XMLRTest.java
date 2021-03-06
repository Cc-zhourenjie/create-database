package com.cc;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.Iterator;
import java.util.List;

public class XMLRTest {


    public static void main(String[] args) {
        SAXReader reader = new SAXReader();
        try {
            String fileName = "field_sql.xml";
            //获取当前项目路径
            String classPath = Thread.currentThread().getContextClassLoader().getResource("").toString();
            classPath = classPath.substring(classPath.indexOf("/") + 1);
            if (!fileName.contains(".xml")) {
                fileName += ".xml";
            }
            classPath += "core" + File.separator + fileName;
            File file = new File(classPath);
            // 通过reader对象的read方法加载books.xml文件,获取docuemnt对象。
            Document document = reader.read(file);
            // 通过document对象获取根节点bookstore
            Element bookStore = document.getRootElement();
            // 通过element对象的elementIterator方法获取迭代器
            Iterator it = bookStore.elementIterator();
            // 遍历迭代器，获取根节点中的信息（书籍）
            while (it.hasNext()) {
                System.out.println("=====开始遍历某一本书=====");
                Element book = (Element) it.next();
                // 获取book的属性名以及 属性值
                List<Attribute> bookAttrs = book.attributes();
                for (Attribute attr : bookAttrs) {
                    System.out.println("属性名：" + attr.getName() + "--属性值："
                            + attr.getValue());
                }
                Iterator itt = book.elementIterator();
                while (itt.hasNext()) {
                    Element bookChild = (Element) itt.next();
                    System.out.println("节点名：" + bookChild.getName() + "--节点值：" + bookChild.getStringValue());
                }
                System.out.println("=====结束遍历某一本书=====");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
