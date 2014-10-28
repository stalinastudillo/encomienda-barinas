package application;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.LinkedHashMap;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import data.PesoTarifa;
import data.PrecioTarifa;
import data.Tarifa;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ConfAsociarTarifaPesoController {

	@FXML	
	private Label lTarifa;
	
	@FXML	
	private Label lPeso;
		
	@FXML	
	private Label lPrecio;
	
	@FXML
	private Label lCodigo;
	
	@FXML
	private ChoiceBox cbPeso;
	
	@FXML
	private ChoiceBox cbTarifa;
	
	@FXML
	private TextField tfPrecio;	
	
	@FXML
	private Button bEditar;
	
	@FXML
	private Button bMas;
	
	@FXML
	private Button bMenos;
	
	@FXML
	private Button bCancelar;
	
	@FXML
	private Label lMsj;
	
	private ObservableList<Tarifa> TarifaList = FXCollections.observableArrayList();
	private ObservableList<String> opcionTarifa = FXCollections.observableArrayList();
	
	private Map<String,Integer> MapTarifa = new LinkedHashMap<String, Integer>();
	private Map<String,Integer> MapPeso = new LinkedHashMap<String, Integer>();
	
	private String tarifaSeleccionada = null;
	
	Query queryResultTarifa;
	
	private ObservableList<PesoTarifa> PesoList = FXCollections.observableArrayList();
	private ObservableList<String> opcionPeso;
	
	Query queryResultPeso;
	
	private ObservableList<PrecioTarifa> PrecioPesoList = FXCollections.observableArrayList();
	
	Query queryResultPesoPrecio;
	
	Query queryUnicoPeso;
	
	private boolean bandTarifa = false, bandPeso = false, bandPrecio = false, bandNuevo = false, bandEditar = false;
	private boolean bandSiExiste = false;
	
	@FXML
	private TableView<PrecioTarifa> tvTabla = new TableView<PrecioTarifa>();	
		
	@FXML
	private TableColumn<PrecioTarifa,String> tcTarifa;
		
	@FXML
	private TableColumn<PrecioTarifa,String> tcPeso;
	
	@FXML
	private TableColumn<PrecioTarifa,String> tcMonto;	
	
	@FXML
	private TableColumn<PrecioTarifa,String> tcUnidad;
			
	private ObservableList<PrecioTarifa> itemsData = FXCollections.observableArrayList();
	
	private ObservableList<PrecioTarifa> itemsPrecioTarifa = FXCollections.observableArrayList();
	
	Query queryResultPrecioTarifa;
	
	PrecioTarifa objPT = new PrecioTarifa();
	
	int posedit=0;
		
	@FXML
	private void initialize(){	
				
		tcTarifa.setCellValueFactory(new PropertyValueFactory<PrecioTarifa,String>("tarifa"));
		tcPeso.setCellValueFactory(new PropertyValueFactory<PrecioTarifa,String>("PesoTarifa"));
		
		tcUnidad.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PrecioTarifa, String>, ObservableValue<String>>(){
			@Override
			public ObservableValue<String> call(CellDataFeatures<PrecioTarifa, String> arg0) {
				return new SimpleStringProperty(""+arg0.getValue().getPesoTarifa().getUndMedida().getDescripcion());
			}			
		});
			
		tcMonto.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PrecioTarifa,String>, ObservableValue<String>>(){

			     @Override
			     public ObservableValue<String> call(CellDataFeatures<PrecioTarifa,String> arg0) {
			      // TODO Auto-generated method stub
			      DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
			 	  simbolos.setDecimalSeparator('.');
			      DecimalFormat xx = new DecimalFormat("##.00",simbolos);
			      return new SimpleStringProperty(""+xx.format(arg0.getValue().getMonto()) );
			     }
		});        
				
