<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="bbmri.action.user.UserActionBean.detail" var="title"/>
<s:useActionBean var="userBean" beanclass="bbmri.action.user.UserActionBean"/>

<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="user"
                 secondarymenu="user_all"
                 ternarymenu="personal_data">
    <s:layout-component name="script">

    </s:layout-component>

    <s:layout-component name="body">

        <s:form beanclass="bbmri.action.user.UserActionBean">
            <s:hidden name="user.id"/>
            <s:hidden name="user.password"/>
                <table>
                    <tr>
                        <th><s:label for="z2" name="user.name"/></th>

                        <td>
                            <security:allowed bean="userBean" event="update">
                                <s:text name="user.name"/>
                            </security:allowed>

                            <security:notAllowed bean="userBean" event="update">
                            <s:text id="z2" name="user.name" readonly="true"/>
                            </security:notAllowed>
                        </td>
                    </tr>
                    <tr>
                        <th><s:label for="z3" name="user.surname"/></th>
                        <td>
                            <security:allowed bean="userBean" event="update">
                                <s:text name="user.surname"/>
                            </security:allowed>

                            <security:notAllowed bean="userBean" event="update">
                            <s:text id="z3" name="user.surname" readonly="true"/>
                            </security:notAllowed>
                        </td>
                    </tr>
                    <tr>
                        <th><s:label for="z4" name="user.created"/></th>
                        <td>
                            <s:text id="z4" name="user.created" readonly="true"/>
                        </td>
                    </tr>
                    <tr>
                        <th><s:label for="z5" name="user.lastLogin"/></th>
                        <td>
                        <s:text id="z5" name="user.lastLogin" readonly="true"/>
                        </td>
                    </tr>

                </table>

                <security:allowed bean="userBean" event="update">
                    <s:submit name="update" class="btn btn-primary"/>
                </security:allowed>
        </s:form>

    </s:layout-component>
</s:layout-render>