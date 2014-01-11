package jp.nabe.pdf2html.parser;

import java.util.ArrayList;
import java.util.List;

public class SentenceSummarizer {

    public Sentence[] summarize(Sentence[] sentences) {
        SentenceParser parser = new SentenceParser();

        List<Sentence> list = new ArrayList<Sentence>();
        for (Sentence sentence : sentences) {
            if (parser.isSentenceCompleted(sentence)) {
                list.add(sentence);
            }
        }

        return list.toArray(new Sentence[0]);
    }
}
