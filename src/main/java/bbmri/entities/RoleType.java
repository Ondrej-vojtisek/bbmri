package bbmri.entities;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 23.7.13
 * Time: 9:45
 * To change this template use File | Settings | File Templates.
 */
public enum RoleType {
      USER("user"),
      ADMINISTRATOR("administrator"),
      BIOBANK_OPERATOR("biobank_operator"),
      PROJECT_LEADER("project_leader"),
      DEVELOPER("developer");

      private String state;

      private RoleType(String state) {
          this.state = state;
      }

      @Override
      public String toString() {
          return this.state;
      }
}
