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
import jp.nabe.pdf2html.parser.SentenceDetector;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFText2HTML;

public class PdfboxHtml extends PDFText2HTML implements Html {

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

        this.document = doc;
    }

    public String getContents(Template template, Resources resources) throws Exception {
        StringWriter writer = new StringWriter();
        writeText(document, writer);

        SentenceDetector detector = new SentenceDetector();

        List<Component> components = new ArrayList<Component>();
        for (Sentence sentence : detector.detect(writer.toString())) {
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

}
