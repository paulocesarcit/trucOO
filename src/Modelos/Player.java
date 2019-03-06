package Modelos;

import java.util.Arrays;

public class Player {
    private String nome;
    private boolean mao;
    private int pontos;
    private int rodadaGanha;
    public Cartas[] listaCarta = new Cartas[3];

    public Player(String nome){
        this.nome = nome;
        this.mao = false;
        this.rodadaGanha = 0;
        this.pontos = 0;
    }
    
    public int getRodadaGanha(){
        return rodadaGanha;
    }
    
    public void setRodadaGanha(int rodadaGanha){
        this.rodadaGanha = rodadaGanha;
    }
    
    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos += pontos;
    }
    
    public String getNomeNotPontos(){
        return nome;
    }
    
    public String getNome() {
        return nome + " | Pontos: " + pontos;
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
        aux += "\nPontos: " + getPontos();
        return aux;
    }
    
}
