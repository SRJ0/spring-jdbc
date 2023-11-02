package spring

import org.springframework.jdbc.core.JdbcTemplate

class temp {
    private val jdbcTemplate: JdbcTemplate? = null
    fun selectByEmail(email: String?): Member? {
        val results = jdbcTemplate!!.query(
            "select * from MEMBER where EMAIL = ?",
            { rs, rowNum ->
                Member(
                    rs.getString("EMAIL"),
                    rs.getString("PASSWORD")
                )
            }, email
        )
        return if (results.isEmpty()) null else results[0]
    }
}
