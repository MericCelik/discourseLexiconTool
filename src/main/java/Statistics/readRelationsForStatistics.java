/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Statistics;

import Converter.ConverterUtils;
import core.Annotation;
import core.Span;
import core.annotationList;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Administrator
 */
public class readRelationsForStatistics {

    private static Charset charset = Charset.forName("UTF-8");
    public static String replaceString;

    public static HashMap<Integer, Annotation> readDATTRelations(String dir, String inputType) throws IOException, org.xml.sax.SAXException, ParserConfigurationException {

        HashMap<Integer, Annotation> connectiveAnnotationMap = new HashMap<>();
        File dattAnnotationFolder = new File(dir);
        String dattAnnotationFile = "";

        if (dattAnnotationFolder.isDirectory()) {
            File[] arrayOfAnnotationFiles = dattAnnotationFolder.listFiles();
            dattAnnotationFile = arrayOfAnnotationFiles[0].getAbsolutePath();
        } else {
            dattAnnotationFile = dir;
        }
        File fXmlFile = new File(dattAnnotationFile);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);

        doc.getDocumentElement()
                .normalize();

        NodeList nList = doc.getElementsByTagName("Relation");
        for (int temp = 0; temp < nList.getLength(); temp++) {

            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element currentElement = (Element) nNode;

                // reading connective and arguments
                NodeList conn = currentElement.getElementsByTagName("Conn");
                NodeList arg1 = currentElement.getElementsByTagName("Arg1");
                NodeList arg2 = currentElement.getElementsByTagName("Arg2");
                NodeList mod = currentElement.getElementsByTagName("Mod");
                NodeList sup1 = currentElement.getElementsByTagName("Supp1");
                NodeList sup2 = currentElement.getElementsByTagName("Supp2");

                ArrayList<Span> connSpans = ConverterUtils.getContext(conn, "conn");
                ArrayList<Span> arg1Spans = ConverterUtils.getContext(arg1, "arg1");
                ArrayList<Span> arg2Spans = ConverterUtils.getContext(arg2, "arg2");
                ArrayList<Span> modSpans = ConverterUtils.getContext(mod, "mod");
                ArrayList<Span> sup1Spans = ConverterUtils.getContext(sup1, "supp1");
                ArrayList<Span> sup2Spans = ConverterUtils.getContext(sup2, "supp2");

                // reading connective and arguments
                // reading note-sense etc.
                String note = currentElement.getAttribute("note");
                String senses = currentElement.getAttribute("sense");
                String type = currentElement.getAttribute("type");
                String genre = currentElement.getAttribute("genre");
                if (!type.contains(inputType)) {
                    continue;
                }
                // check if those fields are empty
                if (note.equals("")) {
                    note = "nothing";
                }
                if (senses.equals("")) {
                    senses = "nothing";
                }
                if (type.equals("")) {
                    type = "nothing";
                }
                if (genre.equals("")) {
                    genre = "nothing";
                }
                // reading note-sense etc.

                Annotation currentAnnotation = new Annotation(connSpans, arg1Spans, arg2Spans, modSpans, sup1Spans, sup2Spans, senses, note, type, genre);
                int connBeg = connSpans.get(0).getBeg();

                if (connectiveAnnotationMap.containsKey(connBeg)) {
                    System.out.println("ALERT!!!! CONNECTIVES WITH SAME INDEX!!");
                } else {
                    connectiveAnnotationMap.put(connBeg, currentAnnotation);
                }
            }
        }

        System.out.println("DATT relations has been read from the file: " + dir);

        return connectiveAnnotationMap;
    }

    public static annotationList readPDTBRelations(String annotationDir, String textDir) throws IOException {

        annotationList pdtbAnnotationList = new annotationList();

        if (checkDirectory(annotationDir, textDir)) {

            File annotationFolder = new File(annotationDir);
            File[] arrayOfAnnotationFiles = annotationFolder.listFiles();
            ArrayList<File> listOfAnnotationFiles = new ArrayList<>(Arrays.asList(arrayOfAnnotationFiles));

            File textFolder = new File(textDir);
            File[] arrayOfTextFiles = textFolder.listFiles();
            ArrayList<File> listOfTextFiles = new ArrayList<>(Arrays.asList(arrayOfTextFiles));

            for (File annotationFile : listOfAnnotationFiles) {
                //    System.out.println("PROCESSING ANNOTATION FILE: " + annotationFile.getName());
                File textFile = null;
                for (File textTmp : listOfTextFiles) {
                    if (textTmp.getName().equals(annotationFile.getName())) {
                        textFile = textTmp;
                    }
                }
                List<String> annotationRaw = Files.readAllLines(annotationFile.toPath(), charset);
                String textString = new Scanner(textFile, charset.displayName()).useDelimiter("/n/r").next();
                textString = textString.replaceAll(replaceString, "");
                for (String annotation : annotationRaw) {

                    if (!annotation.contains("Rejected")) {
                        String[] annotationTokens = annotation.split("\\|");
                        if (!annotationTokens[0].equalsIgnoreCase("entrel")) {
                            String type = annotationTokens[0];
                            ArrayList<Span> conSpans = new ArrayList<>();
                            if (type.equalsIgnoreCase("implicit")) {
                                conSpans.add(new Span(annotationTokens[7], 0, 0, "impConn"));
                            } else {
                                conSpans = extractArgument(annotationTokens[1], textString, "Conn");
                            }

                            ArrayList<Span> arg1Spans = extractArgument(annotationTokens[14], textString, "Arg1");
                            ArrayList<Span> arg2Spans = extractArgument(annotationTokens[20], textString, "Arg2");
                            ArrayList<Span> supp1Spans = new ArrayList<>();// extractArgument(annotationTokens[32], textString, "Supp1");
                            ArrayList<Span> supp2Spans = new ArrayList<>();// extractArgument(annotationTokens[31], textString, "Supp2");
                            ArrayList<Span> modSpans = new ArrayList<>();// extractArgument(annotationTokens[32], textString, "Mod");

                            String senseArray[] = annotationTokens[8].split("\\.");
                            String sense = "";
                            for (String str : senseArray) {
                                sense = sense + ": " + str;
                            }
                            Annotation currentAnnotation = new Annotation(conSpans, arg1Spans, arg2Spans, modSpans, supp1Spans, supp2Spans, sense, "", type, "");
                            pdtbAnnotationList.addAnnotation(currentAnnotation);
                            if (!annotationTokens[9].equals("")) {
                                String sense2Array[] = annotationTokens[9].split("\\.");
                                sense = "";
                                for (String str : sense2Array) {
                                    sense = sense + ": " + str;
                                }
                                currentAnnotation = new Annotation(conSpans, arg1Spans, arg2Spans, modSpans, supp1Spans, supp2Spans, sense, "", type, "");
                                pdtbAnnotationList.addAnnotation(currentAnnotation);
                            }
                        }
                    }
                }
            }
        }
        return pdtbAnnotationList;
    }

    public static ArrayList<Span> extractArgument(String rawIndex, String textString, String belongsTo) {
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

    public static boolean checkDirectory(String annotationDir, String textDir) {
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

}
