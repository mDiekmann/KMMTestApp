//
//  NativeUserSettingsViewModel.swift
//  iosApp
//
//  Created by Michael Diekmann on 11/1/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import CommonKMM

@MainActor
class NativeUserSettingsViewModel: ObservableObject {
    @Published var roomSlug: String {
        didSet {
            if roomSlug.isEmpty {
                userSettings.setRoomSlug(roomSlug: nil)
            } else {
                userSettings.setRoomSlug(roomSlug: roomSlug)
            }
        }
    }
    private let userSettings: UserSettings
    
    init(userSettings: UserSettings) {
        self.userSettings = userSettings
        roomSlug = userSettings.getRoomSlug() ?? ""
    }
}
