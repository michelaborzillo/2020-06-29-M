/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.imdb;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.imdb.model.Director;
import it.polito.tdp.imdb.model.Model;
import it.polito.tdp.imdb.model.Movie;
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

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnAdiacenti"
    private Button btnAdiacenti; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaAffini"
    private Button btnCercaAffini; // Value injected by FXMLLoader

    @FXML // fx:id="boxAnno"
    private ComboBox<Integer> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxRegista"
    private ComboBox<Director> boxRegista; // Value injected by FXMLLoader

    @FXML // fx:id="txtAttoriCondivisi"
    private TextField txtAttoriCondivisi; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	Integer anno= boxAnno.getValue();
//    	if (anno<2004 || anno>2006 || anno==null)
//    		txtResult.appendText("Errore inserimento anno");
//    	else {
    		
    		this.model.creaGrafo(anno);
    		txtResult.appendText("Grafo creato!\n");
    		txtResult.appendText("VERTICI: "+this.model.nVertici()+"\n");
        	txtResult.appendText("ARCHI: "+this.model.nArchi()+"\n");
        	boxRegista.getItems().addAll(model.getRegistiGrafo());
    	//}
    	
    }

    @FXML
    void doRegistiAdiacenti(ActionEvent event) {
    	Director regista= boxRegista.getValue();
    	List<Director> adiacenti= model.getAdiacenza(regista);
    		for (Director d: adiacenti) {
    		d.setAdiacenza(this.model.pesoGrafo(d, regista));
    	}
    		Collections.sort(adiacenti);
    		txtResult.clear();
    		for (Director d: adiacenti) {
    			txtResult.appendText(""+d+" - #attori condivisi "+ d.getPeso()+"\n");
    		}
    }

    @FXML
    void doRicorsione(ActionEvent event) {
    	txtResult.clear();
    	int att= Integer.parseInt(txtAttoriCondivisi.getText());
    	model.trovaEventi(boxRegista.getValue(),boxAnno.getValue(), att);
    	for (Director dd: model.trovaEventi(boxRegista.getValue(),boxAnno.getValue(), att)) {
    		txtResult.appendText(dd.toString()+"\n");
    	}

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnAdiacenti != null : "fx:id=\"btnAdiacenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaAffini != null : "fx:id=\"btnCercaAffini\" was not injected: check your FXML file 'Scene.fxml'.";
       // assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Scene.fxml'.";
       // assert boxRegista != null : "fx:id=\"boxRegista\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtAttoriCondivisi != null : "fx:id=\"txtAttoriCondivisi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
   public void setModel(Model model) {
    	
    	this.model = model;
    	boxAnno.getItems().clear();
    	boxAnno.getItems().addAll(model.annida2004to2006());
    	
    }
    
}
