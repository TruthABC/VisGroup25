package hk.hku.cs.shijian.vis;

import hk.hku.cs.shijian.vis.entity.DetailedUser;
import hk.hku.cs.shijian.vis.entity.UserStat;
import hk.hku.cs.shijian.vis.entity.UserVideoWithTag;
import hk.hku.cs.shijian.vis.entity.VideoTag;

import java.util.*;

public class GlobalDataOfVideos {

    public static Map<Integer, UserVideoWithTag> gVideoWithTagAll; //aid -> value
    public static Map<Integer, List<Integer>> gMid2Aids; //mid -> aid[]

    public static void loadUserVideoWithTagToGlobalData(String pathVideoWithTag) {
        // 0. only once init is needed
        if (gVideoWithTagAll != null) {
            return;
        }

        // 1. VideoWithTag loading
        Map<Integer, List<Integer>> mid2Aids = new HashMap<>();
        Map<Integer, UserVideoWithTag> videoWithTagAll = new HashMap<>();
        Scanner scanner = Global.getFileScanner(pathVideoWithTag);
        if (scanner == null) {
            return;
        }
        scanner.nextLine();

        UserVideoWithTag vWithT = null;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");

            //update user-video mapping
            int mid = Integer.parseInt(parts[0]);
            int aid = Integer.parseInt(parts[2]);
            if (!mid2Aids.containsKey(mid)) {
                mid2Aids.put(mid, new ArrayList<>());
            }
            if (!mid2Aids.get(mid).contains(aid)) {
                mid2Aids.get(mid).add(aid);
            }


            if (vWithT == null || vWithT.getAid() != aid) { // first line with ONE or NO tags
                vWithT = new UserVideoWithTag();
                videoWithTagAll.put(aid, vWithT); //////////////////////////////////Add one more video
                vWithT.setMid(mid);
                vWithT.setAuthor(parts[1]);
                vWithT.setAid(aid); //Integer.parseInt(parts[2]);
                vWithT.setTitle(parts[3]);
                vWithT.setCreated(Long.parseLong(parts[4]));
                vWithT.setLength(parts[5]);
                vWithT.setPic(parts[6]);

                if (parts[7].length() == 0) {
                    parts[7] = "0";
                    parts[8] = "-1";
                    parts[9] = "Unknown";
                    parts[10] = "0";
                    parts[11] = "0";
                    parts[12] = "0";
                    parts[13] = "0";
                    parts[14] = "0";
                    parts[15] = "0";
                }
                vWithT.setDuration(Integer.parseInt(parts[7]));
                vWithT.setTid(Integer.parseInt(parts[8]));
                vWithT.setTname(parts[9]);

                vWithT.setView(Integer.parseInt(parts[10]));
                vWithT.setDanmaku(Integer.parseInt(parts[11]));
                vWithT.setReply(Integer.parseInt(parts[12]));
                vWithT.setLike(Integer.parseInt(parts[13]));
                vWithT.setCoin(Integer.parseInt(parts[14]));
                vWithT.setFavorite(Integer.parseInt(parts[15]));

                List<VideoTag> tags = new ArrayList<>();
                vWithT.setTags(tags);
                VideoTag vt = new VideoTag();
                tags.add(vt);
                if (parts[16].length() == 0) {
                    parts[16] = "-1";
                    parts[18] = "0";
                    parts[19] = "0";
                }
                vt.setTag_id(Integer.parseInt(parts[16]));
                vt.setTag_name(parts[17]);
                vt.setUse(Integer.parseInt(parts[18]));
                vt.setAtten(Integer.parseInt(parts[19]));
            } else { // additional tags (TWO or MORE)
                List<VideoTag> tags = vWithT.getTags();
                VideoTag vt = new VideoTag();
                tags.add(vt);
                if (parts[16].length() == 0) {
                    parts[16] = "-1";
                    parts[18] = "0";
                    parts[19] = "0";
                }
                vt.setTag_id(Integer.parseInt(parts[16]));
                vt.setTag_name(parts[17]);
                vt.setUse(Integer.parseInt(parts[18]));
                vt.setAtten(Integer.parseInt(parts[19]));
            }
        }//End of while (scanner.hasNextLine())
        scanner.close();

        gMid2Aids = mid2Aids;
        gVideoWithTagAll = videoWithTagAll;
        fillDetailedUsers();
        fillUserStats();
    }//End of loadUserVideoWithTagToGlobalData(String pathVideoWithTag)

    private static void fillDetailedUsers() {
        Map<Integer, DetailedUser> detailedUsers = GlobalDataOfUsers.gDetailedUsers;
        for (int mid: detailedUsers.keySet()) {
            DetailedUser du = detailedUsers.get(mid);
            du.setAllVideos(new ArrayList<>());

            //for: in all users videos' aids
            List<Integer> aidList = gMid2Aids.get(mid);
            for (int aid: aidList) {
                UserVideoWithTag vWithT = gVideoWithTagAll.get(aid);
                du.getAllVideos().add(vWithT);
            }
        }
    }

    private static void fillUserStats() {
        Map<Integer, UserStat> userStats = new HashMap<>();

        for (UserVideoWithTag uvt : gVideoWithTagAll.values()) {
            int mid = uvt.getMid();
            if (!userStats.containsKey(mid)) {
                userStats.put(mid, new UserStat(mid, uvt.getAuthor(), GlobalDataOfUsers.gDetailedUsers.get(mid).getFollower(),0,0,0,0,0,0,0,0));
            }
            UserStat us = userStats.get(mid);
            us.setVideo(us.getVideo() + 1);
            us.setMinute(us.getMinute() + uvt.getDuration() / 60.0);
            us.setFav(us.getFav() + uvt.getFavorite());
            us.setCoin(us.getCoin() + uvt.getCoin());
            us.setLike(us.getLike() + uvt.getLike());
            us.setView(us.getView() + uvt.getView());
            us.setDanmaku(us.getDanmaku() + uvt.getDanmaku());
            us.setReply(us.getReply() + uvt.getReply());
        }

        GlobalDataOfUsers.gUserStats = userStats;
    }
}
