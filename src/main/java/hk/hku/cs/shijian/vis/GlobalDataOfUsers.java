package hk.hku.cs.shijian.vis;

import hk.hku.cs.shijian.vis.entity.DetailedUser;
import hk.hku.cs.shijian.vis.entity.RelationOfUser;
import hk.hku.cs.shijian.vis.entity.SimplifiedUser;

import java.util.*;

public class GlobalDataOfUsers {

    public static Map<Integer, DetailedUser> gDetailedUsers;//mid -> value
    public static Map<String, Integer> gName2Mid;
    public static Map<Integer, RelationOfUser> gRelationsOfUsers;

    public static void loadUserDataToGlobalData(String pathDetail, String pathTags, String pathFollowings, String pathFriends) {
        // 0. only once init is needed
        if (gDetailedUsers != null) {
            return;
        }
        Map<Integer, DetailedUser> detailedUsers = new HashMap<>();
        Map<String, Integer> name2Mid = new HashMap<>();

        // 1. Details loading
        Scanner scanner = Global.getFileScanner(pathDetail);
        if (scanner == null) {
            return;
        }
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");
            DetailedUser du = new DetailedUser();
            du.setMid(Integer.parseInt(parts[0]));
            du.setName(parts[1]);
            du.setSex(parts[2]);
            du.setFace(parts[3]);
            du.setFollowing(Integer.parseInt(parts[4]));
            du.setFollower(Integer.parseInt(parts[5]));
            detailedUsers.put(du.getMid(), du);
            name2Mid.put(du.getName(), du.getMid());
        }

        // 2. Tags loading
        scanner = Global.getFileScanner(pathTags);
        if (scanner == null) {
            return;
        }
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");
            if (parts.length <= 2) {
                continue;
            }
            int mid = Integer.parseInt(parts[0]); //String name = parts[1];
            String[] tags = new String[parts.length - 2];
            for (int i = 2; i < parts.length; i++) {
                tags[i-2] = parts[i];
            }
            DetailedUser du = detailedUsers.get(mid);
            du.setTags(tags);
        }

        gDetailedUsers = detailedUsers;
        gName2Mid = name2Mid;
        loadUserFollowingToGlobalData(pathFollowings, pathFriends);
    }

    private static void loadUserFollowingToGlobalData(String pathFollowings, String pathFriends) {
        Map<Integer, RelationOfUser> relationsOfUsers = new HashMap<>();

        //1. Fill followings and followers
        Scanner scanner = Global.getFileScanner(pathFollowings);
        if (scanner == null) {
            return;
        }
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");
            String name1 = parts[0];
            String name2 = parts[1];
            int mid1 = gName2Mid.get(parts[0]);
            int mid2 = gName2Mid.get(parts[1]);

            // add following
            if (relationsOfUsers.containsKey(mid1)) {
                RelationOfUser rou = relationsOfUsers.get(mid1);
                rou.getFollowings().add(new SimplifiedUser(mid2, name2));
            } else {
                RelationOfUser rou = new RelationOfUser(mid1, name1);
                rou.setFollowings(new ArrayList<>());
                rou.setFollowers(new ArrayList<>());
                rou.setFriends(new ArrayList<>());
                rou.getFollowings().add(new SimplifiedUser(mid2, name2));
                relationsOfUsers.put(mid1, rou);
            }

            // add follower
            if (relationsOfUsers.containsKey(mid2)) {
                RelationOfUser rou = relationsOfUsers.get(mid2);
                rou.getFollowers().add(new SimplifiedUser(mid1, name1));
            } else {
                RelationOfUser rou = new RelationOfUser(mid2, name2);
                rou.setFollowings(new ArrayList<>());
                rou.setFollowers(new ArrayList<>());
                rou.setFriends(new ArrayList<>());
                rou.getFollowers().add(new SimplifiedUser(mid1, name1));
                relationsOfUsers.put(mid2, rou);
            }
        }

        //2. Fill friends
        scanner = Global.getFileScanner(pathFriends);
        if (scanner == null) {
            return;
        }
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");
            String name2 = parts[1];
            int mid1 = gName2Mid.get(parts[0]);
            int mid2 = gName2Mid.get(parts[1]);
            RelationOfUser rou = relationsOfUsers.get(mid1);
            rou.getFriends().add(new SimplifiedUser(mid2, name2));
        }

        gRelationsOfUsers = relationsOfUsers;
    }

}
