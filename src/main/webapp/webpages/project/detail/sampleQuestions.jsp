<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.project.ProjectActionBean.createSampleQuestion" var="title"/>
<s:useActionBean var="projectBean" beanclass="cz.bbmri.action.project.ProjectActionBean"/>

<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="project"
                 ternarymenu="sampleQuestions">

    <s:layout-component name="body">

        <table class="table table-hover table-striped">
            <thead>
            <tr>
                <th><f:message key="cz.bbmri.entities.SampleQuestion.created"/></th>
                <th><f:message key="cz.bbmri.entities.SampleQuestion.biobank"/></th>
                <th><f:message key="cz.bbmri.entities.SampleQuestion.requestState"/></th>
                <th><f:message key="cz.bbmri.entities.SampleQuestion.processed"/></th>
                <th><f:message key="cz.bbmri.entities.SampleQuestion.specification"/></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${empty projectBean.sampleQuestions}">
                <tr>
                    <td colspan="6"><f:message key="empty"/></td>
                </tr>
            </c:if>

            <c:forEach items="${projectBean.sampleQuestions}" var="sampleQuestion">
                <tr>
                    <td>${sampleQuestion.created}</td>
                    <td>${sampleQuestion.biobank.name}</td>
                    <td><f:message key="cz.bbmri.entities.enumeration.RequestState.${sampleQuestion.requestState}"/></td>
                    <td>${sampleQuestion.processed}</td>
                    <td>${sampleQuestion.specification}</td>
                    <td class="action">
                        <s:link beanclass="cz.bbmri.action.request.RequestActionBean" event="detail" class="btn btn-primary">
                            <s:param name="sampleQuestionId" value="${sampleQuestion.id}"/>
                            <f:message key="detail"/>
                        </s:link>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </s:layout-component>
</s:layout-render>
