package jp.nabe.pdf2html.pdfbox;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import jp.nabe.pdf2html.Text;

public class TextWriter extends Writer {

    private final StringBuilder text = new StringBuilder();

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        for (int i = off; i < (len + off); i++) {
            char c = cbuf[i];
            text.append(c);
        }
    }

    @Override
    public void flush() throws IOException {
    }

    @Override
    public void close() throws IOException {
    }

    public List<Text> toList() {
        char[] cs = text.toString().toCharArray();
        StringBuilder buff = new StringBuilder();
        List<Text> list = new ArrayList<Text>();
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

            buff.append(escape(current));

            if (isLineEnd(current, prev, next)) {
                Text text = new Text(buff.toString());
                list.add(text);
                buff.setLength(0);
            }
        }
        if (buff.length() > 0) {
            Text text = new Text(buff.toString());
            list.add(text);
            buff.setLength(0);
        }

        return list;
    }

    protected String escape(char c) {
        switch (c) {
        case 34:
            return "&quot;";
        case 38:
            return "&amp;";
        case 60:
            return "&lt;";
        case 62:
            return "&gt;";
        default:
            return String.valueOf(c);
        }
    }

    protected boolean isInvalid(Character current) {
        switch (current) {
        case '\n': // LF
        case '\r': // CR
        case '\t': // Tab
        case '\u0020': // en space
        case '\u3000': // em space
            return true;
        }
        return false;
    }

    protected boolean isLineEnd(Character current, Character prev, Character next) {
        switch (current) {
        case '\u002E': // en period
        case '\u3002': // em period
        case '\uFF0E': // en large period
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
}
