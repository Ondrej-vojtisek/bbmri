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
            <table border="1">
                <tr>
                    <td><f:message key="id"/></td>
                    <td><f:message key="project.name"/></td>
                    <td><f:message key="description"/></td>
                    <td><f:message key="state"/></td>
                </tr>

                <c:forEach items="${ab.projects}" var="z">
                    <tr>
                        <td><c:out value="${z.id}"/></td>
                        <td><c:out value="${z.name}"/></td>
                        <td><c:out value="${z.description}"/></td>
                        <td><c:out value="${z.projectState}"/></td>
                    </tr>
                </c:forEach>
            </table>
        </fieldset>
        <c:choose>
            <c:when test="${ab.loggedResearcher.biobank!=null}">
                <s:useActionBean var="ab" beanclass="bbmri.action.ProjectActionBean"/>
                <s:form beanclass="bbmri.action.ProjectActionBean">

                    <fieldset>
                        <legend><f:message key="project.approve_project"/></legend>
                        <s:select name="projectId">
                            <s:option value=""><f:message key="select_one"/></s:option>
                            <s:options-collection collection="${ab.newProjects}" label="name" value="id"/>
                        </s:select>
                        <s:submit name="approve"><f:message key="project.approve_project"/></s:submit>
                    </fieldset>
                </s:form>

            </c:when>
            <c:otherwise>
               <f:message key="you_dont_have_sufficient_rights"/>
                <br/>
            </c:otherwise>
        </c:choose>

    </s:layout-component>
</s:layout-render>