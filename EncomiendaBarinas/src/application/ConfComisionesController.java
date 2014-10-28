package application;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import data.CiudadDestino;
import data.Cliente;
import data.DetalleVariableConfiguracion;
import data.VariableConfiguracion;
import eu.schudt.javafx.controls.calendar.DatePicker;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

//import javafx.scene.control.DatePicker;
//<DatePicker fx:id="dpFechaVigencia" layoutX="150.0" layoutY="130.0" prefHeight="25.0" prefWidth="100.0" />
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ConfComisionesController {
	
	@FXML
	private GridPane gpFecha;
	
	DatePicker dp ;
	
	@FXML
	private Button bAceptar;
	
	@FXML
	private Button bCancelar;

	@FXML
	private ChoiceBox cbTipoVariable;
		
	@FXML
	private ChoiceBox cbTipoValor;
	
	@FXML
	private TextField tfValorVariable;
		
	@FXML
	private Label lAlerta;
	
//	@FXML
//	private DatePicker dpFechaVigencia;
	
	Query queryResultTipoVariable;	
	private ObservableList<VariableConfiguracion> TipoVariableList = FXCollections.observableArrayList();
	private ObservableList<String> opcionTipoVariable = FXCollections.observableArrayList();
	
	boolean bandValorVariable = false, bandFechaVigencia = false, bandTipoVariable = false, bandTipoDato = false, bandPuntoDecimal = false;
		
	int posTipoVariable=0;
	
	char [] v;
	
	@FXML
	private void initialize(){	
		bAceptar.setDisable(false);
		dp = new DatePicker();
		dp.setDateFormat( new SimpleDateFormat("dd-MM-yyyy"));
		//la propiedad de la show weeks false
		
		dp.setStyle("/application/DatePicker.css");
		gpFecha.add(dp,0,0);
		
		System.out.println("HolaConfComisiones");	
		
//		DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
//		simbolos.setDecimalSeparator('.');
//		final DecimalFormat decimalFormat = new DecimalFormat("##.00",simbolos);
		
		try{			
			Session sesion1 = openSesion();
			queryResultTipoVariable = sesion1.createQuery("from VariableConfiguracion");
			TipoVariableList = FXCollections.observableArrayList(queryResultTipoVariable.list());
			for (int r=0;r<TipoVariableList.size();r++){
				opcionTipoVariable.add(TipoVariableList.get(r).getNombre());
			}
			
			cbTipoVariable.setItems(opcionTipoVariable);
			cbTipoVariable.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){			
				@Override
				public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
					lAlerta.setVisible(false);
					System.out.println(opcionTipoVariable.get(arg2.intValue()));				
			}});
			closeSesion(sesion1);
		}catch(HibernateException e){
			e.printStackTrace();
		}
		
		tfValorVariable.setOnAction(new EventHandler(){
			@Override
			public void handle(Event arg0) {
				tfValorVariable.addEventHandler(javafx.scene.input.KeyEvent.KEY_TYPED, libreria.porcentajeValidacion());
//				http://code.makery.ch/blog/javafx-8-event-handling-examples/				
			}});
		
//		tfValorVariable.setOnAction((event) -> {
//			outputTextArea.appendText("TextField Action\n");
//		});
		
		tfValorVariable.textProperty().addListener(new ChangeListener<String>(){
			 @Override
			    public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {				 
				 	
				 lAlerta.setVisible(false);
				 if (tfValorVariable.getText().equals("")){
						 bandValorVariable=false;
						 bAceptar.setDisable(true);	
						 bandPuntoDecimal=false;
						 v = null;
				 }else{
					 bandValorVariable=true;			 
						
					 if (bandValorVariable && bandFechaVigencia && bandTipoVariable && bandTipoDato)
						bAceptar.setDisable(false);
				}												 
		}});	
		
		cbTipoVariable.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){			
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {		
				bandTipoVariable = true;	
				posTipoVariable=arg2.intValue();
				if (bandValorVariable && bandFechaVigencia && bandTipoVariable && bandTipoDato){
					 System.out.println("activo en cbtv");					
					 bAceptar.setDisable(false);
          		}
				if (bandFechaVigencia && bandTipoVariable && bandTipoDato){				
					 tfValorVariable.setDisable(false);
					 tfValorVariable.setOpacity(1);
       		    }				
		}});
		
		cbTipoValor.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){			
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {		
				lAlerta.setVisible(false);
				bandTipoDato = true;
				bandPuntoDecimal=false;
				tfValorVariable.setText("");
				if (arg2.intValue() == 0){
					System.out.println("Monto: ");
//					asignarTipoMonto();
				}else if (arg2.intValue() == 1){
					System.out.println("Porcentaje: ");
//					asignarTipoPorcentaje();
				}
				 
				if (bandValorVariable && bandFechaVigencia && bandTipoVariable && bandTipoDato){
					 System.out.println("activo en cb td");
					 bAceptar.setDisable(false);
         		 }
				if (bandFechaVigencia && bandTipoVariable && bandTipoDato){				
					 tfValorVariable.setDisable(false);
 					 tfValorVariable.setOpacity(1);
        		 }
		}});
	
