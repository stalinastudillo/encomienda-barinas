package application;

import data.ValorDeclarado;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ValorDeclaradoController {
		
	@FXML
	private TextField tfNombre;
	
	@FXML
	private TextField tfRIF;
	
	@FXML
	private TextField tfNroFactura;
	
	@FXML
	private TextField tfNroControl;
	
	@FXML
	private TextField tfMonto;
	
	@FXML
	private Button bAceptar;
	
	@FXML
	private Button bCancelar;
	
	EncomiendaController ec = new EncomiendaController();
	
	private boolean bandMonto=false, bandNombre=false, bandRIF=false, bandNroFactura=false, bandNroControl=false;
	
	private PruebaVentanas ProgramaPrincipal;
	
	public ValorDeclarado objVD = new ValorDeclarado(); 
	
	@FXML
	private void initialize(){		
		
		if (ContextoEncomienda.getInstance().getValorDeclarado().getMonto()!=null){
			tfNombre.setText(ContextoEncomienda.getInstance().getValorDeclarado().getNombre());
			tfRIF.setText(ContextoEncomienda.getInstance().getValorDeclarado().getRIF());
			tfNroControl.setText(ContextoEncomienda.getInstance().getValorDeclarado().getNroControl());
			tfNroFactura.setText(ContextoEncomienda.getInstance().getValorDeclarado().getNroFactura());
			tfMonto.setText(ContextoEncomienda.getInstance().getValorDeclarado().getMonto());
			bAceptar.setDisable(false);
		}
		
		tfNombre.textProperty().addListener(new ChangeListener<String>(){
			 @Override
			    public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
					 if (tfNombre.getText().equals("")){
						 bandNombre=false;
					 }else{
						 bandNombre=true;	
						 if ( (bandNombre && bandMonto && bandRIF && bandNroFactura && bandNroControl)
				            	   ||( (tfNombre.getText().compareTo("")!=0) &&
				            	      (tfRIF.getText().compareTo("")!=0) && 
				            	      (tfNroControl.getText().compareTo("")!=0) &&
				            	      (tfNroFactura.getText().compareTo("")!=0) &&
				            	      (tfMonto.getText().compareTo("")!=0)
				            	    ) ){
				           				bAceptar.setDisable(false);
				           		   }
					 }		
					 if (tfNombre.getText().equals(""))
						 bAceptar.setDisable(true);
			   }	
		});		
		
		tfRIF.focusedProperty().addListener(new ChangeListener<Boolean>(){
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue){
		    	if (!newPropertyValue){
		        	if (!tfRIF.getText().equals("")){		        		
		        		bandRIF=true;
		        		bAceptar.setDisable(false);
		        	}else if (tfRIF.getText().equals("")){
		        		bAceptar.setDisable(true);
		        		bandRIF=false;
		        	}
		    	}
		    	if (tfRIF.getText().equals("")){
		        	   bandRIF=false;
		        	   bAceptar.setDisable(true);
		        }
		    }
		});
		
		tfNroFactura.lengthProperty().addListener(new ChangeListener<Number>(){
		     @Override
		     public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {              
		    	 	
		           if(newValue.intValue() > oldValue.intValue()){
		               char ch = tfNroFactura.getText().charAt(oldValue.intValue());
		               if ( !(ch >= '0' && ch <= '9' ) && !(ch == ',') && !(ch == '.') ) {
		            	   tfNroFactura.setText(tfNroFactura.getText().substring(0,tfNroFactura.getText().length()-1)); 
		               }else{
		            	   bandNroFactura=true;	
		            	   if ( (bandNombre && bandMonto && bandRIF && bandNroFactura && bandNroControl)
				            	   ||( (tfNombre.getText().compareTo("")!=0) &&
				            	      (tfRIF.getText().compareTo("")!=0) && 
				            	      (tfNroControl.getText().compareTo("")!=0) &&
				            	      (tfNroFactura.getText().compareTo("")!=0) &&
				            	      (tfMonto.getText().compareTo("")!=0)
				            	    ) ){
				           				bAceptar.setDisable(false);
				           		   }
		               }		            	   
		          }
		           if (tfNroFactura.getText().equals("")){
		        	   bandNroFactura=false;
		        	   bAceptar.setDisable(true);
		           }
		     }		     
		});	
		
		tfNroControl.lengthProperty().addListener(new ChangeListener<Number>(){
		     @Override
		     public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {              
		    	 	
		           if(newValue.intValue() > oldValue.intValue()){
		               char ch = tfNroControl.getText().charAt(oldValue.intValue());
		               if ( !(ch >= '0' && ch <= '9' ) && !(ch == ',') && !(ch == '.') ) {
		            	   tfNroControl.setText(tfNroControl.getText().substring(0,tfNroControl.getText().length()-1)); 
		               }else{
		            	   bandNroControl=true;	
		            	   if ( (bandNombre && bandMonto && bandRIF && bandNroFactura && bandNroControl)
				            	   ||( (tfNombre.getText().compareTo("")!=0) &&
				            	      (tfRIF.getText().compareTo("")!=0) && 
				            	      (tfNroControl.getText().compareTo("")!=0) &&
				            	      (tfNroFactura.getText().compareTo("")!=0) &&
				            	      (tfMonto.getText().compareTo("")!=0)
				            	    ) ){
				           				bAceptar.setDisable(false);
				           	  }
		               }	            	   
		          }
		          if (tfNroControl.getText().equals("")){
		        	  bandNroControl=false;
		        	  bAceptar.setDisable(true);
		          }
		     }		     
		});	
		
		tfMonto.lengthProperty().addListener(new ChangeListener<Number>(){
		     @Override
		     public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {              
		    	 	
		           if(newValue.intValue() > oldValue.intValue()){
		               char ch = tfMonto.getText().charAt(oldValue.intValue());
		               if ( !(ch >= '0' && ch <= '9' ) && !(ch == ',') && !(ch == '.') ) {
		            	   tfMonto.setText(tfMonto.getText().substring(0,tfMonto.getText().length()-1));
		            	  
		               }else{
		            	   bandMonto=true;	
		            	   if ( (bandNombre && bandMonto && bandRIF && bandNroFactura && bandNroControl)
		            	   ||( (tfNombre.getText().compareTo("")!=0) &&
		            	      (tfRIF.getText().compareTo("")!=0) && 
		            	      (tfNroControl.getText().compareTo("")!=0) &&
		            	      (tfNroFactura.getText().compareTo("")!=0) &&
		            	      (tfMonto.getText().compareTo("")!=0)
		            	    ) ){
		           				bAceptar.setDisable(false);
		           		   }
		               }		            	   
		          }		          
		           if (tfMonto.getText().equals("")){
		        	   bandMonto=false;
		        	   bAceptar.setDisable(true);
		           }
		     }		     
		});	
		
		tfMonto.addEventHandler(javafx.scene.input.KeyEvent.KEY_TYPED, libreria.decimalValidacion(10));	
		
	}
	
	@FXML
	private void botonActionAceptar(){
		System.out.println("en valor declarado: aceptar");
		ContextoEncomienda.getInstance().getValorDeclarado().setNombre(tfNombre.getText());
		ContextoEncomienda.getInstance().getValorDeclarado().setRIF(tfRIF.getText());
		ContextoEncomienda.getInstance().getValorDeclarado().setNroControl(tfNroControl.getText());
		ContextoEncomienda.getInstance().getValorDeclarado().setNroFactura(tfNroFactura.getText());
		ContextoEncomienda.getInstance().getValorDeclarado().setMonto(tfMonto.getText());
		ContextoEncomienda.getInstance().setBanderaRefresh(true);
		Stage stage = (Stage) bCancelar.getScene().getWindow();
		stage.close();	
	} 
		
	@FXML
	private void botonActionCancelar(){
		System.out.println("en valor declarado: cancelar");	
		Stage stage = (Stage) bCancelar.getScene().getWindow();
		stage.close();
	}
	
	public void setProgramaPrincipal(PruebaVentanas ProgramaPrincipal) {
		 System.out.println("Controlador ventana principal - setPP en DestinatarioController");
		 this.ProgramaPrincipal = ProgramaPrincipal;
	 }
}
