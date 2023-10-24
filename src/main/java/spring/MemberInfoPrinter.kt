package sample

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier

class MemberInfoPrinter {
    // 1. 타입을 이용해서 의존 대상 객체를 검색한다. 더 정확하게는 해당 타입에 할당할 수 있는 빈 객체를 찾아서 선택한다.
    // MemberDao를 상속한 CacheMemberDao가 있을 때,
    // MemberDao memberDao = new CacheMemberDao(); 와 같이 할당이 가능하므로 cacheMemberDao 빈 객체가 의존 주입 대상이 될 수 있다.
    private var memDao: MemberDao? = null
    private var printer: MemberPrinter? = null
    @Autowired
    fun setMemberDao(memberDao: MemberDao?) {
        memDao = memberDao
    }
    //파라미터가 둘 이상일 때에도 Autowired 적용이 가능
    //Qualifier는 각 파라미터에 직접 지정하면 된다.
//    @Autowired
//    fun injectDependecny(memberDao: MemberDao, @Qualifier("p2") printer: MemberPrinter) {
//        this.memDao = memberDao
//        this.printer = printer
//    }

    // Autowired 사용 시 할당할 수 있는 객체가 두 개 이상 존재하는 경우 Exception 발생
    // NoUniqueBeanDefinitionException: No qualifying bean of type 'sample.MemberPrinter' available: expected single matching bean but found 2
    // 2. 이는 Qualifier로 해결 가능
    // Qualifier에 지정한 객체를 찾을 수 없는 경우
    // NoSuchBeanDefinitionException: No qualifying bean of type 'sample.MemberPrinter' available: expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: {}
    // 3. 단, 필드 또는 프로퍼티 이름과 매칭되는 객체가 있으면 그 놈을 선택한다.
//    @Autowired
//    @Qualifier("p2")
    // <bean id="printer" class="sample.MemberPrinter"/> 가 있으면 setPrinter()와 매칭되므로 문제없음
    @Autowired
    fun setPrinter(printer: MemberPrinter?) {
        this.printer = printer
    }

    //정리하면 1 타입이 같은 객체를 검색하고 한 개면 사용한다. 2 Qualifier가 명시되어 있으면 명시된 것과 같은 값을 가져야한다. 둘 이상 존재하면 Qualifier로 지정한 객체를 찾고 없다면 3 이름이 같은 객체를 찾는다 4 못 찾겠으면 익셉션
    fun printMemberInfo(id: String?) {
        val member = memDao!!.selectByLoginId(id)
        if (member == null) {
            println("nodata")
            return
        }
        printer!!.print(member)
        println()
    }
}
