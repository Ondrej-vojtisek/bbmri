<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:useActionBean beanclass="cz.bbmri.action.QuestionActionBean" var="questionActionBean"/>
<core:set var="questionPagination" value="${actionBean.questionPagination}"/>

<stripes:layout-render name="${component.layout.content}"
                 primarymenu="project">

    <stripes:layout-component name="body">

        <%--Set authProjectId of AuthotizationActionBean to enable security tag--%>
        <core:set target="${questionActionBean}" property="authProjectId" value="${actionBean.id}"/>

        <stripes:layout-render name="${component.menu.project}" active="questions"/>

        <table class="table table-hover table-striped">

            <stripes:layout-render name="${component.header.question}" pagination="${questionPagination}"/>

            <tbody>
            <stripes:layout-render name="${component.table.emptyTable}"
                             collection="${questionPagination.myPageList}"/>

            <core:forEach var="item" items="${questionPagination.myPageList}">
                <tr>
                    <stripes:layout-render name="${component.row.question}" item="${item}"/>

                    <td class="action">

                    </td>

                </tr>
            </core:forEach>
            </tbody>
        </table>

        <stripes:layout-render name="${component.pager}"
                         pagination="${questionPagination}"/>

    </stripes:layout-component>
</stripes:layout-render>