package hk.hku.cs.shijian.vis.entity;

import java.util.List;

public class RelationOfUser {

    private int mid;
    private String name;
    private List<SimplifiedUser> followings;
    private List<SimplifiedUser> followers;
    private List<SimplifiedUser> friends;

    public RelationOfUser() {}

    public RelationOfUser(int mid, String name) {
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

    public List<SimplifiedUser> getFollowings() {
        return followings;
    }

    public void setFollowings(List<SimplifiedUser> followings) {
        this.followings = followings;
    }

    public List<SimplifiedUser> getFollowers() {
        return followers;
    }

    public void setFollowers(List<SimplifiedUser> followers) {
        this.followers = followers;
    }

    public List<SimplifiedUser> getFriends() {
        return friends;
    }

    public void setFriends(List<SimplifiedUser> friends) {
        this.friends = friends;
    }
}
