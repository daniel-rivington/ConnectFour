import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
    private JCheckBox AgainstAI;
    private JButton DoAIMove;
    private JButton multiPurposeButtonButton;
    private JFrame frame;

    public String[] columnNames = {"1","2","3","4","5","6","7"};
    public Integer[][] data;
    public gameEngine engine;

    public int aiPlaysAs = 1;


    //buttons



    private void createUIComponents() {
        textArea1 = new JTextArea();
        textArea1.setText(rabbit);

        engine = new gameEngine(6,7);
        engine.resetBoard();

        data = engine.boardState;
        board = new JTable();
        boardModel = new DefaultTableModel();
        boardModel.setDataVector(data,columnNames);
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
                //ArrayList<Integer> a = engine.AITree(2, new ArrayList<Integer>(0), 8, engine.boardState, true);
                //engine.aiStratBranch(1, new ArrayList<Integer>(0), new ArrayList<Integer>(0), engine.boardState);
                //engine.aiStratBranch(2, new ArrayList<Integer>(0), new ArrayList<Integer>(0), engine.boardState);
                System.out.println(" ");
                aiPlaysAs = aiPlaysAs == 2 ? 1:2;
                AIEngine test = new AIEngine(aiPlaysAs, new Integer[] {-1,-1}, 100, 0, new ArrayList<Integer>(0), engine.boardState);
                test.AIStartUp();
                ArrayList<Integer> a = test.allOurMovesSoFar;
                //System.out.println("AI moves: " + a);

                updateBoard(a.get(0), aiPlaysAs);
            }
        });

        multiPurposeButtonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {//it always assumes it is our move first
                System.out.println("player 1: " + engine.aiStratBranch(0,1, new ArrayList<Integer>(0), new ArrayList<Integer>(0), engine.boardState, 1,-1)[0]);
                System.out.println("player 1: " + engine.aiStratBranch(0,2, new ArrayList<Integer>(0), new ArrayList<Integer>(0), engine.boardState, 1,-1)[0]);
            }
        });
    }

    public static void main(String[] args) {

        ConnectFourGUI connectFourGUI = new ConnectFourGUI();
    }

    public void updateBoard(int col, int player){
        boolean win = false;
        if(col == -1){engine.resetBoard();} else {
            win = engine.checkWinTypeAll(engine.dropPiece(col, player,true, engine.boardState), player);
        }
        data = engine.boardState;
        boardModel.setDataVector(data,columnNames);
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

