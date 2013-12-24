package jp.nabe.pdf2html.pdfbox;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jp.nabe.pdf2html.Converter;
import jp.nabe.pdf2html.Page;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.util.Splitter;

public class PdfboxConverter implements Converter {

    private final InputStream data;

    private final List<Page> pages = new ArrayList<Page>();
    private PDDocument doc;

    public PdfboxConverter(InputStream data) {
        this.data = data;
    }

    public PdfboxConverter(byte[] data) {
        this(new ByteArrayInputStream(data));
    }

    public Page getPage(int num) throws Exception {
        int index = num - 1;
        if (index < 0) {
            return null;
        }

        List<Page> pages = getPages();
        if (pages.size() <= index) {
            return null;
        }

        return pages.get(index);
    }

    public List<Page> getPages() throws Exception {
        if (pages == null) {
            parse();
        }
        return pages;
    }

    @SuppressWarnings("unchecked")
    protected void parse() throws Exception {
        pages.clear();
        doc = null;
        try {
            doc = PDDocument.load(data);
            Splitter splitter = new Splitter();
            List<PDDocument> docList = splitter.split(doc);
            List<PDPage> pageList = doc.getDocumentCatalog().getAllPages();
            for (int i = 0; i < docList.size(); i++) {
                PDDocument pageDoc = null;
                try {
                    pageDoc = docList.get(i);
                    PDPage page = pageList.get(i);

                    ByteArrayOutputStream pageData = new ByteArrayOutputStream();
                    pageDoc.save(pageData);
                    pages.add(new PdfboxPage(i + 1, pageDoc, page));
                } finally {
                    if (pageDoc != null) {
                        pageDoc.close();
                    }
                }
            }
        } finally {
            if (doc != null) {
                doc.close();
            }
        }
    }
}
