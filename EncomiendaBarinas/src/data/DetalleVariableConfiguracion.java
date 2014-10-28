package data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class DetalleVariableConfiguracion {
	
	private int Codigo;   
	private double Valor;         
	private String FechaVigencia;     
	private String TipoValor;     
	private VariableConfiguracion objVariable;
	
	@Id
	@GeneratedValue
	public int getCodigo() {
		return Codigo;
	}
	public void setCodigo(int codigo) {
		Codigo = codigo;
	}
	
	public double getValor() {
		return Valor;
	}
	public void setValor(double valor) {
		Valor = valor;
	}
	
	public String getFechaVigencia() {
		return FechaVigencia;
	}
	public void setFechaVigencia(String fechaVigencia) {
		FechaVigencia = fechaVigencia;
	}
	
	public String getTipoValor() {
		return TipoValor;
	}
	public void setTipoValor(String tipoValor) {
		TipoValor = tipoValor;
	}	
	
	@ManyToOne
	@JoinColumn(name="CodVariableConfiguracion")	
	public VariableConfiguracion getObjVariable() {
		return objVariable;
	}
	public void setObjVariable(VariableConfiguracion objVariable) {
		this.objVariable = objVariable;
	}
	
	
}
