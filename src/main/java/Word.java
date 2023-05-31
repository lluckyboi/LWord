public class Word {
    private  int    ID;
    private  String word;
    private  String phonetic;
    private  String definition;

    public Word(int ID,String word, String phonetic, String definition) {
        this.ID = ID;
        this.word = word;
        this.phonetic = phonetic;
        this.definition = definition;
    }

    public int getID(){
        return  ID;
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
