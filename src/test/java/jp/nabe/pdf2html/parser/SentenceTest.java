package jp.nabe.pdf2html.parser;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class SentenceTest {

    @Test
    public void compareTo() throws Exception {
        List<Sentence> list = new ArrayList<Sentence>();
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                Sentence sentence = new Sentence(String.valueOf(x) + String.valueOf(y));
                sentence.setPositionX(x * 70);
                sentence.setPositionY(y * 70);
                list.add(sentence);
            }
        }

        for (int i = 0; i < list.size() - 2; i++) {
            Sentence a = list.get(i);
            Sentence b = list.get(i + 1);
            Sentence c = list.get(i + 2);

            String msg = new StringBuilder()
                .append("a:").append(a.getValue()).append("(").append(a.getCenterX()).append(",").append(a.getCenterY()).append(")")
                .append(" b:").append(b.getValue()).append("(").append(b.getCenterX()).append(",").append(b.getCenterY()).append(")")
                .append(" c:").append(c.getValue()).append("(").append(c.getCenterX()).append(",").append(c.getCenterY()).append(")")
                .toString();
            assertEquals(msg, a.compareTo(b), b.compareTo(a) * -1);
            assertEquals(msg, (a.compareTo(b) > 0 && b.compareTo(c) > 0), a.compareTo(c) > 0);
            assertEquals(msg, a.compareTo(b) == 0, a.compareTo(c) == b.compareTo(c));
        }
    }
}
