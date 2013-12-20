package jp.nabe.pdf2html;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class Page {

    private final InputStream data;

    private int num;
    private Resources resources;
    private Html html;

    public Page(int num, InputStream data) {
        this.num = num;
        this.data = data;
    }

    public Page(int num, byte[] data) {
        this(num, new ByteArrayInputStream(data));
    }

    public Resources getResources() {
        if (resources == null) {
            resources = new Resources(data);
        }
        return resources;
    }

    public Html getHtml() {
        if (html == null) {
            html = new Html(data);
        }
        return null;
    }

    public int getNum() {
        return num;
    }
}
