//
//  tensorDashTableViewCell.swift
//  TensorDash
//
//  Created by Devang Patel on 14/03/20.
//  Copyright © 2020 Tensor Dash. All rights reserved.
//

import UIKit

class tensorDashTableViewCell: UITableViewCell {
    
    //MARK: - Outlets
    @IBOutlet weak var projectName: UILabel!
    @IBOutlet weak var lossLabel: UILabel!
    @IBOutlet weak var accuracyLabel: UILabel!
    @IBOutlet weak var epochLabel: UILabel!
    @IBOutlet weak var statusLabel: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
