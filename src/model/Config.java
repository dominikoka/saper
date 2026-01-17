package model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Config {
  String name;
  private ArrayList<BufferedImage> images;
  private ArrayList<BufferedImage> imagesBorder;
  List<Integer> configFile;
  
  public Config(String name) {
    this.name = name;
    images = new ArrayList<>();
    imagesBorder = new ArrayList<>();
    try {
      readConfig();
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    loadImagesBckg();
    loadImagesBorder();
  }

  private void readConfig() throws FileNotFoundException {
    File file = new File(name);
    Scanner scanner = new Scanner(file);
    configFile = new ArrayList<>();
    while (scanner.hasNextLine()) {
      configFile.add(Integer.parseInt(scanner.nextLine()));
    }
    scanner.close();
  }

  private void loadImagesBckg() {
    String[] imagePaths = {
        "src/img/bckg1.jpg",
        "src/img/ufo.jpg",
        "src/img/bckg2.jpg",
        "src/img/ufo.jpg"
    };
    for (String path : imagePaths) {
      try {
        images.add(ImageIO.read(new File(path)));
      } catch (IOException e) {
        System.err.println("Cant do it " + path);
        e.printStackTrace();
      }
    }
  }

  private void loadImagesBorder() {
    String[] imagePaths = {
        "src/img/border.png",
        "src/img/alien.jpg",
        "src/img/border.png",
        "src/img/alien.jpg"
    };
    for (String path : imagePaths) {
      try {
        imagesBorder.add(ImageIO.read(new File(path)));
      } catch (IOException e) {
        System.err.println("Cant do it " + path);
        e.printStackTrace();
      }
    }
  }
  public BufferedImage getImageBckg() {
    return images.get(configFile.getFirst());
  }

  public BufferedImage getImageBorder() {

    return imagesBorder.get(configFile.get(1));
  }
  public int getCellSize() {
    return configFile.get(2);
  }

  public void setImages(int index)
  {
    configFile.set(0,index);
    configFile.set(1,index);
    saveChanges();
  }
  public void setSizeCell(int index)
  {
    configFile.set(2,index);
    saveChanges();
  }

  private void saveChanges() {
    File file = new File(name);
    try {
      FileWriter fw = new FileWriter(file);
      for(var config : configFile)
      {
        fw.write(config + "\n");
      }
      fw.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  public int getSizeCell()
  {
    return configFile.get(2);
  }
}
