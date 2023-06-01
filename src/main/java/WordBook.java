import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class WordBook {
    private JFrame frame;
    private JLabel wordLabel;
    private JLabel phoLabel;
    private JLabel defLabel;
    private JButton nextButton;
    private JButton forgetButton;
    private JButton defButton;

    private WordTool wordTool;

    public WordBook(WordTool cWordTool) {
        // 初始化 WordTool 对象
        wordTool = cWordTool;

        // 创建主窗口
        frame = new JFrame("LWord");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 设置应用图标
        String path = Objects.requireNonNull(
                        this.getClass().getClassLoader().getResource(""))
                .getPath()+"/LWord.png";
        try {
            Image icon = ImageIO.read(new File(path));
            frame.setIconImage(icon);
        }catch (IOException e) {
            e.printStackTrace();
        }

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
//            setDef(defLabel,nextWord);
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
//        setDef(defLabel, wordTool.GetCurrentWord());

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
                showNextWord();clearDef();
            }
        });

        // 忘记按钮
        forgetButton = new JButton("忘记了");
        forgetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { forgetCurrentWord(); showNextWord();clearDef(); }
        });

        // 查看释义按钮
        defButton = new JButton("查看释义");
        defButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { showDef(); }
        });

        // 设置按钮样式
        setButtonStyle(nextButton);
        setButtonStyle(forgetButton);
        setButtonStyle(defButton);

        // 创建按钮面板
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(nextButton);
        buttonPanel.add(forgetButton);
        buttonPanel.add(defButton);

        // 加入主窗口
        frame.add(buttonPanel, BorderLayout.SOUTH);
    }

    public void showDef() {
        setDef(defLabel,wordTool.GetCurrentWord());
    }

    public void clearDef() {
        defLabel.setText("");
    }

    public void show() {
        // 显示主窗口
        frame.setVisible(true);
    }

    public void setButtonStyle(JButton button){
        // 设置按钮背景色
        Color backgroundColor = new Color(238,233,233);
        button.setBackground(backgroundColor);

        // 设置按钮前景色（文字颜色）
        Color foregroundColor = new Color(30, 30, 30);
        button.setForeground(foregroundColor);

        // 设置按钮边框样式
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);

        // 设置为不透明
        button.setOpaque(true);

        // 添加鼠标悬停效果
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(backgroundColor.brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(backgroundColor);
            }
        });
    }
}
