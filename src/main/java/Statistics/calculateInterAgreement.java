/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Statistics;

import core.Annotation;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author Administrator
 */
public class calculateInterAgreement {

    String annotator1Dir;
    String annotator2Dir;
    HashMap<Integer, Annotation> anno1Map = new HashMap<>();
    HashMap<Integer, Annotation> anno2Map = new HashMap<>();
    static HashMap<Integer, Annotation> uniqueMap = new HashMap<>();
    static HashMap<String, Integer> confusionMatrix = new HashMap<>();

    static int[] overallAgreed = {0, 0, 0};
    static int overallSize = 0;
    public String filterString = "";

    public static void printConfusionMatrix() {
        confusionMatrix.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(50)
                .forEach(System.out::println);
    }

    public calculateInterAgreement(String dir1, String dir2, String type) throws IOException, SAXException, ParserConfigurationException, ParserConfigurationException {
        File folderAnno1 = new File(dir1);
        File folderAnno2 = new File(dir2);

        File[] Anno1Files = folderAnno1.listFiles();
        File[] Anno2Files = folderAnno2.listFiles();

        if (Anno1Files.length != Anno2Files.length) {
            System.out.println("I'm sorry, Dave. I'm afraid I can't do that".toUpperCase());
        }
        for (int i = 0; i < Anno1Files.length; i++) {
            if (Anno1Files[i].getPath().contains(filterString)) {
                if (Anno1Files[i].getName().substring(0, 8).equals(Anno2Files[i].getName().substring(0, 8))) {
                    System.out.println("");
                    System.out.println(Anno1Files[i].getPath().toUpperCase());
                    anno1Map = readRelationsForStatistics.readDATTRelations(Anno1Files[i].getPath(), type);
                    anno2Map = readRelationsForStatistics.readDATTRelations(Anno2Files[i].getPath(), type);
                    calculateInterAgreement(anno1Map, anno2Map, type);
                }
            }
        }
    }

    private void calculateInterAgreement(HashMap<Integer, Annotation> anno1Map, HashMap<Integer, Annotation> anno2Map, String type) {

        System.out.println("Annotations of anno1 : " + anno1Map.size());
        System.out.println("Annotations of anno2 : " + anno2Map.size());

        uniqueMap.putAll(anno1Map);
        uniqueMap.putAll(anno2Map);

        int[] agreed = {0, 0, 0};
        int commonAnno = 0;
        for (Integer key : anno1Map.keySet()) {
            //      if (anno2Map.containsKey(key) && anno2Map.get(key).getType().contains(type) && anno1Map.get(key).getType().contains(type) && 
            // (!anno1Map.get(key).getFullSense().contains("?") || !anno2Map.get(key).getFullSense().contains("?"))) {
            if (anno2Map.containsKey(key) && !anno1Map.get(key).getType().equals(anno2Map.get(key))) {

                if (anno1Map.get(key).getFullSense().contains("Multiple") || anno2Map.get(key).getFullSense().contains("Multiple")) {
                    handleMultiple(anno1Map.get(key), anno2Map.get(key), agreed);
                    commonAnno++;
                    commonAnno++;
                    continue;
                }
                commonAnno++;
                compareAnnotationSenseBased(anno1Map.get(key), anno2Map.get(key), agreed);
                if (agreed[2] > agreed[1]) {
                    System.out.println("");
                }
                //          System.out.println("!COMMON: " + anno1Map.get(key).toString() + " -> " + anno1Map.get(key).getFullSense());
            } else {
                //       System.out.println("NOT COMMON: " + anno1Map.get(key).toString() + " -> " + anno1Map.get(key).getFullSense());
            }
        }
        overallSize = overallSize + commonAnno;
        System.out.println(commonAnno);
        System.out.println(agreed[0] + " " + agreed[1] + " " + agreed[2]);
    }

