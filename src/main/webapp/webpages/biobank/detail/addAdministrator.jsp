<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="biobank"
                 ternarymenu="administrators">

    <stripes:layout-component name="body">

        <fieldset>
            <stripes:form beanclass="${actionBean.name}" class="form-horizontal">
                <stripes:hidden name="biobankId"/>

                <stripes:layout-render name="/webpages/component/form/userFindInput.jsp"/>

                <div class="form-actions">
                    <stripes:submit name="find" class="btn btn-primary btnMargin"/>
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

                                <stripes:form beanclass="cz.bbmri.action.biobank.BiobankAdministratorsActionBean">
                                    <stripes:select name="permission">
                                        <stripes:options-enumeration enum="cz.bbmri.entity.enumeration.Permission"/>
                                    </stripes:select>

                                    <stripes:hidden name="biobankId"/>
                                    <stripes:hidden name="adminId" value="${user.id}"/>

                                    <stripes:submit name="addAdministrator" class="btn btn-primary"/>
                                </stripes:form>
                                    </span>

                            </td>
                        </tr>
                    </core:forEach>
                    </tbody>
                </table>
            </core:if>
        </fieldset>

    </stripes:layout-component>
</stripes:layout-render>
