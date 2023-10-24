package sample

import org.springframework.beans.factory.annotation.Autowired

//Autowired의 required 속성을 false로 설정할 경우 객체가 존재하지 않더라도 exception이 발생하지 않는다.
//한편, 이 경우에는 빈 생성자가 있어야 MemberRegisterService 객체를 생성할 때 exception이 발생하는 것을 막을 수 있다.
class MemberRegisterService() {
    private lateinit var memberDao: MemberDao
    @Autowired(required = false)
    constructor(memberDao: MemberDao) : this() {
        this.memberDao = memberDao
    }

    fun regist(req: RegisterRequest) {
        val member = memberDao.selectByLoginId(req.loginId)
        if (member != null) {
            throw AlreadyExistingMemberException("dup loginid" + req.loginId)
        }
        val newMember = Member(
            req.loginId, req.password
        )
        memberDao.insert(newMember)
    }
}
