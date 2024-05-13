package hellodb.jdbc.connection;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.assertj.core.api.Assertions.*;

@Slf4j
public class DBConnectionUtilTest {
    @Test
    void connection() {
        Connection connection = DBConnectionUtil.getConnection();
        assertThat(connection).isNotNull(); //null이 아니면 성공
    }

}
//h2는 org.jdbc connection을 제공해줌
//DB가 바뀌어도 connection 획득하는 방법을 바꿀 필요 X
//H2 전용 커넥션으로 JDBC 표준 커넥션 인터페이스인 java.sql.Connection인터페이스를 구현함

