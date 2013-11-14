<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="sample.request" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.SampleQuestionActionBean"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="biobank">

    <s:layout-component name="body">
        <s:form beanclass="bbmri.action.SampleQuestionActionBean">
            <table cellspacing="0" class="tablesorter">
                <thead>
                <tr>
                    <th><s:label name="project.name"/></th>
                    <th><s:label name="project.owner"/></th>
                    <th><s:label name="sampleQuestion.specification"/></th>
                </tr>

                </thead>
                <tbody>

                <c:if test="${empty ab.sampleQuestions}">
                    <tr>
                        <td colspan="3"><f:message key="empty"/></td>
                    </tr>
                </c:if>

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