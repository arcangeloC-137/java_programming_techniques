/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.extflightdelays;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.extflightdelays.model.Adiacenze;
import it.polito.tdp.extflightdelays.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="distanzaMinima"
    private TextField distanzaMinima; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalizza"
    private Button btnAnalizza; // Value injected by FXMLLoader

    @FXML
    void doAnalizzaAeroporti(ActionEvent event) {
    	this.txtResult.clear();
    	int distanzaMin = 0;
    	try {
    	    distanzaMin = Integer.parseInt(this.distanzaMinima.getText());
    	}catch(NumberFormatException e) {
    		this.txtResult.appendText("Inserire solo valori numerici!");
    		return;
    	}
    	
    	List<Adiacenze> result = new ArrayList<>(this.model.genereateGraph(distanzaMin));
    	if(this.model.nVertex()==0 || this.model.nEdges()==0){
    		this.txtResult.appendText("Nessun grafo trovato con distanza minima "+distanzaMin);
    		return;
    	}
    	this.txtResult.appendText(String.format("Grafo creato!\n#Vertici: %d\n#Archi: %d", this.model.nVertex(), this.model.nEdges()));
    	
    	this.txtResult.appendText("\nArchi del grafo:\n");
    	for(Adiacenze a: result) {
    		this.txtResult.appendText(a.getAirport1().getAirportName()+" - "+a.getAirport2().getAirportName()+" "+a.getDistanceBetween()+"\n");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert distanzaMinima != null : "fx:id=\"distanzaMinima\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnAnalizza != null : "fx:id=\"btnAnalizza\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
