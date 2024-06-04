import java.util.Objects;

public class Sentence {
    protected final int score; // range: [-2, 2]
    protected final String text; // non-nullable

    public Sentence(int score, String text) {
        if (score < -2 || score > 2)
            throw new IllegalArgumentException("Invalid score: " + score + " not in range [-2, 2]");
        if (text == null)
            throw new IllegalArgumentException("Invalid text: text field is non-nullable");
        this.score = score;
        this.text = text;
    }

    public int getScore() {
        return score;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sentence)) return false;
        Sentence otherSentence = (Sentence) o;
        return score == otherSentence.score && text.equals(otherSentence.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(score, text);
    }

    @Override
    public String toString() {
        return "Sentence{" +
                "score=" + score +
                ", text='" + text + '\'' +
                '}';
    }
}
