package data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Municipio {
	private int Codigo;
	private String Descripcion;
	private Estado estado;
	
	@Id
	@GeneratedValue
	public int getCodigo() {
		return Codigo;
	}
	public void setCodigo(int codigo) {
		Codigo = codigo;
	}
	
	public String getDescripcion() {
		return Descripcion;
	}
	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}
	
	@ManyToOne
	@JoinColumn(name="CodEstado")
	public Estado getEstado() {
		return estado;
	}	
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
}
