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
    var constant:CGFloat = 80.0
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Setting up textField Delegates
        textFieldDelegateSetUp()
        
        // Hidding activity indicator
        load.isHidden = true
        
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
    
//    // MARK: - Function for textFieldDidBeginEditing
//    func textFieldDidBeginEditing(_ textField: UITextField) {
//        if (self.emailTextF.isEditing) {
//            emailTextView.backgroundColor = #colorLiteral(red: 0.3411764801, green: 0.6235294342, blue: 0.1686274558, alpha: 1)
//        }
//        else if(self.passTextF.isEditing) {
//            passTextView.backgroundColor = #colorLiteral(red: 0.3411764801, green: 0.6235294342, blue: 0.1686274558, alpha: 1)
//        }
//        UIView.animate(withDuration: 0.3) {
//            self.upperConstraint.constant -= 70.0
//            self.view.layoutIfNeeded()
//        }
//    }
//    
//    
//    
//    // MARK: - Function for textFieldDidEndEditing
//    func textFieldDidEndEditing(_ textField: UITextField) {
//        if !(self.emailTextF.isEditing || self.passTextF.isEditing) {
//            emailTextView.backgroundColor = UIColor.darkGray
//            passTextView.backgroundColor = UIColor.darkGray
//            self.view.layoutIfNeeded()
//            UIView.animate(withDuration: 0.3, animations: {
//                self.upperConstraint.constant += 70.0
//                self.view.layoutIfNeeded()
//            })
//        }
//    }
    
    
    // MARK: - Create account action
    @IBAction func createAcc(_ sender: Any) {
        // Dismiss keyboard
        dismissKeyboard()
        load.isHidden = false
        load.startAnimating()
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
//    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
//        if textField == passTextF {
//            self.view.endEditing(true)
//        } else {
//            passTextF.becomeFirstResponder()
//        }
//        return true
//    }
}
