package optum;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import java.util.Locale; 
import javax.speech.Central; 
import javax.speech.synthesis.Synthesizer; 
import javax.speech.synthesis.SynthesizerModeDesc;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Scanner;
/**
 *
 * @author Kangbok Lee
 */
public class OptumDAO {
    
    
    ObservableList<String> key;
    
    HashMap<String, String> dtabase = new HashMap<String, String>();
    
    LiveSpeechRecognizer recognizer;
    
    public OptumDAO(){
        key=FXCollections.observableArrayList();
        try{
            File keys = new File("C:/Users/Kangbok Lee/Documents/NetBeansProjects/HackIllinois2019/Optum/src/test.txt");
            Scanner in = new Scanner(keys);
            key.add("start");
            key.add("end");
            while(in.hasNext()){
                key.add(in.next());
            }
            // Setting for voice recognition
            Configuration configuration = new Configuration();
            String path = "file:///C:/Users/Kangbok Lee/Desktop/";
            configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
            configuration.setDictionaryPath(path + "3509.dic");
            configuration.setLanguageModelPath(path + "3509.lm");

        recognizer = new LiveSpeechRecognizer(configuration);
        }
        catch(Exception e){
            
        }
        
    }
        
    public boolean addKey(String s) throws IOException{
        boolean exist = key.contains(s);
        if(!exist){
            key.add(s);
            PrintWriter out = new PrintWriter(new FileWriter("C:/Users/Kangbok Lee/Documents/NetBeansProjects/HackIllinois2019/Optum/src/test.txt", true));
            
            out.print(s + " ");
            out.close();
        }
        return exist;
    }
    
    public boolean deleteKey(String s) throws IOException{
        boolean deleted = key.remove(s);
        PrintWriter out = new PrintWriter(new FileWriter("C:/Users/Kangbok Lee/Documents/NetBeansProjects/HackIllinois2019/Optum/src/test.txt", false));
        
        for(int i = 2; i < key.size(); i ++){
            out.print(key.get(i)+ " ");
        }
        out.close();
        
        return deleted;
    }
    public void record(){
        try{
           // set property as Kevin Dictionary 
        System.setProperty("freetts.voices", 
            "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");  

        // Register Engine 
        Central.registerEngineCentral 
            ("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral"); 

        // Create a Synthesizer 
        Synthesizer synthesizer =                                          
            Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));      

        // Allocate synthesizer 
        synthesizer.allocate();         

        // Resume Synthesizer 
        synthesizer.resume();     
    
        
        // Start recognition process pruning previously cached data.
        recognizer.startRecognition(true);
        synthesizer.speakPlainText("speak now", null);
        synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
        SpeechResult result;  //Check if recognizer recognized the speech
        while ((result = recognizer.getResult()) != null) {
 
            //Get the recognized speech
            String command = result.getHypothesis();
            //Match recognized speech with our commands
            String[] parts = command.split(" ");
            System.out.println(command);
            if(parts[0].equalsIgnoreCase(key.get(0))) {
                for(int i = 1; i < parts.length; i++){
                    if(parts[1].equalsIgnoreCase(key.get(1))){
                        break;
                    }
                    if(key.contains(parts[i])){
                        String temp = "";
                        int j = i + 1;
                        while(!key.contains(parts[j]) && j < parts.length && !parts[j].equalsIgnoreCase(key.get(1))){
                            temp += parts[j] + " ";
                            j++;
                        }
                        dtabase.put(parts[i], temp);
                        if(j< parts.length){
                            if(parts[j].equalsIgnoreCase(key.get(1))){
                                i = j;
                            }
                        }
                        // speaks the given text until queue is empty. 
                        synthesizer.speakPlainText(temp, null);          
                        synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY); 
                        
                    }
                }
            }
        }
        // Deallocate the Synthesizer. 
        synthesizer.deallocate(); 
        }
        catch(Exception e){
            
        }
    }
    
    public void end(){
        recognizer.stopRecognition();
    }
         
    private void showMessageDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
    public ObservableList<String> key(){
        return key;
    }
    
}
