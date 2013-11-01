<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="biobanks.title" var="title"/>
<s:useActionBean var="biobankBean" beanclass="bbmri.action.biobank.BiobankActionBean"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="biobank"
                 secondarymenu="biobank_all">

    <s:layout-component name="body">
        <fieldset>
            <legend><f:message key="biobanks.listOfBanks"/></legend>
            <table class="table table-hover table-striped">
                <thead>
                <tr>
                    <th><s:label name="biobank.name"/></th>
                    <th><s:label name="biobank.address"/></th>
                    <th class="noSort"><s:label name="actions"/></th>
                </tr>
                </thead>
                <tbody>


                <c:if test="${empty biobankBean.biobanks}">
                    <tr>
                        <td colspan="3"><f:message key="empty"/></td>
                    </tr>
                </c:if>
                <c:forEach items="${biobankBean.biobanks}" var="biobank">
                    <tr>
                        <td><c:out value="${biobank.name}"/></td>
                        <td><c:out value="${biobank.address}"/></td>
                        <td>

                                <%--This is important for the instance based ACL--%>
                            <c:set target="${biobankBean}" property="id" value="${biobank.id}"/>

                            <security:allowed bean="biobankBean" event="edit">
                                <s:link beanclass="bbmri.action.biobank.BiobankActionBean" event="edit">
                                    <s:param name="id" value="${biobank.id}"/><f:message
                                        key="edit"/></s:link>
                            </security:allowed>
                            <security:notAllowed bean="biobankBean" event="edit">
                                <security:allowed bean="biobankBean" event="detail">
                                    <s:link beanclass="bbmri.action.biobank.BiobankActionBean" event="detail">
                                        <s:param name="id" value="${biobank.id}"/><f:message
                                            key="detail"/></s:link>
                                </security:allowed>
                            </security:notAllowed>

                            <security:allowed bean="biobankBean" event="delete">
                                <s:link beanclass="bbmri.action.biobank.BiobankActionBean" event="delete">
                                    <s:param name="id" value="${biobank.id}"/><f:message
                                        key="delete"/></s:link>
                            </security:allowed>

                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </fieldset>
    </s:layout-component>
</s:layout-render>