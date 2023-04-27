/**
 * This is an util class containing some predefined roles in the application, which helps with subject authentication
 * and authorization.
 *
 * @author Tong
 */

package book.demo.java.util;

public class PredefinedRole {
    /*
     * Internal roles.
     */
    public static final String ADMIN_ROLE = "ADMIN";

    public static final String MANAGER_ROLE = "MANAGER";

    public static final String EDITOR_ROLE = "EDITOR";

    /*
     * External roles.
     */
    public static final String READER_ROLE = "READER";

    public static final String WRITER_ROLE = "WRITER";
}
