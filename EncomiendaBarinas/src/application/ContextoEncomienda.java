package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import data.CiudadDestino;
import data.Cliente;
import data.FormaPago;
import data.Ipostel;
import data.TipoEmbalaje;
import data.ValorDeclarado;

public class ContextoEncomienda {

	 private final static ContextoEncomienda instancia = new ContextoEncomienda();
	
	 public static ContextoEncomienda getInstance() {
	        return instancia;
	 }
	 
	 // - - - - - - - - - CONTROLADOR DE INICIO - -  - - - - - - - - - - - - - //
	 
	 private String identificadorVentana = new String(); 
	 
	 // - - - - - - - - - CONTROLADOR DE ENCOMIENDA - - - - - - - - - - - - - //
	 private boolean banderaRefresh = false;
	 private boolean banderaNuevaFactura;
	 private Cliente remitenteArriba = new Cliente();
	 private Cliente remitenteAbajo = new Cliente();
	 private String remitArriba = new String();
	 private String remitAbajo = new String();	 
	 private Cliente destinatario = new Cliente();
	 private boolean nuevoDestinatario = true;
	 private String modoPago = new String();
	 private ValorDeclarado valorDeclarado = new ValorDeclarado();
	 private int cuidadDestino;
	 private int tipoEmbalaje;
	 private String totalFactura;
	 private ObservableList<FormaPago> itemsFormaPago = FXCollections.observableArrayList();
	 private String facturaPeso = new String();
	 private String facturaMonto = new String();
	 private String facturaRecargo = new String();
	 private String facturaSeguro = new String();
	 private String facturaContrareembolso = new String();
	 private String facturaDescripcionDeclarado = new String();
	 private String facturaTotalRecargo = new String();
	 private String facturaTotalContrareembolso = new String();
	 private String facturaTotalIpostel = new String();
	 private String facturaTotalSeguro = new String();
	 private String facturaTotalSubTotal = new String();
	 private String facturaTotalIVA = new String();
	 private Ipostel ipostel = new Ipostel();

	 public String getIdentificadorVentana() {
		return identificadorVentana;
	 }
	 public void setIdentificadorVentana(String identificadorVentana) {
		this.identificadorVentana = identificadorVentana;
	 } 
	 
	 public boolean getBanderaNuevaFactura() {
			return banderaNuevaFactura;
	 }
	 public void setBanderaNuevaFactura(boolean banderaNuevaFactura) {
		this.banderaNuevaFactura = banderaNuevaFactura;
	 }	
	 
	 public String getRemitArriba() {
		return remitArriba;
	 }
	 public void setRemitArriba(String remitArriba) {
		this.remitArriba = remitArriba;
	 }
	 
	 public String getRemitAbajo() {
		return remitAbajo;
	 }
	 public void setRemitAbajo(String remitAbajo) {
		this.remitAbajo = remitAbajo;
	 } 	 
	 
	 public Cliente getRemitenteArriba() {
		return remitenteArriba;
	}
	public void setRemitenteArriba(Cliente remitente) {
		this.remitenteArriba = remitente;
	}
	
	public Cliente getRemitenteAbajo() {
		return remitenteAbajo;
	}
	public void setRemitenteAbajo(Cliente remitente) {
		this.remitenteAbajo = remitente;
	}
	
	public Cliente getDestinatario() {
		return destinatario;
	}
	public void setDestinatario(Cliente destinatario) {
		this.destinatario = destinatario;
	}

	public boolean getNuevoDestinatario() {
		return nuevoDestinatario;
	}
	public void setNuevoDestinatario(boolean nuevoDestinatario) {
		this.nuevoDestinatario = nuevoDestinatario;
	}

	public String getModoPago() {
		return modoPago;
	}
	public void setModoPago(String modoPago) {
		this.modoPago = modoPago;
	}
	
	public ValorDeclarado getValorDeclarado() {
		return valorDeclarado;
	}	
	public void setValorDeclarado(ValorDeclarado valorDeclarado) {
		this.valorDeclarado = valorDeclarado;
	}
	
