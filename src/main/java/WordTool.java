import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class WordTool {
    private List<Word> wordList;
    private List<Word> forgetList;
    private final Object[][] WordData;
    private int currentIndex;
    private int relIndex;

    public WordTool() {
        wordList   = new ArrayList<>();
        forgetList = new ArrayList<>();
        WordData   = new data().Data;
        relIndex   = 0;
        try {
            currentIndex = new YamlReader().Idx;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addWord(Word word) {
        wordList.add(word);
    }

    public void removeWord(Word word) {
        wordList.remove(word);
    }

    public int getWordCount() {
        return wordList.size();
    }

    public void ForgetWord(Word word) {
        forgetList.add(word);
    }

    // GenerateList 生成新的列表
    public void GenerateList() {
        // 清空旧列表
        wordList.clear();
        // 先处理忘记列表
        wordList.addAll(forgetList);
        // 清空忘记列表
        forgetList.clear();

        int addCount =  wordList.size();
        // 基于序号顺序添加单词
        for (int i = 0; i < 10 - addCount; i++) {
            // 边界检查
            if(currentIndex+i> WordData.length) break;
            Word newWord = new Word(currentIndex+i,
                    WordData[currentIndex+i][1].toString(),
                    WordData[currentIndex+i][2].toString(),
                    WordData[currentIndex+i][3].toString());
            wordList.add(newWord);
        }
    }

    public Word GetNextWord() {
        if (relIndex < wordList.size()) {
            Word nextWord = wordList.get(relIndex);
            relIndex++;
            currentIndex = nextWord.getID();
            return nextWord;
        }
        return null;
    }

    public Word GetCurrentWord() {
        // wordList从0开始
        return wordList.get(relIndex);
    }
}


