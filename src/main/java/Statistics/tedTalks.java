/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Statistics;

import core.Annotation;
import core.annotationList;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *
 * @author Murathan
 */
public class tedTalks {

    static HashMap<String, Map<String, Integer>> allLangType = new HashMap<>();
    static HashMap<String, Map<String, Integer>> allLangSense1 = new HashMap<>();
    static HashMap<String, Map<String, Integer>> allLangSense2 = new HashMap<>();
    static HashMap<String, Map<String, Integer>> allLangSense3 = new HashMap<>();
    static Set<String> typeSet = new TreeSet<>();
    static Set<String> sense1Set = new TreeSet<>();
    static Set<String> sense2Set = new TreeSet<>();
    static Set<String> sense3Set = new TreeSet<>();
    static Map<String, annotationList> langAnnoList = new TreeMap<String, annotationList>();

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
        String typeArray[] = {"all", "Explicit", "Implicit", "altlex"};
        for (String type : typeArray) {
            System.out.println("------------------" + type.toUpperCase());
            annotationList portugese = readRelationsForStatistics.readPDTBRelations("TEDTalks\\Amalia - Completed Annotations\\ann\\section_1",
                    "TEDTalks\\Amalia - Completed Annotations\\raw\\section_1", type);

            annotationList turkish = readRelationsForStatistics.readPDTBRelations("TEDTalks\\Deniz - Completed Annotations\\Ann\\01",
                    "TEDTalks\\Deniz - Completed Annotations\\Raw\\01", type);

            annotationList english = readRelationsForStatistics.readPDTBRelations("TEDTalks\\Sam - Completed Annotations\\Ann",
                    "TEDTalks\\Sam - Completed Annotations\\Raw", type);

            annotationList german = readRelationsForStatistics.readPDTBRelations("TEDTalks\\Yulia_german\\Ann\\section",
                    "TEDTalks\\Yulia_german\\Raw\\section", type);

            annotationList russian = readRelationsForStatistics.readPDTBRelations("TEDTalks\\Yulia_russian\\Ann\\section",
                    "TEDTalks\\Yulia_russian\\Raw\\section", type);

            annotationList polish = readRelationsForStatistics.readPDTBRelations("TEDTalks\\TED Talks – Polish\\Annotated files",
                    "TEDTalks\\TED Talks – Polish\\Raw translated transcripts", type);
            langAnnoList.put("ENGLISH", english);
            langAnnoList.put("TURKISH", turkish);
            langAnnoList.put("PORTUGESE", portugese);
            langAnnoList.put("GERMAN", german);
            langAnnoList.put("RUSSIAN", russian);

            generate("Turkish", turkish);
            generate("Portugese", portugese);
            generate("English", english);
            generate("German", german);
            generate("Russian", russian);
            //      generate("Polish", polish);

            generateSets(typeSet, allLangType);
            generateSets(sense1Set, allLangSense1);
            generateSets(sense2Set, allLangSense2);
            generateSets(sense3Set, allLangSense3);

            printAnnoNumbers();

            if (type.equalsIgnoreCase("all")) {
                System.out.println("Distribution of Relation Types".toUpperCase());
                pprint(typeSet, allLangType);
                System.out.println("");
            }
            System.out.println("Distribution of Level-1 Senses".toUpperCase());
            pprint(sense1Set, allLangSense1);
            System.out.println("");

            System.out.println("Distribution of Level-2 Senses".toUpperCase());
            pprint(sense2Set, allLangSense2);
            System.out.println("");

            System.out.println("Distribution of Level-3 Senses".toUpperCase());
            pprint(sense3Set, allLangSense3);
            System.out.println("------------------" + type.toUpperCase());
            System.out.println();
            System.out.println();
            langAnnoList.clear();

            /**/
        }
    }

    public static void pprint(Set<String> uniqueElements, Map<String, Map<String, Integer>> map) {
        System.out.printf("%-40s", " ");
        for (String language : langAnnoList.keySet()) {
            System.out.printf("%-12s", "!" + language.toUpperCase() + " ");
        }
        System.out.println("");

        for (String element : uniqueElements) {
            System.out.printf("%-40s", element + " ");

            for (String language : map.keySet()) {
                if (map.get(language).containsKey(element)) {
                    System.out.printf("%1s%-12d", "!", map.get(language).get(element));
                } else {
                    System.out.printf("%1s%-12d", "!", 0);
                }
            }
            System.out.println("");

        }

    }

    private static void printAnnoNumbers() {
        System.out.printf("%-40s", " ");
        for (String language : langAnnoList.keySet()) {
            System.out.printf("%-12s", "!" + language.toUpperCase() + " ");

        }
        System.out.println("");
        System.out.printf("%-40s", "# of Annotation (overall)" + " ");

        for (String language : langAnnoList.keySet()) {
            System.out.printf("%-12s", "!" + langAnnoList.get(language).getAnnotationList().size() + " ");
        }
        System.out.println("");
    }

}
