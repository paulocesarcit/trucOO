package Modelos;

public class Partida {
    private int turno;

    public Partida() {
        this.turno = 0;
    }

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

    public void atribuiForca(Cartas c, int forca){
        c.atribuiForca(forca);
    }
    
    public int calculaForca(Cartas player1, Cartas player2){
        if(player1.getForca() > player2.getForca()){
            return 1; //1 ganhou
        }else{
            if(player2.getForca() > player1.getForca()){
                return 2;//2 ganhou
            }else{
                return 0;//empate
            }
        }
    }
    
    public int calculaEnvido(Cartas[] c) { // retorna os pontos de Envido
        int envido=20;
        if (isEnvido(c[0], c[1])){ // verifica se a carta[0] e a carta[1] tem o mesmo naipe
            if (c[0].getNumero() < 10){ // verifica se o numero da carta[0] é menor que 10
                envido += c[0].getNumero(); // adiciona o numero da carta[0] ao envido
            }
            if (c[1].getNumero() < 10){ // verifica se o numero da carta[0] é menor que 10
                envido += c[1].getNumero(); // adiciona o numero da carta[1] ao envido
            }
        }else if (isEnvido(c[1], c[2])){ // verifica se a carta[1] e a carta[2] tem o mesmo naipe
            if (c[1].getNumero() < 10){ // verifica se o numero da carta[1] é menor que 10
                envido += c[1].getNumero(); // adiciona o numero da carta[1] ao envido
            }
            if (c[2].getNumero() < 10){ // verifica se o numero da carta[2] é menor que 10
                envido += c[2].getNumero(); // adiciona o numero da carta[2] ao envido
            }
        }else if(isEnvido(c[0], c[2])){ // verifica se a carta[0] e a carta[2] tem o mesmo naipe
            if (c[0].getNumero() < 10){ // verifica se o numero da carta[0] é menor que 10
                envido += c[0].getNumero(); // adiciona o numero da carta[0] ao envido
            }
            if (c[2].getNumero() < 10){ // verifica se o numero da carta[2] é menor que 10
                envido += c[2].getNumero(); // adiciona o numero da carta[2] ao envido
            }
        }else{
            envido = 0;
            if (c[0].getNumero() < 10){ // se o numero da carta[0] for menor que 10
                envido = (c[0].getNumero() > envido) ? c[0].getNumero() : envido; // iguala o numero da carta[0] ao envido se for maior que ele
            }
            if (c[1].getNumero() < 10){ // se o numero da carta[1] for menor que 10
                envido = (c[1].getNumero() > envido) ? c[1].getNumero() : envido; //iguala o numero da carta[1] ao envido se for maior que ele
            }
            if (c[2].getNumero() < 10){ // se o numero da carta[2] for menor que 10
                envido = (c[2].getNumero() > envido) ? c[2].getNumero() : envido; //iguala o numero da carta[2] ao envido se for maior que ele
            }
        }
        return envido;
    }
    
    public int calculaFlor(Cartas[] c){ // retorna os pontos de flor
        int flor=20;
        if (c[0].getNumero() < 10){ // se o numero da carta[0] for menor que 10
            flor += c[0].getNumero();
        }
        if (c[1].getNumero() < 10){ // se o numero da carta[1] for menor que 10
            flor += c[1].getNumero();
        }
        if (c[2].getNumero() < 10){ // se o numero da carta[2] for menor que 10
            flor += c[2].getNumero();
        }
        return flor;
    }
    
    public boolean isFlor(Cartas[] c){ // retorna true se tiver Flor
        return (c[0].getNaipe().equals(c[1].getNaipe()) && c[1].getNaipe().equals(c[2].getNaipe())); // verifica os naipes iguais
    }
    
    public boolean isEnvido(Cartas c1, Cartas c2){ // retorna true se tiver Envido
        return (c1.getNaipe().equals(c2.getNaipe())); // verifica os naipes iguais
    }
    
    public int venceChamada(boolean player1Mao, int pontosP1, int pontosP2){ // retorna vencedor: 1 - player1 | 2 - player2
        if(pontosP1 > pontosP2){ // se os pontos do player 1 forem maiores que o player 2
            return 1;
        }else{
            if(pontosP2 > pontosP1){ // se os pontos do player 2 forem maiores que o player 1
                return 2;
            }else{
                if(player1Mao){ // mão vence
                    return 1;
                }else{
                    return 2;
                }
            }
        }
    }
    
    public int verificaPontos(String chamada){ // retorna quantos pontos ganhou conforme o que foi chamado
        int pontos=0;
        switch (chamada) {
            case "Nao":
                pontos = 1;
                break;
            case "Truco":
                pontos = 2;
                break;
            case "Retruco":
                pontos = 3;
                break;
            case "ValeQuatro":
                pontos = 4;
                break;
            case "Envido":
                pontos = 2;
                break;
            case "RealEnvido":
                pontos = 3;
                break;
            case "FaltaEnvido":
                pontos = 12;
                break;
            case "Flor":
                pontos = 3;
                break;
            case "ContraFlor":
                pontos = 6;
                break;
            case "FlorEoResto":
                pontos = 12;
                break;
         }
        System.out.println(chamada);
        return pontos;
    }
    
    public void adicionaPontos(Player player, int pontos){ //adiciona pontos ao player
        player.setPontos(pontos);
    }
    
}
