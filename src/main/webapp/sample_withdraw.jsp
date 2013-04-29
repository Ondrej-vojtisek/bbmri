<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<f:message key="sample.withdraw" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.Sample.SampleActionBean"/>
<s:layout-render name="/layout_content.jsp" title="${title}" logged="${ab.loggedUser.name}"
                 primarymenu="biobank"
                 biobank="${ab.loggedUser.biobank}"
                 administrator="${ab.loggedUser.administrator}"
                 secondarymenu="sample_withdraw">

    <s:layout-component name="body">
        <s:form beanclass="bbmri.action.Sample.SampleActionBean">

            <fieldset>
                <legend><f:message key="selection_criteria"/></legend>
                <p>Example: diagnosis "a%", tissueType "aa" -> diagnosis starts with 'a' AND tissueType = "aa"</p>
                <%@include file="/form/createSampleForm.jsp" %>
                <s:submit name="find"><f:message key="find"/></s:submit>
            </fieldset>
            <fieldset>
                <legend><f:message key="withdraw_samples_of_biobank"/></legend>
                <table id="sortableTable">
                    <thead>
                    <tr>
                        <th><s:label name="sample.numOfSamples"/></th>
                        <th><s:label name="sample.numOfAvailable"/></th>
                        <th><s:label name="sample.grading"/></th>
                        <th><s:label name="sample.diagnosis"/></th>
                        <th><s:label name="sample.tissueType"/></th>
                    </tr>
                    </thead>

                    <c:if test="${ab.resultCount == 0}">
                        <tr>
                            <td colspan="8"><s:label name="no_match_found"/></td>
                        </tr>
                    </c:if>
                    <tbody>
                    <c:forEach items="${ab.results}" var="sample">
                    <tr>
                        <td><c:out value="${sample.numOfSamples}"/></td>
                        <td><c:out value="${sample.numOfAvailable}"/></td>
                        <td><c:out value="${sample.grading}"/></td>
                        <td><c:out value="${sample.diagnosis}"/></td>
                        <td><c:out value="${sample.tissueType}"/></td>
                        <td>
                                    <s:text name="selectedSamples" />
                                    <s:checkbox name="selectedSamples" value="${sample.id}"/>
                        </td>
                    </tr>
                    </c:forEach>
                    <tbody>
                </table>
                <s:submit name="withdrawSamples"><f:message key="request_selected"/></s:submit>
                <s:submit name="withdrawSamples2">Pokus</s:submit>
            </fieldset>


        </s:form>
    </s:layout-component>


</s:layout-render>