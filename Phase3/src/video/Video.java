package video;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Video {
    private static Movie movie;
    public static ArrayList<String> ratedMovie = new ArrayList<String>();
    //private static Map<String, ArrayList<String>> allMovies = new HashMap<>();
    public static Scanner sc= new Scanner(System.in);

    public Video(Movie movie, Statement st, String acc_id){

        this.movie = movie;
        String query = "SELECT R.m_title FROM \"knuMovie\".\"RATING\" AS R " +
                "WHERE R.account_id = \'" + acc_id + "\'";
        try{
            ResultSet rs = st.executeQuery(query);
            boolean flag = true;
            while(rs.next())
            {
                flag =false;
                ratedMovie.add(rs.getString(1));
            }
            if(flag)
            {
                //System.out.println("평가한 영상물이 존재하지 않습니다.");
            }

        } catch(SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void allVidoeInfo(Statement st) {
        System.out.println("모든 영상물 정보. ");
        String query = "SELECT title " +
                "FROM \"knuMovie\".\"MOVIE\"";

        int idx = 0;
        ArrayList<String> movieList = new ArrayList<>();
        try {
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                String title = rs.getString(1);
                if(ratedMovie.contains(title))
                    continue;
                System.out.println(idx + ": " +title);
                movieList.add(title);
                idx +=1;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        while(true) {
            System.out.println("--------------------------------");
            System.out.println("1: 영상물 상세 정보 확인");
            System.out.println("2: 뒤로 가기");

            int func = sc.nextInt();
            if (func == 1) {
                System.out.println("*** 영상물 정보를 확인하려면 인덱스를 입력해주세요. ***");
                int index = sc.nextInt();
                if(index < 0 || index >= movieList.size())
                    System.out.println("잘못된 인덱스입니다.");
                else
                {
                    query = "SELECT * FROM \"knuMovie\".\"MOVIE\" AS M " +
                            "WHERE M.title = \'" + movieList.get(index) + "\'";
                    //System.out.println(query);
                    try {
                        ResultSet rs = st.executeQuery(query);
                        boolean flag = true;
                        while (rs.next()) {
                            flag = false;
                            movie.setTitle(rs.getString(1));
                            movie.setType(rs.getString(2));
                            movie.setRuntime(rs.getInt(3));
                            movie.setStart_year(rs.getString(4));
                            movie.setGenre(rs.getInt(5));
                            movie.setRating(rs.getDouble(6));
                            movie.setViewing_class(rs.getString(7));
                            movie.setAccount_id(rs.getString(8));
                            printInfo();
                        }
                        if(flag)
                            System.out.println("잘못된 정보를 입력했습니다.");
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
            else if(func == 2)
                break;
        }
    }

    public void printInfo() {
        LinkedList list = new LinkedList();

        String title = movie.getTitle();
        if(ratedMovie.contains(title)) return;

        list.add("제목 : " + movie.getTitle());
        list.add("타입 : " + movie.getType());
        list.add("상영시간 : " + movie.getRuntime());
        list.add("상영년도 : " + movie.getStart_year());
        list.add("장르 : " + movie.getGenre());
        list.add("평점 : " + movie.getRating());
        list.add("관람등급 : " + movie.getViewing_class());
        list.add("업로더 : " + movie.getAccount_id());

        System.out.println(list.toString());
    }

    public int titleSearch(Statement st) {
//        제목으로 영상 검색
        System.out.println("제목을 입력해주세요.");

        String title = new Scanner(System.in).nextLine();
        String query = "SELECT M.title " +
                "FROM \"knuMovie\".\"MOVIE\" AS M " +
                "WHERE M.title = " + "\'" + title + "\'";
        //System.out.println(query);
        boolean flag = true;
        try {
            ResultSet rs = st.executeQuery(query);

            if (rs.next()) {
                if(!ratedMovie.contains(title)) {
                    flag = false;
                    System.out.println(title + " : 해당 영상물이 존재합니다.");
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(flag){
            System.out.println("이미 평가한 영상물이거나, 해당 검색 결과가 존재하지 않습니다.");
            return -1;
        }
        System.out.println("--------------------------------");
        System.out.println("1: 영상물 상세 정보 확인");
        System.out.println("2: 뒤로 가기");
        int func = sc.nextInt();
        if (func == 1) {

            query = "SELECT * FROM \"knuMovie\".\"MOVIE\" AS M " +
                    "WHERE M.title = \'" + title + "\'";
            //System.out.println(query);
            try {
                ResultSet rs = st.executeQuery(query);
                flag = true;
                if (rs.next()) {
                    if(!ratedMovie.contains(title)) {
                        flag = false;
                        movie.setTitle(rs.getString(1));
                        movie.setType(rs.getString(2));
                        movie.setRuntime(rs.getInt(3));
                        movie.setStart_year(rs.getString(4));
                        movie.setGenre(rs.getInt(5));
                        movie.setRating(rs.getDouble(6));
                        movie.setViewing_class(rs.getString(7));
                        movie.setAccount_id(rs.getString(8));
                        printInfo();
                    }

                }
                if (flag)
                    System.out.println("잘못된 정보를 입력했습니다.");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return 0;
    }

    public int conditionSearch(Statement st) {
//        조건으로 영상 검색
        String query = "SELECT M.title " +
                "FROM \"knuMovie\".\"MOVIE\" AS M ";
        //System.out.println(query);
        boolean pre = false;
        Scanner sc = new Scanner(System.in);
        System.out.println("검색할 옵션을 선택해 주세요.");

        System.out.println("*** Type(movie, knumoviedb original, tv series) *** ");
        String type = sc.nextLine();
        if(!type.equals(""))
        {
            query += "WHERE M.type = \'" + type + "\' ";
            pre = true;
        }

        System.out.println("*** Runtime (입력값의 -5, +5 범위)*** ");
        String runtime = sc.nextLine();
        if(!runtime.equals(""))
        {
            double rt = Double.parseDouble(runtime);
            if(pre) query += "AND ";
            else {
                query += "WHERE ";
                pre = true;
            }
            query += "M.runtime >= " + (rt - 5) + " ";
            query += "AND M.runtime <= " + (rt + 5) + " ";
        }

        System.out.println("*** Start Year (YYYY) *** ");
        String startYear = sc.nextLine();
        if(!startYear.equals(""))
        {
            if(pre) query += "AND ";
            else {
                query += "WHERE ";
                pre = true;
            }
            query += "extract(YEAR FROM start_year) = " + startYear + " ";
        }

        System.out.println("*** Genre *** ");
        System.out.println("1: Action, 2: Sf, 3: Comedy, 4: Thriller, 5: Romance");
        String genre = sc.nextLine();
        if(!genre.equals(""))
        {
            if(pre) query += "AND ";
            else {
                query += "WHERE ";
                pre = true;
            }
            query += "genre = " + genre + " ";
        }

        System.out.println("*** Rating *** ");
        String rating = sc.nextLine();
        if(!rating.equals(""))
        {
            if(pre) query += "AND ";
            else {
                query += "WHERE ";
                pre = true;
            }
            query += "rating = " + rating + " ";
        }

        System.out.println("*** Viewing Class (ALL, 12, 15, 19)*** ");
        String viewClass = sc.nextLine();
        if(!viewClass.equals(""))
        {
            if(pre) query += "AND ";
            else {
                query += "WHERE ";
                pre = true;
            }
            query += "viewing_class = \'" + viewClass + "\' ";
        }

        System.out.println("*** Admin ID *** ");
        String adminId = sc.nextLine();
        if(!adminId.equals(""))
        {
            if(pre) query += "AND ";
            else {
                query += "WHERE ";
                pre = true;
            }
            query += "account_id = \'" + adminId + "\' ";
        }
        // check
        //System.out.println(query);
        boolean flag = true;
        ArrayList<String> movieList = new ArrayList<>();
        int idx = 0;

        try {
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                String title = rs.getString(1);
                if(ratedMovie.contains(title))
                    continue;
                System.out.println(idx + ": " +title);
                movieList.add(title);
                idx +=1;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        while(true) {
            System.out.println("--------------------------------");
            System.out.println("1: 영상물 상세 정보 확인");
            System.out.println("2: 뒤로 가기");

            int func = sc.nextInt();
            if (func == 1) {
                System.out.println("*** 영상물 정보를 확인하려면 인덱스를 입력해주세요. ***");
                int index = sc.nextInt();
                if(index < 0 || index >= movieList.size())
                    System.out.println("잘못된 인덱스입니다.");
                else
                {
                    query = "SELECT * FROM \"knuMovie\".\"MOVIE\" AS M " +
                            "WHERE M.title = \'" + movieList.get(index) + "\'";
                    try {
                        ResultSet rs = st.executeQuery(query);
                        flag = true;
                        if (rs.next()) {
                            if(!ratedMovie.contains(movieList.get(index))) {
                                flag = false;
                                movie.setTitle(rs.getString(1));
                                movie.setType(rs.getString(2));
                                movie.setRuntime(rs.getInt(3));
                                movie.setStart_year(rs.getString(4));
                                movie.setGenre(rs.getInt(5));
                                movie.setRating(rs.getDouble(6));
                                movie.setViewing_class(rs.getString(7));
                                movie.setAccount_id(rs.getString(8));
                                printInfo();
                            }
                        }
                        if(flag)
                            System.out.println("이미 평가한 영상물이거나, 해당 검색 결과가 존재하지 않습니다.");
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
            else if (func == 2)
                break;
        }
        if(flag) return -1;
        return 0;
    }
}