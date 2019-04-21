package hk.hku.cs.shijian.vis.controller;

import hk.hku.cs.shijian.vis.GlobalDataOfUsers;
import hk.hku.cs.shijian.vis.entity.response.CommonResponse;
import hk.hku.cs.shijian.vis.entity.response.UPBasicResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static hk.hku.cs.shijian.vis.GlobalURL.*;

@RestController
public class UPBasicController {

//    private static final String USERS_DETAIL = "100plus/users-1555232296771.csv";
//    private static final String USERS_TAGS = "100plus/tags-1555232296771.csv";
//    private static final String USERS_FOLLOWING = "100plus/target_edges100-1554013692072.csv";
//    private static final String USERS_FRIEND = "100plus/target_edges100_double-1554013692072.csv";

    @RequestMapping("/up_basic")
    @CrossOrigin
    public CommonResponse getUPBasic(@RequestParam(value="name", defaultValue="") String paramName,
                                     @RequestParam(value="name2", defaultValue="") String paramName2,
                                     HttpServletRequest request) {
        //File Mapping Test (relative path || absolute(real) path || system path || net path)
        String absoluteRootPath = request.getRealPath("/");
        absoluteRootPath += "WEB-INF/classes/static/data/";

        //check null
        if (paramName == null || paramName.length() == 0) {
            return new CommonResponse(1, "Cannot find target of param 'name'");
        }

        GlobalDataOfUsers.loadUserDataToGlobalData(
                absoluteRootPath + USERS_DETAIL,
                absoluteRootPath + USERS_TAGS,
                absoluteRootPath + USERS_FOLLOWING,
                absoluteRootPath + USERS_FRIEND
        );

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

        //get name1's data
        UPBasicResponse ret = new UPBasicResponse(0, "OK",
                GlobalDataOfUsers.gDetailedUsers.get(mid),
                GlobalDataOfUsers.gRelationsOfUsers.get(mid));

        //name2 check null
        mid = 0;
        name = null;
        if (paramName2 != null && paramName2.length() != 0) {
            if (GlobalDataOfUsers.gName2Mid.containsKey(paramName2)) {
                mid = GlobalDataOfUsers.gName2Mid.get(paramName2);
                name = paramName2;
            } else {
                try {
                    mid = Integer.parseInt(paramName2);
                    if (GlobalDataOfUsers.gDetailedUsers.containsKey(mid)){
                        name = GlobalDataOfUsers.gDetailedUsers.get(mid).getName();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (name != null) {
                ret.setDetailedUser2(GlobalDataOfUsers.gDetailedUsers.get(mid));
            }
        }

        return ret;
    }

}
