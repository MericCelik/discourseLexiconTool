/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Statistics;

import core.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *
 * @author Murathan
 */
public class tedTalks {

    private static Charset charset = Charset.forName("UTF-8");

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

    public static void getMult(annotationList list) {

        HashSet<String> set = new HashSet<String>();
        for (Annotation anno : list.getAnnotationList()) {

            int a = sentenceCount(anno.getArgument1().toString());
            int b = sentenceCount(anno.getArgument2().toString());

            if (a > 1 || b > 1) {
                set.add(anno.toString());
            }
        }
        for (String str : set) {
            System.out.println(str);
        }

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

    }

    public static int sentenceCount(String str) {

        int noOfSentence = 0;
        int count = str.length() - str.replace(".", "").length();
        int count2 = str.length() - str.replace("?", "").length();
        int count3 = str.length() - str.replace("!", "").length();
        noOfSentence = noOfSentence + count + count2 + count3;
        return noOfSentence;
    }

    public static void count(String textDir) throws FileNotFoundException {

        int sum = 0;
        int file = 0;
        int max = 0;
        int min = 100000000;
        String minFile = "";
        String maxFile = "";

        int noOfSentence = 0;

        File textFolder = new File(textDir);
        File[] arrayOfTextFiles = textFolder.listFiles();
        ArrayList<File> listOfTextFiles = new ArrayList<>(Arrays.asList(arrayOfTextFiles));
        String textString = "";
        for (File textFile : listOfTextFiles) {
            if (textFile.getName().contains("DS_S")) {
                continue;
            }
            textString = new Scanner(textFile, charset.displayName()).useDelimiter("/n/r").next();
            String textStringCopy = textString;
            StringTokenizer token = new StringTokenizer(textString, " ");
            int count = textStringCopy.length() - textStringCopy.replace(".", "").length();
            int count2 = textStringCopy.length() - textStringCopy.replace("?", "").length();
            int count3 = textStringCopy.length() - textStringCopy.replace("!", "").length();
            noOfSentence = noOfSentence + count + count2 + count3;

            if (token.countTokens() > max) {
                max = token.countTokens();
                maxFile = textFile.getName();
            }
            if (token.countTokens() < min) {
                min = token.countTokens();
                minFile = textFile.getName();
            }
            sum = sum + token.countTokens();
            file++;
            System.out.println(textFile.getName() + " " + token.countTokens());
        }
        System.out.println("sum " + sum);
        System.out.println("file " + file);

        System.out.println("average " + (double) sum / file);

        System.out.println("sentence " + (double) noOfSentence);
        System.out.println("average sentence " + (double) noOfSentence / file);

        System.out.println("longest " + maxFile + " " + max);
        System.out.println("shortest " + minFile + " " + min);
        System.out.println();
        System.out.println();

    }

    public static void main(String[] args) throws IOException {

        System.out.println("");

//        count("TEDTalks" + File.separator + "Amalia - Completed Annotations" + File.separator + "raw" + File.separator + "section_1");
//        count("TEDTalks" + File.separator + "Deniz - Completed Annotations" + File.separator + "Raw" + File.separator + "01");
//        count("TEDTalks" + File.separator + "Sam - Completed Annotations" + File.separator + "Raw");
//        count("TEDTalks" + File.separator + "Yulia_german" + File.separator + "Raw" + File.separator + "section");
//        count("TEDTalks" + File.separator + "Yulia_russian" + File.separator + "Raw" + File.separator + "section");
//        count("TEDTalks" + File.separator + "TED Talks – Polish" + File.separator + "Raw translated transcripts");
        String typeArray[] = {"all"};//, "Explicit", "Implicit", "altlex"};
        for (String type : typeArray) {
            readRelationsForStatistics.replaceString = "";

            System.out.println("------------------" + type.toUpperCase());

//            annotationList english = readRelationsForStatistics.readPDTBRelations("TEDTalks" + File.separator + "Sam - Completed Annotations" + File.separator + "Ann",
//                    "TEDTalks" + File.separator + "Sam - Completed Annotations" + File.separator + "Raw", type);
//            getConList(english);
//
//            annotationList turkish = readRelationsForStatistics.readPDTBRelations("TEDTalks" + File.separator + "Deniz - Completed Annotations" + File.separator + "Ann" + File.separator + "01",
//                    "TEDTalks" + File.separator + "Deniz - Completed Annotations" + File.separator + "Raw" + File.separator + "01", type);
//            getConList(turkish);

            annotationList portugese = readRelationsForStatistics.readPDTBRelations("TEDTalks" + File.separator + "Amalia - Completed Annotations" + File.separator + "ann" + File.separator + "section_1",
                    "TEDTalks" + File.separator + "Amalia - Completed Annotations" + File.separator + "raw" + File.separator + "section_1", type);
            getConList(portugese);
            
            //----------
         /*   readRelationsForStatistics.replaceString = "\n\r";

            annotationList german = readRelationsForStatistics.readPDTBRelations("TEDTalks" + File.separator + "Yulia_german" + File.separator + "Ann" + File.separator + "section",
                    "TEDTalks" + File.separator + "Yulia_german" + File.separator + "Raw" + File.separator + "section", type);

            annotationList russian = readRelationsForStatistics.readPDTBRelations("TEDTalks" + File.separator + "Yulia_russian" + File.separator + "Ann" + File.separator + "section",
                    "TEDTalks" + File.separator + "Yulia_russian" + File.separator + "Raw" + File.separator + "section", type);

            annotationList polish = readRelationsForStatistics.readPDTBRelations("TEDTalks" + File.separator + "TED Talks – Polish" + File.separator + "Annotated files",
                    "TEDTalks" + File.separator + "TED Talks – Polish" + File.separator + "Raw translated transcripts", type);

            

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
             */
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

    private static void getConList(annotationList list) {

        HashMap<String, HashSet<String>> set = new HashMap<>();

        for (Annotation anno : list.getAnnotationList()) {

            if (!set.containsKey(anno.getConnective().toString().toLowerCase() + " >" + anno.getType())) {
                HashSet<String> s = new HashSet<>();
                s.add(anno.getFullSense());
                set.put(anno.getConnective().toString().toLowerCase() + " >" + anno.getType(), s);
            } else {
                HashSet<String> s = set.get(anno.getConnective().toString().toLowerCase() + " >" + anno.getType());
                s.add(anno.getFullSense());
                set.put(anno.getConnective().toString().toLowerCase() + " >" + anno.getType(), s);

            }
        }
        for (String str : set.keySet()) {
            System.out.println(str + " > " + set.get(str).toString());
        }
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

    }

}
