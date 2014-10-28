package application;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class libreria {
	
	public static EventHandler<KeyEvent> telefonoValidacion(final Integer max_Lengh) {
	    return new EventHandler<KeyEvent>() {
	        @Override
	        public void handle(KeyEvent e) {
	            TextField txt_TextField = (TextField) e.getSource();                
	            if (txt_TextField.getText().length() >= max_Lengh) {                    
	                e.consume();
	            }
	            if(e.getCharacter().matches("[0-9]")){ 
	                if(txt_TextField.getText().contains(".") && e.getCharacter().matches("[.]")){
	                    e.consume();
	                }else if(txt_TextField.getText().length() == 0 && e.getCharacter().matches("[.]")){
	                    e.consume(); 
	                }
	            }else{
	                e.consume();
	            }
	        }
	    };
	}   
	
	public static EventHandler<KeyEvent> decimalValidacion(final Integer max_Lengh) {
	    return new EventHandler<KeyEvent>() {
	        @Override
	        public void handle(KeyEvent e) {
	            TextField txt_TextField = (TextField) e.getSource();                
	            if (txt_TextField.getText().length() >= max_Lengh) {                    
	                e.consume();
	            }
	            if(e.getCharacter().matches("[0-9.]")){ 
	                if(txt_TextField.getText().contains(".") && e.getCharacter().matches("[.]")){
	                    e.consume();
	                }else if(txt_TextField.getText().length() == 0 && e.getCharacter().matches("[.]")){
	                    e.consume(); 
	                }else if (txt_TextField.getText().indexOf(".") != -1){
	                	System.out.println("el . de decimal  "+txt_TextField.getText().indexOf("."));
	                	System.out.println("el . de decimal  "+txt_TextField.getText().length());
	                	
	                	if (txt_TextField.getText().length() > (txt_TextField.getText().indexOf(".")+2) ) {
	                        System.out.println("ya tiene 2 decimales");
	                		e.consume();
	                	}
	                	
		        	}
	            }else{
	                e.consume();
	            }
	        }
	    };
	}    

	public static EventHandler<KeyEvent> letraValidacion(final Integer max_Lengh) {
	    return new EventHandler<KeyEvent>() {
	        @Override
	        public void handle(KeyEvent e) {
	            TextField txt_TextField = (TextField) e.getSource();                
	            if (txt_TextField.getText().length() >= max_Lengh) {                    
	                e.consume();
	            }
	            if(e.getCharacter().matches("[A-Za-z ]")){ 

	            }else{
	                e.consume();
	            }
	        }
	    };
	}
	
	public static ChangeListener<? super String> mayuscula(final TextField xx) {
		// TODO Auto-generated method stub
		return new ChangeListener<String>(){
		  	  @Override
		  	    public void changed(ObservableValue<? extends String> observable,
		  	            String oldValue, String newValue) {
		  		   xx.setText(newValue.toUpperCase());   
		  	    }
		  };
	}
	
	public static EventHandler<KeyEvent> porcentajeValidacion (){
		return new EventHandler<KeyEvent>() {
	        @Override
	        public void handle(KeyEvent e) {
	            TextField txt_TextField = (TextField) e.getSource(); 
	            
	            if (e.getCharacter().matches("[,]")){
	        		e.consume();
	        	}else if ( e.getCharacter().matches("[.]") && (txt_TextField.getText().length()==0) ){
	        		e.consume();
	        	}else if (txt_TextField.getText().indexOf(".")!=-1){
	            	if (txt_TextField.getText().indexOf(".")==1){
	            		if (txt_TextField.getText().length()>3) {
	    	        		e.consume();
	    	        	}	  
	            	}else if (txt_TextField.getText().indexOf(".")==2){
	            		if (txt_TextField.getText().length()>4) {
	    	        		e.consume();
	    	        	}	 	            	
	            	}
	        	}else if ( !e.getCharacter().matches("[.]") && !(e.getCharacter().matches("[0]")) 
	        			&& (txt_TextField.getText().length()==2) ){
	        		e.consume();
	        		System.out.println(" el 100%");
	        	}else if ( txt_TextField.getText().equals("100") && (txt_TextField.getText().length()>=3) ){
	        		e.consume();
	        		System.out.println(" el 100% no ingrese mas nada");
	        	}            
	        }
	    };
	}
		
	
}
