import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import model.JAXB.GroupJAXB;
import model.JAXB.Subjects;

import java.io.File;

public class JAXBReader {
    public static void main(String[] args) {
        String fileName = "src/main/resources/saxStudent.xml";

        var groupStudents = fromXmlToObject(fileName);
        assert groupStudents != null;

        groupStudents.getStudents().stream().filter(y -> y.getSubject() != null).forEach(student -> {
            var avg = student.getSubject().size() != 0 ? student.getSubject().stream().mapToInt(Subjects::getMark).sum() / student.getSubject().size() : 0;
            if (avg != student.getAvg()) student.setAvg(avg);
        });

        convertObjectToXml(groupStudents, "src/main/resources/resultJAXB.xml");
    }

    private static GroupJAXB fromXmlToObject(String filePath) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(GroupJAXB.class);
            Unmarshaller un = jaxbContext.createUnmarshaller();

            return (GroupJAXB) un.unmarshal(new File(filePath));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void convertObjectToXml(GroupJAXB student, String filePath) {
        try {
            JAXBContext context = JAXBContext.newInstance(GroupJAXB.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            // маршаллинг объекта в файл
            marshaller.marshal(student, new File(filePath));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

}
