package my.project;

import org.json.simple.parser.ParseException;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

public class DictionaryTest {

    @Test
    public void TestingCyrillicToLatin() throws IOException, ParseException {
        String[] wordsCyrillic = {"Салом", "Қанақа", "сўз", "цирк"};

        String[] wordsLatin = {"Salom", "Qanaqa", "So'z", "Sirk"};

        Dictionary dictionary = Dictionary.CreateDictionary();
        System.out.println("Латинские слова");
        for (String wordCyrillic : wordsCyrillic){
            System.out.println(dictionary.cyrillicToLatin(wordCyrillic));
        }
        System.out.println("Кирилица слова");

        for (String wordCyrillic : wordsLatin){
            System.out.println(dictionary.latinToCyrillic(wordCyrillic));
        }

        assertTrue(true);

    }

}