package spring

import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import spring.Member
import java.sql.SQLException
import java.sql.Timestamp

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

    //IN JAVA public void insert(final Member member) {
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        jdbcTemplate.update(new PreparedStatementCreator() {
//            @Override
//            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
//                PreparedStatement pstmt = con.prepareStatement(
//                        "insert into MEMBER (LOGINID, PASSWORD, REGDATE) values(?, ?, ?)",
//                new String[] {"ID"} // 자동생성되는 키 칼럼 목록을 지정
//                );
//                pstmt.setString(1, member.getLoginId());
//                pstmt.setString(2, member.getPassword());
//                pstmt.setTimestamp(3, new Timestamp(member.getRegisterDate().getTime()));
//                return pstmt;
//            }
//        }, keyHolder); // preparedstatement 실행 후 생성된 키값을 키홀더에 저장
//
//        Number keyValue = keyHolder.getKey();
//        member.setId(keyValue.intValue());
//    }

    //auto_increment 키값을 구하기
    fun insert(member: Member) {
        val keyHolder: KeyHolder = GeneratedKeyHolder()
        jdbcTemplate!!.update({ con ->
            val pstmt = con.prepareStatement(
                "insert into MEMBER (LOGINID, PASSWORD, REGDATE) values(?, ?, ?)", arrayOf("ID")
            )
            pstmt.setString(1, member.loginId)
            pstmt.setString(2, member.password)
            pstmt.setTimestamp(3, Timestamp(member.registerDate.getTime()))
            pstmt
        }, keyHolder) // preparedstatement 실행 후 생성된 키값을 키홀더에 저장
        val keyValue = keyHolder.key
        member.setId(keyValue.toInt())
    }


    fun selectAll(): List<Member> {
        return jdbcTemplate!!.query("select * from MEMBER") { rs, rowNum -> Member(rs.getString("EMAIL"), rs.getString("PASSWORD")) }
    }

    fun count(): Int {
        //queryForObject 는 결과가 1행일 때 사용 (쿼리, 타입) 아니면 exception
        return jdbcTemplate.queryForObject("select count(*) from MEMBER", Int::class.java)
        //위와 달리 List<Member>가 아닌 RowMapper로 변환해주는 타입 (Member)을 리턴한다.
//        return jdbcTemplate.queryForObject("select * from MEMBER where ID = ?",
//            { rs, rowNum -> Member(rs.getString("EMAIL"), rs.getString("PASSWORD"))}, 100)
    }

    fun update(member: Member) {
        //update는 변경된 행의 개수를 리턴 insert update delete 수행
        jdbcTemplate.update("update MEMBER set NAME=?, PASSWORD=? where EMAIL=?"
        , member.name, member.password, member.loginId)
    }

//
//    companion object {
//        private var nextId = 0
//    }
}
