package Modelos;

public abstract class Cartas {
    protected int numero;
    protected int forca;
    protected String naipe;
    
    public abstract void atribuiForca(int numero);

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getForca() {
        return forca;
    }

    public void setForca(int forca) {
        this.forca = forca;
    }

    public String getNaipe() {
        return naipe;
    }

    public void setNaipe(String naipe) {
        this.naipe = naipe;
    }
    
}
