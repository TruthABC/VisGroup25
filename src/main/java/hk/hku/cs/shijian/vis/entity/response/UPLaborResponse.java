package hk.hku.cs.shijian.vis.entity.response;

import hk.hku.cs.shijian.vis.entity.UserStat;
import hk.hku.cs.shijian.vis.entity.UserVideoWithTag;

import java.util.List;

public class UPLaborResponse extends CommonResponse {

    private List<UserVideoWithTag> userVideos;

    private List<Long> monthBins; // also for name2

    private List<Integer> monthBinNum;
    private List<Double> monthBinMinute;
    private List<Double> monthBinHot;
    private List<Double> monthBinView;
    private List<Double> monthBinReply;

    ///////////////////////// for name1 EXTENDED_20190421 //////////////////
    private List<String> videoTypes;
    private List<Integer> videoTypeNums;

    private List<String> videoTags;
    private List<Integer> videoTagNums;
    ///////////////////////// for name1 EXTENDED_20190421 //////////////////

    //for name2
    private List<UserVideoWithTag> userVideos2;
    private List<Integer> monthBinNum2;
    private List<Double> monthBinHot2;

    //Stat for comparing name1 & name2
    private UserStat userStat;
    private UserStat userStat2;

    public UPLaborResponse() {
        super();
    }

    public UPLaborResponse(int errcode, String errmsg) {
        super(errcode, errmsg);
    }

    public List<UserVideoWithTag> getUserVideos() {
        return userVideos;
    }

    public void setUserVideos(List<UserVideoWithTag> userVideos) {
        this.userVideos = userVideos;
    }

    public List<Integer> getMonthBinNum() {
        return monthBinNum;
    }

    public void setMonthBinNum(List<Integer> monthBinNum) {
        this.monthBinNum = monthBinNum;
    }

    public List<Double> getMonthBinMinute() {
        return monthBinMinute;
    }

    public void setMonthBinMinute(List<Double> monthBinMinute) {
        this.monthBinMinute = monthBinMinute;
    }

    public List<Double> getMonthBinHot() {
        return monthBinHot;
    }

    public void setMonthBinHot(List<Double> monthBinHot) {
        this.monthBinHot = monthBinHot;
    }

    public List<Long> getMonthBins() {
        return monthBins;
    }

    public void setMonthBins(List<Long> monthBins) {
        this.monthBins = monthBins;
    }

    public List<Double> getMonthBinView() {
        return monthBinView;
    }

    public void setMonthBinView(List<Double> monthBinView) {
        this.monthBinView = monthBinView;
    }

    public List<Double> getMonthBinReply() {
        return monthBinReply;
    }

    public void setMonthBinReply(List<Double> monthBinReply) {
        this.monthBinReply = monthBinReply;
    }

    public List<UserVideoWithTag> getUserVideos2() {
        return userVideos2;
    }

    public void setUserVideos2(List<UserVideoWithTag> userVideos2) {
        this.userVideos2 = userVideos2;
    }

    public List<Integer> getMonthBinNum2() {
        return monthBinNum2;
    }

    public void setMonthBinNum2(List<Integer> monthBinNum2) {
        this.monthBinNum2 = monthBinNum2;
    }

    public List<Double> getMonthBinHot2() {
        return monthBinHot2;
    }

    public void setMonthBinHot2(List<Double> monthBinHot2) {
        this.monthBinHot2 = monthBinHot2;
    }

    public UserStat getUserStat() {
        return userStat;
    }

    public void setUserStat(UserStat userStat) {
        this.userStat = userStat;
    }

    public UserStat getUserStat2() {
        return userStat2;
    }

    public void setUserStat2(UserStat userStat2) {
        this.userStat2 = userStat2;
    }

    public List<String> getVideoTypes() {
        return videoTypes;
    }

    public void setVideoTypes(List<String> videoTypes) {
        this.videoTypes = videoTypes;
    }

    public List<Integer> getVideoTypeNums() {
        return videoTypeNums;
    }

    public void setVideoTypeNums(List<Integer> videoTypeNums) {
        this.videoTypeNums = videoTypeNums;
    }

    public List<String> getVideoTags() {
        return videoTags;
    }

    public void setVideoTags(List<String> videoTags) {
        this.videoTags = videoTags;
    }

    public List<Integer> getVideoTagNums() {
        return videoTagNums;
    }

    public void setVideoTagNums(List<Integer> videoTagNums) {
        this.videoTagNums = videoTagNums;
    }
}
