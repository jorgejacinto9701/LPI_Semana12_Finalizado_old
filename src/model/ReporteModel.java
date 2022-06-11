package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Logger;

import entidad.ReporteBean;
import util.MySqlDBConexion;

public class ReporteModel {

	private static Logger log = Logger.getLogger(ReporteModel.class.getName());

	public ArrayList<ReporteBean> reportePorAnio(int anioDesde, int anioHasta){
		ArrayList<ReporteBean> lista = new  ArrayList<ReporteBean>();
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			conn = MySqlDBConexion.getConexion();
			String sql = "select año, count(*) from campeonato where year(fechaRegistro) between ? and ? group by año";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, anioDesde);
			pstm.setInt(2, anioHasta);
			log.info(">> SQL >> " + pstm);
			rs = pstm.executeQuery();
			ReporteBean obj = null;
			while(rs.next()) {
				obj = new ReporteBean();
				obj.setAnio(rs.getInt(1));
				obj.setCantidad(rs.getInt(2));
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
	
	public ArrayList<ReporteBean> reportePorEstado(int anioDesde, int anioHasta){
		ArrayList<ReporteBean> lista = new  ArrayList<ReporteBean>();
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			conn = MySqlDBConexion.getConexion();
			String sql = "select estado, count(*) from campeonato where year(fechaRegistro) between ? and ? group by estado";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, anioDesde);
			pstm.setInt(2, anioHasta);
			log.info(">> SQL >> " + pstm);
			rs = pstm.executeQuery();
			ReporteBean obj = null;
			while(rs.next()) {
				obj = new ReporteBean();
				obj.setEstado(rs.getInt(1));
				obj.setCantidad(rs.getInt(2));
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
	
	
	public ArrayList<ReporteBean> reportePorAnioEstado(int anioDesde, int anioHasta){
		ArrayList<ReporteBean> lista = new  ArrayList<ReporteBean>();
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			conn = MySqlDBConexion.getConexion();
			String sql = "select año, estado, count(*) from campeonato where year(fechaRegistro) between ? and ?  group by año, estado;";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, anioDesde);
			pstm.setInt(2, anioHasta);
			log.info(">> SQL >> " + pstm);
			rs = pstm.executeQuery();
			ReporteBean obj = null;
			while(rs.next()) {
				obj = new ReporteBean();
				obj.setAnio(rs.getInt(1));
				obj.setEstado(rs.getInt(2));
				obj.setCantidad(rs.getInt(3));
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
