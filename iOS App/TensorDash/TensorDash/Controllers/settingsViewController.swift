//
//  settingsViewController.swift
//  TensorDash
//
//  Created by Devang Patel on 13/03/20.
//  Copyright © 2020 Tensor Dash. All rights reserved.
//

import UIKit

class settingsViewController: UIViewController {

    //MARK: - Outlets
    @IBOutlet weak var emailLabel: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        emailLabel.text = getEmail()
    }
    
    @IBAction func logoutAct(_ sender: Any) {
        
        signOut()
        
    }
}
