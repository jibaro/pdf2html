package jp.nabe.pdf2html;

import java.io.InputStream;

public interface Resource extends Component {

    public String getUrl();

    public void setUrl(String url);

    public InputStream getInputStream() throws Exception;

    public String getContentType();

}
