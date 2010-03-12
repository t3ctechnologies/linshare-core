/*
 *    This file is part of Linshare.
 *
 *   Linshare is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Affero General Public License as
 *   published by the Free Software Foundation, either version 3 of
 *   the License, or (at your option) any later version.
 *
 *   Linshare is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Affero General Public License for more details.
 *
 *   You should have received a copy of the GNU Affero General Public
 *   License along with Foobar.  If not, see
 *                                    <http://www.gnu.org/licenses/>.
 *
 *   (c) 2008 Groupe Linagora - http://linagora.org
 *
*/
package org.linagora.linShare.core.domain.entities;

/** Internal user.
 */
public class GroupUser extends User {

    /** Default constructor. */
    private GroupUser() {
        super();
    }

    /** Constructor.
     * @param login login.
     * @param firstName first name.
     * @param lastName last name.
     * @param mail email.
     */
    public GroupUser(String login, String firstName, String lastName, String mail) {
        super(login, firstName, lastName, mail, true,true);
    }

    public UserType getUserType() {
        return UserType.GROUP;
    }

}
