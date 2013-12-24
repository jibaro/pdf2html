package jp.nabe.pdf2html;


public interface Page {

    public Resources getResources();

    public Html getHtml();

    public int getNum();

    public void close() throws Exception;
}
