package sample

class MemberDao {
    private val map: MutableMap<String?, Member> = HashMap()
    fun selectByLoginId(loginId: String?): Member? {
        return map[loginId]
    }

    fun insert(member: Member) {
        member.setId(++nextId)
        map[member.loginId] = member
    }

    fun update(member: Member) {
        map[member.loginId] = member
    }

    fun selectAll(): Collection<Member> {
        return map.values
    }

    companion object {
        private var nextId = 0
    }
}
