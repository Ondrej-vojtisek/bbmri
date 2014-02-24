<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.sample.SampleActionBean.detail" var="title"/>
<s:useActionBean var="sampleBean" beanclass="cz.bbmri.action.sample.SampleActionBean"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="sample"
                 ternarymenu="projects">

    <s:layout-component name="body">

        <table class="table table-hover table-striped">
            <s:layout-render name="/webpages/component/detail/project/header.jsp"/>
            <tbody>
            <c:if test="${empty actionBean.projectsBySample}">
                <tr>
                    <td colspan="5"><f:message key="empty"/></td>
                </tr>
            </c:if>

            <c:forEach items="${actionBean.projectsBySample}" var="project">
                <tr>
                    <s:layout-render name="/webpages/component/detail/project/row.jsp" project="${project}"/>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </s:layout-component>
</s:layout-render>