package ia;

import game.*;

public class Negamax extends Minimax
{
    public Negamax(int player, int deepness)
    {
        super(player, deepness);
    }

    //cherche dans tous les states possible le plus score le plus optimal et le retourne
    @Override
    public int browseNode(State state, int deepness)
    {
        int res = Integer.MIN_VALUE;
        if (state.isOver() || deepness == 0)
        {   
            this.countNode += 1;
            if (this.player == state.getPlayer())
                return state.getScore(state.getPlayer());
            return -(state.getScore(state.getPlayer()));
        }
        else
        {
            for (Move move : state.getMove(state.getPlayer()))
            {
                this.countNode += 1;
                State newState = state.play(move);
                int next = this.browseNode(newState, deepness - 1);
                if (res < next)
                    res = next;
            }
        }
        return res;
    }
}
