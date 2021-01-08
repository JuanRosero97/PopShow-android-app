package aplimoviles.com.popshow;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MuseoHN extends AppCompatActivity
{
    public boolean BanderaSitio,BanderaStar1,BanderaStar2,BanderaStar3,BanderaStar4,BanderaStar5;
    public  String banderaSitio,sitioFavorito,PuntuacionSitio;
    public int Puntuacion;

    private WebView browser;
    private String serviceURL;
    private HttpRequest req;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String responseBody = (String) msg.obj;

            try{
                RecibirDescripcion(responseBody);

            }catch (Exception e){
                e.printStackTrace();
            }

        }
    };

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        // protected void onCreate(Bundle savedInstanceState) {
        //     super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_museohn);

        final Toast toast = Toast.makeText(getApplicationContext(), R.string.añadidoFavoritos, Toast.LENGTH_SHORT);
        final Toast toastEstrellas = Toast.makeText(getApplicationContext(),R.string.Gracias,Toast.LENGTH_SHORT);

        final Handler handler = new Handler();

        banderaSitio = "banderaMuseoHN";
        sitioFavorito ="2";

        favorito_modelo favorito = new favorito_modelo();
        final PopShowBD popShowBD=new PopShowBD(getApplicationContext()); //Creo un objeto con las caracteristicas de PopShowBD
        final StarsBD starsBD = new StarsBD(getApplicationContext());

        Button btnMapaSitio = (Button) findViewById(R.id.ButtonMapMuseoHN);
        final ImageButton favSitio = (ImageButton) findViewById(R.id.heart_museo_hn);
        final ImageButton star1Sitio = (ImageButton) findViewById(R.id.star1_MHN);
        final ImageButton star2Sitio = (ImageButton) findViewById(R.id.star2_MHN);
        final ImageButton star3Sitio = (ImageButton) findViewById(R.id.star3_MHN);
        final ImageButton star4Sitio = (ImageButton) findViewById(R.id.star4_MHN);
        final ImageButton star5Sitio = (ImageButton) findViewById(R.id.star5_MHN);
        Button btnFavoritos = (Button) findViewById(R.id.ButtonFavoritos);
        Button btnHome = (Button) findViewById(R.id.ButtonHome);
        Button btnQr = (Button) findViewById(R.id.ButtonQr);
        browser = (WebView) findViewById(R.id.DESCRIPCION_MH);
        serviceURL = getString(R.string.url);

        starsBD.agregarEstrellas(sitioFavorito); //Se agrega una fila a la base de datos para el sitio

        BanderaSitio = popShowBD.buscarFavoritos(banderaSitio);
        PuntuacionSitio = starsBD.buscarEstrellas(sitioFavorito);

        pintarEstrellas(PuntuacionSitio,star1Sitio,star2Sitio,star3Sitio,star4Sitio,star5Sitio);
        Puntuacion = Integer.parseInt(PuntuacionSitio);

        if (BanderaSitio == false)  favSitio.setBackgroundResource(R.mipmap.heart_black);
        else favSitio.setBackgroundResource(R.mipmap.heart_red);

        btnMapaSitio.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                String lugar = "MUSEO DE HISTORIA NATURAL DE LA UNIVERSIDAD DEL CAUCA";
                String coordenadas = "2.443103, -76.601118";
                String query = " ("+ coordenadas + lugar + ") ";
                String encodedQuery = Uri.encode(query);
                String uriString = "geo:"+coordenadas + "?q=" + encodedQuery;
                Uri uri = Uri.parse( uriString );
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri );
                startActivity( intent );
            }
        });

        favSitio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                BanderaSitio=!BanderaSitio;

                if (BanderaSitio == false)  {
                    favSitio.setBackgroundResource(R.mipmap.heart_black);
                    popShowBD.BorrarFavorito(sitioFavorito);
                }
                else {
                    favSitio.setBackgroundResource(R.mipmap.heart_red);
                    popShowBD.agregarFavorito(banderaSitio,sitioFavorito);
                    toast.show();
                    handler.postDelayed(new Runnable() {@Override public void run() { toast.cancel(); } }, 800);}
                }
        });

        star1Sitio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                BanderaStar1=!BanderaStar1;

                if (BanderaStar1 == false)  {
                    Puntuacion= Puntuacion-1;
                    starsBD.editarEstrella(sitioFavorito,Puntuacion);
                    pintarEstrellas(Integer.toString(Puntuacion),star1Sitio,star2Sitio,star3Sitio,star4Sitio,star5Sitio);
                }
                else {
                    Puntuacion=Puntuacion+1;
                    starsBD.editarEstrella(sitioFavorito,Puntuacion);
                    pintarEstrellas(Integer.toString(Puntuacion),star1Sitio,star2Sitio,star3Sitio,star4Sitio,star5Sitio);
                    toastEstrellas.show();
                    handler.postDelayed(new Runnable() {@Override public void run() { toastEstrellas.cancel(); } }, 500);
                }
            }
        });

        star2Sitio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                BanderaStar2=!BanderaStar2;

                if (BanderaStar2 == false)  {
                    Puntuacion= Puntuacion-1;
                    starsBD.editarEstrella(sitioFavorito,Puntuacion);
                    pintarEstrellas(Integer.toString(Puntuacion),star1Sitio,star2Sitio,star3Sitio,star4Sitio,star5Sitio);
                }
                else {
                    Puntuacion=Puntuacion+1;
                    starsBD.editarEstrella(sitioFavorito,Puntuacion);
                    pintarEstrellas(Integer.toString(Puntuacion),star1Sitio,star2Sitio,star3Sitio,star4Sitio,star5Sitio);
                    toastEstrellas.show();
                    handler.postDelayed(new Runnable() {@Override public void run() { toastEstrellas.cancel(); } }, 500);
                }
            }
        });


        star3Sitio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                BanderaStar3=!BanderaStar3;

                if (BanderaStar3 == false)  {
                    Puntuacion= Puntuacion-1;
                    starsBD.editarEstrella(sitioFavorito,Puntuacion);
                    pintarEstrellas(Integer.toString(Puntuacion),star1Sitio,star2Sitio,star3Sitio,star4Sitio,star5Sitio);
                }
                else {
                    Puntuacion=Puntuacion+1;
                    starsBD.editarEstrella(sitioFavorito,Puntuacion);
                    pintarEstrellas(Integer.toString(Puntuacion),star1Sitio,star2Sitio,star3Sitio,star4Sitio,star5Sitio);
                    toastEstrellas.show();
                    handler.postDelayed(new Runnable() {@Override public void run() { toastEstrellas.cancel(); } }, 500);
                }
            }
        });

        star4Sitio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                BanderaStar4=!BanderaStar4;

                if (BanderaStar4 == false)  {
                    Puntuacion= Puntuacion-1;
                    starsBD.editarEstrella(sitioFavorito,Puntuacion);
                    pintarEstrellas(Integer.toString(Puntuacion),star1Sitio,star2Sitio,star3Sitio,star4Sitio,star5Sitio);
                }
                else {
                    Puntuacion=Puntuacion+1;
                    starsBD.editarEstrella(sitioFavorito,Puntuacion);
                    pintarEstrellas(Integer.toString(Puntuacion),star1Sitio,star2Sitio,star3Sitio,star4Sitio,star5Sitio);
                    toastEstrellas.show();
                    handler.postDelayed(new Runnable() {@Override public void run() { toastEstrellas.cancel(); } }, 500);
                }
            }
        });

        star5Sitio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                BanderaStar5=!BanderaStar5;

                if (BanderaStar5 == false)  {
                    Puntuacion= Puntuacion-1;
                    starsBD.editarEstrella(sitioFavorito,Puntuacion);
                    pintarEstrellas(Integer.toString(Puntuacion),star1Sitio,star2Sitio,star3Sitio,star4Sitio,star5Sitio);
                }
                else {
                    Puntuacion=Puntuacion+1;
                    starsBD.editarEstrella(sitioFavorito,Puntuacion);
                    pintarEstrellas(Integer.toString(Puntuacion),star1Sitio,star2Sitio,star3Sitio,star4Sitio,star5Sitio);
                    toastEstrellas.show();
                    handler.postDelayed(new Runnable() {@Override public void run() { toastEstrellas.cancel(); } }, 500);
                }
            }
        });

        btnFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent favoritos = new Intent(getApplicationContext(), favoritos.class);
                startActivity(favoritos);
            }
        });

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


    @Override
    public void onResume() {
        super.onResume();
        updateDescripcion();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void updateDescripcion() {

        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    req = new HttpRequest(serviceURL);
                    Message msg = new Message();
                    msg.obj = req.prepare(HttpRequest.Method.GET).sendAndReadString();
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    //Toast.makeText(WeatherDemo.this, "Request failed: " + e.getMessage(), Toast.LENGTH_LONG)
                    //      .show();
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    void RecibirDescripcion(String raw) throws Exception {

        DocumentBuilder builder = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(raw)));

        doc.getDocumentElement().normalize();
        NodeList SitiosTuristicos = doc.getElementsByTagName("Sitio");
        Node Sitio = (Element) SitiosTuristicos.item(1);

        if (Sitio.getNodeType() == Node.ELEMENT_NODE) {

            Element SitioElemento = (Element) Sitio;                                       //Recoge los elementos del sitio
            NodeList DescripcionList = SitioElemento.getElementsByTagName("Descripcion");  //Coge el parametro a usar, Descripcion
            Element DescripcionElement = (Element) DescripcionList.item(0);

            StringBuffer bufResult = new StringBuffer("<html><body>");
            bufResult.append("<p style=\"text-align:center;\">");
            bufResult.append(DescripcionElement.getAttribute("data"));
            bufResult.append("</p>");
            bufResult.append("</body></html>");
            browser.loadDataWithBaseURL(null, bufResult.toString(), "text/html", "UTF-8", null);
        }
    }

    private void pintarEstrellas(String puntuacionSitio, ImageButton star1Sitio, ImageButton star2Sitio, ImageButton star3Sitio, ImageButton star4Sitio, ImageButton star5Sitio) {

        if (puntuacionSitio.equals("1")) {
            star1Sitio.setBackgroundResource(R.color.Amarillo); BanderaStar1 = true;
            star2Sitio.setBackgroundResource(R.color.Transparente); BanderaStar2 = false;
            star3Sitio.setBackgroundResource(R.color.Transparente); BanderaStar3 = false;
            star4Sitio.setBackgroundResource(R.color.Transparente); BanderaStar4 = false;
            star5Sitio.setBackgroundResource(R.color.Transparente); BanderaStar5 = false;}
        else if (puntuacionSitio.equals("2")){
            star1Sitio.setBackgroundResource(R.color.Amarillo); BanderaStar1 = true;
            star2Sitio.setBackgroundResource(R.color.Amarillo); BanderaStar2 = true;
            star3Sitio.setBackgroundResource(R.color.Transparente); BanderaStar3 = false;
            star4Sitio.setBackgroundResource(R.color.Transparente); BanderaStar4 = false;
            star5Sitio.setBackgroundResource(R.color.Transparente); BanderaStar5 = false;}
        else if (puntuacionSitio.equals("3")){
            star1Sitio.setBackgroundResource(R.color.Amarillo); BanderaStar1 = true;
            star2Sitio.setBackgroundResource(R.color.Amarillo); BanderaStar2 = true;
            star3Sitio.setBackgroundResource(R.color.Amarillo); BanderaStar3 = true;
            star4Sitio.setBackgroundResource(R.color.Transparente); BanderaStar4 = false;
            star5Sitio.setBackgroundResource(R.color.Transparente); BanderaStar5 = false;}

        else if (puntuacionSitio.equals("4")){
            star1Sitio.setBackgroundResource(R.color.Amarillo); BanderaStar1 = true;
            star2Sitio.setBackgroundResource(R.color.Amarillo); BanderaStar2 = true;
            star3Sitio.setBackgroundResource(R.color.Amarillo); BanderaStar3 = true;
            star4Sitio.setBackgroundResource(R.color.Amarillo); BanderaStar4 = true;
            star5Sitio.setBackgroundResource(R.color.Transparente); BanderaStar5 = false;}

        else if (puntuacionSitio.equals("5")){
            star1Sitio.setBackgroundResource(R.color.Amarillo); BanderaStar1 = true;
            star2Sitio.setBackgroundResource(R.color.Amarillo); BanderaStar2 = true;
            star3Sitio.setBackgroundResource(R.color.Amarillo); BanderaStar3 = true;
            star4Sitio.setBackgroundResource(R.color.Amarillo); BanderaStar4 = true;
            star5Sitio.setBackgroundResource(R.color.Amarillo); BanderaStar5 = true;}
        else{
            star1Sitio.setBackgroundResource(R.color.Transparente); BanderaStar1 = false;
            star2Sitio.setBackgroundResource(R.color.Transparente); BanderaStar2 = false;
            star3Sitio.setBackgroundResource(R.color.Transparente); BanderaStar3 = false;
            star4Sitio.setBackgroundResource(R.color.Transparente); BanderaStar4 = false;
            star5Sitio.setBackgroundResource(R.color.Transparente); BanderaStar5 = false;
        }
    }

}
