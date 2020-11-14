package video;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Scanner;

public class Video {
    private static Movie movie;

    public Video(Movie movie) {
        this.movie = movie;
    }

    public void allVidoeInfo(Statement st) {
        System.out.println("모든 영상물 정보. ");
        String query = "SELECT * " +
                "FROM \"knuMovie\".\"MOVIE\"";

        try {
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void printInfo() {
        LinkedList list = new LinkedList();

        list.add(movie.getTitle());
        list.add(movie.getType());
        list.add(movie.getRuntime());
        list.add(movie.getStart_year());
        list.add(movie.getGenre());
        list.add(movie.getRating());
        list.add(movie.getViewing_class());
        list.add(movie.getAccount_id());

        System.out.println(list.toString());
    }

    public void titleSearch(Statement st) {
//        제목으로 영상 검색
        System.out.println("제목을 입력해주세요.");

        String title = new Scanner(System.in).next();
        String query = "SELECT * " +
                "FROM \"knuMovie\".\"MOVIE\" AS M " +
                "WHERE M.title = " + "\'" + title + "\'";

        try {
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void conditionSearch(Statement st) {
//        조건으로 영상 검색
        System.out.println("조건을 입력해주세요.");
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Type + Genre");
        System.out.println("2. Genre + Version");

        int choose = scanner.nextInt();
        String type = "";
        String genre = "";
        String region = "";

        String query = "";
        switch (choose) {
//            type + genre
            case 1:
                scanner.nextLine();
                System.out.print("Type 입력 : ");
                type = scanner.nextLine();

                System.out.print("Genre 입력 : ");
                genre = scanner.next();

                query = "SELECT M.* " +
                        "FROM \"knuMovie\".\"MOVIE\" AS M, \"knuMovie\".\"GENRE\" AS G " +
                        "WHERE M.genre = G.seq " +
                        "AND G.category = " + "\'" + genre + "\' " +
                        "AND M.type = " + "\'" + type + "\'";

                break;

//                genre + region
            case 2:
                System.out.print("Genre 입력 : ");
                genre = scanner.next();
                System.out.print("Region 입력 : ");
                region = scanner.next();

                query = "SELECT M.* " +
                        "FROM \"knuMovie\".\"MOVIE\" AS M, \"knuMovie\".\"GENRE\" AS G,\"knuMovie\".\"VERSION\" AS V " +
                        "WHERE M.title = V.m_title " +
                        "AND M.genre = G.seq " +
                        "AND G.category = " + "\'" + genre + "\'" +
                        "AND V.region = "  + "\'" + region + "\'";

                break;
            default:

        }

        try {
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}