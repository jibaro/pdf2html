package jp.nabe.pdf2html.pdfbox;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import jp.nabe.pdf2html.Resource;

import org.apache.commons.lang3.StringUtils;
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

    public boolean isUrlEmpty() {
        return StringUtils.isEmpty(url);
    }

    public InputStream getInputStream() throws Exception {
        return new ByteArrayInputStream(getData());
    }

    public byte[] getData() throws Exception {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        image.write2OutputStream(data);
        return data.toByteArray();
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
