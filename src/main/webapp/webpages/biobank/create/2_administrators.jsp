<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<format:message key="cz.bbmri.action.biobank.BiobankActionBean.create" var="title"/>

<stripes:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="biobank"
                 secondarymenu="biobank_create">

    <stripes:layout-component name="body">

        <fieldset>
            <legend><format:message key="cz.bbmri.action.biobank.CreateActionBean.secondStep"/></legend>
            <fieldset>
                <stripes:form beanclass="cz.bbmri.action.biobank.CreateActionBean" class="form-horizontal">

                    <%--<stripes:layout-render name="/webpages/component/form/userFindInput.jsp"/>--%>

                    <div class="control-group">
                        <stripes:label for="cz.bbmri.entities.User.name" class="control-label"/>
                        <div class="controls">
                            <stripes:text name="userFind.name"/>
                        </div>
                    </div>

                    <div class="control-group">
                        <stripes:label for="cz.bbmri.entities.User.surname" class="control-label"/>
                        <div class="controls">
                            <stripes:text name="userFind.surname"/>
                        </div>
                    </div>

                    <div class="control-group">
                        <stripes:label for="cz.bbmri.entities.User.email" class="control-label"/>
                        <div class="controls">
                            <stripes:text name="userFind.email"/>
                        </div>
                    </div>

                    <div class="form-actions">
                        <stripes:submit name="find" class="btn btn-primary btnMargin"/>

                        <%--<stripes:submit name="backFromStep2" class="btn btn-inverse"/>--%>

                    </div>
                </stripes:form>
            </fieldset>

            </br>

            <fieldset>
                <core:if test="${empty actionBean.results}">
                    <p><format:message key="cz.bbmri.action.FindActionBean.noResults"/></p>
                </core:if>
                <core:if test="${not empty actionBean.results}">
                    <legend><format:message key="cz.bbmri.action.FindActionBean.results"/></legend>
                    <table class="table table-hover table-striped">
                        <stripes:layout-render name="/webpages/component/detail/user/header.jsp"/>
                        <tbody>
                        <core:forEach var="user" items="${actionBean.results}">
                            <tr>
                                <stripes:layout-render name="/webpages/component/detail/user/row.jsp" record="${user}"/>
                                <td class="action">
                                    <span class="pull-right">
                                        <stripes:form beanclass="${actionBean.name}">
                                            <stripes:hidden name="adminId" value="${user.id}"/>
                                            <stripes:submit name="confirmStep2" class="btn btn-primary btnMargin"/>
                                        </stripes:form>
                                    </span>
                                </td>
                            </tr>
                        </core:forEach>
                        </tbody>
                    </table>
                </core:if>
            </fieldset>
        </fieldset>
    </stripes:layout-component>
</stripes:layout-render>