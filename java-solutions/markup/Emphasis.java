package markup;

import java.util.List;

public class Emphasis extends Element{
    public Emphasis(List<InParagraph> emphasisElements) {
        super("*", "i", emphasisElements);
    }
}