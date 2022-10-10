import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.IOException;

public class ReaderDOM {
    private int indent = 0;
    final static int INDENT = 4;
    private static final String[] TYPE_NAMES = {"ELEMENT_NODE", "ATTRIBUTE_NODE",
            "TEXT_NODE", "CDATA_SECTION_NODE", "ENTITY_REFERENCE_NODE", "ENTITY_NODE",
            "PROCESSING_INSTRUCTION_NODE", "COMMENT_NODE", "DOCUMENT_NODE",
            "DOCUMENT_TYPE_NODE", "DOCUMENT_FRAGMENT_NODE", "NOTATION_NODE"};

    public String getTypeName(short nodeType) {
        return TYPE_NAMES[nodeType - 1];
    }

    public void myPrint(Document document) {
        printDocInfo(document);
        printNodeInfo(document);
    }

    public void printDocInfo(Document document) {
        printString("Имя узла документа : " + document.getNodeName());
    }

    public void printElementInfo(Element element) {
        NamedNodeMap nnm = element.getAttributes();
        for (int i = 0; i < nnm.getLength(); i++) {
            Attr attr = (Attr) nnm.item(i);
            printString("Attribute name = " + attr.getName() + ", value = " + attr.getValue());
        }
    }

    public void printTextInfo(Text text) {
        printString("Значение поля : " + text.getData());
    }

    public void printNodeInfo(Node node) {
        indent += INDENT;
        printString("Node name = " + node.getNodeName() +
                ", node type = " + getTypeName(node.getNodeType()));
        if (node instanceof Element) {
            printElementInfo((Element) node);
        } else if (node instanceof Text) {
            printTextInfo((Text) node);
        }
        NodeList nl = node.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            printNodeInfo(nl.item(i));
        }
        indent -= INDENT;
    }

    public void printString(String str) {
        String ind_s;
        if (indent > 0) {
            char[] ind = new char[indent];
            java.util.Arrays.fill(ind, ' ');
            ind_s = new String(ind, 0, indent);
        } else {
            ind_s = "";
        }
        System.out.println(ind_s + str);
    }

    public static void main(String[] args) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse("src\\main\\resources\\saxStudent.xml");
            ReaderDOM obj = new ReaderDOM();
            obj.myPrint(document);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }
}
