package edu.upc.sw.upcevaluation; /**
 * Created by AgnosticSix on 30/06/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHandler extends SQLiteOpenHelper{

    private static String TAG = "DBHandler Test";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "UPCEvaluationDB.db";

    public static final String DDL = "CREATE TABLE Profesor(idProfesor INTEGER PRIMARY KEY, nombre TEXT)";
    public static final String DDL2 = "CREATE TABLE Alumnos(idAlumno INTEGER PRIMARY KEY, nombre TEXT, idCurso INTEGER)";
    public static final String DDL3 = "CREATE TABLE Cursos(idCurso INTEGER PRIMARY KEY, idProfesor INTEGER, grado INTEGER, descripcion TEXT, hrIni TEXT, hrFin TEXT)";

    public DBHandler(Context context, String name, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.i(TAG, "Creando instancia");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(TAG, "Creando instancia 2");

        sqLiteDatabase.execSQL(DDL);
        sqLiteDatabase.execSQL(DDL2);
        sqLiteDatabase.execSQL(DDL3);

    }

    public static SQLiteDatabase getDB(Context ctx){
        SQLiteOpenHelper db = new DBHandler(ctx, DATABASE_NAME, DATABASE_VERSION);
        return db.getWritableDatabase();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i(TAG, "asdf");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Profesor");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Cursos");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Alumnos");
        onCreate(sqLiteDatabase);
    }

}
