package jp.nabe.pdf2html.parser;

public class Sentence {

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
        if (Math.abs(getCenterY() - other.getCenterY()) > 150) {
            return false;
        }
        if (Math.abs(getCenterX() - other.getCenterX()) > 150) {
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
