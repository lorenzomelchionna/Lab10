/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.rivers;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.rivers.model.Model;
import it.polito.tdp.rivers.model.River;
import it.polito.tdp.rivers.model.Simulator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;
	private Simulator simulator;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxRiver"
    private ComboBox<River> boxRiver; // Value injected by FXMLLoader

    @FXML // fx:id="txtStartDate"
    private TextField txtStartDate; // Value injected by FXMLLoader

    @FXML // fx:id="txtEndDate"
    private TextField txtEndDate; // Value injected by FXMLLoader

    @FXML // fx:id="txtNumMeasurements"
    private TextField txtNumMeasurements; // Value injected by FXMLLoader

    @FXML // fx:id="txtFMed"
    private TextField txtFMed; // Value injected by FXMLLoader

    @FXML // fx:id="txtK"
    private TextField txtK; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    @FXML
    void getDatiFiume(ActionEvent event) {
    	
    	River fiume = boxRiver.getValue();
    	
    	model.riempiFiume(fiume);
    	
    	List<LocalDate> dateFiume = model.getDate(boxRiver.getValue());
    	
    	txtStartDate.setText(dateFiume.get(0).toString());
    	txtEndDate.setText(dateFiume.get(1).toString());
    	
    	txtNumMeasurements.setText(String.valueOf(fiume.getFlows().size()));
    	txtFMed.setText(String.valueOf(fiume.getFlowAvg()));
    	
    }
    
    @FXML
    void doSimula(ActionEvent event) {
    	
    	txtResult.clear();
    	
    	float k;
    	try {
    		k = Float.parseFloat(txtK.getText());
    	} catch(NumberFormatException nfe) {
    		txtResult.setText("Inserire un numero");
    		return;
    	}
    	
    	this.simulator.setK(k);
    	this.simulator.setFmed(Float.parseFloat(txtFMed.getText()));
    	this.simulator.setFiume(boxRiver.getValue());
    	
    	this.simulator.run();
    	
    	txtResult.appendText("Giorni di disservizio: "+this.simulator.getnGiorniDisservizio()+"\n");
    	txtResult.appendText("Cmed: "+this.simulator.getCmed());
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxRiver != null : "fx:id=\"boxRiver\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtStartDate != null : "fx:id=\"txtStartDate\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtEndDate != null : "fx:id=\"txtEndDate\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNumMeasurements != null : "fx:id=\"txtNumMeasurements\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtFMed != null : "fx:id=\"txtFMed\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	
    	this.model = model;
    	this.simulator = new Simulator();
    	
    	boxRiver.getItems().addAll(model.getFiumi());
    	
    }
    
}
