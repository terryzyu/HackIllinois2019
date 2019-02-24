//
//  ViewController2.swift
//  Nurse Connection
//
//  Created by Archie Paredes 

import UIKit
import Speech
import Foundation

class ViewController2: UIViewController, SFSpeechRecognizerDelegate {
//    var Qs : [String : AnyObject?] = ["any pain" : "" as AnyObject, "caused the": "" as AnyObject, "severe is" : "" as AnyObject, "patient look" : "" as AnyObject, "feeling" : "" as AnyObject, "left hand" :"" as AnyObject, "right hand": "" as AnyObject, "fatigue":"" as AnyObject]
    
//
//    var pre = ["any", "caused", "severe", "feeling", "left", "right", "fatigue", "patient"];
    
//    var keyWords = ["START", "END", "GRIP", "PRESSURE", "DRINK", "WALK", "BLOOD"]
    
    var feelz:[String:String] = ["How are you feeling?":"Sick",
                                 "Left Hand Grip Strength?":"3/5",
                                 "Right Hand Grip Strength?":"2/5",
                                 "How fatigue are you?":"5/5"];
    var painz:[String:String] = ["Do you feel any pain?":"Yes",
                                "What actions cause the pain?":"Sickness",
                                "How severe is the pain?" : "5/10",
                                "How does the patient look?":"Gray/pale skin"]
    

   
    
    var keyFeel = ["feeling", "left", "right", "fatigue"];
    var keyPain = ["feel", "actions", "severe", "patient look"];
    var allAnswered:Bool = false;
    var answer:Int = 0;
    var hm = [String : String]();
    
    
    var record:Bool = false;
    var speech:String = "";
    @IBOutlet weak var recordOrNot: UILabel!
    @IBOutlet weak var micButt: UIButton! // mic button outlet

//    func parse(){
//        var k:String = ""
//        var val = "";
//        speech = speech.uppercased();
//        let speechSplit = speech.components(separatedBy: " ");
//        var i:Int = 0
//        while(i < speechSplit.count){
//            if(speechSplit[i] == "START"){ // start
//                if(i+1 == speechSplit.count){}
//                else{
//                    if(keyWords.contains(speechSplit[i+1])){
//                        k = speechSplit[i+1]
//                        var j:Int = i+2;
//                        while (j < speechSplit.count){
//                            val = "";
//                            val += "\(speechSplit[j]) "
//                            if(j+1 < speechSplit.count){
//                                if (speechSplit[j+1] == "AND" || speechSplit[j+1] == "END"){
//                                    i = j + 1;
//                                    break;
//                                }
//                            }
//                            j += 1
//                        }
//                        if (val == "FOR"){
//                            val = "FOUR"
//                        }
//                        hm[k] = val;
//                    }
//                }
//            }
//            i += 1;
//        }
//
//        //print(hm.count);
//
//    }
    
    private var speechRecognizer = SFSpeechRecognizer(locale: Locale.init(identifier: "en-US")) //1
    private var recognitionRequest: SFSpeechAudioBufferRecognitionRequest?
    private var recognitionTask: SFSpeechRecognitionTask?
    private var audioEngine = AVAudioEngine()
    var lang: String = "en-US"
    

    func alert_pop(mes:String){ // handles alerts
        let alert = UIAlertController(title: "Ask these questions:", message: mes, preferredStyle: .alert);
        let dismiss = UIAlertAction(title: "Dismiss", style: .cancel, handler: nil);
        alert.addAction(dismiss);
        self.present(alert, animated: true, completion: nil);
    }
    
