import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class WordToolTest {

    @Test
    void addWord() {
        WordTool wordTool = new WordTool();
        Word word         = new Word(1,"abandon","/əˈbændən/","离弃，遗弃，抛弃;");
        wordTool.addWord(word);
        if (wordTool.GetWordList().get(0).getID()   != 1 )                                         fail();
        if (!Objects.equals(wordTool.GetWordList().get(0).getWord(), "abandon"))                fail();
        if (!Objects.equals(wordTool.GetWordList().get(0).getPhonetic(), "/əˈbændən/"))         fail();
        if (!Objects.equals(wordTool.GetWordList().get(0).getDefinition(), "离弃，遗弃，抛弃;"))   fail();
    }

    @Test
    void removeWord() {
        WordTool wordTool = new WordTool();
        Word word         = new Word(1,"abandon","/əˈbændən/","离弃，遗弃，抛弃;");
        wordTool.addWord(word);
        wordTool.removeWord(word);
        if(wordTool.getWordCount()!=0) fail();
    }

    @Test
    void getWordCount() {
    }

    @Test
    void forgetWord() {
    }

    @Test
    void generateList() {
    }

    @Test
    void getNextWord() {
    }

    @Test
    void getCurrentWord() {
    }

    @Test
    void getWordByID() {
    }

    @Test
    void favWord() {
    }

    @Test
    void cancelFav() {
    }

    @Test
    void getFavListSize() {
    }

    @Test
    void getNextFav() {
    }

    @Test
    void resetFavIdx() {
    }

    @Test
    void isFav() {
    }

    @Test
    void getFavList() {
    }
}