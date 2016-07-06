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

    public static final String DDL = "CREATE TABLE Persona(idPersona INTEGER PRIMARY KEY, nombre TEXT, apellidos TEXT);" +
            "CREATE TABLE Alumnos(idMatricula INTEGER PRIMARY KEY, idPersona INTEGER, idCarrera INTEGER);" +
            "CREATE TABLE Calificacion(idCalificacion INTEGER PRIMARY KEY, idEvidencia INTEGER, idCurso_Alumno INTEGER);" +
            "CREATE TABLE Carrera(idCarrera INTEGER PRIMARY KEY, nombre TEXT);" +
            "CREATE TABLE Curso(idCurso INTEGER PRIMARY KEY, idMateria INTEGER, idMaestro INTEGER);" +
            "CREATE TABLE Curso_Alumno(idCurso_Alumno INTEGER PRIMARY KEY, idMatricula INTEGER, idCurso INTEGER);" +
            "CREATE TABLE Evidencia(idEvidencia INTEGER PRIMARY KEY, idUnidad INTEGER, tipo TEXT, descripcion TEXT);" +
            "CREATE TABLE Maestro(idMaestro INTEGER PRIMARY KEY, idPersona INTEGER, usuario TEXT, password TEXT);" +
            "CREATE TABLE Materia(idMateria INTEGER PRIMARY KEY, nombre TEXT);" +
            "CREATE TABLE Unidad(idUnidad INTEGER PRIMARY KEY, idMateria INTEGER, nombre TEXT);";

    public DBHandler(Context context, String name, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.i(TAG, "Creando instancia");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(TAG, "Creando instancia 2");
        for (String s: DDL.split(";")) {
            Log.i(TAG, s);
            sqLiteDatabase.execSQL(s);
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
        /*values.put(DBHandler.COLUMN_ID, "1");
        values.put(DBHandler.COLUMN_IDASIGNATURA, "ingles1");
        values.put(DBHandler.COLUMN_DESCRIPCION, "Inglés 1");
        values.put(DBHandler.COLUMN_ID, "2");
        values.put(DBHandler.COLUMN_IDASIGNATURA, "algebra1");
        values.put(DBHandler.COLUMN_DESCRIPCION, "Algebra 1");*/

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
