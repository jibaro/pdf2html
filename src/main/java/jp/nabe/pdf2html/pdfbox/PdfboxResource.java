package jp.nabe.pdf2html.pdfbox;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import jp.nabe.pdf2html.Resource;

import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;

public class PdfboxResource implements Resource {

    private final PDXObjectImage image;

    private String url;

    public PdfboxResource(PDXObjectImage image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public InputStream getInputStream() throws Exception {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        image.write2OutputStream(data);
        return new ByteArrayInputStream(data.toByteArray());
    }

    public String getContentType() {
        String suffix = image.getSuffix();
        if ("jpg".equals(suffix)) {
            return "image/jpg";
        }
        if ("png".equals(suffix)) {
            return "image/png";
        }
        if ("tiff".equals(suffix)) {
            return "image/tiff";
        }
        if ("gif".equals(suffix)) {
            return "image/gif";
        }
        return null;
    }

    public String getValue() {
        return getUrl();
    }
}
