package markup;

import java.util.List;

public abstract class Element implements InParagraph{
    protected String symbolMark;
    protected String symbolBBC;
    protected List<InParagraph> elements;

    public Element(String wrapMark, String wrapBBC, List<InParagraph> el) {
        this.elements = el;
        this.symbolMark = wrapMark;
        this.symbolBBC = wrapBBC;
    }

    public void toMarkdown(StringBuilder string) {
        string.append(symbolMark);
        for (InParagraph elem : elements) {
            elem.toMarkdown(string);
        }
        string.append(symbolMark);
    }

    public void toBBCode(StringBuilder string) {
        string.append("[");
        string.append(symbolBBC);
        string.append("]");
        for (InParagraph elem : elements) {
            elem.toBBCode(string);
        }
        string.append("[/");
        string.append(symbolBBC);
        string.append("]");
    }
}