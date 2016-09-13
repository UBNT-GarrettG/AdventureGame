import java.util.Scanner;

public class Adventure
{
  public static void main (String[] args)
  {
    Scanner scanner = new Scanner(System.in);
    final int MAX_NORTH_SOUTH = 4;
    final int MAX_EAST_WEST = 4;
    int northSouth = 0;
    int eastWest = 0;
    String[] inventory =  new String[]{"brass lantern", "rope", "rations", "staff"};
    String choice = "";
    String firstLetter = "";
    String[] parts = {};
    String direction = "";
    boolean quit = false;

    while (!quit)
    {
      System.out.println("What would you like to do?");
      System.out.print("> ");
      choice = scanner.nextLine();
      choice = choice.toLowerCase();
      firstLetter = choice.substring(0,1);
      if (firstLetter.equals("q"))
        quit = true;
      else if (firstLetter.equals("i")){
        System.out.println("These are the items in your inventory: ");
        for(String item : inventory){
          System.out.println(item);
        }
        System.out.println("Your coordinates are (" + northSouth + ", " + eastWest + ")");
      }
      else if (firstLetter.equals("g")){
        parts = choice.split(" ");
        direction = parts[1];
        firstLetter = direction.substring(0,1);
        switch(firstLetter){
          case "n":
            if(northSouth == 0){
              System.out.println("You cannot go any further North!");
            }
            else{
              System.out.println("Moving North...");
              northSouth--;
            }
            break;
          case "s":
            if(northSouth == MAX_NORTH_SOUTH){
              System.out.println("You cannot go any further South!");
            }
            else{
              System.out.println("Moving South...");
              northSouth++;
            }
            break;
          case "e":
            if(eastWest == MAX_EAST_WEST){
              System.out.println("You cannot go any further East!");
            }
            else{
              System.out.println("Moving East...");
              eastWest++;
            }
            break;
          case "w":
            if(eastWest == 0){
              System.out.println("You cannot go any further West!");
            }
            else{
              System.out.println("Moving West...");
              eastWest--;
            }
            break;
          default:
            System.out.println("You did not put in a valid direction");
        }
        System.out.println("Your coordinates are (" + northSouth + ", " + eastWest + ")");
      }
      else{
        System.out.println("Invalid command : " + choice);
      }
    }
  }
}
