/**
 * Created by AgnosticSix on 30/06/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "UPCEvaluationDB.db";
    private static final String TABLE_GRUPOS = "grupos";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_IDASIGNATURA = "idasignatura";
    public static final String COLUMN_DESCRIPCION = "descripcion";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " +
                TABLE_GRUPOS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_IDASIGNATURA
                + " TEXT," + COLUMN_DESCRIPCION + " INTEGER" + ")";
        sqLiteDatabase.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_GRUPOS);
        onCreate(sqLiteDatabase);
    }

    public void addGrupo(Grupos grupos) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_IDASIGNATURA, grupos.get_idAsignatura());
        values.put(COLUMN_DESCRIPCION, grupos.get_descripcion());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_GRUPOS, null, values);
        db.close();
    }

    public Grupos findGrupo(String groupname) {
        String query = "Select * FROM " + TABLE_GRUPOS + " WHERE " + COLUMN_IDASIGNATURA + " =  \"" + groupname + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Grupos grupos = new Grupos();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            grupos.set_id(Integer.parseInt(cursor.getString(0)));
            grupos.set_idAsignatura(cursor.getString(1));
            grupos.set_descripcion(cursor.getString(2));
            cursor.close();
        } else {
            grupos = null;
        }
        db.close();
        return grupos;
    }

    public boolean deleteGrupo(String groupname) {

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
    }
}
