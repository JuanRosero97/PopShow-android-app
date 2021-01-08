package aplimoviles.com.popshow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class favoritos extends AppCompatActivity {

    private RecyclerView recyclerViewFavoritos;
    private RecyclerViewAdaptador adaptadorSitio;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);

        recyclerViewFavoritos = (RecyclerView) findViewById(R.id.recyclerFavoritos);
        recyclerViewFavoritos.setLayoutManager(new LinearLayoutManager(this)); // Definimos la forma de la lista (Vertical) similar a la de un listView

        Button btnHome = (Button) findViewById(R.id.ButtonHome);
        Button btnQr = (Button) findViewById(R.id.ButtonQr);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(getApplicationContext(), Principal.class);
                startActivity(home);
            }
        });

        btnQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addQR();

            }
        });
    }
    public void onResume() {
        super.onResume();
        PopShowBD popShowBD = new PopShowBD(getApplicationContext()); //Crea una instancia de la clase PopShowBD
        // para hacer uso de los metodos que la conforman
        adaptadorSitio = new RecyclerViewAdaptador(popShowBD.mostrarFavoritos()); // Convierte cada fila de la lista de favoritos en un cardview que contenga la informacion del sitio
        recyclerViewFavoritos.setAdapter(adaptadorSitio); // Permite la visualizacion de los cardview que contine la informacion de un sitio favorito
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void addQR() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.initiateScan();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == RESULT_OK) {
            IntentResult scanResult = IntentIntegrator.parseActivityResult(
                    requestCode, resultCode, intent);
            if (scanResult != null) {
                // Handle successful scan
                if(scanResult.getContents()== null){
                    Toast.makeText(this,R.string.QR_no_encontrado,Toast.LENGTH_SHORT).show();
                }
                else {
                    String contents = scanResult.getContents();

                    if(contents.equals("MECARD:N:Parque Caldas;;")){
                        Intent Caldas = new Intent(getApplicationContext(), Caldas.class);
                        startActivity(Caldas);
                    }

                    else if(contents.equals("MECARD:N:Museo de Historia Natural;;")){
                        Intent Museo = new Intent(getApplicationContext(), MuseoHN.class);
                        startActivity(Museo);
                    }

                    else if(contents.equals("MECARD:N:Ermita;;")){
                        Intent Ermita = new Intent(getApplicationContext(), Ermita.class);
                        startActivity(Ermita);
                    }

                    else {
                        Toast.makeText(this, R.string.QR_no_encontrado,Toast.LENGTH_SHORT).show();
                    }
                }

            }
        }
        else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, R.string.scan_canceled,Toast.LENGTH_SHORT).show();
        }

    }
}
