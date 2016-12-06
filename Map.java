import java.io.*;
import java.util.*;

public class Map implements Serializable
{
  private final int PIC_PATHS = 7;
  private int rows;
  private int columns;
  private String[] map;
  private char[][] grid;
  private String mapFile;
  private String tileSize;
  private String itemFilePath;
  private String[] picFilePaths;
  private transient Scanner scanner = null;

  public Map(String filename)
  {
    mapFile = filename;
    picFilePaths = new String[PIC_PATHS];
    this.SetRowsColsAndMap();
    grid = new char[rows][columns];
    this.SetGrid();
  }

  public String GetFileContents()
  {
    String returnString = "";
    returnString = tileSize + ";" + itemFilePath;
    return returnString;
  }

  public String[] GetpicFiles()
  {
    return picFilePaths;
  }

  public int GetRows()
  {
    return rows;
  }

  public int GetColumns()
  {
    return columns;
  }

  public char GetTerrain(int row, int column)
  {
    return grid[row][column];
  }

  public String PrintTerrain(GameChar location)
  {
    String[] position = location.GetCoordinates().split(",");
    int row = Integer.parseInt(position[0]);
    int column = Integer.parseInt(position[1]);
    int vis = location.GetVis();

    int minRow = row - vis;
    int maxRow = row + vis;
    int minCol = column - vis;
    int maxCol = column + vis;

    char[] message = new char[30];
    int index = 0;

    for (int i = minRow; i <= maxRow; i++){
      if (i < 0 || i >= rows){
        for (int j = minCol; j <= maxCol; j++){
          message[index] = PrintOB();
          index++;
        }
      }
      else{
        for(int j = minCol; j <= maxCol; j++){
          if (j < 0 || j >= columns){
            message[index] = PrintOB();
          }
          else {
            message[index] = PrintTerrainTile(i, j);
          }
          index++;
        }
      }
      index++;
    }
    String newMessage = new String(message);
    return newMessage;
  }

  private char PrintTerrainTile(int row, int column)
  {
    return grid[row][column];
  }

  private char PrintOB()
  {
    char outOfBounds = 'X';
    return outOfBounds;
  }

  private void SetRowsColsAndMap()
  {
    boolean firstLine = true;
    boolean gotTileSize = false;
    boolean gotItemFile = false;
    int index = 0;
    try {
      scanner = new Scanner(new File(mapFile));
    } catch (FileNotFoundException x){
      System.out.println("File open failed");
      x.printStackTrace();
      System.exit(0);
    }
    String line;
    while (scanner.hasNextLine()){
      line = scanner.nextLine();
      if (firstLine){
        String[] parts = {};
        parts = line.split(" ");
        rows = Integer.parseInt(parts[0]);
        columns = Integer.parseInt(parts[1]);
        map = new String[rows];
        firstLine = false;
      }
      else if (index < rows && !gotTileSize){
        map[index] = line;
        index++;
      }
      else if (index >= rows && !gotTileSize) {
        tileSize = line;
        gotTileSize = true;
      }
      else if (gotTileSize && !gotItemFile){
        itemFilePath = line;
        gotItemFile = true;
        index = 0;
      }
      else if (gotTileSize && gotItemFile){
        picFilePaths[index] = line;
        index++;
      }

    }
  }

  private void SetGrid()
  {
    for (int i = 0; i < rows; i++){
      for (int j = 0; j < columns; j++){
        grid[i][j] = map[i].charAt(j);
      }
    }
  }

}
