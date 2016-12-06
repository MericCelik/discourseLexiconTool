/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Converter;

import core.Annotation;
import core.Span;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This class includes several methods which are mainly used in PDTBConverter.
 * Also, it includes a "writeToFile" method which is called by both
 * PDTBConverter and DATTConverter. This method basically saves the read
 * annotations in a way that DLVT can read.
 *
 * @author Murathan
 */
public class ConverterUtils {

    static String delimiter = "!#!";
    static String outputFile = "";

    public static void writeToFile(String dir, HashMap<String, ArrayList<Annotation>> connectiveAnnotationMap, String annotationToolType) {
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
                HashSet<String> conSenseSet = new HashSet<>();
                rootElement.appendChild(connectiveElement);

                for (Annotation anno : connectiveAnnotationMap.get(con)) {

                    if (anno.getFullSense().equalsIgnoreCase("nothing")) {
                        continue;
                    }

                    conSenseSet.add(anno.getFullSense());

                    Element annotationElement = doc.createElement("Annotation");
                    connectiveElement.appendChild(annotationElement);

                    ArrayList<Span> connectiveSpans = anno.getConnective();
                    ArrayList<String> connectiveOffsetText = ConverterUtils.getArgumentsForXML(connectiveSpans);
                    annotationElement.setAttribute("conBeginOffset", connectiveOffsetText.get(0));

                    if (anno.getFullSense() == null) {
                        System.out.println(anno.toString());
                    }
                    annotationElement.setAttribute("sense", "" + anno.getFullSense());

                    ArrayList<Span> arg1Spans = anno.getArgument1();
                    ArrayList<Span> arg2Spans = anno.getArgument2();
                    ArrayList<Span> modSpans = anno.getMod();
                    ArrayList<Span> supp1Spans = anno.getSupp1();
                    ArrayList<Span> supp2Spans = anno.getSupp2();

                    Element arg1Element = doc.createElement("Arg1");
                    Element arg2Element = doc.createElement("Arg2");
                    Element modElement = null;
                    Element supp1Element = null;
                    Element supp2Element = null;

                    nodeCreation(doc, arg1Spans, arg1Element, "Arg1", annotationElement);
                    nodeCreation(doc, arg2Spans, arg2Element, "Arg2", annotationElement);
                    nodeCreation(doc, modSpans, modElement, "Mod", annotationElement);
                    nodeCreation(doc, supp1Spans, supp1Element, "Supp1", annotationElement);
                    nodeCreation(doc, supp2Spans, supp2Element, "Supp2", annotationElement);

                }
                String senseList = "";
                for (String str : conSenseSet) {
                    senseList = str + delimiter + senseList;
                }

                connectiveElement.setAttribute("senseList", senseList.substring(0, senseList.length() - delimiter.length()));
            }

            System.out.println("converterUtils: " + dir);
            File f = new File(dir + ".dlvt");
         
            //write the content into xml file
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(f);
            transformer.transform(source, result);
            System.out.println(annotationToolType + " relations has been converted: " + f.getPath());
            outputFile = f.getPath();

        } catch (ParserConfigurationException | TransformerException pce) {
        }
        System.out.println("Done");

    }

    private static void nodeCreation(Document doc, ArrayList<Span> spanList, Element addedElement, String tag, Element annotationElement) {
        if (!spanList.isEmpty()) {
            addedElement = doc.createElement(tag);
            ArrayList<String> OffsetText = ConverterUtils.getArgumentsForXML(spanList);

            Element addedTextElement = doc.createElement("Text");
            Element addedOrderElement = doc.createElement("BeginOffset");

            addedOrderElement.appendChild(doc.createTextNode(OffsetText.get(0)));
            addedTextElement.appendChild(doc.createTextNode(OffsetText.get(1)));

            annotationElement.appendChild(addedElement);
            addedElement.appendChild(addedTextElement);
            addedElement.appendChild(addedOrderElement);
        } else {
            addedElement = doc.createElement(tag);
            Element emptyTextElement = doc.createElement(tag);
            emptyTextElement.appendChild(doc.createTextNode(""));
            annotationElement.appendChild(addedElement);
        }
    }

    public static ArrayList<Span> getContext(NodeList nodeList, String belongsTo) {

        ArrayList<Span> spans = new ArrayList<>();
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

    public static ArrayList<String> getArgumentsForXML(ArrayList<Span> arguments) {
        // 0: Offset, 1 : text
        String offSets = "";
        String textArgument = "";
        ArrayList<String> res = new ArrayList<>();

        if (arguments.size() > 1) {
            for (Span s : arguments) {
                offSets = offSets + delimiter + s.getBeg();
                textArgument = textArgument + delimiter + s.getText();
            }
        } else {
            offSets = "" + arguments.get(0).getBeg();
            textArgument = arguments.get(0).getText();
        }

        if (offSets.startsWith(delimiter)) {
            offSets = offSets.substring(delimiter.length());
        }
        if (textArgument.startsWith(delimiter)) {
            textArgument = textArgument.substring(delimiter.length());
        }
        if (offSets.endsWith(delimiter)) {
            offSets = offSets.substring(0, offSets.length() - delimiter.length());
        }
        if (textArgument.endsWith(delimiter)) {
            textArgument = textArgument.substring(0, offSets.length() - delimiter.length());
        }

        res.add(offSets);
        res.add(textArgument);

        return res;

    }

    public static String getTag(Element eElement, String tag, int level) {
        return eElement.getElementsByTagName(tag).item(level).getTextContent().replace("\n", "").replace("\r", "").replaceAll("[ ]+", " ");

    }

}
