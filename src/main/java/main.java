import javax.swing.*;

public class main {
    public static void main(String[] args) {
        // 初始化
        WordTool wordTool = new WordTool();
        wordTool.GenerateList();

        SwingUtilities.invokeLater(() -> {
            WordBook app = new WordBook(wordTool);
            app.show();
        });

    }
}
