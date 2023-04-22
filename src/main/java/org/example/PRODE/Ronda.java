package org.example.PRODE;

import lombok.*;

import java.util.ArrayList;

public class Ronda {
    @Setter @Getter
    private int numero;
    @Setter @Getter
    private ArrayList <Partido> partidos;

    //le pasamos como parametro la lista de los pronosticos de la persona y va comparando los resultados de cada uno de los partidos por medio de los enum
    public Ronda(int numeroRonda){
        this.numero = numeroRonda;
        partidos = new ArrayList<>();
    }
    public int getPuntos(int puntoPorPartido, int puntosExtra,ArrayList<Pronostico> pronosticos){
        int puntos=0;
        for (int i =0;i<partidos.size();i++){
            if (partidos.get(i).getResultado().equals(pronosticos.get(i).getResultado()))
                puntos+=puntoPorPartido;
        }
        if(getAciertos(pronosticos)==partidos.size())
            return puntos + puntosExtra;
        return puntos;
    }

    public int getAciertos(ArrayList<Pronostico> pronosticos){
        int aciertos=0;
        for (int i =0;i<partidos.size();i++){
            if (partidos.get(i).getResultado().equals(pronosticos.get(i).getResultado()))
                aciertos++;
        }
        return aciertos;
    }

    public void setPartido(Partido p){
        partidos.add(p);
    }
}
