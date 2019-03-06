package teste;

import javax.swing.JOptionPane;

import Modelos.Basto;
import Modelos.Cartas;
import Modelos.Copas;
import Modelos.Espada;
import Modelos.Ouro;
import Modelos.Player;
import Modelos.Partida;

import java.util.Random;

public class Main{
    Object[] confirmacao = {"Sim", "Não"};
    
    public static void main(String[] args){
        Object[] confirmacao = {"Sim", "Não"};
        Partida p = new Partida();
        Player jogador1 = new Player("Paulo");
        Player jogador2 = new Player("Diego");
        Player player1 = null;
        Player player2 = null;
        
        Object[] players = {jogador1.getNomeNotPontos(), jogador2.getNomeNotPontos()}; // Pega o nome sem mostrar os pontos
        do{
            int o = JOptionPane.showOptionDialog(null, "Escolha o mão", "Escolha mão", JOptionPane.WARNING_MESSAGE, 1, null, players, players[0]);
            switch(o) {
                case 0://Coloca o jogador 1 como mão
                    jogador1.setMao(true);
                    player1 = jogador1;
                    player2 = jogador2;
                    break;
                case 1://Coloca o jogador2 como mão
                    jogador2.setMao(true);
                    player1 = jogador2;
                    player2 = jogador1;
                    break;
            }
        }while(!(jogador1.isMao() || jogador2.isMao()));

        while(player1.getPontos() < 12 && player2.getPontos() < 12){//enquanto nenhum jogador chega a 12 pontos
            Object[] gritos = {"Envido", "Flor", "Truco", "Soltar Carta"};//seta os gritos do jogo

            darCartas(player1, player2, p);//da as cartas para os jogadors
            
            //calcula os pontos do player1
            int envP1 = p.calculaEnvido(player1.listaCarta);
            //calcula os pontos do player2
            int envP2 = p.calculaEnvido(player2.listaCarta);

            //armazena as cartas numa string
            String cartasPlayer1 = player1.listaCarta[0].toString() + player1.listaCarta[1].toString() + player1.listaCarta[2].toString();
            String cartasPlayer2 = player2.listaCarta[0].toString() + player2.listaCarta[1].toString() + player2.listaCarta[2].toString();
            
            //reseta as rodadas ganhas
            player1.setRodadaGanha(0);
            player2.setRodadaGanha(0);
            //pontoRodada = variavel que diz se estamos jogando normal, no truco, no retruco ou no vale quatro
            int pontoRodada = 0;
            //reseta o turno
            p.setTurno(0);
            //reseta variavel checkEnvido
            boolean checkEnvido = false;
            //enquanto o turno for menor que 3
            while(p.getTurno() < 3){
                //se não estiver no primeiro turno não da a opção de pedir envido e flor
                //ou se já for pedido envido
                if(p.getTurno() != 0 || checkEnvido){
                    gritos[0] = "";
                    gritos[1] = "";
                } 
                //se não tiverem chamado Truco ainda, mostra a string Truco
                if(pontoRodada == 0){
                    gritos[2] = "Truco";
                }else{
                    //se já pediram Truco, mostra a string Retruco
                    if(pontoRodada == 1){
                        gritos[2] = "Retruco";
                    }else{
                        //se já pediram Retruco, mostra a string Vale Quatro
                        if(pontoRodada == 2){
                            gritos[2] = "Vale Quatro";
                        }else{
                            //se já pediram tudo não mostra nada
                            gritos[2] = "";
                        } 
                    }
                }
                //menu principal do jogo
                int escolha = JOptionPane.showOptionDialog(null, cartasPlayer1 , player1.getNome(), JOptionPane.WARNING_MESSAGE, 0, null, gritos, gritos[0]);
                switch(escolha){
                    case 0:
                     /*---------------------ENVIDO----------------------------*/
                     //se não estiver na primeira rodada não deixa pedir envido
                     if(p.getTurno() != 0 || checkEnvido){
                         JOptionPane.showMessageDialog(null, "Escolha uma opção valida!!");
                         //p.setTurno(p.getTurno() - 1);
                         break;
                     }
                     //se pedir envido não mostra mais nem envido nem flor entre as opções
                     gritos[0] = "";
                     gritos[1] = "";
                     checkEnvido = chamaEnvido(p, player1, player2, envP1, envP2);
                     continue;
                    case 1:
                    /*-------------------------FLOR---------------------------*/
                    //se não estiver na primeira rodada não deixa pedir flor
                    if(p.getTurno() != 0 || checkEnvido){
                        JOptionPane.showMessageDialog(null, "Escolha uma opção valida!!");
                        //p.setTurno(p.getTurno() - 1);
                        break;
                    }
                    checkEnvido = chamaFlor(p, player1, player2);
                    //se pedir flor não mostra mais nem flor nem envido entre as opções
                    gritos[0] = "";
                    gritos[1] = "";
                    continue;
                    case 2:
                    /*--------------------------TRUCO-------------------------*/
                        //testa se ainda não pediram truco
                        if(pontoRodada == 0){
                            //se não pediram truco, chamaTruco
                            pontoRodada = chamaTruco(p, pontoRodada,player1, player2);
                            //se chamaTruco retornar 0, quer dizer que não aceitara turco e o turno termina
                            if(pontoRodada == 0){
                                p.setTurno(5);
                                p.adicionaPontos(player1, p.verificaPontos("Nao"));
                                continue;
                            }
                        }else{
                            //testa se já pediram truco
                            if(pontoRodada == 1){
                                //se já pediram chamaRetruco
                                pontoRodada = chamaRetruco(p, pontoRodada, player1, player2);
                                //se chamaRetruco retornar 1, quer dizer que não aceitaram o retruco e o turno termina
                                if(pontoRodada == 1){
                                    p.setTurno(5);
                                    p.adicionaPontos(player1, p.verificaPontos("Truco"));
                                    continue;
                                }
                            }else{
                                //testa se já pediram retruco
                                if(pontoRodada == 2){
                                    //se já pediram retruco
                                    pontoRodada = chamaValeQuatro(p, pontoRodada, player1, player2);
                                    //se chamaValeQuatro retornar 2, quer dizer que não aceitaram o vale quatro e o turno termina
                                    if(pontoRodada == 2){
                                        p.setTurno(5);
                                        p.adicionaPontos(player1, p.verificaPontos("ValeQuatro"));
                                        continue;
                                    }
                                }else{
                                    //se pedirem uma outra opção da mensagem de erro
                                    JOptionPane.showMessageDialog(null, "Escolha uma opção valida!!");
                                    //p.setTurno(p.getTurno() - 1);
                                }
                            }
                        }
                        break;
                    case 3:
                        break;
                }
                /*JOGAR CARTAS */
                //armazena as cartas escolhidas em jc1 e jc2
                Cartas jc1;
                Cartas jc2;
                //enquanto não escolher uma carta valida segue pedindo uma carta
                do{
                    jc1 = jogarCartas(player1);
                }while(jc1 == null);
                //da as opções para o player2
                String[] truco2 = {"Truco", "Soltar Carta", "Envido", "Flor"};
                //se não estiver no primeiro turno então não mostra envido e flor como opção
                if(p.getTurno() != 0){
                    truco2[2] = "";
                    truco2[3] = "";
                }
                //se ainda não pediram truco mostra truco
                if(pontoRodada == 0){
                    truco2[0] = "Truco";
                }else{
                    //se já pediram truco mostra retruco
                    if(pontoRodada == 1){
                        truco2[0] = "Retruco"; 
                    }else{
                        //se já pediram retruco mostra vale quatro
                        if(pontoRodada == 2){
                            truco2[0] = "Vale Quatro";
                        }else{
                            //se já pediram tudo não mostra nada
                            truco2[0] = "";
                        }
                    }
                }
                //se não estiver no primeiro turno não da a opção de pedir envido e flor
                //ou se já for pedido envido
                if(p.getTurno() != 0 || checkEnvido){
                    truco2[2] = "";
                    truco2[3] = "";
                } 
                int t2 = JOptionPane.showOptionDialog(null, cartasPlayer2, player2.getNome(), JOptionPane.YES_NO_OPTION, 0, null, truco2, truco2[0]);
                if(t2 == 0){
                    //testa se ainda não pediram truco
                    if(pontoRodada == 0){
                        //se ainda não pediram, chamaTruco
                        pontoRodada = chamaTruco(p, pontoRodada, player2, player1);
                        //se chamaTruco retornar 0, quer dizer que não aceitaram o truco e termina o turno
                        if(pontoRodada == 0){
                            p.setTurno(5);
                            p.adicionaPontos(player2, p.verificaPontos("Nao"));
                            continue;
                        }
                    }else{
                        //testa se já pediram truco
                        if(pontoRodada == 1){
                            //se já pediram truco, chamaRetruco
                            pontoRodada = chamaRetruco(p, pontoRodada, player2, player1);
                            //se chamaRetruco retornar 1, quer dizer que não aceitaram o retruco e termina o turno
                            if(pontoRodada == 1){
                                p.setTurno(5);
                                p.adicionaPontos(player2, p.verificaPontos("Retruco"));
                                continue;
                            }
                        }else{
                            //testa se já pediram retruco
                            if(pontoRodada == 2){
                                //se já pediram retruco, chamaValeQuatro
                                pontoRodada = chamaValeQuatro(p, pontoRodada, player2, player1);
                                //se chamaValeQuatro retornar 2, quer dizer que não aceitaram o valeQuatro e termina o turno
                                if(pontoRodada == 2){
                                    p.setTurno(5);
                                    p.adicionaPontos(player2, p.verificaPontos("ValeQuatro"));
                                    continue;
                                }
                            }else{
                                //se tentarem escolher outra opção da isso
                                JOptionPane.showMessageDialog(null, "Escolha uma opção valida!!");
                            }
                        }
                    } 
                }else{
                    if(t2 == 2){
                        //se o player2 chamar envido
                        if(p.getTurno() != 0 || checkEnvido){
                            //se já foi pedido ou se já passou do primeiro turno
                            JOptionPane.showMessageDialog(null, "Escolha uma opção valida!!");
                            //p.setTurno(p.getTurno() - 1);
                        }else{
                            checkEnvido = chamaEnvido(p, player1, player2, envP1, envP2);
                        }
                    }else{
                        if(t2 == 3){
                            //se o player2 chamar flor;
                            if(p.getTurno() != 0 || checkEnvido){
                            //se já foi pedido ou se já passou do primeiro turno
                                JOptionPane.showMessageDialog(null, "Escolha uma opção valida!!");
                                //p.setTurno(p.getTurno() - 1);
                            }else{
                                checkEnvido = chamaFlor(p, player2, player1);
                            }
                        }
                    }
                }
                //p.setTurno(p.getTurno() - 1);
                //enquanto player2 não escolher uma carta valida, segue pedindo
                do{
                    jc2 = jogarCartas(player2);
                }while(jc2 == null);
                int resultado = p.calculaForca(jc1, jc2); //calcula quem ganhou a rodada
                //se o player1 ganhou a rodada, soma mais um para player1
                if(resultado == 1){
                    player1.setRodadaGanha(player1.getRodadaGanha() + 1);
                }else{
                    //se player2 ganhou a rodada, soma mais um para player2
                    if(resultado == 2){
                        player2.setRodadaGanha(player2.getRodadaGanha() + 1);
                    }
                }
                //mostra no console quem ganhou
                System.out.println("----------------------\n");
                System.out.println(player1.getNomeNotPontos() + " ganhou " + player1.getRodadaGanha() + "\n");
                System.out.println(player2.getNomeNotPontos() + " ganhou " + player2.getRodadaGanha() + "\n");
                System.out.println("----------------------\n");
                //se player1 ganhou 2 rodadas
                if(player1.getRodadaGanha() == 2){
                    //mensagem que player1 ganhou
                    JOptionPane.showMessageDialog(null, player1.getNomeNotPontos() + " ganhou a rodada!!");
                    if(pontoRodada == 0){
                        //se não pediram nada ele ganha um ponto
                        p.adicionaPontos(player1, p.verificaPontos("Nao"));
                    }else{
                        //se pediram truco ele ganha 2 pontos
                        if(pontoRodada  == 1){
                            p.adicionaPontos(player1, p.verificaPontos("Truco"));
                        }else{
                            //se pediram retruco ele ganha 3 pontos
                            if(pontoRodada == 2){
                                p.adicionaPontos(player1, p.verificaPontos("Retruco"));
                            }else{
                                //se pediram vale quatro ele ganha 4 pontos
                                if(pontoRodada == 3){
                                    p.adicionaPontos(player1, p.verificaPontos("ValeQuatro"));
                                }
                            }
                        }
                    }
                    //termina o turno
                    p.setTurno(3);
                }else{
                    //se o player2 ganhou dois turnos
                    if(player2.getRodadaGanha() == 2){
                        //mensagem que player2 ganhou
                        JOptionPane.showMessageDialog(null, player2.getNomeNotPontos() + " ganhou a rodada!!");
                        //se não pediram nada ganha 1 ponto
                        if(pontoRodada == 0){
                            p.adicionaPontos(player2, p.verificaPontos("Nao"));
                        }else{
                            //se pediram truco ganha 2 pontos
                            if(pontoRodada == 1){
                                p.adicionaPontos(player2, p.verificaPontos("Truco"));
                            }else{
                                //se pediram retruco ganha 3 pontos
                                if(pontoRodada == 2){
                                    p.adicionaPontos(player2, p.verificaPontos("Retruco"));
                                }else{
                                    //se pediram vale quatro ganha 4 pontos
                                    if(pontoRodada == 3){
                                        p.adicionaPontos(player2, p.verificaPontos("ValeQuatro"));
                                    }
                                }
                            }
                        }
                        //termina o turno
                        p.setTurno(3);
                    }else{
                        //se empataram um turno mas o player1 ganhou o outro
                        if(p.getTurno() == 2 && player1.getRodadaGanha() == 1 && player2.getRodadaGanha() == 0){
                            //mensgaem player1 ganhou
                            JOptionPane.showMessageDialog(null, player1.getNome() + " ganhou a rodada!!");
                            //se não pediram nada ganha 1 ponto
                            if(pontoRodada == 0){
                                p.adicionaPontos(player1, p.verificaPontos("Nao"));
                            }else{
                                //se pediram truco ganha 2 pontos
                                if(pontoRodada  == 1){
                                    p.adicionaPontos(player1, p.verificaPontos("Truco"));
                                }else{
                                    //se pediram retruco ganha 3 pontos
                                    if(pontoRodada == 2){
                                        p.adicionaPontos(player1, p.verificaPontos("Retruco"));
                                    }else{
                                        //se pediram vale quatro ganha 4 pontos
                                        if(pontoRodada == 3){
                                            p.adicionaPontos(player1, p.verificaPontos("ValeQuatro"));
                                        }
                                    }
                                }
                            }
                            //termina o turno
                            p.setTurno(3);
                        }else{
                            //se empataram um turno, mas o player2 ganhou o outro
                            if(p.getTurno() == 2 && player1.getRodadaGanha() == 0 && player2.getRodadaGanha() == 1){
                                //mensagem player2 ganhou
                                JOptionPane.showMessageDialog(null, player2.getNome() + " ganhou a rodada!!");
                                //se não pediram nada ganha 1 ponto
                                if(pontoRodada == 0){
                                    p.adicionaPontos(player2, p.verificaPontos("Nao"));
                                }else{
                                    //se pediram truco ganha 2 pontos
                                    if(pontoRodada == 1){
                                        p.adicionaPontos(player2, p.verificaPontos("Truco"));
                                    }else{
                                        //se pediram retruco ganha 3 pontos
                                        if(pontoRodada == 2){
                                            p.adicionaPontos(player2, p.verificaPontos("Retruco"));
                                        }else{
                                            //se pediram vale quatro ganha 4 pontos
                                            if(pontoRodada == 3){
                                                p.adicionaPontos(player2, p.verificaPontos("ValeQuatro"));
                                            }
                                        }
                                    }
                                }
                                //termina turno
                                p.setTurno(3);
                            }else{
                                //se foram 3 turnos, ninguém ganhou
                                if(p.getTurno() == 3){
                                    //se player1 é o mão
                                    if(player1.isMao()){
                                        //mensagem player1 ganhou
                                        JOptionPane.showMessageDialog(null, player1.getNome() + " ganhou a rodada!!");
                                        //se não pediram nada ganha 1 ponto
                                        if(pontoRodada == 0){
                                            p.adicionaPontos(player1, p.verificaPontos("Nao"));
                                        }else{
                                            //se pediram truco ganha 2 pontos
                                            if(pontoRodada == 1){
                                                p.adicionaPontos(player1, p.verificaPontos("Truco"));
                                            }else{
                                                //se pediram retruco ganha 3 pontos
                                                if(pontoRodada == 2){
                                                    p.adicionaPontos(player1, p.verificaPontos("Retruco"));
                                                }else{
                                                    //se pediram vale quatro ganha 4 pontos
                                                    if(pontoRodada == 3){
                                                        p.adicionaPontos(player1, p.verificaPontos("ValeQuatro"));
                                                    }
                                                }
                                            }
                                        }
                                        //termina o turno
                                        p.setTurno(3);
                                    }else{
                                        //se o player2 é mão
                                        //mensagem player2 ganhou
                                        JOptionPane.showMessageDialog(null, player2.getNome() + " ganhou a rodada!!");
                                        //se não pediram nada ganha 1 ponto
                                        if(pontoRodada == 0){
                                            p.adicionaPontos(player2, p.verificaPontos("Nao"));
                                        }else{
                                            //se pediram truco ganha 1 ponto
                                            if(pontoRodada == 1){
                                                p.adicionaPontos(player2, p.verificaPontos("Truco"));
                                            }else{
                                                //se pediram retruco ganha 2 pontos
                                                if(pontoRodada == 2){
                                                    p.adicionaPontos(player2, p.verificaPontos("Retruco"));
                                                }else{
                                                    //se pediram vale quatro ganha 3 pontos
                                                    if(pontoRodada == 3){
                                                        p.adicionaPontos(player2, p.verificaPontos("ValeQuatro"));
                                                    }
                                                }
                                            }
                                        }
                                        //termina o turno
                                        p.setTurno(3);
                                    }
                                }
                            }
                        }
                    }
                }
                //incrementa a rodada
                p.setTurno(p.getTurno() + 1);
                //pra não aparecer a opção de envido mais
                checkEnvido = true;
                //altera quem é o player1 e o player2
                Player aux = null;
                if(resultado == 1){ 
                    player1.setMao(true);
                    player2.setMao(false);
                }else{
                    if(resultado == 2){
                        aux = player1;
                        player1 = player2;
                        player2 = aux;
                        jogador1.setMao(false);
                        jogador2.setMao(true);
                    }else{
                        if(player1.isMao()){
                            player1.setMao(false);
                            player2.setMao(true);
                        }else{
                            aux = player1;
                            player1 = player2;
                            player2= aux;
                            player1.setMao(true);
                            player2.setMao(false);
                        }
                    }
                }
                System.out.println("j: " + p.getTurno());
            }
            //da um espaço entre cada turno
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n");
        }
        //quando o jogo termina mostra os pontos de cada jogador conforme quem ganhou
        if (player1.getPontos() > player2.getPontos()){
            JOptionPane.showMessageDialog(null, "\nVENCEDOR\n\n" + player1.toString());
            JOptionPane.showMessageDialog(null, "\nPERDEDOR\n\n" + player2.toString());
        }else{
            JOptionPane.showMessageDialog(null, "\nVENCEDOR\n\n" + player2.toString());
            JOptionPane.showMessageDialog(null, "\nPERDEDOR\n\n" + player1.toString());
        }
    }
    
