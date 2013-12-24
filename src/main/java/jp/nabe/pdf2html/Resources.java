package jp.nabe.pdf2html;

import java.util.List;

public interface Resources {

    public List<Resource> toList() throws Exception;

    public Resource getResource(String key) throws Exception;
}
