package Lab_2_Final;

import java.util.Scanner;

interface XMLizable {
    String toXML();

}
//MCQ,LAQ,SAQ all inherit details and marks from the abstract class Question
//implements XMLizable interface to ensure that all subclasses have toXML() method.
abstract class Question implements XMLizable{
    String QuestionDetails;
    String QuestionMarks;
}
//The methods of the following classes MCQ LAQ SAQ are self-explanatory. For marking answers, I implemented a simple .equals() check
//to ensure that the answer string is the same as the one input by the user. These methods are abstracted out so the implementation of the
//exam is lightweight.
//MCQ class takes in option A,B,C,D and an answer
class MCQ extends Question {
    String OptionA;
    String OptionB;
    String OptionC;
    String OptionD;
    String Answer;

    MCQ(String details, String marks){
        this.QuestionDetails = details;
        this.QuestionMarks = marks;
    }

    MCQ(String details, String marks, String answer, String opt_A, String opt_B , String opt_C, String opt_D){
        this.QuestionDetails = details;
        this.QuestionMarks = marks;
        this.Answer = answer;
        this.OptionA = opt_A;
        this.OptionB = opt_B;
        this.OptionC = opt_C;
        this.OptionD = opt_D;
    }

    void askMCQ(){
        System.out.println(this.QuestionDetails + "(" + this.QuestionMarks + " Marks)");
    }

    void printMcqOptions(){
        System.out.println("Option A is " + this.OptionA + ".");
        System.out.println("Option B is " + this.OptionB + ".");
        System.out.println("Option C is " + this.OptionC + ".");
        System.out.println("Option D is " + this.OptionD + ".");
    }

    int markAnswer(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter your answer.");
        String studentAnswer = scanner.nextLine();
        if (this.Answer.equals(studentAnswer)){
            return Integer.parseInt(this.QuestionMarks);
        }
        else {
            return 0;
        }
    }

    int getMarks () {
        return Integer.parseInt(this.QuestionMarks);
    }

    public String toXML(){
        String stringAns = "\t\t<Answer>" + this.Answer + "</Answer>\n";
        String stringMarks = "\t\t<Marks>" + this.QuestionMarks + "</Marks>\n";
        String stringOptionA = "\t\t<OptionA>" + this.OptionA + "</OptionA>\n";
        String stringOptionB = "\t\t<OptionB>" + this.OptionB + "</OptionB>\n";
        String stringOptionC = "\t\t<OptionC>" + this.OptionC + "</OptionC>\n";
        String stringOptionD = "\t\t<OptionD>" + this.OptionD + "</OptionD>\n";
        String stringMCQ = "\t<MCQ question ='" + this.QuestionDetails.replace("'", "") + "'>\n" +  stringAns +
                stringMarks + stringOptionA + stringOptionB + stringOptionC + stringOptionD + "\t</MCQ>\n";
        return stringMCQ;
    }



}
//LAQ takes in an answer
class LAQ extends Question {
    String Answer;

    LAQ(String details, String marks, String answer){
        this.QuestionDetails = details;
        this.QuestionMarks = marks;
        this.Answer = answer;
    }
    void askLongAnsQns () {
        System.out.println(this.QuestionDetails + "(" + this.QuestionMarks + " Marks)");
    }

    String storeAnswer (){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter your answer.");
        String studentAnswer = scanner.nextLine();
        return studentAnswer;
    }

    int getMarks () {
        return Integer.parseInt(this.QuestionMarks);
    }
    public String toXML(){
        String stringAns = "\t\t<Answer>" + this.Answer + "</Answer>\n";
        String stringMarks = "\t\t<Marks>" + this.QuestionMarks + "</Marks>\n";
        String stringLAQ = "\t<LongAns question ='" + this.QuestionDetails.replace("'", "") + "'>\n" +  stringAns +
                stringMarks + "\t</LongAns>\n";
        return stringLAQ;
    }
}
//SAQ takes in an answer
class SAQ extends Question{
    String Answer;

    SAQ(String details, String marks, String answer){
        this.QuestionDetails = details;
        this.QuestionMarks = marks;
        this.Answer = answer;
    }
    void askShortAnsQns () {
        System.out.println(this.QuestionDetails + "(" + this.QuestionMarks + " Marks)");
    }

    int markAnswer(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter your answer.");
        String studentAnswer = scanner.nextLine();
        if (this.Answer.equals(studentAnswer)){
            return Integer.parseInt(this.QuestionMarks);
        }
        else {
            return 0;
        }
    }

    int getMarks () {
        return Integer.parseInt(this.QuestionMarks);
    }
    public String toXML(){
        String stringAns = "\t\t<Answer>" + this.Answer + "</Answer>\n";
        String stringMarks = "\t\t<Marks>" + this.QuestionMarks + "</Marks>\n";
        String stringShortAnsQns = "\t<ShortAns question ='" + this.QuestionDetails.replace("'", "") + "'>\n" +  stringAns +
                stringMarks + "\t</ShortAns>\n";
        return stringShortAnsQns;
    }
}
