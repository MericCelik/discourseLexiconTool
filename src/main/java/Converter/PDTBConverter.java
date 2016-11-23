/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Converter;

import core.Annotation;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author Murathan
 */
public class PDTBConverter {

    String delimiter = "!#!";

    private ArrayList<String> connectiveList = new ArrayList<>();
    private HashMap<String, ArrayList<Annotation>> connectiveAnnotationMap = new HashMap<>();

    public PDTBConverter(String annotationDir, String textDir) throws IOException {
        connectiveAnnotationMap = readPDTBRelations(annotationDir, textDir);

    }

    public HashMap<String, ArrayList<Annotation>> readPDTBRelations(String annotationDir, String textDir) throws IOException {

        if (checkDirectory(annotationDir, textDir)) {

            File annotationFolder = new File(annotationDir);
            File[] arrayOfAnnotationFiles = annotationFolder.listFiles();
            ArrayList<File> listOfAnnotationFiles = new ArrayList<>(Arrays.asList(arrayOfAnnotationFiles));

            File textFolder = new File(textDir);
            File[] arrayOfTextFiles = textFolder.listFiles();
            ArrayList<File> listOfTextFiles = new ArrayList<>(Arrays.asList(arrayOfTextFiles));
            
            for(File file : listOfAnnotationFiles)
            {
                    
            
            }
            
            
            
        }
        return null;
    }

    public static void main(String[] args) throws IOException {

        PDTBConverter pdtb = new PDTBConverter("annotations\\PDTB\\Annotation", "annotations\\PDTB\\Text");

    }

    private boolean checkDirectory(String annotationDir, String textDir) {
        boolean identical = true;
        File annotationFolder = new File(annotationDir);
        String[] arrayOfAnnotationFiles = annotationFolder.list();
        ArrayList<String> listOfAnnotationFiles = new ArrayList<>(Arrays.asList(arrayOfAnnotationFiles));

        File textFolder = new File(textDir);
        String[] arrayOfTextFiles = textFolder.list();
        ArrayList<String> listOfTextFiles = new ArrayList<>(Arrays.asList(arrayOfTextFiles));

        for (String fileName : listOfAnnotationFiles) {
            if (!listOfTextFiles.contains(fileName)) {
                System.out.println("There is not any text file for the annotation file: " + fileName);
                identical = false;
            }
        }
        if(!identical)
            System.out.println("EXIT");
        return identical;
    }
}