    public static boolean chamaEnvido(Partida p, Player player1, Player player2, int envP1, int envP2){
        Object[] confirmacao = {"Sim", "Não"};
        Object[] cartasPlayer1 = {player1.listaCarta[0] != null ? player1.listaCarta[0].toString() : "", 
                                  player1.listaCarta[1] != null ? player1.listaCarta[1].toString() : "", 
                                  player1.listaCarta[2] != null ? player1.listaCarta[2].toString() : ""};
        //mostra só as cartas que o player2 ainda não jogou
        Object[] cartasPlayer2 = {player2.listaCarta[0] != null ? player2.listaCarta[0].toString() : "", 
                                  player2.listaCarta[1] != null ? player2.listaCarta[1].toString() : "", 
                                  player2.listaCarta[2] != null ? player2.listaCarta[2].toString() : ""};
        String[] envidoOpt = {"Não Quero", "Quero", "Real Envido", "Falta Envido"};
        int envido = JOptionPane.showOptionDialog(null, cartasPlayer2, player2.getNome() + " aceita o envido?", JOptionPane.YES_NO_OPTION, 0, null, envidoOpt, envidoOpt[0]);
            //se aceitar o envido
           if(envido == 1){
               //mostra os pontos do player1 e do player2
               System.out.println("Quero");
               System.out.println(envP1);
               System.out.println(envP2);
               //calcula pra saber quem ganhou
               int g = p.venceChamada(player1.isMao(), envP1, envP2);
               //se player1 ganhou atribui os pontos do envido para player1
               if(g == 1){
                   p.adicionaPontos(player1, p.verificaPontos("Envido"));
               }else{
                   //senão atribui os pontos do envido para o player2
                   p.adicionaPontos(player2, p.verificaPontos("Envido"));
               }
           }else{
               //se pediu real envido
               if(envido == 2){
                   int resposta_envido = JOptionPane.showOptionDialog(null, cartasPlayer1, player1.getNome() + " aceita o real envido?", JOptionPane.YES_NO_OPTION, 0, null, confirmacao, confirmacao[0]);
                   //se aceitou o real envido
                   if(resposta_envido == 1){
                       //calcula quem ganha
                       int g = p.venceChamada(player1.isMao(), envP1, envP2);
                       //se player1 ganha atribui os pontos do real envido
                       if(g == 1){
                           p.adicionaPontos(player1, p.verificaPontos("RealEnvido"));
                       }else{
                           //se player2 ganha atribui os pontos do real envido
                           p.adicionaPontos(player2, p.verificaPontos("RealEnvido"));
                       }
                   }else{
                       //se não aceitar atribui os pontos do envido
                       p.adicionaPontos(player2, p.verificaPontos("Envido"));
                   }
               }else{
                   //se pedir falta envido
                   if(envido == 3){
                       int resposta_envido = JOptionPane.showOptionDialog(null, cartasPlayer1, player1.getNome() + " aceita a Falta Envido?", JOptionPane.YES_NO_OPTION, 0, null, confirmacao, confirmacao[0]);
                       //se aceitar o falta envido
                       if(resposta_envido == 0){
                           //calcula quem ganhou
                           int g = p.venceChamada(player1.isMao(), envP1, envP2);
                           //se player1 ganhou atribui os pontos do falta envido
                           if(g == 1){
                               p.adicionaPontos(player1, p.verificaPontos("FaltaEnvido"));
                           }else{
                               //se o player2 ganhou atribui os pontos do falta envido
                               p.adicionaPontos(player2, p.verificaPontos("FaltaEnvido"));
                           }
                       }else{
                           //se não aceitar atribui os pontos do envido
                           p.adicionaPontos(player2, p.verificaPontos("Envido"));
                       }
                   }else{
                       //se não aceitar atribui um não
                       p.adicionaPontos(player2, p.verificaPontos("Nao"));
                   }    
               }
           }
           //mostra que já foi chamado
           return true;
    }
    
