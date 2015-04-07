<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:useActionBean var="createSampleQuestionBean" beanclass="cz.bbmri.action.request.CreateSampleQuestion"/>
<stripes:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="project"
                 secondarymenu="reservations">

    <stripes:layout-component name="body">

        <div class="form-actions">
            <security:allowed bean="createSampleQuestionBean" event="createSampleReservation">
                <stripes:link beanclass="cz.bbmri.action.request.CreateSampleQuestion" event="createSampleReservation"
                        class="btn btn-primary btnMargin">
                    <format:message key="cz.bbmri.action.request.CreateSampleQuestion.createSampleReservation"/>
                </stripes:link>
            </security:allowed>
        </div>

        <stripes:layout-render name="/webpages/component/detail/sortableTable/table.jsp"
                         pagination="${actionBean.pagination}"
                         componentManager="${actionBean.componentManager}"
                         targetBean="cz.bbmri.action.request.RequestActionBean"
                         eventName="detail"
                         paramName="sampleQuestionId"/>
    </stripes:layout-component>
</stripes:layout-render>