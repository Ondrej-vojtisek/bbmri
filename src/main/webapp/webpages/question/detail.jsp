<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:useActionBean var="questionActionBean" beanclass="cz.bbmri.action.QuestionActionBean"/>

<stripes:layout-render name="${component.layout.content}">

    <stripes:layout-component name="body">


        <%--Set authProjectId of AuthotizationActionBean to enable security tag--%>
        <core:set target="${actionBean}" property="authBiobankId" value="${actionBean.question.biobank.id}"/>

        <table class="table table-bordered table-striped">
            <tbody>
            <tr>
                <th width="30%"><format:message key="id"/></th>
                <td width="70%">${actionBean.question.id}</td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.Question.created"/></th>
                <td><format:formatDate value="${actionBean.question.created}" type="both"/></td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.Question.lastModification"/></th>
                <td><format:formatDate value="${actionBean.question.lastModification}" type="both"/></td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.Project.project"/></th>
                <td>${actionBean.question.project.name}</td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.Biobank.biobank"/></th>
                <td>${actionBean.question.biobank.name}</td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.QuestionState.questionState"/></th>
                <td><format:message
                        key="cz.bbmri.entity.QuestionState.${actionBean.question.questionState}"/></td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.Question.specification"/></th>
                <td></td>
            </tr>
            <tr>
                <td colspan="2">${actionBean.question.specification}</td>
            </tr>

            </tbody>
        </table>

        <stripes:form beanclass="cz.bbmri.action.QuestionActionBean" class="form-horizontal">
            <div class="form-actions">
                <stripes:hidden name="id" value="${actionBean.question.id}"/>

                <core:if test="${actionBean.question.isNew}">
                    <security:allowed bean="questionActionBean" event="approve">
                        <stripes:submit name="approve" class="btn btn-primary btnMargin"/>
                    </security:allowed>

                    <security:allowed bean="questionActionBean" event="deny">
                        <stripes:submit name="deny" class="btn btn-danger btnMargin"/>
                    </security:allowed>
                </core:if>
            </div>
        </stripes:form>

    </stripes:layout-component>
</stripes:layout-render>