<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>


<stripes:layout-definition>
    <stripes:useActionBean var="biobankBean" beanclass="cz.bbmri.action.biobank.BiobankActionBean"/>

    <table class="table table-hover table-striped">

        <stripes:layout-render name="/webpages/component/detail/sample/header.jsp"/>

        <tbody>

        <stripes:layout-render name="/webpages/component/detail/empty/emptyTable.jsp"
                         collection="${samples}"/>
        <core:forEach items="${samples}" var="sample">
            <tr>
                <stripes:layout-render name="/webpages/component/detail/sample/row.jsp" record="${sample}"/>

                <td class="action">
                    <div class="tableAction">
                        <stripes:link beanclass="cz.bbmri.action.sample.SampleActionBean" event="detail"
                                class="btn btn-info">
                            <stripes:param name="biobankId" value="${biobankBean.biobankId}"/>
                            <stripes:param name="sampleId" value="${sample.id}"/>
                            <format:message key="detail"/>
                        </stripes:link>
                    </div>
                </td>
            </tr>
        </core:forEach>
        </tbody>
    </table>

</stripes:layout-definition>