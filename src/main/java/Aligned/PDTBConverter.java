/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aligned;

import Converter.*;
import core.Annotation;
import core.Span;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Murathan
 */
public class PDTBConverter extends abstractConverter {

    private Charset charset = Charset.forName("UTF-8");
    private String outputDir = "";

    private ArrayList<String> connectiveList = new ArrayList<>();
    private HashMap<String, ArrayList<Annotation>> connectiveAnnotationMap = new HashMap<>();

    public PDTBConverter(String annotationDir, String textDir, String outputFileName) throws IOException {
        File theDir = new File("Converted Files");
        if (!theDir.exists()) {
            try {
                theDir.mkdir();
            } catch (SecurityException se) {
                //handle it
            }
        }
        this.outputDir = theDir.getAbsolutePath() + "\\" + outputFileName;
        connectiveAnnotationMap = readPDTBRelations(annotationDir, textDir);
        ConverterUtils.writeToFile(outputDir, connectiveAnnotationMap, "PDTB");
    }

    private HashMap<String, ArrayList<Annotation>> readPDTBRelations(String annotationDir, String textDir) throws IOException {

        if (checkDirectory(annotationDir, textDir)) {

            File annotationFolder = new File(annotationDir);
            File[] arrayOfAnnotationFiles = annotationFolder.listFiles();
            ArrayList<File> listOfAnnotationFiles = new ArrayList<>(Arrays.asList(arrayOfAnnotationFiles));

            File textFolder = new File(textDir);
            File[] arrayOfTextFiles = textFolder.listFiles();
            ArrayList<File> listOfTextFiles = new ArrayList<>(Arrays.asList(arrayOfTextFiles));

            for (File annotationFile : listOfAnnotationFiles) {
                System.out.println("PROCESSING ANNOTATION FILE: " + annotationFile.getName());
                File textFile = null;
                for (File textTmp : listOfTextFiles) {
                    if (textTmp.getName().equals(annotationFile.getName())) {
                        textFile = textTmp;
                    }
                }

                List<String> annotationRaw = Files.readAllLines(annotationFile.toPath(), charset);
                String textString = new Scanner(textFile, "UTF-8").useDelimiter("/n/r").next();
                textString = textString.replaceAll("\n", "");
                for (String annotation : annotationRaw) {

                    if (!annotation.contains("Rejected")) {
                        //  System.out.println(annotation);
                        String[] annotationTokens = annotation.split("\\|");

                        if (!annotationTokens[0].equalsIgnoreCase("implicit") && annotationTokens[1].equals("")) {
                            continue;
                        }
                        ArrayList<Span> conSpans = new ArrayList<>(); // initialize conSpan
                        System.out.println(annotationTokens[0]);
                        String connectiveString = "";

                        ArrayList<Span> arg1Spans = extractArgument(annotationTokens[14], textString, "Arg1");
                        ArrayList<Span> arg2Spans = extractArgument(annotationTokens[20], textString, "Arg2");
                        ArrayList<Span> supp1Spans = new ArrayList<>();// extractArgument(annotationTokens[32], textString, "Supp1");
                        ArrayList<Span> supp2Spans = new ArrayList<>();// extractArgument(annotationTokens[31], textString, "Supp2");
                        ArrayList<Span> modSpans = new ArrayList<>();// extractArgument(annotationTokens[32], textString, "Mod");

                        if (!annotationTokens[0].equalsIgnoreCase("implicit")) { //Explicit or Altlex
                            conSpans = extractArgument(annotationTokens[1], textString, "Conn");
                            for (Span s : conSpans) {
                                connectiveString = connectiveString + "_" + s.getText().toLowerCase();
                            }
                            connectiveString = connectiveString.substring(1);
                        } else if (annotationTokens[0].equalsIgnoreCase("entrel")) {
                            connectiveString = "_e_";
                            conSpans.add(new Span(connectiveString, arg2Spans.get(0).getBeg() - 1, "Conn"));
                        } else if (annotationTokens[0].equalsIgnoreCase("norel")) {
                            connectiveString = "_no_";
                            conSpans.add(new Span(connectiveString, arg2Spans.get(0).getBeg() - 1, "Conn"));

                        } else {
                            connectiveString = annotationTokens[7];
                            conSpans.add(new Span(connectiveString, arg2Spans.get(0).getBeg() - 1, "Conn"));
                        }
                        connectiveList.add(connectiveString);
                        String senseArray[] = annotationTokens[8].split("\\.");
                        String type = annotationTokens[0];
                        String sense = "";
                        for (String str : senseArray) {
                            sense = sense + ": " + str;
                        }
                        sense = sense.replaceAll("-", "_");
                        Annotation currentAnnotation = new Annotation(conSpans, arg1Spans, arg2Spans, modSpans, supp1Spans, supp2Spans, sense, "", type, "");
                        currentAnnotation.setTextFile(annotationFile.getName());

                        //  connectiveString = connectiveString + "(" + type + ")";
                        if (connectiveAnnotationMap.keySet().contains(connectiveString)) {
                            ArrayList<Annotation> tempList = connectiveAnnotationMap.get(connectiveString);
                            tempList.add(currentAnnotation);
                            connectiveAnnotationMap.put(connectiveString, tempList);
                        } else {
                            ArrayList<Annotation> tempList = new ArrayList<>();
                            tempList.add(currentAnnotation);
                            connectiveAnnotationMap.put(connectiveString, tempList);
                        }
                        // MULTIPLE SENSE
                        if (!annotationTokens[9].equals("")) {
                            String sense2Array[] = annotationTokens[9].split("\\.");
                            sense = "";
                            for (String str : sense2Array) {
                                sense = sense + ": " + str;
                            }
                            currentAnnotation = new Annotation(conSpans, arg1Spans, arg2Spans, modSpans, supp1Spans, supp2Spans, sense, "", type, "");
                            currentAnnotation.setTextFile(annotationFile.getName());
                            //      connectiveString = connectiveString + "(" + type + ")";
                            if (connectiveAnnotationMap.keySet().contains(connectiveString)) {
                                ArrayList<Annotation> tempList = connectiveAnnotationMap.get(connectiveString);
                                tempList.add(currentAnnotation);
                                connectiveAnnotationMap.put(connectiveString, tempList);
                            } else {
                                ArrayList<Annotation> tempList = new ArrayList<>();
                                tempList.add(currentAnnotation);
                                connectiveAnnotationMap.put(connectiveString, tempList);
                            }
                        }
                    }
                }

            }
        }
        return connectiveAnnotationMap;
    }

