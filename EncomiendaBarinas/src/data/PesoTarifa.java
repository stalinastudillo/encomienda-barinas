package data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class PesoTarifa {
	
	private int Codigo;
	private double Desde;
	private double Hasta;
	private Unidad Unidad;
		
	
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
	
	@Transient
	public String toString(String h){
		return ""+Unidad.getDescripcion();
	}
}
