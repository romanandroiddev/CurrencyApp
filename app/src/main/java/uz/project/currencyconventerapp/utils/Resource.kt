package uz.project.currencyconventerapp.utils

sealed class Resource<T>(val data: T?, message: String?) {
    class Succes<T>(data: T): Resource<T>(data,null)
    class Error<T>(message: String) : Resource<T>(null,null)
}