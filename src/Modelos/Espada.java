package Modelos;

public class Espada extends Cartas{
    public Espada() {
        naipe = "Espada";
    }

    @Override
    public void atribuiForca(int numero) {
        if(numero == 1){
            forca = 50;
        }else{
            if(numero == 7){
                forca = 19;
            }else{
                if(numero > 1 && numero <= 3){
                    forca = numero + 12;
                }else{
                    if(numero >= 4 && numero < 7){
                        forca = numero;
                    }else{
                        if(numero >= 10 && numero <= 12){
                            forca = numero - 2;
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public String toString() {
        String aux = "";
        aux += "Carta: " + numero + " ♠\n";
        return aux;
    }
    
}
