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

public class Caldas extends AppCompatActivity
{
    public boolean BanderaSitio,BanderaStar1,BanderaStar2,BanderaStar3,BanderaStar4,BanderaStar5;
    public  String banderaSitio,sitioFavorito,PuntuacionSitio;
    public int Puntuacion;

    private WebView browser;
    private String serviceURL;
    private HttpRequest req;

    public Toast toast;
    public Toast toastEstrellas;

    Handler handler = new Handler() // Es Un controlador que  permite enviar y procesar a message
    {
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
        setContentView(R.layout.activity_caldas);

        final Handler handler = new Handler();

        // Para conocer cual de los sitios se va a trabajar
        banderaSitio = "banderaCaldas";
        sitioFavorito ="1";

        final PopShowBD popShowBD=new PopShowBD(getApplicationContext()); //Crea una instancia de la clase PopShowBD
                                                                          // para hacer uso de los metodos que la conforman
        final StarsBD starsBD = new StarsBD(getApplicationContext());

          // Mensajes flotantes cuando se añade a favorito o se agrega una estrella de puntuacion
         toast = Toast.makeText(getApplicationContext(), R.string.añadidoFavoritos, Toast.LENGTH_SHORT);
         toastEstrellas = Toast.makeText(getApplicationContext(),R.string.Gracias,Toast.LENGTH_SHORT);

          // Instancion los botones incluidos en el activity
        Button btnMapaSitio = (Button) findViewById(R.id.ButtonMapCaldas);
        final ImageButton favSitio = (ImageButton) findViewById(R.id.heart_caldas);
        final ImageButton star1Sitio = (ImageButton) findViewById(R.id.star1_PC);
        final ImageButton star2Sitio = (ImageButton) findViewById(R.id.star2_PC);
        final ImageButton star3Sitio = (ImageButton) findViewById(R.id.star3_PC);
        final ImageButton star4Sitio = (ImageButton) findViewById(R.id.star4_PC);
        final ImageButton star5Sitio = (ImageButton) findViewById(R.id.star5_PC);
        Button btnFavoritos = (Button) findViewById(R.id.ButtonFavoritos);
        Button btnHome = (Button) findViewById(R.id.ButtonHome);
        Button btnQr = (Button) findViewById(R.id.ButtonQr);
        browser = (WebView) findViewById(R.id.DESCRIPCION_PC);
        serviceURL = getString(R.string.url);

        starsBD.agregarEstrellas(sitioFavorito); //Se agrega una fila a la base de datos de estrellas para el sitio si ya no se ha creado

        BanderaSitio = popShowBD.buscarFavoritos(banderaSitio); // Recupero el estado que tenia la bandera de sitio guardada en la BD
        PuntuacionSitio = starsBD.buscarEstrellas(sitioFavorito); //Recupero la puntuacion del sitio guaradado en la starBD

        pintarEstrellas(PuntuacionSitio,star1Sitio,star2Sitio,star3Sitio,star4Sitio,star5Sitio); //Segun el puntaje, pongo fondo amarillo a las estrellas
        Puntuacion = Integer.parseInt(PuntuacionSitio); //Paso a entero la puntuacion que viene de la starBD

          // Segun el estado de la BanderaSitio, pongo un corazon favorito o no
        if (BanderaSitio == false)  favSitio.setBackgroundResource(R.mipmap.heart_black);
        else favSitio.setBackgroundResource(R.mipmap.heart_red);

       btnMapaSitio.setOnClickListener(new View.OnClickListener() //Ejecuto un busqueda por Google Maps al Parque Caldas
        {
            public void onClick(View v)
            {
                String lugar = "PARQUE CALDAS";
                String coordenadas = "2.44190556, -76.60633889";
                String query = " ("+ coordenadas + lugar + ") ";
                String encodedQuery = Uri.encode(query);
                String uriString = "geo:"+coordenadas + "?q=" + encodedQuery;
                Uri uri = Uri.parse( uriString );
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri );
                startActivity( intent );
            }
        });

          favSitio.setOnClickListener(new View.OnClickListener() { //Cambio el fondo del corazon segun corresponda
              @Override
              public void onClick(View v)
              {
                  BanderaSitio=!BanderaSitio;

                  if (BanderaSitio == false)  {
                      favSitio.setBackgroundResource(R.mipmap.heart_black);
                      popShowBD.BorrarFavorito(sitioFavorito); //Borro de la BD el favorito
                  }
                  else {
                      favSitio.setBackgroundResource(R.mipmap.heart_red);
                      popShowBD.agregarFavorito(banderaSitio,sitioFavorito); //Agrego a favoritos el sitio
                      toast.show();
                      handler.postDelayed(new Runnable() {@Override public void run() { toast.cancel(); } }, 800);}
              }
          });

