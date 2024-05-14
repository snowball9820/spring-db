package hellodb.jdbc.repository;

import hellodb.jdbc.connection.DBConnectionUtil;
import hellodb.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.NoSuchElementException;

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
            //values 안에 값이 들어가게 됨
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

    //조회
    public Member findById(String memberId) throws SQLException {
        //member_id 조회
        String sql = "select * from member where member_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            //connection을 통해 prepareStatement를 얻어야함
            pstmt = con.prepareStatement(sql);
            //prepareStatement에 파라미터 넘겨줌
            pstmt.setString(1,memberId);

            //select query - execute update는 데이터를 변경할 때 쓰는거, executeQuery는 select할 때
            rs = pstmt.executeQuery();

            //내부에 커서가 있는데 한번은 rs.next() 호출해야 함
            if (rs.next()) {
                //member 객체를 만들어서 넣어줌
                Member member = new Member();
                member.setMemberId(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));
                return member;

            } else {
                //데이터가 없을 때
                throw new NoSuchElementException("member not found memberId="+memberId);
            }

        } catch (SQLException e) {
            log.error("db error",e);
            throw e;
        }finally {
            close(con, pstmt, rs);

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

//ctrl+shift+t 테스트 생성
