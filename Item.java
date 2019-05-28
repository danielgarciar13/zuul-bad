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

    /**
     * Constructor for objects of class Item
     */
    public Item(String description, int weigth)
    {
        this.description = description;
        this.weigth = weigth;        
    }
    
    public String getDescription(){
        return description;
    }
    
    public int getWeigth(){
        return weigth;
    }
    
    public String toString(){
        return description + " Weigth: " + weigth;
    }
}
