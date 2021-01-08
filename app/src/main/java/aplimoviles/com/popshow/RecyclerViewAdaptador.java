package aplimoviles.com.popshow;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdaptador extends RecyclerView.Adapter<RecyclerViewAdaptador.ViewHolder> // funciona como un puente entre la presentacion y la fuente de informacion a mostrar
{
    public List<favorito_modelo>favorito_modeloList; // Creacion de la lista de favoritos donde se van almacenar todos los datos

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener // El adaptador del recycler view debe contener una clase que extienda de la clase RecyclerView.viewHolder
    {

        private Button Sitio;
        private ImageView ImagenSitio;
        Context  context;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context= itemView.getContext();
            Sitio = (Button) itemView.findViewById(R.id.BtnFavorito);
            ImagenSitio = (ImageView)itemView.findViewById(R.id.imagenSitio);

        }

        void setOnClickListner(){
              Sitio.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            String stringSitio = Sitio.getText().toString();
            String stringCaldas = context.getResources().getString(R.string.title_caldas);
            String stringMuseo = context.getResources().getString(R.string.title_museo_hn);
            String stringErmita = context.getResources().getString(R.string.title_ermita);

            if(stringSitio.equals(stringCaldas)){
                Intent Caldas = new Intent(context, Caldas.class);
                context.startActivity(Caldas);
            }
            else if(stringSitio.equals(stringMuseo)){
                Intent Museo = new Intent(context, MuseoHN.class);
                context.startActivity(Museo);
            }

            else if(stringSitio.equals(stringErmita)){
                Intent Ermita = new Intent(context, Ermita.class);
                context.startActivity(Ermita);
            }

            else {
                Intent Principal = new Intent(context, Principal.class);
                context.startActivity(Principal);
            }

        }

    }


    public RecyclerViewAdaptador(List<favorito_modelo> favorito_modeloList) // Constructor  del adaptador el cual recibe como parametro la lista creada
    {
        this.favorito_modeloList = favorito_modeloList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) //inflar el contenido de un nuevo item para la lista
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorito,parent,false); //Hacer uso del layout item favoritos dentro del layout activity_favoritos
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) // Realiza las modificaciones para cada item
    {
        holder.Sitio.setText(favorito_modeloList.get(position).getSitio());
        holder.ImagenSitio.setImageResource(favorito_modeloList.get(position).getImagenSitio());

        // Funcion de botones
        holder.setOnClickListner();
    }

    @Override
    public int getItemCount() //Le permite conocer al adaptador la cantidad de datos que se van a procesar
    {
        return favorito_modeloList.size();
    }
}
