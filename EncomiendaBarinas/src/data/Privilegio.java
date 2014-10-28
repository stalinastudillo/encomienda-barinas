package data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Privilegio {

	private int Codigo;
	private String Nombre;
	private String Descripcion;
	
	@Id
	@GeneratedValue
	public int getCodigo() {
		return Codigo;
	}
	public void setCodigo(int id) {
		Codigo = id;
	}	
	
	public String getNombre() {
		return Nombre;
	}
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	
	public String getDescripcion() {
		return Descripcion;
	}
	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}

}
