package data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Oficina {
	private int Codigo;
	private String Descripcion;
	private Character SerieFiscal;
	private int Activa;
	
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
	
	public Character getSerieFiscal() {
		return SerieFiscal;
	}
	public void setSerieFiscal(Character serieFiscal) {
		SerieFiscal = serieFiscal;
	}
	
	public void setActiva(int activa) {
		Activa = activa;
	}
	public int getActiva() {
		return Activa;
	}	
		
}
