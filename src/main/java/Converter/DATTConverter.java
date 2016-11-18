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

    String delimiter = "!#!";

    ArrayList<Annotation> annotationList;
    ArrayList<String> connectiveList = new ArrayList<String>();
    HashMap<String, ArrayList<Annotation>> connectiveAnnotationMap = new HashMap<String, ArrayList<Annotation>>();

    public DATTConverter(String inputDir, String outputDir) throws ParserConfigurationException, SAXException, IOException {

        this.readDATTRelations(inputDir);
        this.writeToFile(outputDir);
    }

    public String getTag(Element eElement, String tag, int level) {
        return eElement.getElementsByTagName(tag).item(level).getTextContent().replace("\n", "").replace("\r", "").replaceAll("[ ]+", " ");

    }

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        DATTConverter d = new DATTConverter("annotations\\explicit.xml", "C:\\Users\\Murathan\\github\\discourseLexiconTool\\testing.xml");



    }

    public HashMap<String, ArrayList<Annotation>> readDATTRelations(String dir) throws IOException, org.xml.sax.SAXException, ParserConfigurationException {

        System.out.println("Reading DATT relations from the file: " + dir + " has started.");
        File fXmlFile = new File(dir);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);
        doc.getDocumentElement().normalize();

        annotationList = new ArrayList<Annotation>();
        NodeList nList = doc.getElementsByTagName("Relation");
        for (int temp = 0; temp < nList.getLength(); temp++) {

            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element currentElement = (Element) nNode;

                // reading connective and arguments
                NodeList conn = currentElement.getElementsByTagName("Conn");
                NodeList arg1 = currentElement.getElementsByTagName("Arg1");
                NodeList arg2 = currentElement.getElementsByTagName("Arg2");


                ArrayList<Span> connSpans = getContext(conn, "conn");
                ArrayList<Span> arg1Spans = getContext(arg1, "arg1");
                ArrayList<Span> arg2Spans = getContext(arg2, "arg2");

                Span cSpan = connSpans.get(0);
                String connectiveString = cSpan.toString().toLowerCase();
                if (!connectiveList.contains(connectiveString.toLowerCase()))
                    connectiveList.add(connectiveString.toString());
                // reading connective and arguments

                // reading note-sense etc.
                String note = currentElement.getAttribute("note");
                String senses = currentElement.getAttribute("sense");
                String type = currentElement.getAttribute("type");
                String genre = currentElement.getAttribute("genre");

                // check if those fields are empty
                if (note.equals(""))
                    note = "nothing";
                if (senses.equals(""))
                    senses = "nothing";
                if (type.equals(""))
                    type = "nothing";
                if (genre.equals(""))
                    genre = "nothing";
                // reading note-sense etc.

                Annotation currentAnnotation = new Annotation(connSpans, arg1Spans, arg2Spans, senses, note, type, genre);

                if (connectiveAnnotationMap.keySet().contains(connectiveString.toString())) {
                    ArrayList<Annotation> tempList = connectiveAnnotationMap.get(connectiveString);
                    tempList.add(currentAnnotation);
                    connectiveAnnotationMap.put(connectiveString, tempList);
                } else {
                    ArrayList<Annotation> tempList = new ArrayList<Annotation>();
                    tempList.add(currentAnnotation);
                    connectiveAnnotationMap.put(connectiveString, tempList);
                }
            }
        }
        System.out.println("Reading DATT relations from the file: " + dir + " has finished.");

        return connectiveAnnotationMap;
    }

    public ArrayList<Span> getContext(NodeList nodeList, String belongsTo) {

        ArrayList<Span> spans = new ArrayList<Span>();
        for (int temp = 0; temp < nodeList.getLength(); temp++) {
            Node nNode = nodeList.item(temp);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;

                for (int i = 0; i < eElement.getElementsByTagName("Text").getLength(); i++) {
                    Span argumentSpan = new Span(getTag(eElement, "Text", i), Integer.parseInt(getTag(eElement, "BeginOffset", i)), Integer.parseInt(getTag(eElement, "EndOffset", i)), belongsTo);
                    spans.add(argumentSpan);
                }
            }
        }
        return spans;
    }

    public void displayAll() {
        for (Annotation anno : annotationList)
            System.out.println(anno);
    }

    public void writeToFile(String dir) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            //root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("Document");
            doc.appendChild(rootElement);

            for (String con : connectiveAnnotationMap.keySet()) {

                //staff elements
                Element connectiveElement = doc.createElement("Connective");
                connectiveElement.setAttribute("Item", con);
                connectiveElement.setAttribute("Size", "" + connectiveAnnotationMap.get(con).size());
                HashSet<String> conSenseSet = new HashSet<String>();
                rootElement.appendChild(connectiveElement);

                for (Annotation anno : connectiveAnnotationMap.get(con)) {

                    if(anno.getFullSense().equalsIgnoreCase("nothing"))
                        continue;

                    conSenseSet.add(anno.getFullSense());

                    Element annotationElement = doc.createElement("Annotation");
                    connectiveElement.appendChild(annotationElement);
                    annotationElement.setAttribute("conBeginOffset", "" + anno.getConnective().get(0).getBeg());
                    if(anno.getFullSense() == null)
                        System.out.println(anno.toString());
                    annotationElement.setAttribute("sense", "" + anno.getFullSense());

                    Element arg1Element = doc.createElement("Arg1");
                    Element arg2Element = doc.createElement("Arg2");

                    Element arg1TextElement = doc.createElement("Text");
                    Element arg1OrderElement = doc.createElement("BeginOffset");

                    Element arg2TextElement = doc.createElement("Text");
                    Element arg2OrderElement = doc.createElement("BeginOffset");

                    ArrayList<Span> arg1Spans = anno.getArgument1();
                    ArrayList<Span> arg2Spans = anno.getArgument2();

                    ArrayList<String> arg1OffsetText = this.getArgumentsForXML(arg1Spans);
                    ArrayList<String> arg2OffsetText = this.getArgumentsForXML(arg2Spans);


                    arg1TextElement.appendChild(doc.createTextNode(arg1OffsetText.get(1)));
                    arg1OrderElement.appendChild(doc.createTextNode(arg1OffsetText.get(0)));

                    arg2TextElement.appendChild(doc.createTextNode(arg2OffsetText.get(1)));
                    arg2OrderElement.appendChild(doc.createTextNode(arg2OffsetText.get(0)));


                    annotationElement.appendChild(arg1Element);
                    annotationElement.appendChild(arg2Element);
                    arg1Element.appendChild(arg1TextElement);
                    arg1Element.appendChild(arg1OrderElement);
                    arg2Element.appendChild(arg2TextElement);
                    arg2Element.appendChild(arg2OrderElement);
                }
                String senseList = "";
                for (String str : conSenseSet)
                    senseList = str + delimiter + senseList;

                connectiveElement.setAttribute("senseList", senseList.substring(0, senseList.length() - delimiter.length()));
            }
            //write the content into xml file
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(new File(dir));
            transformer.transform(source, result);
            System.out.println("DATT relations has been converted: " + dir);

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
        System.out.println("Done");

    }

    public ArrayList<String> getArgumentsForXML(ArrayList<Span> arguments) {

        String offSets = "";
        String textArgument = "";
        ArrayList<String> res = new ArrayList<String>();

        if (arguments.size() > 1) {
            for (Span s : arguments) {
                offSets = offSets + delimiter + s.getBeg();
                textArgument = textArgument + delimiter + s.getText();
            }
        } else {
            offSets = "" + arguments.get(0).getBeg();
            textArgument = arguments.get(0).getText();
        }

        if (offSets.startsWith(delimiter))
            offSets = offSets.substring(delimiter.length());

        if (textArgument.startsWith(delimiter))
            textArgument = textArgument.substring(delimiter.length());

        if (offSets.endsWith(delimiter))
            offSets = offSets.substring(0, offSets.length() - delimiter.length());
        if (textArgument.endsWith(delimiter))
            textArgument = textArgument.substring(0, offSets.length() - delimiter.length());
        res.add(offSets);
        res.add(textArgument);


        return res;

    }

}