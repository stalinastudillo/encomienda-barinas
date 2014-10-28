package data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.Query;
import org.hibernate.Session;

import application.Main;

@Entity
public class Factura {
	
	private int Codigo;
	private long nroFactura;
	private long controlFiscal;
	private String Fecha;
	private String modoPago;
	private String tipo_envio;	
	private TipoEmbalaje tipo_embalaje;
	private CiudadDestino cuidadDestino;
	private double valorDeclarado; 		
	private int cantidadPieza;
	private double peso;
	private Unidad unidadPeso;
	private double monto;
	private double porcentajeRecargo;
	private double porcentajeSeguro;
	private double contrareembolso;
	private String descripcionDeclarado;
	private double montoRecargo;
	private double montoContrareembolso;
	private double montoSeguro;
	private double montoSubtotal;
	private double montoIVA;
	private double montoTotal;
	private Cliente clienteRemitente;
	private Cliente clienteDestinatario;
	private Cliente clienteEnvia;
	private Oficina oficina;
	private Ipostel montoIpostel;
	private double montoRemesa;	
	private String CNombre;
	private String CApellido;
	private String CTelefono;
	private String CDireccion;
	private String CCorreo;
			
	@Id
	@GeneratedValue
	public int getCodigo() {
		return Codigo;
	}
	public void setCodigo(int codigo) {
		Codigo = codigo;
	}	
		
	public long getNroFactura() {
		return nroFactura;
	}
	public void setNroFactura(long nroFactura) {
		this.nroFactura = nroFactura;
	}
	
	public long getControlFiscal() {
		return controlFiscal;
	}
	public void setControlFiscal(long controlFiscal) {
		this.controlFiscal = controlFiscal;
	}
	
	public String getFecha() {
		return Fecha;
	}
	public void setFecha(String fecha) {
		Fecha = fecha;
	}
	
	public String getModoPago() {
		return modoPago;
	}
	public void setModoPago(String modoPago) {
		this.modoPago = modoPago;
	}
	
	public String getTipo_envio() {
		return tipo_envio;
	}
	public void setTipo_envio(String tipo_envio) {
		this.tipo_envio = tipo_envio;
	}
	
	@ManyToOne
	@JoinColumn(name="CodTipoEmbalaje")	
	public TipoEmbalaje getTipo_embalaje() {
		return tipo_embalaje;
	}
	public void setTipo_embalaje(TipoEmbalaje tipo_embalaje) {
		this.tipo_embalaje = tipo_embalaje;
	}	
		
	@ManyToOne
	@JoinColumn(name="CodCiudadDestino")
	public CiudadDestino getCuidadDestino() {
		return cuidadDestino;
	}
	public void setCuidadDestino(CiudadDestino cuidadDestino) {
		this.cuidadDestino = cuidadDestino;
	}
	
	public double getValorDeclarado() {
		return valorDeclarado;
	}
	public void setValorDeclarado(double valorDeclarado) {
		this.valorDeclarado = valorDeclarado;
	}
	
	public int getCantidadPieza() {
		return cantidadPieza;
	}
	public void setCantidadPieza(int cantidad) {
		this.cantidadPieza = cantidad;
	}
	
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	
	@ManyToOne
	@JoinColumn(name="CodUnidadPeso")
	public Unidad getUnidadPeso() {
		return unidadPeso;
	}
	public void setUnidadPeso(Unidad unidadPeso) {
		this.unidadPeso = unidadPeso;
	}
	
	public double getMonto() {
		return monto;
	}
	public void setMonto(double monto) {
		this.monto = monto;
	}
	
	public double getPorcentajeRecargo() {
		return porcentajeRecargo;
	}
	public void setPorcentajeRecargo(double recargo) {
		this.porcentajeRecargo = recargo;
	}
	
	public double getPorcentajeSeguro() {
		return porcentajeSeguro;
	}
	public void setPorcentajeSeguro(double seguro) {
		this.porcentajeSeguro = seguro;
	}
	
	public double getContrareembolso() {
		return contrareembolso;
	}
	public void setContrareembolso(double contrareembolso) {
		this.contrareembolso = contrareembolso;
	}
	
	public String getDescripcionDeclarado() {
		return descripcionDeclarado;
	}
	public void setDescripcionDeclarado(String descripcionDeclarado) {
		this.descripcionDeclarado = descripcionDeclarado;
	}
	
	public double getMontoRecargo() {
		return montoRecargo;
	}
	public void setMontoRecargo(double montoRecargo) {
		this.montoRecargo = montoRecargo;
	}
	