    private void compareAnnotationSenseBased(Annotation a1, Annotation a2, int[] agreed) {

        boolean sense1 = a1.getSense1().equals(a2.getSense1());
        boolean sense2 = a1.getSense2().equals(a2.getSense2());
        boolean sense3 = a1.getSense3().equals(a2.getSense3());

        if (sense1) {
            agreed[0] = agreed[0] + 1;
            overallAgreed[0] = overallAgreed[0] + 1;
        }
        if (sense1 && sense2) {
            agreed[1] = agreed[1] + 1;
            overallAgreed[1] = overallAgreed[1] + 1;
        }
        if (sense1 && sense2 && sense3) {
            agreed[2] = agreed[2] + 1;
            overallAgreed[2] = overallAgreed[2] + 1;
        }
        if (!a1.getFullSense().equals(a2.getFullSense())) {
//            System.out.println(a1.toFullString());
//            System.out.println(a2.toFullString());

            String s = a1.getFullSense() + "_" + a2.getFullSense();
            if (!confusionMatrix.containsKey(s)) {
                confusionMatrix.put(s, 1);
            } else {
                int oValue = confusionMatrix.get(s);
                confusionMatrix.put(s, oValue + 1);
            }
        }
    }

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException, ParserConfigurationException {

        //              calculateInterAgreement calculator = new calculateInterAgreement("Agreement\\Savas", "Agreement\\Agreed", "IMPLICIT"); // 85,  123
//        calculateInterAgreement calculator2 = new calculateInterAgreement("Agreement\\implicit\\tuğçe", "Agreement\\implicit\\murathan", "IMPLICIT"); // 85,  123
//        calculateInterAgreement calculator3 = new calculateInterAgreement("Agreement\\implicit\\savas", "Agreement\\implicit\\serkan", "IMPLICIT"); // 85,  123
        //        calculateInterAgreement calculator2 = new calculateInterAgreement("Agreement\\implicit\\tuğçe", "Agreement\\implicit\\murathan", ""); // 85,  123
//         calculateInterAgreement calculator3 = new calculateInterAgreement("Agreement\\implicit\\savas", "Agreement\\implicit\\serkan", "");
//        calculateInterAgreement calculator = new calculateInterAgreement("Agreement\\explicit\\tuğçe", "Agreement\\explicit\\murathan", "EXPLICIT");
        //     calculateInterAgreement calculator2 = new calculateInterAgreement("Agreement\\explicit\\savas", "Agreement\\explicit\\serkan", "EXPLICIT");
//        calculateInterAgreement calculator2 = new calculateInterAgreement("Agreement\\altlex\\Deniz", "Agreement\\altlex\\Fikret", "ALTLEX");
//        System.out.println(" - ");
//        for (int i : overallAgreed) {
//            System.out.print(i + " -->");
//            System.out.println((double) i / overallSize);
//        }
//        System.out.println("common: " + overallSize);
//        System.out.println("unique: " + uniqueMap.size());
//        System.out.println("common/unique: " + ((double) overallSize / uniqueMap.size()));
//
//        printConfusionMatrix();
        System.out.println("ALTLEX");

        HashMap<Integer, Annotation> altlex = readRelationsForStatistics.readDATTRelations("Agreement/altlex.xml", "ALTLEX");
        HashMap<String, Integer> altlexSenseMap = readRelationsForStatistics.senseAnnotationMap;

        readRelationsForStatistics.readDATTRelations("Agreement/imp_bap.xml", "IMPLICIT");
        HashMap<String, Integer> expSenseMap = readRelationsForStatistics.senseAnnotationMap;

        readRelationsForStatistics.readDATTRelations("Agreement/explicit.xml", "EXPLICIT");
        HashMap<String, Integer> impSenseMap = readRelationsForStatistics.senseAnnotationMap;
        TreeSet<String> sense = new TreeSet<>();
        sense.addAll(expSenseMap.keySet());
        sense.addAll(impSenseMap.keySet());
        sense.addAll(altlexSenseMap.keySet());

        for (String str : sense) {
            System.out.print(str + "&");
            if (impSenseMap.containsKey(str)) {
                System.out.print(impSenseMap.get(str) + "&");
            } else {
                System.out.print("0" + "&");
            }
            if (expSenseMap.containsKey(str)) {
                System.out.print(expSenseMap.get(str) + "&");
            } else {
                System.out.print("0" + "&");
            }
            
            if (altlexSenseMap.containsKey(str)) {
                System.out.print(altlexSenseMap.get(str));
            } else {
                System.out.print("0");
            }
            System.out.print("\\\\");

            System.out.println();

        }
//        HashMap<String, Integer> altlexCon = new HashMap<>();
//
//        
//        for (Integer i : altlex.keySet()) //     if(altlex.get(i).getConnective().toString().contains("biyo") || altlex.get(i).getConnective().toString().contains("önemli") ||  altlex.get(i).getConnective().toString().contains("getirdi"))
//        {
//            //System.out.println(altlex.get(i).getConnective());
//            if (altlexCon.containsKey(altlex.get(i).getConnective().toString().toLowerCase())) {
//                int tmp = altlexCon.get(altlex.get(i).getConnective().toString().toLowerCase());
//                altlexCon.put(altlex.get(i).getConnective().toString().toLowerCase(), tmp + 1);
//            } else {
//                altlexCon.put(altlex.get(i).getConnective().toString().toLowerCase(), 1);
//            }
//        }
//        for (String s : altlexCon.keySet()) //     if(altlex.get(i).getConnective().toString().contains("biyo") || altlex.get(i).getConnective().toString().contains("önemli") ||  altlex.get(i).getConnective().toString().contains("getirdi"))
//        {
//            System.out.println(s + ": " + altlexCon.get(s));
//        }

    }

