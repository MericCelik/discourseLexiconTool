package core;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Murathan on 14-Nov-16.
 */
public class readerDLVT {

    ArrayList<Annotation> annotationList;
    HashMap<String, ArrayList<Annotation>> connectiveAnnotationMap = new HashMap<String, ArrayList<Annotation>>();
   public HashMap<String, ArrayList<String>> connectiveSenseMap = new HashMap<String, ArrayList<String>>();


    public ArrayList<Annotation> readRelations(String dir) throws IOException, org.xml.sax.SAXException, ParserConfigurationException {

        File fXmlFile = new File(dir);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);
        doc.getDocumentElement().normalize();

        annotationList = new ArrayList<Annotation>();
        NodeList nList = doc.getElementsByTagName("Connective");

        for (int temp = 0; temp < nList.getLength(); temp++) {

            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element currentElement = (Element) nNode;
                String con = currentElement.getAttribute("Item");
                System.out.println("Con: " + con);
                connectiveSenseMap.put(currentElement.getAttribute("Item"), new ArrayList<String>(Arrays.asList(currentElement.getAttribute("senseList").split(";"))));

                NodeList arg1NodeList = currentElement.getElementsByTagName("Arg1");
                NodeList arg2NodeList = currentElement.getElementsByTagName("Arg2");

                if (arg1NodeList.getLength() != arg2NodeList.getLength())
                    System.out.println("NEOOOOO!!" );

                ArrayList<Span> arg1 = getContext(arg1NodeList, "Arg1");
                ArrayList<Span> arg2 = getContext(arg2NodeList, "Arg2");


                // ArrayList<Span> conSpans, ArrayList<Span> arg1Spans, ArrayList<Span> arg2Spans, String sense, String note, String type, String genre
                if (arg1.size() != arg2.size())
                    System.out.println("PROBLEM!!" + arg1.size() + " " + arg2.size());
                for (int i = 0; i < arg1.size(); i++) {
                    Annotation a = new Annotation();
                }
                // reading connective and arguments


            }
        }
        return annotationList;
    }

    public ArrayList<Span> getContext(NodeList nodeList, String belongsTo) {

        ArrayList<Span> spans = new ArrayList<Span>();
        for (int temp = 0; temp < nodeList.getLength(); temp++) {
            Node nNode = nodeList.item(temp);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;

                String text = getTag(eElement, "Text");
                String offsets = getTag(eElement, "BeginOffset");


            }
        }
        return spans;
    }

    public String getTag(Element eElement, String tag) {
        String res = eElement.getElementsByTagName(tag).item(0).getTextContent().replace("\n", "").replace("\r", "").replaceAll("[ ]+", " ");
       if(!res.contains("!#!"))
           return res;
        else
       {
           String[] temp = res.split("!#!");


           return null;
       }
    }
}
