package jp.nabe.pdf2html.parser;

import java.util.Comparator;

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

        SentenceParser parser = new SentenceParser();
        return parser.isSentenceCompleted(sentence);
    }

}
