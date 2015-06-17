<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:form beanclass="cz.bbmri.action.SampleSearchActionBean" class="form-horizontal">

    <legend><format:message key="cz.bbmri.entity.Sample.sampleSearch"/></legend>

    <core:if test="${not empty actionBean.reservation}">
        <core:set var="reservationId" value="${actionBean.reservation.id}"/>
    </core:if>
    <core:if test="${empty actionBean.reservation}">
        <core:set var="reservationId" value="${null}"/>
    </core:if>

    <core:if test="${not empty actionBean.withdraw}">
        <core:set var="withdrawId" value="${actionBean.withdraw.id}"/>
    </core:if>
    <core:if test="${empty actionBean.withdraw}">
        <core:set var="withdrawId" value="${null}"/>
    </core:if>

    <core:if test="${not empty actionBean.question}">
        <core:set var="questionId" value="${actionBean.question.id}"/>
    </core:if>
    <core:if test="${empty actionBean.question}">
        <core:set var="questionId" value="${null}"/>
    </core:if>

    <div class="control-group">
        <stripes:label for="cz.bbmri.entity.Sex.sex" class="control-label">
            <b><format:message key="cz.bbmri.entity.Sex.sex"/>:</b>
        </stripes:label>

        <div class="controls">
            <stripes:select id="sexId" name="sexId" value="0" class="form-control btnMargin"
                            onchange="submitSearch('${reservationId}', '${withdrawId}', '${questionId}', '${biobankId}');">
                <stripes:option value="" label="..."/>
                <stripes:options-collection collection="${sampleSearchActionBean.sex}"
                                            value="id"
                                            label="name"/>
            </stripes:select>
        </div>
    </div>


    <div class="control-group">
        <stripes:label for="cz.bbmri.entity.MaterialType.materialType" class="control-label">
            <b><format:message key="cz.bbmri.entity.MaterialType.materialType"/>:</b>
        </stripes:label>

        <div class="controls">
            <stripes:select id="materialTypeId" name="materialTypeId" value="0" class="form-control btnMargin"
                            onchange="submitSearch('${reservationId}', '${withdrawId}', '${questionId}', '${biobankId}');">
                <stripes:option value="" label="..."/>
                <stripes:options-collection collection="${sampleSearchActionBean.materialTypes}"
                                            value="id"
                                            label="name"/>
            </stripes:select>
        </div>
    </div>

    <div class="control-group">
        <stripes:label for="cz.bbmri.entity.Retrieved.retrieved" class="control-label">
            <b><format:message key="cz.bbmri.entity.Retrieved.retrieved"/>:</b>
        </stripes:label>

        <div class="controls">
            <stripes:select id="retrievedId" name="retrievedId" value="0" class="form-control btnMargin"
                            onchange="submitSearch('${reservationId}', '${withdrawId}', '${questionId}', '${biobankId}');">
                <stripes:option value="" label="..."/>
                <stripes:options-collection collection="${sampleSearchActionBean.retrieved}"
                                            value="id"
                                            label="name"/>
            </stripes:select>

        </div>
    </div>

    <div class="control-group">
        <stripes:label for="cz.bbmri.entity.Diagnosis.diagnosis" class="control-label">
            <b><format:message key="cz.bbmri.entity.Diagnosis.diagnosis"/>:</b>
        </stripes:label>

        <div class="controls">
            <stripes:select id="diagnosisKey" name="diagnosisKey" value="0" class="form-control btnMargin"
                            onchange="submitSearch('${reservationId}', '${withdrawId}', '${questionId}', '${biobankId}');">
                <stripes:option value="" label="..."/>
                <stripes:options-collection collection="${sampleSearchActionBean.diagnosis}"/>
            </stripes:select>
        </div>
    </div>

</stripes:form>
