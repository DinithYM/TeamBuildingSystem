import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TeamBuilder {

    private final int teamSize;
    private final int maxSameGamePerTeam = 2;
    private final int maxThinkersPerTeam = 2;
    private final int desiredDistinctRoles = 3;

    public TeamBuilder(int teamSize) {
        if (teamSize < 2) {
            throw new IllegalArgumentException("Team size must be at least 2.");
        }
        this.teamSize = teamSize;
    }

    public List<Team> buildTeams(List<Participant> participants) {

        List<Team> teams = new ArrayList<>();
        if (participants == null || participants.isEmpty()) {
            return teams;
        }


        List<Participant> copy = new ArrayList<>(participants);

        List<Participant> leaders = new ArrayList<>();
        List<Participant> thinkers = new ArrayList<>();
        List<Participant> others  = new ArrayList<>();

        participants.parallelStream().forEach(p -> {
            String type = p.getPersonalityType();
            if (type == null || type.equalsIgnoreCase("UNKNOWN")) {
                return;
            }


            synchronized (leaders) {
                if ("LEADER".equalsIgnoreCase(type)) {
                    leaders.add(p);
                    return;
                }
            }

            synchronized (thinkers) {
                if ("THINKER".equalsIgnoreCase(type)) {
                    thinkers.add(p);
                    return;
                }
            }

            synchronized (others) {
                others.add(p);
            }
        });



        if (leaders.isEmpty() || thinkers.isEmpty()) {
            return teams;
        }


        Comparator<Participant> bySkillDesc =
                (a, b) -> Integer.compare(b.getSkillRating(), a.getSkillRating());

        leaders.sort(bySkillDesc);
        thinkers.sort(bySkillDesc);
        others.sort(bySkillDesc);

        int totalParticipants = copy.size();
        int maxTeamsBySize = (int) Math.floor(totalParticipants / (double) teamSize);


        int teamCount = Math.min(leaders.size(), thinkers.size());
        teamCount = Math.min(teamCount, maxTeamsBySize);

        if (teamCount <= 0) {
            return teams;
        }


        for (int i = 1; i <= teamCount; i++) {
            teams.add(new Team("Team " + i));
        }


        for (int i = 0; i < teamCount; i++) {
            teams.get(i).addMember(leaders.get(i));
        }

        int thinkerIndex = 0;
        for (; thinkerIndex < thinkers.size() && thinkerIndex < teamCount; thinkerIndex++) {
            Participant thinker = thinkers.get(thinkerIndex);
            Team bestTeam = findBestTeamForFirstThinker(thinker, teams);
            if (bestTeam != null) {
                bestTeam.addMember(thinker);
            }
        }


        List<Participant> remaining = new ArrayList<>();


        for (int i = thinkerIndex; i < thinkers.size(); i++) {
            remaining.add(thinkers.get(i));
        }


        remaining.addAll(others);


        remaining.sort(bySkillDesc);

        assignRemaining(remaining, teams);

        return teams;
    }


    private Team findBestTeamForFirstThinker(Participant thinker, List<Team> teams) {
        Team best = null;
        int bestScore = Integer.MAX_VALUE;

        for (Team t : teams) {

            if (t.getSize() >= teamSize) continue;

            if (t.countThinkers() > 0) continue;

            int score = t.getTotalSkill();

            if (score < bestScore) {
                bestScore = score;
                best = t;
            }
        }

        return best;
    }


    private void assignRemaining(List<Participant> remaining, List<Team> teams) {
        for (Participant p : remaining) {
            Team best = chooseBestTeam(p, teams);
            if (best != null) {
                best.addMember(p);
            }

        }
    }

    private Team chooseBestTeam(Participant p, List<Team> teams) {

        Team best = null;
        int bestScore = Integer.MAX_VALUE;

        String game = p.getPreferredGame();
        String role = p.getPreferredRole();
        String type = p.getPersonalityType();

        for (Team t : teams) {


            if (t.getSize() >= teamSize) continue;


            if (game != null && t.countByGame(game) >= maxSameGamePerTeam) continue;


            if ("THINKER".equalsIgnoreCase(type) && t.countThinkers() >= maxThinkersPerTeam) {
                continue;
            }


            int score = t.getTotalSkill();


            if (role != null &&
                    t.countDistinctRoles() < desiredDistinctRoles &&
                    !t.hasRole(role)) {
                score -= 10;
            }


            if ("BALANCED".equalsIgnoreCase(type)) {
                score -= 3;
            }

            if (score < bestScore) {
                bestScore = score;
                best = t;
            }
        }

        return best;
    }
}
