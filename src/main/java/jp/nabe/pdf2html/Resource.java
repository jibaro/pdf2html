package jp.nabe.pdf2html;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class Resource {

    private final InputStream data;
    private final String suffix;

    private String url;

    public Resource(InputStream data, String suffix) {
        this.suffix = suffix;
        this.data = data;
    }

    public Resource(byte[] data, String suffix) {
        this(new ByteArrayInputStream(data), suffix);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public InputStream getInputStream() {
        return data;
    }

    public String getContentType() {
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
}
