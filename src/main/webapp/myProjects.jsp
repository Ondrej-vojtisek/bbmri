<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>


<f:message key="myProjects.title" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.MyProjectsActionBean"/>
<s:layout-render name="/model/design.jsp" title="${title}" logged="${ab.loggedResearcher.name}">
    <s:layout-component name="body">
        <fieldset>
            <legend><f:message key="projects_which_i_lead"/></legend>
            <table border="1">
                <tr>
                    <th><f:message key="id"/></th>
                    <th><f:message key="project.name"/></th>
                    <th><f:message key="description"/></th>
                    <th><f:message key="state"/></th>
                </tr>
                <c:forEach items="${ab.myProjects}" var="project">
                    <tr>
                        <td><c:out value="${project.id}"/></td>
                        <td><c:out value="${project.name}"/></td>
                        <td><c:out value="${project.description}"/></td>
                        <td><c:out value="${project.projectState}"/></td>
                        <td><s:link beanclass="bbmri.action.MyProjectsActionBean" event="edit">
                            <s:param name="project.id" value="${project.id}"/>edit</s:link>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </fieldset>


    </s:layout-component>
</s:layout-render>