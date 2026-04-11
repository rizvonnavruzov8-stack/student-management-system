package com.sms.util;

/**
 * 🎓 EXPLANATION: AuthUtil (Access Control)
 * 
 * 1. WHY IT EXISTS:
 *    As per the proposal (Section 4), the system needs role-based access.
 * 
 * 2. LOGIC:
 *    - Admin: Can do everything (Add, Delete, Update).
 *    - User: Can only View and Search.
 */
public class AuthUtil {

    public enum Role {
        ADMIN, USER
    }

    private static Role currentRole = Role.USER;

    public static void login(String username, String password) {
        if (username.equals("admin") && password.equals("admin123")) {
            currentRole = Role.ADMIN;
        } else {
            currentRole = Role.USER;
        }
    }

    public static Role getCurrentRole() {
        return currentRole;
    }

    public static boolean isAdmin() {
        return currentRole == Role.ADMIN;
    }

    public static void logout() {
        currentRole = Role.USER;
    }
}
