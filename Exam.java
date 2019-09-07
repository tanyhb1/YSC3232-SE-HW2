package Lab_2_Final;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

//exam takes in Name, a list of MCQs, a list of SAQs, and a list of LAQs.
public class Exam {
    String Name;
    List <MCQ> MCQs;
    List <SAQ> SAQs;
    List <LAQ> LAQs;

    Exam(String name){
        this.Name = name;
        this.MCQs = new ArrayList<>();
        this.SAQs = new ArrayList<>();
        this.LAQs = new ArrayList<>();
    }
    
    Exam(){
        this.Name = "TBC";
        this.MCQs = new ArrayList<>();
        this.SAQs = new ArrayList<>();
        this.LAQs = new ArrayList<>();
    }
    //self-explanatory.
    void addMCQ (MCQ q){
        this.MCQs.add(q);
    }
    void addShortAns (SAQ q){
        this.SAQs.add(q);
    }
    void addLongAns (LAQ q){
        this.LAQs.add(q);
    }
    //for our exam, we enforce that MCQs come first, then SAQ come second, then LAQ come last. This is like the Singapore style exams.
    public String toXML (){
        String xmlMCQ = "";
        for (MCQ q: MCQs){
          xmlMCQ+= q.toXML();
      }
        String xmlShortAns = "" ;
        for (SAQ q: SAQs){
          xmlShortAns+= q.toXML();
      }
        String xmlLongAns = "" ;
        for (LAQ q: LAQs){
          xmlLongAns+= q.toXML();
      }
        String xmlExam = "<Exam name='" + this.Name.replace("'", "") + "'>\n"
                + xmlMCQ + xmlShortAns + xmlLongAns
                + "</Exam>\n";
        return xmlExam;
    }
    void saveXMLFile(String filename) {
        String str = this.toXML();
          try {
              Files.write(Paths.get(filename), str.getBytes());
          } catch (IOException ex) {
              Logger.getLogger(Exam.class.getName()).log(Level.SEVERE, null, ex);
          }
    }
    public static Exam readXMLFile(String filename) throws SAXException, IOException {
        
        Exam readExam = new Exam();
        File fXmlFile = new File(filename);
        
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize(); // Combines the new lines in the XML file
            
            Element examE = doc.getDocumentElement();
            String TheExamName = examE.getAttribute("name");
            readExam= new Exam(TheExamName);
            // read each element with the MCQ tag, and within those elements, we iterate through their fields, creating a new MCQ
            NodeList mcqElements = examE.getElementsByTagName("MCQ");
            for (int i = 0; i < mcqElements.getLength(); i++){
                Node bNode = mcqElements.item(i);
                if (bNode.getNodeType() == Node.ELEMENT_NODE){
                    Element mcqE = (Element) bNode; //USUALLY cannot cast superclass to a subclass, the other way is ok.
                    String mcqDetails = mcqE.getAttribute("question");
                    String mcqAnswer = mcqE.getElementsByTagName("Answer").item(0).getTextContent();
                    String mcqMarks = mcqE.getElementsByTagName("Marks").item(0).getTextContent();
                    String mcqOption1 = mcqE.getElementsByTagName("OptionA").item(0).getTextContent();
                    String mcqOption2 = mcqE.getElementsByTagName("OptionB").item(0).getTextContent();
                    String mcqOption3 = mcqE.getElementsByTagName("OptionC").item(0).getTextContent();
                    String mcqOption4 = mcqE.getElementsByTagName("OptionD").item(0).getTextContent();
                    MCQ Q = new MCQ (mcqDetails, mcqMarks, mcqAnswer, mcqOption1, mcqOption2, mcqOption3, mcqOption4);
                    readExam.addMCQ(Q);
                }        
            }
//            read each element with the ShortAns tag, and then iterate through their fields, creating a new SAQ
            NodeList shortansElements = examE.getElementsByTagName("ShortAns");
            for (int i = 0; i < shortansElements.getLength(); i++){
                Node bNode = shortansElements.item(i);
                if (bNode.getNodeType() == Node.ELEMENT_NODE){
                    Element shortAnsE = (Element) bNode; //USUALLY cannot cast superclass to a subclass, the other way is ok.
                    String shortAnsDetails = shortAnsE.getAttribute("question");
                    String shortAnsAnswer = shortAnsE.getElementsByTagName("Answer").item(0).getTextContent();
                    String shortAnsMarks = shortAnsE.getElementsByTagName("Marks").item(0).getTextContent();
                    SAQ Q = new SAQ(shortAnsDetails , shortAnsMarks , shortAnsAnswer);
                    readExam.addShortAns(Q);
                }        
            }
//            read each element with the LongAns tag, and iterate through their fields, creating a new LAQ.
            NodeList longAnsElements = examE.getElementsByTagName("LongAns");
            for (int i = 0; i < longAnsElements.getLength(); i++){
                Node bNode = longAnsElements.item(i);
                if (bNode.getNodeType() == Node.ELEMENT_NODE){
                    Element longAnsE = (Element) bNode; //USUALLY cannot cast superclass to a subclass, the other way is ok.
                    String longAnsDetails = longAnsE.getAttribute("question");
                    String longAnsAnswer = longAnsE.getElementsByTagName("Answer").item(0).getTextContent();
                    String longAnsMarks = longAnsE.getElementsByTagName("Marks").item(0).getTextContent();
                    LAQ Q = new LAQ(longAnsDetails, longAnsMarks, longAnsAnswer);
                    readExam.addLongAns(Q);
                }        
            }
//            return the new exam with all the stored questions.
            return readExam;
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Exam.class.getName()).log(Level.SEVERE, null, ex);
        }
        return readExam;
     }

//     initialize two variables to keep track of total possible marks, and the marks scored by the user.
//    then, using the methods in the subclasses of Question, we ask the question and retrieve the answer from the user.
//    we mandate that LAQ have 0 marks, since it is difficult to machine-check answers for ambiguous long answer questions.
    void implementExam (){
        int totalMarks = 0;
        int totalPossibleMarks = 0;
        for (MCQ q : MCQs){
            q.askMCQ();
            q.printMcqOptions();
            totalMarks += q.markAnswer();
            totalPossibleMarks += q.getMarks();
        }
        for (SAQ q : SAQs){
            q.askShortAnsQns();
            totalMarks += q.markAnswer();
            totalPossibleMarks += q.getMarks();
        }
        for (LAQ q : LAQs){
            q.askLongAnsQns();
            q.storeAnswer();
            totalPossibleMarks += q.getMarks();
        }
        System.out.println("The exam is over.");
        System.out.println("Calculating your score (exclusive of long answer questions)...");
        System.out.println("Congratulations! You have scored " + totalMarks + " out of " + Integer.toString(totalPossibleMarks) +" marks.");
        
    }
}
