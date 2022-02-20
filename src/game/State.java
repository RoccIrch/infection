package game;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class State
{
    protected int[][] board;
    protected int turn;
    protected int player;
    protected boolean lastMoveIsSkip = false;

    public State()
    {
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
        this.turn = lastState.getTurn() + 1;

        if (lastState.getPlayer() == 1)
            this.player = 2;
        if (lastState.getPlayer() == 2)
            this.player = 1;

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
    /* private void setBoard(int[][] board)
    {
        this.board = board;
    }
    private void setPlayer(int player)
    {
        this.player = player;
    } */

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

    public int countPawn(int player)
    {
        int count = 0;
        for (int i = 0 ; i <= 6 ; i++)
            for (int j = 0 ; j <= 6 ; j++)
                if (this.board[i][j] == player)
                    count += 1;
        return count;
    }

    public int getScore(int player)
    {
        if (this.player == 1)
            return this.countPawn(1)/ (this.countPawn(1) + this.countPawn(2));
        return this.countPawn(2) / (this.countPawn(2) + this.countPawn(1));
    }

    public State play(Move move)
    {   
        return new State(this, move);
    }

    public boolean isOver()
    {
        if (this.countPawn(1) == 0 || this.countPawn(2) == 0)
        {
            //System.out.println("Les deux joueurs ont tous les deux 0 pions, la partie est terminée");
            return true;
        }  
        if (this.lastMoveIsSkip() && this.getMove(this.player).isEmpty())
        {
            //System.out.println("Le joueur n°" + this.getPlayer() + " passe aussi son tour. La partie est terminée !");
            return true;
        }
        /*
        if (this.getPastBoards().size() != 0 || this.getPastBoards() != null)
        {
            for (int k = 0; k < this.getPastBoards().size(); k++)
            {
                int count = 0;
                for(int i = 0 ; i <= 6 ; i++)
                {
                    for(int j = 0 ; j <= 6 ; j++)
                    {
                        if(this.getPastBoards().get(k)[i][j] == this.getBoard()[i][j])
                            count += 1;
                    }
                }
                if (count == 49)
                {
                    System.out.println(this.getPastBoards().toString());
                    return false;
                }

                else
                    count = 0;
            }
        }
        */
        return false;
    }

    public Move getRandomMove(int player)
    {
        if (this.getMove(player).isEmpty() == false)
        {
            Random randon = new Random();
            int rdn = randon.nextInt(this.getMove(player).size()); //      choisi un int au hasard selon la taille du array
                int i = 0;
                for (Move move : this.getMove(player))
                {
                    if(i == rdn)
                        return move;
                    i++;
                }
        }
        return new Move(7, 7, 7, 7, false, true);
    }

    /* public State clone()
    {
        State s = new State();
        s.board = this.board.clone();
        s.player = this.player;
        s.turn = this.turn;
        s.lastMoveIsSkip = false;
        return s;
    } */
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
}