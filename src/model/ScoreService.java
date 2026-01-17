package model;

import java.io.*;
import java.util.ArrayList;

public class ScoreService {
  String name = "save.ser";
  public static void saveScore(int result, String name,String fileName) throws IOException, ClassNotFoundException {
    var savedScore = new ArrayList<ScoreIO>();
    try {
      savedScore = readScoreObj(fileName);
    } finally {
      int randomNumber = (int) (Math.random() * 20);
      ScoreIO saveScore = new ScoreIO(name, result);
      FileOutputStream fos = new FileOutputStream(fileName);
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(saveScore);
      for (Object o : savedScore) {
        oos.reset();
        oos.writeObject(o);
      }
      oos.close();
    }
  }

  public static ArrayList<ScoreIO> readScoreObj(String fileName) {
    FileInputStream fis = null;
    try {
      fis = new FileInputStream(fileName);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    ObjectInputStream ois = null;
    try {
      ois = new ObjectInputStream(fis);
    } catch (IOException e) {
      throw new UncheckedIOException("Błąd IO przy odczycie pliku", e);
    }
    return readObjects(ois);
  }

  private static ArrayList<ScoreIO> readObjects(ObjectInputStream ois) {
    ArrayList<ScoreIO> al = new ArrayList<>();
    try {
      while (true) {
        ScoreIO obj = (ScoreIO) ois.readObject();
        if(obj.name!=null)
        {
          al.add(obj);
        }

      }
    } catch (EOFException e) {
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    return al;
  }
}
