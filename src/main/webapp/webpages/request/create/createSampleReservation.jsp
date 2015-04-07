<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:useActionBean var="createSampleQuestionBean" beanclass="cz.bbmri.action.request.CreateSampleQuestion"/>
<stripes:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="project"
                 ternarymenu="">

    <stripes:layout-component name="body">

        <fieldset>
        <fieldset>
            <legend><format:message key="cz.bbmri.action.request.CreateSampleQuestion.createSampleReservation"/></legend>

            <stripes:form beanclass="cz.bbmri.action.request.CreateSampleQuestion" class="form-horizontal">

                <stripes:layout-render name="/webpages/request/create/createSampleQuestionForm.jsp"/>

                <div class="form-actions">
                    <stripes:link beanclass="cz.bbmri.action.reservation.ReservationActionBean"
                            event="all" class="btn btn-inverse btnMargin">
                        <format:message key="cancel"/>
                    </stripes:link>

                    <security:allowed bean="createSampleQuestionBean" event="confirmSampleReservation">
                        <stripes:submit name="confirmSampleReservation" class="btn btn-primary"/>
                    </security:allowed>
                </div>
            </stripes:form>

        </fieldset>

    </stripes:layout-component>
</stripes:layout-render>
