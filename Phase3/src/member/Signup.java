package member;

import java.sql.*;
import java.util.Scanner;

public class Signup {
    private static Account acc;

    public Signup(Account acc) {
        this.acc = acc;
    }

    static Scanner sc = new Scanner(System.in);

    public static boolean duplicate(String acc_id,Statement st) throws SQLException {
        String query = "SELECT acc_id FROM \"knuMovie\".\"ACCOUNT\"";
        ResultSet rs = st.executeQuery(query);
        while(rs.next())
        {
            if(rs.getString(1).equals(acc_id))
                return true;
        }
        return false;
    }

    public static void dbUpdate(Statement st) throws SQLException {
        String query = "INSERT INTO \"knuMovie\".\"ACCOUNT\"" +
                "VALUES( \'" + acc.getAcc_id() + "\', \'" + acc.getAcc_pw() + "\', \'" + acc.getUser_name() + "\', \'" + acc.getPhone_num() + "\', TO_DATE(\'" +
                acc.getBirth_date() + "\', 'YYYY-MM-DD'), " + acc.getAge() + ", \'" + acc.getGender() + "\', \'" + acc.getAddress() + "\', \'" + acc.getJob() + "\', \'" +
                acc.getMem_type() + "\', " + acc.isManager() +")";
        int res = st.executeUpdate(query);
        if(res == 0)
        {
            System.out.println("회원가입이 성공적으로 완료되었습니다.");
            return;
        }
    }

    public void signUp(Statement st) throws SQLException {
        while(true)
        {
            System.out.println("*** 관리자 계정을 등록하시겠습니까? (Y / N) ***");
            acc.setManager((sc.next().equals("Y")? true: false));

            System.out.println("*** 회원가입할 아이디를 입력해주세요. ***");
            acc.setAcc_id(sc.next());
            if(duplicate(acc.getAcc_id(),st))
            {
                System.out.println("이미 존재하는 아이디입니다.");
                System.out.println("다른 아이디를 사용해주세요");
                continue;
            }
            System.out.println("*** 비밀번호를 입력해주세요. ***");
            acc.setAcc_pw(sc.next());

            System.out.println("*** 이름을 입력해주세요. ***");
            acc.setUser_name(sc.next());

            System.out.println("*** 휴대폰 번호를 입력해주세요.(010-XXXX-XXXX) ***");
            String phone = sc.next();
            phone = phone.replaceAll("-", " ");
//            System.out.println(phone);
            acc.setPhone_num(phone);

            System.out.println("*** 생년월일을 입력해주세요.(YYYY-MM-DD) ***");
            acc.setBirth_date(sc.next());

            System.out.println("*** 나이를 입력해주세요. ***");
            acc.setAge(sc.nextInt());

            System.out.println("*** 성별을 입력해주세요.(M / F) ***");
            acc.setGender(sc.next().equals("M") ? "man" : "woman");

            System.out.println("*** 주소를 입력해주세요. ***");
            acc.setAddress(sc.next());

            System.out.println("*** 직업을 입력해주세요. ***");
            acc.setJob(sc.next());

            if(!acc.isManager()) {
                System.out.println("*** 멤버등급 입력해주세요. (basic / premium / prime) ***");
                acc.setMem_type(sc.next());
            } else{
                // manager 의 경우 등급을 prime으로 준다.
                acc.setMem_type("prime");
            }
            break;
        }
        dbUpdate(st);
    }
}
/*
N
aaab
aaab
aaab
010-1234-5678
1997-02-25
24
M
daegu
student
basic


 */
