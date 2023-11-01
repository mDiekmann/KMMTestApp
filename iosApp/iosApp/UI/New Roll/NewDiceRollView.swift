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
    @ObservedObject private var viewModel: NativeNewRollViewModel
    //@State private var viewState: NewRollViewState
    
    init(viewModel: NativeNewRollViewModel) {
        self.viewModel = viewModel
        //self.viewState = viewModel.viewState.value
    }
    
    var body: some View {
        NavigationView {
            VStack(spacing: 20.0) {
                CreateNewRollView(viewModel: viewModel)
                LatestRollView(viewState: viewModel.latestRollViewState)
            }
            .activity(isVisible: viewModel.isLoading)
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
        NewDiceRollView(viewModel: NativeNewRollViewModel(userSettings: iOSUserSettings(delegate: UserDefaults.standard)))
    }
}
