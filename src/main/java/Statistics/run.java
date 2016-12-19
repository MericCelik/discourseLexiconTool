/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Statistics;

import core.annotationList;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;

/**
 *
 * @author Murathan
 */
public class run {

    static HashMap<String, HashMap<String, Integer>> allLangType = new HashMap<>();
    static HashMap<String, HashMap<String, Integer>> allLangSense1 = new HashMap<>();
    static HashMap<String, HashMap<String, Integer>> allLangSense2 = new HashMap<>();
    static HashMap<String, HashMap<String, Integer>> allLangSense3 = new HashMap<>();

    public static void main(String[] args) throws IOException {

        System.out.println("");
        readRelationsForStatistics.replaceString = "\n\r";
        annotationList portugese = readRelationsForStatistics.readPDTBRelations("TEDTalks\\Amalia - Completed Annotations\\ann\\section_1",
                "TEDTalks\\Amalia - Completed Annotations\\raw\\section_1");

        annotationList turkish = readRelationsForStatistics.readPDTBRelations("TEDTalks\\Deniz - Completed Annotations\\Ann\\01",
                "TEDTalks\\Deniz - Completed Annotations\\Raw\\01");

        annotationList english = readRelationsForStatistics.readPDTBRelations("TEDTalks\\Sam - Completed Annotations\\Ann",
                "TEDTalks\\Sam - Completed Annotations\\Raw");

        annotationList german = readRelationsForStatistics.readPDTBRelations("TEDTalks\\Yulia_german\\Ann\\section",
                "TEDTalks\\Yulia_german\\Raw\\section");

        Charset charset = Charset.forName("UTF-8");

        //      String textString = new Scanner(new File("TEDTalks//Yulia_german//Raw//section//talk_1971_de.txt"), charset.displayName()).useDelimiter("/n/r").next();
        //      System.out.println(textString);
        generate("Turkish", turkish);
        generate("English", english);
        generate("German", german);

        print(allLangType);
        System.out.println("");
   //     print(allLangSense1);
        System.out.println("");
  //      print(allLangSense2);
        System.out.println("");
   //     print(allLangSense3);

    }

    public static void generate(String lang, annotationList list) {
        allLangType.put(lang, list.getTypeFreqMap());
        allLangSense1.put(lang, list.getSense1FreqMap());
        allLangSense2.put(lang, list.getSense2FreqMap());
        allLangSense3.put(lang, list.getSense3FreqMap());
    }

    public static void print(HashMap<String, HashMap<String, Integer>> map) {
        for (String str : map.keySet()) {
            System.out.println(str.toUpperCase());
            for (String type : map.get(str).keySet()) {
                System.out.printf("%-20s%5d", type + "!", map.get(str).get(type));
                System.out.println("");
            }
        }
    }

}
