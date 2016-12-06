import java.io.*;

public class Item implements Serializable
{
  private String location;
  private String name;

  public Item(String loc, String desc)
  {
    location = loc;
    name = desc;
  }

  public String getLoc()
  {
    return location;
  }

  public String getName()
  {
    return name;
  }
}
