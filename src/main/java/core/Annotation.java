package core;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * Created by Murathan on 08-Nov-16.
 */
public class Annotation {

    ArrayList<Span> connective;

    ArrayList<Span> mod;

    ArrayList<Span> argument1;
    ArrayList<Span> argument2;

    String sense1;
    String sense2;
    String sense3;

    String fullSense;

    String type;
    String note;
    String genre;

    public Annotation() {
        super();

    }

    public Annotation(ArrayList<Span> conSpans, ArrayList<Span> arg1Spans, ArrayList<Span> arg2Spans, ArrayList<Span> modSpans, String sense, String note, String type, String genre) {
        super();
        connective = conSpans;
        argument1 = arg1Spans;
        argument2 = arg2Spans;
        mod = modSpans;

        StringTokenizer token = new StringTokenizer(sense, ":");
        this.sense1 = token.hasMoreTokens() ? token.nextToken() : "noo";
        this.sense2 = token.hasMoreTokens() ? token.nextToken() : "noo sense 2";
        this.sense3 = token.hasMoreTokens() ? token.nextToken() : "noo sense 3";

        this.sense1 = sense1.trim();
        this.sense2 = sense2.trim();
        this.sense3 = sense3.trim();

        this.fullSense = sense1;

        if (!sense2.contains("oo")) {
            this.fullSense = this.fullSense + ": " + sense2;
        }
        if (!sense3.contains("oo")) {
            this.fullSense = this.fullSense + ": " + sense3;
        }

        this.note = note;
        this.type = type;
        this.genre = genre;
    }

    @Override
    public String toString() {

        String output = "";
        TreeMap<Integer, String> argMap = new TreeMap<>();
        connective.forEach((s) -> {
            argMap.put(s.getBeg(), s.getText());
        });
        argument1.forEach((s) -> {
            argMap.put(s.getBeg(), s.getText());
        });
        argument2.forEach((s) -> {
            argMap.put(s.getBeg(), s.getText());
        });
        if (mod != null) {
            mod.forEach((s) -> {
                argMap.put(s.getBeg(), "#" + s.getText().toUpperCase());
            });
        }
        for (Integer i : argMap.keySet()) {
            //  System.out.print(argMap.get(i) + " ");
            output = output + " " + argMap.get(i);
        }
        return output;
    }

    public boolean checkConnective(String searchedConnective) {
        boolean found = false;
        for (Span s : this.connective) {
            if (s.getText().equals(searchedConnective)) {
                found = true;
            }
        }
        return found;
    }

    public String getFullSense() {
        return fullSense;
    }

    public String getSense1() {
        return sense1;
    }

    public String getSense2() {
        return sense2;
    }

    public String getSense3() {
        return sense3;
    }

    public ArrayList<Span> getArgument1() {
        return argument1;
    }

    public ArrayList<Span> getArgument2() {
        return argument2;
    }

    public ArrayList<Span> getConnective() {
        return connective;
    }

    public ArrayList<Span> getMod() {
        return mod;
    }

}