    public static boolean chamaFlor(Partida p, Player player1, Player player2){
        Object[] confirmacao = {"Sim", "Não"};
        String cartasPlayer1 = player1.listaCarta[0].toString() + player1.listaCarta[1].toString() + player1.listaCarta[2].toString();
        //mostra só as cartas que o player 2 ainda não jogou
        Object[] cartasPlayer2 = {player2.listaCarta[0] != null ? player2.listaCarta[0].toString() : "", 
                                  player2.listaCarta[1] != null ? player2.listaCarta[1].toString() : "", 
                                  player2.listaCarta[2] != null ? player2.listaCarta[2].toString() : ""};
        String[] florOpt = {"É bom...", "Contra Flor", "Flor ao Resto"};
        int flor = JOptionPane.showOptionDialog(null, cartasPlayer2, player2.getNome(), JOptionPane.YES_NO_OPTION, 0, null, florOpt, florOpt[0]);
            //se não tem flor
            if(flor == 0){
                //atribui os pontos de flor para o player2
                p.adicionaPontos(player1, p.verificaPontos("Flor"));
            }else{
                //se pede contra flor
                if(flor == 1){
                    int resposta_flor = JOptionPane.showOptionDialog(null, "Aceita o Contra Flor", player2.getNome(), JOptionPane.YES_NO_OPTION, 0, null, confirmacao, confirmacao[0]);
                    //e aceita a contra flor
                    if(resposta_flor == 0){
                        //calcula os pontos do player1 e do player2
                        int florP1 = 0;
                        if(p.isFlor(player1.listaCarta)){
                            florP1 = p.calculaFlor(player1.listaCarta);
                        }
                        int florP2 = 0;
                        if(p.isFlor(player2.listaCarta)){
                            florP2 = p.calculaFlor(player2.listaCarta);
                        }
                        //calcula quem vence entre eles
                        int g = p.venceChamada(player1.isMao(), florP1, florP2);
                        //se player1 ganhar atribui os pontos de contra flor
                        if(g == 1){
                            p.adicionaPontos(player1, p.verificaPontos("ContraFlor"));
                        }else{
                            //se o player2 ganhar atribui os pontos do contra flor
                            p.adicionaPontos(player2, p.verificaPontos("ContraFlor"));
                        }
                    }else{
                        //se não aceitar atribui os pontos de flor
                        p.adicionaPontos(player2, p.verificaPontos("Flor"));
                    }
                }else{
                    //se pedir flor e o resto
                    int resposta_flor = JOptionPane.showOptionDialog(null, "Aceita o Flor ao Resto?", player2.getNome(), JOptionPane.YES_NO_OPTION, 0, null, confirmacao, confirmacao[0]);
                    //se aceitar o flor e o resto
                    if(resposta_flor == 0){
                        //calcula os pontos de player1 e player2
                        int florP1 = 0;
                        if(p.isFlor(player1.listaCarta)){
                            florP1 = p.calculaFlor(player1.listaCarta);
                        }
                        int florP2 = 0;
                        if(p.isFlor(player2.listaCarta)){
                            florP2 = p.calculaFlor(player2.listaCarta);
                        }
                        //cacula quem vence
                        int g = p.venceChamada(player1.isMao(), florP1, florP2);
                        //se player1 ganhar atribui os pontos de flor e o resto
                        if(g == 1){
                            p.adicionaPontos(player1, p.verificaPontos("FlorEoResto"));
                        }else{
                            //se player2 ganhar atribui os potnso de flor o resto
                            p.adicionaPontos(player2, p.verificaPontos("FlorEoResto"));
                        }
                    }else{
                        //se não aceitar atribui pontos da Flor
                        p.adicionaPontos(player2, p.verificaPontos("Flor"));
                    }
                }
            }
            //mostra que ja foi chamado
            return true;
    }
    
