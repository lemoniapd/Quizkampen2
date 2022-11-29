package Quizkampen.Server.Questions;

import java.util.List;

public class Qdatabase {

    //Här sparar vi alla frågor i listor för varje kategori
    //"Right answer" är en char som vi sen kan jämföra med knapparna (första knappen ger char A, andra B etc...)
    Question m1 = new Question("Matematik", "Vad är 1+1?", new String[]{"1", "2", "3", "4"}, 'B');
    Question m2 = new Question("Matematik", "Vad är 2+2?", new String[]{"1", "2", "3", "4"}, 'D');

    Question g1 = new Question("Geografi", "Hur många världsdelar finns det?", new String[]{"6", "7", "8", "9"}, 'B');
    Question g2 = new Question("Geografi","Hur många olika färger har Finlands flagga?", new String[]{"1", "2", "3", "4"}, 'B');

    Question s1 = new Question("Svenska seder","Vilken månad firas midsommar?", new String[]{"Maj", "Juli", "Juni", "September"}, 'C');
    Question s2 = new Question("Svenska seder","Vilken av dessa äts endast vid jul?", new String[]{"Köttbullar", "Prinskorv", "Sill", "Pepparkakor"}, 'D');

    //Listorna med frågor skickas till klientprogrammet
    List<Question> mqList = List.of(m1, m2);
    List<Question> gqList = List.of(g1, g2);
    List<Question> sqList = List.of(s1, s2);


    public Qdatabase() {
    }

    public List<Question> getMqList() {
        return mqList;
    }
    public List<Question> getGqList() {
        return gqList;
    }
    public List<Question> getSqList() {
        return sqList;
    }
}
