//
//  signInViewController.swift
//  TensorDash
//
//  Created by Devang Patel on 13/03/20.
//  Copyright © 2020 Tensor Dash. All rights reserved.
//

import UIKit

class signInViewController: UIViewController {
    
    
    
    // MARK: - Outlets
    @IBOutlet weak var emailTextF: UITextField!
    @IBOutlet weak var cpassTextF: UITextField!
    @IBOutlet weak var passTextF: UITextField!
    @IBOutlet weak var upperConstraint: NSLayoutConstraint!
    @IBOutlet weak var emailView: UIView!
    @IBOutlet weak var passView: UIView!
    @IBOutlet weak var cpassView: UIView!
    @IBOutlet weak var load: UIActivityIndicatorView!
    
    //MARK: - Variables
    var constant:CGFloat = 90.0
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Setting up textField Delegates
        textFieldDelegateSetUp()
        
        // Hidding activity indicator
        load.isHidden = true
        
        // ADDING Tap gestures
        self.view.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(signInViewController.dismissKeyboard)))
        
    }
    
    // Setting Status Bar to light
    override var preferredStatusBarStyle: UIStatusBarStyle {
        return .lightContent
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        // Function for checking newtwork connection
        checkNewtork(ifError: "Cannot Sign-In.")
    }
    
    // MARK: - Selector function for dismissing keyboard
    @objc func dismissKeyboard() {
        emailTextF.resignFirstResponder()
        passTextF.resignFirstResponder()
        cpassTextF.resignFirstResponder()
    }
    
    // MARK: - Function for textFieldDidBeginEditing
    func textFieldDidBeginEditing(_ textField: UITextField) {
        if (self.emailTextF.isEditing) {
            emailView.backgroundColor = #colorLiteral(red: 0.3411764801, green: 0.6235294342, blue: 0.1686274558, alpha: 1)
        }
        else if(self.passTextF.isEditing) {
            passView.backgroundColor = #colorLiteral(red: 0.3411764801, green: 0.6235294342, blue: 0.1686274558, alpha: 1)
        }
        else if(self.cpassTextF.isEditing) {
            cpassView.backgroundColor = #colorLiteral(red: 0.3411764801, green: 0.6235294342, blue: 0.1686274558, alpha: 1)
        }
        UIView.animate(withDuration: 0.3) {
            self.upperConstraint.constant -= self.constant
            self.view.layoutIfNeeded()
        }
    }
    
    
    
    // MARK: - Function for textFieldDidEndEditing
    func textFieldDidEndEditing(_ textField: UITextField) {
        if !(self.emailTextF.isEditing || self.passTextF.isEditing || self.cpassTextF.isEditing) {
            emailView.backgroundColor = UIColor.darkGray
            passView.backgroundColor = UIColor.darkGray
            cpassView.backgroundColor = UIColor.darkGray
            self.view.layoutIfNeeded()
            UIView.animate(withDuration: 0.3, animations: {
                self.upperConstraint.constant += self.constant
                self.view.layoutIfNeeded()
            })
        }
    }
    
    
    // MARK: - Create account action
    @IBAction func createAcc(_ sender: Any) {
        // Dismiss keyboard
        dismissKeyboard()
        load.isHidden = false
        load.startAnimating()
        
        if (emailTextF.text == "" || passTextF.text == "" || cpassTextF.text == "")
        {
            self.authAlert(titlepass: "Error", message: "Text Field is empty.")
            self.load.stopAnimating()
            self.load.isHidden = true
        }
        else if !(passTextF.text==cpassTextF.text) {
            self.authAlert(titlepass: "Error", message: "Password does not match. Please verify again.")
            self.load.stopAnimating()
            self.load.isHidden = true
        }
        else if (passTextF.text==cpassTextF.text) {
            // Check internet connection
            checkNewtork(ifError: "Cannot Sign-In.")
            FirebaseAuth.emailSignIn(email: emailTextF.text!, pass: passTextF.text!) { (result) in
                switch result {
                case "The password must be 6 characters long or more.":
                    self.authAlert(titlepass: "Error", message: result)
                case "Sucess":
                    // Sucess then login through by entering email and password in Login page
                    self.dismissAlert(titlepass: "Registered Successfully!", message: "Please Login with your Email and Password.")
                case "The email address is already in use by another account.":
                    self.authAlert(titlepass: "Error", message: result)
                default:
                    self.authAlert(titlepass: "Error", message: "Contact Developer.")
                }
                self.load.stopAnimating()
                self.load.isHidden = true
            }
        }
        else {
            self.authAlert(titlepass: "Error", message: "Contact Developer.")
        }
        
        
    }
    
    
    // Go back to login page
    @IBAction func back(_ sender: Any) {
        dismiss(animated: true, completion: nil)
    }
    
}


//MARK: - Extensions
extension signInViewController: UITextFieldDelegate {
    
    //Setup textfield delegates
    func textFieldDelegateSetUp() {
        emailTextF.delegate = self
        passTextF.delegate = self
        cpassTextF.delegate = self
    }
    
    // Setting to go to next textField when pressed next in keyboard
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        
        if textField == cpassTextF {
            self.view.endEditing(true)
        }
        else {
            if passTextF.isEditing == true {
                cpassTextF.becomeFirstResponder()
            }
            else {
                passTextF.becomeFirstResponder()
            }
        }
        return true
    }
}
