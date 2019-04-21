package hk.hku.cs.shijian.vis.entity;

public class UserStat {

    private int mid;
    private String name;

    private int follower;
    private int video;
    private double minute;

    private long fav;
    private long coin;
    private long like;

    private long view;
    private long danmaku;
    private long reply;

    public UserStat() {}

    public UserStat(int mid, String name) {
        this.mid = mid;
        this.name = name;
    }

    public UserStat(int mid, String name, int follower, int video, double minute, long fav, long coin, long like, long play, long danmaku, long reply) {
        this.mid = mid;
        this.name = name;
        this.follower = follower;
        this.video = video;
        this.minute = minute;
        this.fav = fav;
        this.coin = coin;
        this.like = like;
        this.view = play;
        this.danmaku = danmaku;
        this.reply = reply;
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

    public int getFollower() {
        return follower;
    }

    public void setFollower(int follower) {
        this.follower = follower;
    }

    public int getVideo() {
        return video;
    }

    public void setVideo(int video) {
        this.video = video;
    }

    public double getMinute() {
        return minute;
    }

    public void setMinute(double minute) {
        this.minute = minute;
    }

    public long getFav() {
        return fav;
    }

    public void setFav(long fav) {
        this.fav = fav;
    }

    public long getCoin() {
        return coin;
    }

    public void setCoin(long coin) {
        this.coin = coin;
    }

    public long getLike() {
        return like;
    }

    public void setLike(long like) {
        this.like = like;
    }

    public long getView() {
        return view;
    }

    public void setView(long view) {
        this.view = view;
    }

    public long getDanmaku() {
        return danmaku;
    }

    public void setDanmaku(long danmaku) {
        this.danmaku = danmaku;
    }

    public long getReply() {
        return reply;
    }

    public void setReply(long reply) {
        this.reply = reply;
    }
}
