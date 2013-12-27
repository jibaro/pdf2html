package jp.nabe.pdf2html;

public class Text implements Component {

    private final String value;

    public Text(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
