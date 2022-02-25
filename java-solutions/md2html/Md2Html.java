package md2html;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Md2Html {
    public static void main(String[] args) throws IOException {
        Source files = new Source(args[0], args[1]);
        Md2HtmlParser parser = new Md2HtmlParser(files);
        files.write(parser.convert(new StringBuilder()));
        files.close();
    }
}

class Source {
    private BufferedReader in;
    private Writer out;
    private String line = "";
    private StringBuilder paragraph = new StringBuilder();

    public Source(final String input, final String output) {
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(input), StandardCharsets.UTF_8));
            try {
                out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output), StandardCharsets.UTF_8));
            } catch (IOException e) {
                System.out.println("Can not write file");
            }
        } catch (IOException e) {
            System.out.println("Can not read file");
        }
    }

    public String getParagraph() {
        return paragraph.toString();
    }

    public String getLine() {
        return line;
    }

    private String nextLine() throws IOException {
        return line = in.readLine();
    }

    public String nextParagraph() throws IOException {
        if (line == null) {
            return "";
        }

        while (line != null && line.isEmpty()) {
            line = nextLine();
        }

        paragraph = new StringBuilder();
        paragraph.append(line);

        while (nextLine() != null && !getLine().isEmpty()) {
            paragraph.append('\n');
            paragraph.append(getLine());
        }

        while (line != null && line.isEmpty()) {
            nextLine();
        }

        return paragraph.toString();
    }

    public void write(final String out) throws IOException {
        this.out.write(out);
    }

    public void close() throws IOException {
        in.close();
        out.close();
    }
}
