import java.util.ArrayList;

public class AIEngine {
    private GameEngine simulatedGame;
    private int bestMove = 0;
    public AIEngine(GameEngine e )  {
      try { this.simulatedGame = (GameEngine) e.clone();}
      catch (CloneNotSupportedException ce) {
          throw new RuntimeException("Clone not supported");
      };
    }
    public int getBestInitialMove(int player) {
        return getNextAvailablePlay(player);
    }

    /**
     * see if any of the very next columns means we won.
     * If not return a random one.
     * @param player
     * @return
     */
    public int getNextAvailablePlay(int player)  {
        ArrayList<Integer> available = new ArrayList<>();
        try {
            for (int i = 0; i < simulatedGame.colCount-1; i++) {
                // test on a clone if it wins
                GameEngine geClone = (GameEngine) simulatedGame.clone();
                if ( geClone.anyWins(geClone.dropPiece(i, player),player)) return i;
                // see if you can block a move by the opposing guy:
                geClone = (GameEngine) simulatedGame.clone(); // first create a new clone so it doesn't have value from above.
                if (geClone.anyWins(geClone.dropPiece(i,(player==1)?2:1),(player==1)?2:1)) return i;

             if (simulatedGame.boardState[0][i] == null) available.add(new Integer(i));
         }
        } catch (CloneNotSupportedException ce )
        {
            throw new RuntimeException("Clone not supported");
        }
        // none of the games win; select at random.
        if (available.size() != 0) return available.get((int)(Math.random() * (available.size()) ));
        return -1;
    }
}
