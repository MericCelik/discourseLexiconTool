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
    HashMap<String, Integer> sense1FreqMap;
    HashMap<String, Integer> sense2FreqMap;
    HashMap<String, Integer> sense3FreqMap;

    public annotationList() {
        annotationList = new ArrayList();
        typeFreqMap = new HashMap<>();
        sense1FreqMap = new HashMap<>();
        sense2FreqMap = new HashMap<>();
        sense3FreqMap = new HashMap<>();
    }

    public void addAnnotation(Annotation anno) {
        this.annotationList.add(anno);
        updateMap(typeFreqMap, anno.getType());
        updateMap(sense1FreqMap, anno.getSense1());
        updateMap(sense2FreqMap, anno.getSense2());
        updateMap(sense3FreqMap, anno.getSense3());
    }

    private void updateMap(HashMap<String, Integer> map, String toBeAdded) {
        if (!map.containsKey(toBeAdded)) {
            map.put(toBeAdded, 1);
        } else {
            int oldValue = map.get(toBeAdded);
            map.put(toBeAdded, oldValue + 1);
        }

    }

    public HashMap<String, Integer> getSense1FreqMap() {
        return sense1FreqMap;
    }

    public HashMap<String, Integer> getSense2FreqMap() {
        return sense2FreqMap;
    }

    public HashMap<String, Integer> getSense3FreqMap() {
        return sense3FreqMap;
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
