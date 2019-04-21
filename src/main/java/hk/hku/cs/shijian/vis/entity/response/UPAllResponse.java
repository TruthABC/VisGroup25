package hk.hku.cs.shijian.vis.entity.response;

import hk.hku.cs.shijian.vis.entity.SimplifiedUser;
import hk.hku.cs.shijian.vis.entity.RelationOfUser;

import java.util.List;

public class UPAllResponse extends CommonResponse {

    private List<SimplifiedUser> simplifiedUserList;
    private List<RelationOfUser> relationOfUserList;

    public UPAllResponse() {
        super();
    }

    public UPAllResponse(int errcode, String errmsg, List<SimplifiedUser> detailedUserList, List<RelationOfUser> relationOfUserList) {
        super(errcode, errmsg);
        this.simplifiedUserList = detailedUserList;
        this.relationOfUserList = relationOfUserList;
    }

    public List<SimplifiedUser> getSimplifiedUserList() {
        return simplifiedUserList;
    }

    public void setSimplifiedUserList(List<SimplifiedUser> detailedUserList) {
        this.simplifiedUserList = detailedUserList;
    }

    public List<RelationOfUser> getRelationOfUserList() {
        return relationOfUserList;
    }

    public void setRelationOfUserList(List<RelationOfUser> relationOfUserList) {
        this.relationOfUserList = relationOfUserList;
    }
}
