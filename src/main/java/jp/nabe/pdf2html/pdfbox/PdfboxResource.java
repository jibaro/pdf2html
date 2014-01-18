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

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean isUrlEmpty() {
        return StringUtils.isEmpty(url);
    }

    @Override
    public InputStream getInputStream() throws Exception {
        return new ByteArrayInputStream(getData());
    }

    @Override
    public byte[] getData() throws Exception {
        return image.getData();
    }

    @Override
    public String getContentType() {
        return image.getMimeType();
    }

    @Override
    public int getWidth() {
        return image.getWidth();
    }

    @Override
    public int getHeight() {
        return image.getHeight();
    }

    public String getValue() {
        return getUrl();
    }

}
