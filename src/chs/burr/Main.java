package chs.burr;

import chs.burr.data.MySqlAccess;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) throws Exception {
    MySqlAccess dao = new MySqlAccess();
    dao.initializeDatabase();

    dao.readDatabase();
  }

  static void printFileToConsole(String filename) {
    try {
      File myObj = new File(filename);
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        System.out.println(data);
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
}
