public class Word {
    private String word;
    private String phonetic;
    private String definition;

    public Word(String word, String phonetic, String definition) {
        this.word = word;
        this.phonetic = phonetic;
        this.definition = definition;
    }

    public String getWord() {
        return word;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public String getDefinition() {
        return definition;
    }
}
