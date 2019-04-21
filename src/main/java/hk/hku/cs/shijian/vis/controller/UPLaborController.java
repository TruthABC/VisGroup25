package hk.hku.cs.shijian.vis.controller;

import hk.hku.cs.shijian.vis.GlobalDataOfUsers;
import hk.hku.cs.shijian.vis.GlobalDataOfVideos;
import hk.hku.cs.shijian.vis.entity.UserVideoWithTag;
import hk.hku.cs.shijian.vis.entity.VideoTag;
import hk.hku.cs.shijian.vis.entity.response.CommonResponse;
import hk.hku.cs.shijian.vis.entity.response.UPLaborResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static hk.hku.cs.shijian.vis.GlobalURL.*;

@RestController
public class UPLaborController {

    @RequestMapping("/up_labor")
    @CrossOrigin
    public CommonResponse getUPLabor(@RequestParam(value="name", defaultValue="") String paramName,
                                     @RequestParam(value="name2", defaultValue="") String paramName2,
                                     HttpServletRequest request) {
        //File Mapping Test (relative path || absolute(real) path || system path || net path)
        String absoluteRootPath = request.getRealPath("/");
        absoluteRootPath += "WEB-INF/classes/static/data/";

        //check null
        if (paramName == null || paramName.length() == 0) {
            return new CommonResponse(1, "Cannot find target of param 'name'");
        }

        //initialize data
        GlobalDataOfUsers.loadUserDataToGlobalData(
                absoluteRootPath + USERS_DETAIL,
                absoluteRootPath + USERS_TAGS,
                absoluteRootPath + USERS_FOLLOWING,
                absoluteRootPath + USERS_FRIEND
        );
        GlobalDataOfVideos.loadUserVideoWithTagToGlobalData(absoluteRootPath + VIDEOS_WITH_TAG);

        //name check null
        int mid = 0;
        String name = null;
        if (GlobalDataOfUsers.gName2Mid.containsKey(paramName)) {
            mid = GlobalDataOfUsers.gName2Mid.get(paramName);
            name = paramName;
        } else {
            try {
                mid = Integer.parseInt(paramName);
                if (GlobalDataOfUsers.gDetailedUsers.containsKey(mid)){
                    name = GlobalDataOfUsers.gDetailedUsers.get(mid).getName();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (name == null) {
            return new CommonResponse(1, "Cannot find target of param 'name'");
        }

        int mid2 = 0;
        String name2 = null;
        if (paramName2 != null && paramName2.length() != 0) {
            if (GlobalDataOfUsers.gName2Mid.containsKey(paramName2)) {
                mid2 = GlobalDataOfUsers.gName2Mid.get(paramName2);
                name2 = paramName2;
            } else {
                try {
                    mid2 = Integer.parseInt(paramName2);
                    if (GlobalDataOfUsers.gDetailedUsers.containsKey(mid2)){
                        name2 = GlobalDataOfUsers.gDetailedUsers.get(mid2).getName();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        //bins of month
        if (name2 == null) {
            List<UserVideoWithTag> userVideos = GlobalDataOfUsers.gDetailedUsers.get(mid).getAllVideos();
            userVideos.sort(Comparator.comparingLong(UserVideoWithTag::getCreated));
            UPLaborResponse response = fillUPLaborResponse(userVideos);
            return response;
        } else {
            List<UserVideoWithTag> userVideos1 = GlobalDataOfUsers.gDetailedUsers.get(mid).getAllVideos();
            List<UserVideoWithTag> userVideos2 = GlobalDataOfUsers.gDetailedUsers.get(mid2).getAllVideos();
            List<UserVideoWithTag> userVideos1_2 = new ArrayList<>();
            userVideos1_2.addAll(userVideos1);
            userVideos1_2.addAll(userVideos2);
            userVideos1.sort(Comparator.comparingLong(UserVideoWithTag::getCreated));
            userVideos2.sort(Comparator.comparingLong(UserVideoWithTag::getCreated));
            userVideos1_2.sort(Comparator.comparingLong(UserVideoWithTag::getCreated));
            UPLaborResponse response = fillUPLaborResponseFor1_2(userVideos1, userVideos2, userVideos1_2);
            return response;
        }
    }

    private UPLaborResponse fillUPLaborResponse(List<UserVideoWithTag> userVideos) {
        UPLaborResponse res = new UPLaborResponse(0, "OK");
        List<Long> monthBins = new ArrayList<>();
        List<Integer> monthBinNum = new ArrayList<>();
        List<Double> monthBinMinute = new ArrayList<>();
        List<Double> monthBinHot = new ArrayList<>();
        List<Double> monthBinView = new ArrayList<>();
        List<Double> monthBinReply = new ArrayList<>();

        //make data for histogram
        UserVideoWithTag video0 = userVideos.get(0);
        Date lastCreated = new Date(video0.getCreated() * 1000);
        monthBins.add(lastCreated.getTime());
        monthBinNum.add(1);
        monthBinMinute.add(video0.getDuration() / 60.0);
        monthBinHot.add(video0.getFavorite()*0.6 + video0.getCoin()*0.3 + video0.getLike()*0.1);
        monthBinView.add(video0.getView()+0.0);
        monthBinReply.add(video0.getReply()+0.0);
        for (int i = 1; i < userVideos.size(); i++) {
            UserVideoWithTag video = userVideos.get(i);
            Date created = new Date(video.getCreated() * 1000);

            //if same month
            double minute = video.getDuration() / 60.0;
            double hot = video.getFavorite()*0.6 + video.getCoin()*0.3 + video.getLike()*0.1;
            if (created.getYear() == lastCreated.getYear() && created.getMonth() == lastCreated.getMonth()) {
                monthBinNum.set(monthBinNum.size()-1, monthBinNum.get(monthBinNum.size()-1)+1);
                monthBinMinute.set(monthBinMinute.size()-1, monthBinMinute.get(monthBinMinute.size()-1)+minute);
                monthBinHot.set(monthBinHot.size()-1, monthBinHot.get(monthBinHot.size()-1)+hot);
                monthBinView.set(monthBinView.size()-1, monthBinView.get(monthBinView.size()-1)+video.getView());
                monthBinReply.set(monthBinReply.size()-1, monthBinReply.get(monthBinReply.size()-1)+video.getReply());
            } else {//if not same month
                //while more than 1 month passed
                //  (Dec -> Jan) or (m -> m+1)
                while ( (created.getYear() - lastCreated.getYear() > 1) ||
                        (created.getYear() - lastCreated.getYear() == 1 && created.getMonth() - lastCreated.getMonth() != -11) ||
                        (created.getMonth() - lastCreated.getMonth() > 1)) {
                    lastCreated.setMonth(lastCreated.getMonth() + 1);
                    monthBins.add(lastCreated.getTime());
                    monthBinNum.add(0);
                    monthBinMinute.add(0.0);
                    monthBinHot.add(0.0);
                    monthBinView.add(0.0);
                    monthBinReply.add(0.0);
                }
                monthBins.add(created.getTime());
                monthBinNum.add(1);
                monthBinMinute.add(minute);
                monthBinHot.add(hot);
                monthBinView.add(video.getView()+0.0);
                monthBinReply.add(video.getReply()+0.0);
            }
            lastCreated = created;
        }

        res.setUserVideos(userVideos);
        res.setMonthBins(monthBins);
        res.setMonthBinNum(monthBinNum);
        res.setMonthBinMinute(monthBinMinute);
        res.setMonthBinHot(monthBinHot);
        res.setMonthBinView(monthBinView);
        res.setMonthBinReply(monthBinReply);

        //For type and tag
        res.setVideoTypes(new ArrayList<>());
        res.setVideoTypeNums(new ArrayList<>());
        res.setVideoTags(new ArrayList<>());
        res.setVideoTagNums(new ArrayList<>());

        fillUPTypeAndTag(userVideos, res.getVideoTypes(), res.getVideoTypeNums(), res.getVideoTags(), res.getVideoTagNums());

        return res;
    }

    private void fillUPTypeAndTag(List<UserVideoWithTag> userVideos, List<String> videoTypes, List<Integer> videoTypeNums, List<String> videoTags, List<Integer> videoTagNums) {
        for (UserVideoWithTag uvt: userVideos) {
            {
                String type = uvt.getTname();
                int index = videoTypes.indexOf(type);
                if (index == -1) {
                    videoTypes.add(type);
                    videoTypeNums.add(1);
                } else {
                    videoTypeNums.set(index, videoTypeNums.get(index) + 1);
                }
            }
            for (VideoTag vt: uvt.getTags()) {
                String tag = vt.getTag_name();
                int index = videoTags.indexOf(tag);
                if (index == -1) {
                    videoTags.add(tag);
                    videoTagNums.add(1);
                } else {
                    videoTagNums.set(index, videoTagNums.get(index) + 1);
                }
            }
        }//END for (UserVideoWithTag uvt: userVideos)
    }//END private void fillUPTypeAndTag

    private UPLaborResponse fillUPLaborResponseFor1_2(List<UserVideoWithTag> userVideos1, List<UserVideoWithTag> userVideos2, List<UserVideoWithTag> userVideos1_2) {
        UPLaborResponse res = new UPLaborResponse(0, "OK");
        List<Long> monthBins = new ArrayList<>();
        List<Integer> monthBinNum = new ArrayList<>();
        List<Double> monthBinHot = new ArrayList<>();

        List<Integer> monthBinNum2 = new ArrayList<>();
        List<Double> monthBinHot2 = new ArrayList<>();

        //1. make bins for histogram first
        UserVideoWithTag video0 = userVideos1_2.get(0);
        Date lastCreated = new Date(video0.getCreated() * 1000);
        monthBins.add(lastCreated.getTime());
        for (int i = 1; i < userVideos1_2.size(); i++) {
            UserVideoWithTag video = userVideos1_2.get(i);
            Date created = new Date(video.getCreated() * 1000);
            //if same month
            if (created.getYear() == lastCreated.getYear() && created.getMonth() == lastCreated.getMonth()) {

            } else {//if not same month
                //while more than 1 month passed
                //  (Dec -> Jan) or (m -> m+1)
                while ( (created.getYear() - lastCreated.getYear() > 1) ||
                        (created.getYear() - lastCreated.getYear() == 1 && created.getMonth() - lastCreated.getMonth() != -11) ||
                        (created.getMonth() - lastCreated.getMonth() > 1)) {
                    lastCreated.setMonth(lastCreated.getMonth() + 1);
                    monthBins.add(lastCreated.getTime());
                }
                monthBins.add(created.getTime());
            }
            lastCreated = created;
        }

        //2. fill name1
        for (int i = 0; i < monthBins.size(); i++) {
            monthBinNum.add(0);
            monthBinHot.add(0.0);
        }
        for (int i = 0, j1 = 0; i < monthBins.size() && j1 < userVideos1.size();) {
            UserVideoWithTag video = userVideos1.get(j1);

            Date nowBinDate = new Date(monthBins.get(i));
            Date created = new Date(video.getCreated() * 1000);

            //if same month
            if (created.getYear() == nowBinDate.getYear() && created.getMonth() == nowBinDate.getMonth()) {
                double hot = video.getFavorite()*0.6 + video.getCoin()*0.3 + video.getLike()*0.1;
                monthBinNum.set(i, monthBinNum.get(i) + 1);
                monthBinHot.set(i, monthBinHot.get(i) + hot);
                j1++;
            } else {//if not same month
                i++;
            }
        }

        //3. fill name2
        for (int i = 0; i < monthBins.size(); i++) {
            monthBinNum2.add(0);
            monthBinHot2.add(0.0);
        }
        for (int i = 0, j1 = 0; i < monthBins.size() && j1 < userVideos2.size();) {
            UserVideoWithTag video = userVideos2.get(j1);

            Date nowBinDate = new Date(monthBins.get(i));
            Date created = new Date(video.getCreated() * 1000);

            //if same month
            if (created.getYear() == nowBinDate.getYear() && created.getMonth() == nowBinDate.getMonth()) {
                double hot = video.getFavorite()*0.6 + video.getCoin()*0.3 + video.getLike()*0.1;
                monthBinNum2.set(i, monthBinNum2.get(i) + 1);
                monthBinHot2.set(i, monthBinHot2.get(i) + hot);
                j1++;
            } else {//if not same month
                i++;
            }
        }

        res.setUserVideos(userVideos1);
        res.setMonthBins(monthBins);
        res.setMonthBinNum(monthBinNum);
        res.setMonthBinHot(monthBinHot);

        res.setUserVideos2(userVideos2);
        res.setMonthBinNum2(monthBinNum2);
        res.setMonthBinHot2(monthBinHot2);
        res.setUserStat(GlobalDataOfUsers.gUserStats.get(userVideos1.get(0).getMid()));
        res.setUserStat2(GlobalDataOfUsers.gUserStats.get(userVideos2.get(0).getMid()));
        return res;
    }
}
