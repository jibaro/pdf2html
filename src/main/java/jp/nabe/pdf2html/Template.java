package jp.nabe.pdf2html;

import java.util.List;

public interface Template {

    public String getEncoding();

    public String getHeader(String title, int total);

    public String getContent(int pageNum, List<Text> texts, Resources resources);

    public String getFooter();

}
