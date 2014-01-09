package jp.nabe.pdf2html.parser;

import java.util.ArrayList;
import java.util.List;

public class Sentence implements Comparable<Sentence> {

    private final StringBuilder value = new StringBuilder();

    private float fontSize = -1f;
    private float startX = -1f;
    private float startY = -1f;
    private float endX = -1f;
    private float endY = -1f;

    public Sentence() {
    }

    public Sentence(String value) {
        this.value.append(value);
    }

    public String getValue() {
        return value.toString();
    }

    @Override
    public String toString() {
        return getValue();
    }

    @Override
    public int hashCode() {
        return getValue().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return true;
        }
        if (!(obj instanceof Sentence)) {
            return false;
        }
        Sentence other = (Sentence) obj;
        return hashCode() == other.hashCode();
    }

    public char[] toCharArray() {
        return getValue().toCharArray();
    }

    public int getCharLength() {
        return toCharArray().length;
    }

    public List<Character> toCharList() {
        char[] chars = toCharArray();
        List<Character> list = new ArrayList<Character>(chars.length);
        for (char c : chars) {
            list.add(c);
        }
        return list;
    }

    public int compareTo(Sentence other) {
        if (other == null) {
            return 0;
        }
        if (other.equals(this)) {
            return 0;
        }

        if (near(other)) {
            return 0;
        }

        if (getCenterY() != other.getCenterY()) {
            return (int) (getCenterY() - other.getCenterY());
        }
        if (getCenterX() != other.getCenterX()) {
            return (int) (getCenterX() - other.getCenterX());
        }
        return 0;
    }

    public float getCenterX() {
        return startX + ((endX - startX) / 2);
    }

    public float getCenterY() {
        return startY + ((endY - startY) / 2);
    }

    public boolean near(Sentence other) {
        if (other == null) {
            return false;
        }
        float distance = SentenceProperty.NEAR_DISTANCE;
        if (Math.abs(getCenterY() - other.getCenterY()) > distance) {
            return false;
        }
        if (Math.abs(getCenterX() - other.getCenterX()) > distance) {
            return false;
        }
        return true;
    }

    public Sentence append(String value) {
        this.value.append(value);
        return this;
    }

    public float getFontSize() {
        return fontSize;
    }

    public Sentence setFontSize(float fontSize) {
        if (this.fontSize < 0) {
            this.fontSize = fontSize;
        } else {
            this.fontSize = Math.max(this.fontSize, fontSize);
        }
        return this;
    }

    public Sentence setPositionX(float positionX) {
        if (startX < 0) {
            startX = positionX;
        } else {
            startX = Math.min(startX, positionX);
        }
        if (endX < 0) {
            endX = positionX;
        } else {
            endX = Math.max(endX, positionX);
        }

        return this;
    }

    public Sentence setPositionY(float positionY) {
        if (startY < 0) {
            startY = positionY;
        } else {
            startY = Math.min(startY, positionY);
        }
        if (endY < 0) {
            endY = positionY;
        } else {
            endY = Math.max(endY, positionY);
        }

        return this;
    }

    public float getStartX() {
        return startX;
    }

    public void setStartX(float startX) {
        this.startX = startX;
    }

    public float getStartY() {
        return startY;
    }

    public void setStartY(float startY) {
        this.startY = startY;
    }

    public float getEndX() {
        return endX;
    }

    public void setEndX(float endX) {
        this.endX = endX;
    }

    public float getEndY() {
        return endY;
    }

    public void setEndY(float endY) {
        this.endY = endY;
    }

}
