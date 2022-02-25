package markup;

import java.util.List;

public class OrderedList extends AbstractList{
    public OrderedList(List<ListItem> orderedElements) {
        super("list=1", orderedElements);
    }
}