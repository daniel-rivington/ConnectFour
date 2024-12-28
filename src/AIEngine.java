import java.util.ArrayList;

public class AIEngine {
    public int player;
    public Integer[][] exBoardState;
    public int depth;
    public int debt;
    ArrayList<Integer> allOurMovesSoFar = new ArrayList<>(0);
    ArrayList<Integer> fullMovesList = new ArrayList<>(0);
    public int randMoveResponse = -1;
    public Integer[] prevChosenTwoMoves = new Integer[] {-1,-1};// ltm[0] = us ltm[1] = them
    public gameEngine AIGameEngineState;
    public boolean overFlow = false;
    public Boolean won = false;
    public AIEngine(int player, Integer[] lastTwoMoves, int depth, int debt, ArrayList<Integer> incomingAIMovesList, Integer[][] exBoardState) {
        this.depth = depth;
        this.player = player;
        this.debt = debt;
        for(int i = 0; i< 2; i++){
            this.prevChosenTwoMoves[i] = lastTwoMoves[i];
        }
        for(int i = 0; i< incomingAIMovesList.size(); i++){
            allOurMovesSoFar.add(incomingAIMovesList.get(i));
        }
        AIGameEngineState = new gameEngine(6,7,exBoardState);
        //check win here
    }

    public void AIStartUp(){
        if(AIGameEngineState.checkWinIfPlacedAll(prevChosenTwoMoves[0],player)){
            won = true;
        }
        if(depth != 0 && !won){
            AIEngineTree();//debt to counter depth loss
        }
    }

    public void AIEngineTree(){
        if(!(prevChosenTwoMoves[0] == -1)){
            AIGameEngineState.dropPiece(prevChosenTwoMoves[0],player,true, AIGameEngineState.boardState);
        }
        if(prevChosenTwoMoves[1] == -1 && prevChosenTwoMoves[0] != -1){
            randMoveResponse = AIGameEngineState.aiStratBranch(debt, player == 1 ? 2:1, new ArrayList<Integer>(0), new ArrayList<Integer>(0), AIGameEngineState.boardState, player == 1 ? 2:1, -1)[1];
            AIGameEngineState.dropPiece(randMoveResponse,player == 1 ? 2:1,true, AIGameEngineState.boardState);
        } else if(!(prevChosenTwoMoves[0] == -1)){
            AIGameEngineState.dropPiece(prevChosenTwoMoves[1],player == 1 ? 2:1,true, AIGameEngineState.boardState);
        }
        Integer[] aiScoreUs = AIGameEngineState.aiStratBranch(debt, player, new ArrayList<Integer>(0), new ArrayList<Integer>(0), AIGameEngineState.boardState, player,-1);
        Integer[] aiScoreOpp = AIGameEngineState.aiStratBranch(debt, player == 1 ? 2:1,  new ArrayList<Integer>(0), new ArrayList<Integer>(0), AIGameEngineState.boardState, player, aiScoreUs[1]);

        if( aiScoreOpp[0] > aiScoreUs[0]  && debt != 0){
            ArrayList<Integer> avaliable = AIGameEngineState.getColumnsAvailiable(AIGameEngineState.boardState);
            if(!avaliable.isEmpty()){
                allOurMovesSoFar.remove(allOurMovesSoFar.size()-1);
                AIGameEngineState.removeTopPiece(prevChosenTwoMoves[1], AIGameEngineState.boardState);// adding the boardstate could be useful
                AIGameEngineState.removeTopPiece(prevChosenTwoMoves[0], AIGameEngineState.boardState);

                int randMove = avaliable.get((int)(Math.random()*(avaliable.size()-1)));
                allOurMovesSoFar.add(randMove);
                AIEngine AIEngineToCompare = new AIEngine(player, new Integer[] {randMove, -1},depth - 1, debt + 1, allOurMovesSoFar, AIGameEngineState.boardState);
                AIEngineToCompare.AIStartUp();//if this process is finished, either depth ran out or they won
                allOurMovesSoFar.clear();
                allOurMovesSoFar = AIEngineToCompare.allOurMovesSoFar;
            }
        } else {
            allOurMovesSoFar.add(aiScoreUs[1]);
            AIEngine AIEngineToCompare = new AIEngine(player, new Integer[] {aiScoreUs[1], aiScoreOpp[1]}, depth - 1, debt + 1, allOurMovesSoFar, AIGameEngineState.boardState);
            AIEngineToCompare.AIStartUp();//if this process is finished, either depth ran out or they won
            allOurMovesSoFar.clear();
            allOurMovesSoFar = AIEngineToCompare.allOurMovesSoFar;
        }
    }

    public void printDiagnostic(int printWhose){
        System.out.println("AI tree. \nPlayer: " + printWhose);
        System.out.println("depth: " + depth + ", debt: " + debt);
        System.out.println("Last two moves, us: " + prevChosenTwoMoves[0] + ", them: " + (randMoveResponse == -1 ? prevChosenTwoMoves[1]:randMoveResponse) );
        System.out.println("\nState: ");
        AIGameEngineState.printBoardState(AIGameEngineState.boardState);
    }
}
