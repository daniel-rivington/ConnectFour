import java.util.ArrayList;

public class GameEngine implements Cloneable {

    public void setBoardState(Integer[][] boardState) {
        this.boardState = boardState;
    }

    public Integer[][] boardState;
    public int rowCount;
    public int colCount;

    // how many moves in this game
    public int getNumberOfMoves() {
        return numberOfMoves;
    }

    private int numberOfMoves = 0;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        GameEngine cloneObj = new GameEngine(this.rowCount, this.colCount);
        cloneObj.setBoardState(new Integer[cloneObj.rowCount][cloneObj.colCount]);
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount ; j++)
              if (this.boardState[i][j]!= null)  cloneObj.getBoardState()[i][j] = new Integer(this.boardState[i][j]);
        }
        cloneObj.setBoardName(new String(this.getBoardName()));
        return cloneObj;
    }

    public Integer[][] getBoardState() {
        return boardState;
    }

    private String boardName = "Default";

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public void  resetBoard(){
        for(int row = 0; row < rowCount; row++){
            for(int col = 0; col < colCount; col++){
                boardState[row][col] = null;
            }
        }
    }

    public int[] dropPiece(int col, int player){
        numberOfMoves++;
        if(0 > col || col >= colCount){
            return new int[] {-1,-1};
        }
        for(int row = rowCount -1; row >= 0; row--){
            if((boardState[row][col] == null)){
                boardState[row][col] = player;
                return new int[] {row,col};
            }
        }
        return new int[] {-1,-1};

    }

    public int[] checkWinTypeAll(int[] location, int player){ // location[row][col] of placed piece
        //lbRow = lower bound to check for the current row. e.g. if i place in col 1, the lower bound for the row is col 0, upper bound is col 4
        //we check 3 away from every piece placed for 4 consecutive pieces in a row.
        int[] winTypes = {0,0,0,0,player};
        winTypes[0] = testForWin(location, player, 1,1,0, boardState);//bl to tr
        winTypes[1] = testForWin(location, player, 0,1,0, boardState);//hori
        winTypes[2] = testForWin(location, player, 1,0,0, boardState);//vert
        winTypes[3] = testForWin(location, player, 1,-1,0, boardState);//tl to br
        return winTypes;
    }

    public boolean anyWins(int []location, int player) {
        int [] wins = checkWinTypeAll(location,player);
        if (wins[0] + wins[1] + wins[2] + wins[3] > 0) return true;
        return false;
    }

    public int testForWin(int[] location, int player, int testCol, int testRow, int win, Integer[][] boardStateEx) {// location {row, col} of placed piece

        for(int i = -3; i <= 3; i++){
            if((location[0] + i*testCol < 0 || location[0] + i*testCol > 5)||(location[1] + i*testRow < 0 || location[1] + i*testRow > 6)){
                continue;
            }
            if(boardStateEx[location[0] + i*testCol][location[1] + i*testRow] == null || boardStateEx[location[0] + i*testCol][location[1] + i*testRow] != player){
                win = 0;
                continue;
            }
            win++;


            if(win > 3){
                return 1;
            }

        }
        return 0;
    }


    public void printBoardState(Integer[][] boardStateEx){
        System.out.println("BOARD " + getBoardName());
        for(int row = 0; row < rowCount; row++){
            for(int col = 0; col < colCount; col++){
                //System.out.print(boardStateEx[row][col] == null ? "◌ ":boardStateEx[row][col] == 1 ? "● ":"◍ ");
                System.out.print(boardStateEx[row][col] == null ? "◌ ":boardStateEx[row][col] == 1 ? "1 ":"2 ");
            }
            System.out.println("");
        }
        System.out.println("\n");
    }


    public GameEngine(int rowCount, int colCount) {
        this.rowCount = rowCount;
        this.colCount = colCount;
        this.boardState = new Integer[rowCount][colCount];
    }

    //skim over top of board to see if any column doesn't have value and therefore can have something dropped:

}


