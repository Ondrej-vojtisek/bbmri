<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:useActionBean var="createSampleQuestionBean" beanclass="cz.bbmri.action.request.CreateSampleQuestion"/>
<s:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="project"
                 secondarymenu="reservations">

    <s:layout-component name="body">

        <div class="form-actions">
            <security:allowed bean="createSampleQuestionBean" event="createSampleReservation">
                <s:link beanclass="cz.bbmri.action.request.CreateSampleQuestion" event="createSampleReservation"
                        class="btn btn-primary btnMargin">
                    <f:message key="cz.bbmri.action.request.CreateSampleQuestion.createSampleReservation"/>
                </s:link>
            </security:allowed>
        </div>

        <s:layout-render name="/webpages/component/detail/sortableTable/table.jsp"
                         pagination="${actionBean.pagination}"
                         componentManager="${actionBean.componentManager}"
                         targetBean="cz.bbmri.action.request.RequestActionBean"
                         eventName="detail"
                         paramName="sampleQuestionId"/>
    </s:layout-component>
</s:layout-render>