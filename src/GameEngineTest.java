public class GameEngineTest  {
    public static void main(String[] args) throws Exception {

        testClone();
    }

    private static void testClone() throws Exception {
        gameEngine ge = new gameEngine(6,7);
        ge.setBoardName("Test1 ");
        ge.getBoardState()[0][0] = 1;
        ge.getBoardState()[0][1] = 1;
        ge.getBoardState()[0][2] = 1;
        ge.getBoardState()[0][3] = 1;
        ge.getBoardState()[0][4] = 1;
        ge.getBoardState()[0][5] = 1;
        ge.getBoardState()[0][6] = 1;
        ge.setBoardName("ORiginal board");
        gameEngine geClone = (gameEngine) ge.clone();
        geClone.getBoardState()[0][6] = 2;
        geClone.setBoardName("Clone Board");
        ge.printBoardState(ge.getBoardState());
        geClone.printBoardState(geClone.boardState);
        System.out.println("Clone Test is " + (ge.getBoardState()[0][6]!=geClone.getBoardState()[0][6]));

    }

}
