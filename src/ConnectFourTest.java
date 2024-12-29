import org.junit.Test;
import java.util.ArrayList;
public class ConnectFourTest {
    // data needs to be carefully set up
    Integer[][] boardData0 = { {},{},{1,1,1,1}, {2,1,2,1},{2,1,1,1} ,{2,1,1,1} }; //
    Integer[][] boardData1_prevent = {{},{}, {}, {2,2,2,1},{2,1,1,1} ,{2,1,1,2} };
    Integer[][] boardData2_takewin = {{},{}, {}, {1,1,2,1},{2,1,1,1} ,{2,1,1,1} };
    Integer[][] boardData3_prevent = { {},{},{}, {2,2,2,1},{1,2,1,1} ,{1,2,1,2} };
    Integer[][] boardData4_prevent = { {},{},{}, {2,1,2,1},{1,2,1,1} ,{1,2,1,1} };
    Integer[][] boardData5_prevent = { {},{},{}, {2,1,2,1},{1,2,1,1} ,{1,1,1,1} };
    gameEngine ge;
    @Test
    public void testGameEngine() {
        ge = new gameEngine(6,7,boardData0);
        System.out.println("TEST GAME ENGINE THE BOARD IS:");
        ge.printBoardState(ge.boardState);
        assert(ge.checkWinTypeAll(new int[]{0, 2},1)[0] != -1);

    }
    @Test
    public void takesWin1() {
        testAITakesWinIfOffered(boardData1_prevent,1,0);
    }
    @Test
    public void testAIPreventsWinByOpponent1() {
        testAIPreventsWinByOpponent(boardData1_prevent,1,0);
    }
    @Test
    public void testAIPreventsWinByOpponent2() {
        testAIPreventsWinByOpponent(boardData3_prevent,1,1);
    }
    @Test
    public void testAIPreventsWinByOpponent3() {
        testAIPreventsWinByOpponent(boardData4_prevent,2,3);
    }

    private void testAIPreventsWinByOpponent(Integer[][] boardData,int player, int expected) {
        // set up a board data that would make the opponent win in one move and see if AI
        // prevents it
        ge = new gameEngine(6,7,boardData);
        System.out.println("----------------------------------------------------");
        System.out.println("TEST AI PREVENT THE BOARD IS:");
        ge.printBoardState(ge.boardState);
        System.out.println("Player is " + player + " expected is " + expected);
        System.out.println("----------------------------------------------------");
        ArrayList<Integer> m1  = new ArrayList<>();
        ArrayList<Integer> m2  = new ArrayList<>();
        ArrayList<Integer> response = ge.AIEngineStratBranch(player,1,1,
               m1,m2, ge.boardState);
         // I have no idea how to test this. maybe test
        // that it doesn't allow somone to win in the next move
        // check that response doesn't
        System.out.println("RESPONSE WAS : " + response.get(0));
        assert(response.get(0)  == expected   ); // assuming 4 is the winning move for opponent
    }

    public void testAITakesWinIfOffered(Integer[][] boardData,int player, int expected) {
        // set up a board data that would make the opponent win in one move and see if AI
        // prevents it
        ge = new gameEngine(6,7, boardData);
        System.out.println("TEST AI TAKE WIN THE BOARD IS:");
        ge.printBoardState(ge.boardState);
        ArrayList<Integer> m1  = new ArrayList<>();
        ArrayList<Integer> m2  = new ArrayList<>();
        ArrayList<Integer> response = ge.AIEngineStratBranch(player,1,1,
                m1,m2, ge.boardState);
        // I have no idea how to test this. maybe test
        // that it doesn't allow somone to win in the next move
        // check that response doesn't
        assert(response.get(0)  == expected   ); // assuming 3 is the winning move for ai
        System.out.println("RESPONSE WAS : " + response.get(0));

    }

}
