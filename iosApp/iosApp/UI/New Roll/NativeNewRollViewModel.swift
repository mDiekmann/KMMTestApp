//
//  NativeNewRollViewModel.swift
//  iosApp
//
//  Created by Michael Diekmann on 10/31/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import CommonKMM


@MainActor
class NativeNewRollViewModel: ObservableObject {
    private let repository = CommonKMM.DiceRollRepository()
    private let userSettings: UserSettings
    
    private let minDiceCount: Int32 = 1
    private let maxDiceCount: Int32 = 100
    let possibleDiceCounts: [Int32]
    let possibleDiceSides: [DiceSides] = [
        .d4,
        .d6,
        .d8,
        .d12,
        .d20,
        .d100
    ]
    
    @Published var diceCountInput: Int32
    @Published var diceSidesInput: DiceSides
    @Published var latestRollViewState: LatestRollState = LatestRollState.Initial()
    
    @Published var isLoading: Bool = false
    
    init(userSettings: UserSettings) {
        self.possibleDiceCounts = Array(minDiceCount...maxDiceCount)
        self.diceCountInput = minDiceCount
        self.diceSidesInput = DiceSides.d4
        self.userSettings = userSettings
    }
    
    func rollDice() async {
        self.isLoading = true
        
        let rollResult = try? await repository.rollDice(diceCount: diceCountInput, diceSides: diceSidesInput, roomSlug: userSettings.getRoomSlug())
        if let rollResult {
            latestRollViewState = LatestRollState.LastSuccessfulRoll(rollValue: "Last Roll (\(rollResult.equation)): \(rollResult.total)", rollDetails: "[\(rollResult.resultsArr.map{$0.stringValue}.joined(separator: ", "))]")
        }
        self.isLoading = false
    }
}
