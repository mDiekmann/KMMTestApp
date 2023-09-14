//
//  RollHistoryView.swift
//  iosApp
//
//  Created by Michael Diekmann on 9/11/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import KMMViewModelSwiftUI
import CommonKMM

struct RollHistoryView: View {
    @ObservedViewModel private var viewModel: RollHistoryViewModel
    
    init(viewModel: RollHistoryViewModel) {
        self.viewModel = viewModel
    }
    
    var body: some View {
        NavigationView {
            List(viewModel.diceRolls, id: \.rollTimeEpoch) { diceRoll in
                Text(diceRoll.input)
            }
                .navigationBarTitleDisplayMode(.inline)
                   .toolbar {
                       ToolbarItem(placement: .principal) {
                           HStack {
                               Text(SharedRes.strings().newRollTitle.desc().localized()).font(.headline)
                           }
                       }
                       ToolbarItem(placement: .navigationBarTrailing) {
                           Button(action: {
                               viewModel.clearDiceRolls()
                           }, label: {
                               Label("Clear History", systemImage: "trash")
                                   .labelStyle(.iconOnly)
                           })
                       }
                   }
        }
    }
}

struct RollHistoryView_Previews: PreviewProvider {
    static var previews: some View {
        RollHistoryView(viewModel: RollHistoryViewModel())
    }
}
