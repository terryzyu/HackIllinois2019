//
//  ViewController3.swift
//  Nurse Connection
// outputs data
//  Created by Archie Paredes

import UIKit

class ViewController3: UIViewController {
    var feelz:[String:String] = ["How are you feeling?":"",
                                 "Left Hand Grip Strength?":"",
                                 "Right Hand Grip Strength?":"",
                                 "How fatigue are you?":""];
    var painz:[String:String] = ["Do you feel any pain?":"",
                                 "What actions cause the pain?":"",
                                 "How severe is the pain?" : "",
                                 "How does the patient look?":""]
    
    @IBOutlet weak var dataText: UITextView!
    
    var hm = [String : String]();
    var dat:String = ""
    var keyWords = [String]()
    
    @IBOutlet weak var dataLabel: UILabel!
    @IBOutlet weak var dataView: UIView!

    
    @IBAction func updateButton(_ sender: UIButton) {
        
        for (k,v) in painz{
            print("\(k)  \(v)\n")
            dat += "\(k)  \(v)\n\n"
        }
        
        for (k,v) in feelz{
            print("\(k)  \(v)\n")
            dat += "\(k)  \(v)\n\n"
        }
        dataText.text = dat;
    }
    

    @IBAction func goToSetting(_ sender: UIButton) {
        performSegue(withIdentifier: "threeToFour", sender: self);
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
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        
        if (segue.identifier == "threeToFour"){
            let view4 = segue.destination as! SettingsView;
            view4.feelz = feelz;
            view4.painz = painz;
        }
    }

}
