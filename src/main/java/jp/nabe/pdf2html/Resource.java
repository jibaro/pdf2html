package jp.nabe.pdf2html;

import java.io.InputStream;

public interface Resource {

    public String getUrl();

    public void setUrl(String url);

    public InputStream getInputStream() throws Exception;

    public String getContentType();

}
