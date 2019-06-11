import java.util.Stack;
import java.util.ArrayList;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Stack lastRooms;
    private ArrayList<Item> items;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        lastRooms = new Stack();
        items = new ArrayList<>();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room europa, africa, asia, oceania, americaNorte, americaSur, antartida;

        // create the rooms
        europa = new Room("Europe");
        africa = new Room("Africa");
        asia = new Room("Asia");
        oceania = new Room("Oceania");
        americaNorte = new Room("America Norte");
        americaSur = new Room("America Sur");
        antartida = new Room("Antarctica");

        // initialise room exits
        europa.setExit("asia", asia);
        europa.setExit("africa", africa);
        europa.setExit("america-norte", americaNorte);
        africa.setExit("europa", europa);
        africa.setExit("asia", asia);
        africa.setExit("america-sur", americaSur);
        asia.setExit("oceania", oceania);
        asia.setExit("europa", europa);
        asia.setExit("africa", africa);
        oceania.setExit("asia", asia);
        oceania.setExit("antartida", antartida);
        americaNorte.setExit("europa", europa);
        americaNorte.setExit("america-sur", americaSur);
        americaSur.setExit("america-norte", americaNorte);
        americaSur.setExit("africa", africa);
        americaSur.setExit("antartida", antartida);
        antartida.setExit("america-sur", americaSur);
        antartida.setExit("oceania", oceania);

        // initialise room items
        africa.addItem(new Item("Un boligrafo", 99));
        africa.addItem(new Item("Un lapiz", 1));
        oceania.addItem(new Item("Un canguro", 100));
        oceania.addItem(new Item("Una goma", 1));

        currentRoom = europa;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println("You are in " + currentRoom.getDescription());
        printLocationInfo();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("look")) {
            look();
        }
        else if (commandWord.equals("eat")) {
            eat();
        }
        else if (commandWord.equals("back")) {
            back();
        }
        else if (commandWord.equals("take")) {
            take(command);
        }
        else if (commandWord.equals("drop")) {
            drop(command);
        }
        else if (commandWord.equals("items")) {
            getItems();
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("Your command words are:");
        System.out.println(parser.showCommands());
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
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
            System.out.println(currentRoom.getItems());
            printLocationInfo();
        }
    }

    private void printLocationInfo(){
        System.out.println(currentRoom.getLongDescription());
    }

    private void look() {
        System.out.println(currentRoom.getItems());
        System.out.println(currentRoom.getLongDescription());
    }

    private void eat() {    
        System.out.println("You have eaten now and you are not hungry any more");
    }

    private void back() {    
        if(!lastRooms.empty()){
            currentRoom = (Room)lastRooms.pop();
            System.out.println("You are in " + currentRoom.getDescription());
            System.out.println(currentRoom.getItems());
            printLocationInfo();
        }
    }

    private void take(Command command){
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Elige la posicion del objeto");
        }
        else{
            if(currentRoom.getItem(Integer.parseInt(command.getSecondWord())) != null && pesoEnMochila() + currentRoom.getItem(Integer.parseInt(command.getSecondWord())).getWeigth() < 100){
                items.add(currentRoom.getItem(Integer.parseInt(command.getSecondWord())));
                currentRoom.dropItem(Integer.parseInt(command.getSecondWord()));
            }
            else if(currentRoom.getItem(Integer.parseInt(command.getSecondWord())) != null && pesoEnMochila() + currentRoom.getItem(Integer.parseInt(command.getSecondWord())).getWeigth() >= 100){
                System.out.println("Demasiado peso");
            }
            getItems();
        }
    }

    private int pesoEnMochila(){
        int peso = 0;
        for(int c = 0; c < items.size(); c++){
            peso += items.get(c).getWeigth();
        }
        return peso;
    }

    private void drop(Command command){
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Elige la posicion del objeto");
        }
        else{
            if(currentRoom.getItem(Integer.parseInt(command.getSecondWord())) != null){
                currentRoom.addItem(items.get(Integer.parseInt(command.getSecondWord())));
                items.remove(Integer.parseInt(command.getSecondWord()));
                System.out.println(currentRoom.getItems());
            }
        }
    }

    private void getItems(){
        for(int c = 0; c < items.size(); c++){
            System.out.println(c + ": " + items.get(c).toString());;
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
