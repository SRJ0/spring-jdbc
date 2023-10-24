package sample

class MemberPrinter {
    fun print(member: Member?) {
        if (member != null) {
            System.out.printf("회원아이디:%s/ 비번:%s \n", member.loginId, (member.password?.substring(0, 2) ?: "error") + "**")
        }
    }
}
