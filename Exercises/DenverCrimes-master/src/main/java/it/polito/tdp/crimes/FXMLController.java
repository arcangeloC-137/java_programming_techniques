/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.crimes;

import java.net.URL;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.crimes.model.Arco;
import it.polito.tdp.crimes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxCategoria"
    private ComboBox<String> boxCategoria; // Value injected by FXMLLoader

    @FXML // fx:id="boxMese"
    private ComboBox<Month> boxMese; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="boxArco"
    private ComboBox<Arco> boxArco; // Value injected by FXMLLoader

    @FXML // fx:id="btnPercorso"
    private Button btnPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaPercorso(ActionEvent event) { //SECONDO BOTTONE
    	
    	this.txtResult.clear();
    	Arco a = this.boxArco.getValue();
    	if(a==null) {
    		this.txtResult.appendText("Seleziona un arco!");
    		return;
    	}
    	
    	List<String> percorso = this.model.trovaPercorsi(a.getReato1(), a.getReato2());
    	for(String p: percorso) {
    		this.txtResult.appendText(p+"\n");
    	}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {//PRIMO BOTTONE
    	
    	
    	this.txtResult.clear();
    	String categoria = this.boxCategoria.getValue();
    	if(categoria==null) {
    		this.txtResult.appendText("Seleziona categoria!");
    		return;
    	}
    	
    	Month m = this.boxMese.getValue();
    	if(m==null) {
    		this.txtResult.appendText("Seleziona mese!");
    		return;
    	}
    	
    	Integer mese = m.getValue();
    	
    	
    	this.model.generateGraph(categoria, mese);
    	this.txtResult.appendText("Grafo creato!\nNumero vertici: "+this.model.getNvertex()+
    			"\nNumero archi: "+this.model.getNedges()+"\n");
    	
    	List<Arco> archi = this.model.getArchi();
    	this.txtResult.appendText("Archi con peso sup. alla media:\n");
    	for(Arco a: archi) {
    		this.txtResult.appendText(a.getReato1()+"-"+a.getReato2()+", peso: "+a.getPeso()+"\n");
    	}
    	
    	this.boxArco.getItems().addAll(archi);
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxCategoria != null : "fx:id=\"boxCategoria\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxArco != null : "fx:id=\"boxArco\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.boxCategoria.getItems().addAll(this.model.getCategorie());
    	List<Month> mesi = new ArrayList<>();
    	for(Integer i: this.model.getMesi()) {
    		mesi.add(Month.of(i));
    	}
    	this.boxMese.getItems().addAll(mesi);

    }
}
