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

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFText2HTML;
import org.apache.pdfbox.util.TextPosition;

public class PdfboxHtml extends PDFText2HTML implements Html {

    private final SentenceDetector detector;

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
    }

    public String getContents(Template template, Resources resources) throws Exception {
        StringWriter writer = new StringWriter();
        writeText(document, writer);

        SentenceComparator comparator = new SentenceComparator();
        comparator.setHints(writer.toString());

        List<Component> components = new ArrayList<Component>();
        for (Sentence sentence : detector.detect(comparator)) {
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
        detector.reset();
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
        Sentence sentence = new Sentence();
        for (TextPosition position : line) {
            String s = position.getCharacter();
            if (StringUtils.isEmpty(s)) {
                continue;
            }

            sentence.append(s)
                .setFontSize(position.getFontSize())
                .setPositionX(position.getX())
                .setPositionY(position.getY());
        }
        detector.add(sentence);

        writeLine(normalize(line, isRtlDominant, hasRtl), isRtlDominant);
    }

}
