package org.example.PRODE;

import lombok.*;

@AllArgsConstructor
public class Partido {
    @Setter @Getter
    private int nRonda;
    private Equipo equipo1;
    private Equipo equipo2;
    @Getter
    private int goles1;
    @Getter
    private int goles2;

    public ResultadoEnum getResultado(){ //compara la cantidad de goles y devuelve el resultado como un enum
        ResultadoEnum resultado;
        if (goles1>goles2)
            resultado = ResultadoEnum.ganaEquipo1;
        else if (goles1<goles2)
            resultado = ResultadoEnum.ganaEquipo2;
        else
            resultado = ResultadoEnum.empate;
        return resultado;
    }

    public String getEquipo1() {
        return equipo1.getNombre();
    }

    public String getEquipo2() {
        return equipo2.getNombre();
    }
}

