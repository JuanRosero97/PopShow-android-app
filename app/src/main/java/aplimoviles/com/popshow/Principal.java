package aplimoviles.com.popshow;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class Principal extends AppCompatActivity {

    @Override

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_principal);

        Button btnCaldas = (Button) findViewById(R.id.ButtonCaldas);
        Button btnMuseo = (Button) findViewById(R.id.ButtonMuseo);
        Button btnErmita = (Button) findViewById(R.id.ButtonErmita);
        Button btnMorro = (Button) findViewById(R.id.ButtonMorro);
        Button btnCampanario = (Button) findViewById(R.id.ButtonCampanario);
        Button btnPueblito = (Button) findViewById(R.id.ButtonPueblito);
        Button btnFavoritos = (Button) findViewById(R.id.ButtonFavoritos);
        Button btnQr = (Button) findViewById(R.id.ButtonQr);


        btnCaldas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Caldas = new Intent(getApplicationContext(), Caldas.class);
                startActivity(Caldas);
            }
        });

        btnMuseo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Museo = new Intent(getApplicationContext(), MuseoHN.class);
                startActivity(Museo);
            }
        });

        btnErmita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Ermita = new Intent(getApplicationContext(), Ermita.class);
                startActivity(Ermita);
            }
        });

        btnMorro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Principal.this, "EN CONSTRUCCION",Toast.LENGTH_SHORT).show();
            }
        });

        btnCampanario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Principal.this, "EN CONSTRUCCION",Toast.LENGTH_SHORT).show();
            }
        });

        btnPueblito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Principal.this, "EN CONSTRUCCION",Toast.LENGTH_SHORT).show();
            }
        });

        btnFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent favoritos = new Intent(getApplicationContext(), favoritos.class);
                startActivity(favoritos);
            }
        });

        btnQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addQR();

            }
        });
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




