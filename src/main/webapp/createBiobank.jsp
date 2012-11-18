<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>


<f:message key="biobank.create" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.Biobank.CreateBiobankActionBean"/>
<s:layout-render name="/model/design.jsp" title="${title}" logged="${ab.loggedResearcher.name}">
    <s:layout-component name="body">

        <s:form beanclass="bbmri.action.Biobank.CreateBiobankActionBean">
            <fieldset>
                <s:hidden name="project.id"/>
                <legend><f:message key="biobank.create"/></legend>
                <%@include file="/form/createBiobankForm.jsp" %>

                <table>

                    <tr>
                        <th>
                           <f:message key="select_biobank_administrator"/>
                        </th>
                        <td>
                              <s:select name="administrator.id">
                               <s:option value=""><f:message key="select_one"/></s:option>
                                <s:options-collection collection="${ab.researchers}" label="name" value="id"/>
                            </s:select>
                        </td>
                    </tr>

                    <tr>
                        <th>
                             <f:message key="select_ethical_committee"/>
                        </th>
                        <td>
                               <s:select name="ethicalCommittee.id">
                               <s:option value=""><f:message key="select_one"/></s:option>
                                <s:options-collection collection="${ab.researchers}" label="name" value="id"/>
                            </s:select>
                        </td>
                    </tr>


                </table>

                <s:submit name="create"><f:message key="add"/></s:submit>
            </fieldset>
        </s:form>

    </s:layout-component>
</s:layout-render>