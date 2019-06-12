import java.util.Stack;
import java.util.ArrayList;

/**
 * Write a description of class Player here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Player
{
    private Room currentRoom;
    private Stack lastRooms;
    private ArrayList<Item> items;
    
    /**
     * Constructor for objects of class Player
     */
    public Player(Room firstRoom)
    {
        currentRoom = firstRoom;
        lastRooms = new Stack();
        items = new ArrayList<>();
    }
    
    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    public void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);
        lastRooms.push(currentRoom);
        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println("You are in " + currentRoom.getDescription());
            look();
        }
    }
    
    public void look() {
        System.out.println(currentRoom.getItems());
        System.out.println(currentRoom.getLongDescription());
    }

    public void eat() {    
        System.out.println("You have eaten now and you are not hungry any more");
    }
    
    public void back() {    
        if(!lastRooms.empty()){
            currentRoom = (Room)lastRooms.pop();
            System.out.println("You are in " + currentRoom.getDescription());
            look();
        }
    }
    
    public void take(Command command){
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Elige la posicion del objeto");
        }
        else{
            if(currentRoom.getItem(Integer.parseInt(command.getSecondWord())) != null && currentRoom.getItem(Integer.parseInt(command.getSecondWord())).takeable()){
                items.add(currentRoom.getItem(Integer.parseInt(command.getSecondWord())));
                currentRoom.dropItem(Integer.parseInt(command.getSecondWord()));
            }
            getItems();
        }
    }
    
    private void getItems(){
        for(int c = 0; c < items.size(); c++){
            System.out.println(c + ": " + items.get(c).toString());;
        }
    }
    
    public Room getCurrentRoom(){
        return currentRoom;
    }
}
