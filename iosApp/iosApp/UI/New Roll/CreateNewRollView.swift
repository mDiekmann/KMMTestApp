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
    @ObservedViewModel var viewModel = NewRollViewModel()

    var colors = ["Red", "Green", "Blue", "Tartan"]
    @State private var selectedColor = "Red"
    @State private var diceSides: Int32 = 6

    var body: some View {
        VStack {
            HStack {
                Picker("Please choose a color", selection: $selectedColor) {
                    ForEach(colors, id: \.self) {
                        Text($0)
                    }
                }
                Text("d")
                Picker("Your age", selection: $diceSides) {
                    ForEach(viewModel.possibleDiceSides, id: \.self) { number in
                        Text("\(number)")
                    }
                }
            }
            Text("You selected: \(diceSides)")
        }
    }
}

struct CreateNewRollView_Previews: PreviewProvider {
    static var previews: some View {
        CreateNewRollView()
    }
}
