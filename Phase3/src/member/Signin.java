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
    static Scanner scanner = new Scanner(System.in);

    public Signin(Account acc) {
        this.acc = acc;
    }

    public boolean canLogin(Statement st) {

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

                    if (acc.isManager())
                        System.out.println("manager Login");
                    else
                        System.out.println("member Login");

                    return true;
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return false;
    }

    public void modifyPasswd(Statement st) throws SQLException {
        System.out.print("수정할 비밀번호를 입력하세요 : ");
        String passwd = scanner.next();

        String query = "SELECT * " +
                "FROM \"knuMovie\".\"ACCOUNT\" " +
                "WHERE acc_id = " + "\'" + acc.getAcc_id() + "\'";

        String update = "UPDATE \"knuMovie\".\"ACCOUNT\" " +
                "SET acc_pw = " + "\'" + passwd + "\'" +
                "WHERE acc_pw = " + "\'" + acc.getAcc_pw() + "\'";

        st.executeUpdate(update);     //얘가 왜 안먹지?? 쿼리를 날려야 DB에도 저장이 될 거 아니야!
        ResultSet rs = st.executeQuery(query);      //DB에 저장된 내역을 받아서 acc객체에도 저장..

        if (rs.next())
            acc.setAcc_pw(rs.getString(2));


    }

    public void modifyMemberInfo(Statement st) throws SQLException {
        while (true) {
            System.out.println("무엇을 수정하시겠습니까?");
            System.out.println("1. user_name");
            System.out.println("2. phone_num");
            System.out.println("3. birth_date");
            System.out.println("4. age");
            System.out.println("5. gender");
            System.out.println("6. address");
            System.out.println("7. job");
            System.out.println("8. mem_type");
            System.out.println("9. 뒤로가기");

            String query = "SELECT * " +
                    "FROM \"knuMovie\".\"ACCOUNT\" " +
                    "WHERE acc_id = " + "\'" + acc.getAcc_id() + "\'";

            String update = "";
            switch (scanner.nextInt()) {
                case 1:
                    System.out.println("현재 user_name : " + acc.getUser_name());
                    System.out.print("변경할 user_name : ");
                    String user_name = scanner.next();

                    update = "UPDATE \"knuMovie\".\"ACCOUNT\" " +
                            "SET user_name = " + "\'" + user_name + "\' " +
                            "WHERE user_name = " + "\'" + acc.getUser_name() + "\'";

                    break;
                case 2:
                    System.out.println("현재 phone_num : " + acc.getPhone_num());
                    System.out.print("변경할 phone_num : ");
                    String phone_num = scanner.next();

                    update = "UPDATE \"knuMovie\".\"ACCOUNT\" " +
                            "SET phone_num = " + "\'" + phone_num + "\' " +
                            "WHERE phone_num = " + "\'" + acc.getPhone_num() + "\'";

                    break;
                case 3:
                    System.out.println("현재 birth_date : " + acc.getBirth_date());
                    System.out.print("변경할 birth_date : ");
                    String birth_date = scanner.next();

                    update = "UPDATE \"knuMovie\".\"ACCOUNT\" " +
                            "SET birth_date = " + "\'" + birth_date + "\' " +
                            "WHERE birth_date = " + "\'" + acc.getBirth_date() + "\'";

                    break;
                case 4:
                    System.out.println("현재 age : " + acc.getAge());
                    System.out.print("변경할 age : ");
                    int age = scanner.nextInt();

                    update = "UPDATE \"knuMovie\".\"ACCOUNT\" " +
                            "SET age = " + age + " " +
                            "WHERE age = " + acc.getAge();

                    break;
                case 5:
                    System.out.println("현재 gender : " + acc.getGender());
                    System.out.print("변경할 gender (M/F) : ");
                    String gender = scanner.next();

                    String sex = gender.equals("M") ? "man" : "woman";
                    update = "UPDATE \"knuMovie\".\"ACCOUNT\" " +
                            "SET gender = " + "\'" + sex + "\' " +
                            "WHERE gender = " + "\'" + acc.getGender() + "\'";

                    break;
                case 6:
                    System.out.println("현재 address : " + acc.getAddress());
                    System.out.print("변경할 address : ");
                    String address = scanner.next();

                    update = "UPDATE \"knuMovie\".\"ACCOUNT\" " +
                            "SET address = " + "\'" + address + "\' " +
                            "WHERE address = " + "\'" + acc.getAddress() + "\'";

                    break;
                case 7:
                    System.out.println("현재 job : " + acc.getJob());
                    System.out.print("변경할 job : ");
                    String job = scanner.next();

                    update = "UPDATE \"knuMovie\".\"ACCOUNT\" " +
                            "SET job = " + "\'" + job + "\' " +
                            "WHERE job = " + "\'" + acc.getJob() + "\'";

                    break;
                case 8:
                    System.out.println("현재 mem_type : " + acc.getMem_type());
                    System.out.print("변경할 mem_type (basic | premium | prime) : ");
                    String mem_type = scanner.next();

                    update = "UPDATE \"knuMovie\".\"ACCOUNT\" " +
                            "SET mem_type = " + "\'" + mem_type + "\' " +
                            "WHERE mem_type = " + "\'" + acc.getMem_type() + "\'";

                    break;

                case 9:
                    return;

            }

            st.executeUpdate(update);     //얘가 왜 안먹지?? 쿼리를 날려야 DB에도 저장이 될 거 아니야!
            ResultSet rs = st.executeQuery(query);      //DB에 저장된 내역을 받아서 acc객체에도 저장..

            if (rs.next()) {
                acc.setUser_name(rs.getString(3));
                acc.setPhone_num(rs.getString(4));
                acc.setBirth_date(rs.getString(5));
                acc.setAge(rs.getInt(6));
                acc.setGender(rs.getString(7));
                acc.setAddress(rs.getString(8));
                acc.setJob(rs.getString(9));
                acc.setMem_type(rs.getString(10));
                acc.setManager(rs.getBoolean(11));
            }

        }
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

