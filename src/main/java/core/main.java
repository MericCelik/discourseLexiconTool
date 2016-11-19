package core;

import DLVTReader.readerDLVT;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Murathan on 08-Nov-16.
 */
public class main {

    public static void main(String[] args) throws SAXException, ParserConfigurationException, XPathExpressionException, IOException {

        readerDLVT r = new readerDLVT("testing.xml");

        HashMap<String, ArrayList<String>> connectiveSenseMap = r.getConnectiveSenseMap();
        HashMap<String, ArrayList<Annotation>> connectiveAnnotationMap = r.getConnectiveAnnotationMap();

        System.out.println("--------Connective Sense List--------");
        connectiveSenseMap.keySet().stream().map((str) -> {
            System.out.println(str);
            return str;
        }).map((str) -> {
            for (String x : connectiveSenseMap.get(str)) {
                System.out.print("\t" + x + "; ");
            }
            return str;
        }).forEachOrdered((_item) -> {
            System.out.println();
        });
        System.out.println("--------Connective Sense List--------");
        
        PrintWriter wr;
        wr = new PrintWriter("out.txt");

        System.out.println("--------Connective Annotation List--------");
        for (String str : connectiveAnnotationMap.keySet()) {
            System.out.println("\t" + str.toUpperCase());
            wr.println("\t" + str.toUpperCase());
            for (Annotation x : connectiveAnnotationMap.get(str)) {
                if(x.toString().contains("asmak için") )
                {
                    System.out.println("");
                }
                System.out.println(x.toString() );
                wr.println(x.toString() );
            }
            System.out.println();
        }
        wr.close();
        System.out.println("--------Connective Annotation List--------");

    }
}
