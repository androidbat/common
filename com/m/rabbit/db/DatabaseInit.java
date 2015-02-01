package com.m.rabbit.db;

import java.io.File;

public class DatabaseInit {
	
	public static String database_name;
    public static final Integer VERSION = 1;
    public static final String DB_DIR = "/data/data/com.niuyu.shop/";
    
    public static void setDatabaseDir() {
        File dir = new File(DB_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
	
	public static void init(String databasename){
		if (database_name != null && database_name.equals(databasename)) {
			return;
		}
		database_name = databasename;
        DBInitSql dBInitSql=new DBInitSql(DB_DIR, database_name, VERSION);
//        dBInitSql.addCreateTableSql(DatabaseAccess.getCreateTableSqlOrder(ChatRecent.class));
        DBHelper.init(dBInitSql);
	}
	
}
