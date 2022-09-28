package it.polito.tdp.seriea;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.seriea.model.AnnoPunteggio;
import it.polito.tdp.seriea.model.Model;
import it.polito.tdp.seriea.model.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<Team> boxSquadra;

    @FXML
    private Button btnSelezionaSquadra;

    @FXML
    private Button btnTrovaAnnataOro;

    @FXML
    private Button btnTrovaCamminoVirtuoso;

    @FXML
    private TextArea txtResult;

    @FXML
    void doSelezionaSquadra(ActionEvent event) {
    	
    	this.txtResult.clear();
    	
    	try {
    		
    		String result = "Risultati per il team "+this.boxSquadra.getValue().getTeam()+":\n";
    		List<AnnoPunteggio> lista = new ArrayList<>(this.model.getListaAnnoPunteggio(this.boxSquadra.getValue()));
    		
    		for(AnnoPunteggio s: lista) {
    			result+="Stagione: "+s.getStagione().getDescription()+", punteggio ottenuto: "+s.getPunteggio()+"\n";
    		}
    		this.txtResult.appendText(result);
    		
    	}catch(Exception e) {
    		this.txtResult.appendText("Inserire una squadra dal menu' a tendina!\n");
    		return;
    	}
    }

    @FXML
    void doTrovaAnnataOro(ActionEvent event) {
    	
    	this.txtResult.clear();
    	try {
    		
    		this.txtResult.appendText(this.model.getAnnataDoro(this.boxSquadra.getValue()));
    		
    	}catch(Exception e) {
    		this.txtResult.appendText("Inserire una squadra dal menu' a tendina per visualizzare l'annata d'oro!\n");
    		return;
    	}

    }

    @FXML
    void doTrovaCamminoVirtuoso(ActionEvent event) {
    	
    	this.txtResult.clear();
    	try {
    		this.txtResult.appendText(this.model.getCamminoVirtuoso(this.boxSquadra.getValue()));
    	}catch(Exception e) {
    		this.txtResult.appendText("Inserire una squadra dal menu' a tendina e trovare l'annata d'oro per cercare il cammino virtuoso!\n");
    		e.printStackTrace();
    		return;
    	}
    }

    @FXML
    void initialize() {
        assert boxSquadra != null : "fx:id=\"boxSquadra\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert btnSelezionaSquadra != null : "fx:id=\"btnSelezionaSquadra\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert btnTrovaAnnataOro != null : "fx:id=\"btnTrovaAnnataOro\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert btnTrovaCamminoVirtuoso != null : "fx:id=\"btnTrovaCamminoVirtuoso\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'SerieA.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		this.boxSquadra.getItems().setAll(this.model.getAllTeams());
	}
}
