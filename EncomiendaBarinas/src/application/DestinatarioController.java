package application;

import java.awt.event.KeyEvent;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import data.Cliente;
import data.Factura;
import data.Oficina;
import data.Tarifa;
import data.Temporal;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DestinatarioController {

	@FXML
	public Label linforRD;	
			
	@FXML
	public TextField tfNombreC;
	
	@FXML
	public TextField tfApellidoC;
	
	@FXML
	public TextField tfTelefonoC;
	
	@FXML
	public TextField tfCorreoC;
	
	@FXML
	public TextArea tfDireccionC;
	
	@FXML
	public TextField tfRIFCedulaC;	
	
	@FXML
	public ChoiceBox cbRIFCedulaC;
	
	@FXML
	public ChoiceBox cbTarifa;
	
	@FXML
	public Button bLimpiarRD;
		
	@FXML
	public Button bCancelar;
	
	@FXML
	public Button bProcesar;
		
	@FXML
	public Label lMsjPersRemit;	
	
	@FXML
	public Label lAlerta;
	
	@FXML
	public Label lEjemploCIR;		
	
	boolean bandCB_rifced = false, bandClienteYaExiste = false;
	char[] vector;
	
	private ObservableList<Cliente> clienteList = FXCollections.observableArrayList();
		
	char [] v,vv;
	
    int seleccionCBRifCedula = 0;
    
    Cliente objDArriba = new Cliente();   
    
    Query queryResult;
    Query queryResultOficina;	
    Query queryResultTarifa;
    
    private ObservableList<Oficina> OficinaList = FXCollections.observableArrayList();
    private ObservableList<Tarifa> TarifaList = FXCollections.observableArrayList();
	           
    private ObservableList<String> opcionTarifa = FXCollections.observableArrayList();
    
    Factura objFF = new Factura();
    
	private PruebaVentanas ProgramaPrincipal;
    
	@FXML
	private void initialize(){
		
		if (ContextoEncomienda.getInstance().getDestinatario().getNombre()!=null){			
			
			if (ContextoEncomienda.getInstance().getDestinatario().getTipoRifCedula().compareTo("nv")==0){
				cbRIFCedulaC.getSelectionModel().select(0);	
				tfRIFCedulaC.setText("V-");
			}else if (ContextoEncomienda.getInstance().getDestinatario().getTipoRifCedula().compareTo("ne")==0){
				cbRIFCedulaC.getSelectionModel().select(0);
				tfRIFCedulaC.setText("E-");
			}else if (ContextoEncomienda.getInstance().getDestinatario().getTipoRifCedula().compareTo("jj")==0){
				cbRIFCedulaC.getSelectionModel().select(1);
				tfRIFCedulaC.setText("J-");
			}else if (ContextoEncomienda.getInstance().getDestinatario().getTipoRifCedula().compareTo("jg")==0){
				cbRIFCedulaC.getSelectionModel().select(1);
				tfRIFCedulaC.setText("G-");
			}			
			tfRIFCedulaC.setText(tfRIFCedulaC.getText()+String.valueOf(ContextoEncomienda.getInstance().getDestinatario().getRifCedula()));
			tfNombreC.setText(String.valueOf(ContextoEncomienda.getInstance().getDestinatario().getNombre()));
			tfApellidoC.setText(String.valueOf(ContextoEncomienda.getInstance().getDestinatario().getApellido()));
			tfTelefonoC.setText(String.valueOf(ContextoEncomienda.getInstance().getDestinatario().getTelefono()));
			tfDireccionC.setText(String.valueOf(ContextoEncomienda.getInstance().getDestinatario().getDireccion()));
		}
		
		try{						
			Session sesion1 = openSesion();
			queryResultOficina = sesion1.createQuery("from Oficina");
			OficinaList = FXCollections.observableArrayList(queryResultOficina.list());			
			
			queryResultTarifa = sesion1.createQuery("from Tarifa");
			TarifaList = FXCollections.observableArrayList(queryResultTarifa.list());
			for (int r=0;r<TarifaList.size();r++){
				opcionTarifa.add(TarifaList.get(r).getDescripcion());
			}
			cbTarifa.setItems(opcionTarifa);
			closeSesion(sesion1);
		}catch(HibernateException e){
			e.printStackTrace();
		}	
				
		tfRIFCedulaC.textProperty().addListener(new ChangeListener<String>(){
			 @Override
			    public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {              
		    	 lAlerta.setVisible(false);
		    	 lEjemploCIR.setVisible(true);	  
		    	 
		    	 if (seleccionCBRifCedula == 1){
		        		vv = newValue.toCharArray();
					 	if (newValue.compareTo("")!=0){
					 		if (vv.length == 1){	
					 			tipocedularif(vv,"nopersona");					 		
						 	}else if ((vv.length > 2) && (vv.length <= 10)){	
						 		if ( (vv[vv.length-1] == '1') || (vv[vv.length-1] == '2') || (vv[vv.length-1] == '3')
						 				|| (vv[vv.length-1] == '4') || (vv[vv.length-1] == '5') || (vv[vv.length-1] == '6')
						 				|| (vv[vv.length-1] == '7') || (vv[vv.length-1] == '8') || (vv[vv.length-1] == '9')
						 				|| (vv[vv.length-1] == '0') ){
						 			tfRIFCedulaC.setText(newValue);	
						 			Session sesion3 = openSesion();
							 		
									if ((vv[0] == 'V') && (vv[1] == '-')){	
							 			queryResult = sesion3.createQuery("from Cliente where rifCedula="+newValue.substring(2)+"AND tipoRifCedula=\'nv\'");
										clienteList = FXCollections.observableArrayList(queryResult.list());
						 			}else if ((vv[0] == 'E') && (vv[1] == '-')){
							 			queryResult = sesion3.createQuery("from Cliente where rifCedula="+newValue.substring(2)+"AND tipoRifCedula=\'ne\'");
										clienteList = FXCollections.observableArrayList(queryResult.list());
						 			}	
									
									if (clienteList.size() > 0){
										tfNombreC.setText(clienteList.get(0).getNombre());
										tfApellidoC.setText(clienteList.get(0).getApellido());
										tfDireccionC.setText(clienteList.get(0).getDireccion());
										tfTelefonoC.setText(clienteList.get(0).getTelefono());
										tfCorreoC.setText(clienteList.get(0).getCorreo());
										
										for (int r=0;r<TarifaList.size();r++){
											if ( clienteList.get(0).getTarifa().getDescripcion().equals(TarifaList.get(r).getDescripcion()) ){
												cbTarifa.getSelectionModel().select(r);
												break;
											}
										}
										
										tfNombreC.setDisable(true);	tfNombreC.setOpacity(0.5);
										tfApellidoC.setDisable(true); tfApellidoC.setOpacity(0.5);
										tfDireccionC.setDisable(true); tfDireccionC.setOpacity(0.5); 
										tfTelefonoC.setDisable(true); tfTelefonoC.setOpacity(0.5);
										tfCorreoC.setDisable(true);	tfCorreoC.setOpacity(0.5);
										cbTarifa.setDisable(true); cbTarifa.setOpacity(0.5);
										bandClienteYaExiste = true;
										ContextoEncomienda.getInstance().setNuevoDestinatario(false);
										ContextoEncomienda.getInstance().setDestinatario(clienteList.get(0));
									}
									closeSesion(sesion3);
						 		}
						 	}else if (vv.length > 10)
						 		tfRIFCedulaC.setText(oldValue);
					 	}else{
					 		lEjemploCIR.setVisible(false);
					 		tfNombreC.setDisable(false);  tfNombreC.setOpacity(1); tfNombreC.setText("");
					 		tfApellidoC.setDisable(false); tfApellidoC.setOpacity(1); tfApellidoC.setText("");
					 		tfDireccionC.setDisable(false); tfDireccionC.setOpacity(1); tfDireccionC.setText("");
					 		tfTelefonoC.setDisable(false); tfTelefonoC.setOpacity(1); tfTelefonoC.setText("");	
					 		tfCorreoC.setDisable(false); tfCorreoC.setOpacity(1); tfCorreoC.setText("");
					 		cbTarifa.setDisable(false); cbTarifa.setOpacity(1); cbTarifa.getSelectionModel().select(-1);
					 		
					 	}
		         }else if (seleccionCBRifCedula == 2){		        	 
		        	 vv = newValue.toCharArray();
					 	if (newValue.compareTo("")!=0){
					 		if (vv.length == 1){	
					 			tipocedularif(vv,"juridico");					 		
						 	}else if ((vv.length > 2) && (vv.length <= 10)){	
						 		if ( (vv[vv.length-1] == '1') || (vv[vv.length-1] == '2') || (vv[vv.length-1] == '3')
						 				|| (vv[vv.length-1] == '4') || (vv[vv.length-1] == '5') || (vv[vv.length-1] == '6')
						 				|| (vv[vv.length-1] == '7') || (vv[vv.length-1] == '8') || (vv[vv.length-1] == '9')
						 				|| (vv[vv.length-1] == '0') ){
						 			tfRIFCedulaC.setText(newValue);	
						 			
						 			Session sesion2 = openSesion();
							 		
									if ((vv[0] == 'J') && (vv[1] == '-')){	
							 			queryResult = sesion2.createQuery("from Cliente where rifCedula="+newValue.substring(2)+"AND tipoRifCedula=\'jj\'");
										clienteList = FXCollections.observableArrayList(queryResult.list());
						 			}else if ((vv[0] == 'G') && (vv[1] == '-')){
							 			queryResult = sesion2.createQuery("from Cliente where rifCedula="+newValue.substring(2)+"AND tipoRifCedula=\'jg\'");
										clienteList = FXCollections.observableArrayList(queryResult.list());
						 			}	
									
									if (clienteList.size() > 0){
										System.out.println("hola  "+clienteList.get(0).getCorreo());
										tfNombreC.setText(clienteList.get(0).getNombre());
										tfApellidoC.setText(clienteList.get(0).getApellido());
										tfDireccionC.setText(clienteList.get(0).getDireccion());
										tfTelefonoC.setText(clienteList.get(0).getTelefono());
										tfCorreoC.setText(clienteList.get(0).getCorreo());

										for (int r=0;r<TarifaList.size();r++){
											if ( clienteList.get(0).getTarifa().getDescripcion().equals(TarifaList.get(r).getDescripcion()) ){
												cbTarifa.getSelectionModel().select(r);
												break;
											}
										}
										
										tfNombreC.setDisable(true);	tfNombreC.setOpacity(0.5);
										tfApellidoC.setDisable(true); tfApellidoC.setOpacity(0.5);
										tfDireccionC.setDisable(true); tfDireccionC.setOpacity(0.5); 
										tfTelefonoC.setDisable(true); tfTelefonoC.setOpacity(0.5);
										tfCorreoC.setDisable(true);	tfCorreoC.setOpacity(0.5);
										cbTarifa.setDisable(true); cbTarifa.setOpacity(0.5);
										bandClienteYaExiste = true;	
										ContextoEncomienda.getInstance().setNuevoDestinatario(false);
										ContextoEncomienda.getInstance().setDestinatario(clienteList.get(0));
									}	
									closeSesion(sesion2);
						 		}
						 	}else if (vv.length > 10)
						 		tfRIFCedulaC.setText(oldValue);
					 	}else{
					 		lEjemploCIR.setVisible(false);
					 		tfNombreC.setDisable(false);  tfNombreC.setOpacity(1); tfNombreC.setText("");
					 		tfApellidoC.setDisable(false); tfApellidoC.setOpacity(1); tfApellidoC.setText("");
					 		tfDireccionC.setDisable(false); tfDireccionC.setOpacity(1); tfDireccionC.setText("");
					 		tfTelefonoC.setDisable(false); tfTelefonoC.setOpacity(1); tfTelefonoC.setText("");
					 		tfCorreoC.setDisable(false); tfCorreoC.setOpacity(1); tfCorreoC.setText("");
					 		cbTarifa.setDisable(false); cbTarifa.setOpacity(1); cbTarifa.getSelectionModel().select(-1);
					 	}
		         }		    	 
		     }		     
		});
		
		tfNombreC.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
		    	lAlerta.setVisible(false);
			 	lEjemploCIR.setVisible(false);		 	
		    }
		});
		
		tfApellidoC.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
		    	lAlerta.setVisible(false);
			 	lEjemploCIR.setVisible(false);			 	
		    }
		});
		
		cbRIFCedulaC.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){			
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				tfRIFCedulaC.setText("");
				bandCB_rifced = true;
				lAlerta.setVisible(false);
			 	lEjemploCIR.setVisible(false);
			 	if (arg2.intValue() == 0){
					seleccionCBRifCedula = 1;
				}else if (arg2.intValue() == 1){
					seleccionCBRifCedula = 2;				
				}				
				tfRIFCedulaC.setDisable(false);tfRIFCedulaC.setOpacity(1);
		}});	
			
		tfTelefonoC.lengthProperty().addListener(new ChangeListener<Number>(){
		     @Override
		     public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {	           
		    	 lAlerta.setVisible(false);
				 lEjemploCIR.setVisible(false);  	 	
		     }		     
		});				
		
		tfDireccionC.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
		    	lAlerta.setVisible(false);
			 	lEjemploCIR.setVisible(false);			 	
		    }
		});			
		
		tfCorreoC.lengthProperty().addListener(new ChangeListener<Number>(){
		     @Override
		     public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {	           
		    	 lAlerta.setVisible(false);
				 lEjemploCIR.setVisible(false);  	 	
		     }		     
		});
		
		cbTarifa.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){			
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				lAlerta.setVisible(false);
			 	lEjemploCIR.setVisible(false);
		}});	
		
		tfNombreC.addEventHandler(javafx.scene.input.KeyEvent.KEY_TYPED, libreria.letraValidacion(30));
		tfApellidoC.addEventHandler(javafx.scene.input.KeyEvent.KEY_TYPED, libreria.letraValidacion(30));
		tfTelefonoC.addEventHandler(javafx.scene.input.KeyEvent.KEY_TYPED, libreria.telefonoValidacion(11));
		
		//tfNombreC.textProperty().addListener(libreria.mayuscula(tfNombreC));
	}
	
	private void tipocedularif(char [] v, String tipo){
		
		if ((v[0] == '1') || (v[0] == '2') || (v[0] == '3') || (v[0] == '4')
		  || (v[0] == '5') || (v[0] == '6') || (v[0] == '7') || (v[0] == '8')
		  || (v[0] == '9') || (v[0] == '0')){	
			if (tipo.compareTo("nopersona")==0)
				tfRIFCedulaC.setText("");
			else if (tipo.compareTo("juridico")==0)
				tfRIFCedulaC.setText("");
 			v = null;
 		}else if (v[0] == 'V'){	 			 			
 			if (tipo.compareTo("nopersona")==0){
				tfRIFCedulaC.setText("V-");
				v = tfRIFCedulaC.getText().toCharArray();
 			}else if (tipo.compareTo("juridico")==0){
				tfRIFCedulaC.setText("");
				v = tfRIFCedulaC.getText().toCharArray();
 			} 				
 		}else if (v[0] == 'E'){			
 			if (tipo.compareTo("nopersona")==0){
				tfRIFCedulaC.setText("E-");
				v = tfRIFCedulaC.getText().toCharArray();
 			}else if (tipo.compareTo("juridico")==0){
				tfRIFCedulaC.setText("");
				v = tfRIFCedulaC.getText().toCharArray();
 			} 
		}else if (v[0] == 'J'){ 
			if (tipo.compareTo("juridico")==0){
				tfRIFCedulaC.setText("J-");
				v = tfRIFCedulaC.getText().toCharArray();
 			}else if (tipo.compareTo("nopersona")==0){
				tfRIFCedulaC.setText("");
				v = tfRIFCedulaC.getText().toCharArray();
 			}
		}else if (v[0] == 'G'){ 
			if (tipo.compareTo("juridico")==0){
				tfRIFCedulaC.setText("G-");
				v = tfRIFCedulaC.getText().toCharArray();
 			}else if (tipo.compareTo("nopersona")==0){
				tfRIFCedulaC.setText("");
				v = tfRIFCedulaC.getText().toCharArray();
 			}
 			v = null;
 		}else{
 			if (tipo.compareTo("nopersona")==0)
				tfRIFCedulaC.setText("");
			else if (tipo.compareTo("juridico")==0)
				tfRIFCedulaC.setText("");
 			v = null;
 		} 			
	}	
	
	@FXML
	private void actionLimpiarPersona() throws Exception{
		System.out.println("limpiar persona");
	}
	
	@FXML
	private void actionLimpiarRD() throws Exception{
		System.out.println("limpiar RD");
		tfRIFCedulaC.setText("");
		tfNombreC.setText("");
		tfApellidoC.setText("");
		tfTelefonoC.setText("");
		tfDireccionC.setText("");	
		tfCorreoC.setText("");
		cbTarifa.getSelectionModel().select(-1);
	}
	
	@FXML
	private void actionBotonCancelar() throws Exception{
		System.out.println("Boton Cancelar");			
		Stage stage = (Stage) bCancelar.getScene().getWindow();
		stage.close();		
	}
	
	@FXML
	private void actionBotonProcesar() throws Exception{
		System.out.println("le doy click a procesar y chao  ");
		 ContextoEncomienda.getInstance().setBanderaRefresh(true);
		if (ContextoEncomienda.getInstance().getModoPago().compareTo("fletedestino") == 0){
			if ( (tfRIFCedulaC.getText().compareTo("") != 0) && 
					 (tfNombreC.getText().compareTo("") != 0) &&
					 (tfApellidoC.getText().compareTo("") != 0) && 
					 (tfTelefonoC.getText().compareTo("") != 0) &&
					 (tfDireccionC.getText().compareTo("") != 0) ){
						System.out.println("DEBO GUARDAR EN LA TABLA APARTE");
						if (ContextoEncomienda.getInstance().getNuevoDestinatario())
							guardarCliente();	
						Stage stage = (Stage) bProcesar.getScene().getWindow();
						stage.close();
			}else{
			    lAlerta.setVisible(true);
			    lAlerta.setText("Debe ingresar los campos necesarios");
			}
		}else if (ContextoEncomienda.getInstance().getModoPago().compareTo("cancelado") == 0){
			if ( (tfNombreC.getText().compareTo("") != 0) &&
					 (tfApellidoC.getText().compareTo("") != 0) && 
					 (tfTelefonoC.getText().compareTo("") != 0) &&
					 (tfDireccionC.getText().compareTo("") != 0) ){
						System.out.println("DEBO GUARDAR EN LOS CAMPOS EXTRAS DE TABLA FACTURA");
						if (ContextoEncomienda.getInstance().getNuevoDestinatario())
							guardarCliente();						
						Stage stage = (Stage) bProcesar.getScene().getWindow();
						stage.close();
				}else{
					    lAlerta.setVisible(true);
					    lAlerta.setText("Debe ingresar los campos necesarios");
				}
		}
	}
	
	private void guardarCliente(){
		ContextoEncomienda.getInstance().getDestinatario().setNombre(tfNombreC.getText());
		ContextoEncomienda.getInstance().getDestinatario().setApellido(tfApellidoC.getText());
		ContextoEncomienda.getInstance().getDestinatario().setTelefono(tfTelefonoC.getText());
		ContextoEncomienda.getInstance().getDestinatario().setDireccion(tfDireccionC.getText());
				
		if (tfRIFCedulaC.getText().compareTo("")!=0){
			vector = new char[10];
			vector = tfRIFCedulaC.getText().toCharArray();
			
			if (vector[0] == 'V'){
				ContextoEncomienda.getInstance().getDestinatario().setTipoRifCedula("nv");
			}else if (vector[0] == 'E'){
				ContextoEncomienda.getInstance().getDestinatario().setTipoRifCedula("ne");
			}else if (vector[0] == 'J'){
				ContextoEncomienda.getInstance().getDestinatario().setTipoRifCedula("jj");
			}else if (vector[0] == 'G'){
				ContextoEncomienda.getInstance().getDestinatario().setTipoRifCedula("jg");
			}
			String numcedula = tfRIFCedulaC.getText().substring(2);
			ContextoEncomienda.getInstance().getDestinatario().setRifCedula(Integer.parseInt(numcedula));
		}
		ContextoEncomienda.getInstance().getDestinatario().setOficina(OficinaList.get(0));
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
		 this.ProgramaPrincipal = ProgramaPrincipal;
	 }
	 
}
