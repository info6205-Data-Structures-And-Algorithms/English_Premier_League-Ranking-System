/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ranking.model;

import java.text.DateFormat;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Ruchit Gandhi <Ruchit at Northeasten.com>
 */
public class Match {

    // season	home_team_name	away_team_name	date_string	full_time_result	home_goals	away_goals	half_time_HOME_Team_score	half_time_Away_Team_score
    
    private String homeTeam;
    private String awayTeam;
    private int homeGoals;
    private int awayGoals;
    private int halfTimeHomeTeamGoals;
    private int halfTimeAwayTeamGoals;
    private boolean isComplete;
    private boolean isPredicted;
    private Date date;
    
    private int season;
    
    private Result result ;

    public Match(String homeTeam, String awayTeam,int season,Result result,boolean isComplete,boolean isPredicted) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.season = season;
        this.isComplete = isComplete;
        this.isPredicted = isPredicted;
        this.awayGoals = 0;
        this.homeGoals = 0;
        this.halfTimeAwayTeamGoals = 0;
        this.halfTimeHomeTeamGoals = 0;
        this.result = result;
    }

 

    public Match(String homeTeam, String awayTeam,  int homeGoals, int awayGoals, int halfTimeHomeTeamGoals, int halfTimeAwayTeamGoals, int season, Result result) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;
        this.halfTimeHomeTeamGoals = halfTimeHomeTeamGoals;
        this.halfTimeAwayTeamGoals = halfTimeAwayTeamGoals;
        this.season = season;
        this.result = result;        
        this.isComplete = true;
        this.isPredicted = false;
    }

    public boolean isIsComplete() {
        return isComplete;
    }

    public void setIsComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    public boolean isIsPredicted() {
        return isPredicted;
    }

    public void setIsPredicted(boolean isPredicted) {
        this.isPredicted = isPredicted;
    }
    
    

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
    
    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

//    public String getDate() {
//        return date;
//    }
//
//    public void setDate(String date) {
//        this.date = date;
//    }

    public int getHomeGoals() {
        return homeGoals;
    }

    public void setHomeGoals(int homeGoals) {
        this.homeGoals = homeGoals;
    }

    public int getAwayGoals() {
        return awayGoals;
    }

    public void setAwayGoals(int awayGoals) {
        this.awayGoals = awayGoals;
    }

    public int getHalfTimeHomeTeamGoals() {
        return halfTimeHomeTeamGoals;
    }

    public void setHalfTimeHomeTeamGoals(int halfTimeHomeTeamGoals) {
        this.halfTimeHomeTeamGoals = halfTimeHomeTeamGoals;
    }

    public int getHalfTimeAwayTeamGoals() {
        return halfTimeAwayTeamGoals;
    }

    public void setHalfTimeAwayTeamGoals(int halfTimeAwayTeamGoals) {
        this.halfTimeAwayTeamGoals = halfTimeAwayTeamGoals;
    }

    @Override
    public int hashCode() {
        return (homeTeam+""+awayTeam+""+season).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Match other = (Match) obj;
        if (this.season != other.season) {
            return false;
        }
        if (!Objects.equals(this.homeTeam, other.homeTeam)) {
            return false;
        }
        if (!Objects.equals(this.awayTeam, other.awayTeam)) {
            return false;
        }
        return true;
    }

    public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public void setDate(String date) {
		long dateLong = Date.parse(date);
		System.out.println(dateLong);
	}

	@Override
    public String toString() {
        return "Match{" + "homeTeam=" + homeTeam + ", awayTeam=" + awayTeam + ", homeGoals=" + homeGoals + ", awayGoals=" + awayGoals + ", halfTimeHomeTeamGoals=" + halfTimeHomeTeamGoals + ", halfTimeAwayTeamGoals=" + halfTimeAwayTeamGoals + ", season=" + season + ", result=" + result + '}';
    }
    
    
    

}
