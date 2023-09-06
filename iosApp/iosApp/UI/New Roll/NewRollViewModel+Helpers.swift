//
//  NewRollViewModel+Helpers.swift
//  iosApp
//
//  Created by Michael Diekmann on 9/5/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import CommonKMM

extension NewRollViewModel {
    func possibleCountArrayToInt32() -> Array<Int32> {
        return self.possibleDiceCounts.map{ Int32(truncating: $0) }
    }
}
