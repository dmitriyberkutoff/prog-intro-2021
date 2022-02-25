package markup;

import java.util.List;

public class Strikeout extends Element{
    public Strikeout(List<InParagraph> emphasisElements) {
        super("~", "s", emphasisElements);
    }
}