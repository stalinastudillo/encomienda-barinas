package application;	

import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.SQLGrammarException;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import data.Banco;
import data.Bitacora;
import data.Ciudad;
import data.CiudadDestino;
import data.Cliente;
import data.DetalleVariableConfiguracion;
import data.Estado;
import data.Factura;
import data.FormaPago;
import data.Ipostel;
import data.Municipio;
import data.Oficina;
import data.PesoTarifa;
import data.PrecioTarifa;
import data.Privilegio;
import data.Tarifa;
import data.TipoEmbalaje;
import data.TipoTarjeta;
import data.Unidad;
import data.Usuario;
import data.Temporal;
import data.VariableConfiguracion;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.geometry.Side;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class Main extends Application {
		
	public static Configuration conf;
	public static SessionFactory sesionFactory;
	
	public static String con = "fabiola";
	
	public static Stage stagePrincipal;
	
	@FXML
	public AnchorPane root;
		
	@Override
	public void start(Stage primaryStage) {		

		primaryStage.setTitle("ENCOMIENDAS");		
		
		try{
			FXMLLoader cargador = new FXMLLoader(Main.class.getResource("Inicio.fxml"));			
		    root = (AnchorPane) cargador.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("estilo.css").toExternalForm());			
			primaryStage.setScene(scene);		
			primaryStage.initStyle(StageStyle.DECORATED);
			primaryStage.show();
			Screen screen = Screen.getPrimary();
			final Rectangle2D bounds = screen.getVisualBounds();
			
			primaryStage.setX(bounds.getMinX());
			primaryStage.setY(bounds.getMinY());
			primaryStage.setWidth(bounds.getWidth());
			primaryStage.setHeight(bounds.getHeight());
			
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>(){
	              @Override 
	              public void handle(WindowEvent event) {	            	  
	            	  System.exit(1);          
	              }  
	        });
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		initdata(false);
		launch(args);
	}
	
	public static void initdata(boolean band){
		try{				
			conf = new Configuration();
			conf.addAnnotatedClass(Bitacora.class);
			conf.addAnnotatedClass(Ciudad.class);
			conf.addAnnotatedClass(Cliente.class);
			conf.addAnnotatedClass(Estado.class);
			conf.addAnnotatedClass(Factura.class);
			conf.addAnnotatedClass(FormaPago.class);
			conf.addAnnotatedClass(Municipio.class);
			conf.addAnnotatedClass(Oficina.class);
			conf.addAnnotatedClass(Privilegio.class);
			conf.addAnnotatedClass(Tarifa.class);
			conf.addAnnotatedClass(PesoTarifa.class);
			conf.addAnnotatedClass(PrecioTarifa.class);
			conf.addAnnotatedClass(Unidad.class);
			conf.addAnnotatedClass(Ipostel.class);
			conf.addAnnotatedClass(TipoEmbalaje.class);
			conf.addAnnotatedClass(Usuario.class);			
			conf.addAnnotatedClass(CiudadDestino.class);
			conf.addAnnotatedClass(Banco.class);
			conf.addAnnotatedClass(TipoTarjeta.class);
			conf.addAnnotatedClass(VariableConfiguracion.class);
			conf.addAnnotatedClass(DetalleVariableConfiguracion.class);		
			
			Properties propie = new Properties();
			propie.setProperty("hibernate.connection.driver_class","com.mysql.jdbc.Driver");
					
			if (con == "local"){
				propie.setProperty("hibernate.connection.url", "jdbc:mysql://localhost/encomiendabarinas");
				propie.setProperty("hibernate.connection.username", "prueba");
				propie.setProperty("hibernate.connection.password", "12345");
			}			
			if (con == "fabiola"){
				propie.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3307/encomiendabarinas");
				propie.setProperty("hibernate.connection.username", "root");
				propie.setProperty("hibernate.connection.password", "root");
			}			
			if (con == "javier"){
				propie.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3309/encomiendabarinas");
				propie.setProperty("hibernate.connection.username", "prueba");
				propie.setProperty("hibernate.connection.password", "");
			}
			
			propie.setProperty("hibernate.connection.pool_size","2");
			propie.setProperty("hibernate.current_session_context_class","thread");
			
			propie.setProperty("hibernate.cache.provider_class","org.hibernate.cache.NoCacheProvider");
			propie.setProperty("hibernate.dialect","org.hibernate.dialect.MySQL5Dialect");
			propie.setProperty("hibernate.show_sql","true");
			
			propie.setProperty("hibernate.connection.zeroDateTimeBehavior","convertToNull");
			
			conf.addProperties(propie);
						
			ServiceRegistry sr = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();
			sesionFactory = conf.buildSessionFactory(sr);
				
			if (band){				
				new SchemaExport(conf).create(true,true);
			}
		}catch(HibernateException e){
			e.printStackTrace();
		}
	}

	public static Stage getStagePrincipal(){
		return stagePrincipal;
	}
	
	private static Session openSesion(){		
		Session sesion = sesionFactory.getCurrentSession();
		sesion.beginTransaction();
		
		return sesion;
	}
	
	private static void closeSesion(Session sesion){
		
		sesion.getTransaction().commit();
	}
}
