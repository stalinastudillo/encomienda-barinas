package application;


/*
 * al eliminar el ultimo de la lista debe mostrar el anterior. 
 * aqui no funciona el fire de siguiente..
 */

import java.util.ListIterator;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import data.CiudadDestino;
import data.Cliente;
import data.Factura;
import data.Oficina;
import data.Tarifa;
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
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

public class ClienteController {

	@FXML
	private TextField tfRIFCedula;
	
	@FXML
	private TextField tfNombre;
	
	@FXML
	private TextField tfApellido;
	
	@FXML
	private TextArea tfDireccion;
	
	@FXML
	private TextField tfTelefono;	
	
	@FXML
	private TextField tfCorreo;
		
	@FXML
	private Label lAlertaCI;
	
	@FXML
	private Label lAlertaMsj;
	
	@FXML
	private Label lAlertaRIFCedula;
	
	@FXML
	private ChoiceBox cbRIFCedula;
	
	@FXML
	private ChoiceBox cbTarifa;
	
	@FXML
	private Button bAceptar;
	
	@FXML
	private Button bCancelar;
	
	@FXML
	private Button bEliminar;
	
	@FXML
	private Button bLimpiar;

	@FXML
	private Button bAnterior;
	
	@FXML
	private Button bSiguiente;
	
	@FXML
	private Button bPrimero;
	
	@FXML
	private Button bUltimo;
	
	@FXML
	private Label lCodigoReg;
	
	Tooltip ttAceptar = new Tooltip();
	Tooltip ttLimpiar = new Tooltip();
	Tooltip ttCancelar = new Tooltip();
	Tooltip ttEliminar = new Tooltip();
	Tooltip ttSiguiente = new Tooltip();
	Tooltip ttAnterior = new Tooltip();
	Tooltip ttPrimero = new Tooltip();
	Tooltip ttUltimo = new Tooltip();
		
	boolean bandingreso = false;
		
	Query queryResultCliente,queryResultCliente2;
	Query queryResultTarifa, queryResultOficina;
	ObservableList<Cliente> clienteList;
	ObservableList<Oficina> oficinaList= FXCollections.observableArrayList();
	private ObservableList<Tarifa> TarifaList = FXCollections.observableArrayList();
	private ObservableList<String> opcionTarifa = FXCollections.observableArrayList();
		
	ListIterator listite;
	
	Cliente objc,objsig;
	
	boolean band_modif_cliente = false, bandCedulaNatural = false, bandCedulaJuridico = false;
			
	char [] v;
	
	Cliente objCliente = new Cliente();
		
