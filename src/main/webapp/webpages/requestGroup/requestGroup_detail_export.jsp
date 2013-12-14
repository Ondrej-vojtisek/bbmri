<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:useActionBean var="ab" beanclass="cz.bbmri.action.requestGroup.RequestGroupDetailActionBean"/>
<s:layout-render name="/layout_empty.jsp">

    <s:layout-component name="body">
        <s:form beanclass="cz.bbmri.action.requestGroup.RequestGroupDetailActionBean">
            <fieldset>
                <legend><f:message key="Request.group.detail"/></legend>
                <table>
                    <tr>
                        <th><s:label for="z1" name="project.name"/></th>
                        <td><s:text id="z1" readonly="true" name="requestGroup.project.name"/></td>
                    </tr>
                    <tr>
                        <th><s:label for="z2" name="project.homeInstitution"/></th>
                        <td><s:text id="z2" readonly="true" name="requestGroup.project.homeInstitution"/></td>
                    </tr>
                    <tr>
                        <th><s:label for="z3" name="project.mainInvestigator"/></th>
                        <td><s:text id="z3" readonly="true" name="requestGroup.project.mainInvestigator"/></td>
                    </tr>
                    <tr>
                        <th><s:label for="z4" name="project.approvedBy"/></th>
                        <td><s:text id="z4" readonly="true" name="requestGroup.project.approvedBy"/></td>
                    </tr>
                    <tr>
                        <th><s:label for="z5" name="project.owner"/></th>
                        <td><s:text id="z5" readonly="true" name="requestGroup.project.owner.wholeName"/></td>
                    </tr>
                </table>
            </fieldset>

            <fieldset>
                <table>
                    <thead>
                    <tr>
                        <th><s:label name="request.numOfRequested"/></th>
                        <th><s:label name="sample.sampleID"/></th>
                        <th><s:label name="sample.tissueType"/></th>
                        <th><s:label name="sample.biopticalReportYear"/></th>
                        <th><s:label name="sample.biopticalReportNumber"/></th>
                    </tr>
                    </thead>
                    <tbody>

                    <c:if test="${empty ab.requests}">
                        <tr>
                            <td colspan="6"><f:message key="empty"/></td>
                        </tr>
                    </c:if>

                    <c:forEach items="${ab.requests}" var="request" varStatus="loop">
                        <tr>
                            <td><c:out value="${request.numOfRequested}"/></td>
                            <td><c:out value="${request.sample.sampleID}"/></td>
                            <td><c:out value="${request.sample.tissueType}"/></td>
                            <td><c:out value="${request.sample.biopticalReportYear}"/></td>
                            <td><c:out value="${request.sample.biopticalReportNumber}"/></td>

                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </fieldset>


        </s:form>
    </s:layout-component>
</s:layout-render>