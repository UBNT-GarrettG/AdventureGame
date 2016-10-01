import java.util.Scanner;

public class Adventure
{
  public static void main (String[] args)
  {
    Scanner scanner = new Scanner(System.in);
    Map gameMap = new Map(args[0]);
    GameChar playerOption = new GameChar(gameMap.GetRows(), gameMap.GetColumns());
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
      if (firstLetter.equals("q")){
        quit = true;
        break;
      }
      else if (firstLetter.equals("i")){
        playerOption.ShowInventory();
      }
      else if (firstLetter.equals("g")){
        parts = choice.split(" ");
        direction = parts[1];
        firstLetter = direction.substring(0,1);
        playerOption.Move(firstLetter);
      }
      else{
        System.out.println("Invalid command : " + choice);
      }
      playerOption.PrintCoordinates();
      gameMap.PrintTerrain(playerOption);
    }

  }
}
