package ia;

import game.*; 

public class AlphabetaForMinimax extends Algorithme
{
    int alpha = Integer.MIN_VALUE;
    int beta = Integer.MAX_VALUE;

    public AlphabetaForMinimax(int player, int deepness)
    {
        super(player, deepness);
    }

    public int browseNode(State state, int alpha, int beta, int deepness)
    {
        if (state.isOver() || deepness == 0)
        {
            this.countNode += 1;
            return state.getScore(state.getPlayer());
        }
        else
        {
            if (state.getPlayer() == this.player)
            {
                for (Move move : state.getMove(state.getPlayer()))
                {
                    this.countNode += 1;
                    State newState = state.play(move);
                    if (this.browseNode(newState, alpha, beta, deepness - 1) > alpha)
                        alpha = this.browseNode(newState, alpha, beta, deepness - 1);
                    if (alpha >= beta)
                        return alpha;
                }
            }
            else
            {
                for (Move move : state.getMove(state.getPlayer()))
                {
                    this.countNode += 1;
                    State newState = state.play(move);
                    if (this.browseNode(newState, alpha, beta, deepness - 1) < beta)
                        beta = this.browseNode(newState, alpha, beta, deepness - 1);
                    if (alpha >= beta)
                        return beta;
                }
            }
        }
        return 0;
    }

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
            int value = this.browseNode(nextState, alpha, beta, this.deepness);
            if (value > bestValue)
            {
                bestValue = value;
                bestMove = move;
            }
        }
        return bestMove;
    }
}
