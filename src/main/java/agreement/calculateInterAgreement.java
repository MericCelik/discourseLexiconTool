/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agreement;

import Converter.DATTConverter;
import core.Annotation;
import java.io.File;
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
        File folderAnno1 = new File(dir1);
        File folderAnno2 = new File(dir2);

        File[] Anno1Files = folderAnno1.listFiles();
        File[] Anno2Files = folderAnno2.listFiles();

        if (Anno1Files.length != Anno2Files.length) {
            System.out.println("I'm sorry, Dave. I'm afraid I can't do that".toUpperCase());
        }
        for (int i = 0; i < Anno1Files.length; i++) {
            System.out.println(Anno1Files[i].getPath());
            anno1Map = readRelationsForInterAgreement.readDATTRelations(Anno1Files[i].getPath());
            anno2Map = readRelationsForInterAgreement.readDATTRelations(Anno2Files[i].getPath());
            calculateInterAgreement(anno1Map, anno2Map);
        }
    }

    private void calculateInterAgreement(HashMap<String, ArrayList<Annotation>> anno1Map, HashMap<String, ArrayList<Annotation>> anno2Map) {
    }
    
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException, ParserConfigurationException{
        
        calculateInterAgreement calculator = new calculateInterAgreement("Agreement\\Savas", "Agreement\\Agreed");
    }

}
