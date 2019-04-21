package hk.hku.cs.shijian.vis.entity;

public class SimplifiedUser {

    private int mid;
    private String name;

    private String face;
    private int following; //21
    private int follower; //4943441

    public SimplifiedUser() {}

    public SimplifiedUser(int mid, String name) {
        this.mid = mid;
        this.name = name;
    }

    public SimplifiedUser(int mid, String name, String face, int following, int follower) {
        this.mid = mid;
        this.name = name;
        this.face = face;
        this.following = following;
        this.follower = follower;
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

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public int getFollower() {
        return follower;
    }

    public void setFollower(int follower) {
        this.follower = follower;
    }
}
