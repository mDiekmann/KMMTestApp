//
//  UserSettingsView.swift
//  iosApp
//
//  Created by Michael Diekmann on 10/23/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct UserSettingsView: View {
    @State private var viewModel: NativeUserSettingsViewModel
    
    init(viewModel: NativeUserSettingsViewModel) {
        self.viewModel = viewModel
    }
    var body: some View {
        NavigationView {
            VStack(alignment: .center, spacing: 16) {
                Text("Room Slug:")
                    .font(.headline)
                
                TextField("Room Slug", text: $viewModel.roomSlug)
                    .textFieldStyle(RoundedBorderTextFieldStyle())
                    .padding(.horizontal, 16)
            }
        }
    }
}
