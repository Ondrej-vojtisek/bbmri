<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:useActionBean beanclass="cz.bbmri.action.QuestionActionBean" var="questionActionBean"/>
<c:set var="questionPagination" value="${actionBean.questionPagination}"/>

<s:layout-render name="${component.layout.content}"
                 primarymenu="project">

    <s:layout-component name="body">

        <s:layout-render name="${component.menu.project}" active="questions"/>

        <table class="table table-hover table-striped">

            <s:layout-render name="${component.sortableHeader.question}" pagination="${questionPagination}"/>

            <tbody>
            <s:layout-render name="${component.table.emptyTable}"
                             collection="${questionPagination.myPageList}"/>

            <c:forEach var="item" items="${questionPagination.myPageList}">
                <tr>
                    <s:layout-render name="${component.row.question}" item="${item}"/>

                    <td class="action">

                    </td>

                </tr>
            </c:forEach>
            </tbody>
        </table>

        <s:layout-render name="${component.pager}"
                         pagination="${questionPagination}"/>

    </s:layout-component>
</s:layout-render>