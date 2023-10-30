//
//  NewRollViewModel+iOS.swift
//  iosApp
//
//  Created by Michael Diekmann on 10/24/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import CommonKMM
import Combine

class NewRollViewModel: CommonKMM.NewRollViewModel {
    @Published var selectedDiceCount: Int32 = 1 {
        didSet {
            updateDiceCount(diceCount: selectedDiceCount)
        }
    }
    
    @Published var selectedDiceSides: DiceSides = DiceSides.d4 {
        didSet {
            updateDiceSides(diceSides: selectedDiceSides)
        }
    }
    
    // viewModel.possibleDiceCounts is an array of CKMMInt which map to an NSNumber
    // viewModel.diceCountInput is an Int32
    // viewModel.updateDiceCount() takes in Int32
    // mapping possibleDiceCounts to Int32 so types match and Picker selection can happen
    var possibleDiceCountArr: [Int32] {
        return self.possibleDiceCounts.map{ Int32(truncating: $0) }
    }
    
    override init() {
        super.init()
        selectedDiceCount = Int32(truncating: self.diceCountInput.value)
        selectedDiceSides = self.diceSidesInput.value
    }
}
