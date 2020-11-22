package rating;

import member.Account;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import video.*;
public class Rate{
    private static Account acc;
    static Scanner scanner = new Scanner(System.in);

    public Rate(Account acc) {
        this.acc = acc;
    }

    public void checkMyRatings(Statement st){
        String query = "SELECT m.title, m.rating, r.num_vote FROM \"knuMovie\".\"MOVIE\" AS m, \"knuMovie\".\"RATING\" AS r " +
        "WHERE m.title = r.m_title " +
        "AND r.account_id = '" + acc.getAcc_id() + "'";
        //System.out.println(query);
        try {
            boolean flag = true;
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                flag = false;
                System.out.println("제목 : " + rs.getString(1) +
                        " 평점 : " + rs.getDouble(2) +
                        " 영상물의 총 평가 수 " + rs.getString(3));
            }
            if(flag){
                System.out.println("평가한 내역이 존재하지 않습니다.");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("------------------------------");
    }
    public void checkAllRatings(Statement st){
        String query = "SELECT m.title, r.account_id, m.rating FROM \"knuMovie\".\"MOVIE\" AS m, \"knuMovie\".\"RATING\" AS r " +
                "WHERE m.title = r.m_title";

        try {
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                System.out.println("제목 : " + rs.getString(1) +
                        " 평가자 : " + rs.getString(2) +
                        " 총 평점 : " + rs.getInt(3));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void rateMovie(Account acc, Statement st, String title, Video video){//, Video video){
        //Video video = new Video(Movie.getInstance(), st, acc.getAcc_id());
        String query = "SELECT * FROM \"knuMovie\".\"MOVIE\" WHERE title = " + "\'" + title + "\' ";
        try {
            ResultSet rs = st.executeQuery(query);

            if (!rs.next()) {
                return;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if(video.ratedMovie.contains(title)){
            System.out.println("*** 이미 평가한 영상물입니다. ***");
            return;
        }
        System.out.println("점수를 입력해주세요. (0~10)");
        double score;
        while(true)
        {
            score = scanner.nextDouble();
            if(score >= 0 && score <= 10)
                break;
            System.out.println("잘못된 점수입니다. (0~10) 사이로 입력해 주세요");
        }

        /* maxNumVote 구하기 */
        int maxNumVote = 0;
        String getMaxVoteQuery = "SELECT MAX(R.num_vote) FROM \"knuMovie\".\"RATING\" AS R " +
        "WHERE R.m_title = \'" + title + "\'";
        try {
            ResultSet rs = st.executeQuery(getMaxVoteQuery);
            boolean flag = true;
            while (rs.next()) {
                flag = false;
                maxNumVote = rs.getInt(1);
            }
            if(flag){
                // 아직 한번도 평가를 받지 않은 영상물인 경우
                String findMovieQuery = "SELECT * FROM \"knuMovie\".\"MOVIE\" AS M WHERE M.title = \'" + title + "\'";

                try{
                    ResultSet rs2 = st.executeQuery(findMovieQuery);
                    boolean flag2 = true;
                    if (rs.next()) {
                        flag = false;
                    }
                    if(flag2){
                        System.out.println("해당하는 영상물이 존재하지 않습니다.");
                        return;
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                maxNumVote = 0;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        /* maxNumVote 업데이트 */
        String numVoteUpdateQuery = "INSERT INTO \"knuMovie\".\"RATING\" " +
                "VALUES(\'" + title + "\', \'" + acc.getAcc_id() + "\', " +
                (maxNumVote + 1) + ")";
        //System.out.println(numVoteUpdateQuery);
        try{
            st.executeUpdate(numVoteUpdateQuery);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        /* rating 구하기 */
        double rating = 0;
        String getRatingQuery = "SELECT M.rating FROM \"knuMovie\".\"MOVIE\" AS M " +
                "WHERE M.title = \'" + title + "\'";
        try {
            ResultSet rs = st.executeQuery(getRatingQuery);
            boolean flag = true;
            while (rs.next()) {
                flag = false;
                rating = rs.getDouble(1);
            }
            if(flag){
                System.out.println("해당하는 영상물이 존재하지 않습니다.");
                return;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        /* rating 업데이트 */
        // 소수점 둘째자리에서 반올림.
        double newRating = Math.round((maxNumVote * rating + score)/(maxNumVote + 1) * 10)/10;
        //System.out.println("입력값 : " + score + "원래 값 : "+rating + " 수정 값: " + newRating);
        String ratingUpdateQuery = "UPDATE \"knuMovie\".\"MOVIE\" " +
                "SET rating = " + newRating +
                " WHERE title = \'" + title + "\' ";
        //System.out.println(ratingUpdateQuery);
        try{
            st.executeUpdate(ratingUpdateQuery);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        System.out.println("성공적으로 평가하였습니다.");
        video.ratedMovie.add(title);
    }
}