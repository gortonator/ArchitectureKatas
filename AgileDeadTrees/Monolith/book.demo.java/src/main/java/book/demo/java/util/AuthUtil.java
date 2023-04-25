/**
 * The AuthUtil class provides helper methods of authentication.
 *
 * @author Tong
 */

package book.demo.java.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

public class AuthUtil {

    /**
     * This method returns the username of the current subject.
     *
     * @return the username if the current subject is authenticated, otherwise null.
     */
    public static String getAuthenticatedUsername() {
        Subject subject = SecurityUtils.getSubject();
        return subject.isAuthenticated() ? subject.getPrincipal().toString() : null;
    }

}
