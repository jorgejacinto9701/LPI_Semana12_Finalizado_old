package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Logger;

import entidad.Equipo;
import util.MySqlDBConexion;

public class EquipoModel {

	private static Logger log = Logger.getLogger(EquipoModel.class.getName());
	
	public ArrayList<Equipo> listaEquipoPorNombre(String filtro){
		ArrayList<Equipo> lista = new  ArrayList<Equipo>();
		
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			//1 Se realiza la conexión a la bd
			conn = MySqlDBConexion.getConexion();
			
			//2 Se prepara el SQL
			String sql = "select * from equipo where nombre like ?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, filtro+"%");
			log.info(">> SQL >> " + pstm);
			
			//3 se ejecuta el sql en la BD
			rs = pstm.executeQuery();
			
			//4 se pasa los datos del RS al ArrayList
			Equipo obj = null;
			while(rs.next()) {
				obj = new Equipo();
				obj.setIdEquipo(rs.getInt(1));
				obj.setNombre(rs.getString(2));
				obj.setPais(rs.getString(3));
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





