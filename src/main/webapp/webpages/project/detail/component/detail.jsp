<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <c:if test="${editable}">

        <s:form beanclass="${bean.name}">
            <fieldset>
                <legend>Edit</legend>
                <s:hidden name="project.id"/>

                <table>
                    <tr>
                        <th><s:label for="z1" name="project.name"/></th>
                        <td><s:text id="z1" name="project.name"/></td>
                    </tr>
                    <tr>
                        <th><s:label for="z2" name="project.fundingOrganization"/></th>
                        <td><s:text id="z2" name="project.fundingOrganization" readonly="true"/></td>
                    </tr>
                    <tr>
                        <th><s:label for="z3" name="project.approvedBy"/></th>
                        <td><s:text id="z3" name="project.approvedBy" readonly="true"/></td>
                    </tr>
                    <tr>
                        <th><s:label for="z6" name="project.approvalStorage"/></th>
                        <td><s:text id="z6" name="project.approvalStorage" readonly="true"/></td>
                    </tr>

                    <tr>
                        <th><s:label for="z4" name="project.principalInvestigator"/></th>
                        <td><s:text id="z4" name="project.mainInvestigator"/></td>
                    </tr>

                    <tr>
                        <th><s:label for="z5" name="project.homeInstitution"/></th>
                        <td><s:text id="z5" name="project.homeInstitution" readonly="true"/></td>
                    </tr>

                    <tr>
                        <th>
                            <p><s:label for="z7" name="project.annotation"/></p>
                        </th>
                    </tr>

                </table>

                <s:textarea id="z7" name="project.annotation"></s:textarea>
                <s:submit name="update">
                    <s:param name="id" value="${bean.id}"/>
                    <f:message key="save"/></s:submit>
            </fieldset>
        </s:form>
    </c:if>

    <c:if test="${not editable}">
        <fieldset>
            <legend>Detail</legend>

            <table>
                    <tr>
                        <th><s:label for="z1" name="project.name"/></th>
                        <td><s:text id="z1" name="project.name" readonly="true"/></td>
                    </tr>
                    <tr>
                        <th><s:label for="z2" name="project.fundingOrganization"/></th>
                        <td><s:text id="z2" name="project.fundingOrganization" readonly="true"/></td>
                    </tr>
                    <tr>
                        <th><s:label for="z3" name="project.approvedBy"/></th>
                        <td><s:text id="z3" name="project.approvedBy" readonly="true"/></td>
                    </tr>
                    <tr>
                        <th><s:label for="z6" name="project.approvalStorage"/></th>
                        <td><s:text id="z6" name="project.approvalStorage" readonly="true"/></td>
                    </tr>

                    <tr>
                        <th><s:label for="z4" name="project.principalInvestigator"/></th>
                        <td><s:text id="z4" name="project.mainInvestigator" readonly="true"/></td>
                    </tr>
                    <tr>
                        <th><s:label for="z5" name="project.homeInstitution"/></th>
                        <td><s:text id="z5" name="project.homeInstitution" readonly="true"/></td>
                    </tr>

                    <tr>
                    <th>
                        <p><s:label for="z7" name="project.annotation"/></p>
                    </th>
                    </tr>

                <s:textarea id="z7" name="project.annotation" readonly="true"></s:textarea>

                </table>

        </fieldset>
    </c:if>


</s:layout-definition>