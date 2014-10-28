package application;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class VentanaUnidad extends Application {

	@FXML
	public AnchorPane anchorPane;
	
	@FXML
	public Label lNombre;
	
	public Stage stageI;
	
	//public static String IdentVentana;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		stageI = primaryStage;
		stageI.setTitle("UNIDAD");
		//IdentVentana = "UNIDAD";
		ContextoEncomienda.getInstance().setIdentificadorVentana("UNIDAD");
		FXMLLoader cargador = new FXMLLoader(Main.class.getResource("ConfTarifa.fxml"));
		anchorPane = (AnchorPane) cargador.load();			
		Scene scene = new Scene(anchorPane);
		scene.getStylesheets().add(getClass().getResource("estilo.css").toExternalForm());
		
		stageI.setScene(scene);		
		stageI.initStyle(StageStyle.DECORATED);
		stageI.show();				
	}
	
	public void mostrar(String cadena){
		stageI.setTitle(cadena);		
		stageI.show();		
	}
	
	public void ocultar(){		
	    stageI.close();
	}
}
