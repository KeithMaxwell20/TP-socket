package py.una.bd;

import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import py.una.entidad.Cama;

public class CamaDAO {
 
	/**
	 * 
	 * @param condiciones 
	 * @return
	 */
	public List<Cama> seleccionar() {
		String query = "SELECT hospital, cama, estado FROM camas ";
		
		List<Cama> lista = new ArrayList<Cama>();
		
		Connection conn = null; 
        try 
        {
        	conn = Bd.connect();
        	ResultSet rs = conn.createStatement().executeQuery(query);

        	while(rs.next()) {
        		Cama p = new Cama();
        		p.setHospital(rs.getString(1));
        		p.setCama(rs.getString(2));
        		p.setEstado(rs.getString(3));
        		
        		lista.add(p);
        	}
        	
        } catch (SQLException ex) {
            System.out.println("Error en la seleccion: " + ex.getMessage());
        }
        finally  {
        	try{
        		conn.close();
        	}catch(Exception ef){
        		System.out.println("No se pudo cerrar la conexion a BD: "+ ef.getMessage());
        	}
        }
		return lista;

	}
	
	public List<Cama> seleccionarPorHospital(String hospital) {
		String SQL = "SELECT hospital, cama, estado FROM camas WHERE hospital = ? ";
		
		List<Cama> lista = new ArrayList<Cama>();
		
		Connection conn = null; 
        try 
        {
        	conn = Bd.connect();
        	PreparedStatement pstmt = conn.prepareStatement(SQL);
        	
        	ResultSet rs = pstmt.executeQuery();

        	while(rs.next()) {
        		Cama p = new Cama();
        		p.setHospital(rs.getString(1));
        		p.setCama(rs.getString(2));
        		p.setEstado(rs.getString(3));
        		
        		lista.add(p);
        	}
        	
        } catch (SQLException ex) {
            System.out.println("Error en la seleccion: " + ex.getMessage());
        }
        finally  {
        	try{
        		conn.close();
        	}catch(Exception ef){
        		System.out.println("No se pudo cerrar la conexion a BD: "+ ef.getMessage());
        	}
        }
		return lista;

	}
	
    public int insertar(Cama p) throws SQLException {

        
        
        
        String SQL = "INSERT INTO camas(hospital, cama, estado) "
                + "VALUES(?,?,?)";
 
        int resul = 0;
        Connection conn = null;
        
        try 
        {
        	conn = Bd.connect();
        	PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, p.getHospital());
            pstmt.setString(2, p.getCama());
            pstmt.setString(3, p.getEstado());
 
            int affectedRows = pstmt.executeUpdate();
            // check the affected rows 
            if (affectedRows > 0) {
                // get the ID back
                System.out.println("Se insertaron con exito "+affectedRows+" campos");
            }
        } catch (SQLException ex) {
            System.out.println("Error en la insercion: " + ex.getMessage());
            resul=0;
        }
        finally  {
        	try{
        		conn.close();
        	}catch(Exception ef){
        		System.out.println("No se pudo cerrar la conexion a BD: "+ ef.getMessage());
                        resul=-1;
        	}
        }
        	
        return resul;
    	
    	
    }
	

    public long actualizarEstado(Cama p) throws SQLException {

        String SQL = "UPDATE camas SET estado = ?  WHERE hospital = ? AND cama = ?";
 
        int resul = 0;
        Connection conn = null;
        
        try 
        {
        	conn = Bd.connect();
        	PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, p.getEstado());
            pstmt.setString(2, p.getHospital());
            pstmt.setString(3, p.getCama());
 
            int affectedRows = pstmt.executeUpdate();
            // check the affected rows 
            if (affectedRows > 0) {
                // get the ID back
                 System.out.println("Se actualizaron con exito "+affectedRows+" campos");
                 resul=1;
            }
        } catch (SQLException ex) {
            System.out.println("Error en la actualizacion: " + ex.getMessage());
            resul=0;
        }
        finally  {
        	try{
        		conn.close();
        	}catch(Exception ef){
        		System.out.println("No se pudo cerrar la conexion a BD: "+ ef.getMessage());
                        resul = -1;
        	}
        }
        return resul;
    }
    
    public int borrar(String hospital, String cama) throws SQLException {

        String SQL = "DELETE FROM camas WHERE hospital = ? AND cama = ?";
 
   
        Connection conn = null;
        
        try 
        {
        	conn = Bd.connect();
        	PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, hospital);
             pstmt.setString(2, cama);
 
            int affectedRows = pstmt.executeUpdate();
            // check the affected rows 
            if (affectedRows > 0) {
                
                System.out.println("Se borraron con exito "+affectedRows+" campos");
            }
        } catch (SQLException ex) {
            System.out.println("Error en la eliminación: " + ex.getMessage());
            
        }
        finally  {
        	try{
        		conn.close();
        	}catch(Exception ef){
        		System.out.println("No se pudo cerrar la conexion a BD: "+ ef.getMessage());
                        return 0;
        	}
        }
        return 1;
    }
    

}
