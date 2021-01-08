package aplimoviles.com.popshow;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class StarsBD extends SQLiteOpenHelper {

    private static final String NOMBRE_BD="stars.bd";
    private static final int VERSION_BD=1;
    private static final String TABLA_ESTRELLAS="CREATE TABLE ESTRELLAS ( SITIO TEXT, PUNTUACION TEXT )";
    private String Estrellas, puntuacion;
    public StarsBD(Context context){
        super(context, NOMBRE_BD, null, VERSION_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(TABLA_ESTRELLAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLA_ESTRELLAS);
        sqLiteDatabase.execSQL(TABLA_ESTRELLAS);
    }

    public void agregarEstrellas(String SitioEstrellas){
        SQLiteDatabase bdRead=getReadableDatabase();

        Cursor cursor=bdRead.rawQuery("SELECT * FROM ESTRELLAS WHERE SITIO='"+SitioEstrellas+"'", null);

        if(!cursor.moveToFirst()) {
            SQLiteDatabase bdWrite=getWritableDatabase();
            if (bdWrite != null) {
                bdWrite.execSQL("INSERT INTO ESTRELLAS VALUES('" + SitioEstrellas + "','" + 0 + "')");
                bdWrite.close();    }
        }
    }

    public String buscarEstrellas(String SitioEstrellas){

        SQLiteDatabase bd=getReadableDatabase();
        Cursor cursor=bd.rawQuery("SELECT PUNTUACION FROM ESTRELLAS WHERE SITIO='"+SitioEstrellas+"'" , null);

        if(cursor.moveToFirst()){

            do {
                if (cursor.getString(cursor.getColumnIndex("PUNTUACION")).equals("1")) puntuacion= "1";
                else if (cursor.getString(cursor.getColumnIndex("PUNTUACION")).equals("2"))  puntuacion= "2";
                else if (cursor.getString(cursor.getColumnIndex("PUNTUACION")).equals("3"))  puntuacion= "3";
                else if (cursor.getString(cursor.getColumnIndex("PUNTUACION")).equals("4"))  puntuacion= "4";
                else if (cursor.getString(cursor.getColumnIndex("PUNTUACION")).equals("5"))  puntuacion= "5";
                else  puntuacion= "0";

            } while (cursor.moveToNext());
        }
        return puntuacion;
    }

    public void editarEstrella(String SitioEstrellas,int Puntuacion){

        Estrellas = Integer.toString(Puntuacion);

        SQLiteDatabase bd=getWritableDatabase();
        if (bd!=null){
            ContentValues newValues = new ContentValues();
            newValues.put("PUNTUACION",Estrellas);
            bd.update("ESTRELLAS", newValues, "SITIO='"+SitioEstrellas+"'", null);
            bd.close();
        }
    }



}
