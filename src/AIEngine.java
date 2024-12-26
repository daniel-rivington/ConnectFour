import java.util.ArrayList;

public class AIEngine {
    public int player;
    public Integer[][] exBoardState;
    public int depth;
    public int debt;
    public int firstMoveCol;
    public ArrayList<Integer> AIMovesList = new ArrayList<Integer>(0);
    public ArrayList<Integer> oppAIMovesList = new ArrayList<Integer>(0);
    public gameEngine AIGameEngineState;
    public boolean overFlow = false;
    public Boolean won = false;

    public AIEngine(int player, int depth, int debt, int firstMoveCol, ArrayList<Integer> AIMovesList, Integer[][] exBoardState) {
        this.depth = depth;
        this.player = player;
        this.debt = debt;
        AIGameEngineState = new gameEngine(6,7,exBoardState);
        this.firstMoveCol = firstMoveCol;
        if(firstMoveCol != -1){
            if(AIGameEngineState.dropPiece(firstMoveCol,player,false,AIGameEngineState.boardState)[0] == -1){
                overFlow = true;
            }
            AIMovesList.add(firstMoveCol);
            if(AIGameEngineState.checkWinIfPlacedAll(firstMoveCol, player)){
                won = true;
            }

        }

    }

    public void AIStartUp(){
        System.out.println("Player startup " + player);
        if((depth == 0 && !won) || overFlow){
            for(int i = 0; i<30;i++){
                AIMovesList.add(0);
            }
            oppAIMovesList.add(0);
        }

        if(depth != 0 && won){
            oppAIMovesList.add(0);
        }

        if(depth != 0 && !won){
            AIEngineTree();//debt to counter depth loss
        }
        printDiagnostic();

    }

    public void AIEngineTree(){

        if(firstMoveCol != -1){
            AIGameEngineState.dropPiece(firstMoveCol,player,true, AIGameEngineState.boardState);
        }

        AIMovesList = AIGameEngineState.AIEngineStratBranch(player, 0, new ArrayList<Integer>(0), AIGameEngineState.boardState);
        oppAIMovesList = AIGameEngineState.AIEngineStratBranch(player == 1 ? 2:1, 0, new ArrayList<Integer>(0), AIGameEngineState.boardState);

        AIEngine AIEngineToCompare = new AIEngine(player,depth - 1, debt + 1, (int)(Math.random()*6), new ArrayList<Integer>(0), AIGameEngineState.boardState);
        AIEngineToCompare.AIStartUp();

        for(int i = 0; i<debt;i++){
            AIMovesList.add(0);
        }

        oppAIMovesList.add(0);
        AIMovesList.add(0);

        if(AIMovesList.size()/oppAIMovesList.size() > AIEngineToCompare.AIMovesList.size()/AIEngineToCompare.oppAIMovesList.size()){
            AIMovesList = AIEngineToCompare.AIMovesList;
        }

    }

    public void printDiagnostic(){
        System.out.println("\nPlayer: " + player);

        System.out.println("depth: " + depth + ", debt: " + debt + ", moves for me:");
        for(int i = 0; i<AIMovesList.size();i++){
            System.out.print(AIMovesList.get(i) + ", ");
        }
        System.out.println("\nMoves for the opponent: ");
        for(int i = 0; i<oppAIMovesList.size();i++){
            System.out.print(oppAIMovesList.get(i) + ", ");
        }
        System.out.println("\nState: ");
        AIGameEngineState.printBoardState(AIGameEngineState.boardState);
    }
}
