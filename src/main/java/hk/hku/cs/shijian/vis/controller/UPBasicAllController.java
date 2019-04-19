package hk.hku.cs.shijian.vis.controller;

import hk.hku.cs.shijian.vis.GlobalDataOfUsers;
import hk.hku.cs.shijian.vis.entity.DetailedUser;
import hk.hku.cs.shijian.vis.entity.RelationOfUser;
import hk.hku.cs.shijian.vis.entity.response.CommonResponse;
import hk.hku.cs.shijian.vis.entity.response.UPAllResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
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

        List<DetailedUser> detailedUsers = new ArrayList<>();
        detailedUsers.addAll(GlobalDataOfUsers.gDetailedUsers.values());

        List<RelationOfUser> relationOfUsers = new ArrayList<>();
        relationOfUsers.addAll(GlobalDataOfUsers.gRelationsOfUsers.values());

        return new UPAllResponse(0, "", detailedUsers,  relationOfUsers);
    }

}
