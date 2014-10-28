package application;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class VentanaDestinatario extends Application{

	@FXML
	public AnchorPane anchorPane;
	
	public Stage stageI;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		stageI = primaryStage;
		stageI.setTitle("DESTINATARIO");
		
		FXMLLoader cargador = new FXMLLoader(Main.class.getResource("InforDestinatario.fxml"));
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
