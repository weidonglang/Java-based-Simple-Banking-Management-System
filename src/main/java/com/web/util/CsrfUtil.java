package com.web.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.SecureRandom;
import java.util.Base64;

public final class CsrfUtil {
    public static final String SESSION_ATTRIBUTE = "csrfToken";
    public static final String PARAMETER_NAME = "csrfToken";

    private static final SecureRandom RANDOM = new SecureRandom();

    private CsrfUtil() {
    }

    public static String getOrCreateToken(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String token = (String) session.getAttribute(SESSION_ATTRIBUTE);
        if (token == null || token.isEmpty()) {
            byte[] bytes = new byte[32];
            RANDOM.nextBytes(bytes);
            token = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
            session.setAttribute(SESSION_ATTRIBUTE, token);
        }
        return token;
    }

    public static boolean isValid(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return false;
        }
        String sessionToken = (String) session.getAttribute(SESSION_ATTRIBUTE);
        String formToken = request.getParameter(PARAMETER_NAME);
        return sessionToken != null && formToken != null && constantTimeEquals(sessionToken, formToken);
    }

    private static boolean constantTimeEquals(String a, String b) {
        int result = a.length() ^ b.length();
        int max = Math.max(a.length(), b.length());
        for (int i = 0; i < max; i++) {
            char ca = i < a.length() ? a.charAt(i) : 0;
            char cb = i < b.length() ? b.charAt(i) : 0;
            result |= ca ^ cb;
        }
        return result == 0;
    }
}
