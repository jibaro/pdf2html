package jp.nabe.pdf2html;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class Html {

    private final InputStream data;

    public Html(InputStream data) {
        this.data = data;
    }

    public Html(byte[] data) {
        this(new ByteArrayInputStream(data));
    }

    public String toString(Resources resources) {
        return "";
    }
}
