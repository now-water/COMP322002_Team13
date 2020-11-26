package menu;

import java.sql.ResultSet;
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
    private static Movie movie;
    private static boolean loginStatus = false;

    public static Video video;
    public static Scanner sc = new Scanner(System.in);

    public Menu(Account acc, Movie movie, Statement st) {
        this.acc = acc;
        this.movie = movie;
    }

    public static void register(Statement st) throws SQLException {
        Signup up = new Signup(Account.getInstance());
        up.signUp(st);
    }

    //    반복조건 필요할듯..
    public static void start(Statement st) throws SQLException {
        System.out.println("---knuMOVIE 에 오신걸 환영합니다.---");
        while (true) {
            int menu = 0;
            if (loginStatus == false) {
                System.out.println("1. 로그인");
                System.out.println("2. 회원 가입");
<<<<<<< HEAD
                System.out.println("3. 프로그램 종료");
=======
                System.out.println("3. 종료");
>>>>>>> d8cae3e6152ce7931a6bfe1869c18324671fbff4
                menu = sc.nextInt();
            }
            if (menu == 1) {
                loginStatus = true;
                Signin sign = new Signin(Account.getInstance());
                if (sign.canLogin(st)) {
                    video = new Video(Movie.getInstance(), st, acc.getAcc_id());
                    if (acc.isManager() == false) forMember(st, sign);
                    else forAdmin(st, sign);
                }
            } // sin.canLogin();
            else if (menu == 2) {
                register(st);
            } else if(menu == 3){
<<<<<<< HEAD
                System.out.println("프로그램을 종료합니다");
                break;
=======
                System.out.println("프로그램 종료.");

                return;
>>>>>>> d8cae3e6152ce7931a6bfe1869c18324671fbff4
            }
            else {
                loginStatus = false;
                System.out.println("잘못된 정보를 입력하셨습니다. 메뉴를 다시 확인해주세요.");
            }
        }
    }
<<<<<<< HEAD
=======