	public int getCuidadDestino() {
		return cuidadDestino;
	}
	public void setCuidadDestino(int cuidadDestino) {
		this.cuidadDestino = cuidadDestino;
	}
	
	public int getTipoEmbalaje() {
		return tipoEmbalaje;
	}
	public void setTipoEmbalaje(int tipoEmbalaje) {
		this.tipoEmbalaje = tipoEmbalaje;
	}
	
	public String getTotalFactura() {
		return totalFactura;
	}
	public void setTotalFactura(String totalFactura) {
		this.totalFactura = totalFactura;
	}
	
	public ObservableList<FormaPago> getItemsFormaPago() {
		return itemsFormaPago;
	}
	public void setItemsFormaPago(ObservableList<FormaPago> itemsFormaPago) {
		this.itemsFormaPago = itemsFormaPago;
	}
	
	public String getFacturaPeso() {
		return facturaPeso;
	}
	public void setFacturaPeso(String facturaPeso) {
		this.facturaPeso = facturaPeso;
	}
	
	public String getFacturaMonto() {
		return facturaMonto;
	}
	public void setFacturaMonto(String facturaMonto) {
		this.facturaMonto = facturaMonto;
	}
	
	public String getFacturaRecargo() {
		return facturaRecargo;
	}
	public void setFacturaRecargo(String facturaRecargo) {
		this.facturaRecargo = facturaRecargo;
	}
	
	public String getFacturaSeguro() {
		return facturaSeguro;
	}
	public void setFacturaSeguro(String facturaSeguro) {
		this.facturaSeguro = facturaSeguro;
	}
	
	public String getFacturaContrareembolso() {
		return facturaContrareembolso;
	}
	public void setFacturaContrareembolso(String facturaContrareembolso) {
		this.facturaContrareembolso = facturaContrareembolso;
	}
	
	public String getFacturaDescripcionDeclarado() {
		return facturaDescripcionDeclarado;
	}
	public void setFacturaDescripcionDeclarado(String facturaDescripcionDeclarado) {
		this.facturaDescripcionDeclarado = facturaDescripcionDeclarado;
	}
	
	public String getFacturaTotalRecargo() {
		return facturaTotalRecargo;
	}
	public void setFacturaTotalRecargo(String facturaTotalRecargo) {
		this.facturaTotalRecargo = facturaTotalRecargo;
	}
	
	public String getFacturaTotalContrareembolso() {
		return facturaTotalContrareembolso;
	}
	public void setFacturaTotalContrareembolso(String facturaTotalContrareembolso) {
		this.facturaTotalContrareembolso = facturaTotalContrareembolso;
	}
	
	public String getFacturaTotalIpostel() {
		return facturaTotalIpostel;
	}
	public void setFacturaTotalIpostel(String facturaTotalIpostel) {
		this.facturaTotalIpostel = facturaTotalIpostel;
	}
	
	public String getFacturaTotalSeguro() {
		return facturaTotalSeguro;
	}
	public void setFacturaTotalSeguro(String facturaTotalSeguro) {
		this.facturaTotalSeguro = facturaTotalSeguro;
	}
	
	public String getFacturaTotalSubTotal() {
		return facturaTotalSubTotal;
	}
	public void setFacturaTotalSubTotal(String facturaTotalSubTotal) {
		this.facturaTotalSubTotal = facturaTotalSubTotal;
	}
	
	public String getFacturaTotalIVA() {
		return facturaTotalIVA;
	}
	public void setFacturaTotalIVA(String facturaTotalIVA) {
		this.facturaTotalIVA = facturaTotalIVA;
	}
	
	public boolean getBanderaRefresh() {
		return banderaRefresh;
	}
	public void setBanderaRefresh(boolean banderaRefresh) {
		this.banderaRefresh = banderaRefresh;
	}
	
	public Ipostel getIpostel() {
		return ipostel;
	}
	public void setIpostel(Ipostel ipostel) {
		this.ipostel = ipostel;
	}	
}
