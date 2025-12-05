import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Team {

    private String teamName;
    private final List<Participant> members = new ArrayList<>();

    public Team(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamName() {
        return teamName;
    }

    public List<Participant> getMembers() {
        return Collections.unmodifiableList(members);
    }

    public int getSize() {
        return members.size();
    }

    public void addMember(Participant participant) {
        if (participant != null) {
            members.add(participant);
        }
    }



    public boolean hasLeader() {
        for (Participant p : members) {
            if ("LEADER".equalsIgnoreCase(p.getPersonalityType())) {
                return true;
            }
        }
        return false;
    }

    public int countByPersonalityType(String type) {
        if (type == null) return 0;
        int count = 0;
        for (Participant p : members) {
            if (p.getPersonalityType() != null &&
                    p.getPersonalityType().equalsIgnoreCase(type)) {
                count++;
            }
        }
        return count;
    }



    public int countByGame(String game) {
        if (game == null) return 0;
        int count = 0;
        for (Participant p : members) {
            if (p.getPreferredGame() != null &&
                    p.getPreferredGame().equalsIgnoreCase(game)) {
                count++;
            }
        }
        return count;
    }


    public boolean hasRole(String role) {
        if (role == null) return false;
        for (Participant p : members) {
            if (p.getPreferredRole() != null &&
                    p.getPreferredRole().equalsIgnoreCase(role)) {
                return true;
            }
        }
        return false;
    }

    public int countDistinctRoles() {
        Set<String> roles = new HashSet<>();
        for (Participant p : members) {
            if (p.getPreferredRole() != null && !p.getPreferredRole().isEmpty()) {
                roles.add(p.getPreferredRole().toUpperCase());
            }
        }
        return roles.size();
    }

    public int countRole(String role) {
        if (role == null) return 0;
        int count = 0;
        for (Participant p : members) {
            if (p.getPreferredRole() != null &&
                    p.getPreferredRole().equalsIgnoreCase(role)) {
                count++;
            }
        }
        return count;
    }



    public int getTotalSkill() {
        int sum = 0;
        for (Participant p : members) {
            sum += p.getSkillRating();
        }
        return sum;
    }

    @Override
    public String toString() {
        return teamName + " (members=" + members.size() + ")";
    }

    public int countThinkers() {
        int count = 0;
        for (Participant p : members) {
            if (p.getPersonalityType() != null &&
                    p.getPersonalityType().equalsIgnoreCase("THINKER")) {
                count++;
            }
        }
        return count;
    }

}
