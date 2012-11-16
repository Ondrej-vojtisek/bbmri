<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>


<f:message key="project.all_projects" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.ProjectActionBean"/>
<s:layout-render name="/model/design.jsp" title="${title}" logged="${ab.loggedResearcher.name}">
    <s:layout-component name="body">

        <fieldset>
            <legend><f:message key="project.all_projects"/></legend>
            <s:form beanclass="bbmri.action.ProjectActionBean">
                <table border="1">
                    <tr>
                        <th><s:label name="project.id"/></th>
                        <th><s:label name="project.name"/></th>
                        <th><s:label name="project.description"/></th>
                        <th><s:label name="project.projectState"/></th>
                        <th><s:label name="ask_to_join"/></th>
                    </tr>

                    <c:forEach items="${ab.projects}" var="project">
                        <tr>
                            <td><c:out value="${project.id}"/></td>
                            <td><c:out value="${project.name}"/></td>
                            <td><c:out value="${project.description}"/></td>
                            <td><f:message key="ProjectState.${project.projectState}"/></td>
                            <td><s:link beanclass="bbmri.action.ProjectActionBean" event="join">
                            <s:param name="project.id" value="${project.id}"/><f:message key="join"/></s:link>
                            </td>

                        </tr>
                    </c:forEach>
                </table>
            </s:form>
        </fieldset>

    </s:layout-component>
</s:layout-render>