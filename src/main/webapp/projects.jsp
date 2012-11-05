<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>


<s:useActionBean var="ab" beanclass="bbmri.action.ProjectActionBean"/>
<s:layout-render name="/model/design.jsp" title="Projekty" logged="${ab.loggedResearcher.name}">
    <s:layout-component name="body">

        <fieldset>
            <legend>All projects</legend>
            <table border="1">
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
                        <legend>Approve project</legend>
                        <p>This will be done by ethical committee</p>
                        <s:select name="projectId">
                            <s:option value="">Select One</s:option>
                            <s:options-collection collection="${ab.newProjects}" label="name" value="id"/>
                        </s:select>
                        <s:submit name="approve">Approve project</s:submit>
                    </fieldset>
                </s:form>

            </c:when>
            <c:otherwise>
                <p>You don't have administrator's privilegues.</p>
                <br/>
            </c:otherwise>
        </c:choose>

    </s:layout-component>
</s:layout-render>