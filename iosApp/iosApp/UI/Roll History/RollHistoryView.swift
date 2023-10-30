//
//  RollHistoryView.swift
//  iosApp
//
//  Created by Michael Diekmann on 9/11/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import CommonKMM

struct RollHistoryView: View {
    @State private var viewModel: RollHistoryViewModel
    
    @State private var viewState: RollHistoryViewState = .Initial.shared
    
    init(viewModel: RollHistoryViewModel) {
        self.viewModel = viewModel
    }
    
    var body: some View {
        NavigationView {
            ZStack {
                switch onEnum(of: viewState) {
                case .initial:
                    Spacer()
                case .empty:
                    Spacer()
                    Text(SharedRes.strings().rollHistoryEmptyLabel.desc().localized())
                    Spacer()
                case.success(let viewContent):
                    List(viewContent.rolls, id: \.rollTime) { diceRoll in
                        Text(diceRoll.equation)
                    }
                // TODO: this triggers an alert modal
                case .error(let error):
                    Spacer()
                    Text(error.error)
                    Spacer()
                    
                }
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
                        Task {
                            try? await self.viewModel.clearDiceRolls()
                        }
                    }, label: {
                        Label("Clear History", systemImage: "trash")
                            .labelStyle(.iconOnly)
                    })
                }
            }
            .task {
                // this isn't great, I'm assuming this is what the swift package for KMMViewModel handles
                await withTaskCancellationHandler(
                    operation: {
                        Task {
                            // begins observing updates to the roll history
                            try? await viewModel.activate()
                        }
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

struct RollHistoryView_Previews: PreviewProvider {
    static var previews: some View {
        RollHistoryView(viewModel: RollHistoryViewModel())
    }
}