//		dpFechaVigencia.setOnAction(new EventHandler(){
//			@Override
//			public void handle(Event arg0) {
//				lAlerta.setVisible(false);
//				System.out.println("texto en fechaaaa  "+dpFechaVigencia.getValue());
//				
//				if (dpFechaVigencia.getValue()!=null)
//					bandFechaVigencia=true;
//				else
//					bandFechaVigencia=false;
//				
//				if (bandFechaVigencia && bandTipoVariable && bandTipoDato){				
//					 tfValorVariable.setDisable(false);
//					 tfValorVariable.setOpacity(1);
//				}else{
//					 tfValorVariable.setDisable(true);
//					 tfValorVariable.setOpacity(0.5);
//				}					
//			}});
		
		if (bandTipoDato)
			if (cbTipoValor.getSelectionModel().getSelectedItem().toString().equals("Monto")){
				asignarTipoMonto();
				System.out.println("undi montoooooooooooooooooooooooo");	
			}else if (cbTipoValor.getSelectionModel().getSelectedItem().toString().equals("Porcentaje")){
				asignarTipoPorcentaje();
				System.out.println("undi porcentajeeeeeeeeeeeeeee");
			}
	}
	
	private void asignarTipoPorcentaje(){
		tfValorVariable.addEventHandler(javafx.scene.input.KeyEvent.KEY_TYPED, libreria.porcentajeValidacion());
	}

	private void asignarTipoMonto(){
		tfValorVariable.addEventHandler(javafx.scene.input.KeyEvent.KEY_TYPED, libreria.telefonoValidacion(10));
	}
	@FXML
	private void botonActionAceptar(){
//		System.out.println("en ConfOtrasComisiones: aceptar" + dpFechaVigencia.getValue());
		lAlerta.setVisible(false);	
		Calendar c = Calendar.getInstance();
		c.setTime(this.dp.getSelectedDate());
		System.out.println("la fechaaaaaaaaaaaaaaaa   "+c.getTime());
//		objde guardarfactura.setfecha(c.getTime());
		
		try{
			Session sesion2 = openSesion();
			DetalleVariableConfiguracion obj = new DetalleVariableConfiguracion();
			
//			obj.setFechaVigencia(dpFechaVigencia.getValue().toString());
			obj.setTipoValor(cbTipoValor.getSelectionModel().getSelectedItem().toString());
			
			if (cbTipoValor.getSelectionModel().getSelectedIndex() == 0){
				obj.setValor(Double.parseDouble(tfValorVariable.getText()));
			}else if (cbTipoValor.getSelectionModel().getSelectedIndex() == 1){
				if (Double.parseDouble(tfValorVariable.getText())>0 && Double.parseDouble(tfValorVariable.getText())<=100)
					obj.setValor(Double.parseDouble(tfValorVariable.getText()));				
			}			
			
			obj.setObjVariable(TipoVariableList.get(posTipoVariable));
				
			sesion2.save(obj);			
			closeSesion(sesion2);
		}catch(HibernateException e){
			e.printStackTrace();
		}			
		tfValorVariable.setText("");
//		dpFechaVigencia.setValue(null);
	} 
	
	@FXML
	private void botonActionCancelar(){
		lAlerta.setVisible(false);
		System.out.println("en ConfRemesa: cancelar");		
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
}
