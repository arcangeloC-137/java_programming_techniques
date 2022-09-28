package it.polito.tdp.extflightdelays;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.extflightdelays.model.Airport;
import it.polito.tdp.extflightdelays.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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

    @FXML // fx:id="compagnieMinimo"
    private TextField compagnieMinimo; // Value injected by FXMLLoader

    @FXML // fx:id="cmbBoxAeroportoPartenza"
    private ComboBox<Airport> cmbBoxAeroportoPartenza; // Value injected by FXMLLoader

    @FXML // fx:id="cmbBoxAeroportoDestinazione"
    private ComboBox<Airport> cmbBoxAeroportoDestinazione; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalizza"
    private Button btnAnalizza; // Value injected by FXMLLoader

    @FXML // fx:id="btnConnessione"
    private Button btnConnessione; // Value injected by FXMLLoader

    @FXML
    void doAnalizzaAeroporti(ActionEvent event) {
    	
    	int x = 0;
    	try {
    	 x = Integer.parseInt(this.compagnieMinimo.getText());
    	}catch(Throwable t) {
    		this.txtResult.appendText("Errore nell'input");
    		return;
    	}
    	this.model.creaGrafo(x);
    	this.txtResult.appendText("Grafo creato!\n");
    	this.txtResult.appendText("Numero vertici: "+ this.model.nVertex()+"\n");
    	this.txtResult.appendText("Numero archi: "+ this.model.nEdge()+"\n");
    	
    	this.cmbBoxAeroportoPartenza.getItems().addAll(this.model.getAeroporti());
    	this.cmbBoxAeroportoDestinazione.getItems().addAll(this.model.getAeroporti());
    	
    	
    }

    @FXML
    void doTestConnessione(ActionEvent event) {
    	
    	Airport a1 = this.cmbBoxAeroportoPartenza.getValue();
    	Airport a2 = this.cmbBoxAeroportoDestinazione.getValue();
    	
    	if(a1==null || a2==null) {
    		this.txtResult.appendText("Seleziona i due aeroporti!");
    		return;
    	}
    	
    	List<Airport> percorso = this.model.trovaPercorso(a1, a2);
    	
    	if(percorso==null) {
    		this.txtResult.appendText("I due aeroporti non sono collegati!");
    	}else {
    		for(Airport a: percorso) {
    			this.txtResult.appendText(a.getAirportName()+"\n");
    		}
    		
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert compagnieMinimo != null : "fx:id=\"compagnieMinimo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbBoxAeroportoPartenza != null : "fx:id=\"cmbBoxAeroportoPartenza\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbBoxAeroportoDestinazione != null : "fx:id=\"cmbBoxAeroportoDestinazione\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnAnalizza != null : "fx:id=\"btnAnalizza\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnConnessione != null : "fx:id=\"btnConnessione\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	
    }
}
