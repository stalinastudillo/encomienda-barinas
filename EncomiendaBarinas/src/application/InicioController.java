package application;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.SQLGrammarException;

import sun.org.mozilla.javascript.internal.ast.WhileLoop;
import data.Cliente;
import data.Factura;
import data.PesoTarifa;
import data.Tarifa;
import data.TipoEmbalaje;
import data.Unidad;
import data.VariableConfiguracion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage; 

public class InicioController {
	
	@FXML
	private MenuBar mbMenu;
	
	@FXML
	private Label lAlertaMsj;
	
	Tooltip ttEncomienda = new Tooltip();
	Tooltip ttCliente = new Tooltip();
	
	VentanaCliente ventCliente;	
	VentanaConfComisiones ventComisiones;
	VentanaConfAsociarTarifaPeso ventConfAsociarTarifaPeso;
	VentanaConfPeso ventConfPeso;	
	VentanaConfTarifa ventConfTarifa;
	VentanaCiudadDestino ventCiudadDestino;
	VentanaTipoEmbalaje ventTipoEmbalaje;
	VentanaUnidad ventUnidad;
	VentanaConfVariable ventVariable;
	VentanaConfIpostel ventConfIpostel;
	VentanaReporte1 ventReporteUno;
	
	Query queryResultTipoEmbalaje, queryResultTarifa, queryResultUnidad, queryResultVarConf, queryResultPeso;
	private ObservableList<TipoEmbalaje> TipoEmbalajeList = FXCollections.observableArrayList();  //Carga datos de Tabla TipoEmbalaje 
	private ObservableList<Tarifa> TarifaList = FXCollections.observableArrayList();
	
	TipoEmbalaje objTipoEmbalaje;
	Tarifa objTarifa;
	Unidad objUnidad;
	VariableConfiguracion objVarConf;
	PesoTarifa objPeso;
	
	private boolean banderainicio = false;
	
	PruebaVentanas objPV;
	
	@FXML
	private void initialize(){
		ttEncomienda.setText("Facturar encomienda");
		ttCliente.setText("Gestionar clientes");
		try{						
			Session sesion1 = openSesion();
			queryResultTipoEmbalaje = sesion1.createQuery("from TipoEmbalaje");
			TipoEmbalajeList = FXCollections.observableArrayList(queryResultTipoEmbalaje.list());	
			closeSesion(sesion1);
		}catch(SQLGrammarException sq){
			System.out.println("No existen tablas desde INICIO CONTROLLER");
			banderainicio = true;
			Main.initdata(true);
			lAlertaMsj.setText("BASE DATOS GENERADA");
			lAlertaMsj.setVisible(true);
		}		
	}
	
	@FXML
	public void menuEncomienda() throws Exception{
		System.out.println("menu encomienda "+banderainicio);
		lAlertaMsj.setVisible(false);
		if (!banderainicio){			
			try{				
				Session sesion1 = openSesion();
				System.out.println("inicio - - - - - - - - - - - - - - - - - - - -  + + 3");
				queryResultTipoEmbalaje = sesion1.createQuery("from TipoEmbalaje");	
				queryResultTipoEmbalaje.setMaxResults(1);
				objTipoEmbalaje = new TipoEmbalaje();
				objTipoEmbalaje = (TipoEmbalaje) queryResultTipoEmbalaje.uniqueResult();					
				System.out.println("inicio - - - - - - - - - - - - - - - - - - - -  + + 4");
				closeSesion(sesion1);			
			}catch(HibernateException e){
				e.printStackTrace();
			}
			
			if (objTipoEmbalaje!=null){
				objPV = null;
				if (objPV == null){
					objPV = new PruebaVentanas();
					objPV.start(new Stage());
				}
			}else{
				lAlertaMsj.setText("DEBE INGRESAR INFORMACIÓN BÁSICA EN MÓDULO CONFIGURACIÓN");
				lAlertaMsj.setVisible(true);
			}
		}else{
			lAlertaMsj.setText("REGISTRAR INFORMACIÓN EN MÓDULO CONFIGURACIÓN");
			lAlertaMsj.setVisible(true);
		}			
	}
	
	@FXML
	public void menuCliente() throws Exception{
		System.out.println("menu cliente "+banderainicio);
		lAlertaMsj.setVisible(false);
		ventCliente = null;
		if (ventCliente == null){
			ventCliente = new VentanaCliente();
			ventCliente.start(new Stage());
		}
	}
		
	@FXML
	public void menuUnidad() throws Exception{
		System.out.println("menu Unidad");
		ventUnidad = null;
		lAlertaMsj.setVisible(false);
		if (ventUnidad == null){
			ventUnidad = new VentanaUnidad();
			ventUnidad.start(new Stage());
		}
	}
	
	@FXML
	public void menuTarifa() throws Exception{
		System.out.println("menu tarifa");
		ventConfTarifa = null;
		lAlertaMsj.setVisible(false);
		if (ventConfTarifa == null){
			ventConfTarifa = new VentanaConfTarifa();
			ventConfTarifa.start(new Stage());
		}
	}	
	
	@FXML
	public void menuPeso() throws Exception{
		System.out.println("menu peso");
		lAlertaMsj.setVisible(false);
		
		try{
			Session sesion1 = openSesion();			
			queryResultUnidad = sesion1.createQuery("from Unidad");	
			queryResultUnidad.setMaxResults(1);
			objUnidad = new Unidad();
			objUnidad = (Unidad) queryResultUnidad.uniqueResult();	
			closeSesion(sesion1);	
		}catch(HibernateException e){
			e.printStackTrace();
		}		
		
		if (objUnidad != null){
			ventConfPeso = null;
			lAlertaMsj.setVisible(false);
			if (ventConfPeso == null){
				ventConfPeso = new VentanaConfPeso();
				ventConfPeso.start(new Stage());
			}
		}else{
			lAlertaMsj.setText("NO EXISTE INFORMACIÓN EN UNIDAD");
			lAlertaMsj.setVisible(true);
		} 
	}
	
