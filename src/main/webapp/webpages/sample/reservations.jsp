<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.sample.SampleActionBean.detail" var="title"/>
<s:useActionBean var="sampleBean" beanclass="cz.bbmri.action.sample.SampleActionBean"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="sample"
                 ternarymenu="reservations">

    <s:layout-component name="body">

        <table class="table table-hover table-striped">

            <s:layout-render name="/webpages/component/detail/sampleQuestion/header.jsp"/>

            <tbody>
            <s:layout-render name="/webpages/component/detail/empty/emptyTable.jsp"
                             collection="${actionBean.reservationsBySample}"/>

            <c:forEach items="${actionBean.reservationsBySample}" var="sampleReservation">
                <tr>

                    <s:layout-render name="/webpages/component/detail/sampleQuestion/row.jsp"
                                     record="${sampleReservation}"/>

                    <td class="action">
                        <s:link beanclass="cz.bbmri.action.request.RequestActionBean" event="detail"
                                class="btn btn-primary">
                            <s:param name="sampleQuestionId" value="${sampleReservation.id}"/>
                            <s:param name="biobankId" value="${sampleReservation.biobank.id}"/>
                            <f:message key="detail"/>
                        </s:link>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </s:layout-component>
</s:layout-render>