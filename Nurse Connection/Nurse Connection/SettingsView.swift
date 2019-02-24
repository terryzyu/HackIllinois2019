//
//  SettingsView.swift
//  Nurse Connection
//
//  Created by Archie Paredes 

import UIKit

class SettingsView: UIViewController {
    var feelz:[String:String] = ["How are you feeling?":"",
                                 "Left Hand Grip Strength?":"",
                                 "Right Hand Grip Strength?":"",
                                 "How fatigue are you?":""];
    var painz:[String:String] = ["Do you feel any pain?":"",
                                 "What actions cause the pain?":"",
                                 "How severe is the pain?" : "",
                                 "How does the patient look?":""]
    
    
    var v:Int = 0;
    
    var keyWords = [String]()
    
    @IBOutlet weak var newQuestTF: UITextField!
    @IBOutlet weak var addButton: UIButton!
    @IBAction func questionView(_ sender: UIButton) {
        var message:String = "";
        var iter:Int = 1;
        for (i,_) in feelz{
            message += "\(iter).) \(i)\n"
            iter += 1;
        }
        for (i,_) in painz{
            message += "\(iter).) \(i)\n"
            iter += 1;
        }
        alert_pop(mes: message);
    }
    
    @IBAction func addToQs(_ sender: UIButton) {
        if(newQuestTF.text!.isEmpty){
            errorPop(mes: "Empty input")
        } else {
            if (painz.keys.contains(newQuestTF.text!)){
                errorPop(mes: "Question exists")
            }else {
                // (newQuestTF.text!).last! == "?"
//                keyWords[newQuestTF.text!] = "";
//                newQuestTF.text = ""
                painz[newQuestTF.text!] = "";
            }
        }
    }
    
    @IBAction func delfromQs(_ sender: UIButton) {
        if(newQuestTF.text!.isEmpty){
            errorPop(mes: "Empty input")
            return;
        }
        if (keyWords.contains(newQuestTF.text!)){
            keyWords.remove(at: keyWords.firstIndex(of:newQuestTF.text!)!);
        }
    }
    func alert_pop(mes:String){ // handles alerts
        let alert = UIAlertController(title: "Valid Commands:", message: mes, preferredStyle: .alert);
        let dismiss = UIAlertAction(title: "Dismiss", style: .cancel, handler: nil);
        alert.addAction(dismiss);
        self.present(alert, animated: true, completion: nil);
    }
    
    func errorPop(mes:String){ // handles alerts
        let alert = UIAlertController(title: "Invalid input", message: mes, preferredStyle: .alert);
        let dismiss = UIAlertAction(title: "Dismiss", style: .cancel, handler: nil);
        alert.addAction(dismiss);
        self.present(alert, animated: true, completion: nil);
    }
    
    
    @IBAction func goToRecord(_ sender: UIButton) {
        v = 2;
        performSegue(withIdentifier: "fourToTwo", sender: self);
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if (v == 2){
            let view2 = segue.destination as! ViewController2;
            view2.feelz = feelz;
            view2.painz = painz;
        } else {v = 0}
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
