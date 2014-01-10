package jp.nabe.pdf2html.parser;

import java.util.Comparator;
import java.util.LinkedList;

import org.atilika.kuromoji.Token;
import org.atilika.kuromoji.Tokenizer;

public class SentenceComparator implements Comparator<Sentence> {

    public int compare(Sentence s1, Sentence s2) {
        if (s1 == null) {
            if (s2 == null) {
                return 0;
            } else {
                return 1;
            }
        } else if (s2 == null) {
            return -1;
        }
        if (s1.equals(s2)) {
            return 0;
        }

        if (isSentenceCompleted(s1)) {
            if (!isSentenceCompleted(s2)) {
                return -1;
            }
        } else if (isSentenceCompleted(s2)) {
            return 1;
        }

        if (s1.near(s2)) {
            if (isLargeFontSentence(s1)) {
                if (!isLargeFontSentence(s2)) {
                    return -1;
                }
            } else if (isLargeFontSentence(s2)) {
                return 1;
            }
        }

        return s1.compareTo(s2);
    }

    protected boolean isLargeFontSentence(Sentence sentence) {
        return sentence.getFontSize() > SentenceProperty.LARGE_FONT;
    }

    protected boolean isSentenceCompleted(Sentence sentence) {
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