    public ArrayList<Span> extractArgument(String rawIndex, String textString, String belongsTo) {
        ArrayList<Span> argSpans = new ArrayList<>();
        if (!rawIndex.equals("")) {
            String multipleSelection[] = rawIndex.split(";");
            for (String selection : multipleSelection) {
                String indexArray[] = selection.split("\\.\\.");
                int begIndex = Integer.parseInt(indexArray[0]);
                int endIndex = Integer.parseInt(indexArray[1]);

                Span span = new Span(textString.substring(begIndex, endIndex), begIndex, belongsTo);
                argSpans.add(span);
            }
        }
        return argSpans;
    }

    public static void main(String[] args) throws IOException {

        PDTBConverter pdtb = new PDTBConverter("annotations\\PDTB\\ENG\\Ann", "annotations\\PDTB\\ENG\\text", "aligned_english");

    }

    private boolean checkDirectory(String annotationDir, String textDir) {
        boolean identical = true;
        File annotationFolder = new File(annotationDir);
        String[] arrayOfAnnotationFiles = annotationFolder.list();
        ArrayList<String> listOfAnnotationFiles = new ArrayList<>(Arrays.asList(arrayOfAnnotationFiles));

        File textFolder = new File(textDir);
        String[] arrayOfTextFiles = textFolder.list();
        ArrayList<String> listOfTextFiles = new ArrayList<>(Arrays.asList(arrayOfTextFiles));

        for (String fileName : listOfAnnotationFiles) {
            if (!listOfTextFiles.contains(fileName)) {
                System.out.println("There is not any text file for the annotation file: " + fileName);
                System.out.println("Names of the Annotation and Text file must be identical!");

                identical = false;
            }
        }
        if (!identical) {
            System.out.println("EXIT");
        }
        return identical;
    }

    public String getOutputDir() {
        return ConverterUtils.outputFile;
    }
}
