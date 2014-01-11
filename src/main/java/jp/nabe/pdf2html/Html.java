package jp.nabe.pdf2html;

import jp.nabe.pdf2html.parser.SentenceSummarizer;


public interface Html {

    public String toString(Template template, Resources resources) throws Exception;

    public String getContents(Template template, Resources resources) throws Exception;

    public Html setSummarizer(SentenceSummarizer summarizer);
}
