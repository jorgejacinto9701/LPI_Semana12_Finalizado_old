package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import entidad.Docente;
import util.MySqlDBConexion;

public class DocenteModel {

	private static Logger log = Logger.getLogger(DocenteModel.class.getName());
	
	public int insertaDocente(Docente c) {
		int salida = -1;
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = MySqlDBConexion.getConexion();
			String sql = "insert into docente values(null,?,?,?)";
			pstm = con.prepareCall(sql);
			pstm.setString(1, c.getNombre());
			pstm.setString(2, c.getDni());
			pstm.setString(3, c.getFechaNacimiento());
			log.info(">> SQL >> " + pstm);
			salida = pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstm != null)
					pstm.close();
				if (con != null)
					con.close();
			} catch (Exception e2) {
			}
		}
		return salida;
	}
	
	
	public List<Docente> consultaPorFecha(String desde, String hasta){
		ArrayList<Docente> data = new ArrayList<Docente>();
		Connection conn = null;
		PreparedStatement  pstm = null;
		ResultSet rs = null;
		try {
			//1 Se crea la conexión
			conn = 	MySqlDBConexion.getConexion();
			
			//2 Se prepara el SQL
			String sql = "select * from docente where fechaNacimiento between ? and ?"; 
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, desde);
			pstm.setString(2, hasta);
			System.out.println("SQL --> " + pstm);
			
			//3 Se obtiene la data de la BD y es colocada en rs(ResultSet)
			rs = pstm.executeQuery();
	
			//4 Del ResultSet se pasa al ArrayList
			Docente obj = null;
			while(rs.next()){
				obj = new Docente();
				obj.setIdDocente(rs.getInt("iddocente"));//Columnas de la tabla
				obj.setNombre(rs.getString("nombre"));
				obj.setFechaNacimiento(rs.getString("fechaNacimiento"));
				obj.setDni(rs.getString("dni"));
				data.add(obj);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if (rs != null)     rs.close();
				if (pstm != null) pstm.close();
				if (conn != null) conn.close();
			} catch (Exception e2) {}
		}
		return data;
	}
	
	
	public List<Docente> consultaPorNombreDNIFecha(String nombre, String dni,String desde, String hasta){
		ArrayList<Docente> data = new ArrayList<Docente>();
		Connection conn = null;
		PreparedStatement  pstm = null;
		ResultSet rs = null;
		try {
			//1 Se crea la conexión
			conn = 	MySqlDBConexion.getConexion();
			
			//2 Se prepara el SQL
			String sql = "SELECT * FROM docente where "
					+ "(nombre like ?) and "
					+ "(? ='' or dni = ?) and "
					+ "(? ='' or ?='' or fechaNacimiento between ? and ?);"; 
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, "%"+nombre+"%");
			pstm.setString(2, dni);
			pstm.setString(3, dni);
			pstm.setString(4, desde);
			pstm.setString(5, hasta);
			pstm.setString(6, desde);
			pstm.setString(7, hasta);
			
			System.out.println("SQL --> " + pstm);
			
			//3 Se obtiene la data de la BD y es colocada en rs(ResultSet)
			rs = pstm.executeQuery();
	
			//4 Del ResultSet se pasa al ArrayList
			Docente obj = null;
			while(rs.next()){
				obj = new Docente();
				obj.setIdDocente(rs.getInt("iddocente"));//Columnas de la tabla
				obj.setNombre(rs.getString("nombre"));
				obj.setFechaNacimiento(rs.getString("fechaNacimiento"));
				obj.setDni(rs.getString("dni"));
				data.add(obj);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if (rs != null)     rs.close();
				if (pstm != null) pstm.close();
				if (conn != null) conn.close();
			} catch (Exception e2) {}
		}
		return data;
	}
	
	
	
}
