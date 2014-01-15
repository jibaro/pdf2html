package jp.nabe.pdf2html;

import java.util.List;

public interface Converter {

    public Page getPage(int num) throws Exception;

    public List<Page> getPages() throws Exception;

    public String getTitle() throws Exception;

    public void close() throws Exception;
}
