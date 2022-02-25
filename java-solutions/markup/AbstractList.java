package markup;

import java.util.List;

public abstract class AbstractList implements BBCodableOfList{
    protected String symbolBBC;
    protected List<ListItem> elements;

    public AbstractList(String wrapBBC, List<ListItem> el) {
        this.elements = el;
        this.symbolBBC = wrapBBC;
    }

    public void toBBCode(StringBuilder string) {
        string.append("[" + symbolBBC + "]");
        for (ListItem elem : elements) {
            elem.toBBCode(string);
        }
        string.append("[/list]");
    }
}