import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class WordTool {
    private List<Word> wordList;
    private List<Word> forgetList;
    private List<Integer> favList;
    private final Object[][] WordData;
    private int currentIndex;
    private int relIndex;
    private int listNum;
    private int favIdx;

    public WordTool() {
        wordList   = new ArrayList<>();
        forgetList = new ArrayList<>();
        WordData   = new Data().Data;
        relIndex   = 0;
        listNum    = 10;
        favIdx     = 0;

        try {
            currentIndex = new YamlReader().Idx;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            favList    = new YamlReader().FavList;
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
        // relIndex归零
        relIndex = 0;

        int addCount =  wordList.size();
        // 基于序号顺序添加单词
        for (int i = 0; i < listNum - addCount; i++) {
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
        if (relIndex < wordList.size()-1) {
            relIndex++;
            Word nextWord = wordList.get(relIndex);
            currentIndex = nextWord.getID();
            return nextWord;
        }
        return null;
    }

    public Word GetCurrentWord() {
        // wordList从0开始
        return wordList.get(relIndex);
    }

    // 通过序号获取单词
    public Word GetWordByID(int ID) {
    // System.out.println(WordData[ID][1].toString()+WordData[ID][2].toString()+WordData[ID][3].toString());
       return new Word(ID,WordData[ID][1].toString(),WordData[ID][2].toString(),WordData[ID][3].toString());
    }

    // 收藏当前单词
    public void FavWord() {
        favList.add(GetCurrentWord().getID());
    }

    // 取消收藏
    public void CancelFav() {
        favList.remove((Integer) GetCurrentWord().getID());
        favIdx--;
    }

    // 收藏列表大小
    public int GetFavListSize() { return favList.size(); }

    // 获取下一个收藏的单词
    public Word GetNextFav() {
        if (favIdx >= favList.size()) return null;
        return GetWordByID(favList.get(favIdx++));
    }

    // 重置收藏计数器
    public void ResetFavIdx() { favIdx = 0; }

    // 检查当前单词是否被收藏
    public boolean isFav(){
        return favList.contains(GetCurrentWord().getID());
    }

    // 获取收藏列表
    public List<Integer> GetFavList() {
        return favList;
    }
}


