/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.poweroutages;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.poweroutages.model.Blackout;
import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;
	private List<Nerc> listaNerc;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxChoice"
    private ChoiceBox<Nerc> boxChoice; // Value injected by FXMLLoader

    @FXML // fx:id="txtMaxYears"
    private TextField txtMaxYears; // Value injected by FXMLLoader

    @FXML // fx:id="txtMaxHours"
    private TextField txtMaxHours; // Value injected by FXMLLoader

    @FXML // fx:id="btnWorstCase"
    private Button btnWorstCase; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void getWorstCaseAnalysis(ActionEvent event) {
    	
    	this.txtResult.clear();
    	int maxY = 0;
    	int maxH = 0;
    	int totPeople = 0;
		int totHours = 0;
		
    	try{
    		
    		maxY = Integer.parseInt(this.txtMaxYears.getText());
    		maxH = Integer.parseInt(this.txtMaxHours.getText());
    		
		} catch (Exception e) {
			
			this.txtResult.appendText("Inserire solo valori numerici!\n");

		}
    	
		try {
			List<Blackout> listaBlackout = new ArrayList<>(
					this.model.findWorstCase(this.boxChoice.getValue(), maxY, maxH));

			for (Blackout b : listaBlackout) {
				totPeople += b.getCustomersAffected();
				totHours += b.getOreDisservizio();
			}

			String result = "Tot people affected: " + totPeople + "\nTot hours of outage: " + totHours + "\n";
			for (Blackout b : listaBlackout) {

				result += b.toString();
			}

			this.txtResult.appendText(result);
		} catch (NullPointerException npe) {

			this.txtResult.appendText("Soluzione al problema non esistente!\n");
		}

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxChoice != null : "fx:id=\"boxChoice\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMaxYears != null : "fx:id=\"txtMaxYears\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMaxHours != null : "fx:id=\"txtMaxHours\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnWorstCase != null : "fx:id=\"btnWorstCase\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        
        this.model = new Model();
        listaNerc = new ArrayList<>(this.model.getNercList());
        
        for(Nerc n: listaNerc) {
        	this.boxChoice.getItems().add(n);
        }
        
    }
   
}
