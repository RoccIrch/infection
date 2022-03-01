package game;
public class Move
{
    protected int posi_x;
    protected int posi_y;
    protected int posa_x;
    protected int posa_y;
    protected boolean isClone;
    protected boolean isSkip;

    public Move(int posi_x, int posi_y, int posa_x, int posa_y, boolean isClone, boolean isSkip)
    {
        this.posi_x = posi_x;
        this.posi_y = posi_y;
        this.posa_x = posa_x; 
        this.posa_y = posa_y;
        this.isClone = isClone;
        this.isSkip = isSkip;
    }

    //Getter.
    public int getPosaX ()
    {
        return this.posa_x;
    }
    public int getPosaY ()
    {
        return this.posa_y;
    }
    public int getPosiX ()
    {
        return this.posi_x;
    }
    public int getPosiY ()
    {
        return this.posi_y;
    }
    public boolean isClone()
    {
        return this.isClone;
    }
    public boolean isSkip()
    {
        return this.isSkip;
    }
    @Override
    public String toString() {
        return this.getPosiX() + " " + this.getPosiY() +" " +  this.getPosaX() +" " +  this.getPosaY() +" " +  this.isClone;
    }
}