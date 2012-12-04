<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>


<f:message key="biobank.edit" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.Biobank.EditBiobankActionBean"/>

<s:layout-render name="/model/design.jsp" title="${title}" logged="${ab.loggedUser.name}">
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
                             <f:message key="select_ethical_committee"/>
                        </th>
                        <td>
                               <s:select name="ethicalCommittee.id">
                               <s:option value=""><f:message key="select_one"/></s:option>
                                <s:options-collection collection="${ab.users}" label="name" value="id"/>
                            </s:select>
                        </td>
                        <td>
                            <s:submit name="changeEthicalCommittee"><f:message key="change_biobank_ethical_committee"/></s:submit>
                       </td>

                    </tr>
                    <tr>
                        <th>
                           <f:message key="select_biobank_administrator"/>
                        </th>
                        <td>
                              <s:select name="administrator.id">
                               <s:option value=""><f:message key="select_one"/></s:option>
                                <s:options-collection collection="${ab.users}" label="name" value="id"/>
                            </s:select>
                        </td>
                         <td>
                             <s:submit name="changeAdministrator"><f:message key="change_biobank_administrator"/></s:submit>
                       </td>
                    </tr>


                </table>

            </fieldset>

        </s:form>

    </s:layout-component>
</s:layout-render>