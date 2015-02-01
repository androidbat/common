package com.m.rabbit.db;

import java.util.ArrayList;

public class DBInitSql {
    private String dataBaseDir;
    private String dataBaseName;
    private int version;    
     
    private ArrayList<String> createTableSqlList;
    private ArrayList<String> upgradeSqlList;
    
    public DBInitSql(String dataBaseDir,String dataBaseName,int version){
        this.dataBaseDir=dataBaseDir;
        this.dataBaseName=dataBaseName;
        this.version=version;
        createTableSqlList = new ArrayList<String>();
        upgradeSqlList = new ArrayList<String>();
    }
    
    public ArrayList<String> getCreateTableSqlList() {
        return createTableSqlList;
    }

    public void addCreateTableSql(String sql) {
        this.createTableSqlList.add(sql);
    }

    public ArrayList<String> getUpgradeSqlList() {
        return upgradeSqlList;
    }

    public void addUpgradeSql(String sql) {
        this.upgradeSqlList.add(sql);
    }

    public String getDataBaseName() {
        return dataBaseName;
    }


    public String getDataBaseDir() {
        return dataBaseDir;
    }


    public int getVersion() {
        return version;
    }

    
    
}
