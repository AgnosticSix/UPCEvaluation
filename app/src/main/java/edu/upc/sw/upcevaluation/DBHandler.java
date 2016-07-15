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

    /*public static final String DDL = "CREATE TABLE Profesor(id INTEGER PRIMARY KEY, idCurso INTEGER);" +
            "CREATE TABLE Alumnos(idAlumno INTEGER PRIMARY KEY, nombre TEXT);"
            "CREATE TABLE Cursos(idCurso INTEGER PRIMARY KEY, descripcion TEXT, hrIni TEXT, hrFin TEXT);";*/
    public static final String delete = "drop table Persona;"+ "drop table Alumnos;"+ "drop table Calificacion;" + "drop table Carrera;"+
            "drop table Curso;"+ "drop table Curso_Alumno;"+ "drop table Evidencia;"+ "drop table Maestro;"+ "drop table Materia;"+ "drop table Unidad;";

    public DBHandler(Context context, String name, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.i(TAG, "Creando instancia");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(TAG, "Creando instancia 2");
        for (String s: delete.split(";")) {
            Log.i(TAG, s);
            sqLiteDatabase.rawQuery(s,null);
        }
    }

    public static SQLiteDatabase getDB(Context ctx){
        SQLiteOpenHelper db = new DBHandler(ctx, DATABASE_NAME, DATABASE_VERSION);
        return db.getWritableDatabase();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        /*sqLiteDatabase.execSQL("DROP TABLE IF EXISTS ");
        onCreate(sqLiteDatabase);*/
    }

    public void addGrupo(Grupos grupos) {

        ContentValues values = new ContentValues();

        SQLiteDatabase db = this.getWritableDatabase();

        //db.insert(TABLE_GRUPOS, null, values);
        db.close();
    }

    /*public Grupos findGrupo(String groupname) {
        //String query = "Select * FROM " + TABLE_GRUPOS + " WHERE " + COLUMN_IDASIGNATURA + " =  \"" + groupname + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Grupos grupos = new Grupos();

        cursor.moveToFirst();
        while(cursor.isAfterLast() == false) {
            grupos.set_id(Integer.parseInt(cursor.getString(0)));
            grupos.set_idAsignatura(cursor.getString(1));
            grupos.set_descripcion(cursor.getString(2));
            cursor.moveToNext();
            //Log.i(TABLE_GRUPOS,COLUMN_ID +""+ COLUMN_IDASIGNATURA +""+ COLUMN_DESCRIPCION);
        }
        cursor.close();
        db.close();
        return grupos;
    }*/

    /*public boolean deleteGrupo(String groupname) {

        boolean result = false;

        String query = "Select * FROM " + TABLE_GRUPOS + " WHERE " + COLUMN_IDASIGNATURA + " =  \"" + groupname + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Grupos grupos = new Grupos();

        if (cursor.moveToFirst()) {
            grupos.set_id(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_GRUPOS, COLUMN_ID + " = ?",
                    new String[] { String.valueOf(grupos.get_id()) });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }*/
}
