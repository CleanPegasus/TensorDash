//
//  checkNetwork.swift
//  TensorDash
//
//  Created by Devang Patel on 13/03/20.
//  Copyright © 2020 Tensor Dash. All rights reserved.
//

import Foundation
import UIKit

extension UIViewController
{
    internal func checkNewtork(ifError: String) {
        checkConnection { (status, statusCode) in
            if statusCode == 404{
                print("No connection!!")
                // Vibrates on errors
                UIDevice.invalidVibrate()
                self.errorAlert(titlepass: ifError)
            }else{
                print("connection existing")
            }
        }
    }
}
