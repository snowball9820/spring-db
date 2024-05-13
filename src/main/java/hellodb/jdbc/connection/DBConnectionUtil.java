package hellodb.jdbc.connection;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static hellodb.jdbc.connection.ConnectionConst.*;

@Slf4j
public class DBConnectionUtil {
    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            log.info("get connecion={}, class={}",connection,connection.getClass()); //getClass 어떤 클래스인지 클래스 정보 출력
            return connection;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

}

//여기서 DriverManager는 라이브러리에 등록된 DB드라이버를 관리하고 커넥션을 획득하는 기능을 제공함
//등록된 드라이버 목록을 자동으로 인식하고 드라이버들에게 순서대로 다음 정보를 넘겨 커넥션을 획득할 수 있는지 확인
//예를 들어 jdbc:h2로 시작하면 h2 db에 접근하기 위한 규칙임
//MySQL 드라이버가 먼저 실행되면 본인이 처리할 수 없다는 결과를 반환후 그 다음 드라이버에게 순서가 넘어감