    public static int chamaTruco(Partida p, int partidaRodada, Player player1, Player player2){
        String[] trucoOpt = {"Quero", "Não quero", "Retruco"};
            int truco = JOptionPane.showOptionDialog(null, "Aceita o Truco?", player2.getNome(), JOptionPane.YES_NO_OPTION, 0, null, trucoOpt, trucoOpt[0]);
            //se aceita o truco
            if(truco == 0){
                //implementa o partidaRodada para saber que a partida agora está no truco
                partidaRodada++;
            }else{
                //se pede retruco
                if(truco == 2){
                    //incrementa o partidaRodada para saber que passou pelo truco
                    partidaRodada++;
                    //chama a função do retruco
                    partidaRodada = chamaRetruco(p, partidaRodada, player2, player1);
                }else{
                    //se não aceita retorna 0
                    return 0;
                }
            }
            return partidaRodada;
    }
    
    public static int chamaRetruco(Partida p, int partidaRodada, Player player1, Player player2){
        String[] trucoOpt = {"Quero", "Não quero", "Vale Quatro"};
            int truco = JOptionPane.showOptionDialog(null, "Aceita o Retruco?", player2.getNome(), JOptionPane.YES_NO_OPTION, 0, null, trucoOpt, trucoOpt[0]);
            //se aceita o retruco
            if(truco == 0){
                //incrementa a partidaRodada para saber que está no retruco
                partidaRodada++;
            }else{
                //se chama vale quatro
                if(truco == 2){
                    //incrementa partidaRodada para saber que passou pelo retruco
                    partidaRodada++;
                    //chama a função do vale quatro
                    partidaRodada = chamaValeQuatro(p, partidaRodada, player2, player1);
                }else{
                    //retorna partidaRodada significando não
                    return partidaRodada;
                }
            }
            //retorna partidaRodada significando não
            return partidaRodada;   
    }
    
