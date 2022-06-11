package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Logger;

import entidad.Concurso;
import util.MySqlDBConexion;

public class ConcursoModel {

	private static Logger log = Logger.getLogger(ConcursoModel.class.getName());
	
	public int insertaConcurso(Concurso c) {
		int salida = -1;
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = MySqlDBConexion.getConexion();
			String sql = "insert into concurso values(null,?,?,?)";
			pstm = con.prepareStatement(sql);
			pstm.setString(1, c.getNombre());
			pstm.setDouble(2, c.getPremio());
			pstm.setDouble(3, c.getPrecio());
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
	
	
	public ArrayList<Concurso> listaConcursoPorPremio(double desde, double hasta){
		ArrayList<Concurso> lista = new  ArrayList<Concurso>();
		
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			//1 Se realiza la conexión a la bd
			conn = MySqlDBConexion.getConexion();
			
			//2 Se prepara el SQL
			String sql = "select * from concurso where premio between ? and ?";
			pstm = conn.prepareStatement(sql);
			pstm.setDouble(1, desde);
			pstm.setDouble(2, hasta);
			log.info(">> SQL >> " + pstm);
			
			//3 se ejecuta el sql en la BD
			rs = pstm.executeQuery();
			
			//4 se pasa los datos del RS al ArrayList
			Concurso obj = null;
			while(rs.next()) {
				obj = new Concurso();
				obj.setIdConcurso(rs.getInt(1));
				obj.setNombre(rs.getString(2));
				obj.setPremio(rs.getDouble(3));
				obj.setPrecio(rs.getDouble(4));
				lista.add(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (pstm != null) pstm.close();
				if (conn != null) conn.close(); 
			} catch (Exception e2) {}
		}
		
		return lista;
	}
	
	

}
