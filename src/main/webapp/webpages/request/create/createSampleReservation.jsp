<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:useActionBean var="createSampleQuestionBean" beanclass="cz.bbmri.action.request.CreateSampleQuestion"/>
<s:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="project"
                 ternarymenu="">

    <s:layout-component name="body">

        <fieldset>
        <fieldset>
            <legend><f:message key="cz.bbmri.action.request.CreateSampleQuestion.createSampleReservation"/></legend>

            <s:form beanclass="cz.bbmri.action.request.CreateSampleQuestion" class="form-horizontal">

                <s:layout-render name="/webpages/request/create/createSampleQuestionForm.jsp"/>

                <div class="form-actions">
                    <s:link beanclass="cz.bbmri.action.reservation.ReservationActionBean"
                            event="all" class="btn btn-inverse btnMargin">
                        <f:message key="cancel"/>
                    </s:link>

                    <security:allowed bean="createSampleQuestionBean" event="confirmSampleReservation">
                        <s:submit name="confirmSampleReservation" class="btn btn-primary"/>
                    </security:allowed>
                </div>
            </s:form>

        </fieldset>

    </s:layout-component>
</s:layout-render>
