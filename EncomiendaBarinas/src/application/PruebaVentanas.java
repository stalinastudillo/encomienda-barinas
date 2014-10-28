package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class PruebaVentanas extends Application{

	private Stage stagePrincipal;
	
    private AnchorPane rootPane;
    
    @Override
    public void start(Stage stagePrincipal) throws Exception {
    	System.out.println("pruebaVentanas");
    	this.stagePrincipal = stagePrincipal;
        mostrarVentanaPrincipal();       
    } 
    
    public void mostrarVentanaPrincipal() {
    	System.out.println("mostrarVentanaPrincipal  ");
        try {        	
            FXMLLoader loader = new FXMLLoader(PruebaVentanas.class.getResource("EncomiendaPpal.fxml"));
                       
            rootPane=(AnchorPane) loader.load();
            Scene scene = new Scene(rootPane);
            stagePrincipal.setScene(scene);
            
            stagePrincipal.setOnCloseRequest(new EventHandler<WindowEvent>(){
              @Override public void handle(WindowEvent event) {
            	  ContextoEncomienda.getInstance().setBanderaNuevaFactura(false);
            	  //event.consume();           //Consumar el evento
              }  
            });
                 	
            stagePrincipal.show();
        } catch (IOException e) {
            //tratar la excepción.
        }
   }
 
    public void mostrarVentanaDestinatario() throws Exception {
    	System.out.println("mostrarVentanaDestinatario");
    	
        try {
            FXMLLoader loader = new FXMLLoader(PruebaVentanas.class.getResource("InforDestinatario.fxml"));
            AnchorPane ventanaD = (AnchorPane) loader.load();
            Stage ventana = new Stage();
            ventana.initModality(Modality.WINDOW_MODAL);
            ventana.initOwner(stagePrincipal);
            Scene scene = new Scene(ventanaD);
            ventana.setScene(scene);
            DestinatarioController controller = loader.getController();
            controller.setProgramaPrincipal(this);
            ventana.show(); 
        } catch (Exception e) {
            //tratar la excepción
        }
    }   
    
    public void mostrarVentanaRemitente()throws Exception{
    	System.out.println("mostrarVentanaRemitente");
    	
    	try {
            FXMLLoader loader = new FXMLLoader(PruebaVentanas.class.getResource("InforRemitente.fxml"));
            AnchorPane ventanaR = (AnchorPane) loader.load();
            Stage ventana = new Stage();
            ventana.initModality(Modality.WINDOW_MODAL);
            ventana.initOwner(stagePrincipal);
            Scene scene = new Scene(ventanaR);
            ventana.setScene(scene);
            RemitenteController controller = loader.getController();
            controller.setProgramaPrincipal(this);
            ventana.show(); 
        } catch (Exception e) {
            //tratar la excepción
        }
    }
    
    public void mostrarVentanaValorDeclarado(){
    	System.out.println("mostrarVentanaValorDeclarado");
    	
    	try{
    		FXMLLoader loader = new FXMLLoader(PruebaVentanas.class.getResource("InforValorDeclarado.fxml"));
            AnchorPane ventanaR = (AnchorPane) loader.load();
            Stage ventana = new Stage();
            ventana.initOwner(stagePrincipal);
            ventana.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(ventanaR);
            ventana.setScene(scene);
            ValorDeclaradoController controller = loader.getController();
            controller.setProgramaPrincipal(this);
            ventana.show(); 
    	}catch(Exception e){
    		 //tratar la excepción
    	}
    }
    
    public void mostrarVentanaFormaPago(){
    	System.out.println("mostrarVentanaFormaPago");
    	
    	try{
    		FXMLLoader loader = new FXMLLoader(PruebaVentanas.class.getResource("InforFormaPago.fxml"));
    		AnchorPane ventanaR = (AnchorPane) loader.load();
    		Stage ventana = new Stage();
    		ventana.initOwner(stagePrincipal);
    		ventana.initModality(Modality.WINDOW_MODAL);
    		Scene scene = new Scene(ventanaR);
            ventana.setScene(scene);
            FormaPagoController controller = loader.getController();
            controller.setProgramaPrincipal(this);
            ventana.show(); 
    	}catch(Exception e){
    		 //tratar la excepción
    	}
    }
    
}
