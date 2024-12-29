import java.util.ArrayList;

public class gameEngine {
    public Integer[][] boardState;
    public int rowCount;
    public int colCount;






    public void  resetBoard(){
        for(int row = 0; row < rowCount; row++){
            for(int col = 0; col < colCount; col++){
                boardState[row][col] = null;
            }
        }

    }




    public int[] dropPiece(int col, int player,Boolean forReal,Integer[][] boardStateEx){
        if(0 > col || col >= colCount){
            return new int[] {-1,-1};
        }
        if(getColumnsAvailiable(boardStateEx).contains(col) == false){// may cause problems
            return new int[] {-1,-1};
        }

        for(int row = rowCount -1; row >= 0; row--){
            if((boardStateEx[row][col] == null)){
                if(forReal){
                    boardState[row][col] = player;
                }
                if(player == -1){
                    boardState[row][col] = null;
                }

                return new int[] {row,col};
            }
        }
        return new int[] {-1,-1};

    }

    public void removeTopPiece(int col,Integer[][] boardStateEx){
        if(!(0 > col || col >= colCount)){
            for(int row = rowCount -1; row >= 0; row--){
                if(!(boardStateEx[row][col] == null)){
                    boardState[row][col] = null;
                }
            }
        }



    }
    /*
      ^ -
      | increase/decrease row
      v +

    <---> increase/decrease col
    -   +
     */
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

    public  Boolean checkWinIfPlacedAll(int col, int player){ // location[row][col] of placed piece
        int[] location = dropPiece(col, player, false, boardState);
        if( testForWinIfPlaced(location, player, 1,1,0, boardState) == 1){//bl to tr
            return true;
        }
        if( testForWinIfPlaced(location, player, 0,1,0, boardState) == 1){
            return true;
        }
        if( testForWinIfPlaced(location, player, 1,0,0, boardState) == 1){
            return true;
        }
        if( testForWinIfPlaced(location, player, 1,-1,0, boardState) == 1){
            return true;
        }
        return false;
    }


