//
//  NewRollState.swift
//  iosApp
//
//  Created by Michael Diekmann on 9/1/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import CommonKMM

class NewRollState: ObservableObject {
    let viewModel: NewRollViewModel
    
    init() {
        viewModel = NewRollViewModel()
    }
}
