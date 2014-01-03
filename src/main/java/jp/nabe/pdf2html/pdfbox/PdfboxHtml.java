package jp.nabe.pdf2html.pdfbox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.nabe.pdf2html.Component;
import jp.nabe.pdf2html.Html;
import jp.nabe.pdf2html.Resources;
import jp.nabe.pdf2html.Template;

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
        TextWriter writer = new TextWriter();
        writeText(document, writer);

        List<Component> components = new ArrayList<Component>();
        components.addAll(writer.toList());
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
