package chs.burr;

import chs.burr.data.MySqlDao;

public class Main {
  public static void main(String[] args) throws Exception {
    try {

      MySqlDao dao = new MySqlDao();

      dao.initializeDatabases();
      dao.populateDatabases();

    } catch (Exception e) {
      throw e;
    }
  }
}
