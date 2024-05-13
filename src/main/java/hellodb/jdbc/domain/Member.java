package hellodb.jdbc.domain;

import lombok.Data;

//H2 Member1
@Data
public class Member {
    private String memberId;
    private int money;

    public Member() {

    }

    public Member(String memberId, int money) {
        this.memberId = memberId;
        this.money = money;
    }

}
