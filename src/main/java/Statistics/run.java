/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Statistics;

import core.annotationList;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 *
 * @author Murathan
 */
public class run {

    public static void main(String[] args) throws IOException {

        System.out.println("");
        readRelationsForStatistics.replaceString = "\n\r";
        annotationList list = readRelationsForStatistics.readPDTBRelations("TEDTalks\\Amalia - Completed Annotations\\ann\\section_1",
                "TEDTalks\\Amalia - Completed Annotations\\raw\\section_1");
        Charset charset = Charset.forName("UTF-8");

        //      String textString = new Scanner(new File("TEDTalks//Yulia_german//Raw//section//talk_1971_de.txt"), charset.displayName()).useDelimiter("/n/r").next();
        //      System.out.println(textString);
        list.printStatistics();

    }

}
