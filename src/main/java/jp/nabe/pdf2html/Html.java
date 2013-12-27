package jp.nabe.pdf2html;


public interface Html {

    public String toString(Template template, Resources resources) throws Exception;

    public String getContents(Template template, Resources resources) throws Exception;
}
