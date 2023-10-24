//
//  UserSettingsViewModel+iOS.swift
//  iosApp
//
//  Created by Michael Diekmann on 10/24/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import CommonKMM
import Combine

class UserSettingsViewModel: CommonKMM.UserSettingsViewModel {
    @Published var roomSlugText: String = "" {
        didSet {
            if roomSlugText.isEmpty {
                setRoomSlug(roomSlug: nil)
            } else {
                setRoomSlug(roomSlug: roomSlugText)
            }
        }
    }
    
    override init() {
        super.init()
        roomSlugText = self.roomSlug ?? ""
    }
}
