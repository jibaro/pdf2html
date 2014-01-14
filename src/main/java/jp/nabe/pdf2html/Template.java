package jp.nabe.pdf2html;

import java.util.List;

public interface Template {

    public String getEncoding();

    public String getHeader(String title);

    public String getContent(List<Text> texts, Resources resources);

    public String getFooter();

}
