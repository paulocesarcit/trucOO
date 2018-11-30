package Modelos;

public class Copas extends Cartas {

    public Copas() {
        naipe = "Copas";
    }
    

    @Override
    public void atribuiForca(int numero) {
        if(numero >= 1 && numero <= 3){
            forca = numero + 12;
        }else{
            if(numero >= 4 && numero <= 7){
                forca = numero;
            }else{
                if(numero >= 10 && numero <= 12){
                    forca = numero - 2;
                }
            }
        }
    }

    @Override
    public String toString() {
        String aux = "";
        aux += "Carta: " + numero + " ♥\n";
        return aux;
    }
    
    
    
}
