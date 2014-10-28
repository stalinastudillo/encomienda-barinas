package data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class PrecioTarifa {

	private int Codigo;
	private double Monto;
	private Tarifa tarifa;
	private PesoTarifa pesoTarifa;
	
	public PrecioTarifa(double monto, Tarifa tarifa, PesoTarifa pesot){
		this.Monto = monto;
		this.tarifa = tarifa;
		this.pesoTarifa = pesot;
	}
	
	public PrecioTarifa(){		
	}
	
	@Id
	@GeneratedValue
	public int getCodigo() {
		return Codigo;
	}
	public void setCodigo(int codigo) {
		Codigo = codigo;
	}
	
	public double getMonto() {
		return Monto;
	}
	public void setMonto(double monto) {
		Monto = monto;
	}
	
	@ManyToOne
	@JoinColumn(name="CodTarifa")
	public Tarifa getTarifa() {
		return tarifa;
	}
	public void setTarifa(Tarifa tarifa) {
		this.tarifa = tarifa;
	}
	
	@ManyToOne
	@JoinColumn(name="CodPesoTarifa")
	public PesoTarifa getPesoTarifa() {
		return pesoTarifa;
	}
	public void setPesoTarifa(PesoTarifa pesoTarifa) {
		this.pesoTarifa = pesoTarifa;
	}	
}
