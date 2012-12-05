<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>


<f:message key="biobank.edit" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.Biobank.EditBiobankActionBean"/>

<s:layout-render name="/model/design.jsp" title="${title}" logged="${ab.loggedUser.name}">
    <s:layout-component name="primary_menu">
        <li><s:link href="/project_all.jsp"><f:message key="projects"/></s:link></li>
        <li class="active"><s:link href="/biobank_all.jsp"><f:message key="biobanks"/></s:link></li>
        <c:if test="${ab.loggedUser.administrator}">
            <li><s:link href="/user_all.jsp"><f:message key="users"/></s:link></li>
        </c:if>
        <c:if test="${ab.loggedUser.biobank != null}">
            <li><s:link href="/sample_approve_request.jsp"><f:message key="sample.requests"/></s:link></li>
        </c:if>
        <c:if test="${ab.loggedUser.administrator}">
            <li><s:link href="/changeAdministrator.jsp"><f:message key="change_administrator"/></s:link></li>
        </c:if>
    </s:layout-component>

    <s:layout-component name="secondary_menu">
        <li><s:link href="/biobank_all.jsp"><f:message key="all"/></s:link></li>
        <c:if test="${ab.loggedUser.administrator}">
        <li><s:link href="/biobank_create.jsp"><f:message key="biobank_create"/></s:link></li>
        </c:if>
    </s:layout-component>

    <s:layout-component name="body">

        <s:form beanclass="bbmri.action.Biobank.EditBiobankActionBean">
            <s:hidden name="biobank.id"/>
            <fieldset>
                <legend><f:message key="project.edit"/></legend>
                <%@include file="/form/createBiobankForm.jsp" %>
                <s:submit name="update"><f:message key="save"/></s:submit>
                <table>
                    <tr>
                        <th>
                            <s:label name="select_ethical_committee"/>
                        </th>
                        <td>
                            <s:select name="ethicalCommittee.id">
                                <s:option value=""><f:message key="select_one"/></s:option>
                                <s:options-collection collection="${ab.users}" label="name" value="id"/>
                            </s:select>
                        </td>
                        <td>
                            <s:submit name="changeEthicalCommittee"><f:message
                                    key="change_biobank_ethical_committee"/></s:submit>
                        </td>

                    </tr>
                    <tr>
                        <th>
                            <s:label name="select_biobank_administrator"/>
                        </th>
                        <td>
                            <s:select name="administrator.id">
                                <s:option value=""><f:message key="select_one"/></s:option>
                                <s:options-collection collection="${ab.users}" label="name" value="id"/>
                            </s:select>
                        </td>
                        <td>
                            <s:submit name="changeAdministrator"><f:message
                                    key="change_biobank_administrator"/></s:submit>
                        </td>
                    </tr>


                </table>

            </fieldset>

        </s:form>

    </s:layout-component>
</s:layout-render>
