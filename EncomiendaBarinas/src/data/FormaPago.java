package data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class FormaPago {
	
	private int Codigo;
	private String tipoPago;
	private String monto;
	private String signo;
	private String banco;
	private String tipoTarjeta;
	private String nroRef;
	private Factura factura;
	
	public FormaPago(String tipoPago, String monto, String signo, String banco, String tipoTarjeta, String nroRef){		
		this.tipoPago = tipoPago;
		this.monto = monto;
		this.signo = signo;
		this.banco = banco;
		this.tipoTarjeta = tipoTarjeta;
		this.nroRef = nroRef;
	}

	public FormaPago(){
		
	}
	
	@Id
	@GeneratedValue
	public int getCodigo() {
		return Codigo;
	}
	public void setCodigo(int codigo) {
		Codigo = codigo;
	}
	
	public String getTipoPago() {
		return tipoPago;
	}
	public void setTipoPago(String tipopago) {
		tipoPago = tipopago;
	}
	
	public String getMonto() {
		return monto;
	}
	public void setMonto(String monto) {
		this.monto = monto;
	}	
	
	public String getSigno() {
		return signo;
	}
	public void setSigno(String signo) {
		this.signo = signo;
	}
	
	public String getBanco() {
		return banco;
	}
	public void setBanco(String banco) {
		this.banco = banco;
	}
	
	public String getTipoTarjeta() {
		return tipoTarjeta;
	}
	public void setTipoTarjeta(String tipoTarjeta) {
		this.tipoTarjeta = tipoTarjeta;
	}
	
	public String getNroRef() {
		return nroRef;
	}
	public void setNroRef(String nroRef) {
		this.nroRef = nroRef;
	}
	
	@ManyToOne
	@JoinColumn(name="CodFactura")	
	public Factura getFactura() {
		return factura;
	}
	public void setFactura(Factura fact) {
		this.factura = fact;
	}
}
