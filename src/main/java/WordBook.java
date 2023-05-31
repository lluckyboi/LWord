import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WordBook {
    private JFrame frame;
    private JLabel wordLabel;
    private JButton nextButton;

    private WordTool wordTool;

    public WordBook(WordTool cWordTool) {
        // 初始化 WordTool 对象
        wordTool = cWordTool;

        // 创建主窗口
        frame = new JFrame("Word Book");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 创建单词标签
        wordLabel = new JLabel();
        wordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        wordLabel.setFont(new Font("Arial", Font.BOLD, 24));
        updateWordLabel();

        // 创建下一个按钮
        nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showNextWord();
            }
        });

        // 创建按钮面板
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(nextButton);

        // 设置布局管理器为边界布局
        frame.setLayout(new BorderLayout());

        // 将单词标签和按钮面板添加到主窗口
        frame.add(wordLabel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void updateWordLabel() {
        Word nextWord = wordTool.GetNextWord();
        if (nextWord != null) {
            wordLabel.setText(nextWord.getWord());
        } else {
            wordLabel.setText("No words");
        }
    }

    private void showNextWord() {
        updateWordLabel();
    }

    public void show() {
        // 显示主窗口
        frame.setVisible(true);
    }

}