    private void handleMultiple(Annotation a1, Annotation a2, int[] agreed) {

        if (a1.getFullSense().contains("Multiple") && !a2.getFullSense().contains("Multiple")) {
            String[] multSense1 = a1.getNote().split("#");
            Annotation tmp = new Annotation(null, null, null, null, null, null, multSense1[0], "", a1.getType(), "");
            Annotation tmp2 = new Annotation(null, null, null, null, null, null, multSense1[1], "", a1.getType(), "");
            compareAnnotationSenseBased(tmp, a2, agreed);
            compareAnnotationSenseBased(tmp2, a2, agreed);
        } else if (!a1.getFullSense().contains("Multiple") && a2.getFullSense().contains("Multiple")) {
            String[] multSense2 = a2.getNote().split("#");
            Annotation tmp = new Annotation(null, null, null, null, null, null, multSense2[0], "", a2.getType(), "");
            Annotation tmp2 = new Annotation(null, null, null, null, null, null, multSense2[1], "", a2.getType(), "");
            compareAnnotationSenseBased(tmp, a1, agreed);
            compareAnnotationSenseBased(tmp2, a1, agreed);
        } else if (a1.getFullSense().contains("Multiple") && a2.getFullSense().contains("Multiple")) {
            String[] multSense1 = a1.getNote().split("#");
            String[] multSense2 = a2.getNote().split("#");
            Arrays.sort(multSense1);
            Arrays.sort(multSense2);

            Annotation tmp1_1 = new Annotation(null, null, null, null, null, null, multSense1[0], "", a1.getType(), "");
            Annotation tmp1_2 = new Annotation(null, null, null, null, null, null, multSense1[1], "", a1.getType(), "");

            Annotation tmp2_1 = new Annotation(null, null, null, null, null, null, multSense2[0], "", a2.getType(), "");
            Annotation tmp2_2 = new Annotation(null, null, null, null, null, null, multSense2[1], "", a2.getType(), "");

            compareAnnotationSenseBased(tmp1_1, tmp2_1, agreed);
            compareAnnotationSenseBased(tmp1_2, tmp2_2, agreed);
        }

    }

}
