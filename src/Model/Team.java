package Model;


import java.util.*;

public class Team implements Comparable<Team> {

    private String mTeamName;
    private String mCoachName;
    private Set<Player> mTeamPlayers;

    public Team(String teamName, String coachName){
        mTeamName = teamName;
        mCoachName = coachName;
        mTeamPlayers = new TreeSet<>();
    }

    //getters and setters
    public void setTeamName(String teamName){
        mTeamName = teamName;
    }
    public String getTeamName(){
        return mTeamName;
    }
    public void setCoachName(String coachName){
        mCoachName = coachName;
    }
    public String getCoachName(){
        return mCoachName;
    }
    public void setTeamPlayers(Set<Player> teamPlayers){
        mTeamPlayers = teamPlayers;
    }
    public Set<Player> getTeamPlayers(){
        return mTeamPlayers;
    }

    public void addPlayer(Player player){
        mTeamPlayers.add(player);
    }
    public void removePlayer(Player player){
        mTeamPlayers.remove(player);
    }


    @Override
    public String toString(){
        return String.format("%s - %s", mTeamName, mCoachName);
    }


    //method to create map of players height for a team
    public Map<Integer, List<Player>> teamByHeight(){
        Map<Integer, List<Player>> heightMap = new TreeMap<>();

        for(Player player: mTeamPlayers){
            List<Player> playersByHeight = heightMap.get(player.getHeightInInches());
            if(playersByHeight == null){
                playersByHeight = new ArrayList<>();
                heightMap.put(player.getHeightInInches(), playersByHeight);
            }
            playersByHeight.add(player);
        }

        return heightMap;

    }

    //method to create map of experience based on players previous experience and keep count
    public Map<String, Integer> teamByExperience(){
        Map<String, Integer> experienceMap = new TreeMap<>();
        int experienceCount = 0;
        int inexperienceCount = 0;

        for(Player player: mTeamPlayers){
            if(player.isPreviousExperience()){
                experienceCount++;
            }
            else{
                inexperienceCount++;
            }
        }
        experienceMap.put("Experienced Players", experienceCount);
        experienceMap.put("Inexperienced Players", inexperienceCount);

        return experienceMap;

    }

    //method to print teamname, coach name and players out as a report
    public void displayTeamRoster(){

        System.out.printf("********** Roster For %s **********%n%n", mTeamName);
        System.out.printf("The coach for %s is: %s%n", mTeamName, mCoachName);
        System.out.printf("The roster for %s:%n", mTeamName);
        for(Player player : mTeamPlayers){
            System.out.printf("%s, %s Height: %d Experience: %s%n",
                    player.getLastName(), player.getFirstName(),
                    player.getHeightInInches(), player.isPreviousExperience());
        }
        System.out.printf("%n");
    }


    @Override
    public int compareTo(Team t) {
        return mTeamName.compareTo(t.getTeamName());
    }
}
