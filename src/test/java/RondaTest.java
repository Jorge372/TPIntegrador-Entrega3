import org.example.PRODE.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RondaTest {

    @BeforeAll
    public static void inicio(){
        System.out.println("Iniciando pruebas...");
    }
    @AfterAll
    public static void fin(){
        System.out.println("Pruebas finalizadas exitosamente.");
    }

    @Test
    void getPuntos() {

        //Creamos 2 rondas:
        Ronda r1 = new Ronda(1);
        Ronda r2 = new Ronda(2);

        //Creamos 2 equipos
        Equipo equipo1 = new Equipo("Argentina");
        Equipo equipo2 = new Equipo("Brasil");

        //Creamos el partido y se lo asignamos a la ronda 1
        Partido partido1 = new Partido(r1.getNumero(),equipo1,equipo2,3,1);
        r1.setPartido(partido1);

        //Creamos el pronostico de 2 personas
        Pronostico pJuan1 = new Pronostico(ResultadoEnum.ganaEquipo1);
        Pronostico pLucia1 = new Pronostico(ResultadoEnum.ganaEquipo1);

        //Creamos el segundo partido y se lo asignamos a la ronda 2
        Partido partido2 = new Partido(r2.getNumero(),equipo1,equipo2,2,2);
        r2.setPartido(partido2);

        //Creamos el pronostico de 2 personas
        Pronostico pJuan2 = new Pronostico(ResultadoEnum.ganaEquipo1);
        Pronostico pLucia2 = new Pronostico(ResultadoEnum.empate);

        //Creamos las personas
        Persona persona1 = new Persona("Juan");
        Persona persona2 = new Persona("Lucia");

        //Le asignamos a cada persona sus pronosticos
        persona1.agregarPronostico(pJuan1);
        persona1.agregarPronostico(pJuan2);
        persona2.agregarPronostico(pLucia1);
        persona2.agregarPronostico(pLucia2);

        //Sobre esos pronosticos le asignamos el partido a c/u
        persona1.setPartido(0,partido1);
        persona1.setPartido(1,partido2);
        persona2.setPartido(0,partido1);
        persona2.setPartido(1,partido2);

        //Calulamos los puntos de cada persona de cada ronda:
        assertEquals(2, r1.getPuntos(2,0,persona1.getPronosticosRonda(1)));
        assertEquals(2, r1.getPuntos(2,0,persona2.getPronosticosRonda(1)));
        assertEquals(0, r2.getPuntos(1,2,persona1.getPronosticosRonda(2)));
        assertEquals(3, r2.getPuntos(1,2,persona2.getPronosticosRonda(2)));

        //Calulamos los aciertos de cada persona:
        assertEquals(1, r1.getAciertos(persona1.getPronosticosRonda(1)));
        assertEquals(1, r1.getAciertos(persona2.getPronosticosRonda(1)));
        assertEquals(0, r2.getAciertos(persona1.getPronosticosRonda(2)));
        assertEquals(1, r2.getAciertos(persona2.getPronosticosRonda(2)));
    }
}
