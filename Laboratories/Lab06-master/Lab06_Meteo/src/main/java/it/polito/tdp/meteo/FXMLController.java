/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.meteo;

import java.net.URL;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.meteo.model.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.util.Callback;

public class FXMLController {
	
	Model model;
	ObservableList<Month> listaMesi = FXCollections.observableArrayList(getMesi());
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxMese"
    private ChoiceBox<Month> boxMese; // Value injected by FXMLLoader

    @FXML // fx:id="btnUmidita"
    private Button btnUmidita; // Value injected by FXMLLoader

    @FXML // fx:id="btnCalcola"
    private Button btnCalcola; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaSequenza(ActionEvent event) {
    	
    	this.txtResult.clear();
    	Month mese =  this.boxMese.getValue();
    	this.txtResult.appendText(this.model.calcolaSequenza(mese));
    }


	private List<Month> getMesi() {
		List<Month> mesi = new ArrayList<>();
		mesi.add(Month.JANUARY);
		mesi.add(Month.FEBRUARY);
		mesi.add(Month.MARCH);
		mesi.add(Month.APRIL);
		mesi.add(Month.MAY);
		mesi.add(Month.JUNE);
		mesi.add(Month.JULY);
		mesi.add(Month.AUGUST);
		mesi.add(Month.SEPTEMBER);
		mesi.add(Month.OCTOBER);
		mesi.add(Month.NOVEMBER);
		mesi.add(Month.DECEMBER);
		return mesi;
	}


	@FXML
    void doCalcolaUmidita(ActionEvent event) {
    	this.txtResult.clear();
		int mese = this.listaMesi.indexOf(this.boxMese.getValue())+1;
		this.txtResult.appendText(this.model.getUmiditaMedia(mese));
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnUmidita != null : "fx:id=\"btnUmidita\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCalcola != null : "fx:id=\"btnCalcola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

        this.model = new Model();
        this.boxMese.setItems(listaMesi);
       
    }
}

