package entidad;

public class ReporteBean {

	private int anio;
	private int cantidad;
	private int estado;
	private String formatoEstado;
	
	public String getFormatoEstado() {
		if (estado ==0) {
			formatoEstado= "Inactivo";
		}else {
			formatoEstado= "Activo";
		}
		return formatoEstado;
	}
	public void setFormatoEstado(String formatoEstado) {
		this.formatoEstado = formatoEstado;
	}
	public int getAnio() {
		return anio;
	}
	public void setAnio(int anio) {
		this.anio = anio;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	

	
}
