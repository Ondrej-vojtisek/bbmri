/**
 * "Visual Paradigm: DO NOT MODIFY THIS FILE!"
 * 
 * This is an automatic generated file. It will be regenerated every time 
 * you generate persistence class.
 * 
 * Modifying its content may cause the program not work, or your work may lost.
 */

/**
 * Licensee: Masaryk University
 * License Type: Academic
 */
package cz.bbmri.entity;

import java.io.Serializable;

public class ProjectUser extends CompositeKeyGroup implements Serializable {

	public static final String FOLDER = "projectUser";

    public static final String PROP_PROJECT = "project";
   	public static final String PROP_USER = "user";
   	public static final String PROP_PERMISSION = "permission";

	private Project project;
    private long projectId;
    private User user;
    private long userId;
    private Permission permission;
	
	public void setProjectId(long value) {
		this.projectId = value;
	}
	
	public long getProjectId() {
		return projectId;
	}
	
	public void setUserId(long value) {
		this.userId = value;
	}
	
	public long getUserId() {
		return userId;
	}
	
	public void setProject(Project value) {
		this.project = value;
	}
	
	public Project getProject() {
		return project;
	}
	
	public void setUser(User value) {
		this.user = value;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setPermission(Permission value) {
		this.permission = value;
	}
	
	public Permission getPermission() {
		return permission;
	}
	
    public boolean equals(Object aObj) {
   		if (aObj == this)
   			return true;
   		if (!(aObj instanceof ProjectUser))
   			return false;
   		ProjectUser projectuser = (ProjectUser)aObj;
   		if (getProject() == null) {
   			if (projectuser.getProject() != null)
   				return false;
   		}
   		else if (!getProject().equals(projectuser.getProject()))
   			return false;
   		if (getUser() == null) {
   			if (projectuser.getUser() != null)
   				return false;
   		}
   		else if (!getUser().equals(projectuser.getUser()))
   			return false;
   		return true;
   	}

   	public int hashCode() {
   		int hashcode = 0;
   		if (getProject() != null) {
   			hashcode = hashcode + (int) getProject().getId();
   		}
   		if (getUser() != null) {
   			hashcode = hashcode + (int) getUser().getId();
   		}
   		return hashcode;
   	}
}
