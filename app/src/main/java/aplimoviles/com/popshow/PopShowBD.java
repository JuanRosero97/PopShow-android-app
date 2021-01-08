package aplimoviles.com.popshow;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class PopShowBD extends SQLiteOpenHelper {   // Mediante esta clase se puede crear, modificar, eliminar y acceder a todos los datos de la base
    private static final String NOMBRE_BD="POPSHOW.bd"; // Nombre de la base de datos
    private static final int VERSION_BD=2; // Version de la base de datos parar modificar la estructura de la BD
    private static final String TABLA_FAVORITOS="CREATE TABLE FAVORITOS ( BANDERASITIO TEXT, SITIO TEXT PRIMARY KEY)";

    public PopShowBD(Context context) // Constructor de la clase PopShowBD
    {
        super(context, NOMBRE_BD, null, VERSION_BD); // Constructor de la clase
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) // Crea las tablas que conformaran Nuestra Base de datos
    {
        sqLiteDatabase.execSQL(TABLA_FAVORITOS); // Permite ejecutar las sentencias SQL requeridas en nuestra BD
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)  // Se ejecuta cuando sea necesario una actualizacion en la estructura de la BD
    {
       sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLA_FAVORITOS); // Elimina la tabla anterior y crea una nueva con los datos actualizados
        sqLiteDatabase.execSQL(TABLA_FAVORITOS);
    }

    public void agregarFavorito(String Bandera, String sitio){
        SQLiteDatabase bd=getWritableDatabase(); // Permite utilizar la base de datos en modo de lectura y escritura
        if (bd!=null) // Verificamos si la base de datos se abre de manera correcta
        {
            bd.execSQL("INSERT INTO FAVORITOS VALUES('"+Bandera+"','"+sitio+"')");
            bd.close();
        }
    }

    public List<favorito_modelo> mostrarFavoritos() // Crea lista basada en la clase Favorito Modelo que permite consultar los datos en la BD
    {
        SQLiteDatabase bd=getReadableDatabase(); // permite trabajar con la Base de datos en modo lectura
        Cursor cursor=bd.rawQuery("SELECT BANDERASITIO FROM FAVORITOS", null);  // Indica los campos que se van a utilizar y se guardan en el cursor
        List<favorito_modelo> Favoritos=new ArrayList<>();

       if(cursor.moveToFirst())
       {
            do {
                if(cursor.getString(cursor.getColumnIndex("BANDERASITIO")).equals("banderaCaldas"))
                    Favoritos.add(new favorito_modelo(R.string.title_caldas,R.drawable.brain));
                else if (cursor.getString(cursor.getColumnIndex("BANDERASITIO")).equals("banderaMuseoHN"))
                    Favoritos.add(new favorito_modelo(R.string.title_museo_hn,R.drawable.bank));
                else if (cursor.getString(cursor.getColumnIndex("BANDERASITIO")).equals("banderaErmita"))
                    Favoritos.add(new favorito_modelo(R.string.title_ermita,R.drawable.church));
                else{ }
            } while (cursor.moveToNext());
        }

       return Favoritos;
    }

    public boolean buscarFavoritos(String Bandera){
        SQLiteDatabase bd=getReadableDatabase();
        Cursor cursor=bd.rawQuery("SELECT * FROM FAVORITOS WHERE BANDERASITIO='"+Bandera+"'", null);

        if(cursor.moveToFirst()) return true;
        else   return false;
}

    public void BorrarFavorito(String Sitio){
        SQLiteDatabase bd=getWritableDatabase();

        if(bd!=null){
            bd.execSQL("DELETE FROM FAVORITOS WHERE SITIO='"+Sitio+"'");
            bd.close();
        }

    }
}
