<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<f:message key="biobank.create" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.Biobank.BiobankActionBean"/>
<s:layout-render name="/layout_content.jsp" title="${title}" logged="${ab.loggedUser.name}"
                 primarymenu="biobank"
                 biobank="${ab.loggedUser.biobank}"
                 administrator="${ab.loggedUser.administrator}"
                 secondarymenu="biobank_create">

    <s:layout-component name="body">
        <s:form beanclass="bbmri.action.Biobank.BiobankActionBean">
            <fieldset>
                <s:hidden name="project.id"/>
                <legend><f:message key="biobank.create"/></legend>
                <s:errors/>
                <table>
                    <tr>
                        <th><s:label for="z1" name="biobank.name"/></th>
                        <td><s:text id="z1" name="newBiobank.name"/></td>
                    </tr>
                    <tr>
                        <th><s:label for="z2" name="biobank.address"/></th>
                        <td><s:text id="z2" name="newBiobank.address"/></td>
                    </tr>
                </table>

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