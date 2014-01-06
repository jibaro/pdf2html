package jp.nabe.pdf2html.pdfbox;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import jp.nabe.pdf2html.Component;
import jp.nabe.pdf2html.Html;
import jp.nabe.pdf2html.Resources;
import jp.nabe.pdf2html.Template;
import jp.nabe.pdf2html.Text;
import jp.nabe.pdf2html.parser.Sentence;
import jp.nabe.pdf2html.parser.SentenceComparator;
import jp.nabe.pdf2html.parser.SentenceDetector;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFText2HTML;
import org.apache.pdfbox.util.TextPosition;

public class PdfboxHtml extends PDFText2HTML implements Html {

    private final SentenceDetector detector;
    private final SentenceComparator comparator;

    public PdfboxHtml(PDDocument doc) throws IOException {
        super(null);
        setLineSeparator("");
        setPageSeparator("");
        setWordSeparator("");
        setParagraphStart("");
        setParagraphEnd("");
        setPageStart("");
        setPageEnd("");
        setArticleStart("");
        setArticleEnd("");

        document = doc;
        detector = new SentenceDetector();
        comparator = new SentenceComparator();
    }

    public String getContents(Template template, Resources resources) throws Exception {
        StringWriter writer = new StringWriter();
        writeText(document, writer);

        comparator.setHints(writer.toString());

        List<Component> components = new ArrayList<Component>();
        for (Sentence sentence : detector.detect(writer.toString(), comparator)) {
            Component component = new Text(sentence.getValue());
            components.add(component);
        }
        components.addAll(resources.toList());
        return template.getContent(components.toArray(new Component[0]));
    }

    public String toString(Template template, Resources resources) throws Exception {
        String header = template.getHeader(getTitle());
        String contents = getContents(template, resources);
        String footer = template.getFooter();

        StringBuilder text = new StringBuilder(header)
            .append(contents)
            .append(footer);

        return text.toString();
    }

    @Override
    public void resetEngine() {
        super.resetEngine();
        comparator.reset();
    }

    @Override
    protected void writeHeader() throws IOException {
    }

    @Override
    public void endDocument(PDDocument pdf) throws IOException {
    }

    @Override
    protected void startArticle(boolean isltr) throws IOException {
    }

    @Override
    protected void endArticle() throws IOException {
    }

    @Override
    protected void writeString(String chars) throws IOException {
        output.write(chars);
    }

    @Override
    protected void writeLine(List<TextPosition> line, boolean isRtlDominant, boolean hasRtl) throws IOException {
        float maxFontSize = 0;
        float maxPositionX = 0;
        for (TextPosition position : line) {
            String s = position.getCharacter();
            if (s == null || s.isEmpty()) {
                continue;
            }
            maxFontSize = Math.max(maxFontSize, position.getFontSize());
            maxPositionX = Math.max(maxPositionX, position.getX());
        }
        List<String> normalized = normalize(line, isRtlDominant, hasRtl);
        comparator.addHint(normalize(normalized), calcPriority(maxFontSize, maxPositionX));

        writeLine(normalized, isRtlDominant);
    }

    protected float calcPriority(float fontSize, float positionX) {
        return (fontSize * 100) + (1000 - positionX);
    }

    private String normalize(List<String> line) {
        StringBuilder normalized = new StringBuilder();
        for (String s : line) {
            normalized.append(s);
        }
        return normalized.toString();
    }
}
