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
            char c = cs[i];
            if (isInvalid(c)) {
                continue;
            }
            buff.append(escape(c));

            if (isLineEnd(c)) {
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

    protected boolean isInvalid(char c) {
        switch (c) {
        case '\n':
        case '\r':
        case '\t':
        case '\u0020':
        case '\u3000':
            return true;
        }
        return false;
    }

    protected boolean isLineEnd(char c) {
        switch (c) {
        case '\u3001':
        case '\u3002':
        case '\u002E':
        case '\uFF0E':
            return true;
        }
        return false;
    }

}
