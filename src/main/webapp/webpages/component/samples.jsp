<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>


<s:layout-definition>
    <s:useActionBean var="biobankBean" beanclass="cz.bbmri.action.biobank.BiobankActionBean"/>

    <table class="table table-hover table-striped">

        <s:layout-render name="/webpages/component/detail/sample/header.jsp"/>

        <tbody>

        <s:layout-render name="/webpages/component/detail/empty/emptyTable.jsp"
                         collection="${samples}"/>
        <c:forEach items="${samples}" var="sample">
            <tr>
                <s:layout-render name="/webpages/component/detail/sample/row.jsp" sample="${sample}"/>

                <td class="action">
                    <div class="tableAction">
                        <s:link beanclass="cz.bbmri.action.sample.SampleActionBean" event="detail"
                                class="btn btn-info">
                            <s:param name="biobankId" value="${biobankBean.biobankId}"/>
                            <s:param name="sampleId" value="${sample.id}"/>
                            <f:message key="detail"/>
                        </s:link>
                    </div>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</s:layout-definition>