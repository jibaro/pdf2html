package jp.nabe.pdf2html.parser;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.atilika.kuromoji.Token;
import org.atilika.kuromoji.Tokenizer;

public class SentenceComparator implements Comparator<Sentence> {
    private static final String FEATURE_NOUN = "名詞";
    private static final String FEATURE_VERB = "動詞";

    private final List<Hint> hints = new ArrayList<Hint>();

    public int compare(Sentence s1, Sentence s2) {
        int priority1 = getPriority(s1);
        int priority2 = getPriority(s2);
        return priority2 - priority1;
    }

    public int getPriority(Sentence sentence) {
        String value = sentence.getValue();
        int priority = 0;
        for (Hint hint : hints) {
            if (value.contains(hint.text)) {
                priority += hint.priority;
            }
        }
        return priority;
    }

    public void setHints(String text) {
        Tokenizer tokenizer = Tokenizer.builder().build();
        Map<String, Integer> table = new HashMap<String, Integer>();
        for (Token token : tokenizer.tokenize(text)) {
            String s = token.getSurfaceForm();
            if (isTokenEnabled(token)) {
                if (table.containsKey(s)) {
                    Integer count = table.get(s);
                    table.put(s, count++);
                } else {
                    table.put(s, 1);
                }
            }
        }

        for (String s : table.keySet()) {
            Integer count = table.get(s);
            Hint hint = new Hint(s, count);
            hints.add(hint);
        }
    }

    private boolean isTokenEnabled(Token token) {
        String features = token.getAllFeatures();
        if (features.startsWith(FEATURE_NOUN)) {
            return true;
        }
        if (features.startsWith(FEATURE_VERB)) {
            return true;
        }

        return false;
    }

    public void reset() {
        hints.clear();
    }

    public void addHint(String line, float priority) {
        Hint hint = new Hint(line, priority);
        hints.add(hint);
    }

    private class Hint {
        private String text;
        private float priority;

        private Hint(String text, float priority) {
            this.text = text;
            this.priority = priority;
        }
    }
}
