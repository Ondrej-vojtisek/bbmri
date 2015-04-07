<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-definition>

    <fieldset>
        <legend></legend>

        <div class="form-actions">
            <stripes:form beanclass="cz.bbmri.action.request.RequestActionBean">
                <stripes:hidden name="sampleQuestionId" value="${sampleQuestionId}"/>
                <stripes:hidden name="biobankId" value="${biobankId}"/>

                <core:if test="${actionBean.isSampleQuestionApproved}">
                    <stripes:link beanclass="cz.bbmri.action.request.CreateRequestsActionBean" event="display"
                            class="btn btn-primary btnMargin">
                        <stripes:param name="sampleQuestionId" value="${actionBean.sampleQuestionId}"/>
                        <stripes:param name="biobankId" value="${actionBean.sampleQuestion.biobank.id}"/>
                        <format:message key="cz.bbmri.action.request.CreateRequests.create"/>
                    </stripes:link>
                </core:if>

                <security:allowed event="changeStateToClosed">
                    <stripes:submit name="changeStateToClosed" class="btn btn-primary btnMargin"/>
                </security:allowed>

                <core:if test="${actionBean.isSampleRequest}">

                    <security:allowed event="confirmChosenSet">
                        <stripes:submit name="confirmChosenSet" class="btn btn-primary btnMargin"/>
                    </security:allowed>

                    <security:allowed event="denyChosenSet">
                        <stripes:submit name="denyChosenSet" class="btn btn-primary btnMargin"/>
                    </security:allowed>

                </core:if>

                <core:if test="${actionBean.isSampleReservation}">

                    <security:allowed event="assignToProjectResolution">
                        <stripes:link beanclass="cz.bbmri.action.request.RequestActionBean"
                                event="assignToProjectResolution"
                                class="btn btn-primary btnMargin">
                            <stripes:param name="sampleQuestionId" value="${actionBean.sampleQuestionId}"/>
                            <format:message key="cz.bbmri.action.request.RequestActionBean.assignToProject"/>
                        </stripes:link>
                    </security:allowed>

                </core:if>

                <security:allowed event="setAsDelivered">
                    <stripes:submit name="setAsDelivered" class="btn btn-primary btnMargin"/>
                </security:allowed>

                <security:allowed event="exportSampleList">
                    <stripes:link beanclass="cz.bbmri.action.request.RequestActionBean" event="exportSampleList"
                            class="btn btn-primary btnMargin" target="_blank">

                        <stripes:param name="sampleQuestionId" value="${actionBean.sampleQuestionId}"/>
                        <stripes:param name="biobankId" value="${actionBean.biobankId}"/>

                        <format:message key="cz.bbmri.action.request.RequestActionBean.export"/>
                    </stripes:link>
                </security:allowed>

            </stripes:form>
        </div>

        <table class="table table-hover table-striped">

            <stripes:layout-render name="/webpages/component/detail/request/header.jsp"/>

            <tbody>

            <stripes:layout-render name="/webpages/component/detail/empty/emptyTable.jsp"
                             collection="${actionBean.sampleQuestion.requests}"/>

            <core:forEach items="${actionBean.sampleQuestion.requests}" var="request">
                <stripes:form beanclass="cz.bbmri.action.request.RequestActionBean">
                    <tr>
                        <td>${request.sample.sampleIdentification.sampleId}</td>
                        <td>${request.sample.materialType.type}</td>
                        <td class="action">
                            <span class="pull-right">
                                ${request.numOfRequested} / ${request.sample.sampleNos.availableSamplesNo}


                            <stripes:hidden name="sampleQuestionId" value="${sampleQuestionId}"/>
                            <stripes:hidden name="requestId" value="${request.id}"/>
                            <stripes:hidden name="biobankId" value="${biobankId}"/>

                            <security:allowed event="decreaseAmount">
                                <stripes:submit name="decreaseAmount" class="btn btn-default btnMargin"/>
                            </security:allowed>
                            <security:allowed event="increaseAmount">
                                <stripes:submit name="increaseAmount" class="btn btn-default btnMargin"/>
                            </security:allowed>
                          </span>
                        </td>

                        <td class="action">
                            <span class="pull-right">
                            <security:allowed event="removeRequest">
                                <stripes:submit name="removeRequest" class="btn btn-danger"/>
                            </security:allowed>
                            </span>
                        </td>
                    </tr>
                </stripes:form>

            </core:forEach>
            </tbody>
        </table>

    </fieldset>

</stripes:layout-definition>
