import org.json.JSONObject;
import org.w3c.dom.ls.LSOutput;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class SAXReader extends DefaultHandler {
    private int indent = 0;
    final static int INDENT = 4;

    private static HashMap<String, String> subjects = new HashMap<>();

    private static String studentName = "";

    private static int average;
    private static final JSONObject group = new JSONObject();

    public void startDocument() throws SAXException {
        printString("Начало документа");
    }

    public void endDocument() throws SAXException {
        printString("Конец документа");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        indent += INDENT;

        String title = attributes.getValue("title");
        String firstName = attributes.getValue("firstname");
        String lastname = attributes.getValue("lastname");
        String groupnumber = attributes.getValue("groupnumber");
        String mark = attributes.getValue("mark");

        printString("Элемент " + qName + ":");
        if (firstName != null && lastname != null) {
            studentName = firstName + " " + lastname;
            printString(firstName + " " + lastname + " " + groupnumber);
        } else if (title != null && mark != null) {
            subjects.put(title, mark);
            printString(title + " " + mark);
        }
    }

    public void endElement(String uri, String localName,
                           String qName) throws SAXException {
        printString("Конец элемента " + qName + ".");
        if (Objects.equals(qName, "student")) {

            group.put(studentName, subjects);
            subjects = new HashMap<>();

            var elements = group.getJSONObject(studentName);
            int avg = 0;
            for (var key : elements.names()) {
                avg += elements.getInt((String) key);
            }
            avg = avg / elements.names().length();
            if (average == 0) {
                System.err.println("Для студента " + studentName + " была проставлена оценка " + avg);
            } else if (avg != average) {
                printString("" + average);
                System.err.println("Для студента " + studentName);
                System.err.println("Средняя оценка не соответствует действительности оценка: " + average + " была заменена на " + avg);
            }
            average = 0;
        }

        indent -= INDENT;

    }

    public void warning(SAXParseException e) throws SAXException {
        System.out.println("Предупреждение :" + e.getPublicId());
    }

    public void error(SAXParseException e) throws SAXException {
        System.out.println("Ошибка :" + e.getPublicId());
    }

    public void fatalError(SAXParseException e) throws SAXException {
        System.out.println("Фатальная ошибка :" + e.getPublicId());
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        indent += INDENT;
        String str = new String(ch, start, length);
        average = Integer.parseInt(str.trim());
        printString("" + average);
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
        DefaultHandler handler = new SAXReader();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = null;
        try {
            parser = factory.newSAXParser();
            parser.parse("file:///src/main/resources/saxStudent.xml", handler);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        System.out.println();
    }

}
