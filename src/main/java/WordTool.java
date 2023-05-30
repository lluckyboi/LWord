import java.util.ArrayList;
import java.util.List;

public class WordTool {
    private List<Word> wordList;
    private List<Word> forgetList;
    private int currentIndex;

    public WordTool() {
        wordList = new ArrayList<>();
        forgetList = new ArrayList<>();
        currentIndex = 0;
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
        // todo 基于序号顺序添加单词

    }

    public Word GetNextWord() {
        if (currentIndex < wordList.size()) {
            Word nextWord = wordList.get(currentIndex);
            currentIndex++;
            return nextWord;
        }
        return null;
    }
}


