/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agreement;

import Converter.DATTConverter;
import core.Annotation;
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
    HashMap<String, ArrayList<Annotation>> anno1Map = new HashMap<>();
    HashMap<String, ArrayList<Annotation>> anno2Map = new HashMap<>();

    public calculateInterAgreement(String dir1, String dir2) throws IOException, SAXException, ParserConfigurationException, ParserConfigurationException {
        anno1Map = readRelationsForInterAgreement.readDATTRelations(dir1);
        anno2Map = readRelationsForInterAgreement.readDATTRelations(dir2);
    }

}
