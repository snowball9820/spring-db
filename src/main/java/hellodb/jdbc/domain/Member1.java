package hellodb.jdbc.domain;

import lombok.Data;

//H2 Member1
@Data
public class Member1 {
    private String memberId;
    private int money;

    public Member1() {

    }

    public Member1(String memberId, int money) {
        this.memberId = memberId;
        this.money = money;
    }

}
