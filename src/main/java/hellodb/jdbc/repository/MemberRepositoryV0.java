package hellodb.jdbc.repository;

import hellodb.jdbc.connection.DBConnectionUtil;
import hellodb.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;

/*
 * JDBC - DriverManager 사용
 * */
@Slf4j
public class MemberRepositoryV0 {
    public Member save(Member member) throws SQLException {
        ///데이터를 등록하는 insert sql
        String sql = "insert into member(member_id, money) values(?, ?)";

        //연결용
        Connection con = null;
        //db에 쿼리 날림
        PreparedStatement pstmt = null;


        //sql을 넘기면 결과로 preparedStatement를 줌
        try {
            //커넥션 가져옴 (ctrl+alt+M으로 뺌)
            con = getConnection();
            pstmt = con.prepareStatement(sql); //(ctrl+alt+v)
            //values 안에 값이 들어각게 됨
            pstmt.setString(1, member.getMemberId());
            pstmt.setInt(2, member.getMoney());
            pstmt.executeUpdate(); //쿼리가 실제 db에 실행
            return member;
        } catch (SQLException e) {
            //exception 터질 때 로그
            log.error("db error", e);
            throw e;

        } finally {
//            pstmt.close();//Exception이 터지면 밖으로 나가버려서 con.close호출안딤
//            con.close(); //커넥션을 닫아주지 않으면 연결이 끊어지지 않음
            close(con, pstmt, null);
        }


    }

    private void close(Connection con, Statement stmt, ResultSet rs) {

        //쿼리를 종료하면 사용한 리소스들을 모두 닫음, 정리해야함

        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                log.info("error", e);
            }

        }

        //안정성을 위해
        if (stmt != null) {
            try {
                stmt.close();//SQL Exception을 날리는 문제
            } catch (SQLException e) {
                log.info("error", e);
            }

        }

        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                log.info("error", e);
            }

        }
    }

    private static Connection getConnection() {
        return DBConnectionUtil.getConnection();
    }

}

//ctrl+shif+t 테스트 생성
