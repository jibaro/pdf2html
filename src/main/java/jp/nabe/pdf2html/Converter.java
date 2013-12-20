package jp.nabe.pdf2html;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.Splitter;

public class Converter {

    private final InputStream data;

    private List<Page> pages;

    public Converter(InputStream data) {
        this.data = data;
    }

    public Converter(byte[] data) {
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

    protected void parse() throws Exception {
        pages = new ArrayList<Page>();
        PDDocument doc = null;
        try {
            doc = PDDocument.load(data);
            Splitter splitter = new Splitter();
            List<PDDocument> docList = splitter.split(doc);
            for (int i = 0; i < docList.size(); i++) {
                PDDocument pageDoc = null;
                try {
                    pageDoc = docList.get(i);
                    ByteArrayOutputStream pageData = new ByteArrayOutputStream();
                    pageDoc.save(pageData);
                    Page page = new Page(i + 1, pageData.toByteArray());
                    pages.add(page);
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
