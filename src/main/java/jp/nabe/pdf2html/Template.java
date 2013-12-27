package jp.nabe.pdf2html;

public interface Template {

    public String getEncoding();

    public String getHeader(String title, Component... components);

    public String getContent(Component... components);

    public String getFooter(Component... components);

}
