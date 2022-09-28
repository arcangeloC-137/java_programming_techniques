/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.corsi;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.corsi.model.Corso;
import it.polito.tdp.corsi.model.Model;
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

    @FXML // fx:id="txtPeriodo"
    private TextField txtPeriodo; // Value injected by FXMLLoader

    @FXML // fx:id="txtCorso"
    private TextField txtCorso; // Value injected by FXMLLoader

    @FXML // fx:id="btnCorsiPerPeriodo"
    private Button btnCorsiPerPeriodo; // Value injected by FXMLLoader

    @FXML // fx:id="btnNumeroStudenti"
    private Button btnNumeroStudenti; // Value injected by FXMLLoader

    @FXML // fx:id="btnStudenti"
    private Button btnStudenti; // Value injected by FXMLLoader

    @FXML // fx:id="btnDivisioneStudenti"
    private Button btnDivisioneStudenti; // Value injected by FXMLLoader

    @FXML // fx:id="txtRisultato"
    private TextArea txtRisultato; // Value injected by FXMLLoader

    @FXML
    void corsiPerPeriodo(ActionEvent event) {

    	this.txtRisultato.clear();
    	String pdString = this.txtPeriodo.getText();
    	Integer pd;
    	
    	try {
    	pd = Integer.parseInt(pdString);
    	}catch(NumberFormatException nfe) {
    		this.txtRisultato.setText("Devi inserire un numero (1 o 2)!");
    		return;
    	}
    	
    	if(!(pd.equals(1) || pd.equals(2))) {
    		this.txtRisultato.setText("Devi inserire un numero (1 o 2)!");
    		return;
    	}
    	
    	//L'input e' corretto
    	
    	List<Corso> corsi = this.model.getCorsiByPeriodo(pd);
    	
    	for(Corso c: corsi) {
    		this.txtRisultato.appendText(c.toString()+"\n");
    	}
    }

    @FXML
    void numeroStudenti(ActionEvent event) {

    	this.txtRisultato.clear();
    	String pdString = this.txtPeriodo.getText();
    	Integer pd;
    	
    	try {
    	pd = Integer.parseInt(pdString);
    	}catch(NumberFormatException nfe) {
    		this.txtRisultato.setText("Devi inserire un numero (1 o 2)!");
    		return;
    	}
    	
    	if(!(pd.equals(1) || pd.equals(2))) {
    		this.txtRisultato.setText("Devi inserire un numero (1 o 2)!");
    		return;
    	}
    	
    	//L'input e' corretto
    	
    	Map<Corso, Integer> statistiche = this.model.getIscrittiByPeriodo(pd);
    	
    	for(Corso c: statistiche.keySet()) {
    		this.txtRisultato.appendText(c.getNome()+" "+statistiche.get(c)+"\n");
    	}
    }

    @FXML
    void stampaDivisione(ActionEvent event) {

    }

    @FXML
    void stampaStudenti(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtPeriodo != null : "fx:id=\"txtPeriodo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCorso != null : "fx:id=\"txtCorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCorsiPerPeriodo != null : "fx:id=\"btnCorsiPerPeriodo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnNumeroStudenti != null : "fx:id=\"btnNumeroStudenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnStudenti != null : "fx:id=\"btnStudenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnDivisioneStudenti != null : "fx:id=\"btnDivisioneStudenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtRisultato != null : "fx:id=\"txtRisultato\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model=model;
    }
}
