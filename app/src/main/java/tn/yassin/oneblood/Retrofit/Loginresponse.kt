package tn.yassin.oneblood.Retrofit

class Loginresponse {
    internal var token: String? = null
    // Getter name
    fun getToken(): String? {
        return token
    }
    // Setter name
    fun setToken(newName: String?) {
        token = newName
    }
}