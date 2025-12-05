import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvManager {

    // Expected header columns for participants_sample.csv
    // (must match the file you are using)
    private static final String[] EXPECTED_HEADERS = {
            "ID",
            "Name",
            "Email",
            "PreferredGame",
            "SkillLevel",
            "PreferredRole",
            "PersonalityScore",
            "PersonalityType"
    };

    /**
     * Verify that the CSV:
     *  - exists
     *  - is not empty
     *  - has the expected header columns
     */
    public boolean verifyParticipantsCsv(String filePath) {
        File file = new File(filePath);

        // 1) Exists?
        if (!file.exists()) {
            System.out.println("CSV verification failed: file does not exist" + filePath);
            return false;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String header = br.readLine();
            if (header == null || header.trim().isEmpty()) {
                System.out.println("CSV verification failed: file is empty.");
                return false;
            }

            String[] columns = header.split(",");
            if (columns.length < EXPECTED_HEADERS.length) {
                System.out.println("CSV verification failed: Please try again.");
                return false;
            }

            for (int i = 0; i < EXPECTED_HEADERS.length; i++) {
                String actual = columns[i].trim();
                String expected = EXPECTED_HEADERS[i];
                if (!actual.equalsIgnoreCase(expected)) {
                    System.out.println("CSV verification failed: column " + (i + 1)
                            + " should be '" + expected + "' but found '" + actual + "'.");
                    return false;
                }
            }

            // Passed all checks
            return true;

        } catch (IOException e) {
            System.out.println("CSV verification failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Load participants from CSV.
     * Assumes header format:
     * ID,Name,Email,PreferredGame,SkillLevel,PreferredRole,PersonalityScore,PersonalityType
     */
    public List<Participant> loadParticipants(String filePath) throws IOException {

        List<Participant> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line = br.readLine(); // read header
            if (line == null) return list;

            while ((line = br.readLine()) != null) {

                String[] parts = line.split(",");
                if (parts.length < 8) continue; // Not enough columns → skip


                String id       = parts[0].trim();
                String name     = parts[1].trim();
                String email    = parts[2].trim();
                String game     = parts[3].trim();
                String skillStr = parts[4].trim();
                String role     = parts[5].trim();
                String scoreStr = parts[6].trim();
                String type     = parts[7].trim();

                if (id.isEmpty() || name.isEmpty() || game.isEmpty()
                        || skillStr.isEmpty() || role.isEmpty() || type.isEmpty()) {
                    System.out.println("Skipping row due to missing required fields: " + line);
                    continue;
                }

                // Parse skill safely
                int skill;
                try {
                    skill = Integer.parseInt(skillStr);
                } catch (Exception e) {
                    System.out.println("Skipping row due to invalid skill value: " + line);
                    continue;
                }

                // (optional extra rule)
                if (skill < 1 || skill > 10) {
                    System.out.println("Skipping row due to invalid skill range (1–10): " + line);
                    continue;
                }

                Participant p = new Participant(id, name);
                p.setPreferredGame(game);
                p.setPreferredRole(role);
                p.setSkillRating(skill);
                p.setPersonalityType(type.toUpperCase());

                list.add(p);
            }

        }

        return list;
    }

    /**
     * Save formed teams to CSV.
     * Header:
     * TeamName,ID,Name,Game,Role,Skill,Personality
     */
    public void saveTeams(String filePath, List<Team> teams) throws IOException {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {

            bw.write("TeamName,ID,Name,Game,Role,Skill,Personality");
            bw.newLine();

            for (Team team : teams) {
                for (Participant p : team.getMembers()) {
                    bw.write(team.getTeamName() + "," +
                            p.getId() + "," +
                            p.getName() + "," +
                            p.getPreferredGame() + "," +
                            p.getPreferredRole() + "," +
                            p.getSkillRating() + "," +
                            p.getPersonalityType());
                    bw.newLine();
                }
            }
        }
    }
}
