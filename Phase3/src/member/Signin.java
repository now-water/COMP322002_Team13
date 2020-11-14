package member;

/*
 * 1. 로그인 콘솔 -> 1 - 로그인, 2 - 관리자 로그인 ,3 - 회원가입
 *  1.1) 정보 수정, 비밀번호 수정 ( ID수정 불가능 ), 회원 탈퇴
 *  1.2) 영상물 등록 및 정보 수정 가능
 *  1.3) signUp class
 *
 * 2. 영상물 콘솔 -> 1. 조회, 2. 검색,
 *  2.1)
 *  2.2) 제목검색, 조건검색, 영상물 정보 확인, 영상물 평가
 *       -> 평가한 영상물은 검색대상에서 제외
 * */


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Scanner;

public class Signin {
    private static Account acc;

    public Signin(Account acc){
        this.acc = acc;
    }
    public boolean canLogin(Statement st){
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Id와 Pw를 입력하세요 --");
        String id = scanner.next();
        String pw = scanner.next();
        //를 입력받아서 일단 DB에 id정보가 있는지 싹다 조회하고 있으면 해당 id에 관한 행 정보를 Account instance를 생성하여 저장 후 true반환
        String isId = "SELECT * " +
                "FROM \"knuMovie\".\"ACCOUNT\"";
        try {
            ResultSet rs = st.executeQuery(isId);

            while (rs.next()) {
                if (rs.getString(1).equals(id)) {
//                account instance에 정보 삽입

                    acc.setAcc_id(id);
                    acc.setAcc_pw(pw);
                    acc.setUser_name(rs.getString(3));
                    acc.setPhone_num(rs.getString(4));
                    acc.setBirth_date(rs.getString(5));
                    acc.setAge(rs.getInt(6));
                    acc.setGender(rs.getString(7));
                    acc.setAddress(rs.getString(8));
                    acc.setJob(rs.getString(9));
                    acc.setMem_type(rs.getString(10));
                    acc.setManager(rs.getBoolean(11));

                    if(acc.isManager())
                        System.out.println("manager Login");
                    else
                        System.out.println("member Login");

                    return true;
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        System.out.println("객체가 없습니다");
        return false;
    }


    public void printInfo() {
        LinkedList list = new LinkedList();

        list.add(acc.getAcc_id());
        list.add(acc.getAcc_pw());
        list.add(acc.getUser_name());
        list.add(acc.getPhone_num());
        list.add(acc.getBirth_date());
        list.add(acc.getAcc_pw());
        list.add(acc.getGender());
        list.add(acc.getAddress());
        list.add(acc.getJob());
        list.add(acc.getMem_type());
        list.add(acc.isManager());

        System.out.println(list.toString());
    }
}
