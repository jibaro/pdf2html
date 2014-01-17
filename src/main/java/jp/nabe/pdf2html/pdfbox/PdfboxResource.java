package jp.nabe.pdf2html.pdfbox;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import jp.nabe.pdf2html.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDSimpleImages;

public class PdfboxResource implements Resource {

    private final PDSimpleImages.Image image;

    private String url;

    public PdfboxResource(PDSimpleImages.Image image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUrlEmpty() {
        return StringUtils.isEmpty(url);
    }

    public InputStream getInputStream() throws Exception {
        return new ByteArrayInputStream(getData());
    }

    public byte[] getData() throws Exception {
        return image.getData();
    }

    public String getContentType() {
        return image.getMimeType();
    }

    public int getWidth() {
        return image.getWidth();
    }

    public int getHeight() {
        return image.getHeight();
    }

    public String getValue() {
        return getUrl();
    }

}
