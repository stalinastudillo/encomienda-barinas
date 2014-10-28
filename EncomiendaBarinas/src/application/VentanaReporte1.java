package application;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class VentanaReporte1 extends Application {

	@FXML
	public AnchorPane anchorPane;
	
	@FXML
	public Label lNombre;
	
	public Stage stageI;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		stageI = primaryStage;
		stageI.setTitle("REPORTE UNO");
		
		ContextoEncomienda.getInstance().setIdentificadorVentana("REPORTEUNO");
		FXMLLoader cargador = new FXMLLoader(Main.class.getResource("Reporteuno.fxml"));
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
