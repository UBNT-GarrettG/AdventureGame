
public class GameChar
{
  private final int MAX_NORTH_SOUTH;
  private final int MAX_EAST_WEST;
  private int northSouth;
  private int eastWest;
  private int visibility;
  private String[] inventory =  new String[]{"brass lantern", "rope", "rations", "staff"};

  public GameChar(int rows, int columns)
  {
    MAX_NORTH_SOUTH = rows - 1;
    MAX_EAST_WEST = columns - 1;
    northSouth = 0;
    eastWest = 0;
    visibility = 1;
  }

  public void ShowInventory()
  {
    System.out.println("These are the items in your inventory: ");
    for(String item : inventory){
      System.out.println(item);
    }
  }

  public void PrintCoordinates()
  {
    System.out.println("Your coordinates are (" + northSouth + ", " + eastWest + ")");
  }

  public void Move(String direction)
  {
    switch(direction){
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
        if(northSouth < MAX_NORTH_SOUTH){
          System.out.println("Moving South...");
          northSouth++;
        }
        else{
          System.out.println("You cannot go any further South!");
        }
        break;
      case "e":
        if(eastWest < MAX_EAST_WEST){
          System.out.println("Moving East...");
          eastWest++;
        }
        else{
          System.out.println("You cannot go any further East!");
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
  }

  public String GetCoordinates()
  {
    return northSouth + "," + eastWest;
  }

  public int GetVis()
  {
    return visibility;
  }

}
