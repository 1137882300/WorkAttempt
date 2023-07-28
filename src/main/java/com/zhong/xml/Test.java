package com.zhong.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

/**
 * @author: juzi
 * @date: 2023/7/26
 * @desc:
 */
public class Test {

    public static void main(String[] args) {
        try {
            // 创建DocumentBuilderFactory和DocumentBuilder对象
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // 从XML文件中加载文档
            Document document = builder.parse(new File("input.xml"));

            // 获取根元素
            Element root = document.getDocumentElement();

            // 遍历子节点
            NodeList nodeList = root.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    // 获取原始标签名
                    String originalTagName = element.getTagName();

                    // 设置新的标签名
                    String newTagName = "new_" + originalTagName;

                    // 修改标签名
//                    element.setNodeName(newTagName);


                    // 如果需要修改标签内容，可以使用setTextContent()方法
                    // element.setTextContent("New Content");

                    // 输出修改后的标签名
                    System.out.println(originalTagName + " --> " + newTagName);
                }
            }

            // 将修改后的XML保存到新文件中
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File("output.xml"));
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
