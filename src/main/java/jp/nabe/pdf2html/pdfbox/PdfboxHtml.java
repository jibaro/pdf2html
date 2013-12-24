package jp.nabe.pdf2html.pdfbox;

import jp.nabe.pdf2html.Html;
import jp.nabe.pdf2html.Resources;
import jp.nabe.pdf2html.Template;

import org.apache.pdfbox.pdmodel.PDDocument;

public class PdfboxHtml implements Html {

    private final PDDocument doc;

    public PdfboxHtml(PDDocument doc) {
        this.doc = doc;
    }

    public String toString(Template template, Resources resources) {
        return "";
    }
}
