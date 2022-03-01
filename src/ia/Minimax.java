package ia;

import game.Move;
import game.State;

public class Minimax extends Algorithme
{
    public Minimax(int player, int deepness)
    {
        super(player, deepness);
    }

    //cherche dans tous les states possible le plus score le plus optimal et le retourne
    public int browseNode(State state, int deepness)
    {
        int res;
        if (state.isOver() || deepness == 0)
        {
            this.countNode += 1;
            return state.getScore(state.getPlayer());
        }
        else
        {
            if (state.getPlayer() == this.player)
            {
                res = Integer.MIN_VALUE;
                for (Move move : state.getMove(state.getPlayer()))
                {
                    this.countNode += 1;
                    State newState = state.play(move);
                    int next = this.browseNode(newState, deepness - 1);
                    if (next > res)
                        res = next;
                }
            }
            else
            {
                res = Integer.MAX_VALUE;
                for (Move move : state.getMove(state.getPlayer()))
                {
                    this.countNode += 1;
                    State newState = state.play(move);
                    int next = this.browseNode(newState, deepness - 1);
                    if (next < res) 
                        res = next;
                }
            }
        }
        return res;
    }

    //retourne le mouvement permetant de prendre le moins de risque
    @Override
    public Move getBestMove(State state)
    {
        Move bestMove = null;
        int bestValue = Integer.MIN_VALUE; 
        State nextState = null;

        if (state.getMove(player).isEmpty())
            return new Move(7, 7, 7, 7, false, true);
        
        for (Move move : state.getMove(player))
        {
            this.countNode += 1;
            nextState = state.play(move);
            int value = this.browseNode(nextState, this.deepness);
            if (value > bestValue)
            {
                bestValue = value;
                bestMove = move;
            }
        }
        return bestMove;
    }
}
