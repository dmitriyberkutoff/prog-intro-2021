package markup;

import java.util.*;

public class ListItem implements BBCodable {
    public List<BBCodableOfList> listOfItem;

    public ListItem(List<BBCodableOfList> list){
        listOfItem = list;
    }

    public void toBBCode(StringBuilder string) {
        string.append("[*]");
        for (BBCodableOfList elem : listOfItem) {
            elem.toBBCode(string);
        }
    }
}