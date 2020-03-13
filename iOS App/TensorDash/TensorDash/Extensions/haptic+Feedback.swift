//
//  haptic+Feedback.swift
//  TensorDash
//
//  Created by Devang Patel on 13/03/20.
//  Copyright © 2020 Tensor Dash. All rights reserved.
//

import Foundation
import UIKit
import AudioToolbox

extension UIDevice {
    
    // Vibrates when any error occur like invalid password etc.
    static func invalidVibrate() {
        AudioServicesPlaySystemSound(SystemSoundID(1102))
    }
    
    // For sucess login
    static func validVibrate() {
        AudioServicesPlaySystemSound(kSystemSoundID_Vibrate)
    }
}
