package jp.nabe.pdf2html.parser;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class SentenceComparatorTest {

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

        SentenceComparator comparator = new SentenceComparator();
        for (int i = 0; i < list.size() - 2; i++) {
            Sentence a = list.get(i);
            Sentence b = list.get(i + 1);
            Sentence c = list.get(i + 2);

            int ab = comparator.compare(a, b);
            int ac = comparator.compare(a, c);
            int ba = comparator.compare(b, a);
            int bc = comparator.compare(b, c);
            int ca = comparator.compare(c, a);
            int cb = comparator.compare(c, b);

            String msg = new StringBuilder()
                .append("a:").append(a.getValue()).append("(").append(a.getCenterX()).append(",").append(a.getCenterY()).append(")")
                .append(" b:").append(b.getValue()).append("(").append(b.getCenterX()).append(",").append(b.getCenterY()).append(")")
                .append(" c:").append(c.getValue()).append("(").append(c.getCenterX()).append(",").append(c.getCenterY()).append(")")
                .append(" ab:").append(ab)
                .append(" ac:").append(ac)
                .append(" ba:").append(ba)
                .append(" bc:").append(bc)
                .append(" ca:").append(ca)
                .append(" cb:").append(cb)
                .toString();
            assertEquals(msg, ab, ba * -1);
            assertEquals(msg, ac, ca * -1);
            assertEquals(msg, bc, cb * -1);
            if ((ab > 0 && bc > 0)) {
                assertTrue(msg, ac > 0);
            }
            if (ab == 0) {
                assertEquals(msg, ac, bc);
            }
        }
    }
}
