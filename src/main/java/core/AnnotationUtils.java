package core;


import DLVTReader.readerDLVT;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Murathan on 08-Nov-16.
 */
public class AnnotationUtils {


    private ArrayList<Annotation> annotationList;
    private HashMap<String, Set<String>> connectiveSenseList = new HashMap<String, Set<String>>();

    public ArrayList<Annotation> getAnnotationList() {
        return annotationList;
    }


    public AnnotationUtils(String file) throws org.xml.sax.SAXException, ParserConfigurationException, 
            XPathExpressionException, IOException {
       // annotationList = new readerDLVT(file);
        generateConnectiveSenseList();
    }

    public AnnotationUtils() {
        super();
    }
    
    private void generateConnectiveSenseList() {

        for (Annotation anno : annotationList) {

            String connective = anno.getConnective().get(0).getText().toLowerCase();
            Set<String> senseList = new HashSet<>();
            if (!connectiveSenseList.containsKey(connective)) {
                senseList.add(anno.getFullSense());
                connectiveSenseList.put(connective, senseList);
            } else {
                Set<String> oldList = connectiveSenseList.get(connective);
                oldList.add(anno.getFullSense());
                connectiveSenseList.put(connective, oldList);
            }
        }
    }

    public ArrayList<Annotation> searchByConnectiveAndSense(String connective, String sense) {
        ArrayList<Annotation> result = new ArrayList<>();
        for (Annotation anno : annotationList) {
            String allSense = anno.getSense1() + " : " + anno.getSense2() + " : " + anno.getSense3();
            if (anno.checkConnective(connective) && allSense.toLowerCase().contains(sense.toLowerCase()))
                result.add(anno);
        }
        return result;
    }

    public HashMap<String, Set<String>> getConnectiveSenseList() {
        return connectiveSenseList;
    }


}
