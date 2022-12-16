public class Table {
    private String id_chat;
    private String user_id;
    private String count_slave;
    private String count_master;
    private String bucks;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    public Table(String id_chat, String user_id, String count_slave, String count_master, String bucks, String name) {
        this.user_id = user_id;
        this.id_chat = id_chat;
        this.count_slave = count_slave;
        this.count_master = count_master;
        this.bucks = bucks;
        this.name = name;
    }

    public Table(String id_chat, String user_id, String count_slave, String count_master, String bucks, String id, String name) {
        this.id_chat = id_chat;
        this.user_id = user_id;
        this.count_slave = count_slave;
        this.count_master = count_master;
        this.bucks = bucks;
        this.id = id;
        this.name = name;
    }

    public Table(String id, String user_id, String name) {
        this.id = id;
        this.user_id = user_id;
        this.name = name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCount_slave() {
        return count_slave;
    }

    public void setCount_slave(String count_slave) {
        this.count_slave = count_slave;
    }

    public String getCount_master() {
        return count_master;
    }

    public void setCount_master(String count_master) {
        this.count_master = count_master;
    }

    public String getBucks() {
        return bucks;
    }

    public void setBucks(String bucks) {
        this.bucks = bucks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public String getId_chat() {
        return id_chat;
    }

    public void setId_chat(String id_chat) {
        this.id_chat = id_chat;
    }

}

