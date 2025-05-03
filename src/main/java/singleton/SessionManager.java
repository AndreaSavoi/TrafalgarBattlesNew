package singleton;

import users.User;

public class SessionManager {
        private static User currentUser;

        public SessionManager() {
            //costruttore
        }

        public static void setCurrentUser(User user) {
                currentUser = user;
        }

        public static User getCurrentUser() {
                return currentUser;
        }

        public static void clear() {
                currentUser = null;
        }

        @Override
        public String toString() {
            return "UserSession{" +
                    "userName='" + currentUser.getUsername() + '\''+
                    '}';
        }

}
