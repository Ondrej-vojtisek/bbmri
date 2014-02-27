<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.biobank.BiobankActionBean.detail" var="title"/>
<s:useActionBean var="biobankBean" beanclass="cz.bbmri.action.biobank.BiobankActionBean"/>

<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="biobank"
                 ternarymenu="sampleRequests">

    <s:layout-component name="body">

        <s:layout-component name="body">

            <table class="table table-hover table-striped">

                <s:layout-render name="/webpages/component/detail/sampleQuestion/header.jsp"/>

                <tbody>

                <s:layout-render name="/webpages/component/detail/empty/emptyTable.jsp"
                                                  collection="${biobankBean.sampleRequests}"/>

                <c:forEach items="${biobankBean.sampleRequests}" var="sampleQuestion">
                    <tr>

                        <s:layout-render name="/webpages/component/detail/sampleQuestion/row.jsp" sampleQuestion="${sampleQuestion}"/>

                        <td class="action">
                            <s:link beanclass="cz.bbmri.action.request.RequestActionBean" event="detail"
                                    class="btn btn-primary">
                                <s:param name="sampleQuestionId" value="${sampleQuestion.id}"/>
                                <s:param name="biobankId" value="${biobankBean.biobankId}"/>
                                <f:message key="detail"/>
                            </s:link>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </s:layout-component>

    </s:layout-component>
</s:layout-render>
