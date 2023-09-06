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

    // can this be done with just using the viewModel inputs like in Android?
    // it seems the Picker wants to write to those which doesn't work with the setup
    @State private var selectedDiceCount: Int32
    @State private var selectedDiceSides: DiceSides
    
    private var possibleDiceCounts: Array<Int32>

    init(viewModel: NewRollViewModel) {
        self.viewModel = viewModel
        self.selectedDiceCount = viewModel.diceCountInput
        self.selectedDiceSides = viewModel.diceSidesInput
        
        // viewModel.possibleDiceCounts is an array of CKMMInt which map to an NSNumber
        // viewModel.diceCountInput is an Int32
        // viewModel.updateDiceCount() takes in Int32
        // mapping possibleDiceCounts to Int32 so types match and Picker selection can happen
        self.possibleDiceCounts = viewModel.possibleCountArrayToInt32()
        
    }
    
    var body: some View {
        VStack {
            HStack {
                Picker("Dice Count", selection: $selectedDiceCount) {
                    ForEach(possibleDiceCounts, id: \.self) { number in
                        Text("\(number)")
                    }
                }
                .pickerStyle(WheelPickerStyle())
                .onChange(of: selectedDiceCount) { newValue in
                    viewModel.updateDiceCount(diceCount: newValue)
                }
                
                Picker("Dice Sides", selection: $selectedDiceSides) {
                    ForEach(viewModel.possibleDiceSides, id: \.self) { number in
                        Text("\(number)")
                    }
                }
                .pickerStyle(WheelPickerStyle())
                .onChange(of: selectedDiceSides) { newValue in
                    viewModel.updateDiceSides(diceSides: newValue)
                }
            
            }

            Text("You selected: \(selectedDiceCount)\(selectedDiceSides)")
            
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
