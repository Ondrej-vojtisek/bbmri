<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>


<f:message key="projects.createProject" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.Project.CreateProjectActionBean"/>
<s:layout-render name="/model/design.jsp" title="${title}" logged="${ab.loggedResearcher.name}">
    <s:layout-component name="body">

        <s:form beanclass="bbmri.action.Project.CreateProjectActionBean">
            <fieldset>
                <legend><f:message key="project.add_new"/></legend>
                <%@include file="/form/createProjectForm.jsp" %>
                <s:submit name="create"><f:message key="add"/></s:submit>
            </fieldset>
        </s:form>

    </s:layout-component>
</s:layout-render>