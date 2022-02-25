package markup;

public class Text implements InParagraph{
    StringBuilder string = new StringBuilder();

    public Text(String string) {
        this.string.append(string);
    }

    public void toMarkdown(StringBuilder string) {
        string.append(this.string);
    }

    public void toBBCode(StringBuilder string) {
        string.append(this.string);
    }
}