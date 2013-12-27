package jp.nabe.pdf2html;


public interface Page {

    public Resources getResources();

    public Html getHtml() throws Exception;

    public int getNum();

    public void close() throws Exception;
}
