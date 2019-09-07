package Lab_2_Final;

import java.io.IOException;
import org.xml.sax.SAXException;

public class Main {
    public static void main(String[] args) throws SAXException, IOException {
        //insert 6 test questions of 3 different types.
        MCQ mcq1 = new MCQ("5+5 = ?", "2", "D", "1", "5", "-3", "10");
        MCQ mcq2 = new MCQ("10-5 = ?", "2", "B", "2", "5", "10", "7");
        SAQ saq1 = new SAQ("What is the best major?", "4", "MCS");
        SAQ saq2 = new SAQ("What is the name of my school?", "4", "Yale-NUS");
        LAQ laq1 = new LAQ("Did the chicken or the egg come first? Explain.", "0", "Blah blah");
        LAQ laq2 = new LAQ("How did this examination go for you?", "0", "Blah blah blah");

        //add the test questions to the exam.
        Exam ExamDemo = new Exam("Exam 1");
        ExamDemo.addMCQ(mcq1);
        ExamDemo.addMCQ(mcq2);
        ExamDemo.addShortAns(saq1);
        ExamDemo.addShortAns(saq2);
        ExamDemo.addLongAns(laq1);
        ExamDemo.addLongAns(laq2);

//      Printing and saving demo exam as XML file, and checking that when we read it, it is the same.
        System.out.println(ExamDemo.toXML());
        ExamDemo.saveXMLFile("Exam.xml");
        Exam ExamDemo2 = Exam.readXMLFile("Exam.xml");
        System.out.println(ExamDemo2.toXML());
        ExamDemo2.saveXMLFile("Exam2.xml");
//      run the exam.
        ExamDemo.implementExam();
    }
    
}
