<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.user.UserActionBean.all" var="title"/>
<s:useActionBean var="userBean" beanclass="cz.bbmri.action.user.UserActionBean"/>

<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="user"
                 secondarymenu="user_all">

    <s:layout-component name="body">

        <table class="table table-hover table-striped">
            <thead>
            <tr>
                <th><s:label name="id"/></th>
                <th><s:label name="name"/></th>
                <th><s:label name="surname"/></th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${empty userBean.users}">
                <tr>
                    <td colspan="3"><f:message key="empty"/></td>
                </tr>
            </c:if>

            <c:forEach var="user" items="${userBean.users}">
                <tr>
                    <td><c:out value="${user.id}"/></td>
                    <td><c:out value="${user.name}"/></td>
                    <td><c:out value="${user.surname}"/></td>
                    <td class="action">

                        <c:set target="${userBean}" property="id" value="${user.id}"/>
                        <security:allowed bean="userBean" event="update">
                            <div class="tableAction">
                                <s:link beanclass="cz.bbmri.action.user.UserActionBean" event="detail"
                                        class="btn btn-primary btnMargin">
                                    <s:param name="id" value="${user.id}"/>
                                    <f:message key="edit"/>
                                </s:link>
                            </div>
                        </security:allowed>

                        <security:notAllowed bean="userBean" event="update">
                            <security:allowed bean="userBean" event="detail">
                                <div class="tableAction">
                                    <s:link beanclass="cz.bbmri.action.user.UserActionBean"
                                            class="btn btn-info  btnMargin"
                                            event="detail">
                                        <s:param name="id" value="${user.id}"/>
                                        <f:message key="detail"/></s:link>
                                </div>
                            </security:allowed>
                        </security:notAllowed>

                        <security:allowed bean="userBean" event="remove">
                            <s:form beanclass="cz.bbmri.action.user.UserActionBean">

                                <f:message var="question" key="cz.bbmri.action.user.UserActionBean.questionDelete"/>

                                <s:submit name="remove" class="btn btn-danger" onclick="return confirm('${question}')">
                                    <s:param name="id" value="${user.id}"/>
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