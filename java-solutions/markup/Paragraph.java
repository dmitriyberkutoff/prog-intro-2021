package markup;

import java.util.*;

public class Paragraph implements Markable, BBCodableOfList {
    protected List<InParagraph> listOfEl;

    public Paragraph(List<InParagraph> list){
        listOfEl = list;
    }

    public void toMarkdown(StringBuilder string) {
        for (InParagraph elem : listOfEl) {
            elem.toMarkdown(string);
        }
    }

    public void toBBCode(StringBuilder string) {
        for (InParagraph elem : listOfEl) {
            elem.toBBCode(string);
        }
    }
}