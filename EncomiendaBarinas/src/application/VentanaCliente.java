package application;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class VentanaCliente extends Application{

	@FXML
	public AnchorPane anchorPane;	
	
	public Stage stageI;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		stageI = primaryStage;
		stageI.setTitle("CLIENTE");
		
		FXMLLoader cargador = new FXMLLoader(Main.class.getResource("InforCliente.fxml"));
		anchorPane = (AnchorPane) cargador.load();		
		Scene scene = new Scene(anchorPane);
		scene.getStylesheets().add(getClass().getResource("estilo.css").toExternalForm());	
		stageI.setScene(scene);		
		stageI.initStyle(StageStyle.DECORATED);
		stageI.show();	
	}	

	public void mostrar(){		
		stageI.show();		
	}
	
	public void ocultar(){		
	    stageI.close();
	}
}
