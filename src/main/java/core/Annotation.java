package core;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * Created by Murathan on 08-Nov-16.
 */
public class Annotation {

    private ArrayList<Span> connective;
    private ArrayList<Span> mod;
    private ArrayList<Span> argument1;
    private ArrayList<Span> argument2;
    private ArrayList<Span> supp1;
    private ArrayList<Span> supp2;

    private TreeMap<Integer, Span> argMapforPrettyPrint;

    private String sense1;
    private String sense2;
    private String sense3;
    private String fullSense;

    private String type;
    private String note;
    private String genre;

    public Annotation() {
        super();
    }

    public Annotation(ArrayList<Span> conSpans, ArrayList<Span> arg1Spans, ArrayList<Span> arg2Spans, ArrayList<Span> modSpans,
            ArrayList<Span> supp1Spans, ArrayList<Span> supp2Spans, String sense, String note, String type, String genre) {
        super();
        connective = conSpans;
        argument1 = arg1Spans;
        argument2 = arg2Spans;
        mod = modSpans;
        supp1 = supp1Spans;
        supp2 = supp2Spans;

        argMapforPrettyPrint = new TreeMap<>();
        generateTreeMapPP();

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

    private void generateTreeMapPP() {
        connective.forEach((s) -> {
            argMapforPrettyPrint.put(s.getBeg(), s);
        });
        argument1.forEach((s) -> {
            argMapforPrettyPrint.put(s.getBeg(), s);
        });
        argument2.forEach((s) -> {
            argMapforPrettyPrint.put(s.getBeg(), s);
        });
        if (mod != null) {
            mod.forEach((s) -> {
                argMapforPrettyPrint.put(s.getBeg(), s);
            });
        }
        if (supp1 != null) {
            supp1.forEach((s) -> {
                argMapforPrettyPrint.put(s.getBeg(), s);
            });
        }
        if (supp2 != null) {
            supp2.forEach((s) -> {
                argMapforPrettyPrint.put(s.getBeg(), s);
            });
        }
    }

// not used
    public boolean checkConnective(String searchedConnective) {
        boolean found = false;
        String wholeConnective = "";
        for (Span s : this.connective) {
            wholeConnective = wholeConnective + "_" + s.getText();
        }
        wholeConnective = wholeConnective.substring(1);
        System.out.println("check: " + searchedConnective + " s: " + wholeConnective);

        if (wholeConnective.equals(searchedConnective)) {
            System.out.println("check: " + searchedConnective + " s: " + wholeConnective);
            found = true;
        }
        return found;
    }

    @Override
    public String toString() {
        String output = "";
        for (Integer i : argMapforPrettyPrint.keySet()) {
            //  System.out.print(argMap.get(i) + " ");
            output = output + " " + argMapforPrettyPrint.get(i).getText();
        }
        return output;
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

    public TreeMap<Integer, Span> getArgMapforPrettyPrint() {
        return argMapforPrettyPrint;
    }

    public ArrayList<Span> getSupp1() {
        return supp1;
    }

    public ArrayList<Span> getSupp2() {
        return supp2;
    }

}
