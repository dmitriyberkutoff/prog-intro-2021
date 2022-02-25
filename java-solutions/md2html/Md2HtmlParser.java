package md2html;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Md2HtmlParser {
    private final Source file;
    private int index;
    private String curParagraph;
    final private Map<String, String> closeTags = new HashMap<>();
    final private Map<Character, String> specialSymbols = new HashMap<>();
    final private String[] markupSequences = {"**", "*", "__", "_", "--", "```", "`"};

    public Md2HtmlParser(Source source) {
        this.file = source;

        closeTags.put("**", "</strong>");
        closeTags.put("__", "</strong>");
        closeTags.put("*", "</em>");
        closeTags.put("_", "</em>");
        closeTags.put("--", "</s>");
        closeTags.put("```", "</pre>");
        closeTags.put("`", "</code>");

        specialSymbols.put('&', "&amp;");
        specialSymbols.put('>', "&gt;");
        specialSymbols.put('<', "&lt;");
    }

    private String convertHtmlSymbol(char ch) {
        return specialSymbols.containsKey(ch) ? specialSymbols.get(ch) : Character.toString(ch);
    }

    private String convertMarkdownSymbol() {
        char currentChar = curParagraph.charAt(index);

        if (currentChar == '`' || currentChar == '_' || currentChar == '*') {
            return Character.toString(currentChar);
        } else {
            return ("\\" + convertHtmlSymbol(currentChar));
        }
    }

    private boolean checkForStop(String stopSequence) {
        if (stopSequence == null || index + stopSequence.length() > curParagraph.length()) {
            return false;
        }
        return curParagraph.startsWith(stopSequence, index);
    }

    private String convertBetween() {
        String openTag = getOpenTag();
        StringBuilder parseForClose = new StringBuilder();
        int len = openTag != null ? openTag.length(): 0;
        index += len;
        int begin = index;
        String converted = parseForTag(openTag);
        String expectedTag = convertCloseTag(openTag);
        boolean closeExist = false;

        if (converted.endsWith(expectedTag)) {
            parseForClose.append(expectedTag.charAt(0)).append(expectedTag.substring(2));
            closeExist = true;
        } else {
            parseForClose.append(openTag);
        }

        if (closeExist && openTag.equals("```")) {
            parseForClose.append(curParagraph, begin, index - 3);
            parseForClose.append(expectedTag);
        } else if (openTag.equals("```")) {
            parseForClose.append(parseForClose.append(expectedTag.charAt(0)).append(expectedTag.substring(2)));
            parseForClose.append(curParagraph.substring(begin));
        } else if (!closeExist && len > 1) {
            parseForClose.append(expectedTag.charAt(0)).append(expectedTag.substring(2));
            parseForClose.append(converted);
        } else {
            parseForClose.append(converted);
        }

        index--;

        return parseForClose.toString();
    }

    private String convertCloseTag(String tag) {
        return closeTags.get(tag);
    }

    private String getOpenTag() {
        for (String tag : markupSequences) {
            if (index + tag.length() <= curParagraph.length() &&
                    curParagraph.startsWith(tag, index)) {
                return tag;
            }
        }
        return null;
    }

    private String parseForTag(String openTag) {
        StringBuilder parseForTag = new StringBuilder();
        while (index < curParagraph.length()) {
            String curTag = getOpenTag();
            if (index > 0 && curParagraph.charAt(index - 1) == '\\') {
                parseForTag.append(convertMarkdownSymbol());
            } else if (curTag != null && !curTag.equals(openTag)){
                if (openTag == null || !openTag.equals("```")) {
                    parseForTag.append(convertBetween());
                }
            } else if (checkForStop(openTag)) {
                parseForTag.append(convertCloseTag(openTag));
                index += openTag.length();
                return parseForTag.toString();
            } else if (curParagraph.charAt(index) == '\\') {
                if (index + 1 >= curParagraph.length()) {
                    parseForTag.append('\\');
                }
            } else if (curTag != null) {
                parseForTag.append(convertBetween());
            } else {
                parseForTag.append(convertHtmlSymbol(curParagraph.charAt(index)));
            }
            index++;
        }

        return parseForTag.toString();
    }

    private int parseHeadline() {
        while (index < curParagraph.length() && curParagraph.charAt(index) == '#') {
            index++;
        }

        return Character.isWhitespace(curParagraph.charAt(index)) ? index++ : 0;
    }

    private String parseParagraph() {
        index = 0;
        StringBuilder parsedParagraph = new StringBuilder();

        int headLevel = parseHeadline();

        if (headLevel == 0) {
            parsedParagraph.append("<p>").append(curParagraph, 0, index).append(parseForTag(null)).append("</p>");
        } else {
            parsedParagraph.append("<h").append(headLevel).append(">").append(parseForTag(null)).append(
                    "</h").append(headLevel).append(">");
        }
        return parsedParagraph.toString();
    }

    public String convert(StringBuilder parsed) throws IOException {
        while (!file.nextParagraph().isEmpty()) {
            curParagraph = file.getParagraph();
            parsed.append(parseParagraph()).append('\n');
        }
        return parsed.toString();
    }
}