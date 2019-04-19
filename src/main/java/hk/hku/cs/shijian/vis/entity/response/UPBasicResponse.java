package hk.hku.cs.shijian.vis.entity.response;

import hk.hku.cs.shijian.vis.entity.DetailedUser;
import hk.hku.cs.shijian.vis.entity.RelationOfUser;

public class UPBasicResponse extends CommonResponse {

    private DetailedUser detailedUser;
    private RelationOfUser relationOfUser;

    public UPBasicResponse() {
        super();
    }

    public UPBasicResponse(int errcode, String errmsg, DetailedUser detailedUser, RelationOfUser relationOfUser) {
        super(errcode, errmsg);
        this.detailedUser = detailedUser;
        this.relationOfUser = relationOfUser;
    }

    public DetailedUser getDetailedUser() {
        return detailedUser;
    }

    public void setDetailedUser(DetailedUser detailedUser) {
        this.detailedUser = detailedUser;
    }

    public RelationOfUser getRelationOfUser() {
        return relationOfUser;
    }

    public void setRelationOfUser(RelationOfUser relationOfUser) {
        this.relationOfUser = relationOfUser;
    }
}
