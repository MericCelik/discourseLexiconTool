/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Murathan
 */
public class annotationList {

    ArrayList<Annotation> annotationList;

  
    Map<String, Integer> typeFreqMap;
    Map<String, Integer> sense1FreqMap;
    Map<String, Integer> sense2FreqMap;
    Map<String, Integer> sense3FreqMap;

    public annotationList() {
        annotationList = new ArrayList();
        typeFreqMap = new TreeMap<>();
        sense1FreqMap = new TreeMap<>();
        sense2FreqMap = new TreeMap<>();
        sense3FreqMap = new TreeMap<>();
    }

    public void addAnnotation(Annotation anno) {
        this.annotationList.add(anno);
        updateMap(typeFreqMap, anno.getType());
        updateMap(sense1FreqMap, anno.getSense1());
        updateMap(sense2FreqMap, anno.getSense2());
        updateMap(sense3FreqMap, anno.getSense3());
    }

    private void updateMap(Map<String, Integer> map, String toBeAdded) {
        if (!map.containsKey(toBeAdded)) {
            map.put(toBeAdded, 1);
        } else {
            int oldValue = map.get(toBeAdded);
            map.put(toBeAdded, oldValue + 1);
        }
    }
    
    public void printEmpty() {
       for(Annotation x: annotationList)
       {
            if(x.getSense1().equals("") && !x.getType().equalsIgnoreCase("norel"))
                System.out.println(x.toFullString());
       }
    }

    public Map<String, Integer> getSense1FreqMap() {
        return sense1FreqMap;
    }

    public Map<String, Integer> getSense2FreqMap() {
        return sense2FreqMap;
    }

    public Map<String, Integer> getSense3FreqMap() {
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

    public Map<String, Integer> getTypeFreqMap() {
        return typeFreqMap;
    }
      public ArrayList<Annotation> getAnnotationList() {
        return annotationList;
    }
      
    

}
