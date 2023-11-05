//
//  NativeRollHistoryViewModel.swift
//  iosApp
//
//  Created by Michael Diekmann on 11/1/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import CommonKMM

@MainActor
class NativeRollHistoryViewModel: ObservableObject {
    private let repository = CommonKMM.DiceRollRepository()
    @Published var viewState: NativeRollHistoryViewState
    
    init() {
        self.viewState = .initial
        repository.getDiceRolls()
    }
    
    @MainActor
    func activate() async {
        for await rolls in repository.getDiceRolls() {
            if rolls.isEmpty {
                viewState = .empty
            } else {
                viewState = .success(rolls: rolls)
            }
        }
    }
    
    @MainActor
    func clearDiceRolls() async {
        do {
            try await repository.clearLocalCache()
        } catch {
            self.viewState = .error(error: error.localizedDescription)
        }
    }
}

enum NativeRollHistoryViewState {
    case initial
    case empty
    case success(rolls: [RollInfoModel])
    case error(error: String)
}
