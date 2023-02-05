package com.seoleo.fintechlab2023.ui.model

sealed class Message {
    object NoInternet : Message()
    object TokenNotFound : Message()
    object LargeRequestLimit : Message()
    object ManyRequests : Message()
    object Success : Message()
}
