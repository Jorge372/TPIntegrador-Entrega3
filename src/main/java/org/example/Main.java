package org.example;

import org.example.PRODE.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    /*Este programa recibe como parametro: la url de la DB que tiene las tablas pronostico y resultado, el nombre de usuario de la DB,
        la contreña de la DB, los puntos por partido ganado y los puntos extra por acertar todos los partidos de una ronda:
            "jdbc:mysql://db4free.net/jorge1db2023" "jorgearbachsql" "pass2023" "1" "2"
    La tabla resultado debe tener 6 columnas (id_partido, ronda, equipo1, cant_goles1, cant_goles2, equipo2)
    La tabla pronostico debe tener 8 columnas (id_pronostico, particicpante, id_partido, equipo1, gana1, empate, gana2, equipo2)*/
    static String tabla1 = "partido";
    static int columnas1 = 6;
    static String tabla2 = "pronostico";
    static int columnas2 = 8;
    public static void main(String[] args) throws DatosErroneosException {
        ArrayList<Equipo> equipos1; //Aca se almacenaran los equipos 1 de los x partidos
        ArrayList<Equipo> equipos2; //Aca se almacenaran los equipos 2 de los x partidos
        ArrayList<Partido> partidos; //Aca se almacenaran todos los partidos
        ArrayList<Ronda> rondas;
        HashMap<String, Persona> personas;
        ConectorDbMySql conector = new ConectorDbMySql(args[0],args[1],args[2]);

        //Verificamos las tablas
        conector.verificarDatosTabla(tabla1,columnas1);
        conector.verificarDatosTabla(tabla2,columnas2);

        //Obtenemos las personas que hacen los pronosticos
        personas = conector.obtenerPersonas();

        //Creamos las rondas correspondientes
        rondas = conector.obtenerRondas();

        //Creamos los 2 arrays de equipos de la base de datos
        equipos1 = conector.obtenerEquipos1();
        equipos2 = conector.obtenerEquipos2();

        //Creamos los partidos
        partidos = conector.obtenerPartidos(equipos1, equipos2);

        //Asignamos los partidos de las rondas
        for (Ronda r : rondas)
            for (Partido p : partidos) {
                if (p.getNRonda() == r.getNumero())
                    r.setPartido(p);
            }

        //ASignamos los pronosticos a las personas
        for (Persona p : personas.values()) {
            p.setPronosticos(conector.obtenerPronosticos(p.getNombre()));
        }

        //Le asignamos los partidos a cada uno de estos pronosticos de las personas
        for (Persona p : personas.values()) {
            for (int j = 0; j < partidos.size(); j++) {
                p.setPartido(j, partidos.get(j));//le asignamos el partido sobre el cual hizo el pronostico
            }
        }

        int puntos;
        //imprimimos los pronosticos por ronda
        for (Ronda r : rondas) {
            System.out.println("\t\t\tRONDA NUMERO " + r.getNumero() + ":\n"); //imprimimos de cada ronda
            for (int i = 0; i < r.getPartidos().size(); i++) {
                System.out.print("Encuentro nº" + (i + 1) + ": (" + r.getPartidos().get(i).getEquipo1() + " - " + r.getPartidos().get(i).getEquipo2() + "): " + r.getPartidos().get(i).getResultado() + "\n");
                for (Persona p : personas.values()) {
                    System.out.println("\t_ " + p + " " + p.getPronosticosRonda(r.getNumero()).get(i)); //imprimimos el pronostico de cada persona
                }
            }
            //imprimimos el resultado
            System.out.println();
            for (Persona p : personas.values()) {
                puntos=r.getPuntos(Integer.parseInt(args[3]),Integer.parseInt(args[4]),p.getPronosticosRonda(r.getNumero()));//calculamos los puntos de la ronda de esta persona en base a la configuracion que le dimos en los parametros
                System.out.println("\t" + p + " sumo " + puntos + " PUNTO/S en la ronda " + r.getNumero());
                p.sumarPuntos(puntos);
                p.sumarAciertos(r.getAciertos(p.getPronosticosRonda(r.getNumero())));
            }
            System.out.println("-----------------------------------------------------------------------------");
        }

        //imprimimos el total
        System.out.printf("%17s| %17s| %17s|", "NOMBRE", "PUNTOS", "ACIERTOS");
        System.out.println();
        for (Persona p : personas.values()) {
            System.out.printf("%17s  %17s  %17s ", p, p.getPuntos(), p.getAciertos());
            System.out.println();
        }

        conector.cerrarConexiones();
    }
}
