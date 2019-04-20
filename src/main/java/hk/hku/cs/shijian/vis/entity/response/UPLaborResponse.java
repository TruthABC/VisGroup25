package hk.hku.cs.shijian.vis.entity.response;

import hk.hku.cs.shijian.vis.entity.UserVideoWithTag;

import java.util.Date;
import java.util.List;

public class UPLaborResponse extends CommonResponse {

    private List<UserVideoWithTag> userVideos;

    private List<Long> monthBins;
    private List<Integer> monthBinNum;
    private List<Double> monthBinMinute;
    private List<Double> monthBinHot;
    private List<Double> monthBinView;
    private List<Double> monthBinReply;

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
}
