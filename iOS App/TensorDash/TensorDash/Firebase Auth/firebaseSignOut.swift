//
//  firebasesignOut.swift
//  TensorDash
//
//  Created by Devang Patel on 13/03/20.
//  Copyright © 2020 Tensor Dash. All rights reserved.
//

import Foundation
import Firebase

extension UIViewController {
    
    //MARK: - Signout settings
    func signOut() {
        let firebaseAuth = Auth.auth()
        do {
            // Set initial user default for login as false
            UserDefaults.standard.set(false, forKey: "login")
            try firebaseAuth.signOut()
            print("SignOut sucessful")
            let storyboard = UIStoryboard(name: "Main", bundle: nil)
            let controller = storyboard.instantiateViewController(withIdentifier: "loginViewController")
            self.present(controller, animated: true, completion: nil)
        } catch let signOutError as NSError {
            print ("Error signing out: %@", signOutError)
        }
    }
    
}
