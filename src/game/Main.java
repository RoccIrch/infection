package game;

import java.util.ArrayList;

import ia.*;

public class Main
{
    /*public static void main(String[] args) {
        State game = new State();
        while (!(game.isOver()))
        {              
            //Move move = game.getRandomMove(game.getPlayer());
            State copyOfGame = game.clone();
            Minimax minimax = new Minimax(copyOfGame.getPlayer(), 1);
            Move move = minimax.getBestMove(copyOfGame);


            System.out.println("================================================");
            System.out.println("Tour " + game.getTurn() + ". C'est au tour du joueur n°" + game.getPlayer());
            if (move.isClone == false && move.isSkip == false)
                System.out.println("Le mouvement est un saut de : " + move.getPosiX() + "|" + move.getPosiY()+ "->" + move.getPosaX() + "|" + move.getPosaY());
            if (move.isClone == true && move.isSkip == false)
                System.out.println("Le mouvement est un clone de : " + move.getPosiX() + "|" + move.getPosiY()+ "->" + move.getPosaX() + "|" + move.getPosaY());
            if (move.isSkip == true)
                System.out.println("Le joueur passe sont tour");

            game = game.play(move);
            
            for (int[] x : game.getBoard())
                {
                    for (int y : x)
                    {
                            System.out.print(y + "  ");
                    }
                    System.out.println();
                }
            
            System.out.println("Les scores sont maintenant : " + game.countPawn(1) +" , "+  game.countPawn(2));
            //System.out.println(game.getAllBoard().toString());
        }
        if (game.countPawn(1) > game.countPawn(2))
            System.out.println("Le joueur 1 remporte la partie !!");
        else
            System.out.println("Le joueur 2 remporte la partie !!");
    }*/

    public static void main(String[] args) {
        ArrayList<State> allState = new ArrayList<>();
        State actualState = new State();
        boolean isOver = false; 

        Algorithme player1 = new AlphabetaForMinimax(1, 5);
        Algorithme player2 = new AlphabetaForNegamax(2, 5);
        Move move;// actualState.getRandomMove(actualState.player);
        //State copyOfState;

        while(!(isOver))
        {   
            allState.add(actualState);
            
            if (actualState.getPlayer() == 1)
                move = player1.getBestMove(actualState.getClone());
            else 
                move = player2.getBestMove(actualState.getClone()); 


            System.out.println("================================================");
            System.out.println("Tour " + actualState.getTurn() + ". C'est au tour du joueur n°" + actualState.getPlayer());
            if (move.isClone == false && move.isSkip == false)
                System.out.println("Le mouvement est un saut de : " + move.getPosiX() + "|" + move.getPosiY()+ "->" + move.getPosaX() + "|" + move.getPosaY());
            if (move.isClone == true && move.isSkip == false)
                System.out.println("Le mouvement est un clone de : " + move.getPosiX() + "|" + move.getPosiY()+ "->" + move.getPosaX() + "|" + move.getPosaY());
            if (move.isSkip == true)
                System.out.println("Le joueur passe sont tour");

            //move = actualState.getRandomMove(actualState.getPlayer()); 
            actualState = actualState.play(move);
            
            
            for (int[] x : actualState.getBoard())
                {
                    for (int y : x)
                    {
                            System.out.print(y + "  ");
                    }
                    System.out.println();
                }
            
            System.out.println("Les scores sont maintenant : " + actualState.countPawn(1) +" , "+  actualState.countPawn(2));
            
            isOver = actualState.isOver();
            if (allState.contains(actualState))
            {
                System.out.println("//////////////////////////////////////////////////////////////////////////////////////////////////////////////////");
                isOver = true;
            }
        }
        if (actualState.countPawn(1) > actualState.countPawn(2))
            System.out.println("Le joueur 1 remporte la partie !! et a parcouru :" + player1.getCountNode());
        else
            System.out.println("Le joueur 2 remporte la partie !!" + player2.getCountNode());
    }
}