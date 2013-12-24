package jp.nabe.pdf2html.pdfbox;

import jp.nabe.pdf2html.Page;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

public class PdfboxPage implements Page {

    private final PDDocument doc;
    private final PDPage page;

    private int num;
    private PdfboxResources resources;
    private PdfboxHtml html;

    public PdfboxPage(int num, PDDocument doc, PDPage page) {
        this.num = num;
        this.doc = doc;
        this.page = page;
    }

    public PdfboxResources getResources() {
        if (resources == null) {
            resources = new PdfboxResources(page);
        }
        return resources;
    }

    public PdfboxHtml getHtml() {
        if (html == null) {
            html = new PdfboxHtml(doc);
        }
        return html;
    }

    public int getNum() {
        return num;
    }

    public void close() throws Exception {
        if (doc != null) {
            doc.close();
        }
    }
}
