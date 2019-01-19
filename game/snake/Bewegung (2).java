package game.snake;

public class Bewegung
{
    private double bewX, bewY;

    public Bewegung(double pBewX, double pBewY)
    {
        bewX = pBewX;
        bewY = pBewY;
    }

    public double gibBewegungX()
    {
        return bewX;
    }
    
    public double gibBewegungY()
    {
        return bewY;
    }
}
