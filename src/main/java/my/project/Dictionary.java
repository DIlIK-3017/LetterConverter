package my.project;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Dictionary {

    private Map<String, String> latinToCyrillic = new HashMap<String, String>();
    private Map<String, String> cyrillicToLatin = new HashMap<String, String>();

    private static Dictionary instance = null;

    public Map<String, String> getLatinToCyrillic() {
        return latinToCyrillic;
    }

    public void setLatinToCyrillic(Map<String, String> latinToCyrillic) {
        this.latinToCyrillic = latinToCyrillic;
    }

    public Map<String, String> getCyrillicToLatin() {
        return cyrillicToLatin;
    }

    public void setCyrillicToLatin(Map<String, String> cyrillicToLatin) {
        this.cyrillicToLatin = cyrillicToLatin;
    }

    JSONParser jsonParser = new JSONParser();
    JSONArray  jsonArray;

    public static Dictionary CreateDictionary() throws IOException, ParseException {
        if(instance == null)
            instance = new Dictionary();

        return instance;
    }

    private Dictionary() throws IOException, ParseException {
        jsonArray = (JSONArray)jsonParser.parse(new FileReader("Letters.json"));
        for(Object obj : jsonArray){
            JSONObject letters = (JSONObject)obj;
            latinToCyrillic.put((String)letters.get("Latin"), (String)letters.get("Cyrillic"));
            cyrillicToLatin.put((String)letters.get("Cyrillic"), (String)letters.get("Latin"));
        }
    }

    public String latinToCyrillic(String text) throws IOException, ParseException {
        String newWord = "";

        jsonArray = (JSONArray)jsonParser.parse(new FileReader("WordsCyrillic.json"));
        for(Object obj : jsonArray){
            JSONObject letters = (JSONObject)obj;
            if((String)letters.get(text) != null)
                return (String)letters.get(text);
        }

        for (int i = 0; i < text.length(); i++) {
            if (i + 1 < text.length()
                    && latinToCyrillic.get(
                    new String(new char[]{text.toUpperCase().charAt(i),text.toUpperCase().charAt(i + 1)})
            ) != null) {

                newWord += latinToCyrillic.get(new String(new char[]{Character.toUpperCase(text.charAt(i)),
                        Character.toUpperCase(text.charAt(i + 1))}));
                i++;
                continue;
            }

            newWord += latinToCyrillic.get(Character.toString(Character.toUpperCase(text.charAt(i))));
        }

        return isRightCyrillic(text,newWord);
    }

    public String cyrillicToLatin(String text) throws IOException, ParseException {
        String newWord = "";

        jsonArray = (JSONArray)jsonParser.parse(new FileReader("WordsLatin.json"));
        for(Object obj : jsonArray){
            JSONObject letters = (JSONObject)obj;
            if((String)letters.get(text) != null)
                return (String)letters.get(text);
        }

        for (int i = 0; i < text.length(); i++) {
            if (i + 1 < text.length()
                    && cyrillicToLatin.get(
                    new String(new char[]{text.toUpperCase().charAt(i),text.toUpperCase().charAt(i + 1)})
            ) != null) {

                    newWord += cyrillicToLatin.get(new String(new char[]{Character.toUpperCase(text.charAt(i)),
                            Character.toUpperCase(text.charAt(i + 1))}));
                    i++;
                    continue;
            }

            newWord += cyrillicToLatin.get(Character.toString(Character.toUpperCase(text.charAt(i))));
        }

        return isRightLatin(text,newWord);
    }

    public String isRightCyrillic(String latinWord, String cyrillicWord) throws IOException, ParseException {

        JSONObject jsonObject;
        FileWriter fileWriter;
        System.out.println("Слово " + latinWord + " переведенно ли правильно " + cyrillicWord);
        System.out.println("Да/Нет");
        switch (new Scanner(System.in).nextLine().toLowerCase()){
            case "да":
                jsonObject = new JSONObject();
                jsonArray = (JSONArray)jsonParser.parse(new FileReader("WordsCyrillic.json"));
                jsonObject.put(latinWord, cyrillicWord);
                jsonArray.add(jsonObject);
                fileWriter = new FileWriter("WordsCyrillic.json");
                fileWriter.write(jsonArray.toJSONString());
                fileWriter.flush();
                fileWriter.close();
                break;
            case "нет":

                System.out.print("Введите правильный перевод слова " + latinWord + ": ");

                jsonObject = new JSONObject();
                jsonArray = (JSONArray)jsonParser.parse(new FileReader("WordsCyrillic.json"));
                jsonObject.put(latinWord, new Scanner(System.in).nextLine());
                jsonArray.add(jsonObject);

                fileWriter = new FileWriter("WordsCyrillic.json");
                fileWriter.write(jsonArray.toJSONString());
                fileWriter.flush();
                fileWriter.close();

                System.out.println("Слово успешно добавленно в словарь");
                break;

            default: isRightCyrillic(latinWord, cyrillicWord); break;
        }

        return cyrillicWord;

    }

    public String isRightLatin(String cyrillicWord, String latinWord) throws IOException, ParseException {

        JSONObject jsonObject;
        FileWriter fileWriter;
        System.out.println("Слово " + cyrillicWord + " переведенно ли правильно " + latinWord);
        System.out.println("Да/Нет");

        switch (new Scanner(System.in).nextLine().toLowerCase()){
            case "да":
                jsonObject = new JSONObject();
                jsonArray = (JSONArray)jsonParser.parse(new FileReader("WordsLatin.json"));
                jsonObject.put(cyrillicWord, latinWord);
                jsonArray.add(jsonObject);
                fileWriter = new FileWriter("WordsLatin.json");
                fileWriter.write(jsonArray.toJSONString());
                fileWriter.flush();
                fileWriter.close();
                break;
            case "нет":
                System.out.print("Введите правильный перевод слова " + cyrillicWord + ": ");

                jsonObject = new JSONObject();
                jsonArray = (JSONArray)jsonParser.parse(new FileReader("WordsLatin.json"));
                jsonObject.put(cyrillicWord, new Scanner(System.in).nextLine());
                jsonArray.add(jsonObject);

                fileWriter = new FileWriter("WordsLatin.json");
                fileWriter.write(jsonArray.toJSONString());
                fileWriter.flush();
                fileWriter.close();

                System.out.println("Слово успешно добавленно в словарь");
                break;
            default: isRightLatin(cyrillicWord, latinWord); break;
        }

        return latinWord;

    }
}
