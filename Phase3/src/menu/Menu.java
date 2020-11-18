package menu;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import member.Account;
import member.Signin;
import member.Signup;
import rating.Rate;
import video.Movie;
import video.Video;

public class Menu {

    private static Account acc;
    public static Scanner sc = new Scanner(System.in);

    public Menu(Account acc) {
        this.acc = acc;
    }

    public static void register(Statement st) throws SQLException {
        Signup up = new Signup(Account.getInstance());
        up.signUp(st);
    }

    //    반복조건 필요할듯..
    public static void start(Statement st) throws SQLException {
        System.out.println("---knuMOVIE 에 오신걸 환영합니다.---");
        boolean loginStatus = false;
        while (true) {
            if(loginStatus == false)
            {
                System.out.println("1. 로그인");
                System.out.println("2. 회원 가입");
            }

            int menu = sc.nextInt();
            if (menu == 1) {
                Signin sign = new Signin(Account.getInstance());
                if (sign.canLogin(st)) {
                    System.out.println("1. 회원 관련 기능");
                    System.out.println("2. 영상물 관련 기능");
                    System.out.println("3. 평가 관련 기능");
                    System.out.println("9. 뒤로 가기");
//                    영상물 검색 및 출력

                    switch (sc.nextInt()) {
                        case 1:
                            System.out.println("1. 회원정보 수정");
                            System.out.println("2. 비밀번호 수정");

                            switch (sc.nextInt()) {
                                case 1:
                                    sign.modifyMemberInfo(st);

                                    break;
                                case 2:
                                    sign.modifyPasswd(st);

                                    break;
                            }

                            break;
                        case 2:
                            videoMenu(st);

                            break;
                        case 3: // 평가 관련 기능
                            rateMenu(st);
                        default:
                    }
                }


                break;
            } // sin.canLogin();
            else if (menu == 2) {
                register(st);
                break;
            } else System.out.println("잘못된 정보를 입력하셨습니다. 메뉴를 다시 확인해주세요.");
        }
    }

    public static void videoMenu(Statement st) {
        System.out.println("메뉴를 선택해 주세요.");
        System.out.println("1. 영상물 전체 출력");
        System.out.println("2. 영상물 제목 검색");
        System.out.println("3. 영상물 조건 검색");
        System.out.println("4. 뒤로가기");
        int menu = sc.nextInt();
        Video video = new Video(Movie.getInstance(), st, acc.getAcc_id());

        switch (menu) {
            case 1:
                video.allVidoeInfo(st);

                break;

            case 2:
                video.titleSearch(st);

                break;

            case 3:
                video.conditionSearch(st);

                break;

            default:
                return;
        }
        // 영상물 평가할지 묻는 메서드
        rate(st);
    }

    public static void retreive() {
        String title;
        System.out.println("제목을 입력해주세요.");
        title = sc.next();
        // title로 조회
    }

    public static void search() {
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

    public static void member() {
        System.out.println("메뉴를 선택해 주세요.");
        System.out.println("1. 비밀번호 변경");
        System.out.println("2. 회원탈퇴");
        int menu = sc.nextInt();
        switch (menu) {

        }
    }

    public static void admin() {
        System.out.println("메뉴를 선택해 주세요.");
        System.out.println("1. 영상물 등록");
        System.out.println("2. 영상물 정보 변경");
        int menu = sc.nextInt();
        switch (menu) {

        }
    }

    public static void rateMenu(Statement st) {
        //System.out.println("메뉴를 선택해 주세요.");
        Rate rate = new Rate(acc);
        if(acc.isManager()) // 관리자
        {
            System.out.println("----< 모든 영상물 평가 내역 >----");
            rate.checkAllRatings(st);
        }
        else // 일반 회원
        {
            System.out.println("----< 나의 영상물 평가 내역 >----");
            rate.checkMyRatings(st);
        }

    }
    public static void rate(Statement st) {
        System.out.println("메뉴를 선택하세요.");
        System.out.println("1. 뒤로가기");
        System.out.println("2. 평가하기");
        int menu = sc.nextInt();
        if(menu == 2){
            Rate r = new Rate(acc);
            System.out.println("평가할 영상물의 이름을 입력하세요");
            String title = sc.next();
            r.rateMovie(acc, st, title);
        }
    }
}
