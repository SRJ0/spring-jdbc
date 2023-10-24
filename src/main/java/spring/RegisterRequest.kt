package sample

class RegisterRequest {
    @JvmField
    var loginId: String? = null
    @JvmField
    var password: String? = null
    @JvmField
    var confirmPassword: String? = null
    val isPasswordEqualToConfirmPassword: Boolean
        get() = password == confirmPassword
}
