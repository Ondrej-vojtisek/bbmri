<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.user.UserActionBean.all" var="title"/>
<s:useActionBean var="userBean" beanclass="cz.bbmri.action.user.UserActionBean"/>

<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="user"
                 secondarymenu="user_all">

    <s:layout-component name="body">

        <table class="table table-hover table-striped">

            <s:layout-render name="/webpages/component/detail/user/sortableHeader.jsp"/>

            <tbody>

            <s:layout-render name="/webpages/component/detail/empty/emptyTable.jsp"
                             collection="${userBean.users}"/>

            <c:forEach var="user" items="${userBean.users}">
                <tr>
                    <s:layout-render name="/webpages/component/detail/user/row.jsp" user="${user}"/>
                    <td class="action">

                        <c:set target="${userBean}" property="userId" value="${user.id}"/>
                        <security:allowed bean="userBean" event="detail">
                            <div class="tableAction">
                                <s:link beanclass="cz.bbmri.action.user.UserActionBean" event="detail"
                                        class="btn btn-primary btnMargin">
                                    <s:param name="userId" value="${user.id}"/>
                                    <f:message key="detail"/>
                                </s:link>
                            </div>
                        </security:allowed>

                        <security:allowed bean="userBean" event="remove">
                            <s:form beanclass="cz.bbmri.action.user.UserActionBean">

                                <f:message var="question" key="cz.bbmri.action.user.UserActionBean.questionDelete"/>

                                <s:submit name="remove" class="btn btn-danger" onclick="return confirm('${question}')">
                                    <s:param name="userId" value="${user.id}"/>
                                </s:submit>
                            </s:form>
                        </security:allowed>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </s:layout-component>
</s:layout-render>