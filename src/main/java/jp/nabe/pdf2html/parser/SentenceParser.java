package jp.nabe.pdf2html.parser;

import java.util.LinkedList;

import org.atilika.kuromoji.Token;
import org.atilika.kuromoji.Tokenizer;

public class SentenceParser {

    public boolean isSentenceCompleted(Sentence sentence) {
        if (sentence.getCharLength() < SentenceProperty.SHORT_LENGTH) {
            return false;
        }

        Tokenizer tokenizer = Tokenizer.builder().build();
        LinkedList<Token> tokens = new LinkedList<Token>(tokenizer.tokenize(sentence.getValue()));

        Token first = tokens.getFirst();
        String firstFeatures = first.getAllFeatures();
        if (!isFirstWord(firstFeatures)) {
            return false;
        }

        Token last = tokens.getLast();
        if (first.equals(last)) {
            return false;
        }
        String lastFeatures = last.getAllFeatures();
        if (!isLastWord(lastFeatures)) {
            return false;
        }

        return true;
    }

    protected boolean isFirstWord(String features) {
        if (features.startsWith("形容詞")) {
            return true;
        }
        if (features.startsWith("名詞")) {
            return true;
        }
        if (features.startsWith("接続詞")) {
            return true;
        }
        if (features.startsWith("連体詞")) {
            return true;
        }
        if (features.startsWith("副詞")) {
            return true;
        }
        if (features.startsWith("接頭詞")) {
            return true;
        }
        if (features.startsWith("感動詞")) {
            return true;
        }
        if (features.startsWith("記号,アルファベット")) {
            return true;
        }
        if (features.startsWith("記号,括弧開")) {
            return true;
        }
        if (features.startsWith("記号,一般")) {
            return true;
        }
        return false;
    }

    protected boolean isLastWord(String features) {
        if (features.startsWith("記号,句点")) {
            return true;
        }
        if (features.startsWith("記号,一般")) {
            return true;
        }
        if (features.startsWith("記号,括弧閉")) {
            return true;
        }
        if (features.startsWith("動詞")) {
            return true;
        }
        if (features.startsWith("助動詞")) {
            return true;
        }
        return false;
    }

}
