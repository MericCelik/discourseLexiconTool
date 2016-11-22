package DLVTReader;

import core.Annotation;
import core.Span;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Murathan on 14-Nov-16.
 */
public class readerDLVT {

    private ArrayList<Annotation> annotationList = new ArrayList<>();
    private HashMap<String, ArrayList<Annotation>> connectiveAnnotationMap;
    private HashMap<String, ArrayList<String>> connectiveSenseMap;
    private HashMap<String, Set<String>> senseConnectiveMap;

    private HashMap<String, Integer> connectiveNumberofAnnotation;
    private String delimiter = "!#!";

    public readerDLVT(String dir) throws ParserConfigurationException, SAXException, IOException {
        this.connectiveAnnotationMap = new HashMap<>();
        this.connectiveSenseMap = new HashMap<>();
        this.connectiveNumberofAnnotation = new HashMap<>();
        this.senseConnectiveMap = new HashMap<>();
        this.readRelations(dir);
    }

    private ArrayList<Annotation> readRelations(String dir) throws IOException, org.xml.sax.SAXException, ParserConfigurationException {

        File fXmlFile = new File(dir);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);
        doc.getDocumentElement().normalize();

        annotationList = new ArrayList<>();
        NodeList nList = doc.getElementsByTagName("Connective");
        String conString = "";

        for (int temp = 0; temp < nList.getLength(); temp++) {
            ArrayList<Annotation> connectiveBasedAnnotations = new ArrayList<>();
            Node connectiveNode = nList.item(temp);
            if (connectiveNode.getNodeType() == Node.ELEMENT_NODE) {
                Element currentElement = (Element) connectiveNode;
                conString = currentElement.getAttribute("Item");
                int noOfAnno = Integer.parseInt(currentElement.getAttribute("Size"));
                connectiveSenseMap.put(conString, new ArrayList<>(Arrays.asList(currentElement.getAttribute("senseList").split(delimiter))));
                connectiveNumberofAnnotation.put(conString, noOfAnno);
                NodeList annoNodeList = currentElement.getElementsByTagName("Annotation");
                for (int i = 0; i < annoNodeList.getLength(); i++) {

                    Node annotationNode = annoNodeList.item(i);
                    Element annotationElement = (Element) annotationNode;

                    Node arg1Node = currentElement.getElementsByTagName("Arg1").item(i);
                    Node arg2Node = currentElement.getElementsByTagName("Arg2").item(i);
                    Node modNode = currentElement.getElementsByTagName("Mod").item(i);

                    ArrayList<Span> arg1 = getContext(arg1Node, "Arg1");
                    ArrayList<Span> arg2 = getContext(arg2Node, "Arg2");
                    ArrayList<Span> mod = getContext(modNode, "Mod");
                    String sense = annotationElement.getAttribute("sense");

                    if (!senseConnectiveMap.containsKey(sense)) {
                        Set<String> connnectiveListforSenseCon = new HashSet<>();
                        connnectiveListforSenseCon.add(conString);
                        senseConnectiveMap.put(sense, connnectiveListforSenseCon);
                    } else {
                        Set<String> oldList = senseConnectiveMap.get(sense);
                        oldList.add(conString);
                        senseConnectiveMap.put(sense, oldList);
                    }

                    ArrayList<String> conBeginOffsetArray = new ArrayList<>(Arrays.asList(annotationElement.getAttribute("conBeginOffset").split(delimiter)));
                    ArrayList<String> conTextArray = new ArrayList<>(Arrays.asList(conString.split("_")));
                    ArrayList<Span> ConSpanList = new ArrayList<>();

                    if (conTextArray.size() != conBeginOffsetArray.size()) {
                        System.out.println("You have connection problems  " + conTextArray.get(0));
                    } else {
                        for (int k = 0; k < conBeginOffsetArray.size(); k++) {
                            Span ConSpan = new Span(conTextArray.get(k), Integer.parseInt(conBeginOffsetArray.get(k)), "conn");
                            ConSpanList.add(ConSpan);
                        }
                    }

                    // ArrayList<Span> conSpans, ArrayList<Span> arg1Spans, ArrayList<Span> arg2Spans, String sense, String note, String type, String genre
                    // reading connective and arguments
                    Annotation anno = new Annotation(ConSpanList, arg1, arg2, mod, sense, "", "", "");
                    connectiveBasedAnnotations.add(anno);
                    annotationList.add(anno);
                }
            }
            connectiveAnnotationMap.put(conString, connectiveBasedAnnotations);
        }
        return annotationList;
    }

    public ArrayList<Span> getContext(Node argNode, String belongsTo) {

        ArrayList<Span> spans = new ArrayList<>();

        String text = getTag(argNode, "Text");
        String offSet = getTag(argNode, "BeginOffset");
        if ("".equals(text)) {
            return spans;
        }

        String[] textArray = text.split(delimiter);
        String[] offSetArray = offSet.split(delimiter);
        if (textArray.length != offSetArray.length) {
            System.out.println("NOT IN KANSAS ANYMORE!");
        } else {
            for (int i = 0; i < textArray.length; i++) {
                Span tempSpan = new Span(textArray[i], Integer.parseInt(offSetArray[i]), belongsTo);
                spans.add(tempSpan);
            }
        }
        return spans;
    }

    public String getTag(Node argNode, String tag) {

        Element eElement = (Element) argNode;
        String res = "";
        if (eElement.getElementsByTagName(tag).item(0) != null) {
            res = eElement.getElementsByTagName(tag).item(0).getTextContent().replace("\n", "").replace("\r", "").replaceAll("[ ]+", " ");
        }
        return res;
    }

    public ArrayList<Annotation> getAnnotationBasedConnectiveSense(String connective, String sense) {
        ArrayList<Annotation> result = new ArrayList<>();
        for (Annotation anno : connectiveAnnotationMap.get(connective)) {
            String allSense = anno.getFullSense();
            if (allSense.toLowerCase().equals(sense.toLowerCase())) {
                result.add(anno);
            }
        }
        return result;
    }

    public ArrayList<Annotation> getAnnotationList() {
        return annotationList;
    }

    public HashMap<String, ArrayList<Annotation>> getConnectiveAnnotationMap() {
        return connectiveAnnotationMap;
    }

    public HashMap<String, ArrayList<String>> getConnectiveSenseMap() {
        return connectiveSenseMap;
    }

    public HashMap<String, Integer> getConnectiveNumberofAnnotation() {
        return connectiveNumberofAnnotation;
    }

    public HashMap<String, Set<String>> getSenseConnectiveMap() {
        return senseConnectiveMap;
    }
}
