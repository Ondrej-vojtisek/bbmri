package cz.bbmri.entities.enumeration;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 23.7.13
 * Time: 9:45
 * To change this template use File | Settings | File Templates.
 */

/*
    Security annotation without role specification is not working for example. So that's why there is an implicit role USER;
    @RolesAllowed({"if ${isMyAccount}"})
*/

public enum SystemRole {
      USER("user"),
      ADMINISTRATOR("administrator"),
      BIOBANK_OPERATOR("biobank_operator"),
      PROJECT_TEAM_MEMBER("project_team_member"),
      DEVELOPER("developer");

      private final String state;

      private SystemRole(String state) {
          this.state = state;
      }

      @Override
      public String toString() {
          return this.state;
      }
}
