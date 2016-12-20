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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Murathan
 */
public class run {

    static HashMap<String, Map<String, Integer>> allLangType = new HashMap<>();
    static HashMap<String, Map<String, Integer>> allLangSense1 = new HashMap<>();
    static HashMap<String, Map<String, Integer>> allLangSense2 = new HashMap<>();
    static HashMap<String, Map<String, Integer>> allLangSense3 = new HashMap<>();
    static Set<String> typeSet = new HashSet<>();
    static Set<String> sense1Set = new HashSet<>();
    static Set<String> sense2Set = new HashSet<>();
    static Set<String> sense3Set = new HashSet<>();

    public static void generateSets(Set<String> lang, HashMap<String, Map<String, Integer>> allLangType) {
        for (String str : allLangType.keySet()) {
            lang.addAll(allLangType.get(str).keySet());
        }
    }

    public static void generate(String lang, annotationList list) {
        allLangType.put(lang, list.getTypeFreqMap());
        allLangSense1.put(lang, list.getSense1FreqMap());
        allLangSense2.put(lang, list.getSense2FreqMap());
        allLangSense3.put(lang, list.getSense3FreqMap());
    }

    public static void print(Map<String, Map<String, Integer>> map) {
        for (String language : map.keySet()) {
            System.out.println(language.toUpperCase());

            for (String type : map.get(language).keySet()) {
                System.out.printf("%-20s%5d", type + "!", map.get(language).get(type));
                //   System.out.print(type + "!"+  map.get(language).get(type)+"!");
                System.out.println("");
            }
        }
    }

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

        annotationList russian = readRelationsForStatistics.readPDTBRelations("TEDTalks\\Yulia_russian\\Ann\\section",
                "TEDTalks\\Yulia_russian\\Raw\\section");

        annotationList polish = readRelationsForStatistics.readPDTBRelations("TEDTalks\\TED Talks – Polish\\Annotated files",
                "TEDTalks\\TED Talks – Polish\\Raw translated transcripts");

        //      String textString = new Scanner(new File("TEDTalks//Yulia_german//Raw//section//talk_1971_de.txt"), charset.displayName()).useDelimiter("/n/r").next();
        //      System.out.println(textString);
        generate("Turkish", turkish);
        generate("Portugese", portugese);
        generate("English", english);
        generate("German", german);
        generate("Russian", russian);
        generate("Polish", polish);

        generateSets(typeSet, allLangType);
        generateSets(sense1Set, allLangSense1);
        generateSets(sense2Set, allLangSense2);
        generateSets(sense3Set, allLangSense3);

        pprint(typeSet, allLangType);
        System.out.println("");
        pprint(sense1Set, allLangSense1);
        System.out.println("");
        pprint(sense2Set, allLangSense2);
        System.out.println("");
        pprint(sense3Set, allLangSense3);

    }

    public static void pprint(Set<String> uniqueElements, Map<String, Map<String, Integer>> map) {
        System.out.printf("%-40s", " ");
        for (String language : map.keySet()) {
            System.out.printf("%-12s", "!"+language.toUpperCase() + " ");
        }
        System.out.println("");

        for (String element : uniqueElements) {
            System.out.printf("%-40s", element + " ");

            for (String language : map.keySet()) {
                if (map.get(language).containsKey(element)) {
                    System.out.printf("%1s%-12d", "!",map.get(language).get(element));
                } else {
                    System.out.printf("%1s%-12d",  "!",0);
                }
            }
            System.out.println("");

        }

    }

}
