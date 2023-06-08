import org.junit.jupiter.api.Test;

import javax.swing.*;

class mainTest {
    @Test
    void main() {
        // 初始化
        WordTool wordTool = new WordTool();
        wordTool.GenerateList();

        // 绘制GUI
        SwingUtilities.invokeLater(() -> {
            WordBook app = new WordBook(wordTool);
            app.show();
        });
    }
}