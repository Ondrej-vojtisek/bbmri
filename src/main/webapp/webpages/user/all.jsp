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
                <th>

                    <s:link beanclass="cz.bbmri.action.user.UserActionBean" event="display">

                        <c:if test="${userBean.orderParam eq 'id'}">
                            <c:if test="${userBean.desc}">
                                <i class="icon-chevron-down"></i>
                            </c:if>

                            <c:if test="${not userBean.desc}">
                                <i class="icon-chevron-up"></i>
                            </c:if>
                            <s:param name="desc" value="${not userBean.desc}"/>
                        </c:if>
                        <s:param name="orderParam" value="id"/>
                        <f:message key="cz.bbmri.entities.User.id"/>
                    </s:link>

                </th>

                <th>
                    <s:link beanclass="cz.bbmri.action.user.UserActionBean" event="display">

                        <c:if test="${userBean.orderParam eq 'surname'}">

                            <c:if test="${userBean.desc}">
                                <i class="icon-chevron-down"></i>
                            </c:if>

                            <c:if test="${not userBean.desc}">
                                <i class="icon-chevron-up"></i>
                            </c:if>

                            <s:param name="desc" value="${not userBean.desc}"/>
                        </c:if>

                        <s:param name="orderParam" value="surname"/>
                        <f:message key="cz.bbmri.entities.User.wholeName"/>
                    </s:link>
                </th>
                <th>
                    <s:link beanclass="cz.bbmri.action.user.UserActionBean" event="display">

                        <c:if test="${userBean.orderParam eq 'organization'}">
                            <c:if test="${userBean.desc}">
                                <i class="icon-chevron-down"></i>
                            </c:if>

                            <c:if test="${not userBean.desc}">
                                <i class="icon-chevron-up"></i>
                            </c:if>

                            <s:param name="desc" value="${not userBean.desc}"/>
                        </c:if>

                        <s:param name="orderParam" value="organization"/>
                        <f:message key="cz.bbmri.entities.User.organization"/>
                    </s:link>
                </th>
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
                    <td>${user.id}</td>
                    <td>${user.wholeName}</td>
                    <td>${user.organization}</td>
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