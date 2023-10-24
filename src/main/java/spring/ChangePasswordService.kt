package sample

class ChangePasswordService(private val memberDao: MemberDao) {
    fun changePassword(loginId: String, oldPW: String, newPW: String?) {
        val member = memberDao.selectByLoginId(loginId) ?: throw MemberNotFoundException("NOT exist $loginId")
        member.changePassword(oldPW, newPW)
        memberDao.update(member)
    }
}
