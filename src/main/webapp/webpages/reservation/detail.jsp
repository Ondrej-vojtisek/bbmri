<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:useActionBean var="reservationActionBean" beanclass="cz.bbmri.action.ReservationActionBean"/>
<stripes:useActionBean var="sampleActionBean" beanclass="cz.bbmri.action.SampleActionBean"/>

<stripes:layout-render name="${component.layout.content}">

    <stripes:layout-component name="body">

        <table class="table table-bordered table-striped">
            <tbody>
            <tr>
                <th width="30%"><format:message key="id"/></th>
                <td width="70%">${actionBean.reservation.id}</td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.Biobank.biobank"/></th>
                <td>${actionBean.reservation.biobank.acronym}</td>
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

        <h2><format:message key="cz.bbmri.entity.Request.required"/></h2>
        <table class="table table-hover table-striped">
            <stripes:layout-render name="${component.header.request}"/>

            <tbody>
            <stripes:layout-render name="${component.table.emptyTable}"
                                   collection="${actionBean.reservation.request}"/>
            <core:forEach var="item" items="${actionBean.reservation.request}">

                <tr>
                    <stripes:layout-render name="${component.row.request}" item="${item}"/>
                    <td class="action">
                        <span class="pull-right">
                            <div class="tableAction">
                                <stripes:form beanclass="cz.bbmri.action.RequestActionBean">

                                    <stripes:hidden name="requestId" value="${item.id} "/>

                                    <stripes:submit name="remove" class="btn btn-danger"/>
                                    <stripes:submit name="increase" class="btn btn-default"/>
                                    <stripes:submit name="decrease" class="btn btn-default"/>

                                </stripes:form>
                            </div>
                        </span>
                    </td>
                </tr>
            </core:forEach>
            </tbody>
        </table>

        <%--&lt;%&ndash;Set authProjectId of AuthotizationActionBean to enable security tag&ndash;%&gt;--%>
        <%--<core:set target="${sampleActionBean}" property="biobankId" value="${actionBean.reservation.biobank.id}"/>--%>

        <%--<table class="table table-hover table-striped">--%>
            <%--<stripes:layout-render name="${component.header.sample}"/>--%>

            <%--<tbody>--%>
            <%--<stripes:layout-render name="${component.table.emptyTable}" collection="${sampleActionBean.samples}"/>--%>
            <%--<core:forEach var="item" items="${sampleActionBean.sampleSearch}">--%>
                <%--<tr>--%>
                    <%--<stripes:layout-render name="${component.row.sample}" item="${item}"/>--%>

                    <%--<core:if test="${item.isAvailable}">--%>
                        <%--<td class="action">--%>
                        <%--<span class="pull-right">--%>
                            <%--<div class="tableAction">--%>
                                <%--<stripes:form beanclass="cz.bbmri.action.RequestActionBean">--%>
                                    <%--<stripes:hidden name="sampleId" value="${item.id}"/>--%>
                                    <%--<stripes:hidden name="reservationId" value="${actionBean.reservation.id}"/>--%>

                                    <%--<stripes:submit name="addToReservation" class="btn btn-info"/>--%>

                                <%--</stripes:form>--%>
                            <%--</div>--%>
                        <%--</span>--%>
                        <%--</td>--%>
                    <%--</core:if>--%>

                <%--</tr>--%>
            <%--</core:forEach>--%>
            <%--</tbody>--%>
        <%--</table>--%>

    </stripes:layout-component>

</stripes:layout-render>