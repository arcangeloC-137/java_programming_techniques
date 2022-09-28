/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.anagrammi;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.anagrammi.model.Ricerca;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	Ricerca model = new Ricerca();

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtInputParola"
    private TextField txtInputParola; // Value injected by FXMLLoader

    @FXML // fx:id="btnCalcolaAnagrammi"
    private Button btnCalcolaAnagrammi; // Value injected by FXMLLoader

    @FXML // fx:id="txtAnagrammiCorretti"
    private TextArea txtAnagrammiCorretti; // Value injected by FXMLLoader

    @FXML // fx:id="txtAnagrammiErrati"
    private TextArea txtAnagrammiErrati; // Value injected by FXMLLoader

    @FXML // fx:id="btnReset"
    private Button btnReset; // Value injected by FXMLLoader

    @FXML
    void doCalcolaAnagrammi(ActionEvent event) {

    	this.txtAnagrammiCorretti.clear();
    	this.txtAnagrammiErrati.clear();
    	if(this.model.anagrammi(this.txtInputParola.getText())==null) {
    		this.txtAnagrammiCorretti.appendText("Parola non presente nel dizionario\n");
    		return;
    	}
    	List<String> paroleInDizionario = new ArrayList<>(this.model.getParolaInDizionario());
    	List<String> paroleNonInDizionario = new ArrayList<>(this.model.getParoleNonInDizionario());
    	
    	for(String p: paroleInDizionario) {
    		this.txtAnagrammiCorretti.appendText(p+"\n");
    	}
    	for(String p: paroleNonInDizionario) {
    		this.txtAnagrammiErrati.appendText(p+"\n");
    	}
    	return;
    }

    @FXML
    void doReset(ActionEvent event) {

    	this.txtAnagrammiCorretti.clear();
    	this.txtAnagrammiErrati.clear();
    	this.txtInputParola.clear();
    	return;
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtInputParola != null : "fx:id=\"txtInputParola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCalcolaAnagrammi != null : "fx:id=\"btnCalcolaAnagrammi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtAnagrammiCorretti != null : "fx:id=\"txtAnagrammiCorretti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtAnagrammiErrati != null : "fx:id=\"txtAnagrammiErrati\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'Scene.fxml'.";

        
    }
}
