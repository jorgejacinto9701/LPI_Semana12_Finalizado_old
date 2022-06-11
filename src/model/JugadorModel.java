package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import entidad.Jugador;
import util.MySqlDBConexion;

public class JugadorModel {

	private static Logger log = Logger.getLogger(JugadorModel.class.getName());
	
	public int insertaJugador(Jugador obj) {
		int salida = -1;
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			//1 Se crea la conexión
			conn = MySqlDBConexion.getConexion();

			//2 Se prepara el SQL
			String sql = "call sp_inserta_jugador(?,?,?)";
			pstm = conn.prepareCall(sql);
			pstm.setString(1, obj.getNombre());
			pstm.setString(2, obj.getApellido());
			pstm.setDate(3, obj.getFechaNacimiento());

			log.info(">>> " + pstm);
			
			//2Se envía el SQL a la base de datos
			salida = pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstm != null) pstm.close();
				if (conn != null) conn.close();
			} catch (Exception e2) {}
		}
		return salida;
	}
	
	public int actualizaJugador(Jugador c) {
		int actualizados = -1;
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = MySqlDBConexion.getConexion();
			String sql = "call sp_actualiza_jugador(?,?,?,?,?);";
			pstm = con.prepareCall(sql);
			pstm.setString(1, c.getNombre());
			pstm.setString(2, c.getApellido());
			pstm.setDate(3, c.getFechaNacimiento());
			pstm.setInt(4, c.getEstado());
			pstm.setInt(5, c.getIdJugador());
			log.info(">>> " + pstm);
			actualizados = pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstm != null)pstm.close();
				if (con != null)con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return actualizados;
	}
	
	public int eliminaJugador(int idjugador) {
		int eliminados = -1;
		Connection con = null;
		PreparedStatement pstm = null;

		try {
			con = MySqlDBConexion.getConexion();
			String sql = "call sp_elimina_jugador(?)";
			pstm = con.prepareCall(sql);
			pstm.setInt(1, idjugador);
			log.info(">>> " + pstm);
			eliminados = pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstm != null)pstm.close();
				if (con != null)con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return eliminados;
	}
	
	public List<Jugador> listaJugador() {
		ArrayList<Jugador> data = new ArrayList<Jugador>();
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null; //Trae la data de la BD
		try {
			con = MySqlDBConexion.getConexion();
			String sql = "call sp_lista_jugador()";
			pstm = con.prepareCall(sql);
			log.info(">>> " + pstm);
			
			//En rs se trae los datos de la BD segun el SQL
			rs = pstm.executeQuery();
			
			//Se pasa la data del rs al ArrayList(data)
			Jugador c = null;
			while(rs.next()){
				c = new Jugador();
				// Se colocan los campos de la base de datos
				c.setIdJugador(rs.getInt(1));
				c.setNombre(rs.getString(2));
				c.setApellido(rs.getString(3));
				c.setFechaNacimiento(rs.getDate(4));
				c.setEstado(rs.getInt(6));
				data.add(c);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstm != null)pstm.close();
				if (con != null)con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return data;
	}
	
}
