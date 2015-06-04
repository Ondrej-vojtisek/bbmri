<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:useActionBean var="reservationActionBean" beanclass="cz.bbmri.action.ReservationActionBean"/>
<stripes:useActionBean var="sampleSearchActionBean" beanclass="cz.bbmri.action.SampleSearchActionBean"/>


<core:set var="reservation" value="${actionBean.reservation}"/>
<core:set var="biobankId" value="${actionBean.reservation.biobank.id}"/>

<stripes:layout-render name="${component.layout.content}">

    <stripes:layout-component name="body">

        <table class="table table-bordered table-striped">
            <tbody>
            <tr>
                <th width="30%"><format:message key="id"/></th>
                <td width="70%">${reservation.id}</td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.Biobank.biobank"/></th>
                <td>${reservation.biobank.acronym}</td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.Reservation.created"/></th>
                <td><format:formatDate value="${reservation.created}" type="both"/></td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.Reservation.validation"/></th>
                <td><format:formatDate value="${reservation.validation}" type="both"/></td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.Reservation.lastModification"/></th>
                <td><format:formatDate value="${reservation.lastModification}" type="both"/></td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.Reservation.user"/></th>
                <td>${reservation.user.wholeName}</td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.ReservationState.reservationState"/></th>
                <td><format:message
                        key="cz.bbmri.entity.ReservationState.${reservation.reservationState}"/></td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.Reservation.specification"/></th>
                <td></td>
            </tr>
            <tr>
                <td colspan="2">${reservation.specification}</td>
            </tr>

            </tbody>
        </table>

        <core:set target="${reservationActionBean}" property="authBiobankId"
                  value="${reservation.biobank.id}"/>

        <stripes:form beanclass="cz.bbmri.action.ReservationActionBean" class="form-inline">
            <div class="form-actions">
                <stripes:hidden name="id" value="${reservation.id}"/>

                <core:if test="${reservation.isNew}">

                    <%--Approve reservation--%>

                    <security:allowed bean="reservationActionBean" event="approve">
                        <stripes:submit name="approve" class="btn btn-primary btnMargin"/>
                    </security:allowed>

                    <%--Deny reservation--%>

                    <security:allowed bean="reservationActionBean" event="deny">
                        <stripes:submit name="deny" class="btn btn-danger btnMargin"/>
                    </security:allowed>

                </core:if>

                    <%--Confirm chosen set of samples--%>

                <core:if test="${reservation.isApproved}">
                    <security:allowed bean="reservationActionBean" event="confirm">
                        <stripes:submit name="confirm" class="btn btn-danger btnMargin"/>
                    </security:allowed>

                </core:if>

                    <%--Assign project--%>


                <core:if test="${reservation.isConfirmed}">
                    <security:allowed bean="reservationActionBean" event="assignToProject">
                        <div class="control-group">

                            <stripes:label name="cz.bbmri.entity.Project.project" class="control-label">
                                <format:message key="cz.bbmri.entity.Project.project"/>
                            </stripes:label>
                            <div class="controls">
                                <stripes:select name="projectId" value="0" class="form-control btnMargin">
                                    <stripes:option value="0" label="..."/>
                                    <stripes:options-collection collection="${reservationActionBean.projects}"
                                                                value="id"
                                                                label="name"/>
                                </stripes:select>

                                <stripes:submit name="assignToProject" class="btn btn-danger btnMargin"/>
                            </div>
                        </div>
                    </security:allowed>
                </core:if>

            </div>
        </stripes:form>

        <core:if test="${reservation.isApproved}">
            <div id="requests">
                <%@include file="/webpages/request/component/table.jsp" %>
            </div>
        </core:if>

        <core:if test="${reservation.isConfirmed}">
            <div id="requests">
                <%@include file="/webpages/request/component/table-unmodifiable.jsp" %>
            </div>
        </core:if>

        <core:if test="${reservation.isApproved}">

            <%--Set authBiobankId of AuthotizationActionBean to enable security tag--%>
            <core:set target="${sampleSearchActionBean}" property="authBiobankId"
                      value="${actionBean.reservation.biobank.id}"/>

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