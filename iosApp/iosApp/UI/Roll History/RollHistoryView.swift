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
    @ObservedObject private var viewModel: NativeRollHistoryViewModel
    
    init(viewModel: NativeRollHistoryViewModel) {
        self.viewModel = viewModel
    }
    
    var body: some View {
        NavigationView {
            ZStack {
                switch viewModel.viewState {
                case .initial:
                    Spacer()
                case .empty:
                    Spacer()
                    Text(SharedRes.strings().rollHistoryEmptyLabel.desc().localized())
                    Spacer()
                case.success(let rolls):
                    List(rolls, id: \.rollTime) { diceRoll in
                        Text(diceRoll.equation)
                    }
                // TODO: this triggers an alert modal
                case .error(let error):
                    Spacer()
                    Text(error)
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
                            await self.viewModel.clearDiceRolls()
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
                        
                            // begins observing updates to the roll history
                            await viewModel.activate()
                        
                    },
                    onCancel: {
                        //viewModel.clear()
                    }
                )
            }
        }
    }
}

struct RollHistoryView_Previews: PreviewProvider {
    static var previews: some View {
        RollHistoryView(viewModel: NativeRollHistoryViewModel())
    }
}