>>>>>>> d8cae3e6152ce7931a6bfe1869c18324671fbff4
    private static void forAdmin(Statement st, Signin sign) throws SQLException {
        while (true) {
            System.out.println("1. 관리자 기능");
            System.out.println("2. 회원 기능");
            System.out.println("3. 로그아웃");
            int func = sc.nextInt();
            if (func == 2)
<<<<<<< HEAD
            {
                forMember(st, sign);
                loginStatus = false;
                return;
            }
=======
                forMember(st, sign);
>>>>>>> d8cae3e6152ce7931a6bfe1869c18324671fbff4

            else if (func == 1) {
                System.out.println("1. 영상물 등록");
                System.out.println("2. 영상물 정보 수정");

                int flag = sc.nextInt();
                if (flag == 1) {
                    System.out.println("등록할 영상물의 필수 정보를 입력해주세요.");
                    System.out.print("title : ");
                    String title = sc.next();
                    movie.setTitle(title);

                    sc.nextLine();
                    System.out.print("type (movie, tv series, knumoviedb original): ");
                    String type = sc.nextLine();
                    movie.setType(type);

                    System.out.print("runtime : ");
                    String runtime = sc.next();
                    System.out.print("start_year ('yyyy-mm-dd'): ");
                    String start_year = sc.next();
                    movie.setStart_year(start_year);

                    System.out.print("account_id : ");
                    System.out.println(acc.getAcc_id());
                    movie.setAccount_id(acc.getAcc_id());

                    Integer genre = null;
                    Double rating = null;
                    String viewing_class = null;

                    System.out.println("추가 등록 정보를 입력하시겠습니까? Y/N");

                    boolean condition = sc.next().equals("Y") ? true : false;
                    while (condition) {
                        System.out.print("추가 등록을 원하는 항목은 무엇인가요? [1.genre, 2.rating, 3.viewing_class, 9. 뒤로가기] : ");
                        int num = sc.nextInt();
                        if (num == 1) {
                            System.out.print("genre : (1.Action, 2.Sf, 3.Comedy, 4.Thriller, 5.Romance) : ");
                            genre = sc.nextInt();
                            movie.setGenre(genre);
                        } else if (num == 2) {
                            System.out.print("rating : ");
                            rating = sc.nextDouble();
                            movie.setRating(rating);
                        } else if (num == 3) {
                            System.out.print("viewing_class (12, 15, 19, ALL) : ");
                            viewing_class = sc.next();
                            movie.setViewing_class(viewing_class);
                        } else {
                            break;
                        }

                    }

                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("INSERT INTO \"knuMovie\".\"MOVIE\" (title,type,runtime,start_year,genre,rating,viewing_class,account_id) VALUES(");
                    stringBuilder.append("\'" + title + "\'" + ",");
                    stringBuilder.append("\'" + type + "\'" + ",");
                    stringBuilder.append(runtime + ",");
                    stringBuilder.append("\'" + start_year + "\'" + ",");

//                genre, rating, viewing_class
                    stringBuilder.append(genre + ",");
                    stringBuilder.append(rating + ",");

                    if (viewing_class == null) stringBuilder.append(viewing_class + ",");
                    else stringBuilder.append("\'" + viewing_class + "\'" + ",");

                    stringBuilder.append("\'" + movie.getAccount_id() + "\')");
                    String query = String.valueOf(stringBuilder);
                    st.executeUpdate(query);
                } else {
//                        영상물 정보 수정
                    System.out.println("수정할 영상물의 제목을 입력하세요 ");
                    System.out.print("title : ");

                    String ModifyTitle = sc.next();
                    String query = "";

/*
            회원 탈퇴 기능, 반복문 사용으로 뒤로가기 기능 구현 등의 전체적인 콘솔 application의 동작 패턴 정리.. -> switch문을 전부 if로 바꿔야 break하기 편하다!
*/
                    query = "SELECT M.* " +
                            "FROM \"knuMovie\".\"MOVIE\" AS M,\"knuMovie\".\"ACCOUNT\" AS A " +
                            "WHERE M.account_id = A.acc_id " +
                            "  AND M.account_id = " + "\'" + acc.getAcc_id() + "\'";

                    ResultSet rs = st.executeQuery(query);

                    while (rs.next()) {
                        movie.setTitle(rs.getString(1));
                        if (ModifyTitle.equals(movie.getTitle())) {
//                                movie 정보를 다 가져오자
                            movie.setType(rs.getString(2));
                            movie.setRuntime(rs.getInt(3));
                            movie.setRuntime(rs.getInt(3));

                            movie.setStart_year(rs.getString(4));
                            movie.setGenre(rs.getInt(5));
                            movie.setRating(rs.getDouble(6));
                            movie.setViewing_class(rs.getString(7));
                            movie.setAccount_id(acc.getAcc_id());

                            break;
                        }
                    }

                    System.out.println("수정할 정보를 입력하세요");
                    while (true) {
                        System.out.println("1. title");
                        System.out.println("2. type");
                        System.out.println("3. runtime");
                        System.out.println("4. start_year");
                        System.out.println("5. genre");
                        System.out.println("6. rating");
                        System.out.println("7. viewing_class");
                        System.out.println("9. 뒤로가기");

                        int cond = sc.nextInt();
                        if (cond == 1) {
                            System.out.println("current title : " + movie.getTitle());
                            System.out.print("title : ");
                            String title = sc.next();
                            query = "UPDATE \"knuMovie\".\"MOVIE\" " +
                                    "SET title = " + "\'" + title + "\' " +
                                    "WHERE title = " + "\'" + movie.getTitle() + "\'";

                        } else if (cond == 2) {
                            System.out.println("current type : " + movie.getType());
                            System.out.print("type (movie, tv series, knumoviedb original): ");
                            sc.nextLine();
                            String type = sc.nextLine();
                            query = "UPDATE \"knuMovie\".\"MOVIE\" " +
                                    "SET type = " + "\'" + type + "\' " +
                                    "WHERE type = " + "\'" + movie.getType() + "\'";
                        } else if (cond == 3) {
                            System.out.println("current runtime : " + movie.getRuntime());
                            System.out.print("runtime : ");
                            Integer runtime = sc.nextInt();
                            query = "UPDATE \"knuMovie\".\"MOVIE\" " +
                                    "SET runtime = " + runtime + " " +
                                    "WHERE runtime = " + movie.getRuntime();
                        } else if (cond == 4) {
                            System.out.println("current start_year : " + movie.getStart_year());
                            System.out.print("start_year ('yyyy-mm-dd'): ");
                            String start_year = sc.next();
                            query = "UPDATE \"knuMovie\".\"MOVIE\" " +
                                    "SET start_year = " + "\'" + start_year + "\' " +

                                    "WHERE start_year = " + "\'" + movie.getStart_year() + "\'";
                        } else if (cond == 5) {
                            System.out.println("current genre : " + movie.getGenre());
                            System.out.print("genre : (1.Action, 2.Sf, 3.Comedy, 4.Thriller, 5.Romance) : ");
                            Integer genre = sc.nextInt();


                            query = "UPDATE \"knuMovie\".\"MOVIE\" " +
                                    "SET genre = " + genre + " " +
                                    "WHERE title = " + "\'" + movie.getTitle() + "\' " +
                                    "AND account_id = " + "\'" + movie.getAccount_id() + "\' ";
<<<<<<< HEAD


                        } else if (cond == 6) {
                            System.out.println("current rating : " + movie.getRating());
                            System.out.print("rating : ");
                            Double rating = sc.nextDouble();

=======


                        } else if (cond == 6) {
                            System.out.println("current rating : " + movie.getRating());
                            System.out.print("rating : ");
                            Double rating = sc.nextDouble();

>>>>>>> d8cae3e6152ce7931a6bfe1869c18324671fbff4
                            query = "UPDATE \"knuMovie\".\"MOVIE\" " +
                                    "SET rating = " + rating + " " +
                                    "WHERE title = " + "\'" + movie.getTitle() + "\' " +
                                    "AND account_id = " + "\'" + movie.getAccount_id() + "\' ";

                        } else if (cond == 7) {
                            System.out.println("current viewing_class : " + movie.getViewing_class());
                            System.out.print("viewing_class (12, 15, 19, ALL) : ");
                            String viewing_class = sc.next();

                            query = "UPDATE \"knuMovie\".\"MOVIE\" " +
                                    "SET viewing_class = " + "\'" + viewing_class + "\' " +
                                    "WHERE title = " + "\'" + movie.getTitle() + "\' " +
                                    "AND account_id = " + "\'" + movie.getAccount_id() + "\' ";

                        } else {
                            break;
                        }

                        st.executeUpdate(query);
                    }
                }
            } else if (func == 3) {
                loginStatus = false;
                System.out.println("성공적으로 로그아웃하였습니다.");
                break;
            }
        }
    }

    private static void forMember(Statement st, Signin sign) throws SQLException {
        while (true) {
            System.out.println("1. 회원 관련 기능");
            System.out.println("2. 영상물 관련 기능");
            System.out.println("3. 평가 관련 기능");
            System.out.println("4. 로그아웃");

//                    영상물 검색 및 출력
            int cond = sc.nextInt();
            if (cond == 1) {
                System.out.println("1. 회원정보 수정");
                System.out.println("2. 비밀번호 수정");
                System.out.println("3. 회원 탈퇴");
                System.out.println("4. 뒤로 가기");
                int temp = sc.nextInt();
                if (temp == 1)
                    sign.modifyMemberInfo(st);
                else if (temp == 2)
                    sign.modifyPasswd(st);
                else if (temp == 3) {
                    sign.secessionMember(st);
                    loginStatus = false;
                    break;
                } else if (temp == 4)
                    continue;
            } else if (cond == 2) {
                videoMenu(st);
            } else if (cond == 3) {
                rateMenu(st);
            } else if (cond == 4) {
                loginStatus = false;
                System.out.println("성공적으로 로그아웃하였습니다.");
                break;
            }
        }
    }

    public static void videoMenu(Statement st) {
        while (true) {
            System.out.println("메뉴를 선택해 주세요.");
            System.out.println("1. 영상물 전체 출력");
            System.out.println("2. 영상물 제목 검색");
            System.out.println("3. 영상물 조건 검색");
            System.out.println("4. 뒤로가기");
            int menu = sc.nextInt();
            int result = 0;
            switch (menu) {
                case 1:
                    video.allVidoeInfo(st);
                    break;
                case 2:
                    result = video.titleSearch(st);
                    break;
                case 3:
                    result = video.conditionSearch(st);
                    break;
                case 4:
                    return;
            }
            // 비정상적인 종료
            if (result == -1) {
                System.out.println("검색 결과가 존재하지 않습니다.");
                return;
            }

            // 영상물 평가할지 묻는 메서드
            if(result == 0) rate(st);
        }
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

    public static void rateMenu(Statement st) {
        //System.out.println("메뉴를 선택해 주세요.");
        Rate rate = new Rate(acc);
        if (acc.isManager()) // 관리자
        {
<<<<<<< HEAD
            System.out.println("1. 모든 영상물 평가 내역 확인");
            System.out.println("2. 나의 영상물 평가 내역");
            int func = sc.nextInt();
            if (func == 1) {
                System.out.println("----< 모든 영상물 평가 내역 >----");
                rate.checkAllRatings(st);
                return;
            }
=======
            System.out.println("----< 모든 영상물 평가 내역 >----");
            rate.checkAllRatings(st);
        } else // 일반 회원
        {
            System.out.println("----< 나의 영상물 평가 내역 >----");
            rate.checkMyRatings(st);
>>>>>>> d8cae3e6152ce7931a6bfe1869c18324671fbff4
        }
        // 일반 회원
        System.out.println("----< 나의 영상물 평가 내역 >----");
        rate.checkMyRatings(st);
    }

    public static void rate(Statement st) {
        System.out.println("---< 영상물을 평가하시겠습니까? >---.");
        System.out.println("1. 평가하기");
        System.out.println("2. 거절하기");
        int menu = sc.nextInt();
<<<<<<< HEAD
        if(menu == 1){
=======
        if (menu == 2) {
>>>>>>> d8cae3e6152ce7931a6bfe1869c18324671fbff4
            Rate r = new Rate(acc);
            sc.nextLine(); // 개행 제거
            System.out.println("평가할 영상물의 이름을 입력하세요");
            String title = sc.nextLine();
            //System.out.println(title + " 를 선택하셨습니다.");
            r.rateMovie(acc, st, title, video);
        }
    }
}
