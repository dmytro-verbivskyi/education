package codewars;

public class CamelCaseConverter {
    public static String toCamelCase(String input) {
        if (input== null || input.isEmpty()) {
            return "";
        }

        String[] parts = input.split("[-_]");
        StringBuilder output = new StringBuilder(parts[0]);

        for (int i = 1; i < parts.length; i++) {
            String p = parts[i];

            if (p.isEmpty()) {
                continue;
            }
            output.append(p.substring(0, 1).toUpperCase());
            if (p.length() != 1) {
                output.append(p.substring(1, p.length()));
            }
        }
        return output.toString();
    }
}
