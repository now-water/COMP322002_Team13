package member;

public class Account {
    private static Account acc = null;

    //    싱글톤
    public static Account getInstance() {
        if (acc == null) {
            acc =  new Account();
            return acc;
        }
        return acc;
    }

    private String acc_id;
    private String acc_pw;
    String user_name;
    String phone_num;
    String birth_date;
    int age;
    String gender;
    String address;
    String job;
    String mem_type;
    boolean manager;



    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getMem_type() {
        return mem_type;
    }

    public void setMem_type(String mem_type) {
        this.mem_type = mem_type;
    }

    public boolean isManager() {
        return manager;
    }

    public void setManager(boolean manager) {
        this.manager = manager;
    }

    public String getAcc_id() {
        return acc_id;
    }

    public void setAcc_id(String acc_id) {
        this.acc_id = acc_id;
    }

    public String getAcc_pw() {
        return acc_pw;
    }

    public void setAcc_pw(String acc_pw) {
        this.acc_pw = acc_pw;
    }

    public void printInfo() {
        System.out.println(getAcc_id());
        System.out.println(getAcc_pw());
        System.out.println(getUser_name());
        System.out.println(getPhone_num());
        System.out.println(getBirth_date());
        System.out.println(getAcc_pw());
        System.out.println(getGender());
        System.out.println(getAddress());
        System.out.println(getJob());
        System.out.println(getMem_type());
        System.out.println(isManager());
    }
}