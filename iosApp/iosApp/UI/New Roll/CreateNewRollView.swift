//
//  CreateNewRollView.swift
//  iosApp
//
//  Created by Michael Diekmann on 9/1/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import KMMViewModelSwiftUI
import CommonKMM

struct CreateNewRollView: View {
    @ObservedViewModel private var viewModel: NewRollViewModel
    
    private var possibleDiceCounts: Array<Int32>

    init(viewModel: NewRollViewModel) {
        self.viewModel = viewModel
        
        // viewModel.possibleDiceCounts is an array of CKMMInt which map to an NSNumber
        // viewModel.diceCountInput is an Int32
        // viewModel.updateDiceCount() takes in Int32
        // mapping possibleDiceCounts to Int32 so types match and Picker selection can happen
        self.possibleDiceCounts = viewModel.possibleDiceCountArr
        
    }
    
    var body: some View {
        VStack {
            HStack {
                Picker("Dice Count", selection: $viewModel.selectedDiceCount) {
                    ForEach(possibleDiceCounts, id: \.self) { number in
                        Text("\(number)")
                    }
                }
                .pickerStyle(WheelPickerStyle())
                
            Picker("Dice Sides", selection: $viewModel.selectedDiceSides) {
                    ForEach(viewModel.possibleDiceSides, id: \.self) { number in
                        Text("\(number)")
                    }
                }
                .pickerStyle(WheelPickerStyle())
            
            }

            Button("Roll Dice") {
                viewModel.rollDice()
            }
        }
    }
}

struct CreateNewRollView_Previews: PreviewProvider {
    static var previews: some View {
        CreateNewRollView(viewModel: NewRollViewModel())
    }
}
