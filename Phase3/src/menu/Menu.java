package menu;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import member.*;

public class Menu {

    private Account acc;
    public static Scanner sc = new Scanner(System.in);
    public Menu(Account acc){
        this.acc = acc;
    }

    public static void register(Statement st) throws SQLException {
        Signup up = new Signup(Account.getInstance());
        up.signUp(st);
    }

    public static void start(Connection conn, Statement st, ResultSet rs) throws SQLException {
        System.out.println("---knuMOVIE 에 오신걸 환영합니다.---");
        while (true) {
            System.out.println("1. 회원 로그인");
            System.out.println("2. 회원 가입");

            int menu = sc.nextInt();
            if (menu == 1) {
                Signin sign = new Signin(Account.getInstance());
                if (sign.canLogin(conn, st, rs)) {
                    sign.printInfo();
                    //일반 로그인 완료 - 1.
//            완료 이후 기능
                }
                break;
            } // sin.canLogin();
            else if (menu == 2) {
                register(st);
                break;
            }
            else System.out.println("잘못된 정보를 입력하셨습니다. 메뉴를 다시 확인해주세요.");
        }
    }
    public static void movie(){
        System.out.println("메뉴를 선택해 주세요.");
        System.out.println("1. 영상물 조회");
        System.out.println("2. 영상물 검색");
        int menu = sc.nextInt();
        switch(menu){

        }
    }
    public static void retreive(){
        String title;
        System.out.println("제목을 입력해주세요.");
        title = sc.next();
        // title로 조회
    }
    public static void search(){
        // Account account = new Account();
        // setter를 통해서 바로바로 주입
        System.out.println("검색할 옵션을 선택해 주세요.");
        System.out.println("*** Type *** : ");
        String type = sc.next();
        System.out.println("*** Runtime *** : ");
        String runtime = sc.next();
        System.out.println("*** Start Year (YYYY/MM/DD) *** : ");
        String startYear = sc.next();
        System.out.println("*** Genre *** : ");
        String genre = sc.next();
        System.out.println("*** Rating *** : ");
        String rating = sc.next();
        System.out.println("*** Viewing Class *** : ");
        String viewClass = sc.next();
        System.out.println("*** Admin ID *** : ");
        String adminId = sc.next();
        // 위의 정보들로 DB 조회
    }
    public static void member(){
        System.out.println("메뉴를 선택해 주세요.");
        System.out.println("1. 비밀번호 변경");
        System.out.println("2. 회원탈퇴");
        int menu = sc.nextInt();
        switch(menu){

        }
    }
    public static void admin(){
        System.out.println("메뉴를 선택해 주세요.");
        System.out.println("1. 영상물 등록");
        System.out.println("2. 영상물 정보 변경");
        int menu = sc.nextInt();
        switch(menu){

        }
    }
}
