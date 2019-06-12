/**
 * Write a description of class Item here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Item
{
    // instance variables - replace the example below with your own
    private String description;
    private int weigth;
    private boolean takeable;

    /**
     * Constructor for objects of class Item
     */
    public Item(String description, int weigth, boolean takeable)
    {
        this.description = description;
        this.weigth = weigth;
        this.takeable = takeable;
    }
    
    public String getDescription(){return description;}
    
    public int getWeigth(){return weigth;}
    
    public boolean takeable(){return takeable;}
    
    public String toString(){
        return description + " Weigth: " + weigth;
    }
}
