package ia;

import game.*;

public class AlphabetaForNegamax extends AlphabetaForMinimax
{
    public AlphabetaForNegamax(int player, int deepness)
    {
        super(player, deepness);
    }

    //recherche du noeud le plus optimal en ne s'attardant pas sur les noeud ou alpha > beta. 
    @Override
    public int browseNode(State state, int alpha, int beta, int deepness)
    {
        if (state.isOver() || deepness == 0)
        {   
            this.countNode += 1;
            if (this.player == state.getPlayer())
                return state.getScore(state.getPlayer());
            return -(state.getScore(state.getPlayer()));
        }
        for (Move move : state.getMove(state.getPlayer()))
        {
            this.countNode += 1;
            State newState = state.play(move);
            if (this.browseNode(newState, alpha, beta, deepness - 1) > alpha)
                alpha = this.browseNode(newState, alpha, beta, deepness - 1);
            if (alpha >= beta)
                return alpha;
        }
        return alpha;
    }
}