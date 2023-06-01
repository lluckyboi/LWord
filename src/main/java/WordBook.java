import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WordBook {
    private JFrame frame;
    private JLabel wordLabel;
    private JLabel phoLabel;
    private JLabel defLabel;
    private JButton nextButton;
    private JButton forgetButton;

    private WordTool wordTool;

    public WordBook(WordTool cWordTool) {
        // 初始化 WordTool 对象
        wordTool = cWordTool;

        // 创建主窗口
        frame = new JFrame("Word Book");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // 设置布局管理器为边界布局
        frame.setLayout(new BorderLayout());

        showWord();
        showButton();
    }

    private void updateWordLabel() {
        Word nextWord = wordTool.GetNextWord();
        if (nextWord != null) {
            setWord(wordLabel,nextWord);
            setPho(phoLabel,nextWord);
            setDef(defLabel,nextWord);
        } else {
            wordTool.GenerateList();
            if(wordTool.getWordCount() == 0) wordLabel.setText("No words");
        }
    }

    private void showNextWord() {
        updateWordLabel();
    }

    private void forgetWord(Word word) {
        wordTool.ForgetWord(word);
    }

    private void forgetCurrentWord() {
        forgetWord(wordTool.GetCurrentWord());
    }

    private void setWord(JLabel label, Word word){
        label.setText(word.getWord());
    }

    private void setPho(JLabel label , Word word) { label.setText(word.getPhonetic().toString());}

    private void setDef(JLabel label , Word word) { label.setText("<html><div style='width: 250px;text-align: center;'>"+word.getDefinition().toString()+"</div></html>");}

    private void showWord(){
        // Grid分布
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 0, 0, 0);

        // 创建单词面板
        JPanel wordPanel = new JPanel();
        wordPanel.setLayout(new GridLayout(3,1));

        // 创建单词标签
        wordLabel = new JLabel();
        wordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        wordLabel.setFont(new Font("Arial", Font.BOLD, 36));

        // 音标标签
        phoLabel = new JLabel();
        phoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        phoLabel.setFont(new Font("Arial", Font.BOLD, 24));

        //释义标签
        defLabel = new JLabel();
        defLabel.setHorizontalAlignment(SwingConstants.CENTER);
        defLabel.setFont(new Font("正楷", Font.BOLD, 13));

        // 设置文本
        setWord(wordLabel, wordTool.GetCurrentWord());
        setPho(phoLabel, wordTool.GetCurrentWord());
        setDef(defLabel, wordTool.GetCurrentWord());

        // Label加入面板
        wordPanel.add(wordLabel,gbc);
        gbc.gridy = 1;
        wordPanel.add(phoLabel,gbc);
        gbc.gridy = 2;
        wordPanel.add(defLabel,gbc);


        frame.add(wordPanel);
    }

    private void showButton(){
        // 记住按钮
        nextButton = new JButton("认识");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showNextWord();
            }
        });

        // 忘记按钮
        forgetButton = new JButton("忘记了");
        forgetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { forgetCurrentWord(); showNextWord(); }
        });

        // 创建按钮面板
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(nextButton);
        buttonPanel.add(forgetButton);

        // 加入主窗口
        frame.add(buttonPanel, BorderLayout.SOUTH);
    }

    public void show() {
        // 显示主窗口
        frame.setVisible(true);
    }

}