          star1Sitio.setOnClickListener(new View.OnClickListener() { //Cambio el color de las estrellas
              @Override
              public void onClick(View v)
              {
                  BanderaStar1=!BanderaStar1;
                  Puntuacion=PuntuacionEstrellas(BanderaStar1,Puntuacion,star1Sitio,star2Sitio,star3Sitio,star4Sitio,star5Sitio);
                  starsBD.editarEstrella(sitioFavorito,Puntuacion);
              }
          });

          star2Sitio.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v)
              {
                  BanderaStar2=!BanderaStar2;
                  Puntuacion=PuntuacionEstrellas(BanderaStar2,Puntuacion,star1Sitio,star2Sitio,star3Sitio,star4Sitio,star5Sitio);
                  starsBD.editarEstrella(sitioFavorito,Puntuacion);
              }
          });


          star3Sitio.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v)
              {
                  BanderaStar3=!BanderaStar3;
                  Puntuacion=PuntuacionEstrellas(BanderaStar3,Puntuacion,star1Sitio,star2Sitio,star3Sitio,star4Sitio,star5Sitio);
                  starsBD.editarEstrella(sitioFavorito,Puntuacion);
              }
          });

          star4Sitio.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v)
              {
                  BanderaStar4=!BanderaStar4;
                  Puntuacion=PuntuacionEstrellas(BanderaStar4,Puntuacion,star1Sitio,star2Sitio,star3Sitio,star4Sitio,star5Sitio);
                  starsBD.editarEstrella(sitioFavorito,Puntuacion);
              }
          });

          star5Sitio.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v)
              {
                  BanderaStar5=!BanderaStar5;
                  Puntuacion=PuntuacionEstrellas(BanderaStar5,Puntuacion,star1Sitio,star2Sitio,star3Sitio,star4Sitio,star5Sitio);
                  starsBD.editarEstrella(sitioFavorito,Puntuacion);
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

    public int PuntuacionEstrellas(boolean BanderaStar,int Puntuacion, ImageButton star1Sitio, ImageButton star2Sitio, ImageButton star3Sitio, ImageButton star4Sitio, ImageButton star5Sitio){

        if (BanderaStar == false)  {
            Puntuacion= Puntuacion-1;
            pintarEstrellas(Integer.toString(Puntuacion),star1Sitio,star2Sitio,star3Sitio,star4Sitio,star5Sitio);
        }
        else {
            Puntuacion=Puntuacion+1;
            pintarEstrellas(Integer.toString(Puntuacion),star1Sitio,star2Sitio,star3Sitio,star4Sitio,star5Sitio);
            toastEstrellas.show();
            handler.postDelayed(new Runnable() {@Override public void run() { toastEstrellas.cancel(); } }, 500);
        }
    return Puntuacion;
    }

    //Logica para el fondo de las estrellas y el estado de cada una de ellas para segun lo anterior, aumentar o disminuir el puntaje
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

        Thread t = new Thread(new Runnable()  // Se encarga de crear un proceso en segundo plano
        {
            public void run() {
                try {
                    req = new HttpRequest(serviceURL);
                    Message msg = new Message(); // Define un mensaje que contiene datos arbitrarios que se pueden enviar a Handler.
                    msg.obj = req.prepare(HttpRequest.Method.GET).sendAndReadString();
                    System.out.print(msg);
                    handler.sendMessage(msg); // asocia el objetos Message Thread o hilo de ejecucion. Cada instancia de Handler está asociada con un solo Thread.
                } catch (Exception e) {
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
        Node Sitio = (Element) SitiosTuristicos.item(0);   //Se ubica en el parque Caldas

        if (Sitio.getNodeType() == Node.ELEMENT_NODE) {

            Element SitioElemento = (Element) Sitio;                                        //Recoge los elementos del sitio
            NodeList DescripcionList = SitioElemento.getElementsByTagName("Descripcion");  //Se ubica en el parametro a usar, Descripcion
            Element DescripcionElement = (Element) DescripcionList.item(0);

            StringBuffer bufResult = new StringBuffer("<html><body>");                     //Se arma el HTTML
            bufResult.append("<p style=\"text-align:center;\">");
            bufResult.append(DescripcionElement.getAttribute("data"));              // De la descripcion del lugar se coge el data o el valor de la descripcion
            bufResult.append("</p>");
            bufResult.append("</body></html>");
            browser.loadDataWithBaseURL(null, bufResult.toString(), "text/html", "UTF-8", null);
        }
    }

}
