package com.west.branch.payload.request

class View {
    //Enclosing type to define User views
    interface AdventureView {
        //External View for User
        interface External
        interface Internal : External
    }
}