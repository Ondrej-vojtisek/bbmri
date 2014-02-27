<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>



        <table class="table table-hover table-striped">
            <s:layout-render name="/webpages/component/detail/container/header.jsp"/>
            <tbody>

            <s:layout-render name="/webpages/component/detail/empty/emptyTable.jsp"
                                           collection="${actionBean.containers}"/>

            <c:forEach items="${actionBean.containers}" var="container">
                <tr>
                    <s:layout-render name="/webpages/component/detail/container/row.jsp" container="${container}"/>
                    <td class="action">
                        <div class="tableAction">
                            <s:link beanclass="cz.bbmri.action.infrastructure.InfrastructureActionBean"
                                    event="containerDetail"
                                    class="btn btn-primary btnMargin">
                                <s:param name="containerId" value="${container.id}"/>
                                <s:param name="biobankId" value="${actionBean.biobankId}"/>
                                <f:message key="detail"/>
                            </s:link>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>



</s:layout-definition>