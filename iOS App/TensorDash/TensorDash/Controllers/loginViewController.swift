//
//  loginViewController.swift
//  TensorDash
//
//  Created by Devang Patel on 08/03/20.
//  Copyright © 2020 Tensor Dash. All rights reserved.
//

import UIKit
import Firebase

class loginViewController: UIViewController {

    //MARK: - Outlets
    @IBOutlet weak var passTextF: UITextField!
    @IBOutlet weak var emailTextF: UITextField!
    @IBOutlet weak var upperConstraint: NSLayoutConstraint!
    @IBOutlet weak var passTextView: UIView!
    @IBOutlet weak var emailTextView: UIView!
    
    //MARK: - Variables
    var constant:CGFloat = 80.0
    
    override func viewDidLoad() {
        super.viewDidLoad()
    
        // Setting up textField Delegates
        textFieldDelegateSetUp()
        
        // ADDING Tap gestures
        self.view.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(loginViewController.dismissKeyboard)))
    
    }
 
    @objc func dismissKeyboard() {
        emailTextF.resignFirstResponder()
        passTextF.resignFirstResponder()
    }
    
    func textFieldDidBeginEditing(_ textField: UITextField) {
        UIView.animate(withDuration: 0.3) {
            self.upperConstraint.constant -= 70.0
            self.view.layoutIfNeeded()
        }
    }
    
    func textFieldDidEndEditing(_ textField: UITextField) {
        if !(self.emailTextF.isEditing || self.passTextF.isEditing) {
            self.view.layoutIfNeeded()
            UIView.animate(withDuration: 0.3, animations: {
                self.upperConstraint.constant += 70.0
                self.view.layoutIfNeeded()
            })
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
}
