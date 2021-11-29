package tn.yassin.oneblood.Retrofit

class User()
{
    internal var name: String? = null
    internal var email: String? = null
    internal var password: String? = null
    internal var blood: String? = null
    internal var age: String? = null
    internal var weight: String? = null
    internal var adress: String? = null


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

    // Getter password
    fun getPassword(): String? {
        return password
    }
    // Setter password
    fun setPassword(newPassword: String?) {
        password = newPassword
    }

    // Getter blood
    fun getBlood(): String? {
        return blood
    }
    // Setter blood
    fun setBlood(newblood: String?) {
        blood = newblood
    }

    // Getter age
    fun getAge(): String? {
        return age
    }
    // Setter age
    fun setge(newAge: String?) {
        age = newAge
    }

    // Getter weight
    fun getWeight(): String? {
        return weight
    }
    // Setter weight
    fun setWeight(newWeight: String?) {
        weight = newWeight
    }
    // Getter adress
    fun getAdress(): String? {
        return adress
    }
    // Setter adress
    fun setAdress(newAdress: String?) {
        adress = newAdress
    }

}