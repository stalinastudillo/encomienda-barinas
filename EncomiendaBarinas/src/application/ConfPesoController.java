package application;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
















import org.hibernate.exception.ConstraintViolationException;

//dateas.com
import data.CiudadDestino;
import data.Cliente;
import data.Ipostel;
import data.PesoTarifa;
import data.PrecioTarifa;
import data.Unidad;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ConfPesoController {

	@FXML
	private ChoiceBox cbUnidad;
	
	@FXML
	private TextField tfDesde;
	
	@FXML
	private TextField tfHasta;
	
	@FXML
	private TextField tfMonto;
			
	@FXML
	private Button bMas;
	
	@FXML
	private Button bEditar;
		
	@FXML
	private Button bMenos;
	
	@FXML
	private Label lAlerta;
	
	@FXML
	private Label lMonto;
	
	@FXML
	private TableView<Ipostel> tvTablaIpostel = new TableView<Ipostel>();
	
	@FXML
	private TableView<PesoTarifa> tvTablaPT = new TableView<PesoTarifa>();
	
	@FXML
	private TableColumn<Ipostel,String> tcRangoPesoI;
	
	@FXML
	private TableColumn<Ipostel,String> tcValorI;
	
	@FXML
	private TableColumn<Ipostel,String> tcUnidadI;
	
	@FXML
	private TableColumn<PesoTarifa,String> tcRangoPesoPT;
	
	@FXML
	private TableColumn<PesoTarifa,String> tcUnidadPT;
	
	private ObservableList<Ipostel> itemsIpostel = FXCollections.observableArrayList();
	
	private ObservableList<PesoTarifa> itemsPesoTarifa = FXCollections.observableArrayList();
	
	private ObservableList<Unidad> UnidadList = FXCollections.observableArrayList();
	private ObservableList<String> opcionUnidad = FXCollections.observableArrayList();
	
	Query queryResultUnidad, queryResultTabla;
	
	private boolean bandUnidad = false, valorRango = false, bandDesde = false, bandHasta = false;
		
	int idEditar=0,posEditar=0;
	
	@FXML
	private void initialize(){	
				
		//------------- CARGA DE UNIDAD	
		try{			
			Session sesion1 = openSesion();		
						
			if (ContextoEncomienda.getInstance().getIdentificadorVentana().equals("PESO NORMAL")){
				System.out.println("Peso normal: * * * * * * * * * * * * * ");
				queryResultUnidad = sesion1.createQuery("from Unidad");
				tvTablaPT.setVisible(true);
				tcRangoPesoPT.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PesoTarifa, String>, ObservableValue<String>>(){
					@Override
					public ObservableValue<String> call(CellDataFeatures<PesoTarifa, String> arg0) {
						return new SimpleStringProperty(""+arg0.getValue().getDesde()+"-"+arg0.getValue().getHasta());
					}			
				});
				
				tcUnidadPT.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PesoTarifa, String>, ObservableValue<String>>(){
					@Override
					public ObservableValue<String> call(CellDataFeatures<PesoTarifa, String> arg0) {
						return new SimpleStringProperty(""+arg0.getValue().getUndMedida().getDescripcion());
					}			
				});
				
				tvTablaPT.setColumnResizePolicy(tvTablaPT.UNCONSTRAINED_RESIZE_POLICY);	
				
				queryResultTabla = sesion1.createQuery("from PesoTarifa");
				itemsPesoTarifa = FXCollections.observableArrayList(queryResultTabla.list()); 
				tvTablaPT.setItems(itemsPesoTarifa);
				
			}else if (ContextoEncomienda.getInstance().getIdentificadorVentana().equals("IPOSTEL")){
				System.out.println("ipostel: * * * * * * * * * * * * * ");
				queryResultUnidad = sesion1.createQuery("from Unidad where factor <= 1");
				tvTablaIpostel.setVisible(true);
				lMonto.setVisible(true);
				tfMonto.setVisible(true);
				tfMonto.addEventHandler(javafx.scene.input.KeyEvent.KEY_TYPED, libreria.decimalValidacion(10));
				
				tcRangoPesoI.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Ipostel, String>, ObservableValue<String>>(){
					@Override
					public ObservableValue<String> call(CellDataFeatures<Ipostel, String> arg0) {
						return new SimpleStringProperty(""+arg0.getValue().getDesde()+"-"+arg0.getValue().getHasta());
					}			
				});
			
				tcValorI.setCellValueFactory(new PropertyValueFactory<Ipostel,String>("Valor"));		
				
				tcUnidadI.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Ipostel, String>, ObservableValue<String>>(){
					@Override
					public ObservableValue<String> call(CellDataFeatures<Ipostel, String> arg0) {
						return new SimpleStringProperty(""+arg0.getValue().getUndMedida().getDescripcion());
					}			
				});
			
				tvTablaIpostel.setColumnResizePolicy(tvTablaIpostel.UNCONSTRAINED_RESIZE_POLICY);	
				
				queryResultTabla = sesion1.createQuery("from Ipostel");
				itemsIpostel = FXCollections.observableArrayList(queryResultTabla.list()); 
				tvTablaIpostel.setItems(itemsIpostel);
			}	
				
			UnidadList = FXCollections.observableArrayList(queryResultUnidad.list());
			
			for (int r=0;r<UnidadList.size();r++){
				opcionUnidad.add(UnidadList.get(r).getDescripcion());
			}
			
			cbUnidad.setItems(opcionUnidad);
			cbUnidad.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){			
				@Override
				public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
					System.out.println(opcionUnidad.get(arg2.intValue()));
					bandUnidad=true;
					lAlerta.setVisible(false);
			}});
			
			closeSesion(sesion1);
			
		}catch(HibernateException e){
			e.printStackTrace();
		}	
		
		tfDesde.lengthProperty().addListener(new ChangeListener<Number>(){
		     @Override
		     public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {	           
		    	 bandDesde = true;     lAlerta.setVisible(false);
		    	 System.out.println("idEditar desde tfdesde: "+idEditar);
		}});
		
		tfHasta.lengthProperty().addListener(new ChangeListener<Number>(){
		     @Override
		     public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {	           
		    	 bandHasta = true;
			     lAlerta.setVisible(false);
			     System.out.println("idEditar desde tfhasta: "+idEditar);	
		}});			
	
		tvTablaIpostel.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Ipostel>(){
			@Override
			public void changed(ObservableValue<? extends Ipostel> arg0, Ipostel arg1,Ipostel arg2) {
					bMas.setDisable(true);
					bEditar.setDisable(false);
					if (arg2 != null){
						System.out.println("Opción de tabla seleccionada arg2: "+arg2.getDesde()+" "+arg2.getHasta()+" "+arg2.getValor()+" "+arg2.getUndMedida().getDescripcion());
						tfDesde.setText(String.valueOf(arg2.getDesde()));
						tfHasta.setText(String.valueOf(arg2.getHasta()));
						tfMonto.setText(String.valueOf(arg2.getValor()));
						idEditar = arg2.getCodigo();
						
						for (int d=0;d<UnidadList.size();d++){
							if (UnidadList.get(d).getCodigo() == arg2.getUndMedida().getCodigo()){
								cbUnidad.getSelectionModel().select(d);
								break;
							}
						}
						
						for (int d=0;d<itemsIpostel.size();d++){
							if (itemsIpostel.get(d).getCodigo() == arg2.getCodigo())
								posEditar=d;
						}
					}
			}			
		});		
		
		tvTablaPT.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PesoTarifa>(){
			@Override
			public void changed(ObservableValue<? extends PesoTarifa> arg0, PesoTarifa arg1,PesoTarifa arg2) {
					bMas.setDisable(true);	
					bEditar.setDisable(false);
					if (arg2 != null){
						System.out.println("Opción de tabla seleccionada arg2: "+arg2.getDesde()+" "+arg2.getHasta()+" "+arg2.getUndMedida().getDescripcion());
						tfDesde.setText(String.valueOf(arg2.getDesde()));
						tfHasta.setText(String.valueOf(arg2.getHasta()));
						idEditar = arg2.getCodigo();
						
						for (int d=0;d<UnidadList.size();d++){
							if (UnidadList.get(d).getCodigo() == arg2.getUndMedida().getCodigo()){
								cbUnidad.getSelectionModel().select(d);
								break;
							}
						}	
						
						for (int d=0;d<itemsPesoTarifa.size();d++){
							if (itemsPesoTarifa.get(d).getCodigo() == arg2.getCodigo())
								posEditar=d;
						}
					}
			}			
		});	
		
		tfDesde.addEventHandler(javafx.scene.input.KeyEvent.KEY_TYPED, libreria.decimalValidacion(10));
		tfHasta.addEventHandler(javafx.scene.input.KeyEvent.KEY_TYPED, libreria.decimalValidacion(10));
	}
	
	@FXML
	private void actionBotonMas(){
		System.out.println("El boton mas");
		boolean band_rangoexiste=false;
		
		if (ContextoEncomienda.getInstance().getIdentificadorVentana().equals("PESO NORMAL")){
			if (!tfDesde.getText().equals("") && !tfHasta.getText().equals("") 
					&& cbUnidad.getSelectionModel().getSelectedIndex()!=-1){				
				Session sesion1 = openSesion();	
				Query queryResult = sesion1.createQuery("from PesoTarifa");
				
				//if (validacionRango(queryResult)){
					System.out.println("inserto!!!!!!!! peso tarifa");
					PesoTarifa obj = new PesoTarifa();
					obj.setDesde(Double.parseDouble(tfDesde.getText()));
					obj.setHasta(Double.parseDouble(tfHasta.getText()));
					obj.setUndMedida(UnidadList.get(cbUnidad.getSelectionModel().getSelectedIndex()));
					sesion1.save(obj);
					
					queryResultTabla = sesion1.createQuery("from PesoTarifa");
					itemsPesoTarifa = FXCollections.observableArrayList(queryResultTabla.list());
					tvTablaPT.setItems(itemsPesoTarifa);
					limpiarCampos();
				//}
				closeSesion(sesion1);
			}
		}else if (ContextoEncomienda.getInstance().getIdentificadorVentana().equals("IPOSTEL")){
			if (!tfDesde.getText().equals("") && !tfHasta.getText().equals("") && !tfMonto.getText().equals("") 
					&& cbUnidad.getSelectionModel().getSelectedIndex()!=-1){				
				Session sesion1 = openSesion();					
				Query queryResult = sesion1.createQuery("from Ipostel");
				
				//if (validacionRango(queryResult)){
					System.out.println("inserto!!!!!!!! Ipostel");
					Ipostel obj = new Ipostel();
					obj.setDesde(Double.parseDouble(tfDesde.getText()));
					obj.setHasta(Double.parseDouble(tfHasta.getText()));
					obj.setValor(Double.parseDouble(tfMonto.getText()));
					obj.setUndMedida(UnidadList.get(cbUnidad.getSelectionModel().getSelectedIndex()));
					sesion1.save(obj);
					
					queryResultTabla = sesion1.createQuery("from Ipostel");
					itemsIpostel = FXCollections.observableArrayList(queryResultTabla.list()); 
					tvTablaIpostel.setItems(itemsIpostel);
					limpiarCampos();
				//}
				closeSesion(sesion1);
			}else{
				lAlerta.setText("Debe ingresar toda la información");
				lAlerta.setVisible(true);
			}
		}
	}
	
	private void limpiarCampos(){
		
		tfDesde.setText("");
		tfHasta.setText("");			
//		cbUnidad.setItems(opcionUnidad);
//		cbUnidad.getSelectionModel().select(-1);
		if (ContextoEncomienda.getInstance().getIdentificadorVentana().equals("IPOSTEL")){			
			tfMonto.setText("");
		}
	}
	
	private boolean validacionRango(Query queryResult){
		boolean band_rangoexiste=false;
		
		if (ContextoEncomienda.getInstance().getIdentificadorVentana().equals("PESO NORMAL")){			
			ObservableList<PesoTarifa> list_pesotarifa = FXCollections.observableArrayList(queryResult.list());
			
			for (PesoTarifa list : list_pesotarifa){
				System.out.println("1: "+list.getCodigo()+" 2: "+list.getDesde()+" 3:  "+list.getHasta()+" 4: "+list.getUndMedida());
				System.out.println(" * * * *  *");
				
				if ( cbUnidad.getSelectionModel().getSelectedItem().toString().equals(list.getUndMedida().getDescripcion())){
					if ( Double.parseDouble(tfDesde.getText()) >= list.getDesde() 
					   && Double.parseDouble(tfDesde.getText()) <= list.getHasta() ){
						band_rangoexiste=true;
						break;
					}							
					if ( Double.parseDouble(tfHasta.getText()) >= list.getDesde() 
						&& Double.parseDouble(tfHasta.getText()) <= list.getHasta() ){
						band_rangoexiste=true;
						break;
					}
				}
			}			
		}else if (ContextoEncomienda.getInstance().getIdentificadorVentana().equals("IPOSTEL")){
			ObservableList<Ipostel> list_ipostel = FXCollections.observableArrayList(queryResult.list());
					
			for (Ipostel list : list_ipostel){
				System.out.println("1: "+list.getCodigo()+" 2: "+list.getDesde()+" 3:  "+list.getHasta()+" 4: "+list.getValor()+" 5: "+list.getUndMedida());
				System.out.println(" * * * * size *"+list_ipostel.size());
				System.out.println(" * * * * index  *"+list_ipostel.indexOf(list));
				
				if ( cbUnidad.getSelectionModel().getSelectedItem().toString().equals(list.getUndMedida().getDescripcion())){
					
					if ( Double.parseDouble(tfDesde.getText()) > list.getDesde() 
					   && Double.parseDouble(tfDesde.getText()) < list.getHasta() ){
						band_rangoexiste=true;
						break;
					}							
					if ( Double.parseDouble(tfHasta.getText()) > list.getDesde() 
						|| Double.parseDouble(tfHasta.getText()) < list.getHasta() ){
						band_rangoexiste=true;
						break;
					}
				}
			}			
		}
		if (band_rangoexiste){
			lAlerta.setText("Rango incorrecto");
			lAlerta.setVisible(true);
			System.out.println("el rango existe no puedo insertar");
			band_rangoexiste=false;
			return false;
		}else{			
			System.out.println("el rango no existe puedo insertar");
			return true;
		}	
		
	}
	
	@FXML
	private void actionBotonMenos(){
		System.out.println("Quiere eliminar: " + idEditar);
		
		try{
			Query queryResultEliminar;
			
			if (ContextoEncomienda.getInstance().getIdentificadorVentana().equals("PESO NORMAL")){
				PesoTarifa objpt;
				Session sesion1 = openSesion();
				queryResultEliminar = sesion1.createQuery("from PesoTarifa where Codigo = :codBor");
				queryResultEliminar.setInteger("codBor",idEditar);
				queryResultEliminar.setMaxResults(1);
				objpt = (PesoTarifa) queryResultEliminar.uniqueResult();
				
				sesion1.delete(objpt);
				queryResultTabla = sesion1.createQuery("from PesoTarifa");
				itemsPesoTarifa = FXCollections.observableArrayList(queryResultTabla.list());				
				tvTablaPT.setItems(itemsPesoTarifa);
				limpiarCampos();
				bMas.setDisable(false);
				bEditar.setDisable(false);
				closeSesion(sesion1);				
			}else if (ContextoEncomienda.getInstance().getIdentificadorVentana().equals("IPOSTEL")){
				Ipostel obji;
				Session sesion1 = openSesion();
				queryResultEliminar = sesion1.createQuery("from Ipostel where Codigo = :codBor");
				queryResultEliminar.setInteger("codBor",idEditar);
				queryResultEliminar.setMaxResults(1);
				obji = (Ipostel) queryResultEliminar.uniqueResult();
				sesion1.delete(obji);
				queryResultTabla = sesion1.createQuery("from Ipostel");
				itemsIpostel = FXCollections.observableArrayList(queryResultTabla.list()); 
				tvTablaIpostel.setItems(itemsIpostel);
				limpiarCampos();
				bMas.setDisable(false);
				bEditar.setDisable(false);
				closeSesion(sesion1);
			}
			lAlerta.setText("Eliminación exitosa");
			lAlerta.setVisible(true);
			
		}catch(ConstraintViolationException ee){
			lAlerta.setText("El rango no se puede eliminar, se encuentra relacionado con factura");
			lAlerta.setLayoutX(10);
			lAlerta.setVisible(true);
		}
	}
	
	@FXML
	private void actionBotonEditar(){
		System.out.println("Quiere editar: " + idEditar );		
		
		if (ContextoEncomienda.getInstance().getIdentificadorVentana().equals("PESO NORMAL")){
			
			Session sesion1 = openSesion();
			Query queryResult = sesion1.createQuery("from PesoTarifa");
			
//			if (validacionRango(queryResult)){
				PesoTarifa objpt;
				objpt = (PesoTarifa) sesion1.get(PesoTarifa.class, idEditar);
				objpt.setDesde(Double.parseDouble(tfDesde.getText()));
				objpt.setHasta(Double.parseDouble(tfHasta.getText()));
				if (cbUnidad.getSelectionModel().getSelectedIndex() != -1)
					objpt.setUndMedida(UnidadList.get(cbUnidad.getSelectionModel().getSelectedIndex()));
				sesion1.update(objpt);
				
				itemsPesoTarifa.set(posEditar,objpt);
				tvTablaPT.setItems(itemsPesoTarifa);
				
				lAlerta.setText("Registro actualizado exitosamente");
				lAlerta.setVisible(true);
				idEditar=0;				
				bEditar.setDisable(true);
				bMas.setDisable(false);
				limpiarCampos();
//			}else{
//				lAlerta.setText("Rango incorrecto");
//				lAlerta.setVisible(true);
//			}	
			closeSesion(sesion1);
			
		}else if (ContextoEncomienda.getInstance().getIdentificadorVentana().equals("IPOSTEL")){
			
			Session sesion1 = openSesion();
			Query queryResult = sesion1.createQuery("from Ipostel");
			
//			if (validacionRango(queryResult)){
				Ipostel obji = (Ipostel) sesion1.get(Ipostel.class, idEditar);	
				obji.setDesde(Double.parseDouble(tfDesde.getText()));
				obji.setHasta(Double.parseDouble(tfHasta.getText()));
				obji.setValor(Double.parseDouble(tfMonto.getText()));
				if (cbUnidad.getSelectionModel().getSelectedIndex() != -1)
					obji.setUndMedida(UnidadList.get(cbUnidad.getSelectionModel().getSelectedIndex()));
				sesion1.update(obji);
				
				itemsIpostel.set(posEditar, obji);
				tvTablaIpostel.setItems(itemsIpostel);
				
				lAlerta.setText("Registro actualizado exitosamente");
				lAlerta.setVisible(true);
				idEditar=0;				
				bEditar.setDisable(true);
				bMas.setDisable(false);
				limpiarCampos();
//			}else{
//				lAlerta.setText("Rango incorrecto");
//				lAlerta.setVisible(true);
//			}
			closeSesion(sesion1);
		}
		
		
	}
	
	@FXML
	private void actionBotonCancelar(){
		System.out.println("Boton Cancelar");			
		Stage stage = (Stage) bMas.getScene().getWindow();
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
