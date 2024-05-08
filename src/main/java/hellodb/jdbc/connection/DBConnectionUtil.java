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
