<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <fieldset>
        <legend></legend>

        <div class="form-actions">
            <s:form beanclass="cz.bbmri.action.request.RequestActionBean">
                <s:hidden name="sampleQuestionId" value="${sampleQuestionId}"/>
                <s:hidden name="biobankId" value="${biobankId}"/>

                <c:if test="${actionBean.isSampleQuestionApproved}">
                    <s:link beanclass="cz.bbmri.action.request.CreateRequestsActionBean" event="display"
                            class="btn btn-primary btnMargin">
                        <s:param name="sampleQuestionId" value="${actionBean.sampleQuestionId}"/>
                        <s:param name="biobankId" value="${actionBean.biobankId}"/>
                        <f:message key="cz.bbmri.action.request.CreateRequests.create"/>
                    </s:link>
                </c:if>

                <security:allowed event="changeStateToClosed">
                    <s:submit name="changeStateToClosed" class="btn btn-primary btnMargin"/>
                </security:allowed>

                <c:if test="${actionBean.isSampleRequest}">

                    <security:allowed event="confirmChosenSet">
                        <s:submit name="confirmChosenSet" class="btn btn-primary btnMargin"/>
                    </security:allowed>

                    <security:allowed event="denyChosenSet">
                        <s:submit name="denyChosenSet" class="btn btn-primary btnMargin"/>
                    </security:allowed>

                </c:if>

                <c:if test="${actionBean.isSampleReservation}">

                    <security:allowed event="assignToProjectResolution">
                        <s:link beanclass="cz.bbmri.action.request.RequestActionBean"
                                event="assignToProjectResolution"
                                class="btn btn-primary btnMargin">
                            <s:param name="sampleQuestionId" value="${actionBean.sampleQuestionId}"/>
                            <f:message key="cz.bbmri.action.request.RequestActionBean.assignToProject"/>
                        </s:link>
                    </security:allowed>

                </c:if>

                <security:allowed event="setAsDelivered">
                    <s:submit name="setAsDelivered" class="btn btn-primary btnMargin"/>
                </security:allowed>

                <security:allowed event="exportSampleList">
                    <s:link beanclass="cz.bbmri.action.request.RequestActionBean" event="exportSampleList"
                            class="btn btn-primary btnMargin" target="_blank">

                        <s:param name="sampleQuestionId" value="${actionBean.sampleQuestionId}"/>
                        <s:param name="biobankId" value="${actionBean.biobankId}"/>

                        <f:message key="cz.bbmri.action.request.RequestActionBean.export"/>
                    </s:link>
                </security:allowed>

            </s:form>
        </div>

        <table class="table table-hover table-striped">

            <s:layout-render name="/webpages/component/detail/request/header.jsp"/>

            <tbody>

            <s:layout-render name="/webpages/component/detail/empty/emptyTable.jsp"
                             collection="${actionBean.requests}"/>

            <c:forEach items="${actionBean.requests}" var="request">
                <s:form beanclass="cz.bbmri.action.request.RequestActionBean">
                    <tr>
                        <td>${request.sample.sampleIdentification.sampleId}</td>
                        <td>${request.sample.materialType.type}</td>
                        <td class="action">
                                ${request.numOfRequested} / ${request.sample.sampleNos.availableSamplesNo}


                            <s:hidden name="sampleQuestionId" value="${sampleQuestionId}"/>
                            <s:hidden name="requestId" value="${request.id}"/>
                            <s:hidden name="biobankId" value="${biobankId}"/>

                            <security:allowed event="decreaseAmount">
                                <s:submit name="decreaseAmount" class="btn btn-default btnMargin"/>
                            </security:allowed>
                            <security:allowed event="increaseAmount">
                                <s:submit name="increaseAmount" class="btn btn-default btnMargin"/>
                            </security:allowed>

                        </td>

                        <td class="action">
                            <security:allowed event="removeRequest">
                                <s:submit name="removeRequest" class="btn btn-danger"/>
                            </security:allowed>
                        </td>
                    </tr>
                </s:form>

            </c:forEach>
            </tbody>
        </table>

    </fieldset>

</s:layout-definition>
