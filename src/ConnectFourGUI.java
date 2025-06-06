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
    private JFrame frame;

    public String[] columnNames = {"1","2","3","4","5","6","7"};
    public Integer[][] data;
    public gameEngine engine;

    public int aiPlaysAs = 2;


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
                aiPlaysAs = aiPlaysAs == 2 ? 1:2;
                AIEngine test = new AIEngine(aiPlaysAs, 8, 0, -1, new ArrayList<Integer>(0), engine.boardState);
                test.AIStartUp();
                ArrayList<Integer> a = test.AIMovesList;
                updateBoard(a.get(0), aiPlaysAs);
            }
        });


    }





    public static void main(String[] args) {
        ConnectFourGUI connectFourGUI = new ConnectFourGUI();
    }



    public void updateBoard(int col, int player){

        if(col == -1){engine.resetBoard();} else {
            int[] a = engine.checkWinTypeAll(engine.dropPiece(col, player,true, engine.boardState), player);
            /*for(int i = 0; i < 4; i++){
                System.out.print(" " + a[i]);
            }
            System.out.println("\n-------- player " + player );*/
        }

        data = engine.boardState;
        boardModel.setDataVector(data,columnNames);

        //ArrayList<Integer> a = engine.AITree(1, new ArrayList<Integer>(0), 8, engine.boardState);
        //System.out.println(" \n player best moves \n");
        //for (int i = 0; i<a.size();i++){
        //    System.out.print(" Move " + i + ": " + a.get(i));
        //}

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


/* failed fall animation
public int fallAnim(int col, int endRow, int currentRow,  int player){

        if(endRow == currentRow){
            return 0;
        } else {
            board.setValueAt(player, currentRow, col);
            if(!(currentRow == 0)){
                board.setValueAt(null,currentRow - 1,col);
            }

            return fallAnim(col, endRow, currentRow + 1, player);
        }
    }
    System.out.println(" \n ai best moves \n");
                for (int i = 0; i<a.size();i++){
                    System.out.print(a.get(i) + ", ");
                }

 */
