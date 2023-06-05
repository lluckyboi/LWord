import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static javax.swing.SwingConstants.HORIZONTAL;

public class WordBook {
    private JFrame frame;
    private JFrame favFrame;
    private JLabel wordLabel;
    private JLabel phoLabel;
    private JLabel defLabel;
    private JButton favButton;
    private JPanel  popupPanel;
    private JScrollPane  scrollPane;

    private WordTool wordTool;

    public WordBook(WordTool cWordTool) {
        // 初始化 WordTool 对象
        wordTool = cWordTool;

        // 创建主窗口
        frame = new JFrame("LWord");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // 初始化收藏窗口
        favFrame = new JFrame("收藏");
        favFrame.setSize(300,600);

        // 设置应用图标
        String path = Objects.requireNonNull(
                        this.getClass().getClassLoader().getResource(""))
                .getPath()+"/LWord.png";
        try {
            Image icon = ImageIO.read(new File(path));
            frame.setIconImage(icon);
            favFrame.setIconImage(icon);
        }catch (IOException e) {
            e.printStackTrace();
        }

        // 固定大小 不能缩放
        favFrame.setResizable(false);

        // 创建弹出窗口的内容面板
        popupPanel = new JPanel();
        popupPanel.setLayout(new BoxLayout(popupPanel, BoxLayout.Y_AXIS));
        popupPanel.setBackground(Color.WHITE);

        // 创建滚动面板
        scrollPane = new JScrollPane(popupPanel);

        // 隐藏垂直滚动条
        // scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        // 隐藏水平滚动条
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // 设置滚动面板的垂直滚动条策略为始终显示
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // 设置自定义的滚动条UI
        scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());

        // 修改滚动条宽度
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(8, Integer.MAX_VALUE));

        // 设置使用鼠标滚轮时滚动条灵敏度
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);

        // 鼠标移上去时变为指针
        scrollPane.getVerticalScrollBar().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        scrollPane.getHorizontalScrollBar().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // 将滚动面板添加到收藏窗口
        favFrame.setContentPane(scrollPane);

        // 设置布局管理器为边界布局
        frame.setLayout(new BorderLayout());

        showWord();
        showButton();
        loadFavWords();
    }

    // 自定义滚动条UI类
    class CustomScrollBarUI extends BasicScrollBarUI {
        @Override
        protected void configureScrollBarColors() {
            // 设置滚动条的背景色、前景色和滑块颜色
            trackColor = Color.WHITE;
            thumbColor = new Color(234, 221, 221, 255);
        }

        @Override
        protected JButton createDecreaseButton(int orientation) {
            // 移除向上箭头按钮
            return new JButton() {
                @Override
                public Dimension getPreferredSize() {
                    return new Dimension(0, 0);
                }
            };
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            // 移除向下箭头按钮
            return new JButton() {
                @Override
                public Dimension getPreferredSize() {
                    return new Dimension(0, 0);
                }
            };
        }
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

    private void showWord() {
        // Grid分布
        GridBagConstraints gbc = new GridBagConstraints();
        GridLayout gLayout = new GridLayout(3,1);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill  = HORIZONTAL;

        // 创建单词面板
        JPanel wordPanel = new JPanel();
        wordPanel.setLayout(gLayout);
        wordPanel.setBackground(Color.WHITE);

        // 创建单词标签
        wordLabel = new JLabel();
        wordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        wordLabel.setFont(new Font("Arial", Font.BOLD, 36));

        // 音标标签
        phoLabel = new JLabel();
        phoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        phoLabel.setFont(new Font("Arial", Font.ITALIC, 20));

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

    private void showButton() {
        // 记住按钮
        JButton nextButton = new JButton("认识");
        nextButton.addActionListener(e -> {
            showNextWord();
            clearDef();
            changeFav(wordTool.isFav());
        });

        // 忘记按钮
        JButton forgetButton = new JButton("忘记了");
        forgetButton.addActionListener(e -> {
            forgetCurrentWord();
            showNextWord();
            clearDef();
            changeFav(wordTool.isFav());
        });

        // 查看释义按钮
        JButton defButton = new JButton("查看释义");
        defButton.addActionListener(e -> showDef());

        // 收藏按钮
        boolean isf = wordTool.isFav();
        String favText = "☆收藏";
        if(isf) favText = "★收藏";
        favButton = new JButton(favText);
        favButton.addActionListener(e -> {
            if(wordTool.isFav()) cancelFav();
            else favWord();

            changeFav(wordTool.isFav());
        });

        // 查看收藏
        JButton lookFavButton = new JButton("查看收藏");
        lookFavButton.addActionListener(e -> {
            loadFavWords();
            showFavFrame();
        });

        // 设置按钮样式
        setButtonStyle(nextButton);
        setButtonStyle(forgetButton);
        setButtonStyle(defButton);
        setButtonStyle(favButton);
        setButtonStyle(lookFavButton);

        // 创建按钮面板
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(nextButton);
        buttonPanel.add(forgetButton);
        buttonPanel.add(defButton);
        buttonPanel.add(favButton);

        // 加入主窗口
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(lookFavButton, BorderLayout.PAGE_START);
    }

    private void showDef() {
        setDef(defLabel,wordTool.GetCurrentWord());
    }

    private void clearDef() {
        defLabel.setText("");
    }

    public void show() {
        // 显示主窗口
        frame.setVisible(true);
    }

    private void setButtonStyle(JButton button){
        // 悬停时鼠标变为指针
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // 设置按钮背景色
        Color backgroundColor = new Color(234, 221, 221, 255);
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

    private void showFavFrame() { favFrame.setVisible(true); }

    // 从panel中移除当前word对应Label
    private void removeFromFav() {
        Word word = wordTool.GetCurrentWord();
        Component[] components = popupPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JLabel &&
                    ((JLabel) component).getText().
                            equals("<html><div style='width: 200px;text-align: center;margin: 5px'>" +
                                    word.getWord()+"<br>" +
                                    word.getDefinition()+
                                    "</div><hr style='width: 95%;'><br></html>")) {
                popupPanel.remove(component);
                break;
            }
        }
    }

    private void loadFavWords() {
        for(int i =1;i<= 10;i++){
            Word word = wordTool.GetNextFav();
            if (word != null){
                JLabel wordLabel = new JLabel("<html><div style='width: 200px;text-align: center;margin: 5px'>" +
                        word.getWord()+"<br>" +
                        word.getDefinition()+
                        "</div><hr style='width: 95%;'><br></html>");
                wordLabel.setFont(new Font("宋体", Font.BOLD, 18));
                wordLabel.setVerticalAlignment(SwingConstants.TOP);
                wordLabel.setHorizontalAlignment(SwingConstants.LEFT); // 设置水平对齐方式为左对齐
                popupPanel.add(wordLabel);
            } else {
                break;
            }
        }
        // 刷新弹出窗口的内容
        popupPanel.revalidate();
        popupPanel.repaint();
    }

    private void changeFavY(JButton favButton) {
        favButton.setText("★收藏");
    }

    private void changeFavN() {
        favButton.setText("☆收藏");
    }

    private void changeFav(boolean isFav) {
        if(!isFav) changeFavN();
        else changeFavY(favButton);
        loadFavWords();
    }

    private void cancelFav() {
        wordTool.CancelFav();
        removeFromFav();
    }

    private void favWord() {
        wordTool.FavWord();
        loadFavWords();
    }
}
