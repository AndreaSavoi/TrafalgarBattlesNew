package applicationcontrollers;

public class CurrentUser {
        private static String username;

        public CurrentUser() {
            //costruttore
        }

        public static void setUsername(String username) { applicationcontrollers.CurrentUser.username = username; }


        public static String getUser() { return username; }

        @Override
        public String toString() {
            return "UserSession{" +
                    "userName='" + username + '\''+
                    '}';
        }

}
