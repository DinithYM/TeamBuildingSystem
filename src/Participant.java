public class Participant extends Person {

    private String preferredGame;
    private String preferredRole;
    private int skillRating;
    private int personalityScoreRaw;
    private int personalityScoreScaled;
    private String personalityType;

    public Participant(String id, String name) {
        super(id, name);
    }



    public String getPreferredGame() {
        return preferredGame;
    }

    public void setPreferredGame(String preferredGame) {
        this.preferredGame = preferredGame;
    }

    public String getPreferredRole() {
        return preferredRole;
    }

    public void setPreferredRole(String preferredRole) {
        this.preferredRole = preferredRole;
    }

    public int getSkillRating() {
        return skillRating;
    }

    public void setSkillRating(int skillRating) {
        this.skillRating = skillRating;
    }

    public int getPersonalityScoreRaw() {
        return personalityScoreRaw;
    }

    public int getPersonalityScoreScaled() {
        return personalityScoreScaled;
    }

    public void setPersonalityScore(int rawScore) {
        this.personalityScoreRaw = rawScore;
        this.personalityScoreScaled = rawScore * 4;
    }

    public String getPersonalityType() {
        return personalityType;
    }

    public void setPersonalityType(String personalityType) {
        this.personalityType = personalityType;
    }


    @Override
    public String getSummary() {
        return id + " | " + name
                + " | Role=" + preferredRole
                + " | Game=" + preferredGame
                + " | Skill=" + skillRating
                + " | Type=" + personalityType;
    }

    @Override
    public String toString() {
        return getSummary();
    }
}
