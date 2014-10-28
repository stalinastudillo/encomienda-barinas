package application;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import data.Banco;
import data.TipoTarjeta;
import data.FormaPago;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class FormaPagoController {
	
	private ObservableList<Banco> BancoArray = FXCollections.observableArrayList();
	private ObservableList<String> opcionBanco = FXCollections.observableArrayList();
	
	private ObservableList<TipoTarjeta> TTarjetaCArray = FXCollections.observableArrayList();
	private ObservableList<String> opcionTTarjetaC = FXCollections.observableArrayList();
	
	private ObservableList<TipoTarjeta> TTarjetaDArray = FXCollections.observableArrayList();
	private ObservableList<String> opcionTTarjetaD = FXCollections.observableArrayList();
	
	Query queryResultBanco,queryResultTTarjetaC,queryResultTTarjetaD;	
		
	@FXML
	private ChoiceBox cbBanco;
	
	@FXML
	private ChoiceBox cbTipoTarjetaC;	
	
	@FXML
	private ChoiceBox cbTipoTarjetaD;
	
	@FXML
	private TextField tfNroRef;
	
	@FXML
	private RadioButton rbEfectivo;
	
	@FXML
	private RadioButton rbCredito;
	
	@FXML
	private RadioButton rbCheque;
	
	@FXML
	private RadioButton rbDebito;
	
	private boolean bandEfectivo = false;
	
	private boolean bandCredito = false;
	
	private boolean bandCheque = false;
	
	private boolean bandDebito = false;
	
	@FXML
	private TextField tfMonto;
	
	@FXML
	private TableView<FormaPago> tvTabla = new TableView<FormaPago>();	
	
	@FXML
	private TableColumn<FormaPago,String> tcTipoPago;
	
	@FXML
	private TableColumn<FormaPago,String> tcMonto;
		
	@FXML
	private Button bMas;
	
	@FXML
	private Button bMenos;
	
	@FXML
	private Button bGuardar;
	
	@FXML
	private Button bCancelar;
	
	private ObservableList<FormaPago> itemsData = FXCollections.observableArrayList();
	
	@FXML
	private Label lMontoAPagar;
	
	@FXML
	private Label lMontoPagado;
	
	@FXML
	private Label lMontoResta;
	
	double totalmas = 0, totalmenos = 0;
	
	@FXML
	private Label lAlerta;
	
	private boolean bandBanco = false, bandTarjeta = false;
	
	int r=0;
	
	String val;
	
	BigDecimal big;
	
	private PruebaVentanas ProgramaPrincipal;
	
	@FXML
	private void initialize(){		
		
		lMontoAPagar.setText(ContextoEncomienda.getInstance().getTotalFactura());
		lMontoResta.setText(ContextoEncomienda.getInstance().getTotalFactura());
		
		cbTipoTarjetaC.setVisible(false);
		cbTipoTarjetaD.setVisible(false);
		tcTipoPago.setCellValueFactory(new PropertyValueFactory<FormaPago,String>("tipoPago"));
		tcMonto.setCellValueFactory(new PropertyValueFactory<FormaPago,String>("monto"));
		
		tvTabla.setColumnResizePolicy(tvTabla.UNCONSTRAINED_RESIZE_POLICY);
				
		try{						
			Session sesion1 = openSesion();
			queryResultBanco = sesion1.createQuery("from Banco");
			
			//------------- CARGA DE BANCO	
			BancoArray = FXCollections.observableArrayList(queryResultBanco.list());		
			for (r=0;r<BancoArray.size();r++){
				opcionBanco.add(BancoArray.get(r).getNombre());
			}
			
			cbBanco.setItems(opcionBanco);
			cbBanco.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){			
				@Override
				public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
					bandBanco = true;
					System.out.println("banco *: "+opcionBanco.get(arg2.intValue()));			
			}});		
			
			//------------- CARGA DE TIPO TARJETAS CREDITO	
			queryResultTTarjetaC = sesion1.createQuery("from TipoTarjeta where tipo=1");
			TTarjetaCArray = FXCollections.observableArrayList(queryResultTTarjetaC.list());
			for (r=0;r<TTarjetaCArray.size();r++){
				opcionTTarjetaC.add(TTarjetaCArray.get(r).getNombre());
			}			
			cbTipoTarjetaC.setItems(opcionTTarjetaC);
			cbTipoTarjetaC.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){			
				@Override
				public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
					bandTarjeta = true;
//					System.out.println(opcionTTarjetaC.get(arg2.intValue()));					
			}});
			
			//------------- CARGA DE TIPO TARJETAS DEBITO
			
			queryResultTTarjetaD = sesion1.createQuery("from TipoTarjeta where tipo=0");
			TTarjetaDArray = FXCollections.observableArrayList(queryResultTTarjetaD.list());				
			for (r=0;r<TTarjetaDArray.size();r++){
				opcionTTarjetaD.add(TTarjetaDArray.get(r).getNombre());
			}			
			cbTipoTarjetaD.setItems(opcionTTarjetaD);			
			cbTipoTarjetaD.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){			
				@Override
				public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
					bandTarjeta = true;
//					System.out.println(opcionTTarjetaD.get(arg2.intValue()));					
			}});
			
			
			closeSesion(sesion1);
		}catch(HibernateException e){
			e.printStackTrace();
		}
		
		tvTabla.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<FormaPago>(){
			@Override
			public void changed(ObservableValue<? extends FormaPago> arg0, FormaPago arg1, FormaPago arg2) {
				
				//System.out.println(arg2.getCodigo() + " " + arg2.getMonto());
			}
		});
	}
	
	@FXML
	public void actionBotonEfectivo(){
		lAlerta.setVisible(false);
		bandEfectivo = true;
		bandCredito = false;
		bandCheque = false;
		bandDebito = false;		
		tfMonto.setDisable(false);
		ActivarBotones();		
		DesactivarCamposBanco();	
		rbCheque.setSelected(false);
		rbDebito.setSelected(false);
		rbCredito.setSelected(false);
		tfNroRef.setText("");
		tfMonto.setText("");
		cbTipoTarjetaC.setVisible(false);
		cbTipoTarjetaD.setVisible(false);
		cbTipoTarjetaD.setDisable(true);
		cbTipoTarjetaC.setDisable(true);
	}

	@FXML
	public void actionBotonTCredito(){
		cbTipoTarjetaC.setVisible(true);
		cbTipoTarjetaD.setVisible(false);
		cbTipoTarjetaD.setDisable(true);
		cbTipoTarjetaC.setDisable(false);
		lAlerta.setVisible(false);
		bandEfectivo = false;
		bandCredito = true;
		bandCheque = false;
		bandDebito = false;		
		ActivarBotones();	
		ActivarCamposBanco();
		tfMonto.setDisable(false);
		rbEfectivo.setSelected(false);
		rbCheque.setSelected(false);
		rbDebito.setSelected(false);
		tfMonto.setText("");
		tfNroRef.setText("");			
		
	}
	
	@FXML
	public void actionBotonCheque(){
		lAlerta.setVisible(false);
		bandEfectivo = false;
		bandCredito = false;
		bandCheque = true;
		bandDebito = false;		
		ActivarBotones();
		ActivarCamposBanco();
		tfMonto.setDisable(false);
		rbEfectivo.setSelected(false);
		rbDebito.setSelected(false);
		rbCredito.setSelected(false);
		tfMonto.setText("");
		tfNroRef.setText("");
		cbTipoTarjetaC.setDisable(true);
		cbTipoTarjetaD.setDisable(true);
		cbTipoTarjetaC.setVisible(false);
		cbTipoTarjetaD.setVisible(false);
	}
	
	@FXML
	public void actionBotonTDebito(){
		cbTipoTarjetaC.setVisible(false);
		cbTipoTarjetaD.setVisible(true);
		cbTipoTarjetaD.setDisable(false);
		cbTipoTarjetaC.setDisable(true);
		
		lAlerta.setVisible(false);
		bandEfectivo = false;
		bandCredito = false;
		bandCheque = false;
		bandDebito = true;	
		ActivarBotones();
		ActivarCamposBanco();
		tfMonto.setDisable(false);
		rbEfectivo.setSelected(false);
		rbCheque.setSelected(false);
		rbCredito.setSelected(false);	
		tfMonto.setText("");
		tfNroRef.setText("");		
			
	}
	
	@FXML
	public void actionBotonMas(){
		lAlerta.setVisible(false);
		bMenos.setDisable(false);
		System.out.println("+ + + +");			
		totalmas = 0;		
		
		if ( ((tfMonto.getText().compareTo("")!=0) && (bandEfectivo)) || ((tfMonto.getText().compareTo("")!=0) && ((bandCheque)||(bandCredito)||(bandDebito))) ) {
			if ( Double.valueOf(lMontoResta.getText()) >= Double.valueOf(tfMonto.getText()) ){			
				
				if ( ( ((bandBanco) && (bandTarjeta) && (tfNroRef.getText().compareTo("")!=0)) && ( ((bandCredito)||(bandDebito))&&(!bandEfectivo)) )
						|| (bandEfectivo) || ((bandCheque)&&(bandBanco)&&(tfNroRef.getText().compareTo("")!=0)) ){
									
					if (bandEfectivo)
						itemsData.add(new FormaPago("EF",tfMonto.getText(),"+","noaplica","noaplica","noaplica"));
					else if (bandCheque)
						itemsData.add(new FormaPago("CH",tfMonto.getText(),"+",cbBanco.getSelectionModel().getSelectedItem().toString(),"noaplica",tfNroRef.getText()));
					else if (bandDebito)
						itemsData.add(new FormaPago("TD",tfMonto.getText(),"+",cbBanco.getSelectionModel().getSelectedItem().toString(),cbTipoTarjetaD.getSelectionModel().getSelectedItem().toString(),tfNroRef.getText()));
					else if (bandCredito)
						itemsData.add(new FormaPago("TC",tfMonto.getText(),"+",cbBanco.getSelectionModel().getSelectedItem().toString(),cbTipoTarjetaC.getSelectionModel().getSelectedItem().toString(),tfNroRef.getText()));
										
					tvTabla.setItems(itemsData);
							
					for (int y=0; y<=itemsData.size()-1; y++){
						if (itemsData.get(y).getSigno().compareTo("+") == 0)
							totalmas += Double.valueOf(itemsData.get(y).getMonto());
					}			
					val= String.valueOf(Double.valueOf(totalmas-totalmenos));
					big = new BigDecimal(val);
					big = big.setScale(2, RoundingMode.HALF_UP);
					lMontoPagado.setText(String.valueOf(big));
					
					val= String.valueOf(Double.valueOf(lMontoAPagar.getText()) - Double.valueOf(lMontoPagado.getText()));
					big = new BigDecimal(val);
					big = big.setScale(2, RoundingMode.HALF_UP);
					lMontoResta.setText(String.valueOf(big));						
				
				}else if ( ((!bandBanco) || (!bandTarjeta) || (tfNroRef.getText().compareTo("")==0)) && (((bandCheque)||(bandCredito)||(bandDebito))&&(!bandEfectivo)) ){				
					lAlerta.setText("Debe seleccionar todos los datos bancarios necesarios");
					lAlerta.setVisible(true);
				}			
				
			}else if (Double.parseDouble(tfMonto.getText()) > Double.parseDouble(lMontoResta.getText())){	
				lAlerta.setText("La deuda es menor");
				lAlerta.setVisible(true);
			}	
		}else if ((tfMonto.getText().compareTo("") == 0) && (bandEfectivo)){
			lAlerta.setText("Debe ingresar el monto");
			lAlerta.setVisible(true);
		}else if  ((tfMonto.getText().compareTo("") == 0) && ((bandCheque)||(bandCredito)||(bandDebito))){
			lAlerta.setText("Debe ingresar el monto y la información bancaria");
			lAlerta.setVisible(true);
		}	
		
		if (Double.parseDouble(lMontoResta.getText()) == 0.00){
			bGuardar.setDisable(false);
		}
	}
		
	@FXML
	public void actionBotonMenos(){	
		lAlerta.setVisible(false);
		System.out.println("- - - -");			
		totalmenos = 0;	
			
		if ( Double.valueOf(tfMonto.getText()) <= Double.valueOf(lMontoPagado.getText()) ){
			if (bandEfectivo)
				itemsData.add(new FormaPago("EF",tfMonto.getText(),"-","noaplica","noaplica","noaplica"));
			else if (bandCheque)
				itemsData.add(new FormaPago("CH",tfMonto.getText(),"-",cbBanco.getSelectionModel().getSelectedItem().toString(),"noaplica",tfNroRef.getText()));
			else if (bandDebito)
				itemsData.add(new FormaPago("TD",tfMonto.getText(),"-",cbBanco.getSelectionModel().getSelectedItem().toString(),cbTipoTarjetaD.getSelectionModel().getSelectedItem().toString(),tfNroRef.getText()));
			else if (bandCredito)
				itemsData.add(new FormaPago("TC",tfMonto.getText(),"-",cbBanco.getSelectionModel().getSelectedItem().toString(),cbTipoTarjetaC.getSelectionModel().getSelectedItem().toString(),tfNroRef.getText()));
			
			tvTabla.setItems(itemsData);
			for (int y=0; y<=itemsData.size()-1; y++){
				if (itemsData.get(y).getSigno().compareTo("-") == 0){
					totalmenos += Double.valueOf(itemsData.get(y).getMonto());				
				}
			}
			
			val= String.valueOf(Double.valueOf(totalmas-totalmenos));
			big = new BigDecimal(val);
			big = big.setScale(2, RoundingMode.HALF_UP);
			lMontoPagado.setText(String.valueOf(big));
			
			val= String.valueOf(Double.valueOf(lMontoAPagar.getText()) - Double.valueOf(lMontoPagado.getText()));
			big = new BigDecimal(val);
			big = big.setScale(2, RoundingMode.HALF_UP);
			lMontoResta.setText(String.valueOf(big));
		}else{
			lAlerta.setText("El monto depositado es menor");
			lAlerta.setVisible(true);
		}
		
		if (Double.parseDouble(lMontoResta.getText()) == 0.00){
			bGuardar.setDisable(false);
		}
	}	
	
	@FXML
	public void actionBotonGuardar(){
		lAlerta.setVisible(false);
		for (int y=0; y<=itemsData.size()-1; y++){
			System.out.println(itemsData.get(y).getTipoPago()+" "+itemsData.get(y).getMonto()+" "+itemsData.get(y).getSigno()+" "+itemsData.get(y).getBanco()+" "+itemsData.get(y).getTipoTarjeta()+" "+itemsData.get(y).getNroRef());
		}
		ContextoEncomienda.getInstance().setBanderaRefresh(true);
		ContextoEncomienda.getInstance().setItemsFormaPago(itemsData);		
		//ec.llegodepago(lMontoResta.getText(),itemsData);
		Stage stage = (Stage) bGuardar.getScene().getWindow();
		stage.close();
		//ProgramaPrincipal.mostrarVentanaPrincipal();
	}	
	
	@FXML
	public void actionBotonCancelar(){
		int borrar = tvTabla.getSelectionModel().getSelectedIndex();
	
		if (itemsData.size()>1){
			Double d = Double.parseDouble( lMontoPagado.getText()) - Double.parseDouble(itemsData.get(borrar).getMonto());
			lMontoPagado.setText(String.valueOf(d));
			d = Double.parseDouble( lMontoResta.getText()) + Double.parseDouble(itemsData.get(borrar).getMonto());
			lMontoResta.setText(String.valueOf(d));
			itemsData.remove(borrar);			
		}else if ( (borrar==0) && (itemsData.size()==1) ){
			Double d = Double.parseDouble( lMontoPagado.getText()) - Double.parseDouble(itemsData.get(borrar).getMonto());
			lMontoPagado.setText(String.valueOf(d));
			d = Double.parseDouble( lMontoResta.getText()) + Double.parseDouble(itemsData.get(borrar).getMonto());
			lMontoResta.setText(String.valueOf(d));
		}
		
		if (itemsData.size()<1)
			bCancelar.setDisable(true);
	}	
		
	private void ActivarBotones(){
		bMas.setDisable(false);
		bCancelar.setDisable(false);
	}	
	
	private void ActivarCamposBanco(){
		cbBanco.setDisable(false);
		tfNroRef.setDisable(false);	
	}	
	
	private void DesactivarCamposBanco(){
		cbBanco.setDisable(true);
		tfNroRef.setDisable(true);	
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
		 System.out.println("Controlador forma pago - setPP");
		 this.ProgramaPrincipal = ProgramaPrincipal;
	 }
}

