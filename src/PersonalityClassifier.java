public class PersonalityClassifier {

    public void classify(Participant participant) {
        if (participant == null) return;

        int scaled = participant.getPersonalityScoreScaled();
        String type = "UNKNOWN";

        if (scaled >= 90 && scaled <= 100) {
            type = "LEADER";
        } else if (scaled >= 70 && scaled <= 89) {
            type = "BALANCED";
        } else if (scaled >= 50 && scaled <= 69) {
            type = "THINKER";
        }

        participant.setPersonalityType(type);
    }

    public void classifyFromRawScore(Participant participant, int rawTotal) {
        if (participant == null) return;
        participant.setPersonalityScore(rawTotal);
        classify(participant);
    }
}
