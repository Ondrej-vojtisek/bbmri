<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>


    <table class="table table-hover table-striped">
        <s:layout-render name="/webpages/component/detail/rack/header.jsp"/>
        <tbody>

        <s:layout-render name="/webpages/component/detail/empty/emptyTable.jsp"
                                       collection="${actionBean.racks}"/>

        <c:forEach items="${actionBean.racks}" var="rack">
            <tr>
                <s:layout-render name="/webpages/component/detail/rack/row.jsp" rack="${rack}"/>
                <td class="action">
                    <div class="tableAction">
                        <s:link beanclass="cz.bbmri.action.infrastructure.InfrastructureActionBean"
                                event="rackDetail"
                                class="btn btn-primary btnMargin">
                            <s:param name="rackId" value="${rack.id}"/>
                            <s:param name="containerId" value="${actionBean.containerId}"/>
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