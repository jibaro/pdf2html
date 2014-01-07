package jp.nabe.pdf2html.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

public class SentenceDetector {

    private final List<Sentence> original = new ArrayList<Sentence>();

    public void add(Sentence sentence) {
        original.add(sentence);
    }

    public void reset() {
        original.clear();
    }

    public Sentence[] detect() throws Exception {
        Collections.sort(original, new Comparator<Sentence>() {

            public int compare(Sentence s1, Sentence s2) {
                return (int) (s1.getCenterY() - s2.getCenterY());
            }
        });

        List<Sentence> list = new ArrayList<Sentence>();
        int bracket = 0;
        Sentence sentence = null;
        for (int i = 0; i < original.size(); i++) {
            Sentence org = original.get(i);
            char[] cs = org.toString().toCharArray();
            int start = 0;
            int end = cs.length;

            char[] merged = cs;
            if (i - 1 > 0) {
                Sentence orgPrev = original.get(i - 1);
                if (org.near(orgPrev)) {
                    char[] csPrev = orgPrev.toString().toCharArray();
                    merged = ArrayUtils.addAll(csPrev, merged);
                    start = csPrev.length;
                } else if (sentence != null) {
                    sentence.setFontSize(orgPrev.getFontSize());
                    sentence.setEndX(orgPrev.getEndX());
                    sentence.setEndY(orgPrev.getEndY());
                    list.add(sentence);
                    sentence = null;
                }
            }
            if (i + 1 < original.size()) {
                Sentence orgNext = original.get(i + 1);
                if (org.near(orgNext)) {
                    char[] csNext = orgNext.toString().toCharArray();
                    merged = ArrayUtils.addAll(merged, csNext);
                    end = start + cs.length;
                }
            }

            for (int j = start; j < end; j++) {
                Character current = merged[j];
                if (isInvalid(current)) {
                    continue;
                }
                Character prev = null;
                if (j - 1 > 0) {
                    prev = merged[j - 1];
                }
                Character next = null;
                if (j + 1 < merged.length) {
                    next = merged[j + 1];
                }

                if (isBracketStart(current)) {
                    bracket++;
                } else if (isBracketEnd(current)) {
                    bracket--;
                }

                if (sentence == null) {
                    sentence = new Sentence();
                    sentence.setFontSize(org.getFontSize());
                    sentence.setStartX(org.getStartX());
                    sentence.setStartY(org.getStartY());
                }
                sentence.append(escape(current));

                if (isLineEnd(current, prev, next, bracket)) {
                    sentence.setFontSize(org.getFontSize());
                    sentence.setEndX(org.getEndX());
                    sentence.setEndY(org.getEndY());
                    list.add(sentence);
                    sentence = null;
                }
            }
        }
        if (sentence != null) {
            list.add(sentence);
            sentence = null;
        }

        return list.toArray(new Sentence[0]);
    }

    public Sentence[] detect(Comparator<Sentence> comparator) throws Exception {
        Sentence[] list = detect();
        Arrays.sort(list, comparator);
        return list;
    }

    protected String escape(Character c) {
        if (c == null) {
            return "";
        }

        switch (c) {
        case '"':
            return "&quot;";
        case '&':
            return "&amp;";
        case '<':
            return "&lt;";
        case '>':
            return "&gt;";
        default:
            return String.valueOf(c);
        }
    }

    protected boolean isInvalid(Character c) {
        if (c == null) {
            return true;
        }

        switch (c) {
        case '\n': // LF
        case '\r': // CR
        case '\t': // Tab
        case '\u0020': // en space
        case '\u3000': // em space
            return true;
        }
        return false;
    }

    protected boolean isLineEnd(Character current, Character prev, Character next, int bracket) {
        if (bracket > 0) {
            return false;
        }
        if (current == null) {
            return false;
        }

        switch (current) {
        case '\u002E': // en period
        case '\u3002': // ja period
        case '\uFF0E': // em period
            return !(isDigit(prev) || isDigit(next));
        }
        return false;
    }

    protected boolean isDigit(Character c) {
        if (c == null) {
            return false;
        }
        return Character.isDigit(c);
    }

    protected boolean isBracketStart(Character c) {
        if (c == null) {
            return false;
        }

        switch (c) {
        case '\u0028': // (
        case '\uFF08': // em (
        case '\uFE35': // pre (
        case '\u005B': // [
        case '\uFF3B': // em [
        case '\uFE47': // pre [
        case '\u007B': // {
        case '\uFF5B': // em {
        case '\uFE37': // pre {
        case '\u300C': // ja
        case '\uFF62': // em ja
        case '\uFE41': // pre ja
        case '\u300E': // d ja
        case '\uFE43': // pre d ja
            return true;
        }
        return false;
    }

    protected boolean isBracketEnd(Character c) {
        if (c == null) {
            return false;
        }

        switch (c) {
        case '\u0029': // )
        case '\uFF09': // em )
        case '\uFE36': // pre )
        case '\u005D': // ]
        case '\uFF3D': // em ]
        case '\uFE48': // pre ]
        case '\u007D': // }
        case '\uFF5D': // em }
        case '\uFE38': // pre }
        case '\u300D': // ja
        case '\uFF63': // em ja
        case '\uFE42': // pre ja
        case '\u300F': // d ja
        case '\uFE44': // pre d ja
            return true;
        }
        return false;
    }

}
