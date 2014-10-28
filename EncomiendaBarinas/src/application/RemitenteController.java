package application;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import data.Cliente;
import data.Oficina;
import data.Tarifa;
import data.Temporal;
import data.TipoEmbalaje;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class RemitenteController {
		
	@FXML
	public Label linforRD;	
		
	@FXML
	public TextArea tfDireccionP;
	
	@FXML
	public TextField tfNombreP;
	
	@FXML
	public TextField tfApellidoP;
	
	@FXML
	public TextField tfTelefonoP;
	
	@FXML
	public TextField tfCedulaP;
	
	@FXML
	public TextField tfCorreoP;
		
	@FXML
	public ChoiceBox cbTarifaP;
	
	@FXML
	public TextField tfNombreC;
	
	@FXML
	public TextField tfApellidoC;
	
	@FXML
	public TextField tfTelefonoC;
	
	@FXML
	public TextArea tfDireccionC;
	
	@FXML
	public TextField tfCorreoC;
	
	@FXML
	public TextField tfRIFCedulaC;	
	
	@FXML
	public ChoiceBox cbRIFCedulaC;
	
	@FXML
	public ChoiceBox cbTarifaC;
	
	@FXML
	public Button bLimpiarRD;
	
	@FXML
	public Button bLimpiarP;
	
	@FXML
	public Button bCancelar;
	
	@FXML
	public Button bProcesar;
	
	@FXML
	public ImageView iFondoPersRemit;
	
	@FXML
	public Label lMsjPersRemit;
	
	@FXML
	public Button bSiPersRemit;
	
	@FXML
	public Button bNoPersRemit;	
	
	@FXML
	public Label lAlerta;
	
	@FXML
	public Label lEjemploCIR;	
	
	@FXML
	public Label lEjemploCIP;
	
	boolean bandCB_rifced = false;
	
	boolean bandMsjPregunta = false;
	
	boolean bandMsjSi = false;
	
	char[] vector;
	
	private ObservableList<Cliente> clientearrayUp = FXCollections.observableArrayList();
	private ObservableList<Cliente> clientearrayDown = FXCollections.observableArrayList();
	Query queryResult;
	
	char [] v,vv;
	
    int seleccionCBRifCedula = 0;
	
    Cliente objDArriba = new Cliente(), objDAbajo = new Cliente();    
    
    boolean bandDatosArribaNuevo = false, bandDatosAbajoNuevo = false;
    
    Query queryResultOficina;	
    Query queryResultTarifa;
    
    private ObservableList<Oficina> OficinaList = FXCollections.observableArrayList();
    private ObservableList<Tarifa> TarifaList = FXCollections.observableArrayList();
	           
    private ObservableList<String> opcionTarifa = FXCollections.observableArrayList();
        
    EncomiendaController ec = new EncomiendaController();
            
    private PruebaVentanas ProgramaPrincipal;
    
	@FXML
	private void initialize(){	
		
		try{						
			Session sesion1 = openSesion();
			queryResultOficina = sesion1.createQuery("from Oficina");
			OficinaList = FXCollections.observableArrayList(queryResultOficina.list());			
			
			queryResultTarifa = sesion1.createQuery("from Tarifa");
			TarifaList = FXCollections.observableArrayList(queryResultTarifa.list());
			for (int r=0;r<TarifaList.size();r++){
				opcionTarifa.add(TarifaList.get(r).getDescripcion());
			}
			cbTarifaC.setItems(opcionTarifa);
			cbTarifaP.setItems(opcionTarifa);
			closeSesion(sesion1);
		}catch(HibernateException e){
			e.printStackTrace();
		}		
			
		if ((ContextoEncomienda.getInstance().getRemitenteAbajo().getNombre()!=null) && (ContextoEncomienda.getInstance().getRemitenteArriba().getNombre()!=null)){
			System.out.println(ContextoEncomienda.getInstance().getRemitenteAbajo().getNombre());
			System.out.println(ContextoEncomienda.getInstance().getRemitAbajo());
			System.out.println(ContextoEncomienda.getInstance().getRemitenteArriba().getNombre());
			System.out.println(ContextoEncomienda.getInstance().getRemitArriba());
					
			tfCedulaP.setDisable(false);   tfCedulaP.setOpacity(1);
			tfNombreP.setDisable(true);	  tfNombreP.setOpacity(0.5);
			tfApellidoP.setDisable(true);	  tfApellidoP.setOpacity(0.5);	
			tfDireccionP.setDisable(true);   tfDireccionP.setOpacity(0.5);	
			tfTelefonoP.setDisable(true);    tfTelefonoP.setOpacity(0.5);
			tfCorreoP.setDisable(true);      tfCorreoP.setOpacity(0.5);
			cbTarifaP.setDisable(true);      cbTarifaP.setOpacity(0.5);
			
			cbRIFCedulaC.setDisable(false);   cbRIFCedulaC.setOpacity(0.5);
			tfRIFCedulaC.setDisable(true);   tfRIFCedulaC.setOpacity(0.5);
			tfNombreC.setDisable(true);	  tfNombreC.setOpacity(0.5);
			tfApellidoC.setDisable(true);	  tfApellidoC.setOpacity(0.5);	
			tfDireccionC.setDisable(true);   tfDireccionC.setOpacity(0.5);	
			tfTelefonoC.setDisable(true);    tfTelefonoC.setOpacity(0.5);
			tfCorreoC.setDisable(true);      tfCorreoC.setOpacity(0.5);
			cbTarifaC.setDisable(true);      cbTarifaC.setOpacity(0.5);

			if (ContextoEncomienda.getInstance().getRemitenteArriba().getTipoRifCedula().compareTo("nv")==0){
				tfCedulaP.setText("V-");
			}else if (ContextoEncomienda.getInstance().getRemitenteArriba().getTipoRifCedula().compareTo("ne")==0){
				tfCedulaP.setText("E-");
			}			
			
			tfCedulaP.setText(tfCedulaP.getText()+String.valueOf(ContextoEncomienda.getInstance().getRemitenteArriba().getRifCedula()));
			tfNombreP.setText(ContextoEncomienda.getInstance().getRemitenteArriba().getNombre());
			tfApellidoP.setText(ContextoEncomienda.getInstance().getRemitenteArriba().getApellido());
			tfDireccionP.setText(ContextoEncomienda.getInstance().getRemitenteArriba().getDireccion());
			tfTelefonoP.setText(ContextoEncomienda.getInstance().getRemitenteArriba().getTelefono());
			tfCorreoP.setText(ContextoEncomienda.getInstance().getRemitenteArriba().getCorreo());
			
			for (int r=0;r<TarifaList.size();r++){
				if ( ContextoEncomienda.getInstance().getRemitenteArriba().getTarifa().getDescripcion().equals(TarifaList.get(r).getDescripcion()) ){
					cbTarifaP.getSelectionModel().select(r);
					break;
				}
			}
			
			if (ContextoEncomienda.getInstance().getRemitenteAbajo().getTipoRifCedula().compareTo("nv")==0){
				cbRIFCedulaC.getSelectionModel().select(0);	
				tfRIFCedulaC.setText("V-");
			}else if (ContextoEncomienda.getInstance().getRemitenteAbajo().getTipoRifCedula().compareTo("ne")==0){
				cbRIFCedulaC.getSelectionModel().select(0);
				tfRIFCedulaC.setText("E-");
			}else if (ContextoEncomienda.getInstance().getRemitenteAbajo().getTipoRifCedula().compareTo("jj")==0){
				cbRIFCedulaC.getSelectionModel().select(1);
				tfRIFCedulaC.setText("J-");
			}else if (ContextoEncomienda.getInstance().getRemitenteAbajo().getTipoRifCedula().compareTo("jg")==0){
				cbRIFCedulaC.getSelectionModel().select(1);
				tfRIFCedulaC.setText("G-");
			}			
			
			tfRIFCedulaC.setText(tfRIFCedulaC.getText()+String.valueOf(ContextoEncomienda.getInstance().getRemitenteAbajo().getRifCedula()));
			tfNombreC.setText(ContextoEncomienda.getInstance().getRemitenteAbajo().getNombre());
			tfApellidoC.setText(ContextoEncomienda.getInstance().getRemitenteAbajo().getApellido());
			tfDireccionC.setText(ContextoEncomienda.getInstance().getRemitenteAbajo().getDireccion());
			tfTelefonoC.setText(ContextoEncomienda.getInstance().getRemitenteAbajo().getTelefono());
			tfCorreoC.setText(ContextoEncomienda.getInstance().getRemitenteAbajo().getCorreo());
			
			for (int r=0;r<TarifaList.size();r++){
				if (ContextoEncomienda.getInstance().getRemitenteAbajo().getTarifa().getDescripcion().equals(TarifaList.get(r).getDescripcion()) ){
					cbTarifaC.getSelectionModel().select(r);
					break;
				}
			}
			bProcesar.setDisable(false); bProcesar.setOpacity(1);
		}

		tfCedulaP.textProperty().addListener(new ChangeListener<String>(){
			 @Override
			    public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {			    	 
				
				 if (ContextoEncomienda.getInstance().getRemitArriba().compareTo("consulta")==0){
						tfNombreP.setDisable(false);	  tfNombreP.setOpacity(1);
						tfApellidoP.setDisable(false);	  tfApellidoP.setOpacity(1);	
						tfDireccionP.setDisable(false);   tfDireccionP.setOpacity(1);	
						tfTelefonoP.setDisable(false);    tfTelefonoP.setOpacity(1);
						tfCorreoP.setDisable(false);      tfCorreoP.setOpacity(1);
						cbTarifaP.setDisable(false);      cbTarifaP.setOpacity(1);
						
						tfNombreP.setText("");		tfApellidoP.setText("");
						tfDireccionP.setText("");	tfTelefonoP.setText("");
						tfCorreoP.setText("");
						cbTarifaP.getSelectionModel().select(-1);
				 }
				 
				 bProcesar.setDisable(false);bProcesar.setOpacity(1);		 	
				 	lEjemploCIP.setVisible(true);
				 	lEjemploCIR.setVisible(false);			 	
				 	v = newValue.toCharArray();
				 	if (newValue.compareTo("")!=0){				 	
				 		
				 		if (v.length == 1){	
				 			tipocedularif(v,"persona");					 		
					 	}else if ((v.length > 2) && (v.length <= 10)){	
					 		if ( (v[v.length-1] == '1') || (v[v.length-1] == '2') || (v[v.length-1] == '3')
					 				|| (v[v.length-1] == '4') || (v[v.length-1] == '5') || (v[v.length-1] == '6')
					 				|| (v[v.length-1] == '7') || (v[v.length-1] == '8') || (v[v.length-1] == '9')
					 				|| (v[v.length-1] == '0') ){
					 			tfCedulaP.setText(newValue);					 			
					 			Session sesion1 = Main.sesionFactory.getCurrentSession();
								sesion1.beginTransaction();
						 		
								if ((v[0] == 'V') && (v[1] == '-')){	
						 			queryResult = sesion1.createQuery("from Cliente where rifCedula="+newValue.substring(2)+"AND tipoRifCedula=\'nv\'");
									clientearrayUp = FXCollections.observableArrayList(queryResult.list());
					 			}else if ((v[0] == 'E') && (v[1] == '-')){
						 			queryResult = sesion1.createQuery("from Cliente where rifCedula="+newValue.substring(2)+"AND tipoRifCedula=\'ne\'");
									clientearrayUp = FXCollections.observableArrayList(queryResult.list());
					 			}	
								
								if (clientearrayUp.size() > 0){
									tfNombreP.setText(clientearrayUp.get(0).getNombre());
									tfApellidoP.setText(clientearrayUp.get(0).getApellido());
									tfDireccionP.setText(clientearrayUp.get(0).getDireccion());
									tfTelefonoP.setText(clientearrayUp.get(0).getTelefono());
									tfCorreoP.setText(clientearrayUp.get(0).getCorreo());
									for (int r=0;r<TarifaList.size();r++){
										if ( clientearrayUp.get(0).getTarifa().getDescripcion().equals(TarifaList.get(r).getDescripcion()) ){
											cbTarifaP.getSelectionModel().select(r);
											break;
										}
									}									
									bandDatosArribaNuevo = false;									
								}else{
									System.out.println("Ingreso nuevo Datos persona: ");
									tfNombreP.setText("");
									tfApellidoP.setText("");
									tfDireccionP.setText("");
									tfTelefonoP.setText("");
									tfCorreoP.setText("");
									cbTarifaP.getSelectionModel().select(-1);
									bandDatosArribaNuevo = true;
								}						
								sesion1.getTransaction().commit();
					 		}
					 	}else if (v.length > 10)
					 		tfCedulaP.setText(oldValue);				 							
				 	}else
				 		lEjemploCIP.setVisible(false);  	 
			 }			
		});
		
		tfNombreP.textProperty().addListener(new ChangeListener<String>(){
			 @Override
			    public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
				 	bProcesar.setDisable(false);bProcesar.setOpacity(1);
				 	lEjemploCIP.setVisible(false);
				 	lEjemploCIR.setVisible(false);
			    }	
		});
		
		tfApellidoP.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
		    	lAlerta.setVisible(false);
		    	lEjemploCIP.setVisible(false);
			 	lEjemploCIR.setVisible(false);
		    }
		});
		
		tfTelefonoP.textProperty().addListener(new ChangeListener<String>(){			
			 @Override
			    public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
				 	bProcesar.setDisable(false);bProcesar.setOpacity(1);
				 	lEjemploCIP.setVisible(false);
				 	lEjemploCIR.setVisible(false);
			    }			
		});
		
		tfCorreoP.textProperty().addListener(new ChangeListener<String>(){			
			 @Override
			    public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
				 	bProcesar.setDisable(false);bProcesar.setOpacity(1);
				 	lEjemploCIP.setVisible(false);
				 	lEjemploCIR.setVisible(false);
			    }			
		});
		
		tfDireccionP.textProperty().addListener(new ChangeListener<String>(){
			 @Override
			    public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
				 bProcesar.setDisable(false);bProcesar.setOpacity(1);
				 lEjemploCIP.setVisible(false);
				 lEjemploCIR.setVisible(false);
			    }		
		});		
		
		tfTelefonoP.lengthProperty().addListener(new ChangeListener<Number>(){
		     @Override
		     public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {              
		    	 	lEjemploCIP.setVisible(false);
				 	lEjemploCIR.setVisible(false);
		           if(newValue.intValue() > oldValue.intValue()){
		               char ch = tfTelefonoP.getText().charAt(oldValue.intValue());
		               if(!(ch >= '0' && ch <= '9' )){
		            	   tfTelefonoP.setText(tfTelefonoP.getText().substring(0,tfTelefonoP.getText().length()-1)); 
		               }
		          }
		     }		     
		});
		//----------------------------------------------------------------------
						
		tfRIFCedulaC.textProperty().addListener(new ChangeListener<String>(){
			 @Override
			    public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
				 				 
				 lAlerta.setVisible(false);
				 lEjemploCIP.setVisible(false);
		    	 lEjemploCIR.setVisible(true);
		    	 if (seleccionCBRifCedula == 1){
		        	 System.out.println("cédula de parte abajo NATURAL");
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
						 			Session sesion1 = Main.sesionFactory.getCurrentSession();
									sesion1.beginTransaction();
							 		
									if ((vv[0] == 'V') && (vv[1] == '-')){
							 			queryResult = sesion1.createQuery("from Cliente where rifCedula="+newValue.substring(2)+"AND tipoRifCedula=\'nv\'");
										clientearrayDown = FXCollections.observableArrayList(queryResult.list());
						 			}else if ((vv[0] == 'E') && (vv[1] == '-')){
							 			queryResult = sesion1.createQuery("from Cliente where rifCedula="+newValue.substring(2)+"AND tipoRifCedula=\'ne\'");
										clientearrayDown = FXCollections.observableArrayList(queryResult.list());
						 			}	
									
									if (clientearrayDown.size() > 0){
										tfNombreC.setText(clientearrayDown.get(0).getNombre());
										tfApellidoC.setText(clientearrayDown.get(0).getApellido());
										tfDireccionC.setText(clientearrayDown.get(0).getDireccion());
										tfTelefonoC.setText(clientearrayDown.get(0).getTelefono());
										tfCorreoC.setText(clientearrayDown.get(0).getCorreo());
										for (int r=0;r<TarifaList.size();r++){
											if ( clientearrayDown.get(0).getTarifa().getDescripcion().equals(TarifaList.get(r).getDescripcion()) ){
												cbTarifaC.getSelectionModel().select(r);
												break;
											}
										}
										bandDatosAbajoNuevo = false;
									}else{
										tfNombreC.setText("");
										tfApellidoC.setText("");
										tfDireccionC.setText("");
										tfTelefonoC.setText("");
										tfCorreoC.setText("");
										cbTarifaC.getSelectionModel().select(-1);
										bandDatosAbajoNuevo = true;
									}						
									sesion1.getTransaction().commit();
						 		}
						 	}else if (vv.length > 10)
						 		tfRIFCedulaC.setText(oldValue);
					 	}else
					 		lEjemploCIR.setVisible(false);
		         }else if (seleccionCBRifCedula == 2){
		        	 System.out.println("cédula de parte abajo JURIDICO");
		        	 
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
						 			Session sesion1 = Main.sesionFactory.getCurrentSession();
									sesion1.beginTransaction();
							 		
									if ((vv[0] == 'J') && (vv[1] == '-')){	
							 			queryResult = sesion1.createQuery("from Cliente where rifCedula="+newValue.substring(2)+"AND tipoRifCedula=\'jj\'");
										clientearrayDown = FXCollections.observableArrayList(queryResult.list());
						 			}else if ((vv[0] == 'G') && (vv[1] == '-')){
							 			queryResult = sesion1.createQuery("from Cliente where rifCedula="+newValue.substring(2)+"AND tipoRifCedula=\'jg\'");
										clientearrayDown = FXCollections.observableArrayList(queryResult.list());
						 			}	
									
									if (clientearrayDown.size() > 0){
										tfNombreC.setText(clientearrayDown.get(0).getNombre());
										tfApellidoC.setText(clientearrayDown.get(0).getApellido());
										tfDireccionC.setText(clientearrayDown.get(0).getDireccion());
										tfTelefonoC.setText(clientearrayDown.get(0).getTelefono());
										tfCorreoC.setText(clientearrayDown.get(0).getCorreo());
										for (int r=0;r<TarifaList.size();r++){
											if ( clientearrayDown.get(0).getTarifa().getDescripcion().equals(TarifaList.get(r).getDescripcion()) ){
												cbTarifaC.getSelectionModel().select(r);
												break;
											}
										}
										bandDatosAbajoNuevo = false;
									}else{
										tfNombreC.setText("");
										tfApellidoC.setText("");
										tfDireccionC.setText("");
										tfTelefonoC.setText("");
										tfCorreoC.setText("");
										cbTarifaC.getSelectionModel().select(-1);
										bandDatosAbajoNuevo = true;
									}						
									sesion1.getTransaction().commit();
						 		}
						 	}else if (vv.length > 10)
						 		tfRIFCedulaC.setText(oldValue);
					 	}else
					 		lEjemploCIR.setVisible(false);
		        	 
		         }	
			 }
		});
		
		tfNombreC.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
		    	lAlerta.setVisible(false);
		    	lEjemploCIP.setVisible(false);
			 	lEjemploCIR.setVisible(false);
		    }
		});
		
		tfApellidoC.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
		    	lAlerta.setVisible(false);
		    	lEjemploCIP.setVisible(false);
			 	lEjemploCIR.setVisible(false);
		    }
		});
		
		cbRIFCedulaC.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){			
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {		
				
				if (ContextoEncomienda.getInstance().getRemitAbajo().compareTo("consulta")==0){
					 tfNombreC.setDisable(false);	  tfNombreC.setOpacity(1);
					 tfApellidoC.setDisable(false);	  tfApellidoC.setOpacity(1);	
					 tfDireccionC.setDisable(false);   tfDireccionC.setOpacity(1);	
					 tfTelefonoC.setDisable(false);    tfTelefonoC.setOpacity(1);
					 tfCorreoC.setDisable(false);    tfCorreoC.setOpacity(1);
					 cbTarifaC.setDisable(false);    cbTarifaC.setOpacity(1);
				 }
				
				bandCB_rifced = true;
				lAlerta.setVisible(false);
				lEjemploCIP.setVisible(false);
			 	lEjemploCIR.setVisible(false);
				if (arg2.intValue() == 0){
					seleccionCBRifCedula = 1;
					System.out.println("Natural");
				}else if (arg2.intValue() == 1){
					seleccionCBRifCedula = 2;
					System.out.println("Jurídico");					
				}		
				tfRIFCedulaC.setText("");
				tfNombreC.setText("");
				tfApellidoC.setText("");
				tfDireccionC.setText("");
				tfTelefonoC.setText("");
				tfCorreoC.setText("");
				cbTarifaC.getSelectionModel().select(-1);
				tfRIFCedulaC.setDisable(false);tfRIFCedulaC.setOpacity(1);
		}});		
				
		tfTelefonoC.lengthProperty().addListener(new ChangeListener<Number>(){
		     @Override
		     public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {	           
		    	 lAlerta.setVisible(false);
		    	 lEjemploCIP.setVisible(false);
				 lEjemploCIR.setVisible(false);		    	 
		     }		     
		});				
				
		tfCorreoC.textProperty().addListener(new ChangeListener<String>(){			
			 @Override
			    public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
				 	bProcesar.setDisable(false);bProcesar.setOpacity(1);
				 	lEjemploCIP.setVisible(false);
				 	lEjemploCIR.setVisible(false);
			    }			
		});
		
		tfDireccionC.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
		    	lAlerta.setVisible(false);
		    	lEjemploCIP.setVisible(false);
			 	lEjemploCIR.setVisible(false);
		    }
		});
		
		tfNombreP.addEventHandler(javafx.scene.input.KeyEvent.KEY_TYPED, libreria.letraValidacion(30));
		tfApellidoP.addEventHandler(javafx.scene.input.KeyEvent.KEY_TYPED, libreria.letraValidacion(30));
		tfTelefonoP.addEventHandler(javafx.scene.input.KeyEvent.KEY_TYPED, libreria.telefonoValidacion(11));
		
		tfNombreC.addEventHandler(javafx.scene.input.KeyEvent.KEY_TYPED, libreria.letraValidacion(30));
		tfApellidoC.addEventHandler(javafx.scene.input.KeyEvent.KEY_TYPED, libreria.letraValidacion(30));
		tfTelefonoC.addEventHandler(javafx.scene.input.KeyEvent.KEY_TYPED, libreria.telefonoValidacion(11));		
	}
	
	private void tipocedularif(char [] v, String tipo){		
		if ((v[0] == '1') || (v[0] == '2') || (v[0] == '3') || (v[0] == '4')
		  || (v[0] == '5') || (v[0] == '6') || (v[0] == '7') || (v[0] == '8')
		  || (v[0] == '9') || (v[0] == '0')){
			if (tipo.compareTo("persona")==0)
				tfCedulaP.setText("");
			else if (tipo.compareTo("nopersona")==0)
				tfRIFCedulaC.setText("");
			else if (tipo.compareTo("juridico")==0)
				tfRIFCedulaC.setText("");
 			v = null;
 		}else if (v[0] == 'V'){	
 			if (tipo.compareTo("persona")==0){
				tfCedulaP.setText("V-");
 				v = tfCedulaP.getText().toCharArray();
 			}else if (tipo.compareTo("nopersona")==0){
				tfRIFCedulaC.setText("V-");
				v = tfRIFCedulaC.getText().toCharArray();
 			}else if (tipo.compareTo("juridico")==0){
				tfRIFCedulaC.setText("");
				v = tfRIFCedulaC.getText().toCharArray();
 			} 				
 		}else if (v[0] == 'E'){ 
 			if (tipo.compareTo("persona")==0){
				tfCedulaP.setText("E-");
 				v = tfCedulaP.getText().toCharArray();
 			}else if (tipo.compareTo("nopersona")==0){
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
 			}else if (tipo.compareTo("persona")==0){
				tfCedulaP.setText("");
 				v = tfCedulaP.getText().toCharArray();
 			}else if (tipo.compareTo("nopersona")==0){
				tfRIFCedulaC.setText("");
				v = tfRIFCedulaC.getText().toCharArray();
 			}
		}else if (v[0] == 'G'){ 
			if (tipo.compareTo("juridico")==0){
				tfRIFCedulaC.setText("G-");
				v = tfRIFCedulaC.getText().toCharArray();
 			}else if (tipo.compareTo("persona")==0){
				tfCedulaP.setText("");
 				v = tfCedulaP.getText().toCharArray();
 			}else if (tipo.compareTo("nopersona")==0){
				tfRIFCedulaC.setText("");
				v = tfRIFCedulaC.getText().toCharArray();
 			}
 			v = null;
 		}else{
 			if (tipo.compareTo("persona")==0)
				tfCedulaP.setText("");
			else if (tipo.compareTo("nopersona")==0)
				tfRIFCedulaC.setText("");
			else if (tipo.compareTo("juridico")==0)
				tfRIFCedulaC.setText("");
 			v = null;
 		} 			
	}
	
	@FXML
	private void actionSiPersRemit(){
		bandMsjSi = true;
		iFondoPersRemit.setVisible(false);					
		lMsjPersRemit.setVisible(false);					
		bSiPersRemit.setVisible(false);					
		bNoPersRemit.setVisible(false);
		
		//COPIO LO DE PERSONA EN REMITENTE
		cbRIFCedulaC.getSelectionModel().select(0);
		tfRIFCedulaC.setText(tfCedulaP.getText());
		tfNombreC.setText(tfNombreP.getText());
		tfApellidoC.setText(tfApellidoP.getText());
		tfTelefonoC.setText(tfTelefonoP.getText());
		tfDireccionC.setText(tfDireccionP.getText());
		tfCorreoC.setText(tfCorreoP.getText());
				
		//DESACTIVO CAMPOS DE PERSONA Y REMITENTE
		cbRIFCedulaC.setOpacity(1);
		tfRIFCedulaC.setOpacity(1);
		tfNombreC.setOpacity(1);
		tfApellidoC.setOpacity(1);
		tfTelefonoC.setOpacity(1);
		tfDireccionC.setOpacity(1);
		tfCorreoC.setOpacity(1);
		cbTarifaC.setOpacity(1);
		tfCedulaP.setOpacity(0.5);
		tfNombreP.setOpacity(0.5);
		tfApellidoP.setOpacity(0.5);
		tfTelefonoP.setOpacity(0.5);
		tfDireccionP.setOpacity(0.5);
		tfCorreoP.setOpacity(0.5);
		cbTarifaP.setOpacity(0.5);
		
		//DESACTIVO BOTONES DE LIMPIEZA
		bLimpiarP.setDisable(true); bLimpiarP.setOpacity(0.5);
		bLimpiarRD.setDisable(true); bLimpiarRD.setOpacity(0.5);
		
		bProcesar.setDisable(false);bProcesar.setOpacity(1);	
	}
	
	@FXML
	private void actionNoPersRemit(){		
		bandMsjSi = false;		
		iFondoPersRemit.setVisible(false);					
		lMsjPersRemit.setVisible(false);					
		bSiPersRemit.setVisible(false);					
		bNoPersRemit.setVisible(false);
		
		//DESACTIVO PERSONA Y COPIO
		tfCedulaP.setDisable(true);tfCedulaP.setOpacity(0.5);
		tfNombreP.setDisable(true);tfNombreP.setOpacity(0.5);
		tfApellidoP.setDisable(true);tfApellidoP.setOpacity(0.5);
		tfDireccionP.setDisable(true);tfDireccionP.setOpacity(0.5);
		tfTelefonoP.setDisable(true);tfTelefonoP.setOpacity(0.5);
		tfCorreoP.setDisable(true);tfCorreoP.setOpacity(0.5);
		cbTarifaP.setDisable(true);cbTarifaP.setOpacity(0.5);
		bLimpiarP.setDisable(true);bLimpiarP.setOpacity(0.5);
		
		//ACTIVO CAMPOS DE REMITENTE
		tfRIFCedulaC.setDisable(true);tfRIFCedulaC.setOpacity(1);
		tfNombreC.setDisable(false);tfNombreC.setOpacity(1);
		tfApellidoC.setDisable(false);tfApellidoC.setOpacity(1);
		tfTelefonoC.setDisable(false);tfTelefonoC.setOpacity(1);
		tfDireccionC.setDisable(false);tfDireccionC.setOpacity(1);
		tfCorreoC.setDisable(false);tfCorreoC.setOpacity(1);
		cbRIFCedulaC.setDisable(false);cbRIFCedulaC.setOpacity(1);
		cbTarifaC.setDisable(false);cbTarifaC.setOpacity(1);
		bLimpiarRD.setDisable(false);bLimpiarRD.setOpacity(1);
		
		bCancelar.setDisable(false);bCancelar.setOpacity(1);
	}
		
	@FXML
	private void actionLimpiarPersona() throws Exception{
		tfCedulaP.setText("");
		tfNombreP.setText("");
		tfApellidoP.setText("");
		tfTelefonoP.setText("");
		tfDireccionP.setText("");
	}
	
	@FXML
	private void actionLimpiarRD() throws Exception{
		tfRIFCedulaC.setText("");
		tfNombreC.setText("");
		tfApellidoC.setText("");
		tfTelefonoC.setText("");
		tfDireccionC.setText("");		
	}
	
	@FXML
	private void actionBotonProcesar() throws Exception{
	
			if (bandMsjPregunta == false){
				if ((tfCedulaP.getText().compareTo("")!=0) && (tfNombreP.getText().compareTo("")!=0) && (tfApellidoP.getText().compareTo("")!=0)
					&& (tfTelefonoP.getText().compareTo("")!=0) && (tfDireccionP.getText().compareTo("")!=0) ){
						iFondoPersRemit.setVisible(true);					
						lMsjPersRemit.setVisible(true);					
						bSiPersRemit.setVisible(true);					
						bNoPersRemit.setVisible(true);
						bandMsjPregunta = true;
				}			
			}else{
				ContextoEncomienda.getInstance().setBanderaRefresh(true);
				//SI COPIO DATOS
				if (bandMsjSi == true){
					//NUEVO ARRIBA, COPIO ABAJO
					if (bandDatosArribaNuevo){
						guardarClienteUp();
						objDAbajo = objDArriba;						
						ContextoEncomienda.getInstance().setRemitenteArriba(objDArriba);
						ContextoEncomienda.getInstance().setRemitenteAbajo(objDArriba);
						System.out.println("NUEVO ARRIBA - COPIO ABAJO");
						Stage stage = (Stage) bProcesar.getScene().getWindow();
						stage.close();
						ContextoEncomienda.getInstance().setRemitArriba("nuevo");
						ContextoEncomienda.getInstance().setRemitAbajo("copia");						
					//GUARDADO ARRIBA, COPIO ABAJO
					}else if (!bandDatosArribaNuevo){
						objDArriba = clientearrayUp.get(0);
						objDAbajo = objDArriba;
						System.out.println("GUARDADO ARRIBA - COPIO ABAJO");
						ContextoEncomienda.getInstance().setRemitenteArriba(objDArriba);
						ContextoEncomienda.getInstance().setRemitenteAbajo(objDArriba);
						ContextoEncomienda.getInstance().setRemitArriba("consulta");
						ContextoEncomienda.getInstance().setRemitAbajo("copia");
						Stage stage = (Stage) bProcesar.getScene().getWindow();
						stage.close();
					}				
				//NO COPIO DATOS
				}else{
					//VALIDO QUE LOS CAMPOS DE REMITENTE ESTEN LLENOS
					if ((tfRIFCedulaC.getText().compareTo("")!=0) && (tfNombreC.getText().compareTo("")!=0) && (tfApellidoC.getText().compareTo("")!=0) &&
						(tfTelefonoC.getText().compareTo("")!=0) && (tfDireccionC.getText().compareTo("")!=0)){
						lAlerta.setVisible(false);
						
						if (bandDatosAbajoNuevo){							
							guardarClienteDown();						
							if (bandDatosArribaNuevo){
								guardarClienteUp();
								System.out.println("NUEVO ARRIBA - NUEVO ABAJO");
								ContextoEncomienda.getInstance().setRemitArriba("nuevo");
								ContextoEncomienda.getInstance().setRemitAbajo("nuevo");
								ContextoEncomienda.getInstance().setRemitenteArriba(objDArriba);
								ContextoEncomienda.getInstance().setRemitenteAbajo(objDAbajo);
								Stage stage = (Stage) bProcesar.getScene().getWindow();
								stage.close();							
							}else if (!bandDatosArribaNuevo){
								objDArriba = clientearrayUp.get(0);
								System.out.println("CONSULTA ARRIBA - NUEVO ABAJO");
								ContextoEncomienda.getInstance().setRemitArriba("consulta");
								ContextoEncomienda.getInstance().setRemitAbajo("nuevo");
								ContextoEncomienda.getInstance().setRemitenteArriba(objDArriba);
								ContextoEncomienda.getInstance().setRemitenteAbajo(objDAbajo);
								Stage stage = (Stage) bProcesar.getScene().getWindow();
								stage.close();							
							}			
						}else if (!bandDatosAbajoNuevo){
							objDAbajo = clientearrayDown.get(0);
							if (bandDatosArribaNuevo){
								guardarClienteUp();
								System.out.println("NUEVO ARRIBA - GUARDADO ABAJO");
								ContextoEncomienda.getInstance().setRemitArriba("nuevo");
								ContextoEncomienda.getInstance().setRemitAbajo("consulta");
								ContextoEncomienda.getInstance().setRemitenteArriba(objDArriba);
								ContextoEncomienda.getInstance().setRemitenteAbajo(objDAbajo);
								Stage stage = (Stage) bProcesar.getScene().getWindow();
								stage.close();
							}else if (!bandDatosArribaNuevo){
								objDArriba = clientearrayUp.get(0);
								System.out.println("CONSULTA ARRIBA - GUARDADO ABAJO");
								ContextoEncomienda.getInstance().setRemitArriba("consulta");
								ContextoEncomienda.getInstance().setRemitAbajo("consulta");
								ContextoEncomienda.getInstance().setRemitenteArriba(objDArriba);
								ContextoEncomienda.getInstance().setRemitenteAbajo(objDAbajo);
								Stage stage = (Stage) bProcesar.getScene().getWindow();
								stage.close();
							}
						}
					}else{
						lAlerta.setVisible(true);
					}
				}		
			}		
	}	
		
	private void guardarClienteDown(){

		objDAbajo.setNombre(tfNombreC.getText());
		objDAbajo.setApellido(tfApellidoC.getText());
		objDAbajo.setDireccion(tfDireccionC.getText());
		objDAbajo.setTelefono(tfTelefonoC.getText());
		objDAbajo.setCorreo("prueba@gmail.com");
		
		vector = null;
		vector = new char[10];
		vector = tfRIFCedulaC.getText().toCharArray();
		
		if (vector[0] == 'V'){
			objDAbajo.setTipoRifCedula("nv");
		}else if (vector[0] == 'E'){
			objDAbajo.setTipoRifCedula("ne");
		}else if (vector[0] == 'J'){
			objDAbajo.setTipoRifCedula("jj");
		}else if (vector[0] == 'G'){
			objDAbajo.setTipoRifCedula("jg");
		}

		String numcedula = tfRIFCedulaC.getText().substring(2);
		objDAbajo.setRifCedula(Integer.parseInt(numcedula));
		objDAbajo.setOficina(OficinaList.get(0));
	}
	
	private void guardarClienteUp(){		
				
		objDArriba.setNombre(tfNombreP.getText());
		objDArriba.setApellido(tfApellidoP.getText());
		objDArriba.setDireccion(tfDireccionP.getText());
		objDArriba.setTelefono(tfTelefonoP.getText());
		objDArriba.setCorreo("prueba@gmail.com");
				
		vector = new char[10];
		vector = tfCedulaP.getText().toCharArray();
		
		if (vector[0] == 'V'){
			objDArriba.setTipoRifCedula("nv");
		}else if (vector[0] == 'E'){
			objDArriba.setTipoRifCedula("ne");
		}
		
		String numcedula = tfCedulaP.getText().substring(2);
		objDArriba.setRifCedula(Integer.parseInt(numcedula));
		objDArriba.setOficina(OficinaList.get(0));
	} 
	
	@FXML
	private void actionBotonCancelar() throws Exception{
		System.out.println("Boton Cancelar");			
		Stage stage = (Stage) bCancelar.getScene().getWindow();
		stage.close();		
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
