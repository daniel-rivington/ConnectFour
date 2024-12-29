import org.junit.Test;
import java.util.ArrayList;
public class ConnectFourTest {
    // data needs to be carefully set up
    Integer[][] boardData0 = { {}, {},{1,1,2,1,2,1,1}, {1,1,2,1,2,1,2},{1,2,1,2,1,2,1},{1,2,1,2,2,2,1},  };
    Integer[][] boardData1 = { {}, {},{1,1,2,1,2,1,1}, {1,1,2,1,2,1,2},{1,2,1,2,1,2,1},{1,2,1,2,2,2,1},  };
    Integer[][] boardData2 = { {}, {},{2,1,2,1,2,1,1}, {1,1,2,1,2,1,2},{1,2,1,2,1,2,1},{1,2,1,2,2,2,1},  };

    gameEngine ge;
    @Test
    public void testGameEngine() {
        ge = new gameEngine(6,7,boardData0);
        assert(ge.checkWinTypeAll(new int[]{1, 2},1)[0] != -1);
    }
    @Test
    public void testAIPreventsWinByOpponent() {
        // set up a board data that would make the opponent win in one move and see if AI
        // prevents it
        ge = new gameEngine(6,7,boardData1);
        ArrayList<Integer> m1  = new ArrayList<>();
        ArrayList<Integer> m2  = new ArrayList<>();
        ArrayList<Integer> response = ge.AIEngineStratBranch(1,1,1,
               m1,m2, ge.boardState);
         // I have no idea how to test this. maybe test
        // that it doesn't allow somone to win in the next move
        // check that response doesn't
        assert(response.get(0)  != 4   ); // assuming 4 is the winning move for opponent
    }
    @Test
    public void testAITakesWinIfOffered() {
        // set up a board data that would make the opponent win in one move and see if AI
        // prevents it
        ge = new gameEngine(6,7,boardData2);
        ArrayList<Integer> m1  = new ArrayList<>();
        ArrayList<Integer> m2  = new ArrayList<>();
        ArrayList<Integer> response = ge.AIEngineStratBranch(1,1,1,
                m1,m2, ge.boardState);
        // I have no idea how to test this. maybe test
        // that it doesn't allow somone to win in the next move
        // check that response doesn't
        assert(response.get(0)  == 0   ); // assuming 4 is the winning move for ai
    }

}