	@FXML
	public void	menuVariable() throws Exception{
		System.out.println("menu variable");
		ventVariable = null;
		lAlertaMsj.setVisible(false);
		if (ventVariable == null){
			ventVariable = new VentanaConfVariable();
			ventVariable.start(new Stage());
		}
	}
	
	@FXML
	public void menuCiudadDestino() throws Exception{
		System.out.println("menu ciudad destino");
		ventCiudadDestino = null;
		lAlertaMsj.setVisible(false);
		if (ventCiudadDestino == null){
			ventCiudadDestino = new VentanaCiudadDestino();
			ventCiudadDestino.start(new Stage());
		}
	}
	
	@FXML
	public void menuTipoEmbalaje() throws Exception{
		System.out.println("menu tipo embalaje");
		lAlertaMsj.setVisible(false);
		try{
			Session sesion1 = openSesion();			
			queryResultTarifa = sesion1.createQuery("from Tarifa");	
			queryResultTarifa.setMaxResults(1);
			objTarifa = new Tarifa();
			objTarifa = (Tarifa) queryResultTarifa.uniqueResult();	
			closeSesion(sesion1);	
		}catch(HibernateException e){
			e.printStackTrace();
		}
		
		if (objTarifa != null){
			ventTipoEmbalaje = null;			
			if (ventTipoEmbalaje == null){
				ventTipoEmbalaje = new VentanaTipoEmbalaje();
				ventTipoEmbalaje.start(new Stage());
			}
		}else{
			lAlertaMsj.setText("NO EXISTE INFORMACIÓN EN TARIFA");
			lAlertaMsj.setVisible(true);
		} 	
	}
	
	@FXML
	public void menuTarifaPeso() throws Exception{
		System.out.println("menu tarifaPeso");
		ventConfAsociarTarifaPeso = null;
		lAlertaMsj.setVisible(false);
		
		try{
			Session sesion1 = openSesion();			
			queryResultTarifa = sesion1.createQuery("from Tarifa");
			queryResultTarifa.setMaxResults(1);
			objTarifa = new Tarifa();
			objTarifa = (Tarifa) queryResultTarifa.uniqueResult();	
			queryResultPeso = sesion1.createQuery("from PesoTarifa");
			queryResultPeso.setMaxResults(1);
			objPeso = new PesoTarifa();
			objPeso = (PesoTarifa) queryResultPeso.uniqueResult();			
			closeSesion(sesion1);	
		}catch(HibernateException e){
			e.printStackTrace();
		}
		
		if ((objTarifa!=null) && (objPeso!=null)){
			if (ventConfAsociarTarifaPeso == null){
				ventConfAsociarTarifaPeso = new VentanaConfAsociarTarifaPeso();
				ventConfAsociarTarifaPeso.start(new Stage());
			}
		}else{
			lAlertaMsj.setText("NO EXISTE INFORMACIÓN EN TARIFA Y/O PESO");
			lAlertaMsj.setVisible(true);
		} 
	}
	
	@FXML
	public void menuDetalleVariable() throws Exception{
		System.out.println("menu detalle variable");
		lAlertaMsj.setVisible(false);
		
		try{
			Session sesion1 = openSesion();			
			queryResultVarConf = sesion1.createQuery("from VariableConfiguracion");	
			queryResultVarConf.setMaxResults(1);
			objVarConf = new VariableConfiguracion();
			objVarConf = (VariableConfiguracion) queryResultVarConf.uniqueResult();	
			closeSesion(sesion1);	
		}catch(HibernateException e){
			e.printStackTrace();
		}	
		
		if (objVarConf!=null){
			ventComisiones = null;
			if (ventComisiones == null){
				ventComisiones = new VentanaConfComisiones();
				ventComisiones.start(new Stage());
			}
		}else{
			lAlertaMsj.setText("NO EXISTE INFORMACIÓN EN VARIABLE");
			lAlertaMsj.setVisible(true);
		} 
	}
	
	@FXML
	public void menuIpostel() throws Exception{
		System.out.println("menu ipostel");
		lAlertaMsj.setVisible(false);
		
		try{
			Session sesion1 = openSesion();			
			queryResultUnidad = sesion1.createQuery("from Unidad");	
			queryResultUnidad.setMaxResults(1);
			objUnidad = new Unidad();
			objUnidad = (Unidad) queryResultUnidad.uniqueResult();	
			closeSesion(sesion1);	
		}catch(HibernateException e){
			e.printStackTrace();
		}		
		
		if (objUnidad != null){
			ventConfIpostel = null;
			lAlertaMsj.setVisible(false);
			if (ventConfIpostel == null){
				ventConfIpostel = new VentanaConfIpostel();
				ventConfIpostel.start(new Stage());
			}
		}else{
			lAlertaMsj.setText("NO EXISTE INFORMACIÓN EN TABLA UNIDAD");
			lAlertaMsj.setVisible(true);
		} 
	}
	
	@FXML
	public void menuReporte1() throws Exception{
		System.out.println("acción reporte 1");
		
		if (ventReporteUno == null){
			ventReporteUno = new VentanaReporte1();
			ventReporteUno.start(new Stage());
		}
			
	}
	
	@FXML
	public void menuSalir() throws Exception{
		System.out.println("acción salir");
		lAlertaMsj.setVisible(false);
		System.exit(1);
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
