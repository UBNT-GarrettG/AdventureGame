import java.util.*;
import java.io.*;

public class GameChar implements Serializable
{
  private final int MAX_NORTH_SOUTH;
  private final int MAX_EAST_WEST;
  private int northSouth;
  private int eastWest;
  private int visibility;
  private List<String> inventory = new ArrayList<String>(Arrays.asList("brass lantern", "rope", "rations", "staff"));
  public GameChar(int rows, int columns)
  {
    MAX_NORTH_SOUTH = rows - 1;
    MAX_EAST_WEST = columns - 1;
    northSouth = 0;
    eastWest = 0;
    visibility = 2;
  }

  public String GetInventory()
  {
    String message = "These are the items in your inventory: \n";
    for(String item : inventory){
      message += item + "\n";
    }
    return message;
  }

  public void PrintCoordinates()
  {
    System.out.println("Your coordinates are (" + northSouth + ", " + eastWest + ")");
  }

  public String Move(String direction)
  {
    String message = "";
    switch(direction){
      case "n":
        if(northSouth == 0){
          message = "You cannot go any further North!";
        }
        else{
          message = "Moving North...";
          northSouth--;
        }
        break;
      case "s":
        if(northSouth < MAX_NORTH_SOUTH){
          message = "Moving South...";
          northSouth++;
        }
        else{
          message = "You cannot go any further South!";
        }
        break;
      case "e":
        if(eastWest < MAX_EAST_WEST){
          message = "Moving East...";
          eastWest++;
        }
        else{
          message = "You cannot go any further East!";
        }
        break;
      case "w":
        if(eastWest == 0){
          message = "You cannot go any further West!";
        }
        else{
          message = "Moving West...";
          eastWest--;
        }
        break;
      default:
        message = "You did not put in a valid direction";
    }
    return message;
  }

  public String Drop(String itemName, Adventure adventure)
  {
    boolean itemDropped = false;
    for (String item : inventory){
      if (itemName.equals(item)){
          int index = inventory.indexOf(item);
          inventory.remove(index);
          itemDropped = true;
          break;
      }
    }

    if (itemDropped){
      adventure.AddItem(itemName);
      return itemName + " dropped";
    }
    else{
      return itemName + " is not in your inventory";
    }
  }

  public String Take(String itemName, Adventure adventure)
  {
    String message;
    if (adventure.PopItem(itemName)){
      inventory.add(itemName);
      message = "Added " + itemName + " to inventory";
    }
    else{
      message = "Could not take " + itemName + " invalid item";
    }
    return message;
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
