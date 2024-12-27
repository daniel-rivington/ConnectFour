import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectFourGUI {

    private DefaultTableModel boardModel;
    private JTable board;
    private JPanel panel1;
    private JTextArea textArea1;
    private JButton vButton;
    private JButton vButton1;
    private JButton vButton2;
    private JButton vButton3;
    private JButton vButton4;
    private JButton vButton5;
    private JButton vButton6;
    private JToolBar toolBar1;
    private JButton resetButton;
    private JCheckBox player1CheckBox;
    private JLabel Wins;
    private JButton DoAIMove;
    private JFrame frame;
    private boolean wonState = false;
    public String[] columnNames = {"1","2","3","4","5","6","7"};

    public GameEngine engine;

    private void createUIComponents() {
        textArea1 = new JTextArea();
        textArea1.setText(rabbit);

        engine = new GameEngine(6,7);
        engine.resetBoard();


        board = new JTable();
        boardModel = new DefaultTableModel();
        boardModel.setDataVector(engine.boardState,columnNames);
        board.setModel(boardModel);


        // TODO: place custom component creation code here
    }
    public ConnectFourGUI(){
        frame = new JFrame("ConnectFourGUI");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        vButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBoard(0,player1CheckBox.isSelected() ? 1:2);
            }
        });
        vButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBoard(1,player1CheckBox.isSelected() ? 1:2);
            }
        });
        vButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBoard(2,player1CheckBox.isSelected() ? 1:2);
            }
        });
        vButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBoard(3,player1CheckBox.isSelected() ? 1:2);
            }
        });
        vButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { updateBoard(4,player1CheckBox.isSelected() ? 1:2); }
        });
        vButton5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBoard(5,player1CheckBox.isSelected() ? 1:2);
            }
        });
        vButton6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBoard(6,player1CheckBox.isSelected() ? 1:2);
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                engine.resetBoard();
                updateBoard(-1,player1CheckBox.isSelected() ? 1:2);
            }
        });

        DoAIMove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AIEngine test = new AIEngine(engine );
                int move = test.getBestInitialMove(player1CheckBox.isSelected() ? 1:2);
                updateBoard(move, player1CheckBox.isSelected() ? 1:2);

            }
        });
    }

    public static void main(String[] args) {
        ConnectFourGUI connectFourGUI = new ConnectFourGUI();
    }

    public void updateBoard(int col, int player){
        if(col == -1 || wonState){
            engine.resetBoard();
            wonState = false;
            textArea1.setBackground(Color.white);
            textArea1.setText(rabbit);
        } else {
           if ( engine.anyWins(engine.dropPiece(col, player), player)) {
               textArea1.setText("WINNER: \n" + "PLAYER " + player);
               textArea1.setBackground(Color.ORANGE);
               wonState = true;
           }
        };
        boardModel.setDataVector(engine.boardState,columnNames);
        engine.printBoardState(engine.boardState);
        player1CheckBox.setSelected(!player1CheckBox.isSelected());

    }

    public String rabbit = "Art by Row\n" +
            "             ,\n" +
            "            /|      __\n" +
            "           / |   ,-~ /\n" +
            "          Y :|  //  /\n" +
            "          | jj /( .^\n" +
            "          >-\"~\"-v\"\n" +
            "         /       Y\n" +
            "        jo  o    |\n" +
            "       ( ~T~     j\n" +
            "        >._-' _./\n" +
            "       /   \"~\"  |\n" +
            "      Y     _,  |\n" +
            "     /| ;-\"~ _  l\n" +
            "    / l/ ,-\"~    \\\n" +
            "    \\//\\/      .- \\\n" +
            "     Y        /    Y    \n" +
            "     l       I     !\n" +
            "     ]\\      _\\    /\"\\\n" +
            "    (\" ~----( ~   Y.  )\n" +
            "~~~~~~~~~~~~~~~~~~~~~~~~~";

}

