package View;


import Model.Player;
import Model.Team;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class PromptMenu {

    private Map<String, String> mMenu;
    private BufferedReader mReader;
    private Set<Player> mPlayers;
    private List<Team> mTeams;
    //private List<Team> mTeams ;//= new TreeSet<Team>();
    protected int MAXTEAMS;



    public PromptMenu(Player[] players){
        //reader to read user input
        mReader = new BufferedReader(new InputStreamReader(System.in));
        mPlayers = new TreeSet(Arrays.asList(players));
        MAXTEAMS = players.length / 11; //TODO maybe fix this

       // mTeams = new ArrayList<>();
        mTeams = new ArrayList<>();

        //new hashmap for menu
        mMenu = new TreeMap<String, String>();


        mMenu.put("1)", "Create A New Team");
        mMenu.put("2)", "Add Player To A Team");
        mMenu.put("3)", "Remove Player From A Team");
        mMenu.put("4)", "View Height Report For A Team");
        mMenu.put("5)", "View A League Balance Report");
        mMenu.put("6)", "View Team Roster");
        mMenu.put("7)", "Quit Program");

    }//end promptMenu constructor

    /*Method to display menu from hash map and get user input and set the input to choice
    which will be returned.
     */
    public String promptForAction() throws IOException{

        System.out.println("Welcome To The Soccer League Manager");
        System.out.println("Please Choose From The Following Menu...");

        for(Map.Entry<String, String> option: mMenu.entrySet()){
            System.out.printf("%s %s %n", option.getKey(), option.getValue());
        }

        System.out.println("Type The Number Of The Menu Option: ");
        String choice = mReader.readLine().trim().toLowerCase();

        return choice;
    }//end prompt for action method

    /*method that uses a switch statement to determine what will be displayed based on the users choice*/

    public void runPromptMenu(){
        String choice = "";

        do{
            try{
                choice = promptForAction();

                switch(choice){
                    case "1":
                        //user adds team by typing in team name and coach name
                        isValidTeamToAdd();
                        break;
                    case "2":
                        if(mTeams.size() == 0){
                            System.out.printf("There Are No Teams Available, but there are %d " +
                                    "available team slots available. %n", MAXTEAMS);
                            choice = promptForAction();
                        }
                        //user selects team
                        Team teamToAddPlayersTo = pickTeam();
                        //user selects player
                        Player playerToAddToTeam = pickPlayer();
                        //selected player is added to team
                        teamToAddPlayersTo.addPlayer(playerToAddToTeam);
                        //remove player from list of players
                        mPlayers.remove(playerToAddToTeam);
                        //print successful statement
                        System.out.printf("%s , %s has been added to the team, %s. " +
                                "This player is no longer available unless removed.%n",
                                playerToAddToTeam.getLastName(),
                                playerToAddToTeam.getFirstName(),
                                teamToAddPlayersTo.getTeamName());
                        System.out.println("");
                        break;
                    case "3":
                        if(mTeams.size() == 0){
                            System.out.printf("There Are No Teams Available, but there are %d " +
                                    "available team slots available. %n", MAXTEAMS);
                            choice = promptForAction();
                        }

                        Team teamToRemovePlayerFrom = pickTeam();
                        //make sure team has players
                        if(teamToRemovePlayerFrom.getTeamPlayers().size() == 0){
                            System.out.println("There are no players on this team");
                        }
                        else {
                            Player playerToRemoveFromTeam = removePlayer(teamToRemovePlayerFrom); //user picks team
                            //user selects player, player is removed
                            teamToRemovePlayerFrom.removePlayer(playerToRemoveFromTeam);
                            //print successful statement
                            System.out.printf("%s , %s has been successfully removed from %s.%n",
                                    playerToRemoveFromTeam.getLastName(),
                                    playerToRemoveFromTeam.getFirstName(),
                                    teamToRemovePlayerFrom.getTeamName());
                            //add player back to available players
                            mPlayers.add(playerToRemoveFromTeam);
                        }
                        break;
                    case "4":
                        if(mTeams.size() == 0){
                            System.out.printf("There Are No Teams Available, but there are %d " +
                                    "available team slots available. %n", MAXTEAMS);
                            choice = promptForAction();
                        }
                        Team teamToViewReportFor = pickTeam();
                        displayHeightReport(teamToViewReportFor);
                        break;
                    case "5":
                        if(mTeams.size() == 0){
                            System.out.printf("There Are No Teams Available, but there are %d " +
                                    "available team slots available. %n", MAXTEAMS);
                            choice = promptForAction();
                        }
                        else{
                            displayLeagueBalanceReport();
                        }

                        break;
                    case "6":
                        if(mTeams.size() == 0){
                            System.out.printf("There Are No Teams Available, but there are %d " +
                                    "available team slots available. %n", MAXTEAMS);
                            choice = promptForAction();
                        }
                        else{
                            Team teamToViewRosterFor = pickTeam();
                            teamToViewRosterFor.displayTeamRoster();
                        }
                        break;
                    case "7":
                        System.out.println("Thank You For Using The League Manager Program.");
                        break;
                    default:
                        System.out.printf("Unknown Choice %s, please choose from 1 thru 7.", choice);
                }
            }catch(IOException ioe){
                System.out.println("Error Processing Input");
                ioe.printStackTrace();
            }
        }while(!choice.toLowerCase().equals("7"));
    }


    /*validates if players are available based on maxteams (11 per team), if open slots than checks
    to see if team exists already, if not add the team. Displays appropriate messages to user based on outcome.
     */
    public void isValidTeamToAdd()throws IOException{

        System.out.println("Enter A Team Name: ");
        String teamName = mReader.readLine();
        System.out.println("Enter The Coach's Name: ");
        String coachName = mReader.readLine();


        if(MAXTEAMS > 0){
            Team team = new Team(teamName, coachName);

            if(isValid(team.getTeamName())){
                mTeams.add(team);
                MAXTEAMS--;
                System.out.printf("The Team %s Has Been Successfully Added.%n", team.getTeamName());
            }
            else{
                System.out.printf("The team %s has already been added to the soccer league.%n", team.getTeamName());
            }
        }
        else{
            System.out.println("There are non enough players available to create a new team." +
                    "Please select from the menu options. %n");
        }
        //return new Team(teamName,coachName);
    }


    //method to display the team and accept user input for the team of choice
    public Team pickTeam() throws IOException {
        int count = 1;
        int choice;

        //sort teams array list
        Collections.sort(mTeams);

        do {
            for (Team team : mTeams) {
                System.out.printf("%d) %s%n", count, team.getTeamName());
                count++;
            }

            System.out.println("Please Select From The Above Options: ");
            choice = Integer.parseInt(mReader.readLine().toLowerCase());

            if (choice <= 0 || choice > mTeams.size()) {
                System.out.printf("Please Select From The Choices 1 thru %d.%n", mTeams.size());
                count--;
            }


        }while(choice <= 0 || choice > mTeams.size());

        List<Team> teamsInLeague = new ArrayList<Team>(mTeams);
        return teamsInLeague.get(choice - 1);
    }

    //method to display a list of available players, so the user can pick one
    public Player pickPlayer() throws IOException{
        int count = 1;
        int choice;
        Set<Player> playerList = mPlayers;


        for(Player player: playerList){
            System.out.printf("%d) %s, %s    Height: %d in.   Experience: %s%n", count, player.getLastName(),
                    player.getFirstName(), player.getHeightInInches(), player.isPreviousExperience());
            count++;
        }
        System.out.println("Please Select A Player By Choosing The Number: ");
        choice = Integer.parseInt(mReader.readLine().toLowerCase().trim());
        List<Player> availablePlayers = new ArrayList<>(playerList);
        return availablePlayers.get(choice - 1);
    }

    //Remove method to get chosen team as parameter
    public Player removePlayer(Team chosenTeam) throws IOException{
        int choice;
        int count= 1;
        Set<Player> teamPlayers = new TreeSet<>();
        teamPlayers = chosenTeam.getTeamPlayers();


        for(Player player: teamPlayers){
            System.out.printf("%d) %s, %s    Height: %d in.   Experience: %s%n", count, player.getLastName(),
                    player.getFirstName(), player.getHeightInInches(), player.isPreviousExperience());
            count++;
        }

        System.out.println("Please Select A Player By Choosing The Number: ");
        choice = Integer.parseInt(mReader.readLine().toLowerCase().trim());
        List<Player> availableTeamPlayers = new ArrayList<>(teamPlayers);

        return availableTeamPlayers.get(choice - 1);

    }

    //method to sort team by height and print to screen

    public void displayHeightReport(Team chosenTeam){
        System.out.printf("********** Height Report For %s ********** %n", chosenTeam.getTeamName());

        if(chosenTeam.getTeamPlayers().size() == 0){
            System.out.println("This team does not have any players and cannot print an empty height report.");
        }

        int count = 0;
        Map<Integer, List<Player>> teamHeightReport = chosenTeam.teamByHeight();

        for(Map.Entry<Integer, List<Player>> entry : teamHeightReport.entrySet()){
            System.out.printf("%nHeight: %d %n" + "-----------%n", entry.getKey());

            for(Player player: entry.getValue()){
                System.out.printf("%s, %s %n", player.getLastName(), player.getFirstName());
                count++;
            }
            System.out.printf("There is a total number of %d players at the height of %d. %n", count, entry.getKey());
            count = 0;
        }

    }

    //method to create report for all teams showing experience by team
    public void displayLeagueBalanceReport(){
        System.out.println("********** League Balance Report ********** %n");

        for(Team team: mTeams){
            Map<String, Integer> teamExperience = team.teamByExperience();

            System.out.printf("%n%s Experience: ", team.getTeamName());

            for(Map.Entry<String, Integer> entry: teamExperience.entrySet()){
                System.out.printf("%s: %d%n                 ", entry.getKey(), entry.getValue());
            }

            System.out.printf("%d out of %d has previous experience.%n",
                    teamExperience.get("Experienced Players"),
                    team.getTeamPlayers().size());
        }
    }


    //check to see if the teamname is contained in the list of teams
    public boolean isValid(String name){

        for(int i = 0; i < mTeams.size(); i++){
            if(mTeams.get(i).getTeamName().equalsIgnoreCase(name)){
                return false;
            }
        }
        return true;
    }




}
