
package it.polito.tdp.borders;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;


public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader
    
    @FXML // fx:id="btnTrovaVicini"
    private Button btnTrovaVicini; // Value injected by FXMLLoader
    
    @FXML // fx:id="statesComboBox"
    private ComboBox<Country> statesComboBox; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	
    	this.txtResult.clear();
    	int data;
    	try {
    		data = Integer.parseInt(this.txtAnno.getText());
    		if(data<1816 || data>2016) {
    			this.txtResult.appendText("La data deve essere compresa tra 1816 e 2016!");
        		return;
    		}
    	}catch(NumberFormatException nfe){
    		this.txtResult.appendText("Inserire una data in formato numerico, compresa tra 1816 e 2016!");
    		return;
    	}
    	
    	this.model.generateGraph(data);
    	this.txtResult.appendText("Grafo creato!\nNumero vertici: "+this.model.nVertex()+"\nNumero archi: "+this.model.nEdges()+
    			"\nNumero di componenti connesse: "+this.model.numeroComponentiGrafo()+"\n");
    	this.txtResult.appendText(this.model.trovaStatiConfinanti());

    	this.statesComboBox.disableProperty().set(false);
    	this.btnTrovaVicini.disableProperty().set(false);
    	this.statesComboBox.getItems().addAll(this.model.getStatiDelGrafo());
    }
    
    @FXML
    void doCalcolaVicini(ActionEvent event) {
    	
    	this.txtResult.clear();
    	List<Country> temp = new ArrayList<>(this.model.visitaInAmpiezza(this.statesComboBox.getValue()));
    	this.txtResult.appendText("Stati raggiungibili via terra dallo stato selezionato:\n");
    	temp.remove(0);
    	for(Country c: temp) {
    		this.txtResult.appendText(c.getStateName()+"\n");
    	}
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert statesComboBox != null : "fx:id=\"statesComboBox\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnTrovaVicini != null : "fx:id=\"btnTrovaVicini\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
