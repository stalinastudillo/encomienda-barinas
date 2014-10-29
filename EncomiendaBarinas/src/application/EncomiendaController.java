package application;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ListIterator;
import java.util.ResourceBundle;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.SQLGrammarException;

import data.CiudadDestino;
import data.Cliente;
import data.DetalleVariableConfiguracion;
import data.Factura;
//import data.IVARemesa;
import data.FormaPago;
import data.Ipostel;
import data.Oficina;
import data.PesoTarifa;
import data.PrecioTarifa;
import data.Temporal;
import data.TipoEmbalaje;
import data.Unidad;
import data.VariableConfiguracion;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class EncomiendaController{	
	
	@FXML
	private Label lFecha;
	
	@FXML
	private Label lValorFecha;
	
	@FXML
	private TextField tfBuscador;
	
	@FXML
	private ChoiceBox cbEnvio;
		
	@FXML
	private ChoiceBox cbTipoEmbalaje; 
	
	@FXML
	private ChoiceBox cbCiudadDestino;
		
	@FXML
	private ChoiceBox cbNroPieza;
	
	@FXML
	private ChoiceBox cbRIFCedula;
		
	@FXML
	private ChoiceBox cbUnidadPeso; 
	
	@FXML
	private TextField tfPeso;
	
	@FXML
	private Label lResulMonto;
	
	@FXML
	private TextField tfRecargo;
	
	@FXML
	private Label lValorContrareembolso;
	
	@FXML
	private TextField tfSeguro;
	
	@FXML
	public TextField tfDescripDeclarado;
	
	@FXML
	private Label lTotalRecargo;
			
	@FXML
	private Label lTotalContrareembolso;
	
	@FXML
	private Label lTotalSeguro;
	
	@FXML
	private Label lSubTotal;
	
	@FXML
	private Label lTotalIVA;
	
	@FXML
	private Label lTotalTotal;
	
	@FXML
	private Label lTotalipostel;
		
	@FXML
	private Label lNombreRemitente;
	
	@FXML
	private Label lNombreDestinatario;
	
	@FXML
	private Label lFacturaCodigo;
	
	@FXML
	private Label lAlertaMsj;
		
	@FXML
	private Label lResulValorDeclar;
	
	@FXML
	private Label lTotalValorDeclar;
	
	@FXML
	private Button bPrimero;
	
	@FXML
	private Button bAnterior;
	
	@FXML
	private Button bSiguiente;
	
	@FXML
	private Button bUltimo;
	
	@FXML
	private Button bNuevaFactura;
	
	@FXML
	private Button bEliminarFactura;	
	
	@FXML
	private Button botonRemitente;
	
	@FXML
	private Button botonDestinatario;	
		
	@FXML
	private Button bCalcularMonto;
	
	@FXML
	private Button bCancelar;	
	
	@FXML
	private Button bPago;
	
	@FXML
	private Button bValorDeclarado;
		
	@FXML
	private RadioButton rbCancelado;
	
	@FXML
	private RadioButton	rbFletedestino;
	
	@FXML
	private RadioButton rbValorDeclarado;	
			
	@FXML
	VentanaRemitente ventanaRemitente;	
	
	@FXML
	VentanaDestinatario ventanaDestinatario;

	@FXML
	public Label lTituloFactura1;
	
	@FXML
	public Label lTituloFactura2;
		
	private ObservableList<TipoEmbalaje> TipoEmbalajeArray = FXCollections.observableArrayList();  //Carga datos de Tabla TipoEmbalaje 
    private ObservableList<String> opcionTipoEmbalaje = FXCollections.observableArrayList();
			
	private ObservableList<CiudadDestino> CiudadDestinoArray = FXCollections.observableArrayList();  // Carga datos de Ciudad Destino 
	private ObservableList<String> opcionCiudadDestino = FXCollections.observableArrayList();
		
	private ObservableList<Unidad> UnidadPesoList = FXCollections.observableArrayList();  // Carga datos de Ciudad Destino 
	private ObservableList<String> opcionUnidadPeso = FXCollections.observableArrayList();
			
	private ObservableList<Oficina> OficinaList = FXCollections.observableArrayList();
	
	private ObservableList<PesoTarifa> PesoTarifaList = FXCollections.observableArrayList();
	private ObservableList<PrecioTarifa> PrecioTarifaList = FXCollections.observableArrayList();
	
	private ObservableList<Ipostel> IpostelList = FXCollections.observableArrayList();
	static private ObservableList<FormaPago> itemsPago = FXCollections.observableArrayList();
	int r;	
	
	Query queryResultModoPago;	
	Query queryResultTipoEmbalaje;	
	Query queryResultCiudadDestino;	
	Query queryResultCliente;
	Query queryResultFactura, queryResultFactura2;
	Query queryResultOficina;
	Query queryResultUnidadPeso;
	Query queryResultPesoTarifa;
	Query queryResultPrecioTarifa;	
	Query queryResultIVA;
	Query queryResultIVADetalle;
	Query queryResultRemesa;
	Query queryResultRemesaDetalle;
	Query queryResultPorcVD;
	Query queryResultPorcVDDetalle;
	Query queryResultIpostel;
	
	ObservableList<Factura> facturaArray;	
	
	boolean bandValidComponentes=false, primeraejecucion = false; 
	public static boolean bandRemitente = false, bandDestinatario = false, bandGuardarImprimir=false;	
	static boolean nuevoRemitenteDown = false, nuevoRemitenteUp = false, nuevoRegDestinatario = false, existeRegDestinatario = false;
	
	ListIterator listite;
	
	String val;
	BigDecimal big;
	
	static Cliente RemitenteUp = new Cliente(), RemitenteDown = new Cliente();
	static Cliente Destinatario = new Cliente();
		
	Factura objFactura = new Factura();
	Factura objf,objant;	
	
	VentanaFormaPago ventPago;
	VentanaValorDeclarado ventanaValorDeclarado;
			
	Factura objFP = new Factura(); 
	
	private PruebaVentanas ProgramaPrincipal = new PruebaVentanas();
	
	@FXML
	private void initialize(){	
		
			tfPeso.focusedProperty().addListener(new ChangeListener<Boolean>(){
			    @Override
			    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue){
			        
			    	if (!newPropertyValue) {
			    		lAlertaMsj.setVisible(false);
			        	if (tfPeso.getText().compareTo("") != 0){
			        	
			        			if ((ContextoEncomienda.getInstance().getRemitenteArriba().getNombre() != null)
			        					&& (ContextoEncomienda.getInstance().getDestinatario().getNombre() != null)
			        					&& cbUnidadPeso.getSelectionModel().getSelectedIndex()!=-1 ){
			        				int idUnidad=0,idPeso=0,idTarifa=0,idIpostel=0;
			        		
					        		if (ContextoEncomienda.getInstance().getModoPago().equals("cancelado")){ 
							        	if ((ContextoEncomienda.getInstance().getRemitAbajo().compareTo("nuevo")==0)
							        	   || (ContextoEncomienda.getInstance().getRemitAbajo().compareTo("consulta")==0)){
							        		idTarifa=ContextoEncomienda.getInstance().getRemitenteAbajo().getTarifa().getCodigo();					        		
							        	}else if ((ContextoEncomienda.getInstance().getRemitAbajo().compareTo("copia")==0)){
							        		idTarifa=ContextoEncomienda.getInstance().getRemitenteArriba().getTarifa().getCodigo();
							        	}
						        	}else if (ContextoEncomienda.getInstance().getModoPago().equals("fletedestino")){
						        		
						        		idTarifa=ContextoEncomienda.getInstance().getDestinatario().getTarifa().getCodigo();
						        	}
				        		
					        		for (int r=0;r<UnidadPesoList.size();r++){
					        			if (r==cbUnidadPeso.getSelectionModel().getSelectedIndex()){
					        				idUnidad = UnidadPesoList.get(r).getCodigo();
					        				break;
					        			}
					        		}			        		
					        		for (int r=0;r<PesoTarifaList.size();r++){
					        			if (PesoTarifaList.get(r).getUndMedida().getCodigo()==idUnidad){
					        				if ( (Double.parseDouble(tfPeso.getText()) >= PesoTarifaList.get(r).getDesde()) 
					        					&& (Double.parseDouble(tfPeso.getText()) <= PesoTarifaList.get(r).getHasta()) ){
					        					idPeso = PesoTarifaList.get(r).getCodigo();
					        				}
					        			}
					        		}	
					        		
					        		for (int r=0;r<PrecioTarifaList.size();r++){
					        			if ((PrecioTarifaList.get(r).getPesoTarifa().getCodigo()==idPeso)
					        				 && (PrecioTarifaList.get(r).getTarifa().getCodigo()==idTarifa)){
					        					lResulMonto.setText(String.valueOf(PrecioTarifaList.get(r).getMonto()));
					        				}
					        		}
					        		
					        		for (int r=0;r<IpostelList.size();r++){
					        			if (IpostelList.get(r).getUndMedida().getCodigo() == idUnidad)
				        					if ( (Double.parseDouble(tfPeso.getText()) >= IpostelList.get(r).getDesde()) 
						        				&& (Double.parseDouble(tfPeso.getText()) <= IpostelList.get(r).getHasta()) ){
				        						System.out.println("monto iposssssssssssssssssssstel  "+IpostelList.get(r).getValor());
						        				lTotalipostel.setVisible(true);
						        				ContextoEncomienda.getInstance().setIpostel(IpostelList.get(r));
						        				lTotalipostel.setText(presentacionDecimal(String.valueOf(IpostelList.get(r).getValor())));
						        			}
					        		}	
					        		
			        		}
			        		
			        	}else if (tfPeso.getText().compareTo("") == 0){
			            	lResulMonto.setText("");
			            	lTotalipostel.setText("");
			            	if  (validarInformacionCompleta())  	bCalcularMonto.setDisable(false);
			        		else	bCalcularMonto.setDisable(true);
			        	}			        	
			        }
			    }
			});	
			try{						
				Session sesion1 = openSesion();
				queryResultTipoEmbalaje = sesion1.createQuery("from TipoEmbalaje");
				queryResultCiudadDestino = sesion1.createQuery("from CiudadDestino");						
				queryResultOficina = sesion1.createQuery("from Oficina");
				queryResultUnidadPeso = sesion1.createQuery("from Unidad");
				queryResultPesoTarifa = sesion1.createQuery("from PesoTarifa");
				queryResultPrecioTarifa = sesion1.createQuery("from PrecioTarifa");
				queryResultIpostel = sesion1.createQuery("from Ipostel");	
							
				PesoTarifaList = FXCollections.observableArrayList(queryResultPesoTarifa.list());
				PrecioTarifaList = FXCollections.observableArrayList(queryResultPrecioTarifa.list());
				IpostelList =  FXCollections.observableArrayList(queryResultIpostel.list());
				
				//------------- CARGA DE TIPO ENVIO				
				cbEnvio.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){			
					@Override
					public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {		
						lAlertaMsj.setVisible(false);
						if (validarInformacionCompleta()) 		bCalcularMonto.setDisable(false);
				    	else   		bCalcularMonto.setDisable(true);
				}});	
						
				//------------- CARGA DE TIPO EMBALAJE			
				
				TipoEmbalajeArray = FXCollections.observableArrayList(queryResultTipoEmbalaje.list());	
			
				for (r=0;r<TipoEmbalajeArray.size();r++){
					opcionTipoEmbalaje.add(TipoEmbalajeArray.get(r).getNombre());
				}
				
				cbTipoEmbalaje.setItems(opcionTipoEmbalaje);
				
				cbTipoEmbalaje.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){			
					@Override
					public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {	
						ContextoEncomienda.getInstance().setTipoEmbalaje(arg2.intValue());
						lAlertaMsj.setVisible(false);
						if (validarInformacionCompleta()) 		bCalcularMonto.setDisable(false);
				    	else   		bCalcularMonto.setDisable(true);
				}});
				
				//------------- CARGA DE CIUDAD DESTINO		
				
				CiudadDestinoArray = FXCollections.observableArrayList(queryResultCiudadDestino.list());		
				for (r=0;r<CiudadDestinoArray.size();r++){
					opcionCiudadDestino.add(CiudadDestinoArray.get(r).getDescripcion());
				}
				
				cbCiudadDestino.setItems(opcionCiudadDestino);
				cbCiudadDestino.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){			
					@Override
					public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {						
						System.out.println(arg1.intValue()+" ocd "+arg2.intValue());
						lAlertaMsj.setVisible(false);
						ContextoEncomienda.getInstance().setCuidadDestino(arg2.intValue());
						if (validarInformacionCompleta()) 		bCalcularMonto.setDisable(false);
				    	else   		bCalcularMonto.setDisable(true);
				}});	
								
				//------------- CARGA DE PESO		
				
				UnidadPesoList = FXCollections.observableArrayList(queryResultUnidadPeso.list());		
				for (r=0;r<UnidadPesoList.size();r++){
					opcionUnidadPeso.add(UnidadPesoList.get(r).getDescripcion());
				}
				
				cbUnidadPeso.setItems(opcionUnidadPeso);
				cbUnidadPeso.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){			
					@Override
					public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {		
						lAlertaMsj.setVisible(false);
						if ( (ContextoEncomienda.getInstance().getDestinatario().getNombre()!= null) &&
							 (ContextoEncomienda.getInstance().getRemitenteArriba().getNombre() != null) && 
							 (cbUnidadPeso.getSelectionModel().getSelectedIndex() != -1) &&
							 !(tfPeso.getText().equals(""))	){
									
									int idUnidad=0,idPeso=0,idTarifa=0;					        		
					        		if (ContextoEncomienda.getInstance().getModoPago().equals("cancelado")){ 
							        	if ((ContextoEncomienda.getInstance().getRemitAbajo().compareTo("nuevo")==0)
							        	   || (ContextoEncomienda.getInstance().getRemitAbajo().compareTo("consulta")==0)){
							        		
							        		idTarifa=ContextoEncomienda.getInstance().getRemitenteAbajo().getTarifa().getCodigo();				        		
							        		
							        	}else if ((ContextoEncomienda.getInstance().getRemitAbajo().compareTo("copia")==0)){
							        		
							        		idTarifa=ContextoEncomienda.getInstance().getRemitenteArriba().getTarifa().getCodigo();							        		
							        	}
						        	}else if (ContextoEncomienda.getInstance().getModoPago().equals("fletedestino")){						        		
						        		idTarifa=ContextoEncomienda.getInstance().getDestinatario().getTarifa().getCodigo();
						        	}					        		
					        		
					        		for (int r=0;r<UnidadPesoList.size();r++){
					        			if (r==cbUnidadPeso.getSelectionModel().getSelectedIndex()){
					        				idUnidad = UnidadPesoList.get(r).getCodigo();
					        				break;
					        			}
					        		}			        		
					        		for (int r=0;r<PesoTarifaList.size();r++){
					        			if (PesoTarifaList.get(r).getUndMedida().getCodigo()==idUnidad){
					        				if ( (Double.parseDouble(tfPeso.getText()) >= PesoTarifaList.get(r).getDesde()) 
					        					&& (Double.parseDouble(tfPeso.getText()) <= PesoTarifaList.get(r).getHasta()) ){
					        					idPeso = PesoTarifaList.get(r).getCodigo();
					        				}
					        			}
					        		}			       
					        		boolean bandera1=false,bandera2=false;
					        		for (int r=0;r<PrecioTarifaList.size();r++){
					        			if ((PrecioTarifaList.get(r).getPesoTarifa().getCodigo()==idPeso)
					        				 && (PrecioTarifaList.get(r).getTarifa().getCodigo()==idTarifa)){
					        					bandera1=true;					        					
					        					lResulMonto.setText(presentacionDecimal(String.valueOf(PrecioTarifaList.get(r).getMonto())));
					        				}
					        		}
					        		
					        		for (int r=0;r<IpostelList.size();r++){
					        			if (IpostelList.get(r).getUndMedida().getCodigo() == idUnidad)
				        					if ( (Double.parseDouble(tfPeso.getText()) >= IpostelList.get(r).getDesde()) 
						        				&& (Double.parseDouble(tfPeso.getText()) <= IpostelList.get(r).getHasta()) ){
						        				System.out.println("monto iposs---ssssssstel  "+IpostelList.get(r).getValor());
						        				bandera2=true;lTotalipostel.setVisible(true);
						        				ContextoEncomienda.getInstance().setIpostel(IpostelList.get(r));
						        				lTotalipostel.setText(presentacionDecimal(String.valueOf(IpostelList.get(r).getValor())));
						        			}
					        		}	
					        		if (!bandera1)	lResulMonto.setText("");					        			
					        		bandera1=false;
					        		
					        		if (!bandera2)	lTotalipostel.setText("");					        			
					        		bandera2=false;
					        		
					        		if (validarInformacionCompleta()) 		bCalcularMonto.setDisable(false);
							    	else   		bCalcularMonto.setDisable(true);
							}
				}});
				
				if (ContextoEncomienda.getInstance().getBanderaNuevaFactura() == false){
					queryResultFactura = sesion1.createQuery("from Factura order by Codigo desc");	
					System.out.println(" / / / / / / / / / / / / / / / / / / "+queryResultFactura.list().size());				
					
					if (queryResultFactura.list().size()>0){
						queryResultFactura.setMaxResults(1);					
						objFactura = (Factura) queryResultFactura.uniqueResult();
						bSiguiente.setDisable(true);
						bUltimo.setDisable(true);
						printFactura(objFactura);
					}else{
						lAlertaMsj.setText("NO EXISTEN FACTURAS REGISTRADAS");
						lAlertaMsj.setVisible(true);
						bAnterior.setDisable(true);bSiguiente.setDisable(true);
						bPrimero.setDisable(true);bUltimo.setDisable(true);
						botonRemitente.setDisable(true); botonDestinatario.setDisable(true);
					}
				}	
				
				closeSesion(sesion1);
			}catch(SQLGrammarException sq){
				System.out.println("No existen tablas");			
			}catch(HibernateException e){
				e.printStackTrace();
			}	
							
			//------------- NUMERO DE PIEZA
			cbNroPieza.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){			
				@Override
				public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {		
					System.out.println("Nro Pieza " + (arg2.intValue() + 1) );	
					lAlertaMsj.setVisible(false);
					if (validarInformacionCompleta()) 		bCalcularMonto.setDisable(false);
			    	else   		bCalcularMonto.setDisable(true);
			}});		
					
			tfRecargo.focusedProperty().addListener(new ChangeListener<Boolean>(){
			    @Override
			    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue){
			    	lAlertaMsj.setVisible(false);
			    	
		        	if (validarInformacionCompleta()) 		bCalcularMonto.setDisable(false);
		        	else   		bCalcularMonto.setDisable(true);
			    }
			});	
			
			tfSeguro.focusedProperty().addListener(new ChangeListener<Boolean>(){
			    @Override
			    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue){
			    	lAlertaMsj.setVisible(false);
			    	
			    		if (validarInformacionCompleta())		bCalcularMonto.setDisable(false);
			    		else   		bCalcularMonto.setDisable(true);
			    }
			});	
			
			tfDescripDeclarado.focusedProperty().addListener(new ChangeListener<Boolean>(){
			    @Override
			    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue){
			    	lAlertaMsj.setVisible(false);
			    	
		    		if (validarInformacionCompleta())		bCalcularMonto.setDisable(false);
		    		else   		bCalcularMonto.setDisable(true);
			    }
			});	
						
			tfPeso.addEventHandler(javafx.scene.input.KeyEvent.KEY_TYPED, libreria.decimalValidacion(8));	
	
			tfRecargo.addEventHandler(javafx.scene.input.KeyEvent.KEY_TYPED, libreria.porcentajeValidacion());
			
			tfSeguro.addEventHandler(javafx.scene.input.KeyEvent.KEY_TYPED, libreria.porcentajeValidacion());
	}
	
	private boolean validarInformacionCompleta(){
		
		if  ( (cbEnvio.getSelectionModel().getSelectedIndex()!=-1) && 
  			  (cbCiudadDestino.getSelectionModel().getSelectedIndex()!=-1) &&
  			  (cbTipoEmbalaje.getSelectionModel().getSelectedIndex()!=-1) &&
  			  (cbNroPieza.getSelectionModel().getSelectedIndex()!=-1) &&
  			  (cbUnidadPeso.getSelectionModel().getSelectedIndex()!=-1) &&
  			  (!tfPeso.getText().equals("")) && 
  			  (!tfRecargo.getText().equals("")) &&
  			  (!tfSeguro.getText().equals("")) &&
  			  (!tfDescripDeclarado.getText().equals("")) &&
  			  (!lResulMonto.getText().equals("")) ){
				return true;
		}else
				return false;		
	}
	
	@FXML
	private void actionCalcularMonto() throws Exception{
			lTotalipostel.setVisible(true);
			try{						
				Session sesion1 = openSesion();			
				
				if (validarInformacionCompleta()
				    && (ContextoEncomienda.getInstance().getDestinatario().getNombre() != null) 
				    && (ContextoEncomienda.getInstance().getRemitenteArriba().getNombre() != null)){			
					
					lTotalRecargo.setText(presentacionDecimal(String.valueOf( new BigDecimal(lResulMonto.getText()).multiply(new BigDecimal(tfRecargo.getText())).divide(new BigDecimal("100")) )));					        
					lTotalSeguro.setText(presentacionDecimal(String.valueOf( new BigDecimal(lResulMonto.getText()).multiply(new BigDecimal(tfSeguro.getText())).divide(new BigDecimal("100")) )));
					
					closeSesion(sesion1);				
					double ValorRemesa = objFP.buscarVariable("Remesa");				
					sesion1 = openSesion();
									
					BigDecimal subTotal = new BigDecimal("0");
					
					if (!lValorContrareembolso.getText().isEmpty() && (rbValorDeclarado.isSelected())){ 
						lTotalContrareembolso.setText(presentacionDecimal(lValorContrareembolso.getText()));						
						subTotal= ( new BigDecimal(lResulMonto.getText()).multiply(new BigDecimal(tfRecargo.getText())).divide(new BigDecimal("100")) ).add( new BigDecimal(lResulMonto.getText()).multiply(new BigDecimal(tfSeguro.getText())).divide(new BigDecimal("100")) ).add( new BigDecimal(lResulMonto.getText()) ).add( new BigDecimal(ValorRemesa) ).add( new BigDecimal(lTotalContrareembolso.getText()) );					
						System.out.println("VD VA  ++ + + + + + + + + + + + ++ ");						
					}else{						
						subTotal= ( new BigDecimal(lResulMonto.getText()).multiply(new BigDecimal(tfRecargo.getText())).divide(new BigDecimal("100")) ).add( new BigDecimal(lResulMonto.getText()).multiply(new BigDecimal(tfSeguro.getText())).divide(new BigDecimal("100")) ).add( new BigDecimal(lResulMonto.getText()) ).add( new BigDecimal(ValorRemesa) );					
						System.out.println("VD NO VA  ++ + + + + + + + + + + + ++ ");
					}
						
					if (!lTotalipostel.getText().isEmpty()){
						subTotal = (subTotal.add( new BigDecimal( lTotalipostel.getText() ) ) ) ; 
					}
					
					lSubTotal.setText(presentacionDecimal(subTotal.toString()));
					closeSesion(sesion1);	
					double ValorIVA = objFP.buscarVariable("Iva");		
					sesion1 = openSesion();
					
					BigDecimal porcentajeIVA = new BigDecimal(String.valueOf(ValorIVA/100));
					porcentajeIVA.setScale(2, RoundingMode.HALF_UP);
					
					BigDecimal total = new BigDecimal("0");
					
					porcentajeIVA=subTotal.multiply(porcentajeIVA);							
					total = subTotal.add(porcentajeIVA);
					total.setScale(2, RoundingMode.HALF_UP);
					
					lTotalIVA.setText(presentacionDecimal(porcentajeIVA.toString()));
					lTotalTotal.setText(presentacionDecimal(total.toString()));
					bPago.setDisable(false);
					bCalcularMonto.setDisable(true);
				}else{
					lAlertaMsj.setVisible(true);
					lAlertaMsj.setText("Debe ingresar toda la información");
				}
			
				closeSesion(sesion1);			
			}catch(HibernateException e){
				e.printStackTrace();
			}			
	}
	
	@FXML
	private void actionBotonRemitente() throws Exception{
		lAlertaMsj.setVisible(false);
		ProgramaPrincipal.mostrarVentanaRemitente();	
	}	
	
	@FXML
	private void actionBotonDestinatario() throws Exception{
		lAlertaMsj.setVisible(false);
		ProgramaPrincipal.mostrarVentanaDestinatario();
	}
	
	@FXML
	private void actionCancelar() throws Exception{
		Stage stage = (Stage) bCancelar.getScene().getWindow();
		ContextoEncomienda.getInstance().setBanderaNuevaFactura(false);
		stage.close();		
	}	
	
	@FXML
	private void actionRbCancelado(){
		ContextoEncomienda.getInstance().setModoPago("cancelado");
		rbFletedestino.setSelected(false);	
		lTituloFactura2.setText(lNombreRemitente.getText());
		botonRemitente.setDisable(false);botonRemitente.setOpacity(1);
		botonDestinatario.setDisable(false);botonDestinatario.setOpacity(1);
	}
	
	@FXML
	private void actionRbFleteDestino(){
		ContextoEncomienda.getInstance().setModoPago("fletedestino");	
		rbCancelado.setSelected(false);				
		lTituloFactura2.setText(lNombreDestinatario.getText());
		botonRemitente.setDisable(false);botonRemitente.setOpacity(1);
		botonDestinatario.setDisable(false);botonDestinatario.setOpacity(1);
	}
	
	@FXML
	private void actionRbValorDeclarado() throws Exception{
		lAlertaMsj.setVisible(false);	
		bValorDeclarado.setVisible(false);
		if (rbValorDeclarado.isSelected()){	
			if (ContextoEncomienda.getInstance().getValorDeclarado().getMonto()==null)
				bValorDeclarado.setText("Ingresar monto");
			if (ContextoEncomienda.getInstance().getValorDeclarado().getMonto()!=null){
				lResulValorDeclar.setVisible(true);
				lValorContrareembolso.setVisible(true);	
				lTotalContrareembolso.setVisible(true);
				bCalcularMonto.setDisable(false);
			}
			bValorDeclarado.setVisible(true);
			bValorDeclarado.setDisable(false);
			bValorDeclarado.setOpacity(1);			
			lTotalValorDeclar.setVisible(true);
		}else{
			bCalcularMonto.setDisable(true);
			bValorDeclarado.setVisible(false);
			lValorContrareembolso.setVisible(false);
			lResulValorDeclar.setVisible(false);
			lTotalValorDeclar.setVisible(false);
			lTotalContrareembolso.setVisible(false);
		}
		
	}	

	@FXML
	private void actionValorDeclarado() {
		lAlertaMsj.setVisible(false);
//		System.out.println("Boton valor declarado");
		ProgramaPrincipal.mostrarVentanaValorDeclarado();
	}

	@FXML
	private void actionBotonFacturaNueva(){
//		System.out.println("Factura Nueva ");
		ContextoEncomienda.getInstance().setBanderaNuevaFactura(true);
		if (primeraejecucion){
//			System.out.println("primera ejecucion + boton nueva factura ");			
			try{						
				Session sesion1 = openSesion();
				queryResultTipoEmbalaje = sesion1.createQuery("from TipoEmbalaje");
				queryResultCiudadDestino = sesion1.createQuery("from CiudadDestino");						
					
//				System.out.println("consulta de tipo embalaje "+queryResultTipoEmbalaje.list());
				
				//------------- CARGA DE TIPO EMBALAJE			
				
				TipoEmbalajeArray = FXCollections.observableArrayList(queryResultTipoEmbalaje.list());	
			
				for (r=0;r<TipoEmbalajeArray.size();r++){
					opcionTipoEmbalaje.add(TipoEmbalajeArray.get(r).getNombre());
				}
				
				cbTipoEmbalaje.setItems(opcionTipoEmbalaje);
				cbTipoEmbalaje.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){			
					@Override
					public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
//						System.out.println(opcionTipoEmbalaje.get(arg2.intValue()));				
				}});
				
				//------------- CARGA DE CIUDAD DESTINO
				
				CiudadDestinoArray = FXCollections.observableArrayList(queryResultCiudadDestino.list());		
				for (r=0;r<CiudadDestinoArray.size();r++){
					opcionCiudadDestino.add(CiudadDestinoArray.get(r).getDescripcion());
				}
				
				cbCiudadDestino.setItems(opcionCiudadDestino);
				cbCiudadDestino.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){			
					@Override
					public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
//						System.out.println(opcionCiudadDestino.get(arg2.intValue()));	
//						System.out.println(arg1.intValue()+" ocd "+arg2.intValue());
				}});			
			}catch(HibernateException he){
			
			}		
		}		
		try{						
			Session sesion3 = openSesion();
			queryResultTipoEmbalaje = sesion3.createQuery("from TipoEmbalaje");
			queryResultCiudadDestino = sesion3.createQuery("from CiudadDestino");
			//------------- CARGA DE TIPO EMBALAJE
			cbTipoEmbalaje.setItems(opcionTipoEmbalaje);
			
			
			//------------- CARGA DE CIUDAD DESTINO
			cbCiudadDestino.setItems(opcionCiudadDestino);
			closeSesion(sesion3);
		}catch(HibernateException he){
			
		}
		ContextoEncomienda.getInstance().getDestinatario().setApellido(null);
		ContextoEncomienda.getInstance().getDestinatario().setNombre(null);
		ContextoEncomienda.getInstance().getRemitenteArriba().setApellido(null);
		ContextoEncomienda.getInstance().getRemitenteArriba().setNombre(null);
		lFecha.setVisible(false);
		lValorFecha.setVisible(false);
		rbValorDeclarado.setSelected(false);
		bValorDeclarado.setVisible(false);
		bValorDeclarado.setDisable(false);
		ContextoEncomienda.getInstance().getValorDeclarado().setMonto(null);
		rbCancelado.setDisable(false);
		rbFletedestino.setDisable(false);		
		rbCancelado.setSelected(false);
		rbFletedestino.setSelected(false);	
		cbEnvio.getSelectionModel().select(-1);
		cbCiudadDestino.getSelectionModel().select(-1);
		cbTipoEmbalaje.getSelectionModel().select(-1);
		cbUnidadPeso.getSelectionModel().select(-1);
		cbNroPieza.getSelectionModel().select(-1);
		desactivarComponentes();
		tfPeso.setText("");
		lResulMonto.setText("");
		tfRecargo.setText("");
		tfSeguro.setText("");
		tfDescripDeclarado.setText("");
		lValorContrareembolso.setText("");
		bAnterior.setDisable(true);
		bPrimero.setDisable(true);
		bSiguiente.setDisable(true);
		bUltimo.setDisable(true);
		
		lNombreDestinatario.setText("");
		lNombreRemitente.setText("");
		lTituloFactura2.setText("");
		
		lTotalRecargo.setText("");
		lTotalIVA.setText("");
		lTotalSeguro.setText("");
		lTotalContrareembolso.setText("");
		lTotalTotal.setText("");
		lSubTotal.setText("");
		lTotalipostel.setVisible(false);		
	}		
	
	@FXML
	private void botonActionPagoGuardarFact() throws Exception{	
		System.out.println(" * * * * * * * * *               * * * * * * * * * * *");		
		
		bCalcularMonto.setDisable(true);
		if (bandGuardarImprimir==false){		
			System.out.println("acción pago");
			ContextoEncomienda.getInstance().setFacturaPeso(tfPeso.getText());
			ContextoEncomienda.getInstance().setFacturaMonto(lResulMonto.getText());
			ContextoEncomienda.getInstance().setFacturaRecargo(tfRecargo.getText());
			ContextoEncomienda.getInstance().setFacturaSeguro(tfSeguro.getText());
			ContextoEncomienda.getInstance().setFacturaContrareembolso(lValorContrareembolso.getText());
			ContextoEncomienda.getInstance().setFacturaDescripcionDeclarado(tfDescripDeclarado.getText());
			ContextoEncomienda.getInstance().setFacturaTotalRecargo(lTotalRecargo.getText());
			ContextoEncomienda.getInstance().setFacturaTotalIpostel(lTotalipostel.getText());
			ContextoEncomienda.getInstance().setFacturaTotalContrareembolso(lTotalContrareembolso.getText());
			ContextoEncomienda.getInstance().setFacturaTotalSeguro(lTotalSeguro.getText());
			ContextoEncomienda.getInstance().setFacturaTotalSubTotal(lSubTotal.getText());
			ContextoEncomienda.getInstance().setFacturaTotalIVA(lTotalIVA.getText());			
			ContextoEncomienda.getInstance().setTotalFactura(lTotalTotal.getText());
			ProgramaPrincipal.mostrarVentanaFormaPago();
		}else{
			System.out.println("acción guardar");	
			System.out.println(" es nuevo remitente arriba: "+ContextoEncomienda.getInstance().getRemitArriba());
			System.out.println(" es nuevo remitente abajo: "+ContextoEncomienda.getInstance().getRemitAbajo());
			System.out.println(" es nuevo destinatario: "+ContextoEncomienda.getInstance().getNuevoDestinatario());
			try{
				Session sesion2 = openSesion();
				Cliente objd,objr;
				if (ContextoEncomienda.getInstance().getRemitArriba().equals("nuevo")){
					System.out.println(" es nuevo remitente arriba: ++ "+ContextoEncomienda.getInstance().getRemitArriba());
					objr = new Cliente();
					objr = RemitenteUp;
					sesion2.save(objr);
				}
				if (ContextoEncomienda.getInstance().getRemitAbajo().equals("nuevo")){
					System.out.println(" es nuevo remitente abajo: ++ "+ContextoEncomienda.getInstance().getRemitAbajo());
					objr = new Cliente();
					objr = RemitenteDown;
					sesion2.save(objr);
				}
				if ( (ContextoEncomienda.getInstance().getNuevoDestinatario() == true) && 
					(ContextoEncomienda.getInstance().getModoPago().equals("fletedestino")) ){
					System.out.println(" es nuevo destinatario: ++ "+ContextoEncomienda.getInstance().getNuevoDestinatario());
					objd = new Cliente();
					objd = Destinatario;
					sesion2.save(objd);
				}
				closeSesion(sesion2);
			}catch(HibernateException e){
				e.printStackTrace();
			}			
			
			try{
				Session sesion1 = openSesion();
				Factura objf = new Factura();
				objf.setNroFactura(10);
				objf.setControlFiscal(20);
				objf.setFecha("09/07/2014");
				objf.setModoPago(ContextoEncomienda.getInstance().getModoPago());				
				objf.setTipo_envio(cbEnvio.getSelectionModel().getSelectedItem().toString());
				objf.setTipo_embalaje(TipoEmbalajeArray.get(cbTipoEmbalaje.getSelectionModel().getSelectedIndex()));
				objf.setCuidadDestino(CiudadDestinoArray.get(cbCiudadDestino.getSelectionModel().getSelectedIndex()));
				
				if (ContextoEncomienda.getInstance().getValorDeclarado().getMonto()!=null){
					objf.setValorDeclarado(Double.valueOf(bValorDeclarado.getText()));
					objf.setContrareembolso(Double.parseDouble(lValorContrareembolso.getText()));
					objf.setMontoContrareembolso(Double.parseDouble(lTotalContrareembolso.getText()));					
				}else{
					objf.setValorDeclarado(100.25);
					objf.setContrareembolso(100.25);
					objf.setMontoContrareembolso(100.25);
				}
				
				objf.setCantidadPieza(Integer.parseInt(cbNroPieza.getSelectionModel().getSelectedItem().toString()));
				objf.setPeso(Double.parseDouble(tfPeso.getText()));
				
				objf.setUnidadPeso(UnidadPesoList.get(cbUnidadPeso.getSelectionModel().getSelectedIndex()));

				objf.setMonto(Double.parseDouble(lResulMonto.getText()));
				objf.setPorcentajeRecargo(Double.parseDouble(tfRecargo.getText()));
				objf.setPorcentajeSeguro(Double.parseDouble(tfSeguro.getText()));

				objf.setDescripcionDeclarado(tfDescripDeclarado.getText());
				objf.setMontoRecargo(Double.parseDouble(lTotalRecargo.getText()));
				objf.setMontoSeguro(Double.parseDouble(lTotalSeguro.getText()));
				objf.setMontoSubtotal(Double.parseDouble(lSubTotal.getText()));
				objf.setMontoIVA(Double.parseDouble(lTotalIVA.getText()));
				objf.setMontoTotal(Double.parseDouble(lTotalTotal.getText()));
				objf.setClienteRemitente(RemitenteDown);
				objf.setClienteEnvia(RemitenteUp);
				
				objf.setIpostel(ContextoEncomienda.getInstance().getIpostel());
								
				System.out.println();System.out.println();
				
				if (ContextoEncomienda.getInstance().getModoPago().equals("cancelado")){
					System.out.println("cancelado o o o o ");
					objf.setCNombre(Destinatario.getNombre());
					objf.setCApellido(Destinatario.getApellido());
					objf.setCDireccion(Destinatario.getDireccion());
					objf.setCTelefono(Destinatario.getTelefono());
					objf.setCCorreo(Destinatario.getCorreo());
				}else {
					System.out.println("fd d d d d d d");
					objf.setClienteDestinatario(Destinatario);
				}
				
				queryResultOficina = sesion1.createQuery("from Oficina");
				OficinaList = FXCollections.observableArrayList(queryResultOficina.list());	
				objf.setOficina(OficinaList.get(0));
				sesion1.save(objf);
				objFactura=objf;
				closeSesion(sesion1);
				System.out.println("FIN");
			}catch(HibernateException e){
				e.printStackTrace();
			}
			guardarFormaPago();
		}	
	}	
	
	@FXML
	private void actionBotonPrimero(){
		
		bAnterior.setDisable(true);
		bPrimero.setDisable(true);
		bSiguiente.setDisable(false);
		bUltimo.setDisable(false);
		botonDestinatario.setDisable(true);
		botonRemitente.setDisable(true);
		rbFletedestino.setDisable(true);
 		rbCancelado.setDisable(true);
 		rbFletedestino.setOpacity(1);
 		rbCancelado.setOpacity(1);
 		cbEnvio.setOpacity(1);
 		cbTipoEmbalaje.setOpacity(1);
 		cbCiudadDestino.setOpacity(1);
 		cbNroPieza.setOpacity(1);
 		cbUnidadPeso.setOpacity(1);
 		
 		try{
			Session sesion1 = openSesion();
			queryResultFactura = sesion1.createQuery("from Factura order by Codigo asc");
			queryResultFactura.setMaxResults(1);
			objFactura = (Factura) queryResultFactura.uniqueResult();
			
			printFactura(objFactura);
			closeSesion(sesion1);
		}catch(HibernateException e){
			e.printStackTrace();
		}
	}
	
	@FXML
	private void actionBotonAnterior(){
		bSiguiente.setDisable(false);
		bUltimo.setDisable(false);
		try{
			int codfac = objFactura.getCodigo();
			Session sesion1 = openSesion();
			Factura temp;
			queryResultFactura = sesion1.createQuery("from Factura where Codigo < :cdd order by Codigo desc");
			queryResultFactura.setInteger("cdd",codfac);
			queryResultFactura.setMaxResults(1);
			
			objFactura = (Factura) queryResultFactura.uniqueResult();
			
			if (objFactura != null){
				printFactura(objFactura);
				queryResultFactura = sesion1.createQuery("from Factura where Codigo < :cd order by Codigo desc");
				queryResultFactura.setInteger("cd",objFactura.getCodigo());
				queryResultFactura.setMaxResults(1);
				temp = (Factura) queryResultFactura.uniqueResult();
				if (temp == null){
					bAnterior.setDisable(true);
					bPrimero.setDisable(true);
				}
			}
			closeSesion(sesion1);		
		}catch(HibernateException e){
			e.printStackTrace();
		}	
	}
	
	@FXML
	private void botonActionSiguiente(){
		bAnterior.setDisable(false);
		bPrimero.setDisable(false);
		
		try{			
			int codfac = objFactura.getCodigo();
			Session sesion1 = openSesion();
			Factura temp;
			
			queryResultFactura = sesion1.createQuery("from Factura where Codigo > :cdd order by Codigo asc");
			queryResultFactura.setInteger("cdd",codfac);
			queryResultFactura.setMaxResults(1);
			
			objFactura = (Factura) queryResultFactura.uniqueResult();
			
			if (objFactura != null){
				printFactura(objFactura);
				queryResultFactura = sesion1.createQuery("from Factura where Codigo > :cd order by Codigo asc");
				queryResultFactura.setInteger("cd",objFactura.getCodigo());
				queryResultFactura.setMaxResults(1);
				temp = (Factura) queryResultFactura.uniqueResult();
				if (temp == null){
					bSiguiente.setDisable(true);
					bUltimo.setDisable(true);
				}
			}
			closeSesion(sesion1);		
		}catch(HibernateException e){
			e.printStackTrace();
		}		
	}
	
	@FXML
	private void botonActionUltimo(){
		bAnterior.setDisable(false);
		bPrimero.setDisable(false);
		bSiguiente.setDisable(true);
		bUltimo.setDisable(true);
		botonDestinatario.setDisable(true);
		botonRemitente.setDisable(true);
		rbFletedestino.setDisable(true);
 		rbCancelado.setDisable(true);
 		rbFletedestino.setOpacity(1);
 		rbCancelado.setOpacity(1);
 		cbEnvio.setOpacity(1);
 		cbTipoEmbalaje.setOpacity(1);
 		cbCiudadDestino.setOpacity(1);
 		cbNroPieza.setOpacity(1);
 		cbUnidadPeso.setOpacity(1);
 		
 		try{
			Session sesion1 = openSesion();
			queryResultFactura = sesion1.createQuery("from Factura order by Codigo desc");		
			queryResultFactura.setMaxResults(1);
			objFactura = (Factura) queryResultFactura.uniqueResult();
			
			printFactura(objFactura);
			closeSesion(sesion1);
		}catch(HibernateException e){
			e.printStackTrace();
		}	
	}		
	
	@FXML
	private void actionBotonFacturaEliminar(){
		
		try{
			Session sesion1 = openSesion();
			queryResultFactura = sesion1.createQuery("from Factura");		
			facturaArray = FXCollections.observableArrayList(queryResultFactura.list());
			objf = (Factura) sesion1.get(Factura.class, Integer.parseInt(lFacturaCodigo.getText()));
		
			int p=0;
			while ( ! String.valueOf(facturaArray.get(p).getCodigo()).equals(lFacturaCodigo.getText() ) ){
				p++;
			}
			System.out.println("p igual: " +p);
			listite = facturaArray.listIterator(p);

			if ( (p>0) && (p<(facturaArray.size()-1)) ){
				objant = (Factura) listite.next();
				objant = (Factura) listite.next();
			}else if (p == facturaArray.size()-1){
				objant = (Factura) listite.previous();
			}else if (p == 0){
				objant = (Factura) listite.next();
				objant = (Factura) listite.next();
			} 
			
			printFactura(objant);
			sesion1.delete(objf);	
			lAlertaMsj.setText("Registro eliminado exitosamente");
			lAlertaMsj.setVisible(true);
			closeSesion(sesion1);
		}catch(HibernateException e){
			e.printStackTrace();
		}
	}
	
	public void setDestinatario(Factura objf){
		
		objFP = objf;
		lTituloFactura2 = new Label();
		lTituloFactura2.setText("aja aja llegue");
		tfDescripDeclarado = new TextField();
		tfDescripDeclarado.setDisable(false);
		tfDescripDeclarado.setText("aaaaaaaaakkkk");
		lTituloFactura2.setVisible(true);
	}
	
	void activarComponentes(){
		botonRemitente.setDisable(false); botonRemitente.setOpacity(1);
		botonDestinatario.setDisable(false); botonDestinatario.setOpacity(1);
		tfPeso.setDisable(false);  tfPeso.setOpacity(1);
		lResulMonto.setOpacity(1);
		tfRecargo.setDisable(false);  tfRecargo.setOpacity(1);
		lValorContrareembolso.setDisable(false);  lValorContrareembolso.setOpacity(1);
		tfSeguro.setDisable(false);  tfSeguro.setOpacity(1);
		tfDescripDeclarado.setDisable(false);  tfDescripDeclarado.setOpacity(1);
		
		cbUnidadPeso.setDisable(false);  cbUnidadPeso.setOpacity(1);				
		cbCiudadDestino.setDisable(false);  cbCiudadDestino.setOpacity(1);
		cbEnvio.setDisable(false);  cbEnvio.setOpacity(1);
		cbNroPieza.setDisable(false);  cbNroPieza.setOpacity(1);
		cbTipoEmbalaje.setDisable(false);  cbTipoEmbalaje.setOpacity(1);
		rbValorDeclarado.setDisable(false);	 rbValorDeclarado.setOpacity(1);	
	}	
	
	void desactivarComponentes(){
		botonDestinatario.setDisable(true); botonDestinatario.setOpacity(0.5);
		botonRemitente.setDisable(true); botonRemitente.setOpacity(0.5);		
		
		cbEnvio.setDisable(true); cbEnvio.setOpacity(0.5);	
		cbCiudadDestino.setDisable(true); cbCiudadDestino.setOpacity(0.5);	
		cbTipoEmbalaje.setDisable(true); cbTipoEmbalaje.setOpacity(0.5);
		cbUnidadPeso.setDisable(true); cbUnidadPeso.setOpacity(0.5);	
		cbNroPieza.setDisable(true); cbNroPieza.setOpacity(0.5);
					
		tfPeso.setDisable(true);  tfPeso.setOpacity(0.5);
		lResulMonto.setOpacity(0.5);
		tfRecargo.setDisable(true);  tfRecargo.setOpacity(0.5);
		lValorContrareembolso.setDisable(true);  lValorContrareembolso.setOpacity(0.5);
		tfSeguro.setDisable(true);  tfSeguro.setOpacity(0.5);
		tfDescripDeclarado.setDisable(true);  tfDescripDeclarado.setOpacity(0.5);
		rbValorDeclarado.setDisable(true); rbValorDeclarado.setOpacity(0.5);
	}
	
	private void guardarFormaPago(){
		FormaPago objFP = new FormaPago();
		Session sesion3;
		
		try{			
			for (int y=0; y<=itemsPago.size()-1; y++){
				sesion3 = openSesion();	
				
				System.out.println(itemsPago.get(y).getTipoPago()+" "+itemsPago.get(y).getMonto()+" "+itemsPago.get(y).getSigno()+" "+itemsPago.get(y).getBanco()+" "+itemsPago.get(y).getTipoTarjeta()+" "+itemsPago.get(y).getNroRef());
				objFP.setTipoPago(itemsPago.get(y).getTipoPago());
				objFP.setMonto(itemsPago.get(y).getMonto());
				objFP.setSigno(itemsPago.get(y).getSigno());
				objFP.setBanco(itemsPago.get(y).getBanco());
				objFP.setTipoTarjeta(itemsPago.get(y).getTipoTarjeta());
				objFP.setNroRef(itemsPago.get(y).getNroRef());
				objFP.setFactura(objFactura);
				sesion3.save(objFP);
				closeSesion(sesion3);
			}			
		}catch(HibernateException e){
			e.printStackTrace();
		}
	}
	
	private void printFactura(Factura obj){	
		 System.out.println("llegue a print factura  "+obj.getCodigo());
		
		 lFecha.setVisible(true);
		 lValorFecha.setVisible(true);
		 lValorFecha.setText(obj.getFecha());
		 
		 lFacturaCodigo.setText(String.valueOf(obj.getCodigo()));
		 tfPeso.setText( presentacionDecimal( String.valueOf( obj.getPeso() ) ) );
		 tfPeso.setDisable(true);  tfPeso.setOpacity(1);	
		 lResulMonto.setText( presentacionDecimal(  String.valueOf(obj.getMonto()) ) );
		 lResulMonto.setOpacity(1);
		 
		 tfRecargo.setText(  presentacionDecimal( String.valueOf(obj.getPorcentajeRecargo()) ) );
		 tfRecargo.setDisable(true);  tfRecargo.setOpacity(1);
		 tfSeguro.setText( presentacionDecimal( String.valueOf(obj.getPorcentajeSeguro()) ) );
		 tfSeguro.setDisable(true);  tfSeguro.setOpacity(1);
		 lValorContrareembolso.setText(String.valueOf(obj.getContrareembolso()));
		 lValorContrareembolso.setDisable(true);  lValorContrareembolso.setOpacity(1);
		 tfDescripDeclarado.setText(obj.getDescripcionDeclarado());
		 tfDescripDeclarado.setDisable(true); tfDescripDeclarado.setOpacity(1);
		 lTotalRecargo.setText(String.valueOf(obj.getMontoRecargo()));		 
		 lTotalContrareembolso.setText(String.valueOf(obj.getMontoContrareembolso()));
		 
		 lTotalipostel.setText( presentacionDecimal ( String.valueOf ( obj.getIpostel().getValor() ) ) );
		 
		 lTotalSeguro.setText(String.valueOf(obj.getMontoSeguro()));
		 lSubTotal.setText(String.valueOf(obj.getMontoSubtotal()));
		 lTotalIVA.setText(String.valueOf(obj.getMontoIVA()));
		 lTotalTotal.setText(String.valueOf(obj.getMontoTotal()));
		 cbCiudadDestino.setOpacity(1);
		 cbEnvio.setOpacity(1);
		 cbNroPieza.setOpacity(1);
		 cbTipoEmbalaje.setOpacity(1);
		 cbUnidadPeso.setOpacity(1);
		 if (obj.getModoPago().compareTo("cancelado")==0){
			 	rbCancelado.setSelected(true);
			 	rbFletedestino.setSelected(false);			 	
			 	desactivarRB();
			 	lTituloFactura2.setText(obj.getClienteRemitente().getNombre()+" "+obj.getClienteRemitente().getApellido());
		 }else if (obj.getModoPago().compareTo("flete destino")==0){
		 		rbFletedestino.setSelected(true);
		 		rbCancelado.setSelected(false);		 		
		 		desactivarRB();	
//		 		System.out.println("u u u u  "+obj.getClienteDestinatario().getNombre());
		 		if (obj.getClienteDestinatario()!=null){
		 			lTituloFactura2.setText(obj.getClienteDestinatario().getNombre()+" "+obj.getClienteDestinatario().getApellido());
		 			lTituloFactura2.setVisible(true);
				}else{
					lTituloFactura2.setText(obj.getCNombre()+" "+obj.getCApellido());
					lTituloFactura2.setVisible(true);
				}
	    }
		
		if (obj.getTipo_envio().compareTo("Oficina")==0){
				cbEnvio.getSelectionModel().select(0);
		}else if (obj.getTipo_envio().compareTo("Dirección fiscal")==0){
				cbEnvio.getSelectionModel().select(1);
		}		 
		cbTipoEmbalaje.getSelectionModel().select(obj.getTipo_embalaje().getCodigo()-1);	
		cbCiudadDestino.getSelectionModel().select(obj.getCuidadDestino().getCodigo()-1);
		if (obj.getValorDeclarado() != 100.25){
			rbValorDeclarado.setSelected(true);
			rbValorDeclarado.setDisable(true);		 		
			rbValorDeclarado.setOpacity(1);
		}else if (obj.getValorDeclarado() == 100.25){
			rbValorDeclarado.setSelected(false);	
			rbValorDeclarado.setDisable(true);		 		
			rbValorDeclarado.setOpacity(1);
		}
		bValorDeclarado.setVisible(true);
		bValorDeclarado.setText(String.valueOf(obj.getValorDeclarado()));
		bValorDeclarado.setDisable(true);
		bValorDeclarado.setOpacity(0.5);
		cbNroPieza.getSelectionModel().select(obj.getCantidadPieza()-1);
		for (int w=0;w<UnidadPesoList.size();w++)
			if (UnidadPesoList.get(w).getDescripcion().equals( obj.getUnidadPeso().getDescripcion()) )
				cbUnidadPeso.getSelectionModel().select(w);		
		if (obj.getClienteDestinatario()!=null){
			lNombreDestinatario.setText(obj.getClienteDestinatario().getNombre()+" "+obj.getClienteDestinatario().getApellido());
			lNombreDestinatario.setVisible(true);
		}else{
			lNombreDestinatario.setText(obj.getCNombre()+" "+obj.getCApellido());
			lNombreDestinatario.setVisible(true);
		}
		lNombreRemitente.setText(obj.getClienteRemitente().getNombre()+" "+obj.getClienteRemitente().getApellido());
		lNombreRemitente.setVisible(true);
		
		lTotalRecargo.setText( presentacionDecimal(lTotalRecargo.getText()) );
		lTotalSeguro.setText( presentacionDecimal(lTotalSeguro.getText()) );
		lTotalContrareembolso.setText( presentacionDecimal(lTotalContrareembolso.getText()) );
		lValorContrareembolso.setText( presentacionDecimal(lValorContrareembolso.getText()) );		
		lSubTotal.setText( presentacionDecimal(lSubTotal.getText()) );
		lTotalIVA.setText( presentacionDecimal(lTotalIVA.getText()) );
		lTotalTotal.setText( presentacionDecimal(lTotalTotal.getText()) );
		bValorDeclarado.setText( presentacionDecimal(bValorDeclarado.getText()) );		
	}
	
	private String presentacionDecimal(String cadena){
		DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
		simbolos.setDecimalSeparator('.');
		final DecimalFormat decimalFormat = new DecimalFormat("##.00",simbolos);	
		
		return decimalFormat.format( Double.parseDouble( cadena) );
	}	

	private void desactivarRB(){
		rbFletedestino.setDisable(true);
	 	rbCancelado.setDisable(true);			 	
	 	rbFletedestino.setOpacity(1);
	 	rbCancelado.setOpacity(1);
	}
	
	private Session openSesion(){		
		Session sesion = Main.sesionFactory.getCurrentSession();
		sesion.beginTransaction();
		
		return sesion;
	}
	
	private void closeSesion(Session sesion){
		
		sesion.getTransaction().commit();
	}
	
	public void setProgramaPrincipal(PruebaVentanas ProgramaPrincipal) {
		 System.out.println("Controlador ventana principal - setPP");
		 this.ProgramaPrincipal = ProgramaPrincipal;
	 }
	 
	 public void setTexto(String param){
		 System.out.println("limpiar todo y colocar el combobox de cancelado/fletedestino segun consulta temporal");
		 
		 lNombreDestinatario.setText(param);
		 lNombreDestinatario.setVisible(true);
	 }
	 
	 @FXML
	 private void actionMouseEnter(){
		 if (ContextoEncomienda.getInstance().getBanderaRefresh()){
			 ContextoEncomienda.getInstance().setBanderaRefresh(false);
			 
			 if (ContextoEncomienda.getInstance().getBanderaNuevaFactura() == true){				
				botonDestinatario.setDisable(false);
				botonRemitente.setDisable(false);
				bSiguiente.setDisable(true);	bUltimo.setDisable(true);
				bAnterior.setDisable(true);		bPrimero.setDisable(true);
				botonDestinatario.setOpacity(1);	botonRemitente.setOpacity(1);
				
				if ( (ContextoEncomienda.getInstance().getDestinatario().getNombre()!=null) &&
					(ContextoEncomienda.getInstance().getRemitenteArriba().getNombre() != null) )
					activarComponentes();
				 
				if (ContextoEncomienda.getInstance().getModoPago().compareTo("cancelado")==0){
					rbFletedestino.setSelected(false);	
					rbCancelado.setSelected(true);
					if (ContextoEncomienda.getInstance().getRemitenteArriba().getNombre() != null){
						lTituloFactura2.setVisible(true);					
						lTituloFactura2.setText(ContextoEncomienda.getInstance().getRemitenteArriba().getNombre()+" "+ContextoEncomienda.getInstance().getRemitenteArriba().getApellido());					
					}
				}else if (ContextoEncomienda.getInstance().getModoPago().compareTo("fletedestino")==0){
					rbFletedestino.setSelected(true);	
					rbCancelado.setSelected(false);
					if (ContextoEncomienda.getInstance().getDestinatario().getNombre() != null){
						lTituloFactura2.setVisible(true);					
						lTituloFactura2.setText(ContextoEncomienda.getInstance().getDestinatario().getNombre()+" "+ContextoEncomienda.getInstance().getDestinatario().getApellido());
					}
				}
				if (ContextoEncomienda.getInstance().getDestinatario().getNombre()!=null){
					 Destinatario = ContextoEncomienda.getInstance().getDestinatario();
					 lNombreDestinatario.setVisible(true);
					 lNombreDestinatario.setText(ContextoEncomienda.getInstance().getDestinatario().getNombre()+" "+ContextoEncomienda.getInstance().getDestinatario().getApellido());
				 }
				if (ContextoEncomienda.getInstance().getRemitenteArriba().getNombre() != null){
//						System.out.println(" - - - - - - - - -  ENTRE A REMITENTE  -- - - - - - - - - ");
						
						bandRemitente = true;
						  if ( ( (ContextoEncomienda.getInstance().getRemitArriba().compareTo("nuevo")==0) ||
							 (ContextoEncomienda.getInstance().getRemitArriba().compareTo("consulta")==0) ) &&
							 (ContextoEncomienda.getInstance().getRemitAbajo().compareTo("copia")==0) ) {
							   if (ContextoEncomienda.getInstance().getModoPago().compareTo("cancelado") == 0){
									lTituloFactura2.setVisible(true);					
									lTituloFactura2.setText(ContextoEncomienda.getInstance().getRemitenteArriba().getNombre()+" "+ContextoEncomienda.getInstance().getRemitenteArriba().getApellido());							
							   }
//							   System.out.println("nuevo || consulta y copia");
							   if (ContextoEncomienda.getInstance().getRemitArriba().compareTo("nuevo")==0){
								   nuevoRemitenteUp = true;   
							   }
							   lNombreRemitente.setVisible(true);
							   lNombreRemitente.setText(ContextoEncomienda.getInstance().getRemitenteArriba().getNombre()+" "+ContextoEncomienda.getInstance().getRemitenteArriba().getApellido());					  	
							   RemitenteUp = RemitenteDown = ContextoEncomienda.getInstance().getRemitenteArriba();
						  }	
						  
						  if ( ( (ContextoEncomienda.getInstance().getRemitArriba().compareTo("nuevo")==0) ||
							(ContextoEncomienda.getInstance().getRemitArriba().compareTo("consulta")==0) ) &&
						    (ContextoEncomienda.getInstance().getRemitAbajo().compareTo("nuevo")==0) ){
							  	if (ContextoEncomienda.getInstance().getModoPago().compareTo("cancelado") == 0){
							  		lTituloFactura2.setVisible(true);	
							  		lTituloFactura2.setText(ContextoEncomienda.getInstance().getRemitenteAbajo().getNombre()+" "+ContextoEncomienda.getInstance().getRemitenteAbajo().getApellido());				  		
							  	}
//							  	System.out.println("nuevo || consulta y nuevo");
							  	if (ContextoEncomienda.getInstance().getRemitArriba().compareTo("nuevo")==0){
							  		nuevoRemitenteUp = true;
							  	}
							  	if (ContextoEncomienda.getInstance().getRemitAbajo().compareTo("nuevo")==0){
							  		nuevoRemitenteDown = true;
							  	}
							  	
							  	lNombreRemitente.setVisible(true);
								lNombreRemitente.setText(ContextoEncomienda.getInstance().getRemitenteAbajo().getNombre()+" "+ContextoEncomienda.getInstance().getRemitenteAbajo().getApellido());
								RemitenteUp = ContextoEncomienda.getInstance().getRemitenteArriba();
								RemitenteDown = ContextoEncomienda.getInstance().getRemitenteAbajo();
						  }
						  
						  if ( ( (ContextoEncomienda.getInstance().getRemitArriba().compareTo("nuevo")==0) ||
							(ContextoEncomienda.getInstance().getRemitArriba().compareTo("consulta")==0) ) &&
							(ContextoEncomienda.getInstance().getRemitAbajo().compareTo("consulta")==0) ){
							  
							  if (ContextoEncomienda.getInstance().getModoPago().compareTo("cancelado") == 0){
							  		lTituloFactura2.setVisible(true);	
							  		lTituloFactura2.setText(ContextoEncomienda.getInstance().getRemitenteAbajo().getNombre()+" "+ContextoEncomienda.getInstance().getRemitenteAbajo().getApellido());					  		
							  }
//							  System.out.println("nuevo || consulta y consulta");
							  if (ContextoEncomienda.getInstance().getRemitArriba().compareTo("nuevo")==0){
							  		nuevoRemitenteUp = true;
							  	}
							  if (ContextoEncomienda.getInstance().getRemitAbajo().compareTo("nuevo")==0){
							  		nuevoRemitenteDown = true;
							  }
							  lNombreRemitente.setVisible(true);
							  lNombreRemitente.setText(ContextoEncomienda.getInstance().getRemitenteAbajo().getNombre()+" "+ContextoEncomienda.getInstance().getRemitenteAbajo().getApellido());
							  RemitenteUp = ContextoEncomienda.getInstance().getRemitenteArriba();
							  RemitenteDown = ContextoEncomienda.getInstance().getRemitenteAbajo();				  
						}
				}				
					
				if (ContextoEncomienda.getInstance().getValorDeclarado().getMonto()!=null){			
					bValorDeclarado.setVisible(true);
					lResulValorDeclar.setVisible(true);
					bValorDeclarado.setText( presentacionDecimal(ContextoEncomienda.getInstance().getValorDeclarado().getMonto()) );	
					rbValorDeclarado.setSelected(true);
					lTotalValorDeclar.setVisible(true);				
								
					double ValorValorDeclarado = objFP.buscarVariable("Valor declarado");	
					
					//la multiplicacion por el porcentaje por defecto de configuracion: 0.10 ejm
					lTotalValorDeclar.setText( presentacionDecimal(String.valueOf(Double.parseDouble(ContextoEncomienda.getInstance().getValorDeclarado().getMonto()) * (ValorValorDeclarado / 100) )));					
					lValorContrareembolso.setText(presentacionDecimal(String.valueOf(Double.parseDouble(ContextoEncomienda.getInstance().getValorDeclarado().getMonto()) * (ValorValorDeclarado / 100) )));
					lTotalContrareembolso.setText(presentacionDecimal(String.valueOf(Double.parseDouble(ContextoEncomienda.getInstance().getValorDeclarado().getMonto()) * (ValorValorDeclarado / 100) )));
				}else if (ContextoEncomienda.getInstance().getValorDeclarado().getMonto()==null){
					bValorDeclarado.setText("Ingresar monto");
					rbValorDeclarado.setSelected(false);
				}
					
				if (!ContextoEncomienda.getInstance().getItemsFormaPago().isEmpty()){
					itemsPago=ContextoEncomienda.getInstance().getItemsFormaPago();
					tfPeso.setText(ContextoEncomienda.getInstance().getFacturaPeso());
					lResulMonto.setText(ContextoEncomienda.getInstance().getFacturaMonto());
					tfRecargo.setText(ContextoEncomienda.getInstance().getFacturaRecargo());
					tfSeguro.setText(ContextoEncomienda.getInstance().getFacturaSeguro());
					lValorContrareembolso.setText(ContextoEncomienda.getInstance().getFacturaContrareembolso());
					tfDescripDeclarado.setText(ContextoEncomienda.getInstance().getFacturaDescripcionDeclarado());
					lTotalRecargo.setText(ContextoEncomienda.getInstance().getFacturaTotalRecargo());
					lTotalipostel.setText(ContextoEncomienda.getInstance().getFacturaTotalIpostel());
					lTotalContrareembolso.setText(ContextoEncomienda.getInstance().getFacturaTotalContrareembolso());
					lTotalSeguro.setText(ContextoEncomienda.getInstance().getFacturaTotalSeguro());
					lSubTotal.setText(ContextoEncomienda.getInstance().getFacturaTotalSubTotal());
					lTotalIVA.setText(ContextoEncomienda.getInstance().getFacturaTotalIVA());
					lTotalTotal.setText(ContextoEncomienda.getInstance().getTotalFactura());
					bandGuardarImprimir = true;				bPago.setVisible(true);
					bPago.setText("Guardar e imprimir factura");		bPago.setDisable(false);
					desactivarComponentes();
					rbFletedestino.setDisable(true); rbCancelado.setDisable(true);
					rbFletedestino.setOpacity(0.5);  rbCancelado.setOpacity(0.5);
					botonRemitente.setDisable(true); botonDestinatario.setDisable(true);
					botonRemitente.setOpacity(0.5); botonDestinatario.setOpacity(0.5);
					bValorDeclarado.setDisable(true); bValorDeclarado.setOpacity(0.5);
				}				
			}
		 }
	}
	 
	 
}
