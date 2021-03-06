import java.util.HashMap;
import java.util.ArrayList;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */
public class Room 
{
    private String description;
    private HashMap<String, Room> rooms;
    private ArrayList<Item> items;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        rooms = new HashMap<>();
        items = new ArrayList<>();
    }

    public Room getExit(String direction){
        Room room = null;
        if(rooms.containsKey(direction)) {
            room = rooms.get(direction);
        }
        return room;
    }

    /**
     * Devuelve la informaci�n de las salidas existentes
     * Por ejemplo: "Exits: north east west"
     *
     * @return Una descripci�n de las salidas existentes.
     */
    public String getExitString(){
        String exits = "Exits: ";
        for(String exit : rooms.keySet()){
            exits += exit + " ";
        }
        return exits;
    }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param north The north exit.
     * @param east The east east.
     * @param south The south exit.
     * @param west The west exit.
     */
    public void setExit(String direction, Room room) 
    {
        rooms.put(direction, room);
    }

    /**
     * Devuelve un texto con la descripcion larga de la habitacion del tipo:
     *     You are in the 'name of room'
     *     Exits: north west southwest
     * @return Una descripcion de la habitacion incluyendo sus salidas
     */
    public String getLongDescription(){
        return "You are in " + description + "\n" + getExitString();
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

    public String getItems(){
        String infoItem = "";
        for(int c = 0; c < items.size(); c++){
            infoItem += c + ": " + items.get(c).toString() + "\n";
        }
        return infoItem;
    }

    public Item getItem(int index){
        Item item = null;
        if(index <= items.size() - 1){
            item = items.get(index);
        }
        return item;
    }

    public void addItem(Item item){
        items.add(item);
    }

    public void dropItem(int index){
        items.remove(index);
    }
}
