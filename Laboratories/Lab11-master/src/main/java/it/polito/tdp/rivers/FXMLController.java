/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.rivers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.Model;
import it.polito.tdp.rivers.model.River;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class FXMLController {
	
	Model model;

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
    void btnSimula(ActionEvent event) {
    	
    	double num;
    	this.txtResult.clear();
    	
    	try {
    		
    		num = Double.parseDouble(this.txtK.getText());
    		if(num<0) {
    			this.txtResult.appendText("Il valore inserito deve essere un numero maggiore di 0");
    			return;
    		}
    		
    	}catch(NumberFormatException e) {
    		this.txtResult.appendText("Il valore inserito non è un numero!");
    		return;
    	}
    	
    	this.txtResult.appendText(this.model.getStatisticheSimulazione(this.boxRiver.getValue(), num));
    }
    
    @FXML
    void setStatistiche(ActionEvent event) {
    	
    	this.txtStartDate.clear();
		this.txtEndDate.clear();
		this.txtFMed.clear();
		this.txtNumMeasurements.clear();
		
        if(this.boxRiver.getValue()!=null) {
        	List<Flow> list = new ArrayList<>(this.model.getAllRilevamenti(this.boxRiver.getValue()));
    		this.txtStartDate.appendText(""+list.get(0).getDay().toString());
    		this.txtEndDate.appendText(""+list.get(list.size()-1).getDay().toString());
    		double avg = Math.ceil(this.model.getAvgFlow(this.boxRiver.getValue()));
    		this.txtFMed.appendText(""+avg);
    		this.txtNumMeasurements.appendText(""+list.size());
    	}
    }

    @FXML
    void setFiumi(MouseEvent event) {
    	this.boxRiver.getItems().setAll(this.model.getAllRivers());
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
    }
}

