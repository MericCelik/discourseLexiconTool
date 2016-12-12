/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agreement;

import Converter.DATTConverter;
import core.Annotation;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
    static int[] overallAgreed = {0, 0, 0};
    static int overallSize = 0;

    public calculateInterAgreement(String dir1, String dir2) throws IOException, SAXException, ParserConfigurationException, ParserConfigurationException {
        File folderAnno1 = new File(dir1);
        File folderAnno2 = new File(dir2);

        File[] Anno1Files = folderAnno1.listFiles();
        File[] Anno2Files = folderAnno2.listFiles();

        if (Anno1Files.length != Anno2Files.length) {
            System.out.println("I'm sorry, Dave. I'm afraid I can't do that".toUpperCase());
        }
        for (int i = 0; i < Anno1Files.length; i++) {
            System.out.println("");
            System.out.println(Anno1Files[i].getPath().toUpperCase());
            anno1Map = readRelationsForInterAgreement.readDATTRelations(Anno1Files[i].getPath());
            anno2Map = readRelationsForInterAgreement.readDATTRelations(Anno2Files[i].getPath());
            calculateInterAgreement(anno1Map, anno2Map);
        }
    }

    private void calculateInterAgreement(HashMap<Integer, Annotation> anno1Map, HashMap<Integer, Annotation> anno2Map) {

        System.out.println("Annotations of anno1 : " + anno1Map.keySet().size());
        System.out.println("Annotations of anno2 : " + anno2Map.keySet().size());

        int smaller = (anno1Map.keySet().size() < anno2Map.keySet().size()) ? anno1Map.keySet().size() : anno2Map.keySet().size();
        overallSize = overallSize + smaller;
        int[] agreed = {0, 0, 0};
        int commonAnno = 0;
        for (Integer key : anno1Map.keySet()) {
            if (anno2Map.containsKey(key)) {
                System.out.println("COMMON: " + anno1Map.get(key).toString() + " -> " + anno1Map.get(key).getFullSense());

                commonAnno++;
                compareAnnotation(anno1Map.get(key), anno2Map.get(key), agreed);
            } else {
            }
        }
        System.out.println(commonAnno);
    }

    private void compareAnnotation(Annotation a1, Annotation a2, int[] agreed) {

        if (a1.getSense1().equals(a2.getSense1())) {
            agreed[0] = agreed[0] + 1;
            overallAgreed[0] = overallAgreed[0] + 1;
        }
        if (a1.getSense2().equals(a2.getSense2())) {
            agreed[1] = agreed[1] + 1;
            overallAgreed[1] = overallAgreed[1] + 1;
        }
        if (a1.getSense3().equals(a2.getSense3())) {
            agreed[2] = agreed[2] + 1;
            overallAgreed[2] = overallAgreed[2] + 1;
        }

    }

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException, ParserConfigurationException {
        calculateInterAgreement calculator = new calculateInterAgreement("Agreement\\Savas", "Agreement\\Agreed");
        System.out.println(" - ");
        for (int i : overallAgreed) {
            System.out.println((double) i / overallSize);
        }

    }

}
