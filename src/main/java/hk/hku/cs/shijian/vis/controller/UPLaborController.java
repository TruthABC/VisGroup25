package hk.hku.cs.shijian.vis.controller;

import hk.hku.cs.shijian.vis.GlobalDataOfUsers;
import hk.hku.cs.shijian.vis.GlobalDataOfVideos;
import hk.hku.cs.shijian.vis.entity.UserVideoWithTag;
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
    public CommonResponse getUPLabor(@RequestParam(value="name", defaultValue="") String paramName, HttpServletRequest request) {
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

        //check null
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

        //bins of month
        List<UserVideoWithTag> userVideos = GlobalDataOfUsers.gDetailedUsers.get(mid).getAllVideos();
        userVideos.sort(Comparator.comparingLong(UserVideoWithTag::getCreated));
        UPLaborResponse response = fillUPLaborResponse(userVideos);
        return response;
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
                while ((created.getYear() > lastCreated.getYear() && created.getMonth() - lastCreated.getMonth() != -11) || created.getMonth() - lastCreated.getMonth() > 1) {
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
        return res;
    }
}
