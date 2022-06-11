package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.logging.Logger;

import entidad.Empresa;
import util.MySqlDBConexion;

public class EmpresaModel {

	private static Logger log = Logger.getLogger(EmpresaModel.class.getName());
	
	public int insertaEmpresa(Empresa obj) {
		log.info(">> Inicio >> insertaEmpresa() ");
		int salida = -1;
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			//1 Crear la conexión
			conn = MySqlDBConexion.getConexion();
			
			//2 Se prepara el SQL
			String sql = "insert into empresa values(null,?,?,?,?, curtime(), 1)";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, obj.getNombre());
			pstm.setString(2, obj.getRuc());
			pstm.setDate(3, obj.getFechaCreacion());
			pstm.setString(4, obj.getSede());
			
			log.info(">> SQL >> " + pstm);
			
			//Se envía el SQL a la base de dastos champion del MYSQL
			//Se obtiene la cantidad de registros insertados
			salida = pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if (pstm != null) pstm.close();
				if (conn != null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		log.info(">> Fin >> insertaEmpresa() ");
		return salida;
	}
	
}
