package jp.nabe.pdf2html;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Resources {

    private final InputStream data;

    private final Map<String, Resource> map = new HashMap<String, Resource>();

    public Resources(InputStream data) {
        this.data = data;
    }

    public Resources(byte[] data) {
        this(new ByteArrayInputStream(data));
    }

    public List<Resource> toList() {
        if (map.isEmpty()) {
            parse();
        }
        return new ArrayList<Resource>(map.values());
    }

    public Resource getResource(String key) {
        if (map.isEmpty()) {
            parse();
        }
        return map.get(key);
    }

    protected void parse() {
        
    }
}
