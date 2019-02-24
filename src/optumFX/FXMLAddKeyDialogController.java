/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optumFX;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import optum.OptumDAO;

/**
 * FXML Controller class
 *
 * @author Kangbok Lee
 */
public class FXMLAddKeyDialogController implements Initializable {
    
    OptumDAO model;
    
    @FXML
    TextField key;
    
    @FXML
    Label status;
    
    @FXML
    private void add(ActionEvent evt) throws IOException{
        boolean exist = model.addKey(key.getText());
        if(exist){
            Stage parent = (Stage) key.getScene().getWindow();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLErrorDialog.fxml"));
                Parent root = (Parent)loader.load();
                Scene scene = new Scene(root);
                Stage dialog = new Stage();
                dialog.setScene(scene);
                dialog.setTitle("Error");
                dialog.initOwner(parent);
                dialog.initModality(Modality.WINDOW_MODAL);
                dialog.initStyle(StageStyle.UTILITY);
                dialog.setX(parent.getX() + parent.getWidth()/4);
                dialog.setY(parent.getY() + parent.getHeight()/3);

                FXMLErrorDialogController controller = (FXMLErrorDialogController) loader.getController();
                controller.setText("ERROR: The keyword already exists");
                dialog.show();
            } catch(Exception ex) {
                System.out.println("Could not open dialog.");
                ex.printStackTrace();
            }
        }
        else{
            status.setText("Successfully added!");
        }
    }
    
    @FXML
    private void cancel(ActionEvent evt){
        key.getScene().getWindow().hide();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setModel(OptumDAO model){
        this.model = model;
    }
    
}
