//
//  LoadingView.swift
//  iosApp
//
//  Created by Michael Diekmann on 10/27/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct DIActivity: ViewModifier {
    enum Style {
        case regular
        case small
        case mini
    }
    
    private enum Constants {
        static let blurRadius: CGFloat = 2.0
        static let backgroundOpacity: Double = 0.8
        static let width: CGFloat = UIScreen.main.bounds.width * 0.45
        static let smallWidth: CGFloat = UIScreen.main.bounds.width * 0.35
        static let miniWidth: CGFloat = 95.0
        static let aspect: CGFloat = 0.46875
    }
    
    private let isVisible: Bool
    private let width: CGFloat


    init(isVisible: Bool, style: Style = .regular) {
        self.isVisible = isVisible
        switch style {
        case .regular:
            self.width = Constants.width
        case .small:
            self.width = Constants.smallWidth
        case .mini:
            self.width = Constants.miniWidth
        }
    }

    func body(content: Content) -> some View {
        content
            .blur(radius: isVisible ? Constants.blurRadius : .zero, opaque: false)
            .overlay(
                ZStack(content: {
                    Rectangle()
                        .foregroundColor(.white.opacity(Constants.backgroundOpacity))
                    ProgressView()
                    //LottieView(name: "activity", loopMode: .loop, startProgress: isVisible ? .zero : nil)
                        .frame(width: width, height: width * Constants.aspect)
                }).ignoresSafeArea()
                .opacity(isVisible ? 1.0 : .zero)
                .animation(.easeIn(duration: 0.15), value: isVisible)
            )
    }
}

extension View {
    func activity(isVisible: Bool, style: DIActivity.Style = DIActivity.Style.regular) -> some View {
        return self.modifier(DIActivity(isVisible: isVisible, style: style))
    }
}
