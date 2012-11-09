<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>


<f:message key="samples.title" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.SampleActionBean"/>
<s:layout-render name="/model/design.jsp" title="${title}" logged="${ab.loggedResearcher.name}">
    <s:layout-component name="body">
        <c:choose>
            <c:when test="${ab.loggedResearcher.projects!=null}">
                <fieldset>
                <legend><f:message key="my_approved_projects"/></legend>
                <table border="1">
                    <tr>
                        <td><f:message key="id"/></td>
                        <td><f:message key="project.name"/></td>
                        <td><f:message key="description"/></td>
                        <td><f:message key="state"/></td>
                    </tr>
                    <c:forEach items="${ab.myApprovedProjects}" var="z">
                        <tr>
                            <td><c:out value="${z.id}"/></td>
                            <td><c:out value="${z.name}"/></td>
                            <td><c:out value="${z.description}"/></td>
                            <td><c:out value="${z.projectState}"/></td>
                        </tr>
                    </c:forEach>
                </table>


                <table border="1">
                    <c:forEach items="${ab.samples}" var="z">
                        <tr>
                            <td><c:out value="${z.TNM}"/></td>
                            <td><c:out value="${z.pTNM}"/></td>
                            <td><c:out value="${z.grading}"/></td>
                        </tr>
                    </c:forEach>
                </table>

                <s:form beanclass="bbmri.action.SampleActionBean">
                    <s:errors/>
                    <s:select name="projectId">
                        <s:option value=""><f:message key="select_from_your_projects"/></s:option>
                        <s:options-collection collection="${ab.myApprovedProjects}" label="name" value="id"/>
                    </s:select>
                    <s:submit name="selectForEdit"><f:message key="select"/></s:submit>
                </s:form>
                <fieldset>


            </c:when>
            <c:otherwise>
                <f:message key="you_dont_have_sufficient_rights"/>
                <br/>
            </c:otherwise>
        </c:choose>
    </s:layout-component>
</s:layout-render>