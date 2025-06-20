package util;

public class PrivacyUtil {

    private PrivacyUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String obfuscateEmail(String email) {
        if (email == null || !email.contains("@")) {
            return email;
        }

        int atIndex = email.indexOf("@");
        if (atIndex <= 2) {
            return email;
        }

        String localPart = email.substring(0, atIndex);
        String domainPart = email.substring(atIndex);

        char firstChar = localPart.charAt(0);
        char lastChar = localPart.charAt(localPart.length() - 1);
        String stars = "*".repeat(localPart.length() - 2);

        return firstChar + stars + lastChar + domainPart;
    }
}
