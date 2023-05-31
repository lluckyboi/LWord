public class main {
    public static void main(String[] args) {
        WordTool wordTool = new WordTool();
        wordTool.GenerateList();
        Word word = wordTool.GetCurrentWord();
        System.out.println("ID:"+word.getID());
        System.out.println("word:"+word.getWord());
        System.out.println("phonetic:"+word.getPhonetic());
        System.out.println("Definition:"+word.getDefinition());
    }
}
