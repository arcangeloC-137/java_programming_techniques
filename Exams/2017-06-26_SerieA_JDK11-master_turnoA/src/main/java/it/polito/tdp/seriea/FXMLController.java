package it.polito.tdp.seriea;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.seriea.model.Model;
import it.polito.tdp.seriea.model.Season;
import it.polito.tdp.seriea.model.SquadrePartiteGiocate;
import it.polito.tdp.seriea.model.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

//controller turno A --> switchare al branch master_turnoB o master_turnoC per turno B o C

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<Team> boxSquadra;

    @FXML
    private ChoiceBox<Season> boxStagione;

    @FXML
    private Button btnCalcolaConnessioniSquadra;

    @FXML
    private Button btnSimulaTifosi;

    @FXML
    private Button btnAnalizzaSquadre;

    @FXML
    private TextArea txtResult;

    @FXML
    void doAnalizzaSquadre(ActionEvent event) {
    	
    	this.txtResult.clear();
    	
    	try{
    		
    		this.model.creaGrafo();
    		this.txtResult.appendText("Grafo creato!\n"
    				+ "# vertici: "+this.model.getNvertici()+"\n# archi: "+this.model.getNarchi());
    		this.boxSquadra.getItems().setAll(this.model.getSquadre());
    		this.boxStagione.getItems().setAll(this.model.getStagioni());
    		
    	}catch(Exception e) {
    		
    		this.txtResult.appendText("Errore nella creazione del grafo!");
    		return;
    	}
    }

    @FXML
    void doCalcolaConnessioniSquadra(ActionEvent event) {
    	
    	this.txtResult.clear();
    	try {
    		
    		List<SquadrePartiteGiocate> temp = new ArrayList<>(this.model.getConnessioniSquadra(this.boxSquadra.getValue()));
    		String result="Squadre affrontate dal Team "+this.boxSquadra.getValue().getTeam()+":\n";
    		
    		for(SquadrePartiteGiocate s: temp) {
    			result+=s.getTeam2().getTeam()+", "+s.getPartiteGiocate()+" volte\n";
    		}
    		
    		this.txtResult.appendText(result);
    		
    	}catch(Exception e) {
    		this.txtResult.appendText("Premere \"Analizza squadre\" e scegliere una squadra dal menu' a tendina!");
    		return;
    	}
    }

    @FXML
    void doSimulaTifosi(ActionEvent event) {
    	
    	this.txtResult.clear();
    	try {
    		
    		this.txtResult.appendText(this.model.simulaTifosi(this.boxStagione.getValue()));
    		
    	}catch(Exception e) {
    		this.txtResult.appendText("Premere \"Analizza squadre\", poi scegliere una Stagione dal menu' a tendina!");
    		e.printStackTrace();
    		return;
    	}
    }

    @FXML
    void initialize() {
        assert boxSquadra != null : "fx:id=\"boxSquadra\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert boxStagione != null : "fx:id=\"boxStagione\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert btnCalcolaConnessioniSquadra != null : "fx:id=\"btnCalcolaConnessioniSquadra\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert btnSimulaTifosi != null : "fx:id=\"btnSimulaTifosi\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert btnAnalizzaSquadre != null : "fx:id=\"btnAnalizzaSquadre\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'SerieA.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		this.txtResult.editableProperty().set(false);
	}
}