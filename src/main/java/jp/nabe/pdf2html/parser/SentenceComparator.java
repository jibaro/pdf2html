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

        if (isShortSentence(s1)) {
            if (!isShortSentence(s2)) {
                return 1;
            }
        } else if (isShortSentence(s2)) {
            return -1;
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

    protected boolean isShortSentence(Sentence sentence) {
        return sentence.getCharLength() < SentenceProperty.SHORT_LENGTH;
    }

    protected boolean isLargeFontSentence(Sentence sentence) {
        return sentence.getFontSize() > SentenceProperty.LARGE_FONT;
    }

}
