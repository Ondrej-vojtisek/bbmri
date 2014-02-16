<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>



        <table class="table table-hover table-striped">
            <s:layout-render name="/webpages/component/detail/box/header.jsp"/>
            <tbody>

            <c:if test="${empty boxes}">
                <tr>
                    <td colspan="6">
                        <f:message key="cz.bbmri.action.infrastructure.InfrastructureActionBean.emptyBoxes"/>
                    </td>
                </tr>
            </c:if>

            <c:forEach items="${boxes}" var="box">
                <tr>
                    <s:layout-render name="/webpages/component/detail/box/row.jsp" box="${box}"/>
                    <td class="action">
                        <div class="tableAction">
                            <s:link beanclass="cz.bbmri.action.infrastructure.InfrastructureActionBean"
                                    event="boxDetail"
                                    class="btn btn-primary btnMargin">
                                <s:param name="biobankId" value="${actionBean.biobankId}"/>
                                <s:param name="containerId" value="${actionBean.containerId}"/>
                                <s:param name="rackId" value="${actionBean.rackId}"/>
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