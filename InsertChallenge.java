package com.example.navigationsandtabs;

public class InsertChallenge {

    String challengeName;
    String challengeDescription;
    int points;

    public InsertChallenge(String challengeName, String challengeDescription, int points) {
        this.challengeDescription = challengeDescription;
        this.challengeName = challengeName;
        this.points = points;
    }

    public String getChallengeDescription() {
        return challengeDescription;
    }

    public String getChallengeName() {
        return challengeName;
    }

    public int getPoints() {
        return points;
    }
}
