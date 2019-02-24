//
//  ViewController.swift
//  Nurse Connection
//
//  Created by Archie Paredes 

import UIKit
import Speech

class ViewController: UIViewController {
    var username:String = "hello";
    var pw:String = "world";
    @IBOutlet weak var usernameTF: UITextField!
    @IBOutlet weak var passwordTF: UITextField!
    
    func alert_pop(mes:String){ // handles alerts
        let alert = UIAlertController(title: "Error!", message: mes, preferredStyle: .alert);
        let dismiss = UIAlertAction(title: "Dismiss", style: .cancel, handler: nil);
        alert.addAction(dismiss);
        self.present(alert, animated: true, completion: nil);
    }
    
    @IBAction func loginButton(_ sender: UIButton) {
        if (passwordTF.text == "" || usernameTF.text == ""){
            alert_pop(mes: "Username/Password Invalid");
            return;
        } else if (passwordTF.text == pw && usernameTF.text == username){} // move on
        else{
            alert_pop(mes: "Username/Password Invalid");
            return;
        }
    }
    override func viewDidLoad() {
        
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }


}

