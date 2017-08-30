package com.bwie.day.app;

import android.app.Application;

import org.xutils.DbManager;
import org.xutils.x;

import java.io.File;

/**
 * 1. 类的用途
 * 2. @author admin
 * 3. @date 2017/8/30 14:12
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(false); //输出debug日志，开启会影响性能
    }
    //配置数据库
    public  static DbManager getdb(){
        DbManager.DaoConfig config=new DbManager.DaoConfig().setDbName("student.db").setDbDir(new File("/mnt/sdcard")).setDbVersion(1);

        DbManager db = x.getDb(config);
        return  db;
    }
}
