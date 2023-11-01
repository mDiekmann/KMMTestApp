//
//  CreateNewRollView.swift
//  iosApp
//
//  Created by Michael Diekmann on 9/1/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import CommonKMM

struct CreateNewRollView: View {
    @ObservedObject private var viewModel: NativeNewRollViewModel

    init(viewModel: NativeNewRollViewModel) {
        self.viewModel = viewModel
    }
    
    var body: some View {
        VStack {
            HStack {
                Picker("Dice Count", selection: $viewModel.diceCountInput) {
                    ForEach(viewModel.possibleDiceCounts, id: \.self) { number in
                        Text("\(number)")
                    }
                }
                .pickerStyle(WheelPickerStyle())
                
            Picker("Dice Sides", selection: $viewModel.diceSidesInput) {
                    ForEach(viewModel.possibleDiceSides, id: \.self) { number in
                        Text(number.name)
                    }
                }
                .pickerStyle(WheelPickerStyle())
            
            }

            Button("Roll Dice") {
                Task{
                    await viewModel.rollDice()
                }
            }
        }
    }
}

struct CreateNewRollView_Previews: PreviewProvider {
    static var previews: some View {
        CreateNewRollView(viewModel: NativeNewRollViewModel(userSettings: iOSUserSettings(delegate: UserDefaults.standard)))
    }
}
