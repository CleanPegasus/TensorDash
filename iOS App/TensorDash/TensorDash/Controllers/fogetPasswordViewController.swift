//
//  fogetPasswordViewController.swift
//  TensorDash
//
//  Created by Devang Patel on 13/03/20.
//  Copyright © 2020 Tensor Dash. All rights reserved.
//

import UIKit

class fogetPasswordViewController: UIViewController {

    //MARK: - Outlets
    @IBOutlet weak var emailTextF: UITextField!
    @IBOutlet weak var load: UIActivityIndicatorView!
    @IBOutlet weak var emailView: UIView!
    @IBOutlet weak var upperConstraint: NSLayoutConstraint!
    
    //MARK: - Variables
    var constant:CGFloat = 40.0
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Setting up textField Delegates
        textFieldDelegateSetUp()
        
        // Hidding activity indicator
        load.isHidden = true
        
        // ADDING Tap gestures
        self.view.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(fogetPasswordViewController.dismissKeyboard)))
        
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        // Function for checking newtwork connection
        checkNewtork(ifError: "Cannot Reset Password.")
    }
    
    // MARK: - Selector function for dismissing keyboard
    @objc func dismissKeyboard() {
        emailTextF.resignFirstResponder()
    }
    
    // MARK: - Function for textFieldDidBeginEditing
    func textFieldDidBeginEditing(_ textField: UITextField) {
        if (self.emailTextF.isEditing) {
            emailView.backgroundColor = #colorLiteral(red: 0.3411764801, green: 0.6235294342, blue: 0.1686274558, alpha: 1)
        }
        UIView.animate(withDuration: 0.3) {
            self.upperConstraint.constant -= self.constant
            self.view.layoutIfNeeded()
        }
    }
    
    
    
    // MARK: - Function for textFieldDidEndEditing
    func textFieldDidEndEditing(_ textField: UITextField) {
        if !(self.emailTextF.isEditing) {
            emailView.backgroundColor = UIColor.darkGray
            self.view.layoutIfNeeded()
            UIView.animate(withDuration: 0.3, animations: {
                self.upperConstraint.constant += self.constant
                self.view.layoutIfNeeded()
            })
        }
    }
    
    
    //MARK: - Reset password action
    @IBAction func resetAction(_ sender: UIButton) {
    }
    
    // Go to login page
    @IBAction func goBack(_ sender: Any) {
        dismiss(animated: true, completion: nil)
    }
    
    
}


//MARK: - Extensions
extension fogetPasswordViewController: UITextFieldDelegate {
    
    //Setup textfield delegates
    func textFieldDelegateSetUp() {
        emailTextF.delegate = self
    }
    
    // Setting to go to hide keyboard when pressed done in keyboard
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        self.view.endEditing(true)
        return true
    }
}
