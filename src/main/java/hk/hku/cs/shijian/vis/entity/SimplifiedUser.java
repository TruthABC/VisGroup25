package hk.hku.cs.shijian.vis.entity;

public class SimplifiedUser {

    private int mid;
    private String name;

    public SimplifiedUser() {}

    public SimplifiedUser(int mid, String name) {
        this.mid = mid;
        this.name = name;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
