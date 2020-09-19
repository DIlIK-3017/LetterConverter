package my.project;

import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        String[] wordsCyrillic = {"Салом", "Қанақа", "сўз", "цирк", "Алейкум"};

        String[] wordsLatin = {"Salom", "Qanaqa", "So'z", "Sirk", "Qish"};

        Dictionary dictionary = Dictionary.CreateDictionary();
        System.out.println("Латинские слова");
        for (String wordCyrillic : wordsCyrillic){
            System.out.println(dictionary.cyrillicToLatin(wordCyrillic));
        }
        System.out.println("Кирилица слова");

        for (String wordCyrillic : wordsLatin){
            System.out.println(dictionary.latinToCyrillic(wordCyrillic));
        }
    }
}
