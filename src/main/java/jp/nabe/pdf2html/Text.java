package jp.nabe.pdf2html;

public class Text {

    private final String value;

    public Text(String value) {
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