    public static int chamaValeQuatro(Partida p, int partidaRodada, Player player1, Player player2){
        Object[] confirmacao = {"Sim", "Não"};
            int truco = JOptionPane.showOptionDialog(null, "Aceita o Vale Quatro?", player2.getNome(), JOptionPane.YES_NO_OPTION, 0, null, confirmacao, confirmacao[0]);
            //se aceita o vale quatro
            if(truco == 0){
                //incrementa o partidaRodada mostrando que está no vale quatro
                partidaRodada++;
            }else{
                //retorna partidaRodada mostrando que não aceitou
               return partidaRodada;
            }
            //retorna partidaRodada mostrando que não aceitou
            return partidaRodada;
    }
    
    public static Cartas jogarCartas(Player player){
        //mostra as cartas que ainda tem na mão
        String[] jogarCarta = {player.listaCarta[0] != null ? player.listaCarta[0].toString() : "", 
                               player.listaCarta[1] != null ? player.listaCarta[1].toString() : "", 
                               player.listaCarta[2] != null ? player.listaCarta[2].toString() : ""};
        int escolheCarta = JOptionPane.showOptionDialog(null, "Qual carta jogar?", player.getNome(), JOptionPane.YES_NO_OPTION, 0, null, jogarCarta, jogarCarta[0]);
        switch(escolheCarta){
            case 0:
                //se escolhe a primeira carta e ela não foi jogada ainda
                if(player.listaCarta[0] != null){
                    //armazena numa variavel auxiliar
                    Cartas f1 = player.listaCarta[0];
                    //mostra a carta que foi largada
                    System.out.println(player.getNome() + "\n" + player.listaCarta[0].toString());
                    //atribui null para não poder ser usada
                    player.listaCarta[0] = null;
                    //retorna a carta que foi escolhida
                    return f1;
                }else{
                    //se a carta já foi jogada retorna a mensagem de erro e null
                    JOptionPane.showMessageDialog(null, "Escolha uma opção valida!!");
                    return null;
                }
            case 1:
                //se escolhe a segunda carta e ela não foi jogada ainda
                if(player.listaCarta[1] != null){
                    //armazena numa variavel auxiliar
                    Cartas f2 = player.listaCarta[1];
                    //mostra a carta que foi largada
                    System.out.println(player.getNome() + "\n" + player.listaCarta[1].toString());
                    //atribui null para ela não poder ser usada
                    player.listaCarta[1] = null;
                    //retorna carta
                    return f2;
                }else{
                    //se a carta já foi jogada retorna mensagem de erro e null
                    JOptionPane.showMessageDialog(null, "Escolha uma opção valida!!");                    
                    return null;
                }
            case 2:
                //se escolhe a terceira carta e ela não foi jogada ainda
                if(player.listaCarta[2] != null){
                    //armazena numa variavel auxiliar
                    Cartas f3 = player.listaCarta[2];
                    //mostra a carta que foi largada
                    System.out.println(player.getNome() + "\n" + player.listaCarta[2].toString());
                    //atribui null para não poder ser mais usada
                    player.listaCarta[2] = null;
                    //retorna a carta
                    return f3;
                }else{
                    //se a carta já foi largada retorna mensagem de erro e null
                    JOptionPane.showMessageDialog(null, "Escolha uma opção valida!!");
                    return null;
                }
        }
        //retorna null
        return null;
    }
    
