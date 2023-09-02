//
//  CreateNewRollView.swift
//  iosApp
//
//  Created by Michael Diekmann on 9/1/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import CommonKMM

struct CreateNewRollView: View {
    @ObservedObject var state: CommonKMM.NewRollViewModel

    var body: some View {
        Text(/*@START_MENU_TOKEN@*/"Hello, World!"/*@END_MENU_TOKEN@*/)
    }
}

struct CreateNewRollView_Previews: PreviewProvider {
    static var previews: some View {
        CreateNewRollView()
    }
}
