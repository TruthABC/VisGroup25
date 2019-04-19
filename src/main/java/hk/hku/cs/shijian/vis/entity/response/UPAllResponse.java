package hk.hku.cs.shijian.vis.entity.response;

import hk.hku.cs.shijian.vis.entity.DetailedUser;
import hk.hku.cs.shijian.vis.entity.RelationOfUser;

import java.util.List;

public class UPAllResponse extends CommonResponse {

    private List<DetailedUser> detailedUserList;
    private List<RelationOfUser> relationOfUserList;

    public UPAllResponse() {
        super();
    }

    public UPAllResponse(int errcode, String errmsg, List<DetailedUser> detailedUserList, List<RelationOfUser> relationOfUserList) {
        super(errcode, errmsg);
        this.detailedUserList = detailedUserList;
        this.relationOfUserList = relationOfUserList;
    }

    public List<DetailedUser> getDetailedUserList() {
        return detailedUserList;
    }

    public void setDetailedUserList(List<DetailedUser> detailedUserList) {
        this.detailedUserList = detailedUserList;
    }

    public List<RelationOfUser> getRelationOfUserList() {
        return relationOfUserList;
    }

    public void setRelationOfUserList(List<RelationOfUser> relationOfUserList) {
        this.relationOfUserList = relationOfUserList;
    }
}
