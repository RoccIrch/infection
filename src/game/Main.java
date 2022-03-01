package game;

import java.util.ArrayList;
import ia.*;


public class Main
{
    public static final String COLORTEXT_BLUE = "\u001B[34m";
    public static final String COLORTEXT_RED = "\u001B[31m";
    public static final String COLORTEXT_RESET = "\u001B[0m";

    public static void main(String[] args) {
        //création des deux joueur et assignation des algorithmes selon les arguments.
        Algorithme player1 = new Minimax(1, Integer.parseInt(args[0]));
        Algorithme player2 = new Negamax(2, Integer.parseInt(args[1]));

        if (args.length == 3)
        {
            if (Integer.parseInt(args[2]) == 1)
            {
                player1 = new AlphabetaForMinimax(1, Integer.parseInt(args[0]));
                player2 = new AlphabetaForNegamax(2, Integer.parseInt(args[1]));
            }
        }
        else
        {
            System.out.println("S'il vous plait utilisez la syntaxe suivante");
            System.out.println("java main profondeur1 profondeur2 alphabeta");
            System.out.println("Ou profondeur1 et 2 est in entier qui représente la profondeur de recherche (conseil ne pas dépasser 5)");
            System.out.println("Et alphabeta est un boolean qui indique l'utilisation d'alphabeta ou non (1=oui, 0=non)");
            System.exit(0);
        }

        //création de l'état initial, la condition de fin et de la liste des états passés.

        ArrayList<State> allState = new ArrayList<>();
        State actualState = new State();
        boolean isOver = false;
        Move move;

        while(!(isOver))
        {
            //Recherche du meilleur coup sur un clone de l'état principale
            allState.add(actualState);
            if (actualState.getPlayer() == 1)
                move = player1.getBestMove(actualState.getClone());
            else
                move = player2.getBestMove(actualState.getClone());

            //affichage du tour, du joueur et du coup joué
            System.out.println("=====================================");
            System.out.println("Tour " + actualState.getTurn() + ". C'est au tour du joueur n°" + actualState.getPlayer());
            if (move.isClone() == false && move.isSkip() == false)
                System.out.println("Le mouvement est un saut de : " + move.getPosiX() + "|" + move.getPosiY()+ "->" + move.getPosaX() + "|" + move.getPosaY());
            if (move.isClone() == true && move.isSkip() == false)
                System.out.println("Le mouvement est un clone de : " + move.getPosiX() + "|" + move.getPosiY()+ "->" + move.getPosaX() + "|" + move.getPosaY());
            if (move.isSkip() == true)
                System.out.println("Le joueur passe sont tour");

            //on joue le coup selectionné par l'algorithme
            actualState = actualState.play(move);

            //on affiche le plateau après le coup
            for (int[] x : actualState.getBoard())
                {
                    for (int y : x)
                    {
                        if (y == 1)
                            System.out.print(COLORTEXT_BLUE + y + COLORTEXT_RESET + "  ");
                        else if (y == 2)
                            System.out.print(COLORTEXT_RED + y + COLORTEXT_RESET + "  ");
                        else
                            System.out.print(y + "  ");
                    }
                    System.out.println();
                }

            // récapitulatif du nombres de pions par joueur
            System.out.println("Les scores sont maintenant : " + COLORTEXT_BLUE + actualState.countPawn(1) + COLORTEXT_RESET +" , "+ COLORTEXT_RED + actualState.countPawn(2) + COLORTEXT_RESET);

            //vérification des conditions de fin
            isOver = actualState.isOver();
            if (allState.contains(actualState))
                isOver = true;
        }
        // Message de victoire et compte des noeuds parcourus par joueur
        System.out.println("Le joueur " + actualState.victory() + " a gagné !");
        System.out.println(COLORTEXT_BLUE +"Le joueur 1 à parcouru " + player1.getCountNode() + " noeuds lors de la partie"+ COLORTEXT_RESET);
        System.out.println(COLORTEXT_RED +"Le joueur 2 à parcouru " + player2.getCountNode() + " noeuds lors de la partie"+ COLORTEXT_RESET);
    }
}