package jp.nabe.pdf2html.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;

public class SentenceDetector {

    private final List<Sentence> original = new ArrayList<Sentence>();

    public void add(Sentence sentence) {
        original.add(sentence);
    }

    public void reset() {
        original.clear();
    }

    public Sentence[] detect() throws Exception {
        Collections.sort(original);

        List<Sentence> list = new ArrayList<Sentence>();
        int bracket = 0;
        Sentence sentence = null;
        for (int i = 0; i < original.size(); i++) {
            // merge prev, current and next character
            ListIterator<Sentence> sectionIt = original.listIterator(i);
            Sentence prevSentence = null;
            if (sectionIt.hasPrevious()) {
                prevSentence = original.get(sectionIt.previousIndex());
            }
            Sentence currentSentence = sectionIt.next();
            List<Character> charList = currentSentence.toCharList();
            int start = 0;
            int end = currentSentence.getCharLength();
            if (prevSentence != null) {
                if (currentSentence.near(prevSentence)) {
                    charList = prevSentence.toCharList();
                    charList.addAll(currentSentence.toCharList());
                    start = prevSentence.getCharLength();
                } else if (sentence != null) {
                    sentence.setFontSize(prevSentence.getFontSize());
                    sentence.setEndX(prevSentence.getEndX());
                    sentence.setEndY(prevSentence.getEndY());
                    list.add(sentence);
                    sentence = null;
                }
            }
            if (sectionIt.hasNext()) {
                Sentence nextSentence = sectionIt.next();
                if (currentSentence.near(nextSentence)) {
                    charList.addAll(nextSentence.toCharList());
                    end = start + currentSentence.getCharLength();
                }
            } else if (sentence != null) {
                sentence.setFontSize(currentSentence.getFontSize());
                sentence.setEndX(currentSentence.getEndX());
                sentence.setEndY(currentSentence.getEndY());
                list.add(sentence);
                sentence = null;
            }

            // check character
            for (int j = start; j < end; j++) {
                ListIterator<Character> charIt = charList.listIterator(j);
                Character prevChar = null;
                if (charIt.hasPrevious()) {
                    prevChar = charList.get(charIt.previousIndex());
                }
                Character currentChar = charIt.next();
                if (isInvalid(currentChar)) {
                    continue;
                }
                Character nextChar = null;
                if (charIt.hasNext()) {
                    nextChar = charIt.next();
                }

                if (isBracketStart(currentChar)) {
                    bracket++;
                } else if (isBracketEnd(currentChar)) {
                    bracket--;
                }

                if (sentence == null) {
                    sentence = new Sentence();
                    sentence.setFontSize(currentSentence.getFontSize());
                    sentence.setStartX(currentSentence.getStartX());
                    sentence.setStartY(currentSentence.getStartY());
                }
                sentence.append(escape(currentChar));

                if (isLineEnd(currentChar, prevChar, nextChar, bracket)) {
                    sentence.setFontSize(currentSentence.getFontSize());
                    sentence.setEndX(currentSentence.getEndX());
                    sentence.setEndY(currentSentence.getEndY());
                    list.add(sentence);
                    sentence = null;
                }
            }
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
