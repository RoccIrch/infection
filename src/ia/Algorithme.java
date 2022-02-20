package ia;

import game.*;

public abstract class Algorithme 
{
    protected int player;
    protected int deepness;
    protected double countNode = 0;

    public Algorithme(int player, int deepness)
    {
        this.player = player;
        this.deepness = deepness;
    }

    public double getCountNode()
    {
        return this.countNode;
    }

    public abstract Move getBestMove(State state);    
}
