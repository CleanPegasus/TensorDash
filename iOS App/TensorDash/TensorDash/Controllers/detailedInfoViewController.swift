//
//  detailedInfoViewController.swift
//  TensorDash
//
//  Created by Devang Patel on 28/03/20.
//  Copyright © 2020 Tensor Dash. All rights reserved.
//

import UIKit

class detailedInfoViewController: UIViewController {

    //MARK: - Outlets
    @IBOutlet weak var navBarTitle: UINavigationItem!
    @IBOutlet weak var nameLabel: UILabel!
    @IBOutlet weak var validationLossLabel: UILabel!
    @IBOutlet weak var validationAccLabel: UILabel!
    @IBOutlet weak var lossLabel: UILabel!
    @IBOutlet weak var accuracyLabel: UILabel!
    @IBOutlet weak var epochLabel: UILabel!
    @IBOutlet weak var statusLabel: UILabel!
    
    //MARK: - Variables
    internal var projectName : String?
    internal var validationLoss : String?
    internal var validationAcc : String?
    internal var loss : String?
    internal var accuracy : String?
    internal var epoch : String?
    internal var status : String?
   
    
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        //calling initial() to set up labels and navBar title
        initial()
        
    }
    
    //MARK: - Function to set view - initialise
    func initial() {
        navBarTitle.title = "Project Details"
        nameLabel.text = projectName
        epochLabel.text = epoch
        accuracyLabel.text = accuracy
        lossLabel.text = loss
        validationAccLabel.text = "0.999999"
        validationLossLabel.text = "0.99999"
        statusLabel.text = status
        if status == "Completed" {
            statusLabel.backgroundColor = #colorLiteral(red: 0.003921568627, green: 0.4823529412, blue: 0.02352941176, alpha: 1)
        }
        else if status == "In progress" {
            statusLabel.backgroundColor = #colorLiteral(red: 0.6470588235, green: 0.3490196078, blue: 0, alpha: 1)
        }
        else {
            statusLabel.backgroundColor = #colorLiteral(red: 0.6941176471, green: 0, blue: 0, alpha: 1)
        }
    }
    
    
}
