<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:useActionBean var="sampleSearchActionBean" beanclass="cz.bbmri.action.SampleSearchActionBean"/>
<stripes:useActionBean var="requestActionBean" beanclass="cz.bbmri.action.RequestActionBean"/>

<table class="table table-hover table-striped">
    <stripes:layout-render name="${component.header.sample}"/>

    <tbody>
    <stripes:layout-render name="${component.table.emptyTable}" collection="${sampleSearchActionBean.samples}"/>
    <core:forEach var="item" items="${sampleSearchActionBean.samples}">
        <tr>
            <stripes:layout-render name="${component.row.sample}" item="${item}"/>


            <td class="action">
                <core:if test="${item.isAvailable}">
                    <span class="pull-right">
                    <div class="tableAction">

                        <stripes:form beanclass="cz.bbmri.action.SampleSearchActionBean">
                            <stripes:hidden name="sampleId" value="${item.id}"/>

                            <core:if test="${not empty actionBean.reservation}">
                                <stripes:hidden name="reservationId" value="${actionBean.reservation.id}"/>
                                <stripes:hidden name="biobankId" value="${actionBean.reservation.biobank.id}"/>

                                <stripes:submit name="addToReservation" class="btn btn-info"
                                                onclick="return submitAdd(this);"/>
                            </core:if>

                            <core:if test="${not empty actionBean.withdraw}">
                                <stripes:hidden name="withdrawId" value="${actionBean.withdraw.id}"/>
                                <stripes:hidden name="biobankId" value="${actionBean.withdraw.biobank.id}"/>

                                <stripes:submit name="addToWithdraw" class="btn btn-info"
                                                onclick="return submitAdd(this);"/>
                            </core:if>

                            <core:if test="${not empty actionBean.question}">
                                <stripes:hidden name="questionId" value="${actionBean.question.id}"/>
                                <stripes:hidden name="biobankId" value="${actionBean.question.biobank.id}"/>

                                <stripes:submit name="addToQuestion" class="btn btn-info"
                                                onclick="return submitAdd(this);"/>
                            </core:if>


                        </stripes:form>

                    </div>
                    </span>
                </core:if>

            </td>
        </tr>
    </core:forEach>
    </tbody>
</table>
