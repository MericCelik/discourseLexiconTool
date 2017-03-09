/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aligned;

import DLVTReader.readerDLVT;
import core.Annotation;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author Murathan
 */
public class RelationMatcher {

    readerDLVT sourceLang;
    readerDLVT targetLang;
    private Charset charset = Charset.forName("UTF-8");

    HashMap<String, Integer> timeStampsSource;
    HashMap<String, Integer> timeStampsTarget;

    public RelationMatcher(String src, String target, String srcTime, String targetTime) throws ParserConfigurationException, SAXException, IOException {
        sourceLang = new readerDLVT(src);
        targetLang = new readerDLVT(target);
        timeStampsSource = this.getTimeStamps(srcTime);
        timeStampsTarget = this.getTimeStamps(targetTime);

        processAnnotations(sourceLang.getAnnotationListBasedOnTextFile("1927"), timeStampsSource);
        //    processAnnotations(targetLang.getAnnotationList(), timeStampsTarget);
    }

    private void processAnnotations(ArrayList<Annotation> annotationList, HashMap<String, Integer> timeStampsSource) {

        int numberFound = 0;
        for (int i = 0; i < 20; i++) {

            String arg1 = annotationList.get(i).printArg1();
            String arg2 = annotationList.get(i).printArg2();

            boolean found = false;

            for (String str : timeStampsSource.keySet()) {
                if (str.contains(arg1)) {
                    found = true;
                    numberFound++;
                }
            }
            if (found && numberFound > 1) {

            }
            if (!found) {
                System.out.println(arg1);
                List<String> keys = new ArrayList(timeStampsSource.keySet());
                for (int index = 0; index < timeStampsSource.keySet().size() - 1; index++) {
                    String combined = keys.get(index) + " " + keys.get(index + 1);

                    if (combined.contains(arg1)) {
                        found = true;
                        System.out.println("found! arg1: " + arg1);
                        System.out.println(timeStampsSource.get(keys.get(i)));
                        numberFound++;
                    }
                }
            }
        }
        System.out.println(annotationList.size() + " " + numberFound);
    }

    private HashMap<String, Integer> getTimeStamps(String dir) throws IOException {
        HashMap<String, Integer> timeStamps = new HashMap<>();

        List<String> raw = Files.readAllLines(new File(dir).toPath(), charset);
        for (String str : raw) {
            String[] split = str.split("\t");
            timeStamps.put(split[1], Integer.parseInt(split[0]));
        }
        return timeStamps;
    }

    public void printRelations(String textFile) {
        for (Annotation anno : sourceLang.getAnnotationList()) {
            if (anno.getTextFile().contains(textFile)) {
                System.out.println(anno.toString() + " " + anno.getTextFile());
            }
        }
        System.out.println("-----------");
        for (Annotation anno : targetLang.getAnnotationList()) {
            if (anno.getTextFile().contains(textFile)) {
                System.out.println(anno.toString() + " " + anno.getTextFile());
            }
        }
    }

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        RelationMatcher r = new RelationMatcher("Converted Files\\aligned_english.dlvt", "Converted Files\\aligned_turkish.dlvt", "aligner\\1927_eng_timestamps.txt", "aligner\\1927_tur_timestamps.txt");

    }

}
