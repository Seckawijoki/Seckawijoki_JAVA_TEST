import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Administrator on 2019/8/12 at 15:39.
 */

public class XmlReader {
  private static final String PATH = "F:\\trunk\\env1\\client\\AppPlay\\OpenMiniWorld\\app\\src\\main\\res\\xml\\channel_info.xml";
//  private static final String PATH = "channel_info.xml";
  public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
    Document document = documentBuilder.parse(PATH);
    Set<String> packageSet = new HashSet<>();
    Node node = document.getFirstChild();
//    NodeList nodeList = node.getChildNodes();
    System.out.println("XmlReader.main(): node.getNodeName() = " + node.getNodeName());
    node = node.getFirstChild();
    System.out.println("XmlReader.main(): node.getNodeName() = " + node.getNodeName());
    NamedNodeMap namedNodeMap;
    String nodeName;
    Node attrNode;
//    String apiid;
    String packageName;
    StringBuilder stringBuilder = new StringBuilder();
    int count = 0;
    while ( node != null ){
      nodeName = node.getNodeName();
      System.out.println("XmlReader.main(): nodeName = " + nodeName);
      if (!nodeName.equals("channel") ){
        node = node.getNextSibling();
        continue;
      }
      namedNodeMap = node.getAttributes();
//      attrNode = namedNodeMap.getNamedItem("apiid");
//      apiid = attrNode.getNodeValue();
      attrNode = namedNodeMap.getNamedItem("package");
      packageName = attrNode.getNodeValue();
      if (packageSet.contains(packageName)){
        node = node.getNextSibling();
        continue;
      }
      packageSet.add(packageName);
      ++count;
      System.out.println("XmlReader.main(): packageName = " + packageName);
      if (count > 1) {
//        stringBuilder.append("\n|| ");
//        stringBuilder.append("\n ");
      }
//      stringBuilder.append("strcpy(packageName, \"").append(packageName).append("\") == 0");
//      stringBuilder.append("packageName.compare(\"").append(packageName).append("\") == 0");
//      stringBuilder.append("m_PackageStringSet.insert(std::string(\"").append(packageName).append("\"));\n");
//      stringBuilder.append("m_PackageStringSet.insert(\"").append(packageName).append("\");\n");
      stringBuilder.append("m_PackageStringBoolMap[\"").append(packageName).append("\"] = 1;\n");
      node = node.getNextSibling();
    }
    System.out.println("XmlReader.main(): stringBuilder.toString() = " + stringBuilder.toString());
  }
}
