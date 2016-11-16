package core;

import javafx.util.Pair;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by Murathan on 08-Nov-16.
 */
public class main {


    public static void main(String[] args) throws SAXException, ParserConfigurationException, jdk.internal.org.xml.sax.SAXException, XPathExpressionException, IOException {

        readerDLVT r =  new readerDLVT();
        r.readRelations("testing.xml");
        HashMap<String, ArrayList<String>> connectiveSenseMap = r.connectiveSenseMap;

        for(String str: connectiveSenseMap.keySet())
        {
            System.out.println(str);
            for(String x : connectiveSenseMap.get(str))
                System.out.print("\t" + x + " -- ");
            System.out.println();
        }


    }
}
