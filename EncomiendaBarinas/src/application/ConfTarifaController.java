package application;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import data.CiudadDestino;
import data.Tarifa;
import data.TipoEmbalaje;
import data.Unidad;
import data.VariableConfiguracion;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ConfTarifaController {
	
	@FXML
	private TextField tfNombre;
	
	@FXML
	private Label lAlerta;
	
	@FXML
	private Label lNombre;
	
	@FXML
	private Label Nombrecb;
	
	@FXML
	private ChoiceBox cbTarifa;
	
	@FXML
	private TextField tfFactor;
	
	@FXML
	private Button bAceptar;
	
	@FXML
	private Stage stageI;
	
	private Tarifa objTarifa;
	private TipoEmbalaje objTipoEmbalaje;
	private CiudadDestino objCiudadDestino;
	private Unidad objUnidad;
	private VariableConfiguracion objVariable;
	
	private ObservableList<Tarifa> TarifaList = FXCollections.observableArrayList(); 
	private ObservableList<String> opcionTarifa = FXCollections.observableArrayList();
	
	Query queryResultTarifa;
	Query queryVariableConfiguracion;
	
	VentanaConfComisiones ventComisiones;
	
	@FXML
	private void initialize(){	
		
		if (ContextoEncomienda.getInstance().getIdentificadorVentana().equals("CIUDAD DESTINO")){
			System.out.println("work con ciudad destino");
			lNombre.setText("Ciudad Destino");
			cbTarifa.setVisible(false);
			Nombrecb.setVisible(false);
			tfFactor.setVisible(false);
		}else if (ContextoEncomienda.getInstance().getIdentificadorVentana().equals("TIPO EMBALAJE")){
			System.out.println("work con tipo embalaje");
			lNombre.setText("Tipo Embalaje");
			try{						
				Session sesion1 = openSesion();
				queryResultTarifa = sesion1.createQuery("from Tarifa");
				TarifaList = FXCollections.observableArrayList(queryResultTarifa.list());
				
				for (int r=0;r<TarifaList.size();r++){
					opcionTarifa.add(TarifaList.get(r).getDescripcion());
				}
				
				cbTarifa.setItems(opcionTarifa);
				cbTarifa.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){			
					@Override
					public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
						System.out.println(opcionTarifa.get(arg2.intValue()));						
						if (tfNombre.getText().compareTo("") != 0){
			    			bAceptar.setDisable(false);
			    		}else if (tfNombre.getText().compareTo("") == 0){
			    			bAceptar.setDisable(true);
			    		}
				}});
				cbTarifa.setVisible(true);
				Nombrecb.setVisible(true);
				Nombrecb.setText("Tarifa");
				tfFactor.setVisible(false);
				closeSesion(sesion1);
			}catch(HibernateException e){
				e.printStackTrace();
			}
		}else if (ContextoEncomienda.getInstance().getIdentificadorVentana().equals("TARIFA")){
			System.out.println("work con tarifa");
			lNombre.setText("Tarifa");
			cbTarifa.setVisible(false);
			Nombrecb.setVisible(false);
			tfFactor.setVisible(false);
		}else if (ContextoEncomienda.getInstance().getIdentificadorVentana().equals("UNIDAD")){
			System.out.println("work con unidad");
			lNombre.setText("Unidad");
			cbTarifa.setVisible(false);
			Nombrecb.setVisible(true);
			Nombrecb.setText("Factor");
			tfFactor.setVisible(true);
		}else if (ContextoEncomienda.getInstance().getIdentificadorVentana().equals("VARIABLE")){
			System.out.println("work con variable");
			lNombre.setText("Variable");
			cbTarifa.setVisible(false);
			Nombrecb.setVisible(false);
			tfFactor.setVisible(false);
		}	

		tfNombre.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
		    	lAlerta.setVisible(false);	
		    	
		    	if (ContextoEncomienda.getInstance().getIdentificadorVentana().equals("UNIDAD")){
		    		if ((tfNombre.getText().compareTo("") != 0) && (tfFactor.getText().compareTo("") != 0)){
		    			bAceptar.setDisable(false);
		    		}else
		    			bAceptar.setDisable(true);
		    	}else if (ContextoEncomienda.getInstance().getIdentificadorVentana().equals("TIPO EMBALAJE")){
		    		try{
		    			if ((tfNombre.getText().compareTo("") != 0) && (cbTarifa.getSelectionModel().getSelectedItem().toString().compareTo("")!=0)){
			    			bAceptar.setDisable(false);
			    		}else
			    			bAceptar.setDisable(true);
			    	}catch(NullPointerException e){
			    		bAceptar.setDisable(true);
			    	}
		    	}else{		    	
		    		if (tfNombre.getText().compareTo("") != 0){
		    			bAceptar.setDisable(false);
		    		}else if (tfNombre.getText().compareTo("") == 0){
		    			bAceptar.setDisable(true);
		    		}
		    	}
		    }
		});
		
		tfFactor.lengthProperty().addListener(new ChangeListener<Number>(){
		     @Override
		     public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {	           
	    	 
		    	 if (ContextoEncomienda.getInstance().getIdentificadorVentana().equals("UNIDAD")){
		    		 if ((tfNombre.getText().compareTo("") != 0) && (tfFactor.getText().compareTo("") != 0)){
			    			bAceptar.setDisable(false);
			    		}else
			    			bAceptar.setDisable(true);
		    	 }else{
			    	 if (tfFactor.getText().compareTo("") != 0){
				    		bAceptar.setDisable(false);
				     }else if (tfFactor.getText().compareTo("") == 0){
				    		bAceptar.setDisable(true);
				     }
			    }		    	 
		     }		     
		});	
	
		tfNombre.addEventHandler(javafx.scene.input.KeyEvent.KEY_TYPED, libreria.letraValidacion(30));
		tfFactor.addEventHandler(javafx.scene.input.KeyEvent.KEY_TYPED, libreria.decimalValidacion(20));		
	}
	
	@FXML
	private void actionBotonAceptar(){
		System.out.println("Boton guardar");		
		
		if (tfNombre.getText().compareTo("") != 0){
			Session sesion = openSesion();		
			
			if (ContextoEncomienda.getInstance().getIdentificadorVentana().equals("CIUDAD DESTINO")){
				objCiudadDestino = new CiudadDestino();
				objCiudadDestino.setDescripcion(tfNombre.getText());
				sesion.save(objCiudadDestino);
				closeSesion(sesion);
			}else if (ContextoEncomienda.getInstance().getIdentificadorVentana().equals("TIPO EMBALAJE")){
				objTipoEmbalaje = new TipoEmbalaje();
				objTipoEmbalaje.setNombre(tfNombre.getText());
				sesion.save(objTipoEmbalaje);
				closeSesion(sesion);
			}else if (ContextoEncomienda.getInstance().getIdentificadorVentana().equals("TARIFA")){
				objTarifa = new Tarifa();
				objTarifa.setDescripcion(tfNombre.getText());
				sesion.save(objTarifa);
				closeSesion(sesion);
			}else if (ContextoEncomienda.getInstance().getIdentificadorVentana().equals("UNIDAD")){
				objUnidad = new Unidad();
				objUnidad.setDescripcion(tfNombre.getText());
				objUnidad.setFactor(Double.parseDouble(tfFactor.getText()));
				sesion.save(objUnidad);
				closeSesion(sesion);
				tfFactor.setText("");
			}else if (ContextoEncomienda.getInstance().getIdentificadorVentana().equals("VARIABLE")){
				
				queryVariableConfiguracion = sesion.createQuery("from VariableConfiguracion where nombre = :cadena");
				queryVariableConfiguracion.setParameter("cadena", tfNombre.getText());
				queryVariableConfiguracion.setMaxResults(1);
				VariableConfiguracion objvc = (VariableConfiguracion) queryVariableConfiguracion.uniqueResult();
				
				if (objvc==null){
					System.out.println("no encontro algo con " + tfNombre.getText());
					objVariable = new VariableConfiguracion();
					objVariable.setNombre(tfNombre.getText());
					sesion.save(objVariable);
					closeSesion(sesion);
					
					Stage stage = (Stage) bAceptar.getScene().getWindow();
					stage.close();
					
					ventComisiones = null;
					if (ventComisiones == null){
						ventComisiones = new VentanaConfComisiones();
						try {
							ventComisiones.start(new Stage());
						} catch (Exception e) {
							//e.printStackTrace();
						}
					}
				}else{
					closeSesion(sesion);
					lAlerta.setText(tfNombre.getText()+" ya se encuentra registrado");
					lAlerta.setVisible(true);
					System.out.println("si encontro algo con " + objvc.getNombre());					
				}				
			}
			tfNombre.setText("");			
		}else{
			lAlerta.setVisible(true);
		}		
	}
	
	@FXML
	private void actionBotonCancelar(){
		System.out.println("Boton Cancelar");
		ContextoEncomienda.getInstance().setIdentificadorVentana(null);
		
		Stage stage = (Stage) bAceptar.getScene().getWindow();
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
}
