package cz.bbmri.entities.enumeration;

/**
 * General system role of user.
 * USER - anyone who is authorized to access system (who fullfill authorization requirements to acces)
 * implicit role
 * ADMINISTRATOR - administrator of system. He has access to global settings of BBMRI, can browse everything
 * DEVELOPER - programmer responsible for maintenance of system
 * BIOBANK_OPERATOR - user managing at least one biobank
 * PROJECT_TEAM_WORKER - user working on at leat one project which is upload to system
 * PROJECT_TEAM_MEMBER_CONFIRMED - user working on at least one confirmed project
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public enum SystemRole {
      USER("user"),
      ADMINISTRATOR("administrator"),
      BIOBANK_OPERATOR("biobank_operator"),
      PROJECT_TEAM_MEMBER("project_team_member"),
      PROJECT_TEAM_MEMBER_CONFIRMED("project_team_member_confirmed"),
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
