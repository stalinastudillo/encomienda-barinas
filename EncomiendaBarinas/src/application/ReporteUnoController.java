package application;

import javafx.fxml.FXML;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;

public class ReporteUnoController {

	@FXML
	private void initialize(){
		System.out.println("en controlador de reporte uno");
		
		try {
			JasperCompileManager.compileReportToFile("primero.jrxml");
		} catch (JRException e) {
			e.printStackTrace();
		}
		
	}
}
