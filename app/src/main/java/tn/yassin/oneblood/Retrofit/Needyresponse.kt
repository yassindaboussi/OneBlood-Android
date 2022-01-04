package tn.yassin.oneblood.Retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Needyresponse {

    @SerializedName("_id")
    @Expose
    internal var id: String? = null

    @SerializedName("nom")
    @Expose
    internal var nom: String? = null

    @SerializedName("location")
    @Expose
    internal var location: String? = null

    @SerializedName("blood")
    @Expose
    internal var blood: String? = null

    @SerializedName("phone")
    @Expose
    internal var phone: Int? = null

    @SerializedName("__v")
    @Expose
    internal var v: Int? = null

    @SerializedName("datepost")
    @Expose
    internal var datepost: String? = null

    @SerializedName("postedby")
    @Expose
    internal var postedby: String? = null

    @SerializedName("situation")
    @Expose
    internal var situation: String? = null

    ///////////////////////////////////////////////////////:
    @SerializedName("avatar")
    @Expose
    internal var avatar: String? = null
    fun getAvatar(): String? {
        return avatar
    }
    fun setAvatar(avatar: String?) {
        this.avatar = avatar
    }
    /////////////////////////////////////////////////////////

    fun getSituation(): String? {
        return situation
    }

    fun setSituation(situation: String?) {
        this.situation = situation
    }

    fun getPostedby(): String? {
        return postedby
    }

    fun setPostedby(postedby: String?) {
        this.postedby = postedby
    }

    fun getDatepost(): String? {
        return datepost
    }

    fun setDatepost(datepost: String?) {
        this.datepost = datepost
    }

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getNom(): String? {
        return nom
    }

    fun setNom(nom: String?) {
        this.nom = nom
    }

    fun getLocation(): String? {
        return location
    }

    fun setLocation(location: String?) {
        this.location = location
    }

    fun getBlood(): String? {
        return blood
    }

    fun setBlood(blood: String?) {
        this.blood = blood
    }

    fun getPhone(): Int? {
        return phone
    }

    fun setPhone(phone: Int?) {
        this.phone = phone
    }

    fun getV(): Int? {
        return v
    }

    fun setV(v: Int?) {
        this.v = v
    }


}