    public static void darCartas(Player player1, Player player2, Partida p){
        //inicializa o numero aleatório
        Random rand = new Random();
        int numeroCarta;
        //testa para não ter cartas repetidas
        do{
            for(int i = 0; i < 3; i++){
                //tira uma carta entre 1 - 7 e 10 - 12
                do{
                    numeroCarta = rand.nextInt(12) + 1;
                }while(numeroCarta == 8 || numeroCarta == 9);
                //escolhe um naipe
                int numeroNaipe = rand.nextInt(4) + 1;
                switch(numeroNaipe){
                    case 1:
                        //se tira 1 cria uma instancia do basto
                        Basto basto = new Basto();
                        //atribui o numero
                        basto.setNumero(numeroCarta);
                        //atribui a força
                        p.atribuiForca(basto, numeroCarta);
                        //armazena para o player1
                        player1.listaCarta[i] = basto;
                        break;
                    case 2:
                        //se tira 2 cria uma instancia de copas
                        Copas copa = new Copas();
                        //atirbui numero
                        copa.setNumero(numeroCarta);
                        //atribui forçca
                        p.atribuiForca(copa, numeroCarta);
                        //armazena para o player1
                        player1.listaCarta[i] = copa;
                        break;
                    case 3:
                        //se tira 3 cria uma instancia de espada
                        Espada espada = new Espada();
                        //atribui o numero
                        espada.setNumero(numeroCarta);
                        //atribui a força
                        p.atribuiForca(espada, numeroCarta);
                        //armazena para o player1
                        player1.listaCarta[i] = espada;
                        break;
                    case 4:
                        //se tira 4 cria uma instancia de ouro
                        Ouro ouro = new Ouro();
                        //atribui o numero
                        ouro.setNumero(numeroCarta);
                        //atribui a força
                        p.atribuiForca(ouro, numeroCarta);
                        //armazena para o player1
                        player1.listaCarta[i] = ouro;
                        break;
                }
            }

            for(int i = 0; i < 3; i++){
                //escolhe um numero entre 1 - 7, 10 - 12
                do{
                    numeroCarta = rand.nextInt(12) + 1;
                }while(numeroCarta == 8 || numeroCarta == 9);
                //escolhe um numero entre 1 - 4
                int numeroNaipe = rand.nextInt(4) + 1;
                switch(numeroNaipe){
                    case 1:
                        //se tira 1 cria uma instancia do basto
                        Basto basto = new Basto();
                        //atribui o numero
                        basto.setNumero(numeroCarta);
                        //atribui a força
                        p.atribuiForca(basto, numeroCarta);
                        //armazena para o player2
                        player2.listaCarta[i] = basto;
                        break;
                    case 2:
                        //se tira 2 cria uma instancia de copas
                        Copas copa = new Copas();
                        //atirbui numero
                        copa.setNumero(numeroCarta);
                        //atribui forçca
                        p.atribuiForca(copa, numeroCarta);
                        //armazena para o player2
                        player2.listaCarta[i] = copa;
                        break;
                    case 3:
                        //se tira 3 cria uma instancia de espada
                        Espada espada = new Espada();
                        //atribui o numero
                        espada.setNumero(numeroCarta);
                        //atribui a força
                        p.atribuiForca(espada, numeroCarta);
                        //armazena para o player2
                        player2.listaCarta[i] = espada;
                        break;
                    case 4:
                        //se tira 4 cria uma instancia de ouro
                        Ouro ouro = new Ouro();
                        //atribui o numero
                        ouro.setNumero(numeroCarta);
                        //atribui a força
                        p.atribuiForca(ouro, numeroCarta);
                        //armazena para o player2
                        player2.listaCarta[i] = ouro;
                        break;
                }
            }
        }while(cartasRepetidas(player1.listaCarta, player2.listaCarta));
    }
    
    public static boolean cartasRepetidas(Cartas[] player1, Cartas[] player2){
        //testa se tem cartas repetidas entre o player1
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(i != j){
                    if(player1[i].getNumero() == player1[j].getNumero()){
                        if(player1[i].getNaipe().equals(player1[j].getNaipe())){
                            return true;
                        }
                    }
                }
            }
        }
        //testa se tem cartas repetidas entre o player2
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(i != j){
                    if(player2[i].getNumero() == player2[j].getNumero()){
                        if(player1[i].getNaipe().equals(player1[j].getNaipe())){
                            return true;
                        }
                    }
                }
            }
        }
        //testa se tem cartas repetidas entre o player1 e o player2
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(player1[i].getNumero() == player2[j].getNumero()){
                    if(player1[i].getNaipe().equals(player2[j].getNaipe())){
                        return true;
                    }
                }
            }
        }
        //teste se tem cartas repetidas entre o player2 e o player1
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(player2[i].getNumero() == player1[j].getNumero()){
                    if(player2[i].getNaipe().equals(player1[j].getNaipe())){
                        return true;
                    }
                }
            }
        }
        //não tem cartas repetidas
        return false;
    }
}
