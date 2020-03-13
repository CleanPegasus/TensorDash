//
//  loginViewController.swift
//  TensorDash
//
//  Created by Devang Patel on 08/03/20.
//  Copyright © 2020 Tensor Dash. All rights reserved.
//

import UIKit

class loginViewController: UIViewController {
    
    //MARK: - Outlets
    @IBOutlet weak var passTextF: UITextField!
    @IBOutlet weak var emailTextF: UITextField!
    @IBOutlet weak var upperConstraint: NSLayoutConstraint!
    @IBOutlet weak var passTextView: UIView!
    @IBOutlet weak var emailTextView: UIView!
    @IBOutlet weak var load: UIActivityIndicatorView!
    
    //MARK: - Variables
    var constant:CGFloat = 80.0
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Setting up textField Delegates
        textFieldDelegateSetUp()
        
        // Hidding activity indicator
        load.isHidden = true
        
        // ADDING Tap gestures
        self.view.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(loginViewController.dismissKeyboard)))
        
    }
    
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        // Function for checking newtwork connection
        checkNewtork(ifError: "Cannot login.")
    }
    
    
    // MARK: - Selector function for dismissing keyboard
    @objc func dismissKeyboard() {
        emailTextF.resignFirstResponder()
        passTextF.resignFirstResponder()
    }
    
    
    // MARK: - Function for textFieldDidBeginEditing
    func textFieldDidBeginEditing(_ textField: UITextField) {
        if (self.emailTextF.isEditing) {
            emailTextView.backgroundColor = #colorLiteral(red: 0.3411764801, green: 0.6235294342, blue: 0.1686274558, alpha: 1)
        }
        else if(self.passTextF.isEditing) {
            passTextView.backgroundColor = #colorLiteral(red: 0.3411764801, green: 0.6235294342, blue: 0.1686274558, alpha: 1)
        }
        UIView.animate(withDuration: 0.3) {
            self.upperConstraint.constant -= self.constant
            self.view.layoutIfNeeded()
        }
    }
    
    
    // MARK: - Function for textFieldDidEndEditing
    func textFieldDidEndEditing(_ textField: UITextField) {
        if !(self.emailTextF.isEditing || self.passTextF.isEditing) {
            emailTextView.backgroundColor = UIColor.darkGray
            passTextView.backgroundColor = UIColor.darkGray
            self.view.layoutIfNeeded()
            UIView.animate(withDuration: 0.3, animations: {
                self.upperConstraint.constant += self.constant
                self.view.layoutIfNeeded()
            })
        }
    }
    
    
    //MARK: - Login Action
    @IBAction func loginAction(_ sender: UIButton) {
        // Dismiss keyboard
        dismissKeyboard()
        load.isHidden = false
        load.startAnimating()
        if (emailTextF.text == "" || passTextF.text == "")
        {
            self.authAlert(titlepass: "Error", message: "Text Field is empty.")
            self.load.stopAnimating()
            self.load.isHidden = true
        }
        else {
            // Check internet connection
            checkNewtork(ifError: "Cannot login")
            FirebaseAuth.emailLoginIn(email: emailTextF.text!, pass: passTextF.text!) { (result) in
                switch result {
                case "The email address is badly formatted.":
                    self.authAlert(titlepass: "Error", message: result)
                case "The password is invalid or the user does not have a password.":
                    self.authAlert(titlepass: "Error", message: result)
                case "There is no user record corresponding to this identifier. The user may have been deleted.":
                    self.authAlert(titlepass: "Error", message: "There is no user registered to this Email ID.")
                case "Sucess":
                    print("WQe")
                default:
                    self.authAlert(titlepass: "Error", message: "Contact Developer.")
                }
                self.load.stopAnimating()
                self.load.isHidden = true
            }
        }
    }
    
}



//MARK: - Extensions
extension loginViewController: UITextFieldDelegate {
    
    //Setup textfield delegates
    func textFieldDelegateSetUp() {
        emailTextF.delegate = self
        passTextF.delegate = self
    }
    
    // Setting to go to next textField when pressed next in keyboard
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        if textField == passTextF {
            self.view.endEditing(true)
        } else {
            passTextF.becomeFirstResponder()
        }
        return true
    }
}
