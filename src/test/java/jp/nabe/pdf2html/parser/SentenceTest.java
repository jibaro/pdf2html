package jp.nabe.pdf2html.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class SentenceTest {

    @BeforeClass
    public static void beforeClass() throws Exception {
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
    }

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

        Collections.shuffle(list);
//        for (int i = 0; i < list.size() - 2; i++) {
//            Sentence a = list.get(i);
//            Sentence b = list.get(i + 1);
//            Sentence c = list.get(i + 2);
//
//            int ab = a.compareTo(b);
//            int ac = a.compareTo(c);
//            int ba = b.compareTo(a);
//            int bc = b.compareTo(c);
//            int ca = c.compareTo(a);
//            int cb = c.compareTo(b);
//
//            String msg = new StringBuilder()
//                .append("a:").append(a.getValue()).append("(").append(a.getCenterX()).append(",").append(a.getCenterY()).append(")")
//                .append(" b:").append(b.getValue()).append("(").append(b.getCenterX()).append(",").append(b.getCenterY()).append(")")
//                .append(" c:").append(c.getValue()).append("(").append(c.getCenterX()).append(",").append(c.getCenterY()).append(")")
//                .append(" ab:").append(ab)
//                .append(" ac:").append(ac)
//                .append(" ba:").append(ba)
//                .append(" bc:").append(bc)
//                .append(" ca:").append(ca)
//                .append(" cb:").append(cb)
//                .toString();
//            assertEquals(msg, ab, ba * -1);
//            assertEquals(msg, ac, ca * -1);
//            assertEquals(msg, bc, cb * -1);
//            assertEquals(msg, (ab > 0 && bc > 0), ac > 0);
//            assertEquals(msg, ab == 0, ac == bc);
//        }
    }
}
