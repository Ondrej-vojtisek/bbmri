<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="project"
                 secondarymenu="reservations">

    <s:layout-component name="body">

        <div class="form-actions">

        </div>

        <table class="table table-hover table-striped">

            <s:layout-render name="/webpages/component/detail/sampleQuestion/header.jsp"/>

            <tbody>
            <s:layout-render name="/webpages/component/detail/empty/emptyTable.jsp"
                             collection="${actionBean.sampleReservations}"/>

            <c:forEach items="${actionBean.sampleReservations}" var="sampleReservation">
                <tr>

                    <s:layout-render name="/webpages/component/detail/sampleQuestion/row.jsp"
                                     sampleQuestion="${sampleReservation}"/>

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