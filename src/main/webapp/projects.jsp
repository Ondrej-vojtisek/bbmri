<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>


<s:useActionBean var="ab" beanclass="bbmri.model.ProjectActionBean"/>
<s:layout-render name="/model/design.jsp" title="Projekty" logged="${ab.loggedResearcher.name}">
    <s:layout-component name="body">
        <table border="1">

            <c:forEach items="${ab.projects}" var="z">
                <tr>
                    <td><c:out value="${z.id}"/></td>
                    <td><c:out value="${z.name}"/></td>
                    <td><c:out value="${z.description}"/></td>
                    <td><c:out value="${z.state}"/></td>

                </tr>
            </c:forEach>
        </table>

        <s:form beanclass="bbmri.model.ProjectActionBean">
            <fieldset>
                <legend>Novy projekt</legend>
                <s:errors/>
                <table>
                    <tr>
                        <td><label for="z1">Name</label></td>
                        <td><s:text id="z1" name="project.name"/></td>
                    </tr>
                    <tr>
                        <td><label for="z2">Description</label></td>
                        <td><s:text id="z2" name="project.description"/></td>
                    </tr>
                    <tr>
                        <td><label for="z3">State</label></td>
                        <td><s:text id="z3" name="project.state"/></td>
                    </tr>

                </table>
                <s:submit name="add"><f:message key="add"/></s:submit>
            </fieldset>
        </s:form>

    </s:layout-component>
</s:layout-render>