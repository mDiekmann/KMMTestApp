//
//  LatestRollView.swift
//  iosApp
//
//  Created by Michael Diekmann on 9/6/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import KMMViewModelSwiftUI
import CommonKMM

struct LatestRollView: View {
    private var viewState: LatestRollState
    
    init(viewState: LatestRollState) {
        self.viewState = viewState
    }
    
    var body: some View {
        switch viewState {
        case is LatestRollState.Initial:
            InitialRollView()
        case let latestRollInfo as LatestRollState.LastSuccessfulRoll:
            DiceRollResultView(rollValue: latestRollInfo.rollValue, rollDetails:  latestRollInfo.rollDetails)
        default:
            InitialRollView()
        }
    }
}

struct InitialRollView: View {
    var body: some View {
        Text("Press above to roll")
    }
}

struct DiceRollResultView: View {
    private var rollValue: String
    private var rollDetails: String
    
    init(rollValue: String, rollDetails: String) {
        self.rollValue = rollValue
        self.rollDetails = rollDetails
    }
    var body: some View {
        VStack(spacing: 20.0) {
            Text(rollValue)
            Text(rollDetails)
        }
    }
}

struct LatestRollView_Previews: PreviewProvider {
    static var previews: some View {
        LatestRollView(viewState: LatestRollState.LastSuccessfulRoll(
            rollValue: "Last Roll (10d12}): 61",
            rollDetails: "[5, 9, 12, 4, 7, 2, 4, 9, 7, 2]"))
    }
}
