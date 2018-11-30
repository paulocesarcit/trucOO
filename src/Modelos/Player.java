package Modelos;

import java.util.Arrays;

public class Player {
    private String nome;
    private boolean mao;
    public Cartas[] listaCarta = new Cartas[3];

    public Player(String nome){
        this.nome = nome;
        this.mao = false;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isMao() {
        return mao;
    }

    public void setMao(boolean mao) {
        this.mao = mao;
    }

    @Override
    public String toString() {
        String aux = "";
        aux += "Nome: " + nome;
        aux += mao ? ", É mão" : "Não é mão";
        aux += ", Cartas: " + Arrays.toString(listaCarta);
        return aux;
    }
    
    
}