//		tcTarifa.setCellFactory(new Callback<TableColumn<PrecioTarifa, String>, TableCell<PrecioTarifa, String>>() {
//			   @Override
//			   public TableCell<PrecioTarifa, String> call(TableColumn<PrecioTarifa, String> arg0) {
//			    // TODO Auto-generated method stub
//			    return new TableCellFormat();
//			   }
//		});
//		
//		tcPeso.setCellFactory(new Callback<TableColumn<PrecioTarifa, String>, TableCell<PrecioTarifa, String>>() {
//			   @Override
//			   public TableCell<PrecioTarifa, String> call(TableColumn<PrecioTarifa, String> arg0) {
//			    // TODO Auto-generated method stub
//			    return new TableCellFormat();
//			   }
//		});
//		
//		tcUnidad.setCellFactory(new Callback<TableColumn<PrecioTarifa, String>, TableCell<PrecioTarifa, String>>() {
//			   @Override
//			   public TableCell<PrecioTarifa, String> call(TableColumn<PrecioTarifa, String> arg0) {
//			    // TODO Auto-generated method stub
//			    return new TableCellFormat();
//			   }
//		});
//		
//		tcMonto.setCellFactory(new Callback<TableColumn<PrecioTarifa, String>, TableCell<PrecioTarifa, String>>() {
//			   @Override
//			   public TableCell<PrecioTarifa, String> call(TableColumn<PrecioTarifa, String> arg0) {
//			    // TODO Auto-generated method stub
//			    return new TableCellFormat();
//			   }
//		});
		
		tvTabla.setColumnResizePolicy(tvTabla.UNCONSTRAINED_RESIZE_POLICY);	
		
		try{			
			Session sesion1 = openSesion();	

			//------------- CARGA DE TARIFA				
			queryResultTarifa = sesion1.createQuery("from Tarifa");	
			TarifaList = FXCollections.observableArrayList(queryResultTarifa.list());
			
			for (int r=0;r<TarifaList.size();r++){
				MapTarifa.put(TarifaList.get(r).getDescripcion(),TarifaList.get(r).getCodigo());
				opcionTarifa.add(TarifaList.get(r).getDescripcion());
			}
			
			cbTarifa.setItems(opcionTarifa);
			closeSesion(sesion1);

		}catch(HibernateException e){
			e.printStackTrace();
		}		
		
		cbTarifa.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){			
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				System.out.println(" holahola " + opcionTarifa.get(arg2.intValue()));
				
				tarifaSeleccionada = opcionTarifa.get(arg2.intValue());
				System.out.println(" - - - - - - --- - - - - - - - - - - - - - - - - - - ");
				System.out.println("tarifa seleccionada:   "+tarifaSeleccionada+ " desde el hlm: " + MapTarifa.get(tarifaSeleccionada));
				lMsj.setVisible(false);
				bMas.setDisable(false);
				tfPrecio.setDisable(false);
				cbPeso.setDisable(false);
				cargaPeso();	
				bandTarifa=true;
				tfPrecio.setText("");
				
				if (bandTarifa){
					try{							
						itemsPrecioTarifa.clear();
						final Session sesion2 = openSesion();
						
						queryResultPrecioTarifa = sesion2.createQuery("from PrecioTarifa where codTarifa = :cd");
						queryResultPrecioTarifa.setInteger("cd", TarifaList.get(arg2.intValue()).getCodigo());
						
						itemsPrecioTarifa = FXCollections.observableArrayList(queryResultPrecioTarifa.list());
						
						if (itemsPrecioTarifa.isEmpty())
							System.out.println("es null");
						
						tvTabla.setItems(itemsPrecioTarifa);
						
						closeSesion(sesion2);
					}catch(HibernateException e){
						e.printStackTrace();
					}
				}
		}});
		
		tfPrecio.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
		    	bandPrecio = true;
		    	if (tfPrecio.getText().compareTo("")==0)
		    		bandPrecio = false;
		    	cbTarifa.setDisable(true); cbTarifa.setOpacity(1);
		    	lMsj.setVisible(false);		
		    }
		});
		
		tvTabla.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PrecioTarifa>(){
			@Override
			public void changed(ObservableValue<? extends PrecioTarifa> arg0, PrecioTarifa arg1, PrecioTarifa arg2) {
				
				if (!itemsPrecioTarifa.isEmpty()){															
					lCodigo.setText(String.valueOf(arg2.getCodigo()));
					for (int y=0;y<itemsPrecioTarifa.size();y++){
						if (lCodigo.getText().compareTo(String.valueOf(itemsPrecioTarifa.get(y).getCodigo())) == 0){
							posedit=y;
						}
					}
					// cargar el combobox de peso segun el registro seleccionado para actualizar
					
					opcionPeso=null;    opcionPeso=FXCollections.observableArrayList();	
					opcionPeso.add(arg2.getPesoTarifa().toString());
					cbPeso.setItems(opcionPeso);
					cbPeso.setDisable(true); cbPeso.setOpacity(1);	
					cbPeso.getSelectionModel().select(arg2.getPesoTarifa().toString());
					
					cbTarifa.setDisable(true); cbTarifa.setOpacity(1);
					System.out.println("Precio del registro: "+ arg2.getMonto());
					tfPrecio.setVisible(true);
					tfPrecio.setText(String.valueOf(arg2.getMonto()));
					
					bMas.setVisible(false);
					bEditar.setDisable(false);
					bMenos.setDisable(false);
				}			
			}
		});		
			
		tfPrecio.addEventHandler(javafx.scene.input.KeyEvent.KEY_TYPED, libreria.decimalValidacion(10));
	}
	
	private class TableCellFormat extends TableCell<PrecioTarifa, String>{
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            this.setText(item);
            this.setAlignment(Pos.BASELINE_CENTER);
        }
    }
	
	private void cargaPeso(){
		System.out.println("Carga peso");
		System.out.println(MapTarifa.get(tarifaSeleccionada));
		bandSiExiste=false;	cbPeso.setDisable(false);
		try{			
			Session sesion3 = openSesion();	
			queryResultPesoPrecio = sesion3.createQuery("from PrecioTarifa where codTarifa = :cod");
			queryResultPesoPrecio.setInteger("cod",MapTarifa.get(tarifaSeleccionada));
			PrecioPesoList = FXCollections.observableArrayList(queryResultPesoPrecio.list());
								
			queryResultPeso = sesion3.createQuery("from PesoTarifa");
			PesoList = FXCollections.observableArrayList(queryResultPeso.list());
			opcionPeso=null;    opcionPeso=FXCollections.observableArrayList();	
			MapPeso = null;		MapPeso = new LinkedHashMap<String, Integer>();
			
			if ((PrecioPesoList.size()>0) && (PrecioPesoList.size()< PesoList.size())){
				for (int a=0;a<PesoList.size();a++){
					for (int b=0;b<PrecioPesoList.size();b++){
						if (PesoList.get(a).getCodigo() == PrecioPesoList.get(b).getPesoTarifa().getCodigo()){
							bandSiExiste = true;
							break;								
						}
					}
					if (!bandSiExiste){
						opcionPeso.add(PesoList.get(a).toString());
						MapPeso.put(PesoList.get(a).toString(),PesoList.get(a).getCodigo());
					}												
					bandSiExiste = false;
				}							
			}else if (PrecioPesoList.size() == 0){
				bMenos.setDisable(true);
				bEditar.setDisable(true);				
				lMsj.setVisible(true);
				lMsj.setText("No existen pesos asociados a la tarifa seleccionada");
				for (int a=0;a<PesoList.size();a++){
					opcionPeso.add(PesoList.get(a).toString());
					MapPeso.put(PesoList.get(a).toString(),PesoList.get(a).getCodigo());
				}
				cbTarifa.setDisable(false); cbTarifa.setOpacity(1);
			}else if (PrecioPesoList.size() == PesoList.size()){
				lMsj.setVisible(true);
				lMsj.setText("Todos los pesos existentes ya han sido asociados");
				bMas.setDisable(true);
				tfPrecio.setDisable(true);
				cbPeso.setDisable(true);
			}
			System.out.println("map: "+MapPeso);	
			cbPeso.setItems(opcionPeso);
			closeSesion(sesion3);
		}catch(HibernateException e){
			e.printStackTrace();
		}			
	}
	
	@FXML
	private void actionBotonMas(){
		System.out.println("Mas  "+tfPrecio.getText());	
		bandNuevo = true;
		bEditar.setDisable(false);
		bMenos.setDisable(false);
		cbPeso.setDisable(false); cbPeso.setOpacity(1);	
		
		PrecioTarifa objpf = new PrecioTarifa();
		PesoTarifa objPeso;
				
		try{
			itemsPrecioTarifa.clear();
			Session sesion1 = openSesion();			
			queryResultPrecioTarifa = sesion1.createQuery("from PrecioTarifa where codTarifa = :ct and codPesoTarifa = :cpt");
			System.out.println("tarifa seleccionada:  "+ TarifaList.get(cbTarifa.getSelectionModel().getSelectedIndex()).getCodigo()  );
			System.out.println("peso seleccionado codigo:  "+  MapPeso.get(cbPeso.getSelectionModel().getSelectedItem().toString()));
			System.out.println("peso seleccionado:  "+  cbPeso.getSelectionModel().getSelectedItem().toString() );
			
	   	    queryResultPrecioTarifa.setInteger("ct", TarifaList.get(cbTarifa.getSelectionModel().getSelectedIndex()).getCodigo());
			queryResultPrecioTarifa.setInteger("cpt", MapPeso.get(cbPeso.getSelectionModel().getSelectedItem().toString()));				
			itemsPrecioTarifa = FXCollections.observableArrayList(queryResultPrecioTarifa.list());
			
			System.out.println("cuando no hay nada:  "+itemsPrecioTarifa.isEmpty() + " / size: " +itemsPrecioTarifa.size());	
			if (itemsPrecioTarifa.isEmpty()){
				//Consulta para traer de tabla peso, el objeto segun el codigo que esta en Map de Peso
				queryUnicoPeso = sesion1.createQuery("from PesoTarifa where codigo = :codp");
				queryUnicoPeso.setInteger("codp", MapPeso.get(cbPeso.getSelectionModel().getSelectedItem().toString()));
				queryUnicoPeso.setMaxResults(1);
				objPeso = new PesoTarifa();
				objPeso = (PesoTarifa) queryUnicoPeso.uniqueResult();
								
				objpf.setTarifa(TarifaList.get(cbTarifa.getSelectionModel().getSelectedIndex()));
				objpf.setPesoTarifa(objPeso);
				objpf.setMonto(Double.parseDouble(tfPrecio.getText()));
				sesion1.save(objpf);
				lMsj.setVisible(true);
				lMsj.setText("Registro guardado exitosamente");
				
				queryResultPrecioTarifa = sesion1.createQuery("from PrecioTarifa where codTarifa = :ct");
				queryResultPrecioTarifa.setInteger("ct", TarifaList.get(cbTarifa.getSelectionModel().getSelectedIndex()).getCodigo());
				itemsPrecioTarifa = FXCollections.observableArrayList(queryResultPrecioTarifa.list());
				
				System.out.println("tamaño para la tabla " + itemsPrecioTarifa.size());
				tvTabla.setItems(itemsPrecioTarifa);
				
			}else if (!itemsPrecioTarifa.isEmpty()){
				lMsj.setVisible(true);
				lMsj.setText("Relación tarifa y precios existentes");
			}	
			
			closeSesion(sesion1);
		}catch(HibernateException e){
			e.printStackTrace();
		}			
		cargaPeso();
		tfPrecio.setText("");
		cbTarifa.setDisable(false); cbTarifa.setOpacity(1);	
	}
	
	@FXML
	private void actionBotonMenos(){
		System.out.println("menos " + lCodigo.getText() + "   " + cbTarifa.getSelectionModel().getSelectedIndex());
		PrecioTarifa objpf;
		try{
			itemsPrecioTarifa.clear();
			Session sesion1 = openSesion();
					
			queryResultPrecioTarifa = sesion1.createQuery("from PrecioTarifa where codigo = :codt");
			queryResultPrecioTarifa.setInteger("codt", Integer.parseInt(lCodigo.getText()) );
			queryResultPrecioTarifa.setMaxResults(1);
			objpf = new PrecioTarifa();
			objpf = (PrecioTarifa) queryResultPrecioTarifa.uniqueResult();
			sesion1.delete(objpf);	

			lMsj.setText("Registro eliminado exitosamente");
			lMsj.setVisible(true);
			System.out.println("listo!: "+ TarifaList.get(cbTarifa.getSelectionModel().getSelectedIndex()).getCodigo() );
			queryResultPrecioTarifa = sesion1.createQuery("from PrecioTarifa where codTarifa = :ct");
			queryResultPrecioTarifa.setInteger("ct", TarifaList.get(cbTarifa.getSelectionModel().getSelectedIndex()).getCodigo() );
			itemsPrecioTarifa = FXCollections.observableArrayList(queryResultPrecioTarifa.list());					
			tvTabla.setItems(itemsPrecioTarifa);
			
			tfPrecio.setText("");			
			closeSesion(sesion1);
		}catch(HibernateException e){
			e.printStackTrace();
		}		
		bMas.setVisible(true);
		cargaPeso();
	}
	
	@FXML
	private void actionBotonEditar(){
		bandEditar = true;
		PrecioTarifa objpf;
		
		try{
			Session sesion1 = openSesion();		
			objpf = (PrecioTarifa) sesion1.get(PrecioTarifa.class, Integer.parseInt(lCodigo.getText()));
			objpf.setMonto(Double.parseDouble(tfPrecio.getText()));
			sesion1.update(objpf);		
			
			lMsj.setText("Registro actualizado exitosamente");
			lMsj.setVisible(true);	
			
			itemsPrecioTarifa.set(posedit, objpf);			
			tvTabla.setItems(itemsPrecioTarifa);
			
			closeSesion(sesion1);
		}catch(HibernateException e){
			e.printStackTrace();
		}	
		tfPrecio.setText("");
		cbTarifa.setDisable(false); cbTarifa.setOpacity(1);
		bMas.setDisable(false); bMas.setVisible(true); bMas.setOpacity(1);
		cargaPeso();
		cbPeso.setDisable(false); cbPeso.setOpacity(1);
	}	
	
	@FXML
	private void actionBotonCancelar(){	
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
}
