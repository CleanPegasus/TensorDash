//
//  FirebaseAuth.swift
//  TensorDash
//
//  Created by Devang Patel on 13/03/20.
//  Copyright © 2020 Tensor Dash. All rights reserved.
//

import Foundation
import FirebaseAuth


class FirebaseAuth: UIViewController {
    
    
    //MARK: - Function for Login using email and password
    public static func emailLoginIn(email: String, pass: String, completion: @escaping (String) -> ()) {
        
        Auth.auth().signIn(withEmail: email, password: pass) { (user, error)
            in
            if error != nil {
                // Vibrates on errors
                UIDevice.invalidVibrate()
                completion(error?.localizedDescription ?? "Error")
            }
            else {
                // Vibrates on valid
                UIDevice.validVibrate()
                completion("Sucess")
            }
        }
    }
    
    
    // MARK: - Function for Sign-In using email and password
    public static func emailSignIn(email: String, pass: String, completion: @escaping (String) -> ()) {
        Auth.auth().createUser(withEmail: email, password: pass) { (authResult, error)
            in
            if error != nil {
                // Vibrates on errors
                UIDevice.invalidVibrate()
                print(error?.localizedDescription ?? "Error")
                completion("Error")
            }
            else {
                // Vibrates on valid
                UIDevice.validVibrate()
                completion("Sucess")
            }
        }
    }
    
}
