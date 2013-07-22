<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<f:message key="sample.request" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.SampleQuestionActionBean"/>
<s:layout-render name="/layout_content.jsp" title="${title}" logged="${ab.loggedUser.name}"
                 primarymenu="biobank"
                 biobank="${ab.loggedUser.biobank}"
                 administrator="${ab.loggedUser.administrator}">

    <s:layout-component name="body">
        <s:form beanclass="bbmri.action.SampleQuestionActionBean">
        <table id="sortableTable">
            <thead><tr>
                <th><s:label name="project.name"/></th>
                <th><s:label name="project.owner"/></th>
                <th><s:label name="sampleQuestion.specification"/></th>
            </tr>

            </thead>
            <tbody>

        <c:forEach items="${ab.sampleQuestions}" var="sampleQuestion">
            <tr>
                <td><c:out value="${sampleQuestion.project.name}"/></td>
                <td><c:out value="${sampleQuestion.project.owner}"/></td>
                <td><c:out value="${sampleQuestion.specification}"/></td>
            </tr>
        </c:forEach>
            </tbody>
            </table>

        </s:form>
    </s:layout-component>
</s:layout-render>