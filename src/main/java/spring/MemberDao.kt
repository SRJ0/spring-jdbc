package spring

import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper
import spring.Member
import java.sql.SQLException

class MemberDao() {
    private lateinit var jdbcTemplate: JdbcTemplate;

    constructor(dataSource: DataSource) : this() {
        jdbcTemplate = JdbcTemplate(dataSource)
    }

    fun selectByEmail(email: String?): Member? { // ?: nullable  !!: not nullable
        val results = jdbcTemplate.query(
            "select * from MEMBER where EMAIL = ?",
// RowMapper는 rs으로부터 한 행을 읽어와 객체로 만들어준다.
//  in Java   new RowMapper<Member>() {
//                @Override
//                public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
//                    ...
//                }
//            }

            { rs, rowNum ->
                Member(
                    rs.getString("EMAIL"),
                    rs.getString("PASSWORD")
                )
            }, email
        )
        return if (results.isEmpty()) null else results[0]
    }
//
//    fun insert(member: Member) {
//        member.setId(++nextId)
//        map[member.loginId] = member
//    }
//
//    fun update(member: Member) {
//        map[member.loginId] = member
//    }
//
//    fun selectAll(): Collection<Member> {
//        return map.values
//    }
//
//    companion object {
//        private var nextId = 0
//    }
}
