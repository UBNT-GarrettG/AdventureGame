import java.io.*;
import java.util.*;

public class Map
{
  private int rows;
  private int columns;
  private String[] map;
  private char[][] grid;
  private String mapFile;
  private Scanner scanner = null;

  public Map(String filename)
  {
    mapFile = filename;
    this.SetRowsColsAndMap();
    grid = new char[rows][columns];
    this.SetGrid();
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

  public void PrintTerrain(GameChar location)
  {
    String[] position = location.GetCoordinates().split(",");
    int row = Integer.parseInt(position[0]);
    int column = Integer.parseInt(position[1]);
    int vis = location.GetVis();

    int minRow = row - vis;
    int maxRow = row + vis;
    int minCol = column - vis;
    int maxCol = column + vis;

    System.out.println("You are in terrain " + grid[row][column]);

    if (minRow >= 0 && minCol >= 0){
      PrintTerrainTile(minRow, minCol);
    }
    else{
      PrintOB();
    }

    if (minRow >= 0){
      PrintTerrainTile(minRow, column);
    }
    else{
      PrintOB();
    }

    if (minRow >= 0 && maxCol < columns){
      PrintTerrainTile(minRow, maxCol);
    }
    else{
      PrintOB();
    }

    System.out.print("\n");

    if (minCol >= 0){
      PrintTerrainTile(row, minCol);
    }
    else{
      PrintOB();
    }

    PrintTerrainTile(row, column);

    if (maxCol < columns){
      PrintTerrainTile(row, maxCol);
    }
    else{
      PrintOB();
    }

    System.out.print("\n");

    if (maxRow < rows && minCol >= 0){
      PrintTerrainTile(maxRow, minCol);
    }
    else{
      PrintOB();
    }

    if (maxRow < rows){
      PrintTerrainTile(maxRow, column);
    }
    else{
      PrintOB();
    }

    if (maxRow < rows && maxCol < columns){
      PrintTerrainTile(maxRow, maxCol);
    }
    else{
      PrintOB();
    }

    System.out.print("\n");

  }

  private void PrintTerrainTile(int row, int column)
  {
    System.out.print(grid[row][column]);
  }

  private void PrintOB()
  {
    char outOfBounds = 'X';
    System.out.print(outOfBounds);
  }

  private void SetRowsColsAndMap()
  {
    boolean firstLine = true;
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
      else{
        map[index] = line;
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