    public int testForWinIfPlaced(int[] location, int player, int testCol, int testRow, int win, Integer[][] boardStateEx) {// location {row, col} of placed piece

        for(int i = -3; i <= 3; i++){
            if((location[0] + i*testCol < 0 || location[0] + i*testCol > 5)||(location[1] + i*testRow < 0 || location[1] + i*testRow > 6)){
                continue;
            }
            if(boardStateEx[location[0] + i*testCol][location[1] + i*testRow] == null || boardStateEx[location[0] + i*testCol][location[1] + i*testRow] != player){
                if(!(i == 0)){
                    win = 0;
                    continue;
                }

            }
            win++;

            if(win > 3){
                return 1;
            }

        }
        return 0;
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

    public int winScoreIfPlaced(int[] location, int WSplayer, int testCol, int testRow, int win, Integer[][] boardStateEx) {// location {row, col} of placed piece
        int win1 = win;
        for(int i = -3; i <= 0; i++){
            if((location[0] + i*testCol < 0 || location[0] + i*testCol > 5)||(location[1] + i*testRow < 0 || location[1] + i*testRow > 6)){
                continue;
            }
            if(boardStateEx[location[0] + i*testCol][location[1] + i*testRow] == null || boardStateEx[location[0] + i*testCol][location[1] + i*testRow] != WSplayer){
                if(!(i == 0)){
                    win1 = 0;
                    continue;
                }

            }
            win1++;

        }
        int win2 = win;
        for(int i = 0; i <= 3; i++){
            if((location[0] + i*testCol < 0 || location[0] + i*testCol > 5)||(location[1] + i*testRow < 0 || location[1] + i*testRow > 6)){
                continue;
            }
            if(boardStateEx[location[0] + i*testCol][location[1] + i*testRow] == null || boardStateEx[location[0] + i*testCol][location[1] + i*testRow] != WSplayer){
                if(!(i == 0)){

                    win2 = 0;
                    continue;
                }

            }
            win2++;

        }
        return Math.max(win1,win2);// n x n 1 1 n n counts as a streak of 2, problem
    }

    public int fullWinScoreIfPlaced(int col, int FWSplayer, Integer[][] boardStateEx){
        int[] location = dropPiece(col, FWSplayer, false, boardStateEx);
        int max1 = Math.max(winScoreIfPlaced(location,FWSplayer, 1,1,0,boardStateEx), winScoreIfPlaced(location,FWSplayer, 0,1,0,boardStateEx));
        int max2 = Math.max(winScoreIfPlaced(location,FWSplayer, 1,0,0,boardStateEx), winScoreIfPlaced(location,FWSplayer, 1,-1,0,boardStateEx));


        return Math.max(max1, max2);
    }
    /*
    public ArrayList<Integer> AIStratBranch(int player, int prevWinScore, ArrayList<Integer> Moves, Integer[][] boardStateEx){
        if(prevWinScore > 3 || Moves.size() > 10){
            System.out.print("length: " + Moves.size() + ", {");
            for(int i = 0;i<Moves.size();i++){
                System.out.print(" " + Moves.get(i));
            }
            System.out.println( "}, player " + player);
            System.out.println("\n");
            for(int i = 0;i<Moves.size();i++){
                System.out.print("move " + i + ": " + Moves.get(i) + ", ");
            }
            return Moves;
        }

        Integer[][] newBoardState4Branch = new Integer[6][7];
        for(int i = 0; i< boardStateEx.length; i++){
            for(int p = 0;p<boardStateEx[i].length;p++){
                newBoardState4Branch[i][p] = boardStateEx[i][p];
            }
        }

        int max = 0;
        int bestCol = 3;
        int randCol;
        int randColScore;
        int randColOppScore;
        for(int i = 0; i<20; i++){

            randCol = (int)(Math.random()*6);
            randColScore = fullWinScoreIfPlaced(randCol, player,newBoardState4Branch);
            randColOppScore = fullWinScoreIfPlaced(randCol,player == 1 ? 2:1,newBoardState4Branch);
            //System.out.println("column: " + randCol + "Score" + randColScore);
            if(randColOppScore > max){
                max = randColOppScore;
                bestCol = randCol;
            }
            if(randColScore > max ){
                max = randColScore;
                bestCol = randCol;
            }


        }



        Moves.add(bestCol);
        int[] piecePos = dropPiece(bestCol,player,false,newBoardState4Branch);
        newBoardState4Branch[piecePos[0]][piecePos[1]] = player;

        //printBoardState(newBoardState);
        //System.out.println("choice: " + bestCol + ", move number: " + Moves.size() + " as player " + player);
        return AIStratBranch(player, max, Moves, newBoardState4Branch);
    }
    public ArrayList<Integer> AITree(int player, ArrayList<Integer> Moves, int depth, Integer[][] boardStateEx, Boolean reallyMe){
        if(depth == 0){
            return Moves;
            //return AIStratBranch(player,0,new ArrayList<Integer>(0), boardStateEx);
        }
        Integer[][] newBoardState1 = new Integer[6][7];
        for(int i = 0; i< boardStateEx.length; i++){
            for(int p = 0;p<boardStateEx[i].length;p++){
                newBoardState1[i][p] = boardStateEx[i][p];
            }
        }

        Integer[][] newBoardState2 = new Integer[6][7];
        for(int i = 0; i< boardStateEx.length; i++){
            for(int p = 0;p<boardStateEx[i].length;p++){
                newBoardState2[i][p] = boardStateEx[i][p];
            }
        }

        ArrayList<Integer> addmove1 = AIStratBranch(player,0,new ArrayList<Integer>(0), newBoardState1);

        int[] piecePos1 = dropPiece(addmove1.get(0),player,false,newBoardState1);
        if(addmove1.size() == 1){
            Moves.add(addmove1.get(0));
            System.out.println("best");
            return Moves;
        }
        newBoardState1[piecePos1[0]][piecePos1[1]] = player;

        ArrayList<Integer> movesToAdd1 = new ArrayList<>();
        for(int i = 0; i<Moves.size();i++){
            movesToAdd1.add(Moves.get(i));
        }
        movesToAdd1.add(addmove1.get(0));
        ArrayList<Integer> movesToCompare1 = AITree(player == 2 ? 1:2, movesToAdd1, depth - 1, newBoardState1, !reallyMe);


        ArrayList<Integer> addmove2 = AIStratBranch(player,0,new ArrayList<Integer>(0), newBoardState2);

        int[] piecePos2 = dropPiece(addmove2.get(0),player,false,newBoardState2);
        if(addmove1.size() == 1){
            Moves.add(addmove2.get(0));
            System.out.println("best");
            return Moves;
        }
        newBoardState2[piecePos2[0]][piecePos2[1]] = player;

        ArrayList<Integer> movesToAdd2 = new ArrayList<>();
        for(int i = 0; i<Moves.size();i++){
            movesToAdd2.add(Moves.get(i));
        }
        movesToAdd2.add(addmove2.get(0));
        ArrayList<Integer> movesToCompare2 = AITree(player == 2 ? 1:2, movesToAdd2, depth - 1, newBoardState2, !reallyMe);//Maybe this would be better? ArrayList<Integer> movesToCompare2 = AITree(player == 1 ? 2:1, movesToAdd2, depth - 1, newBoardState);

        //System.out.println("mtc1: "+ movesToCompare1.size() + " mtc2: "+ movesToCompare2.size());

        if(movesToCompare2.size() > movesToCompare1.size()){
            return movesToCompare1;
        } else {
            return movesToCompare2;
        }

    }*/






    public void printBoardState(Integer[][] boardStateEx){

        for(int row = 0; row < rowCount; row++){
            for(int col = 0; col < colCount; col++){
                //System.out.print(boardStateEx[row][col] == null ? "◌ ":boardStateEx[row][col] == 1 ? "● ":"◍ ");
                System.out.print(boardStateEx[row][col] == null ? "◌ ":boardStateEx[row][col] == 1 ? "1 ":"2 ");
            }
            System.out.println("");
        }
        System.out.println("\n");
    }


    public ArrayList<Integer> AIEngineStratBranch(int givenPlayer, int prevWinScoreP1, int prevWinScoreP2, ArrayList<Integer> movesP1, ArrayList<Integer> movesP2, Integer[][] boardStateEx){
        Integer[][] newBoardState4Branch = new Integer[6][7];
        for(int i = 0; i< boardStateEx.length; i++){
            for(int p = 0;p<boardStateEx[i].length;p++){
                newBoardState4Branch[i][p] = boardStateEx[i][p];
            }
        }

        ArrayList<Integer> available = getColumnsAvailiable(newBoardState4Branch);
        if(available.isEmpty()){
            (givenPlayer == 1 ? movesP1:movesP2).add(0);
            return givenPlayer == 1 ? movesP1:movesP2;
        }





        //max as 0 the whole time?

        int maxP1 = prevWinScoreP1;
        int maxP2 = prevWinScoreP2;
        int bestCol = 0;
        if(available.size() != 0){
            bestCol = available.get((int)(Math.random()*(available.size()-1)));
        }
        int randCol;
        int randColScore;

        for(int i = 0; i<available.size(); i++){
            //randCol = (int)(Math.random()*6);
            randCol = available.get(i);
            randColScore = fullWinScoreIfPlaced(randCol, givenPlayer,newBoardState4Branch);
            if(randColScore >= Math.max(maxP1, 2) && givenPlayer == 1){
                maxP1 = randColScore;
                bestCol = randCol;
            }
            if(randColScore >= Math.max(maxP2, 2) && givenPlayer == 2){
                maxP2 = randColScore;
                bestCol = randCol;
            }


        }
        //System.out.println("player: " + givenPlayer + ", moves so far " + (givenPlayer ==  1 ? movesP1:movesP2) + " to add" + bestCol);

        if(givenPlayer == 1){
            movesP1.add((Integer)bestCol);
        } else {
            movesP2.add((Integer)bestCol);
        }

        int[] piecePos = dropPiece(bestCol,givenPlayer,false,newBoardState4Branch);
        if((piecePos[0] != -1)){
            newBoardState4Branch[piecePos[0]][piecePos[1]] = givenPlayer;
        } else {
            return givenPlayer == 1 ? movesP1:movesP2;
        }

        System.out.println("AI Strat Branch for player " + givenPlayer);
        System.out.println("Win score is " + (givenPlayer == 1 ? maxP1:maxP2) + " for column " + bestCol);
        printBoardState(newBoardState4Branch);


        if((givenPlayer == 1 ? maxP1:maxP2) > 3){
            //System.out.println("Win, or > 4 moves, player = " + givenPlayer + ", move num = " + movesP1.size());
            return givenPlayer == 1 ? movesP1:movesP2;
        }

        if((givenPlayer == 2 ? maxP1:maxP2) > 3){
            for(int i = 0; i< 20 - (givenPlayer == 2 ? movesP1:movesP2).size(); i++){
                (givenPlayer == 1 ? movesP1:movesP2).add(0);
            }
            return givenPlayer == 1 ? movesP1:movesP2;
        }


        return AIEngineStratBranch(givenPlayer == 1 ? 2:1, maxP1, maxP2, movesP1, movesP2, newBoardState4Branch);
    }

    public ArrayList<Integer> aiEngineStratBranch(int givenPlayer, ArrayList<Integer> movesP1, ArrayList<Integer> movesP2, Integer[][] boardStateEx){

        //clone boardstate
        Integer[][] newBoardState4Branch = new Integer[6][7];
        for(int i = 0; i< boardStateEx.length; i++){
            for(int p = 0;p<boardStateEx[i].length;p++){
                newBoardState4Branch[i][p] = boardStateEx[i][p];
            }
        }
        //check if available

        ArrayList<Integer> available = getColumnsAvailiable(newBoardState4Branch);


        if(available.isEmpty()){
            (givenPlayer == 1 ? movesP1:movesP2).add(0);
            return givenPlayer == 1 ? movesP1:movesP2;
        }


        //find best column
        int max = 0;
        int bestCol = 0;
        bestCol = available.get((int)(Math.random()*(available.size()-1)));
        int score;

        for(int i = 0; i<available.size(); i++){
            score = fullWinScoreIfPlaced(available.get(i), givenPlayer,newBoardState4Branch);
            if(score >= Math.max(max, 2)){
                max = score;
                bestCol = available.get(i);
            }
        }
        //add it to the moves

        (givenPlayer == 1 ? movesP1:movesP2).add((Integer)bestCol);
        int[] piecePos = dropPiece(bestCol,givenPlayer,false,newBoardState4Branch);
        newBoardState4Branch[piecePos[0]][piecePos[1]] = givenPlayer;
        //print it out


        //check for our win
        if(max > 3){
            return givenPlayer == 1 ? movesP1:movesP2;
        }
        //check available
        available.clear();
        available = getColumnsAvailiable(newBoardState4Branch);



        if(available.isEmpty()){
            (givenPlayer == 1 ? movesP1:movesP2).add(0);
            return givenPlayer == 1 ? movesP1:movesP2;
        }
        //find best column for opp
        max = 0;
        bestCol = 0;
        bestCol = available.get((int)(Math.random()*(available.size()-1)));
        score = 0;

        for(int i = 0; i<available.size(); i++){
            score = fullWinScoreIfPlaced(available.get(i), givenPlayer == 1 ? 2:1,newBoardState4Branch);
            if(score >= Math.max(max, 2)){
                max = score;
                bestCol = available.get(i);
            }
        }

        (givenPlayer == 2 ? movesP1:movesP2).add((Integer)bestCol);
        piecePos = dropPiece(bestCol,givenPlayer == 2 ? 1:2,false,newBoardState4Branch);
        newBoardState4Branch[piecePos[0]][piecePos[1]] = givenPlayer == 2 ? 1:2;
        //print it out

        //make ours long
        if(max > 3){
            for(int i = 0; i< 20 - (givenPlayer == 2 ? movesP1:movesP2).size(); i++){
                (givenPlayer == 1 ? movesP1:movesP2).add(0);
            }
            return givenPlayer == 1 ? movesP1:movesP2;
        }


        return aiEngineStratBranch(givenPlayer, movesP1, movesP2, newBoardState4Branch);
    }


    public Integer[] aiStratBranch(int prevMoves, int givenPlayer, ArrayList<Integer> movesP1, ArrayList<Integer> movesP2, Integer[][] boardStateEx,int firstMovingPlayer, int firstMoveIfGiven){//prevmoves might not be necessary

        //clone boardstate
        Integer[][] newBoardState4Branch = new Integer[6][7];
        for(int i = 0; i< boardStateEx.length; i++){
            for(int p = 0;p<boardStateEx[i].length;p++){
                newBoardState4Branch[i][p] = boardStateEx[i][p];
            }
        }
        //check if available

        ArrayList<Integer> available = getColumnsAvailiable(newBoardState4Branch);


        if(available.isEmpty()){
            return new Integer[] {0 + prevMoves,(givenPlayer == 1 ? movesP1:movesP2).get(0)};
        }


        //find best column
        int max = 0;
        int bestCol = 0;
        bestCol = available.get((int)(Math.random()*(available.size()-1)));
        int score;

        for(int i = 0; i<available.size(); i++){
            score = fullWinScoreIfPlaced(available.get(i), firstMovingPlayer,newBoardState4Branch);
            if(score >= Math.max(max, 2)){
                max = score;
                bestCol = available.get(i);
            }
        }
        //add it to the moves
        if(firstMoveIfGiven != -1){
            bestCol = firstMoveIfGiven;
        }

        (firstMovingPlayer == 1 ? movesP1:movesP2).add((Integer)bestCol);
        int[] piecePos = dropPiece(bestCol,firstMovingPlayer,false,newBoardState4Branch);
        newBoardState4Branch[piecePos[0]][piecePos[1]] = firstMovingPlayer;



        //check for first mover win
        if(max > 3){ //if first mover won, and
            if(firstMovingPlayer == givenPlayer){//if we have the first move,
                return new Integer[] {22-(givenPlayer == 1 ? movesP1:movesP2).size() - prevMoves, (givenPlayer == 1 ? movesP1:movesP2).get(0)};//return as our win
            }//else
            if ((givenPlayer == 1 ? movesP1:movesP2).isEmpty()){ //if second mover loses on first move give random col
                return new Integer[] {(givenPlayer == 2 ? movesP1:movesP2).size() + prevMoves - 22,3};
            }
            return new Integer[] {(givenPlayer == 2 ? movesP1:movesP2).size() + prevMoves - 22, (givenPlayer == 1 ? movesP1:movesP2).get(0)};//return as other guy's win
            //System.out.println("player " + givenPlayer + " "+ (22-(givenPlayer == 1 ? movesP1:movesP2).size()));

        }
        //check available
        available.clear();
        available = getColumnsAvailiable(newBoardState4Branch);
        if(available.isEmpty()){
            return new Integer[] {0 + prevMoves,(givenPlayer == 1 ? movesP1:movesP2).get(0)};
        }
        max = 0;
        bestCol = 0;
        bestCol = available.get((int)(Math.random()*(available.size()-1)));
        score = 0;

        for(int i = 0; i<available.size(); i++){
            score = fullWinScoreIfPlaced(available.get(i), firstMovingPlayer == 1 ? 2:1,newBoardState4Branch);
            if(score >= Math.max(max, 2)){
                max = score;
                bestCol = available.get(i);
            }
        }

        (firstMovingPlayer == 2 ? movesP1:movesP2).add((Integer)bestCol);
        piecePos = dropPiece(bestCol,firstMovingPlayer == 2 ? 1:2,false,newBoardState4Branch);
        newBoardState4Branch[piecePos[0]][piecePos[1]] = firstMovingPlayer == 2 ? 1:2;
        //print it out


        if(max > 3){//if the second mover won
            if(firstMovingPlayer == givenPlayer){// and we are the first mover
                return new Integer[] {(givenPlayer == 2 ? movesP1:movesP2).size() + prevMoves - 22, (givenPlayer == 1 ? movesP1:movesP2).get(0)};// treat as our loss
            }
            // if we're the second mover
            return new Integer[] {22-(givenPlayer == 1 ? movesP1:movesP2).size() - prevMoves, (givenPlayer == 1 ? movesP1:movesP2).get(0)};//treat as our win
        }


        return aiStratBranch(prevMoves,givenPlayer, movesP1, movesP2, newBoardState4Branch,firstMovingPlayer,-1);
    }



    public void printMoves(ArrayList<Integer> movesP1, ArrayList<Integer> movesP2){

        for(int i = 0; i <Math.min(movesP1.size(),movesP2.size());i++){
            System.out.print(movesP1.get(i));
            System.out.println(movesP2.get(i));
        }
    }



    public gameEngine(int rowCount, int colCount) {
        this.rowCount = rowCount;
        this.colCount = colCount;
        this.boardState = new Integer[rowCount][colCount];
    }

    public gameEngine(int rowCount, int colCount, Integer[][] exBoardState){
        this.rowCount = rowCount;
        this.colCount = colCount;
        this.boardState = new Integer[rowCount][colCount];

        for(int i = 0; i< exBoardState.length; i++){
            for(int p = 0;p<exBoardState[i].length;p++){
                this.boardState[i][p] = exBoardState[i][p];
            }
        }

    }

    public ArrayList<Integer> getColumnsAvailiable(Integer[][] givenBoardState){
        ArrayList<Integer> columnsAvailiable = new ArrayList<>(0);
        for(int i = 0;i<7;i++){
            int full = 1;
            for(int p = 0;p<6;p++){
                if(givenBoardState[p][i] == null){
                    full = 0;
                }
            }
            if(full == 0){
                columnsAvailiable.add(i);
            }
        }
        return columnsAvailiable;
    }




}

/*
try {
                hori = boardState[lbRow + Math.min(lbRow + i, ubRow)][location[1]] == player ? hori + 1 : 0;
                vert = boardState[location[0]][lbCol + Math.min(lbCol + i, ubCol)] == player ? vert + 1 : 0;
            } catch (Exception ignored) {
            }
            if(vert == 4 || hori == 4){
                System.out.println("win for player " + player);
                return player;
            }



                private void testVert(int[] location, int player, int lbCol, int ubCol, int vert) {
        for(int i = lbCol; i<= ubCol; i++){// on the row of the current piece, check 3 columns left and 3 right for a 4 in a row
            if(boardState[i][location[1]] == null || boardState[i][location[1]] != player){
                vert = 0;
                continue;
            }
            vert++;
            if(vert >3){
                System.out.println("vert win for player " + player);
            }
        }
    }

    private void testHori(int[] location, int player, int lbRow, int ubRow, int hori) {
        for(int i = lbRow; i<= ubRow; i++){// on the row of the current piece, check 3 columns left and 3 right for a 4 in a row
            if(boardState[location[0]][i] == null || boardState[location[0]][i] != player){
                hori = 0;
                continue;
            }
            hori++;
            if(hori >3){
                System.out.println("hori win for player " + player);
            }
        }
    }

    private void testDiag1(int[] location, int player, int lbRow, int ubRow, int lbCol, int ubCol, int diag) {

        for(int i = -3; i < 3; i++){// we prob can optimize this... (rn it's if our piece's col# minus i is less than the lower bounds, skip)
            if((location[1] + i < lbRow || location[0] + i  < lbCol) || (location[1] + i  > ubRow || location[0] + i  > ubCol)){
                continue;
            }
            if(boardState[location[1] + i][location[0] + i] == null || boardState[location[1] + i][location[0] + i] != player){
                diag = 0;
                continue;
            }

            diag++;
            if(diag > 3){
                System.out.println("Diag win for player " + player);
            }
        }
    }


                if(testCol == 1){
                    if(testRow == 1){
                        return 1;//diagonal tl to br
                    }
                    if(testRow == 0){
                        return 1;// vertical
                    }
                } else {
                    if (testRow == 1){
                        return 1;// horizontal
                    }
                }
                if (testRow == -1){
                    return 1;//bl to tr
                }

                 private ArrayList<Integer> forseeOneAwayWin(int[] location, int player, int testCol, int testRow, int win, Integer[][] boardStateEx, ArrayList<Integer> placement, int prevScore) {// location {row, col} of placed piece
        ArrayList<Integer> streakMemory = new ArrayList<>(0);
        ArrayList<Integer> streakTemp = new ArrayList<>(0);
        for(int i = -3; i <= 3; i++){
            if((location[0] + i*testCol < 0 || location[0] + i*testCol > 5)||(location[1] + i*testRow < 0 || location[1] + i*testRow > 6)){
                continue;
            }
            if(boardStateEx[location[0] + i*testCol][location[1] + i*testRow] == null || boardStateEx[location[0] + i*testCol][location[1] + i*testRow] != player){
                win = 0;
                streakTemp.clear();
                continue;
            }
            win++;
            streakTemp.add(location[0] + i*testCol);

            if(win > 3){
                for(Integer pos : streakTemp){
                    streakMemory.add(pos);
                }
                return streakMemory;
            }

            if(win > prevScore){
                streakTemp.clear();
            }
        }

        return streakMemory;
    }

    Integer addmove1 = AIStratBranch(player,0,new ArrayList<Integer>(0), newBoardState).get(0);
        Moves.add(addmove1);
        ArrayList<Integer> movesToCompare1 = AITree(player, Moves, depth - 1, newBoardState);
        Moves.remove(Moves.size()-1);

        Integer addmove2 = AIStratBranch(player,0,new ArrayList<Integer>(0), newBoardState).get(0);
        Moves.add(addmove1);
        ArrayList<Integer> movesToCompare2 = AITree(player, Moves, depth - 1, newBoardState);

        if(addmove1.size() == 1){
            Moves.add(addmove1.get(0));
            System.out.println("best");
            return Moves;
        }

        if(addmove2.size() == 1){
            System.out.println("best");
            Moves.add(addmove1.get(0));
            return Moves;
        }

 */
