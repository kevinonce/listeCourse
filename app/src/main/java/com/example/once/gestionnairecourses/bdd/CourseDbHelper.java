package com.example.once.gestionnairecourses.bdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.once.gestionnairecourses.pojo.Article;
import com.example.once.gestionnairecourses.pojo.ListeCourse;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by kevin on 20/09/2015.
 */
public class CourseDbHelper extends OrmLiteSqliteOpenHelper {

    private static String DB_NAME = "course.db";
    private static int DB_VERSION = Integer.valueOf(4);

    public CourseDbHelper(Context context) {
        super(context, DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Article.class);
            TableUtils.createTable(connectionSource, ListeCourse.class);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, ListeCourse.class,true);
            TableUtils.dropTable(connectionSource, Article.class, true);
            onCreate(database,connectionSource);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
