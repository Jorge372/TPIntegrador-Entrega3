package org.example.PRODE;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ConectorDbMySql {
    private Statement stmt;
    private ResultSet rs;
    public ConectorDbMySql(String url, String usuario, String contra){
        try {
            Connection conne = DriverManager.getConnection(url,
                    usuario, contra);
            stmt = conne.createStatement(); //Este método es usado para crear la sentencia. Esta sentencia es la responsable de ejecutar las consultas a la DB.
        } catch (Exception e){System.out.println("Error: "+e.getMessage());}
    }

    public HashMap<String,Persona> obtenerPersonas(){
        rs = null;
        HashMap<String,Persona> personas = new HashMap<>();

        try {
            rs = stmt.executeQuery("select * from pronostico\n"+"\n" + "ORDER BY id_pronostico");//Este método realiza la consulta y devuelve el resultado de la misma

            while (rs.next())  {
                personas.put(rs.getString("participante"),new Persona(rs.getString("participante")));
            }
        } catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }

        return personas;
    }

    public ArrayList<Ronda> obtenerRondas(){
        rs = null;
        HashMap<Integer,Ronda> rondasaux = new HashMap<>();

        try {
            rs = stmt.executeQuery("select * from partido\n"+"\n" + "ORDER BY id_partido");

            while (rs.next())  {
                rondasaux.put(rs.getInt("ronda"),new Ronda(rs.getInt("ronda")));
            }
        } catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }

        return new ArrayList<>(rondasaux.values());
    }

    public ArrayList<Equipo> obtenerEquipos1(){
        rs = null;
        ArrayList<Equipo> equipos1 = new ArrayList<>();

        try {
            rs = stmt.executeQuery("select * from partido\n"+"\n" + "ORDER BY id_partido");

            while (rs.next())  {
                equipos1.add(new Equipo(rs.getString("equipo1")));
            }
        } catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }

        return equipos1;
    }

    public ArrayList<Equipo> obtenerEquipos2(){
        rs = null;
        ArrayList<Equipo> equipos1 = new ArrayList<>();

        try {
            rs = stmt.executeQuery("select * from partido\n"+"\n" + "ORDER BY id_partido");

            while (rs.next())  {
                equipos1.add(new Equipo(rs.getString("equipo2")));
            }
        } catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }

        return equipos1;
    }

    public ArrayList<Partido> obtenerPartidos(ArrayList<Equipo> equipos1, ArrayList<Equipo> equipos2){
        rs = null;
        ArrayList<Partido> partidos = new ArrayList<>();

        try {
            rs = stmt.executeQuery("SELECT * FROM partido\n" +"\n" + "ORDER BY id_partido");

            int i=0;
            while (rs.next())  {
                partidos.add(new Partido(rs.getInt("ronda"), equipos1.get(i), equipos2.get(i),
                        rs.getInt("cant_goles1"), rs.getInt("cant_goles2")));
                i++;
            }
        } catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }

        return partidos;
    }

    public ArrayList<Pronostico> obtenerPronosticos(String participante){
        rs = null;
        ArrayList<Pronostico> pronosticos = new ArrayList<>();

        try {
            rs = stmt.executeQuery("SELECT * FROM pronostico, partido\n" + "WHERE pronostico.id_partido  = partido.id_partido\n" +
                    "AND pronostico.participante = '" +participante+"'\n" + "ORDER BY partido.id_partido ");

            while (rs.next())  {
                pronosticos.add(new Pronostico(Pronostico.obtenerResultado(rs.getInt("gana1"),rs.getInt("gana2"))));
            }
        } catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }

        return pronosticos;
    }

    public void verificarDatosTabla(String tabla,int columnas) throws DatosErroneosException {
        rs = null;
        try {
            rs = stmt.executeQuery("SELECT COUNT(*)\n" + "FROM information_schema.columns\n" + "WHERE table_name = '"+tabla +"'");
            rs.next();
            if (rs.getInt(1)!=columnas)
                throw new DatosErroneosException("");
        } catch(DatosErroneosException d){
            throw new DatosErroneosException("La cantidad de datos ingresados en la base de datos son erroneos \nVerifique las columnas necesarias y vuelva a correr el programa");
        } catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }
    }

    public void cerrarConexiones(){
        if (rs!=null){
            try {
                rs.close();
            }
            catch (Exception e){}
        }
        if (stmt!=null){
            try {
                stmt.close();
            }
            catch (Exception e){}
        }
    }
}