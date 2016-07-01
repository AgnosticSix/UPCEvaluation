/**
 * Created by AgnosticSix on 30/06/2016.
 */

import android.content.Context;
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

    }
}
