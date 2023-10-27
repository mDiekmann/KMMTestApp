package com.mtd.kmmtestapp.viewModels

expect abstract class ViewModel() {
    protected open fun onCleared()
}