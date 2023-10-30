//
//  NewDiceRollView.swift
//  iosApp
//
//  Created by Michael Diekmann on 9/8/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import CommonKMM

struct NewDiceRollView: View {
    @State private var viewModel: NewRollViewModel
    @State private var viewState: NewRollViewState
    
    init(viewModel: NewRollViewModel) {
        self.viewModel = viewModel
        self.viewState = viewModel.viewState.value
    }
    
    var body: some View {
        NavigationView {
            VStack(spacing: 20.0) {
                CreateNewRollView(viewModel: viewModel)
                LatestRollView(viewState: viewState.latestRollViewState)
            }
            .activity(isVisible: viewModel.viewState.value.isLoading)
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
               ToolbarItem(placement: .principal) {
                   HStack {
                       Text(SharedRes.strings().newRollTitle.desc().localized())
                   }
               }
            }
            .task {
                // this isn't great, I'm assuming this is what the swift package for KMMViewModel handles
                await withTaskCancellationHandler(
                    operation: {
                        for await viewState in viewModel.viewState {
                            self.viewState = viewState
                        }
                    },
                    onCancel: {
                        viewModel.clear()
                    }
                )
            }
        }
    }
}

struct NewDiceRollView_Previews: PreviewProvider {
    static var previews: some View {
        NewDiceRollView(viewModel: NewRollViewModel())
    }
}
