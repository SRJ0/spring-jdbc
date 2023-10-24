package sample

import javax.annotation.Resource


// resource 어노테이션은 빈의 이름으로 객체를 검색한다.
// 필드나 메서드에만 적용할 수 있다.
// name 속성이 없으면 타입으로 객체를 찾는다. 다음은 autowired와 같다
// 이름을 선호하지 않는 이유 http://javacan.tistory.com/entry/Reason-Why-I-dont-use-name-based-Autowiring
class MemberListPrinter(@Resource(name="memberDao") private val memberDao: MemberDao, @Resource(name="memberPrinter") private val printer: MemberPrinter) {
    fun printAll() {
        val members = memberDao.selectAll()
        for (m in members) {
            printer.print(m)
        }
    }
}
