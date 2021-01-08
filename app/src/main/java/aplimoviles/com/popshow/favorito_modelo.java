package aplimoviles.com.popshow;

public class favorito_modelo // Clase que permite representar al sitio favorito y los datos con los que va a trabajar
{
    private int Sitio;
    private int ImagenSitio;

    public favorito_modelo() { }

    public favorito_modelo(int sitio, int imagenSitio) {
        this.Sitio = sitio;
        this.ImagenSitio = imagenSitio;
    }

    public int getSitio() {
        return Sitio;
    }

    public void setSitio(int sitio) {
        this.Sitio = sitio;
    }


    public int getImagenSitio() {
        return ImagenSitio;
    }

    public void setImagenSitio(int imagenSitio) {
        this.ImagenSitio = imagenSitio;
    }
}
