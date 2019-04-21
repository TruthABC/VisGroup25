package hk.hku.cs.shijian.vis.controller;

import hk.hku.cs.shijian.vis.GlobalDataOfUsers;
import hk.hku.cs.shijian.vis.entity.DetailedUser;
import hk.hku.cs.shijian.vis.entity.RelationOfUser;
import hk.hku.cs.shijian.vis.entity.SimplifiedUser;
import hk.hku.cs.shijian.vis.entity.response.CommonResponse;
import hk.hku.cs.shijian.vis.entity.response.UPAllResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static hk.hku.cs.shijian.vis.GlobalURL.*;

@RestController
public class UPBasicAllController {

    @RequestMapping("/up_basic_all")
    @CrossOrigin
    public CommonResponse getUPAll(HttpServletRequest request) {
        //File Mapping Test (relative path || absolute(real) path || system path || net path)
        String absoluteRootPath = request.getRealPath("/");
        absoluteRootPath += "WEB-INF/classes/static/data/";

        GlobalDataOfUsers.loadUserDataToGlobalData(
                absoluteRootPath + USERS_DETAIL,
                absoluteRootPath + USERS_TAGS,
                absoluteRootPath + USERS_FOLLOWING,
                absoluteRootPath + USERS_FRIEND
        );

        List<SimplifiedUser> simplifiedUsers = new ArrayList<>();
        for (DetailedUser detailedUser: GlobalDataOfUsers.gDetailedUsers.values()) {
            simplifiedUsers.add(new SimplifiedUser(
                    detailedUser.getMid(),
                    detailedUser.getName(),
                    detailedUser.getFace(),
                    detailedUser.getFollowing(),
                    detailedUser.getFollower()
            ));
        }
        simplifiedUsers.sort(Comparator.comparingInt(SimplifiedUser::getFollower).reversed());

        List<RelationOfUser> relationOfUsers = new ArrayList<>();
        relationOfUsers.addAll(GlobalDataOfUsers.gRelationsOfUsers.values());

        return new UPAllResponse(0, "", simplifiedUsers,  relationOfUsers);
    }

}
