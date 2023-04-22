package org.example.PRODE;

import lombok.*;


public class Pronostico {
    @Getter @Setter
    private Partido partido;
    @Getter
    private final ResultadoEnum resultado;

    public Pronostico(ResultadoEnum r){
        resultado=r;
    }

    @Override
    public String toString(){
        String cadena = "aposto por ";
        ResultadoEnum r = getResultado();
        if (r== ResultadoEnum.ganaEquipo1)
            cadena += "el equipo 1 ("+partido.getEquipo1()+")";
        else if (r== ResultadoEnum.ganaEquipo2)
            cadena += "el equipo 2 ("+partido.getEquipo2()+")";
        else
            cadena += "empate";
        return cadena;
    }

    public static ResultadoEnum obtenerResultado(int a, int b) { //Verifica en que "celda" la persona marco para apostar por dicho equipo y lo establece como resultado en un Enum
        ResultadoEnum resultado;
        if (a==1)
            resultado = ResultadoEnum.ganaEquipo1;
        else if (b==1)
            resultado = ResultadoEnum.ganaEquipo2;
        else
            resultado = ResultadoEnum.empate;
        return resultado;
    }
}
