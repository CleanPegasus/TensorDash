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

    override func viewDidLoad() {
        super.viewDidLoad()
    
        
        //Hide Keyboard
        NotificationCenter.default.addObserver(self, selector: #selector(keyboardwilchange(notification:)), name: UIResponder.keyboardWillShowNotification, object: nil)
        NotificationCenter.default.addObserver(self, selector: #selector(keyboardwilchange(notification:)), name: UIResponder.keyboardWillHideNotification, object: nil)
        NotificationCenter.default.addObserver(self, selector: #selector(keyboardwilchange(notification:)), name: UIResponder.keyboardWillChangeFrameNotification, object: nil)
        
    
    }
    
    // MARK: - Code below this is for hiding keyboard
    
    deinit {
        NotificationCenter.default.removeObserver(self, name: UIResponder.keyboardWillShowNotification, object: nil)
        NotificationCenter.default.removeObserver(self, name: UIResponder.keyboardWillHideNotification, object: nil)
        NotificationCenter.default.removeObserver(self, name: UIResponder.keyboardWillChangeFrameNotification, object: nil)
    }
    
    func hideKeyboard(){
        view.resignFirstResponder()
    }
    
    @objc func keyboardwilchange(notification: Notification){
        view.frame.origin.y = -210
        
    }
    
    //UITextFieldDeligate Methods
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        hideKeyboard()
        return true
    }
    
    //Hide when touch outside keyboard
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        self.view.endEditing(true)
        view.frame.origin.y = 0
        
    }


}
