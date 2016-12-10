import java.io.*;

public class Pic implements Serializable
{
  private String symbol;
  private String terrainName;
  private String path;

  public Pic(String s, String n, String p)
  {
    symbol = s;
    terrainName = n;
    path = p;
  }

  public String GetSymbol()
  {
    return symbol;
  }

  public String GetPath()
  {
    return path;
  }

  public String GetName()
  {
    return terrainName;
  }
}
