//
//  tensorDashViewController.swift
//  TensorDash
//
//  Created by Devang Patel on 14/03/20.
//  Copyright © 2020 Tensor Dash. All rights reserved.
//

import UIKit

class tensorDashViewController: UIViewController {

    //MARK: - Outlets
    @IBOutlet weak var tensorTable: UITableView!
    
    //MARK: - Variables
    private var projectName = ["Project-name-one","Project-name-two","Project-name-three"]
    private var status = ["Completed","In progress","Crashed"]
    private var epoch = ["9","19","19"]
    private var accuracy = ["0.123123","0.99999","0.25"]
    private var loss = ["0.123","0.1","0.99999"]
    private var indexPathForPrepareSegue : IndexPath?
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        tensorTable.tableFooterView = UIView()
    }

}


extension tensorDashViewController: UITableViewDelegate, UITableViewDataSource {
    
    
    //MARK: - Table View datasource Method
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 3
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tensorTable.dequeueReusableCell(withIdentifier: "cell", for: indexPath) as! tensorDashTableViewCell
        
        cell.projectName.text = projectName[indexPath.row]
        cell.epochLabel.text = epoch[indexPath.row]
        cell.accuracyLabel.text = accuracy[indexPath.row]
        cell.lossLabel.text = loss[indexPath.row]
        cell.statusLabel.text = status[indexPath.row]
        if status[indexPath.row] == "Completed" {
            cell.statusLabel.backgroundColor = #colorLiteral(red: 0.003921568627, green: 0.4823529412, blue: 0.02352941176, alpha: 1)
        }
        else if status[indexPath.row] == "In progress" {
            cell.statusLabel.backgroundColor = #colorLiteral(red: 0.6470588235, green: 0.3490196078, blue: 0, alpha: 1)
        }
        else {
            cell.statusLabel.backgroundColor = #colorLiteral(red: 0.6941176471, green: 0, blue: 0, alpha: 1)
        }
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        tableView.deselectRow(at: indexPath, animated: true)
        
        indexPathForPrepareSegue = indexPath
        
        performSegue(withIdentifier: "toInfo", sender: self)
        
    }
    
    //MARK: - Prepare for segue.
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "toInfo" {
            
            let destinationVC = segue.destination as! detailedInfoViewController
            destinationVC.projectName = projectName[indexPathForPrepareSegue!.row]
            destinationVC.loss = loss[indexPathForPrepareSegue!.row]
            destinationVC.accuracy = accuracy[indexPathForPrepareSegue!.row]
            destinationVC.epoch = epoch[indexPathForPrepareSegue!.row]
            destinationVC.status = status[indexPathForPrepareSegue!.row]
        }
    }
    
    // Setting height for rows in tableView
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 130.0
    }
    
    // Setting custom header heigth
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return 16
    }
    
    // Setting Custom headerView background color
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        let headerView = UIView()
        headerView.backgroundColor = #colorLiteral(red: 0.1882352941, green: 0.1882352941, blue: 0.1882352941, alpha: 1)
        return headerView
    }
    
   
}
