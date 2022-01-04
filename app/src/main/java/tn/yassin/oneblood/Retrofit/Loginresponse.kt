package tn.yassin.oneblood.Retrofit

class Loginresponse {
    internal var token: String? = null
    internal var id: String? = null
    internal var name: String? = null
    internal var email: String? = null
    internal var password: String? = null
    internal var blood: String? = null
    internal var age: String? = null
    internal var weight: String? = null
    internal var adress: String? = null
    internal var phone: String? = null
    internal var usertype: String? = null
    internal var avatar: String? = null
    // Getter token
    fun getToken(): String? {
        return token
    }
    // Setter token
    fun setToken(newName: String?) {
        token = newName
    }
    // Getter ID
    fun getID(): String? {
        return id
    }
    // Setter name
    fun setID(newID: String?) {
        id = newID
    }
    // Getter name
    fun getName(): String? {
        return name
    }
    // Setter name
    fun setName(newName: String?) {
        name = newName
    }
    // Getter email
    fun getEmail(): String? {
        return email
    }
    // Setter email
    fun setEmail(newEmail: String?) {
        email = newEmail
    }
    // Getter Password
    fun getPassword(): String? {
        return password
    }
    // Setter Password
    fun setPassword(newPassword: String?) {
        password = newPassword
    }
    // Getter Blood
    fun getBlood(): String? {
        return blood
    }
    // Setter Blood
    fun setBlood(newBlood: String?) {
        blood = newBlood
    }
    // Getter Age
    fun getAge(): String? {
        return age
    }
    // Setter Age
    fun setAge(newAge: String?) {
        age = newAge
    }
    // Getter Weight
    fun getWeight(): String? {
        return weight
    }
    // Setter Weight
    fun setWeight(newWeight: String?) {
        weight = newWeight
    }
    // Getter Adress
    fun getAdresse(): String? {
        return adress
    }
    // Setter Adress
    fun setAdresse(newAdress: String?) {
        adress = newAdress
    }
    // Getter Phone
    fun getPhone(): String? {
        return phone
    }
    // Setter Phone
    fun setPhone(newPhone: String?) {
        phone = newPhone
    }
    // Getter UserType
    fun getUserType(): String? {
        return usertype
    }
    // Setter UserType
    fun setUserType(newUserType: String?) {
        usertype = newUserType
    }
    // Getter UserType
    fun getAvatar(): String? {
        return avatar
    }
    // Setter UserType
    fun setAvatar(newAvatar: String?) {
        avatar = newAvatar
    }

}