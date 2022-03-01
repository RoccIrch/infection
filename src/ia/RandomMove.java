package ia;

import java.util.Random;

import game.*;

public class RandomMove 
{
    protected State state;

    public RandomMove(State state)
    {
        this.state = state;
    }
    //récupère un déplacement aléatoire dans la liste des déplacements possible
    public Move getRandomMove(int player)
    {
        if (this.state.getMove(player).isEmpty() == false)
        {
            Random randon = new Random();
            int rdn = randon.nextInt(this.state.getMove(player).size()); //      choisi un int au hasard selon la taille du array
                int i = 0;
                for (Move move : this.state.getMove(player))
                {
                    if(i == rdn)
                        return move;
                    i++;
                }
        }
        //si la liste est vide on retourne un skip !
        return new Move(7, 7, 7, 7, false, true);
    }
}
