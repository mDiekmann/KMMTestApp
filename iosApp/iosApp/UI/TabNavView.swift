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
    var body: some View {
        TabView {
            NewDiceRollView(viewModel: NewRollViewModel())
                .tabItem( {
                    Image(uiImage: SharedRes.images().dice_icon.toUIImage()!)
                        .resizable(capInsets: .init(top: 10.0, leading: 10.0, bottom: 10.0, trailing: 10.0))

                    Text(SharedRes.strings().newRollLabel.desc().localized())
                    
                })

            RollHistoryView(viewModel: RollHistoryViewModel())
                .tabItem( {
                    Image(uiImage: SharedRes.images().list_icon.toUIImage()!)
                        .renderingMode(.template)
                    Text(SharedRes.strings().rollHistoryLabel.desc().localized())
                    
                })
        }
    }
}

struct TabNavScreen_Previews: PreviewProvider {
    static var previews: some View {
        TabNavView()
    }
}
