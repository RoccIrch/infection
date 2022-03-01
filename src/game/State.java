package game;

import java.util.HashSet;
import java.util.Set;

public class State
{
    protected int[][] board;
    protected int turn;
    protected int player;
    protected boolean lastMoveIsSkip = false;

    public State()
    {
        //création de l'état initial : plateau, tour 1, joueur 1.
        this.board = new int[7][7];
        for(int i = 0 ; i <= 6 ; i++){for(int j = 0 ; j <= 6 ; j++){this.board[i][j] = 0;}}
        this.board[0][0] = 2;
        this.board[6][6] = 2;
        this.board[0][6] = 1;
        this.board[6][0] = 1;

        this.turn = 1;
        this.player = 1;
    }

    public State(State lastState, Move move)
    {
        //Tour suivant 
        this.turn = lastState.getTurn() + 1;

        //Changement du joueur actif
        if (lastState.getPlayer() == 1)
            this.player = 2;
        if (lastState.getPlayer() == 2)
            this.player = 1;

        //récupération de l'ancien plateau et bouge les pions selon le mouvement.
        this.board = lastState.getBoard();
        if (move.isSkip())
        {
            this.lastMoveIsSkip = true;
        }
        else
        {
            this.board[move.getPosaX()][move.getPosaY()] = lastState.getPlayer();
            if (move.isClone() == false)
               this.board[move.getPosiX()][move.getPosiY()] = 0;
            //Puis on infecte les pions autour du pions qui vient de bouger. 
            for (int a = -1 ; a <=1 ; a++)
            {
                for (int b = -1 ; b <= 1 ; b++)
                {
                    if (move.getPosaX()+a <= 6 && move.getPosaX()+a >= 0 && move.getPosaY()+b <= 6 && move.getPosaY()+b >=0)
                            if (this.board[move.getPosaX()+a][move.getPosaY()+b] == this.player)
                                this.board[move.getPosaX()+a][move.getPosaY()+b] = lastState.getPlayer();
                }
            }
        }
    }

    //crée un nouveau state qui est considéré comme le suivant.
    public State play(Move move)
    {
        return new State(this, move);
    }

    //renvoie la liste de tous les mouvement possible pour le joueur pris en argument.
    public Set<Move> getMove(int player)
    {
        HashSet<Move> allMoves = new HashSet<Move>();
        for (int i = 0 ; i <= 6 ; i++)
        {
            for (int j = 0 ; j <= 6 ; j++)
            {
                if (this.board[i][j] == player)
                {
                    //clone
                    for (int a = -1 ; a <=1 ; a++)
                        for (int b = -1 ; b <= 1 ; b++){
                            if (i+a <= 6 && i+a >= 0 && j+b <= 6 && j+b >=0 && this.board[i+a][j+b] == 0)
                            {
                                Move move = new Move(i, j, i+a, j+b, true, false);
                                allMoves.add(move);
                            }
                            if (i+a <= 6 && i+a >= 0 && j+b <= 6 && j+b >=0 && this.board[i+a][j+b] != 0)
                            {
                                if (i+a*2 <= 6 && i+a*2 >= 0 && j+b*2 <= 6 && j+b*2 >=0)
                                {
                                    if (this.board[i+a*2][j+b*2] == 0)
                                    {
                                        Move move = new Move(i, j, i+a*2, j+b*2, false, false);
                                        allMoves.add(move);
                                    }
                                }
                            }
                        }
                }
            }
        }
        return allMoves;
    }

    // vérifie si le tableau ou si les deux joueurs passe leur tour en meme temps et renvoie un booleen indiquant si la partie est terminé 
    public boolean isOver()
    {
        if (this.countPawn(1) == 0 || this.countPawn(2) == 0)
        {
            return true;
        }
        if (this.lastMoveIsSkip() && this.getMove(this.player).isEmpty())
        {
            return true;
        }
        return false;
    }

    //renvoie un cole du state actuel.
    public State getClone()
    {
        State cloneState = new State();

        for(int i=0;i<7;i++){
			for(int j=0;j<7;j++){
				cloneState.board[i][j]=this.board[i][j];
			}
		}
        cloneState.lastMoveIsSkip = false;
        cloneState.player = this.player;
        cloneState.turn = this.player;

        return cloneState;
    }

    //renvoie un int représentant le joueur victorieux ou 0 si match nul.
    public int victory()
    {
        if (this.countPawn(1) == this.countPawn(2))
            return 0;
        else if (this.countPawn(1) > this.countPawn(2))
            return 1;
        return 2;
    }

    //Getter.
    public int[][] getBoard()
    {
        return this.board;
    }
    public int getTurn()
    {
        return this.turn;
    }
    public int getPlayer()
    {
        return this.player;
    }
    public boolean lastMoveIsSkip()
    {
        return this.lastMoveIsSkip;
    }
    //calcul du nombre de pions du joueur en argument.
    public int countPawn(int player)
    {
        int count = 0;
        for (int i = 0 ; i <= 6 ; i++)
            for (int j = 0 ; j <= 6 ; j++)
                if (this.board[i][j] == player)
                    count += 1;
        return count;
    }
    //retourne le score du joueur pris en argument (ce score servira de donnée heuristique pour les algorithmes).
    public int getScore(int player)
    {
        if (this.player == 1)
            return this.countPawn(1)/ (this.countPawn(1) + this.countPawn(2));
        return this.countPawn(2) / (this.countPawn(2) + this.countPawn(1));
    }
}