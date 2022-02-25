package markup;

import java.util.List;

public class Strong extends Element{
    public Strong(List<InParagraph> emphasisElements) { super("__", "b", emphasisElements); }
}