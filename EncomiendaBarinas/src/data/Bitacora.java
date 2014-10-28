package data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;

@Entity
public class Bitacora {

	private int Codigo;
	//private Usuario Usuario;
	private String Ingreso; //fecha y hora
	private String Movimiento;
	
	@Id
	@GeneratedValue
	public int getCodigo() {
		return Codigo;
	}
	public void setCodigo(int codigo) {
		Codigo = codigo;
	}
	
	/*
	@ManyToOne
	@JoinColumn(name="CodUsuario")
	public Usuario getUsuario() {
		return Usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.Usuario = usuario;
	}
	*/
	
	public String getIngreso() {
		return Ingreso;
	}
	public void setIngreso(String ingreso) {
		this.Ingreso = ingreso;
	}
	
	public String getMovimiento() {
		return Movimiento;
	}
	public void setMovimiento(String movimiento) {
		Movimiento = movimiento;
	}		
}
