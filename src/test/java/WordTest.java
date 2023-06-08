import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordTest {

    @Test
    void getID() {
            Word word = new Word(1,"abandon","/əˈbændən/","离弃，遗弃，抛弃;");
            if(word.getID()!= 1){
                fail();
            }
    }

    @Test
    void getWord() {
        Word word = new Word(1,"abandon","/əˈbændən/","离弃，遗弃，抛弃;");
        if(word.getWord()!= "abandon"){
            fail();
        }
    }

    @Test
    void getPhonetic() {
        Word word = new Word(1,"abandon","/əˈbændən/","离弃，遗弃，抛弃;");
        if(word.getPhonetic()!= "/əˈbændən/"){
            fail();
        }
    }

    @Test
    void getDefinition() {
        Word word = new Word(1,"abandon","/əˈbændən/","离弃，遗弃，抛弃;");
        if(word.getDefinition() != "离弃，遗弃，抛弃;"){
            fail();
        }
    }
}