    func startRecording() {
        
        if recognitionTask != nil {
            recognitionTask?.cancel()
            recognitionTask = nil
        }
        
        let audioSession = AVAudioSession.sharedInstance()
        do {
            try AVAudioSession.sharedInstance().setCategory(.playback, mode: .default)
            try audioSession.setMode(AVAudioSession.Mode.measurement)
            try audioSession.setActive(true, options: .notifyOthersOnDeactivation)
        } catch {
            print("audioSession properties weren't set because of an error.")
        }
        
        recognitionRequest = SFSpeechAudioBufferRecognitionRequest()
        
        let inputNode = audioEngine.inputNode
        
        guard let recognitionRequest = recognitionRequest else {
            fatalError("Unable to create an SFSpeechAudioBufferRecognitionRequest object")
        }
        
        recognitionRequest.shouldReportPartialResults = true
        
        recognitionTask = speechRecognizer?.recognitionTask(with: recognitionRequest, resultHandler: { (result, error) in
            
            var isFinal = false
            
            if result != nil {
                self.speech = (result?.bestTranscription.formattedString)!
                self.recordOrNot.text = self.speech;
                isFinal = (result?.isFinal)!
            }
            
            if error != nil || isFinal {
                self.audioEngine.stop()
                inputNode.removeTap(onBus: 0)
                
                self.recognitionRequest = nil
                self.recognitionTask = nil
                
                self.micButt.isEnabled = true
            }
        })
        
        let recordingFormat = inputNode.outputFormat(forBus: 0)
        inputNode.installTap(onBus: 0, bufferSize: 1024, format: recordingFormat) { (buffer, when) in
            self.recognitionRequest?.append(buffer)
        }
        
        audioEngine.prepare()
        
        do {
            try audioEngine.start()
        } catch {
            print("audioEngine couldn't start because of an error.")
        }
        
        //textView.text = "Say something, I'm listening!"
        
    }
    
    func speechRecognizer(_ speechRecognizer: SFSpeechRecognizer, availabilityDidChange available: Bool) {
        if available {
            micButt.isEnabled = true
        } else {
            micButt.isEnabled = false
        }
    }
    
    
    @IBAction func MicButton(_ sender: UIButton) { //button action
        if(record == false) {
            record = true;
            recordOrNot.text = "App is recording"
            startRecording()
            //micButt.setTitle("Stop Recording", for: .normal)
            //TODO start recording
        } else {
            return;
        }
    }
    
    @IBAction func seeData(_ sender: UIButton) {
        performSegue(withIdentifier: "twoToThree", sender: self);
    }
    

    @IBAction func stopButton(_ sender: UIButton) {
        
        record = false;
        recordOrNot.text = "App is NOT recording";
        if audioEngine.isRunning {
            audioEngine.stop();
            recognitionRequest?.endAudio();
            micButt.isEnabled = false;
            //parse();
            recordOrNot.text = "App is NOT recording";
            //micButt.setTitle("", for: .normal)
        }
    }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        recordOrNot.text = "App is NOT recording";
        // Do any additional setup after loading the view.
    }
    

    @IBAction func helpButton(_ sender: UIButton) {
        var questions:String = "1.) How are you feeling?\n2.) Left Hand Grip Strength?\n3.) Right Hand Grip Strength?\n4.) How fatigue are you? \n5.) Do you feel any pain?\n6.) What actions cause the pain?\n7.) How severe is the pain?\n8.) How does the patient look?";
        alert_pop(mes:questions);
    }
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */
    
    @IBAction func goToSettings(_ sender: UIButton) {
        performSegue(withIdentifier: "twoToFour", sender: self);
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        
        if (segue.identifier == "twoToThree"){
            let view3 = segue.destination as! ViewController3;
            view3.feelz = feelz;
            view3.painz = painz;
        
        } else if (segue.identifier == "twoToFour"){
            let setView = segue.destination as! SettingsView;
            setView.feelz = feelz; // sends
            setView.painz = painz;
        }
        
    }
    
    override func viewWillAppear(_ animated: Bool) {
        
        super.viewWillAppear(animated)
        //Add animation code herw
        micButt.isEnabled = false  //2
        speechRecognizer?.delegate = self as? SFSpeechRecognizerDelegate  //3
        speechRecognizer = SFSpeechRecognizer(locale: Locale.init(identifier: lang))
        SFSpeechRecognizer.requestAuthorization { (authStatus) in  //4
            
            var isButtonEnabled = false
            
            switch authStatus {  //5
            case .authorized:
                isButtonEnabled = true
                
            case .denied:
                isButtonEnabled = false
                print("User denied access to speech recognition")
                
            case .restricted:
                isButtonEnabled = false
                print("Speech recognition restricted on this device")
                
            case .notDetermined:
                isButtonEnabled = false
                print("Speech recognition not yet authorized")
            }
            
            OperationQueue.main.addOperation() {
                self.micButt.isEnabled = isButtonEnabled
            }
        }
        
    }

}
