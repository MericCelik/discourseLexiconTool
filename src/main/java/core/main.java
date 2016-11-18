package core;

import DLVTReader.readerDLVT;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Murathan on 08-Nov-16.
 */
public class main {

    public static void main(String[] args) throws SAXException, ParserConfigurationException, XPathExpressionException, IOException {

        readerDLVT r =  new readerDLVT("testing.xml");

        HashMap<String, ArrayList<String>> connectiveSenseMap = r.getConnectiveSenseMap();

        System.out.println("--------Connective Sense List--------");
        for(String str: connectiveSenseMap.keySet())
        {
            System.out.println(str);
            for(String x : connectiveSenseMap.get(str))
                System.out.print("\t" + x + "; ");
            System.out.println();
        }
        System.out.println("--------Connective Sense List--------");





    }
}
