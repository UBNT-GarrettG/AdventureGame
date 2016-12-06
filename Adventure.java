import java.util.Scanner;
import java.util.*;
import java.io.*;

public class Adventure implements Serializable
{
  private boolean quit = false;
  private GameChar playerOption;
  private Map gameMap;
  private String itemFilePath;
  private List<Item> items = new ArrayList<Item>();
  private transient Scanner scanner;

  public Adventure(String mapFile)
  {
    gameMap = new Map(mapFile);
    playerOption = new GameChar(gameMap.GetRows(), gameMap.GetColumns());
  }

  public String InitialMap()
  {
    return gameMap.PrintTerrain(playerOption);
  }

  public Map GetMap()
  {
    return gameMap;
  }

  public String SeparateItemAndPic()
  {
    String[] parts = {};
    String tileSize = "";
    parts = gameMap.GetFileContents().split(";");
    tileSize = parts[0];
    itemFilePath = parts[1];
    SetItems();
    return tileSize;
  }

  public void SetItems()
  {
    try{
      scanner = new Scanner(new File(itemFilePath));
    }catch (FileNotFoundException x){
      System.out.println("File open failed");
      x.printStackTrace();
      System.exit(0);
    }

    String line;

    while (scanner.hasNextLine()){
      line = scanner.nextLine();
      String[] parts = {};
      String[] stripA = {};
      parts = line.split(";");
      String location = parts[0] + "," + parts[1];
      stripA = parts[2].split(" ");
      String description = "";
      for (int i = 1; i < stripA.length; i++){
        if (i == stripA.length - 1)
          description += stripA[i];
        else
          description += stripA[i] + " ";
      }
      Item newItem = new Item(location, description);
      items.add(newItem);
    }
  }

  public void AddItem(String item)
  {
    if (item != ""){
      Item newItem = new Item(playerOption.GetCoordinates(), item);
      items.add(newItem);
    }
  }

  public boolean PopItem(String itemName)
  {
    boolean itemRemoved = false;
    for (Item item : items){
      if (itemName.equals(item.getName())){
        int index = items.indexOf(item);
        items.remove(index);
        itemRemoved = true;
        break;
      }
    }

    if (!itemRemoved){
      itemName = "a " + itemName;
      for (Item item : items){
        if (itemName.equals(item.getName())){
          int index = items.indexOf(item);
          items.remove(index);
          itemRemoved = true;
          break;
        }
      }
    }
    return itemRemoved;
  }

  public String CheckItems()
  {
    String outString = "";
    for (Item item : items){
      if (item.getLoc().equals(playerOption.GetCoordinates())){
        outString += "\nThere is a " + item.getName() + " in this location";
      }
    }
    return outString;
  }

  public String Process(String message)
  {
      String firstLetter = "";
      String[] parts = {};
      String direction = "";
      if (message.equals("Load")){
        message = "Your game has been opened.";
      }
      else{
        message = message.toLowerCase();
        firstLetter = message.substring(0,1);
        if (firstLetter.equals("q")){
          quit = true;
        }
        else if (firstLetter.equals("i")){
          message = playerOption.GetInventory();
        }
        else if (firstLetter.equals("g")){
          parts = message.split(" ");
          direction = parts[1];
          firstLetter = direction.substring(0,1);
          message = playerOption.Move(firstLetter);
          message += CheckItems();
        }
        else if (firstLetter.equals("d")){
          parts = message.split(" ");
          if (parts.length > 1){
            String itemName = "";
            for (int i = 1; i < parts.length; i++){
              if (i == parts.length - 1)
                itemName += parts[i];
              else
                itemName += parts[i] + " ";
            }
            message = playerOption.Drop(itemName, this);
            }
          }
          else if (firstLetter.equals("t")){
            parts = message.split(" ");
            if (parts.length > 1){
              String itemCheck = CheckItems();
              if (itemCheck != ""){
                String itemName = "";
                for (int i = 1; i < parts.length; i++){
                  if (i == parts.length - 1)
                  itemName += parts[i];
                  else
                  itemName += parts[i] + " ";
                }
                message = playerOption.Take(itemName, this);
              }
              else{
                message = "The item you entered is not available to take.";
              }
            }
          }
          else{
            message = "Invalid command : " + message;
          }
        }
      message += "\nWhat would you like to do now?";
      message += "\nYour coordinates are: " + playerOption.GetCoordinates();
      message += ";" + gameMap.PrintTerrain(playerOption);

      return message;
  }


  public static void main (String[] args)
  {
    GameGui gui = new GameGui(args[0]);
    gui.setSize(600, 600);
    gui.setVisible(true);
  }

}
