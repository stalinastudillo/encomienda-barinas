package data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Ipostel {

	private int Codigo;
	private double Desde;
	private double Hasta;
	private double Valor;
	private Unidad Unidad;
	
	public Ipostel(double desde, double hasta, double valor, Unidad unidad){
		this.Desde = desde;
		this.Hasta = hasta;
		this.Valor = valor;
		this.Unidad = unidad;
	}
	public Ipostel(){		
	}
	
	@Id
	@GeneratedValue
	public int getCodigo() {
		return Codigo;
	}
	public void setCodigo(int codigo) {
		Codigo = codigo;
	}
	
	public double getDesde() {
		return Desde;
	}
	public void setDesde(double desde) {
		Desde = desde;
	}
	
	public double getHasta() {
		return Hasta;
	}
	public void setHasta(double hasta) {
		Hasta = hasta;
	}		
	
	public double getValor() {
		return Valor;
	}
	public void setValor(double valor) {
		Valor = valor;
	}
	
	@ManyToOne
	@JoinColumn(name="CodUnidad")
	public Unidad getUndMedida() {
		return Unidad;
	}
	public void setUndMedida(Unidad undMedida) {
		Unidad = undMedida;
	}
	
	@Transient
	public String toString(){
		return ""+this.Desde+" - "+this.Hasta;
	}
}
