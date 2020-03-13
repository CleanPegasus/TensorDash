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
            self.upperConstraint.constant -= 70.0
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
                self.upperConstraint.constant += 70.0
                self.view.layoutIfNeeded()
            })
        }
    }
    
    
    //MARK: - Login Action
    @IBAction func loginAction(_ sender: UIButton) {
        dismissKeyboard()
        load.isHidden = false
        load.startAnimating()
        
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
