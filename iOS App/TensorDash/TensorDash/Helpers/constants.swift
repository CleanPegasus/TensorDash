//
//  constants.swift
//  TensorDash
//
//  Created by Devang Patel on 28/03/20.
//  Copyright © 2020 Tensor Dash. All rights reserved.
//

import Foundation
import Firebase



internal func getEmail() -> String {
    let userEmail = Auth.auth().currentUser?.email
    return userEmail ?? "not Found Pls contact developer."
}
