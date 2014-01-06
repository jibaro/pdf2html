package jp.nabe.pdf2html.parser;

public class Sentence {

    private final String value;

    public Sentence(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getValue();
    }

}