	@FXML
	private void initialize(){				
		bSiguiente.setDisable(true);
		bUltimo.setDisable(true);
		
		ttAceptar.setText("Guardar información");
		ttLimpiar.setText("Limpiar pantalla");
		ttCancelar.setText("Volver al menú");
		ttEliminar.setText("Eliminar registro");
		ttAnterior.setText("Anterior registro");
		ttSiguiente.setText("Siguiente registro");
		ttPrimero.setText("Primer registro");
		ttUltimo.setText("Ultimo registro");
		
		bAceptar.setTooltip(ttAceptar);
		bLimpiar.setTooltip(ttLimpiar);
		bCancelar.setTooltip(ttCancelar);
		bEliminar.setTooltip(ttEliminar);
		bAnterior.setTooltip(ttAnterior);
		bSiguiente.setTooltip(ttSiguiente);
		bPrimero.setTooltip(ttPrimero);
		bUltimo.setTooltip(ttUltimo);			
		
		try{				
			Session sesion1 = openSesion();
			
			queryResultOficina = sesion1.createQuery("from Oficina");
			oficinaList = FXCollections.observableArrayList(queryResultOficina.list());
						
			queryResultTarifa = sesion1.createQuery("from Tarifa");
			TarifaList = FXCollections.observableArrayList(queryResultTarifa.list());
			for (int r=0;r<TarifaList.size();r++){
				opcionTarifa.add(TarifaList.get(r).getDescripcion());
			}
			cbTarifa.setItems(opcionTarifa);
			
			queryResultCliente = sesion1.createQuery("from Cliente order by Codigo desc");			
			queryResultCliente.setMaxResults(1);
			objCliente = (Cliente) queryResultCliente.uniqueResult();
			
			if (objCliente!=null){
				queryResultCliente2 = sesion1.createQuery("from Cliente");	
				System.out.println("Cantidad de clientes "+	queryResultCliente2.list().size());
				
				if (queryResultCliente2.list().size() == 1){
					bAnterior.setDisable(true);
					bSiguiente.setDisable(true);
					bUltimo.setDisable(true);
					bPrimero.setDisable(true);
				} 
				printCliente(objCliente);		
			}else{
				lAlertaMsj.setText("NO EXISTEN CLIENTES REGISTRADOS");
				lAlertaMsj.setVisible(true);
				bAnterior.setDisable(true);
				bPrimero.setDisable(true);
				bEliminar.setDisable(true);
			}
			closeSesion(sesion1);			
		}catch(HibernateException e){
			e.printStackTrace();
		}		
		
		cbRIFCedula.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){			
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {	
				lAlertaRIFCedula.setVisible(false);
				lAlertaMsj.setVisible(false);
				
				if (arg2.intValue() == 0){	
					bandCedulaNatural = true;
					bandCedulaJuridico = false;
				}else if (arg2.intValue() == 1){	
					bandCedulaJuridico = true;
					bandCedulaNatural = false;
				}	
				tfRIFCedula.setText("");
		}});	
	
		tfRIFCedula.textProperty().addListener(new ChangeListener<String>(){		
			@Override			 
			public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {			 			 	
			 	lAlertaCI.setVisible(true);
			 	lAlertaMsj.setVisible(false);
			 	lAlertaRIFCedula.setVisible(false);				 	
			 	v = newValue.toCharArray();
			 	if (newValue.equals("")){
			 		tfRIFCedula.setText("");
			 		tfNombre.setText("");
			 		tfApellido.setText("");
			 		tfDireccion.setText("");
			 		tfCorreo.setText("");
			 		tfTelefono.setText("");
			 		cbTarifa.getSelectionModel().select(-1);
			 		cbRIFCedula.getSelectionModel().select(-1);
			 	}
			 	if (v.length == 1){
			 		if ((v[0] == '1') || (v[0] == '2') || (v[0] == '3') || (v[0] == '4')
			 		   || (v[0] == '5') || (v[0] == '6') || (v[0] == '7') || (v[0] == '8')
			 		   || (v[0] == '9') || (v[0] == '0')){
			 				tfRIFCedula.setText("");
			 				v = null;
			 		}else if ((v[0] == 'V') && (bandCedulaNatural)){	
			 			tfRIFCedula.setText("V-");
			 			v = tfRIFCedula.getText().toCharArray();
			 		}else if ((v[0] == 'E') && (bandCedulaNatural)){ 
			 			tfRIFCedula.setText("E-");
			 			v = tfRIFCedula.getText().toCharArray();
					}else if ((v[0] == 'J') && (bandCedulaJuridico)){ 
			 			tfRIFCedula.setText("J-");
			 			v = tfRIFCedula.getText().toCharArray();
					}else if ((v[0] == 'G') && (bandCedulaJuridico)){ 
			 			tfRIFCedula.setText("G-");
			 			v = tfRIFCedula.getText().toCharArray();
					}else{
			 			tfRIFCedula.setText("");
			 			v = null;
			 		}
			 	}else if ((v.length > 2) && (v.length <= 10)){				 		
			 		if ( (v[v.length-1] == '1') || (v[v.length-1] == '2') || (v[v.length-1] == '3')
			 				|| (v[v.length-1] == '4') || (v[v.length-1] == '5') || (v[v.length-1] == '6')
			 				|| (v[v.length-1] == '7') || (v[v.length-1] == '8') || (v[v.length-1] == '9')
			 				|| (v[v.length-1] == '0') )
			 			tfRIFCedula.setText(newValue);				 						 			
			 	}else if (v.length > 10)
			 		tfRIFCedula.setText(oldValue);				 				 	
			}		
		});
			
		tfNombre.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
		    	lAlertaCI.setVisible(false);
			 	lAlertaMsj.setVisible(false);
			 	lAlertaRIFCedula.setVisible(false);
			 	bAceptar.setDisable(false);
		    }
		});
		
		tfApellido.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
		    	lAlertaCI.setVisible(false);
			 	lAlertaMsj.setVisible(false);
			 	lAlertaRIFCedula.setVisible(false);
			 	bAceptar.setDisable(false);
		    }
		});
		
		tfDireccion.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
		    	lAlertaCI.setVisible(false);
			 	lAlertaMsj.setVisible(false);
			 	lAlertaRIFCedula.setVisible(false);
			 	bAceptar.setDisable(false);
		    }
		});
			
		tfTelefono.lengthProperty().addListener(new ChangeListener<Number>(){
		     @Override
		     public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {	           
		    	 lAlertaCI.setVisible(false);
				 lAlertaMsj.setVisible(false);
				 lAlertaRIFCedula.setVisible(false);
				 bAceptar.setDisable(false);
		     }		     
		});		
				
		
		tfNombre.addEventHandler(javafx.scene.input.KeyEvent.KEY_TYPED, libreria.letraValidacion(30));
		tfApellido.addEventHandler(javafx.scene.input.KeyEvent.KEY_TYPED, libreria.letraValidacion(30));
		tfTelefono.addEventHandler(javafx.scene.input.KeyEvent.KEY_TYPED, libreria.telefonoValidacion(11));
		
		if (objCliente!=null){
			if (cbRIFCedula.getSelectionModel().getSelectedItem().toString().compareTo("Natural") == 0){
				bandCedulaNatural=true;
				bandCedulaJuridico=false;
			}else if (cbRIFCedula.getSelectionModel().getSelectedItem().toString().compareTo("Juridico") == 0){
				bandCedulaNatural=false;
				bandCedulaJuridico=true;
			}
		}
	}
		
	@FXML
	private void actionBotonPrimero() throws Exception{
		bAnterior.setDisable(true);
		bPrimero.setDisable(true);
		bSiguiente.setDisable(false);
		bUltimo.setDisable(false);	
		lAlertaMsj.setVisible(false);
		lAlertaRIFCedula.setVisible(false);		
		
		try{
			Session sesion1 = openSesion();			
			queryResultCliente = sesion1.createQuery("from Cliente order by Codigo asc");	
			queryResultCliente.setMaxResults(1);
			objCliente = (Cliente) queryResultCliente.uniqueResult();			
			queryResultCliente2 = sesion1.createQuery("from Cliente");	
			
			if (queryResultCliente2.list().size() == 1){
				bAnterior.setDisable(true);
				bSiguiente.setDisable(true);
				bUltimo.setDisable(true);
				bPrimero.setDisable(true);
			} 			
			printCliente(objCliente);
			closeSesion(sesion1);			
		}catch(HibernateException e){
			e.printStackTrace();
		}
	}
	
	@FXML
	private void actionBotonUltimo() throws Exception{		
		System.out.println("ultimo ");
		bAnterior.setDisable(false);
		bPrimero.setDisable(false);
		bSiguiente.setDisable(true);
		bUltimo.setDisable(true);	
		lAlertaMsj.setVisible(false);
		lAlertaRIFCedula.setVisible(false);		
		try{
			Session sesion1 = openSesion();		
			queryResultCliente = sesion1.createQuery("from Cliente order by Codigo desc");
			queryResultCliente.setMaxResults(1);
			objCliente = (Cliente) queryResultCliente.uniqueResult();
			queryResultCliente2 = sesion1.createQuery("from Cliente");	
			
			if (queryResultCliente2.list().size() == 1){
				bAnterior.setDisable(true);
				bSiguiente.setDisable(true);
				bUltimo.setDisable(true);
				bPrimero.setDisable(true);
			}			
			printCliente(objCliente);
			closeSesion(sesion1);
		}catch(HibernateException e){
			e.printStackTrace();
		}				
	}		
	
	@FXML
	private void actionBotonAnterior() throws Exception{
		System.out.println("anterior");
		lAlertaMsj.setVisible(false);
		lAlertaRIFCedula.setVisible(false);	
		bSiguiente.setDisable(false);
		bUltimo.setDisable(false);	
		
		try{			
			int codcli = objCliente.getCodigo();
			Session sesion1 = openSesion();
			Cliente temp;
			
			queryResultCliente = sesion1.createQuery("from Cliente where Codigo < :cdd order by Codigo desc");
			queryResultCliente.setInteger("cdd",codcli);
			queryResultCliente.setMaxResults(1);
			
			objCliente = (Cliente) queryResultCliente.uniqueResult();
			
			if (objCliente != null){
				
				queryResultCliente2 = sesion1.createQuery("from Cliente");	
				
				if (queryResultCliente2.list().size() == 1){
					bAnterior.setDisable(true);
					bSiguiente.setDisable(true);
					bUltimo.setDisable(true);
					bPrimero.setDisable(true);
				} 
				
				printCliente(objCliente);
				queryResultCliente = sesion1.createQuery("from Cliente where Codigo < :cd order by Codigo desc");
				queryResultCliente.setInteger("cd",objCliente.getCodigo());
				queryResultCliente.setMaxResults(1);
				
				temp = (Cliente) queryResultCliente.uniqueResult();
				
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
	private void actionBotonSiguiente() throws Exception{	
		System.out.println("siguiente");
		lAlertaMsj.setVisible(false);
		lAlertaRIFCedula.setVisible(false);
		bAnterior.setDisable(false);
		bPrimero.setDisable(false);
		
		try{				
			System.out.println("siguiente::::  "+objCliente.getCodigo());
			int codcli = objCliente.getCodigo();
			Session sesion1 = openSesion();
			System.out.println("siguiente:::    ::::  "+objCliente.getCodigo());
			Cliente temp;
			
			queryResultCliente = sesion1.createQuery("from Cliente where Codigo > :cdd order by Codigo asc");
			queryResultCliente.setInteger("cdd",codcli);
			queryResultCliente.setMaxResults(1);
			
			objCliente = (Cliente) queryResultCliente.uniqueResult();
			System.out.println("siguiente::::  "+objCliente.getCodigo());
			if (objCliente != null){
				queryResultCliente2 = sesion1.createQuery("from Cliente");
				
				if (queryResultCliente2.list().size() == 1){
					bAnterior.setDisable(true);
					bSiguiente.setDisable(true);
					bUltimo.setDisable(true);
					bPrimero.setDisable(true);
				} 
				
				printCliente(objCliente);
				queryResultCliente = sesion1.createQuery("from Cliente where Codigo > :cd order by Codigo asc");
				queryResultCliente.setInteger("cd",objCliente.getCodigo());
				queryResultCliente.setMaxResults(1);
				temp = (Cliente) queryResultCliente.uniqueResult();
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
	private void actionBotonLimpiar() throws Exception{
		tfRIFCedula.setText("");
		tfNombre.setText("");
		tfApellido.setText(""); 
		tfDireccion.setText("");
		tfTelefono.setText("");
		lAlertaCI.setVisible(false);
	 	lAlertaMsj.setVisible(false);
	 	lAlertaRIFCedula.setVisible(false);
	}	
	
	@FXML
	private void actionBotonAceptar() throws Exception{
		lAlertaCI.setVisible(false);
		bEliminar.setDisable(false);
		try{
			Session sesion1 = openSesion();			
			queryResultCliente = sesion1.createQuery("from Cliente");		
			clienteList = FXCollections.observableArrayList(queryResultCliente.list());
			System.out.println("antes if");
			if ((tfRIFCedula.getText().compareTo("")!=0) && (tfNombre.getText().compareTo("")!=0) && (tfApellido.getText().compareTo("")!=0)
					&& (tfTelefono.getText().compareTo("")!=0) && (tfDireccion.getText().compareTo("")!=0) && (tfCorreo.getText().compareTo("")!=0)
					&& (cbRIFCedula.getSelectionModel().getSelectedIndex() != -1) ){
				System.out.println("despues if");
				for (Cliente o:clienteList){			

					if ( tfRIFCedula.getText().substring(2).compareTo(String.valueOf(o.getRifCedula())) == 0 ){
						band_modif_cliente = true;
					}
				}
				Cliente objc;
				queryResultOficina = sesion1.createQuery("from Oficina");	
				queryResultOficina.setMaxResults(1);
				Oficina objo = (Oficina) queryResultOficina.uniqueResult();	
				
				if (band_modif_cliente){
					System.out.println("es para modificar");					
					objc = (Cliente) sesion1.get(Cliente.class, Integer.parseInt(lCodigoReg.getText()));					
					
					objc.setNombre(tfNombre.getText());
					objc.setApellido(tfApellido.getText());
					objc.setDireccion(tfDireccion.getText());
					objc.setTelefono(tfTelefono.getText());
					objc.setCorreo(tfCorreo.getText());
					objc.setRifCedula(Integer.parseInt(tfRIFCedula.getText().substring(2)));
					
					if (cbTarifa.getSelectionModel().getSelectedIndex() != -1){
						objc.setTarifa(TarifaList.get(cbTarifa.getSelectionModel().getSelectedIndex()));					
					}				
					
					if (bandCedulaNatural && !bandCedulaJuridico){
						System.out.println("es natural, v e");
						if (tfRIFCedula.getText().charAt(0) == 'V'){
							objc.setTipoRifCedula("nv");
						}else if (tfRIFCedula.getText().charAt(0) == 'E'){
							objc.setTipoRifCedula("ne");
						}
												
					}else if (!bandCedulaNatural && bandCedulaJuridico){
						System.out.println("es juridico, j g");
						if (tfRIFCedula.getText().charAt(0) == 'J'){
							objc.setTipoRifCedula("jj");
						}else if (tfRIFCedula.getText().charAt(0) == 'G'){
							objc.setTipoRifCedula("jg");
						}
					}	
					objc.setOficina(objo);
					sesion1.update(objc);
					lAlertaMsj.setVisible(true);
					lAlertaMsj.setText("Registro actualizado exitosamente");
					band_modif_cliente = false;
				}else{ 				
					System.out.println("es para registrar nuevo");
					objc = new Cliente();
					objc.setNombre(tfNombre.getText());
					objc.setApellido(tfApellido.getText());	
					objc.setRifCedula(Integer.parseInt(tfRIFCedula.getText().substring(2)));			
					objc.setDireccion(tfDireccion.getText());
					objc.setTelefono(tfTelefono.getText());
					objc.setCorreo(tfCorreo.getText());
					 
					if (cbRIFCedula.getSelectionModel().getSelectedItem().toString().compareTo("Natural") == 0){
						
						if (tfRIFCedula.getText().charAt(0) == 'V'){
							objc.setTipoRifCedula("nv");
						}else if (tfRIFCedula.getText().charAt(0) == 'E'){
							objc.setTipoRifCedula("ne");
						}
												
					}else if (cbRIFCedula.getSelectionModel().getSelectedItem().toString().compareTo("Jurídico") == 0){
						if (tfRIFCedula.getText().charAt(0) == 'J'){
							objc.setTipoRifCedula("jj");
						}else if (tfRIFCedula.getText().charAt(0) == 'G'){
							objc.setTipoRifCedula("jg");
						}
					}	
					
					if (cbTarifa.getSelectionModel().getSelectedIndex() != -1){
						objc.setTarifa(TarifaList.get(cbTarifa.getSelectionModel().getSelectedIndex()));					
					}
							
					objc.setOficina(objo);					
					sesion1.save(objc);
					lAlertaMsj.setVisible(true);
					lAlertaMsj.setText("Registro guardado exitosamente");
					objCliente=objc;
				}
			}else{
				lAlertaMsj.setText("Debes ingresar todos los datos");
				lAlertaMsj.setVisible(true);
				lAlertaRIFCedula.setVisible(false);				
			}	
			closeSesion(sesion1);
		}catch(HibernateException e){
			e.printStackTrace();
		}
		
		try{
			Session sesion1 = openSesion();		
			queryResultCliente2 = sesion1.createQuery("from Cliente");	
			
			if (queryResultCliente2.list().size() > 1){
				bAnterior.setDisable(false);
				bPrimero.setDisable(false);
			} 
			
			closeSesion(sesion1);
		}catch(HibernateException e){
			e.printStackTrace();
		}
	}
	
	@FXML
	private void actionBotonEliminar() { 
		System.out.println("CODIGO CLIENTE borrar"+	objCliente.getCodigo());
		
		try{
			int codcli = objCliente.getCodigo();
			Session sesion1 = openSesion();
			Cliente temp,temp2;
			queryResultCliente = sesion1.createQuery("from Cliente where Codigo = :cdd");
			queryResultCliente.setInteger("cdd",codcli);
			queryResultCliente.setMaxResults(1);
			temp = (Cliente) queryResultCliente.uniqueResult();
			queryResultCliente2 = sesion1.createQuery("from Cliente order by Codigo desc");
			queryResultCliente2.setMaxResults(1);
			temp2 = (Cliente) queryResultCliente2.uniqueResult();
			sesion1.delete(temp);
			System.out.println(" - - -  aqui 0 - - - -");
			System.out.println(" - - -  aqui 000 - - - -");
			closeSesion(sesion1);
			lAlertaMsj.setText("Registro eliminado exitosamente");
			lAlertaMsj.setVisible(true);	
			System.out.println(" - - -  aqui 1 - - - -");
			if (temp.getCodigo() == temp2.getCodigo()){
				bUltimo.setDisable(false);
				bUltimo.fire();
				System.out.println(" - - -  aqui 2 - - - -");
			}else{
				System.out.println(" - - -  aqui 3 - - - -");
				bSiguiente.fire();
			}
		}catch(ConstraintViolationException ee){
			System.out.println(" - - -  aqui 4 - - - -");
			lAlertaMsj.setText("El cliente no se puede eliminar, se encuentra relacionado con factura");
			lAlertaMsj.setLayoutX(10);
			lAlertaMsj.setVisible(true);
			System.out.println(" - - -  aqui 5 - - - -");
		}catch(HibernateException e){
			System.out.println(" - - -  aqui 6 - - - -");
			//e.printStackTrace();
		}
	}
		
	@FXML
	private void actionBotonCancelar() throws Exception{
		System.out.println("Boton Cancelar");			
		Stage stage = (Stage) bCancelar.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	private void actionBotonClienteNuevo() throws Exception{
		tfRIFCedula.setText("");
 		tfNombre.setText("");
 		tfApellido.setText("");
 		tfDireccion.setText("");
 		tfCorreo.setText("");
 		tfTelefono.setText("");
 		cbTarifa.getSelectionModel().select(-1);
 		cbRIFCedula.getSelectionModel().select(-1);
 		bAceptar.setDisable(true);
 		bEliminar.setDisable(true);
	}
	
	private Session openSesion(){
		
		Session sesion = Main.sesionFactory.getCurrentSession();
		sesion.beginTransaction();
		
		return sesion;
	}
	
	private void closeSesion(Session sesion){
		
		sesion.getTransaction().commit();
	}
	
	private void activoTodosBotones(){
		bAnterior.setDisable(false);
		bPrimero.setDisable(false);
		bSiguiente.setDisable(false);
		bUltimo.setDisable(false);
	}
	
	private void activoAntDesactivoSig(){
		bAnterior.setDisable(false);
		bPrimero.setDisable(false);
		bSiguiente.setDisable(true);
		bUltimo.setDisable(true);	
	}
	
	private void printCliente(Cliente obj){
		
		lCodigoReg.setText(String.valueOf(obj.getCodigo()));	
		ObservableList<String> listaOpciones = FXCollections.observableArrayList();
		listaOpciones.add("Natural");	listaOpciones.add("Juridico");
		cbRIFCedula.setItems(listaOpciones);
		
//		System.out.println(" / / / / / / / / / / "+obj.getTipoRifCedula()+" / / / / / / / / / / ");
		
		if (obj.getTipoRifCedula().equals("nv")){
			System.out.println("nv");
			cbRIFCedula.getSelectionModel().select(0);
			tfRIFCedula.setText("V-"+String.valueOf(obj.getRifCedula()));
		}else if (obj.getTipoRifCedula().equals("ne")){
			System.out.println("ne");
			cbRIFCedula.getSelectionModel().select(0);
			tfRIFCedula.setText("E-"+String.valueOf(obj.getRifCedula()));
		}else if (obj.getTipoRifCedula().equals("jj")){
			cbRIFCedula.getSelectionModel().select(1);
			tfRIFCedula.setText("J-"+String.valueOf(obj.getRifCedula()));
		}else if (obj.getTipoRifCedula().equals("jg")){
			System.out.println("jg");
			cbRIFCedula.getSelectionModel().select(1);
			tfRIFCedula.setText("G-"+String.valueOf(obj.getRifCedula()));
		}	
		tfNombre.setText(obj.getNombre());
		tfApellido.setText(obj.getApellido());
		tfDireccion.setText(obj.getDireccion());
		tfTelefono.setText(obj.getTelefono());
		tfCorreo.setText(obj.getCorreo());
		
		for (int r=0;r<TarifaList.size();r++){
			if (obj.getTarifa()!=null){
				if ( obj.getTarifa().getDescripcion().equals(TarifaList.get(r).getDescripcion()) ){
					cbTarifa.getSelectionModel().select(r);
					break;
				}
			}
		}
	}
}
