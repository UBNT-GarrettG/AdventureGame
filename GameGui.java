import java.io.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import javax.swing.filechooser.*;

public class GameGui extends JFrame implements ActionListener
{
  private BoxLayout boxLayout = new BoxLayout(getContentPane(), BoxLayout.Y_AXIS);
  private JPanel button, map, text, commandPane, mapPane;
  private JButton quit, save, open, submit;
  private JTextField commands;
  private JTextArea transcript;
  private JFileChooser chooser;
  private Adventure adventure;
  private String imageSize, loadPath;
  private boolean loaded;
  private List<Pic> picPaths = new ArrayList<Pic>();

  public GameGui(String mapFile)
  {
    setLayout(boxLayout);
    button = new JPanel(new FlowLayout(FlowLayout.LEFT));
    map = new JPanel(new GridLayout(5, 5));
    mapPane = new JPanel();
    text = new JPanel();
    commandPane = new JPanel();
    quit = new JButton("Quit");
    save = new JButton("Save");
    open = new JButton("Open");
    submit = new JButton("Submit");
    chooser = new JFileChooser();
    button.add(open);
    button.add(save);
    button.add(quit);
    button.setBackground(Color.darkGray);
    map.setPreferredSize(new Dimension(300, 300));
    transcript = new JTextArea(10, 45);
    JScrollPane scroll = new JScrollPane(transcript, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
      JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    commands = new JTextField();
    commands.setColumns(45);
    text.add(scroll);
    text.setBackground(Color.darkGray);
    commandPane.add(commands);
    commandPane.add(submit);
    mapPane.add(map, BorderLayout.CENTER);
    add(button);
    add(mapPane);
    add(text);
    add(commandPane);
    pack();
    submit.addActionListener(this);
    save.addActionListener(this);
    open.addActionListener(this);
    quit.addActionListener(this);
    transcript.append("Welcome to CS3250 Adventure Game!\nDeveloped by Garrett Glad\n\nWhat would you like to do first?\n");
    loaded = false;
    getRootPane().setDefaultButton(submit);
    adventure = new Adventure(mapFile);
    SetPicPath();
    SetMap(adventure.InitialMap());
    imageSize = adventure.SeparateItemAndPic();
  }

  public void actionPerformed(ActionEvent e)
  {
    if (e.getActionCommand().equals("Submit")){
      String[] parts = {};
      String commandText = commands.getText();
      commands.setText("");
      String message = adventure.Process(commandText);
      parts = message.split(";");
      AppendTranscript(parts[0]);
      if (parts.length > 1){
        map.removeAll();
        SetMap(parts[1]);
      }
    }
    else if(e.getActionCommand().equals("Save")){
      if (!loaded){
        chooser.setCurrentDirectory(new File("."));
        int result = chooser.showSaveDialog(GameGui.this);

        if (result == JFileChooser.APPROVE_OPTION){
           loadPath = chooser.getSelectedFile().getPath();
          try{
            FileOutputStream fileOut = new FileOutputStream(loadPath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(adventure);
            out.close();
            fileOut.close();
            loaded = true;
            AppendTranscript("Game has been saved.");
          } catch(IOException i){
            i.printStackTrace();
          }
        }
      }
      else{
        try{
          FileOutputStream fileOut = new FileOutputStream(loadPath);
          ObjectOutputStream out = new ObjectOutputStream(fileOut);
          out.writeObject(adventure);
          out.close();
          fileOut.close();
          AppendTranscript("Game has been saved.");
        } catch(IOException i){
          i.printStackTrace();
        }
      }
    }
    else if(e.getActionCommand().equals("Open")){
      chooser.setCurrentDirectory(new File("."));
      int result = chooser.showOpenDialog(GameGui.this);

      if (result == JFileChooser.APPROVE_OPTION)
      {
        loadPath = chooser.getSelectedFile().getPath();
        try{
          FileInputStream fileIn = new FileInputStream(loadPath);
          ObjectInputStream in = new ObjectInputStream(fileIn);
          adventure = (Adventure) in.readObject();
          in.close();
          fileIn.close();
          String[] parts = {};
          String message = adventure.Process("Load");
          parts = message.split(";");
          AppendTranscript(parts[0]);
          if (parts.length > 1){
            map.removeAll();
            SetMap(parts[1]);
          }
          loaded = true;
        }catch(IOException i){
          i.printStackTrace();
          return;
        }catch(ClassNotFoundException c){
          c.printStackTrace();
          return;
        }
      }
    }
    else if(e.getActionCommand().equals("Quit")){
      System.exit(1);
    }
  }

  public void SetPicPath()
  {
    Map setMap = adventure.GetMap();
    String[] pics = setMap.GetpicFiles();
    String[] parts = {};
    for (String s : pics){
      parts = s.split(";");
      Pic pic = new Pic(parts[0], parts[1], parts[2]);
      picPaths.add(pic);
    }
  }

  public void SetMap(String mapString)
  {
    String current = "";

    for (int i = 0; i < mapString.length(); i++)
    {
      char c = mapString.charAt(i);
      String s = Character.toString(c);
      for (Pic p : picPaths){
        if (s.equals(p.GetSymbol())){
          current = p.GetPath();
        }
      }
      if (i == 14){
        String person = "person";
        for (Pic p : picPaths){
          if (person.equals(p.GetName())){
            current = p.GetPath();
          }
        }
      }
      if (current != ""){
        ImageComponent tile = new ImageComponent(current);
        map.add(tile);
      }
      current = "";
    }
    revalidate();
    repaint();
  }

  public void AppendTranscript(String message)
  {
    transcript.append(message + "\n");
  }

  private class ImageComponent extends JComponent
  {
    private Image image;

    public ImageComponent(String filepath)
    {
      image = new ImageIcon(filepath).getImage();
    }

    protected void paintComponent(Graphics g)
    {
      int imageWidth = image.getWidth(this);
      int imageHeight = image.getHeight(this);
      g.drawImage(image, 0, 0, imageWidth, imageHeight, null);
    }

    public Dimension getPreferredSize()
    {
      return new Dimension(100, 100);
    }
  }
}
