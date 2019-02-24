/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optumFX;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import optum.OptumDAO;

/**
 * FXML Controller class
 *
 * @author Kangbok Lee
 */
public class FXMLOptumController implements Initializable {

    OptumDAO model;
    
    @FXML
    Label status;
    
    @FXML
    AnchorPane border;
    
    @FXML
    private void add(ActionEvent evt){
        model.record();
        status.setText(String.valueOf(model.key().size()));
    }
    
    @FXML
    private void end(ActionEvent evt){
        //model.end();
    }
    
    @FXML
    private void addKey(ActionEvent evt){
        Stage parent = (Stage) border.getScene().getWindow();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLAddKeyDialog.fxml"));
            Parent root = (Parent)loader.load();
            Scene scene = new Scene(root);
            Stage dialog = new Stage();
            dialog.setScene(scene);
            dialog.setTitle("Add New Key");
            dialog.initOwner(parent);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initStyle(StageStyle.UTILITY);
            dialog.setX(parent.getX() + parent.getWidth()/4);
            dialog.setY(parent.getY() + parent.getHeight()/3);
            
            FXMLAddKeyDialogController controller = (FXMLAddKeyDialogController) loader.getController();
            controller.setModel(model);
            dialog.show();
        } catch(Exception ex) {
            System.out.println("Could not open dialog.");
            ex.printStackTrace();
        }
    }
    @FXML
    private void deleteKey(ActionEvent evt){
        Stage parent = (Stage) border.getScene().getWindow();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDeleteKeyDialog.fxml"));
            Parent root = (Parent)loader.load();
            Scene scene = new Scene(root);
            Stage dialog = new Stage();
            dialog.setScene(scene);
            dialog.setTitle("Delete Key");
            dialog.initOwner(parent);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initStyle(StageStyle.UTILITY);
            dialog.setX(parent.getX() + parent.getWidth()/4);
            dialog.setY(parent.getY() + parent.getHeight()/3);
            
            FXMLDeleteKeyDialogController controller = (FXMLDeleteKeyDialogController) loader.getController();
            controller.setModel(model);
            dialog.show();
        } catch(Exception ex) {
            System.out.println("Could not open dialog.");
            ex.printStackTrace();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        model = new OptumDAO();
    }    
    public void setModel(OptumDAO model){
        this.model = model;
    }
}
