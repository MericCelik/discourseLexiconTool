package core;

/**
 * Created by Murathan on 08-Nov-16.
 */
public class Span {

    private String text;
    private int beg;
    private int end;
    private String belongsTo;

    public Span(String text, int beg, String belongsTo) {
        this.text = text;
        this.beg = beg;
        this.belongsTo = belongsTo;
    }

    public Span(String text, int beg, int end, String belongsTo) {
        this.text = text;
        this.beg = beg;
        this.end = end;
        this.belongsTo = belongsTo;
    }

    public String getBelongsTo() {
        return belongsTo;
    }

    public void setBelongsTo(String belongsTo) {
        this.belongsTo = belongsTo;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getBeg() {
        return beg;
    }

    public void setBeg(int beg) {
        this.beg = beg;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String toString()
    {
        return this.text;
    }
}
