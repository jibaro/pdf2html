package jp.nabe.pdf2html;

import jp.nabe.pdf2html.parser.Sentence;
import jp.nabe.pdf2html.parser.SentenceSummarizer;


public interface Html {

    public String toString(Template template, Resources resources) throws Exception;

    public String getContents(int pageNum, Template template, Resources resources) throws Exception;

    public Html setSummarizer(SentenceSummarizer summarizer);

    public String getTitle() throws Exception;

    Sentence[] getSentences() throws Exception;
}
