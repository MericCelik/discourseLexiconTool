/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Murathan
 */
public class annotationList {

    ArrayList<Annotation> annotationList;
    HashMap<String, Integer> typeFreqMap;

    public annotationList() {
        annotationList = new ArrayList();
        typeFreqMap = new HashMap<>();
    }

    public void addAnnotation(Annotation anno) {
        this.annotationList.add(anno);
        String type = anno.getType();
        if (!typeFreqMap.containsKey(type)) {
            typeFreqMap.put(type, 1);
        } else {
            int oldValue = typeFreqMap.get(type);
            typeFreqMap.put(type, oldValue + 1);
        }
    }

    public void print() {
        for (Annotation anno : annotationList) {
            System.out.println(anno.getType() + " " + anno.toString());
        }
    }

    public void printStatistics() {

        for (String type : typeFreqMap.keySet()) {
            System.out.printf("%-10s%5d", type, typeFreqMap.get(type));
            System.out.println("");
        }
    }

    public HashMap<String, Integer> getTypeFreqMap() {
        return typeFreqMap;
    }

}