	public double getMontoContrareembolso() {
		return montoContrareembolso;
	}
	public void setMontoContrareembolso(double montoContrareembolso) {
		this.montoContrareembolso = montoContrareembolso;
	}
	
	public double getMontoSeguro() {
		return montoSeguro;
	}
	public void setMontoSeguro(double montoSeguro) {
		this.montoSeguro = montoSeguro;
	}
	
	public double getMontoSubtotal() {
		return montoSubtotal;
	}
	public void setMontoSubtotal(double montoSubtotal) {
		this.montoSubtotal = montoSubtotal;
	}
	
	public double getMontoIVA() {
		return montoIVA;
	}
	public void setMontoIVA(double montoIVA) {
		this.montoIVA = montoIVA;
	}
	
	public double getMontoTotal() {
		return montoTotal;
	}
	public void setMontoTotal(double montototal) {
		this.montoTotal = montototal;
	}
	
	@ManyToOne
	@JoinColumn(name="CodClienteRemitente")
	public Cliente getClienteRemitente() {
		return clienteRemitente;
	}
	public void setClienteRemitente(Cliente clienteRemitente) {
		this.clienteRemitente = clienteRemitente;
	}
	
	@ManyToOne
	@JoinColumn(name="CodClienteDestinatario")
	public Cliente getClienteDestinatario() {
		return clienteDestinatario;
	}
	public void setClienteDestinatario(Cliente clienteDestinatario) {
		this.clienteDestinatario = clienteDestinatario;
	}
	
	@ManyToOne
	@JoinColumn(name="CodClienteEnvia")
	public Cliente getClienteEnvia() {
		return clienteEnvia;
	}
	public void setClienteEnvia(Cliente clientEnvia) {
		this.clienteEnvia = clientEnvia;
	}
		
	@ManyToOne
	@JoinColumn(name="CodOficina")
	public Oficina getOficina() {
		return oficina;
	}
	public void setOficina(Oficina oficina) {
		this.oficina = oficina;
	}
		
	public String getCNombre() {
		return CNombre;
	}
	public void setCNombre(String cNombre) {
		CNombre = cNombre;
	}
	
	public String getCApellido() {
		return CApellido;
	}
	public void setCApellido(String cApellido) {
		CApellido = cApellido;
	}
	
	public String getCTelefono() {
		return CTelefono;
	}
	public void setCTelefono(String cTelefono) {
		CTelefono = cTelefono;
	}
	
	public String getCDireccion() {
		return CDireccion;
	}
	public void setCDireccion(String cDireccion) {
		CDireccion = cDireccion;
	}
	
	public String getCCorreo() {
		return CCorreo;
	}
	public void setCCorreo(String cCorreo) {
		CCorreo = cCorreo;
	}
	
	@ManyToOne
	@JoinColumn(name="CodIpostel")
	public Ipostel getIpostel() {
		return montoIpostel;
	}	
	public void setIpostel(Ipostel ipostel) {
		montoIpostel = ipostel;
	}
	
	public double getMontoRemesa() {
		return montoRemesa;
	}
	public void setMontoRemesa(double remesa) {
		montoRemesa = remesa;
	}	
	
	private Session openSesion(){		
		Session sesion = Main.sesionFactory.getCurrentSession();
		sesion.beginTransaction();
		
		return sesion;
	}
	
	private void closeSesion(Session sesion){		
		sesion.getTransaction().commit();
	}
	
	public double buscarVariable(String nombreVar){
		
		VariableConfiguracion temporal;
		DetalleVariableConfiguracion dtemporal;
		Query query,queryd;
		Session sesion2 = openSesion();
		
		query = sesion2.createQuery("from VariableConfiguracion where Nombre = :cadena");
		query.setString("cadena",nombreVar);
		query.setMaxResults(1);					
		temporal = (VariableConfiguracion) query.uniqueResult();
		
		System.out.println("variable en bean factura  "+temporal.getCodigo() + " "+temporal.getNombre());
		
		queryd = sesion2.createQuery("from DetalleVariableConfiguracion where CodVariableConfiguracion = :numero order by FechaVigencia DESC");
		queryd.setInteger("numero", temporal.getCodigo());				
		queryd.setMaxResults(1);
		dtemporal = (DetalleVariableConfiguracion) queryd.uniqueResult();
		
		System.out.println("en bean la informacion detallada de variable "+dtemporal.getCodigo() + " " +dtemporal.getFechaVigencia() +  " " + dtemporal.getValor()+ " " +dtemporal.getTipoValor());
		closeSesion(sesion2);
		
		return dtemporal.getValor();		
	}		
	
}
