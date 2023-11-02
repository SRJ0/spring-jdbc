package spring

class Member(val loginId: String?, val password: String?) {
    private var id = 0
    var name = "hong"
    var registerDate = ""

    fun setId(id: Int) {
        this.id = id
    }

    fun changePassword(oldPW: String, newPW: String?) {
        if (password != oldPW) {
            throw IdPasswordNotMatchingException("check your account info!")
        }
    }

}
