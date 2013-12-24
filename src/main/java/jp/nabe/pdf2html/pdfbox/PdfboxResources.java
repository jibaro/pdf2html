package jp.nabe.pdf2html.pdfbox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.nabe.pdf2html.Resource;
import jp.nabe.pdf2html.Resources;

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;

public class PdfboxResources implements Resources {

    private final PDPage page;

    private final Map<String, Resource> map = new HashMap<String, Resource>();

    public PdfboxResources(PDPage page) {
        this.page = page;
    }

    public List<Resource> toList() throws Exception {
        if (map.isEmpty()) {
            parse();
        }
        return new ArrayList<Resource>(map.values());
    }

    public Resource getResource(String key) throws Exception {
        if (map.isEmpty()) {
            parse();
        }
        return map.get(key);
    }

    protected void parse() throws Exception {
        map.clear();

        PDResources resources = page.getResources();
        Map<String, PDXObjectImage> images = resources.getImages();
        for (String key : images.keySet()) {
            PDXObjectImage image = images.get(key);

            PdfboxResource resource = new PdfboxResource(image);
            map.put(key, resource);
        }
    }
}
