<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:useActionBean var="reservationActionBean" beanclass="cz.bbmri.action.ReservationActionBean"/>

<stripes:layout-render name="${component.layout.content}">

    <stripes:layout-component name="body">

        <table class="table table-bordered table-striped">
            <tbody>
            <tr>
                <th width="30%"><format:message key="id"/></th>
                <td width="70%">${actionBean.reservation.id}</td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.Reservation.created"/></th>
                <td><format:formatDate value="${actionBean.reservation.created}" type="both"/></td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.Reservation.validation"/></th>
                <td><format:formatDate value="${actionBean.reservation.validation}" type="both"/></td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.Reservation.lastModification"/></th>
                <td><format:formatDate value="${actionBean.reservation.lastModification}" type="both"/></td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.Reservation.user"/></th>
                <td>${actionBean.reservation.user.wholeName}</td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.ReservationState.reservationState"/></th>
                <td><format:message
                        key="cz.bbmri.entity.ReservationState.${actionBean.reservation.reservationState}"/></td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.Reservation.specification"/></th>
                <td></td>
            </tr>
            <tr>
                <td colspan="2">${actionBean.reservation.specification}</td>
            </tr>

            </tbody>
        </table>

        <stripes:form beanclass="cz.bbmri.action.ReservationActionBean" class="form-horizontal">
            <div class="form-actions">
                <stripes:hidden name="id" value="${actionBean.reservation.id}"/>

                    <core:if test="${actionBean.reservation.isNew}">
                <security:allowed bean="reservationActionBean" event="approve">
                    <stripes:submit name="approve" class="btn btn-primary btnMargin"/>
                </security:allowed>

                <security:allowed bean="reservationActionBean" event="deny">
                    <stripes:submit name="deny" class="btn btn-danger btnMargin"/>
                </security:allowed>
                    </core:if>
            </div>
        </stripes:form>

    </stripes:layout-component>

</stripes:layout-render>