package jp.nabe.pdf2html.pdfbox;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import jp.nabe.pdf2html.Html;
import jp.nabe.pdf2html.Resources;
import jp.nabe.pdf2html.Template;
import jp.nabe.pdf2html.Text;
import jp.nabe.pdf2html.parser.Sentence;
import jp.nabe.pdf2html.parser.SentenceComparator;
import jp.nabe.pdf2html.parser.SentenceDetector;
import jp.nabe.pdf2html.parser.SentenceSummarizer;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.TextPosition;

public class PdfboxHtml extends PDFTextStripper implements Html {

    private final SentenceDetector detector;
    private SentenceSummarizer summarizer;

    public PdfboxHtml(PDDocument doc) throws IOException {
        super();
        setLineSeparator("");
        setPageSeparator("");
        setWordSeparator("");
        setParagraphStart("");
        setParagraphEnd("");
        setPageStart("");
        setPageEnd("");
        setArticleStart("");
        setArticleEnd("");

        document = doc;
        detector = new SentenceDetector();
    }

    public String getContents(Template template, Resources resources) throws Exception {
        Sentence[] sentences = getSentences();
        return getContents(template, resources, sentences);
    }

    public String toString(Template template, Resources resources) throws Exception {
        Sentence[] sentences = getSentences();

        String header = template.getHeader(getTitle(sentences));
        String contents = getContents(template, resources);
        String footer = template.getFooter();

        StringBuilder text = new StringBuilder(header)
            .append(contents)
            .append(footer);

        return text.toString();
    }

    protected Sentence[] getSentences() throws Exception {
        StringWriter writer = new StringWriter();
        writeText(document, writer);

        SentenceComparator comparator = new SentenceComparator();

        return summarize(detector.detect(comparator));
    }

    protected String getTitle(Sentence[] sentences) {
        String titleGuess = document.getDocumentInformation().getTitle();
        if (!StringUtils.isEmpty(titleGuess)) {
            return titleGuess;
        }

        if (sentences == null || sentences.length == 0) {
            return "";
        }
        return sentences[0].getValue();
    }

    protected String getContents(Template template, Resources resources, Sentence[] sentences) throws Exception {
        List<Text> texts = new ArrayList<Text>();
        for (Sentence sentence : sentences) {
            Text text = new Text(sentence.getValue());
            texts.add(text);
        }
        return template.getContent(texts, resources);
    }

    @Override
    public Html setSummarizer(SentenceSummarizer summarizer) {
        this.summarizer = summarizer;
        return this;
    }

    protected Sentence[] summarize(Sentence[] sentences) {
        if (summarizer != null) {
            sentences = summarizer.summarize(sentences);
        }
        return sentences;
    }

    @Override
    public void resetEngine() {
        super.resetEngine();
        detector.reset();
    }

    @Override
    public void endDocument(PDDocument pdf) throws IOException {
    }

    @Override
    protected void startArticle(boolean isltr) throws IOException {
    }

    @Override
    protected void endArticle() throws IOException {
    }

    @Override
    protected void writeString(String chars) throws IOException {
        output.write(chars);
    }

    @Override
    protected void writeLine(List<TextPosition> line, boolean isRtlDominant, boolean hasRtl) throws IOException {
        Sentence sentence = new Sentence();
        for (TextPosition position : line) {
            String s = position.getCharacter();
            if (StringUtils.isEmpty(s)) {
                continue;
            }

            sentence.append(s)
                .setFontSize(position.getFontSizeInPt())
                .setPositionX(position.getX())
                .setPositionY(position.getY());
        }
        detector.add(sentence);

        writeLine(normalize(line, isRtlDominant, hasRtl), isRtlDominant);
    }

}
