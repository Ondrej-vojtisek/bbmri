<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>


<f:message key="biobank.create" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.Biobank.BiobankActionBean"/>
<s:layout-render name="/layout_content.jsp" title="${title}" logged="${ab.loggedUser.name}">

    <s:layout-component name="primary_menu">
        <li><s:link href="/project_my_projects.jsp" ><f:message key="projects"/></s:link></li>
        <li class="active"><s:link href="/biobank_all.jsp" ><f:message key="biobanks"/></s:link></li>
        <c:if test="${ab.loggedUser.administrator}">
            <li><s:link href="/user_all.jsp" ><f:message key="users"/></s:link></li>
        </c:if>
        <c:if test="${ab.loggedUser.administrator}">
            <li><s:link href="/changeAdministrator.jsp" ><f:message key="change_administrator"/></s:link></li>
        </c:if>
    </s:layout-component>

    <s:layout-component name="secondary_menu">
        <li><s:link href="/biobank_all.jsp"><f:message key="biobanks_all"/></s:link></li>
        <c:if test="${ab.loggedUser.administrator}">
            <li class="active"><s:link href="/biobank_create.jsp"><f:message key="biobank_create"/></s:link></li>
        </c:if>
        <c:if test="${ab.loggedUser.biobank != null}">
            <li><s:link href="/sample_approve_request.jsp"><f:message key="approve_sample_request"/></s:link></li>
            <li><s:link href="/sample_create.jsp"><f:message key="sample_create"/></s:link></li>
        </c:if>
    </s:layout-component>

    <s:layout-component name="body">
        <s:form beanclass="bbmri.action.Biobank.BiobankActionBean">
            <fieldset>
                <s:hidden name="project.id"/>
                <legend><f:message key="biobank.create"/></legend>
                <%@include file="/form/createBiobankForm.jsp" %>

                <table>
                    <tr>
                        <th><s:label name="select_biobank_administrator"/></th>
                        <td><s:select name="administrator.id">
                            <s:option value=""><f:message key="select_one"/></s:option>
                            <s:options-collection collection="${ab.nonAdministrators}" label="wholeName" value="id"/>
                        </s:select>
                        </td>
                    </tr>
                </table>
                <s:submit name="create"><f:message key="add"/></s:submit>
            </fieldset>
        </s:form>
    </s:layout-component>
</s:layout-render>