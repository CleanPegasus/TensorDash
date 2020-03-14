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
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }

}


extension tensorDashViewController: UITableViewDelegate, UITableViewDataSource {
    
    
    //MARK: - Table View datasource Method
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 3
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tensorTable.dequeueReusableCell(withIdentifier: "cell", for: indexPath) as! tensorDashTableViewCell
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
         tableView.deselectRow(at: indexPath, animated: true)
    }
    
    // Setting height for rows in tableView
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 120.0
    }
    
    // Setting custom header heigth
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return 17
    }
    
    // Setting Custom headerView background color
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        let headerView = UIView()
        headerView.backgroundColor = #colorLiteral(red: 0.1882352941, green: 0.1882352941, blue: 0.1882352941, alpha: 1)
        return headerView
    }
    
   
}
