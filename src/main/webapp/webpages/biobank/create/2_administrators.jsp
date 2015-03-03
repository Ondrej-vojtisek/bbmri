<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.biobank.BiobankActionBean.create" var="title"/>

<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="biobank"
                 secondarymenu="biobank_create">

    <s:layout-component name="body">

        <fieldset>
            <legend><f:message key="cz.bbmri.action.biobank.CreateActionBean.secondStep"/></legend>
            <fieldset>
                <s:form beanclass="cz.bbmri.action.biobank.CreateActionBean" class="form-horizontal">

                    <%--<s:layout-render name="/webpages/component/form/userFindInput.jsp"/>--%>

                    <div class="control-group">
                        <s:label for="cz.bbmri.entities.User.name" class="control-label"/>
                        <div class="controls">
                            <s:text name="userFind.name"/>
                        </div>
                    </div>

                    <div class="control-group">
                        <s:label for="cz.bbmri.entities.User.surname" class="control-label"/>
                        <div class="controls">
                            <s:text name="userFind.surname"/>
                        </div>
                    </div>

                    <div class="control-group">
                        <s:label for="cz.bbmri.entities.User.email" class="control-label"/>
                        <div class="controls">
                            <s:text name="userFind.email"/>
                        </div>
                    </div>

                    <div class="form-actions">
                        <s:submit name="find" class="btn btn-primary btnMargin"/>

                        <%--<s:submit name="backFromStep2" class="btn btn-inverse"/>--%>

                    </div>
                </s:form>
            </fieldset>

            </br>

            <fieldset>
                <c:if test="${empty actionBean.results}">
                    <p><f:message key="cz.bbmri.action.FindActionBean.noResults"/></p>
                </c:if>
                <c:if test="${not empty actionBean.results}">
                    <legend><f:message key="cz.bbmri.action.FindActionBean.results"/></legend>
                    <table class="table table-hover table-striped">
                        <s:layout-render name="/webpages/component/detail/user/header.jsp"/>
                        <tbody>
                        <c:forEach var="user" items="${actionBean.results}">
                            <tr>
                                <s:layout-render name="/webpages/component/detail/user/row.jsp" record="${user}"/>
                                <td class="action">
                                    <span class="pull-right">
                                        <s:form beanclass="${actionBean.name}">
                                            <s:hidden name="adminId" value="${user.id}"/>
                                            <s:submit name="confirmStep2" class="btn btn-primary btnMargin"/>
                                        </s:form>
                                    </span>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:if>
            </fieldset>
        </fieldset>
    </s:layout-component>
</s:layout-render>