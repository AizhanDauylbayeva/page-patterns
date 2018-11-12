package Entity;

import java.util.ArrayList;
import java.util.List;

public class Mail {
    private String addressee;
    private String subject;
    private String body;
   // private List<Mail> letters = new ArrayList<Mail>();

    public Mail(String addressee, String subject, String body) {
        this.addressee = addressee;
        this.subject = subject;
        this.body = body;
    }

    /*public void getLetter(){
        for (Mail letter : letters) {
            System.out.println(letter);
        }
    }*/
    public String getAddressee() {
        return addressee;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "Mail{" +
                "addressee='" + addressee + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
