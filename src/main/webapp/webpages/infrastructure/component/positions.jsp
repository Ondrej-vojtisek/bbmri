<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>



        <table class="table table-hover table-striped">
            <s:layout-render name="/webpages/component/detail/position/header.jsp"/>
            <tbody>

            <s:layout-render name="/webpages/component/detail/empty/emptyTable.jsp"
                                           collection="${actionBean.positions}"/>

            <c:forEach items="${actionBean.positions}" var="position">
                <tr>
                    <s:layout-render name="/webpages/component/detail/position/row.jsp" position="${position}"/>
                    <td class="action">
                        <div class="tableAction">
                            <s:link beanclass="cz.bbmri.action.infrastructure.InfrastructureActionBean"
                                    event="boxDetail"
                                    class="btn btn-primary btnMargin">
                                <s:param name="biobankId" value="${actionBean.biobankId}"/>
                                <s:param name="boxId" value="${box.id}"/>
                                <f:message key="detail"/>
                            </s:link>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>


</s:layout-definition>