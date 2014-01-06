package jp.nabe.pdf2html.parser;

import java.util.ArrayList;
import java.util.List;

public class SentenceDetector {

    public Sentence[] detect(String text) throws Exception {
        char[] cs = text.toCharArray();
        StringBuilder buff = new StringBuilder();
        List<Sentence> list = new ArrayList<Sentence>();
        int bracket = 0;
        for (int i = 0; i < cs.length; i++) {
            Character current = cs[i];
            if (isInvalid(current)) {
                continue;
            }
            Character prev = null;
            if (i - 1 > 0) {
                prev = cs[i - 1];
            }
            Character next = null;
            if (i + 1 < cs.length) {
                next = cs[i + 1];
            }

            if (isBracketStart(current)) {
                bracket++;
            } else if (isBracketEnd(current)) {
                bracket--;
            }

            buff.append(escape(current));

            if (isLineEnd(current, prev, next, bracket)) {
                Sentence sentence = new Sentence(buff.toString());
                list.add(sentence);
                buff.setLength(0);
            }
        }
        if (buff.length() > 0) {
            Sentence sentence = new Sentence(buff.toString());
            list.add(sentence);
            buff.setLength(0);
        }

        return list.toArray(new Sentence[0]);
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
