package chs.burr;

import chs.burr.data.MySqlAccess;

public class Main 
{
    public static void main(String[] args) throws Exception 
    {
    	MySqlAccess dao = new MySqlAccess();     
        dao.initializeDatabase();
        
        //dao.readDatabase();        
    }
}
