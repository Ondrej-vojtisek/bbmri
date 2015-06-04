<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:useActionBean var="questionActionBean" beanclass="cz.bbmri.action.QuestionActionBean"/>
<stripes:useActionBean var="sampleSearchActionBean" beanclass="cz.bbmri.action.SampleSearchActionBean"/>
<stripes:useActionBean var="requestActionBean" beanclass="cz.bbmri.action.RequestActionBean"/>

<core:set var="question" value="${actionBean.question}"/>
<core:set var="biobankId" value="${actionBean.question.biobank.id}"/>

<stripes:layout-render name="${component.layout.content}">

    <stripes:layout-component name="body">


        <%--Set authProjectId of AuthotizationActionBean to enable security tag--%>
        <core:set target="${actionBean}" property="authBiobankId" value="${actionBean.question.biobank.id}"/>

        <table class="table table-bordered table-striped">
            <tbody>
            <tr>
                <th width="30%"><format:message key="id"/></th>
                <td width="70%">${question.id}</td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.Question.created"/></th>
                <td><format:formatDate value="${question.created}" type="both"/></td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.Question.lastModification"/></th>
                <td><format:formatDate value="${question.lastModification}" type="both"/></td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.Project.project"/></th>
                <td>${question.project.name}</td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.Biobank.biobank"/></th>
                <td>${question.biobank.name}</td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.QuestionState.questionState"/></th>
                <td><format:message
                        key="cz.bbmri.entity.QuestionState.${question.questionState}"/></td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.Question.specification"/></th>
                <td></td>
            </tr>
            <tr>
                <td colspan="2">${question.specification}</td>
            </tr>

            </tbody>
        </table>

        <core:set target="${questionActionBean}" property="authBiobankId"
                  value="${question.biobank.id}"/>

        <stripes:form beanclass="cz.bbmri.action.QuestionActionBean" class="form-horizontal">
            <div class="form-actions">
                <stripes:hidden name="id" value="${question.id}"/>

                <core:if test="${question.isNew}">
                    <security:allowed bean="questionActionBean" event="approve">
                        <stripes:submit name="approve" class="btn btn-primary btnMargin"/>
                    </security:allowed>

                    <security:allowed bean="questionActionBean" event="deny">
                        <stripes:submit name="deny" class="btn btn-danger btnMargin"/>
                    </security:allowed>
                </core:if>

                <core:if test="${question.isApproved}">
                    <security:allowed bean="questionActionBean" event="close">
                        <stripes:submit name="close" class="btn btn-danger btnMargin"/>
                    </security:allowed>
                </core:if>

                <core:if test="${question.isClosed}">
                    <security:allowed bean="questionActionBean" event="agree">
                        <stripes:submit name="agree" class="btn btn-danger btnMargin"/>
                    </security:allowed>

                    <security:allowed bean="requestActionBean" event="printRequests">
                        <stripes:link event="printRequests" beanclass="cz.bbmri.action.RequestActionBean" target="_blank"
                                      class="btn btn-info btnMargin">
                            <format:message key="cz.bbmri.entity.Request.export"/>
                            <stripes:param name="questionId" value="${question.id}"/>
                        </stripes:link>
                    </security:allowed>
                </core:if>

                    <%--<core:if test="${question.isClosed}">--%>
                    <%--<security:allowed bean="questionActionBean" event="deliver">--%>
                    <%--<stripes:submit name="deliver" class="btn btn-danger btnMargin"/>--%>
                    <%--</security:allowed>--%>
                    <%--</core:if>--%>
            </div>
        </stripes:form>

        <core:if test="${question.isApproved}">
            <div id="requests">
                <%@include file="/webpages/request/component/table.jsp" %>
            </div>
        </core:if>

        <core:if test="${question.isAgreed or question.isClosed}">
            <div id="requests">
                <%@include file="/webpages/request/component/table-unmodifiable.jsp" %>
            </div>
        </core:if>

        <core:if test="${question.isApproved}">

            <%--Set authBiobankId of AuthotizationActionBean to enable security tag--%>
            <core:set target="${sampleSearchActionBean}" property="authBiobankId"
                      value="${question.biobank.id}"/>

            <%@include file="/webpages/sample/component/search.jsp" %>

            <div id="searched_samples">
                <%@include file="/webpages/request/component/samples.jsp" %>
            </div>

        </core:if>


    </stripes:layout-component>

    <stripes:layout-component name="script">

        <script type="text/javascript" src="${context}/libs/my/sample_search.js"></script>

    </stripes:layout-component>

</stripes:layout-render>