//
//  NewDiceRollView.swift
//  iosApp
//
//  Created by Michael Diekmann on 9/8/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import KMMViewModelSwiftUI
import CommonKMM

struct NewDiceRollView: View {
    @ObservedViewModel private var viewModel: NewRollViewModel
    
    init(viewModel: NewRollViewModel) {
        self.viewModel = viewModel
    }
    
    var body: some View {
        NavigationView {
            VStack(spacing: 20.0) {
                CreateNewRollView(viewModel: viewModel)
                LatestRollView(viewState: viewModel.viewState.latestRollViewState)
            }
            .overlay {
                if viewModel.viewState.isLoading {
                    ProgressView("Loading")
                }
            }
            .navigationBarTitleDisplayMode(.inline)
               .toolbar {
                   ToolbarItem(placement: .principal) {
                       HStack {
                           Text(SharedRes.strings().newRollTitle.desc().localized())
                       }
                   }
               }
        }
    }
}

struct NewDiceRollView_Previews: PreviewProvider {
    static var previews: some View {
        NewDiceRollView(viewModel: NewRollViewModel())
    }
}
