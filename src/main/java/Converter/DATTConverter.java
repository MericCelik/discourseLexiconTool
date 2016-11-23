package Converter;

/**
 * Created by Murathan on 08-Nov-16.
 */
import core.Annotation;
import core.Span;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class DATTConverter {

    String delimiter = ConverterUtils.delimiter;

    private ArrayList<String> connectiveList = new ArrayList<>();
    private HashMap<String, ArrayList<Annotation>> connectiveAnnotationMap = new HashMap<>();

    public DATTConverter(String inputDir, String outputDir) throws ParserConfigurationException, SAXException, IOException {

        this.readDATTRelations(inputDir);
        ConverterUtils.writeToFile(outputDir, connectiveAnnotationMap, "DATT");
    }

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        DATTConverter d = new DATTConverter("annotations//DATT//explicit.xml", "testing_datt.xml");
    }

    public HashMap<String, ArrayList<Annotation>> readDATTRelations(String dir) throws IOException, org.xml.sax.SAXException, ParserConfigurationException {

        File fXmlFile = new File(dir);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);
        doc.getDocumentElement().normalize();

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

                String connectiveString = "";
                for (Span s : connSpans) {
                    connectiveString = connectiveString + "_" + s.getText().toLowerCase();
                }
                connectiveString = connectiveString.substring(1);
                if (!connectiveList.contains(connectiveString)) {
                    connectiveList.add(connectiveString);
                }
                // reading connective and arguments

                // reading note-sense etc.
                String note = currentElement.getAttribute("note");
                String senses = currentElement.getAttribute("sense");
                String type = currentElement.getAttribute("type");
                String genre = currentElement.getAttribute("genre");

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
        System.out.println("DATT relations has been read from the file: " + dir);

        return connectiveAnnotationMap;
    }

    

    

   

}
