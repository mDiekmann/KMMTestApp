//
//  TabNavView.swift
//  iosApp
//
//  Created by Michael Diekmann on 9/1/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import CommonKMM

struct TabNavView: View {
    @StateObject var newRollViewModel = NativeNewRollViewModel(userSettings: iOSUserSettings(delegate: UserDefaults.standard))
    @StateObject var rollHistoryViewModel = NativeRollHistoryViewModel()
    @StateObject var userSettingsViewModel = NativeUserSettingsViewModel(userSettings: iOSUserSettings(delegate: UserDefaults.standard))
    
    var body: some View {
        TabView {
            NewDiceRollView(viewModel: newRollViewModel)
                .tabItem( {
                    Image(uiImage: SharedRes.images().dice_icon.toUIImage()!)
                        .resizable(capInsets: .init(top: 10.0, leading: 10.0, bottom: 10.0, trailing: 10.0))

                    Text(SharedRes.strings().newRollLabel.desc().localized())
                    
                })

            RollHistoryView(viewModel: rollHistoryViewModel)
                .tabItem( {
                    Image(uiImage: SharedRes.images().list_icon.toUIImage()!)
                        .renderingMode(.template)
                    Text(SharedRes.strings().rollHistoryLabel.desc().localized())
                    
                })
            
            UserSettingsView(viewModel: userSettingsViewModel)
                .tabItem( {
                    Image(uiImage: SharedRes.images().settings_icon.toUIImage()!)
                        .renderingMode(.template)
                    Text(SharedRes.strings().userSettingsLabel.desc().localized())
                    
                })
        }
    }
}

struct TabNavScreen_Previews: PreviewProvider {
    static var previews: some View {
        TabNavView()
    }
}
