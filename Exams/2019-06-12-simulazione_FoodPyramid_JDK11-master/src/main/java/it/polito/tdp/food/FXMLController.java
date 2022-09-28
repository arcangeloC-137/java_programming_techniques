package it.polito.tdp.food;

import java.net.URL;

import java.util.ResourceBundle;

import javafx.scene.control.TextArea;
import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtCalorie;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private ComboBox<Condiment> boxIngrediente;
    
    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    private Button btnDietaEquilibrata;

    @FXML
    void doCalcolaDieta(ActionEvent event) {

    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	this.txtResult.clear();
    	double calorie;
    	
    	try {
    		
    		calorie = Double.parseDouble(this.txtCalorie.getText());
    		this.model.creaGrafo(calorie);
    		this.txtResult.appendText("Grafo creato!\n# vertici: "+this.model.getNumVertici()+
    				"\n# archi: "+this.model.getNumArchi()+"\n"+this.model.getListaIngredienti());
    		
    		this.boxIngrediente.getItems().clear();
    		this.boxIngrediente.getItems().setAll(this.model.getIngredienti());
    		
    	}catch(NumberFormatException e) {
    		
    		this.txtResult.appendText("Errore! Inserire solo valori numerici!");
    		return;
    	}
    }

    @FXML
    void initialize() {
        assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxIngrediente != null : "fx:id=\"boxIngrediente\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnDietaEquilibrata != null : "fx:id=\"btnDietaEquilibrata\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
	}
}
