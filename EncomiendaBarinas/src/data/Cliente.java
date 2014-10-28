package data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Cliente {
	
	private int Codigo;	
	private int RifCedula;
	private String TipoRifCedula;
	private String Nombre;
	private String Apellido;
	private String Telefono;
	private String Direccion;
	private String Correo;
	private Oficina	Oficina;
	private Tarifa Tarifa;
		
	@Id
	@GeneratedValue
	public int getCodigo() {
		return Codigo;
	}
	public void setCodigo(int codigo) {
		Codigo = codigo;
	}	
	
	public int getRifCedula() {
		return RifCedula;
	}
	public void setRifCedula(int rifCedula) {
		RifCedula = rifCedula;
	}
	
	public String getTipoRifCedula() {
		return TipoRifCedula;
	}
	public void setTipoRifCedula(String tipoRifCedula) {
		TipoRifCedula = tipoRifCedula;
	}
	
	public String getNombre() {
		return Nombre;
	}	
	public void setNombre(String nombre) {
		Nombre = nombre;
	}	
	
	public String getApellido() {
		return Apellido;
	}
	public void setApellido(String apellido) {
		Apellido = apellido;
	}
	
	public String getTelefono() {
		return Telefono;
	}
	public void setTelefono(String telefono) {
		Telefono = telefono;
	}
	
	public String getDireccion() {
		return Direccion;
	}
	public void setDireccion(String direccion) {
		Direccion = direccion;
	}
	
	public String getCorreo() {
		return Correo;
	}
	public void setCorreo(String correo) {
		Correo = correo;
	}
	
	@ManyToOne
	@JoinColumn(name="CodOficina")
	public Oficina getOficina() {
		return Oficina;
	}
	public void setOficina(Oficina oficina) {
		Oficina = oficina;
	}
	
	@ManyToOne
	@JoinColumn(name="CodTarifa")
	public Tarifa getTarifa() {
		return Tarifa;
	}
	public void setTarifa(Tarifa tarifa) {
		this.Tarifa = tarifa;
	}	
	
}
