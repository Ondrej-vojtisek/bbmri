<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<h2><format:message key="cz.bbmri.entity.Request.required"/></h2>
<table class="table table-hover table-striped">
    <stripes:layout-render name="${component.header.request}"/>


    <%--Initialization of generalized typ requisition which stand for any type which is used to --%>
    <%--make sample allocation--%>

    <core:if test="${not empty actionBean.reservation}">
        <core:set var="requisition" value="${actionBean.reservation}"/>
    </core:if>

    <core:if test="${not empty actionBean.question}">
        <core:set var="requisition" value="${actionBean.question}"/>
    </core:if>

    <core:if test="${not empty actionBean.withdraw}">
        <core:set var="requisition" value="${actionBean.withdraw}"/>
    </core:if>


    <tbody>
    <stripes:layout-render name="${component.table.emptyTable}"
                           collection="${requisition.request}"/>
    <core:forEach var="item" items="${requisition.request}">

        <tr>
            <stripes:layout-render name="${component.row.request}" item="${item}"/>
            <td class="action">
                  <span class="pull-right">
                      <div class="tableAction">
                          <stripes:form beanclass="cz.bbmri.action.RequestActionBean">

                              <stripes:hidden name="requestId" value="${item.id} "/>

                              <stripes:hidden name="biobankId" value="${requisition.biobank.id}"/>

                              <core:if test="${not empty actionBean.reservation}">
                                  <stripes:hidden name="reservationId" value="${requisition.id}"/>
                              </core:if>

                              <core:if test="${not empty actionBean.question}">
                                  <stripes:hidden name="questionId" value="${requisition.id}"/>
                              </core:if>

                              <core:if test="${not empty actionBean.withdraw}">
                                  <stripes:hidden name="withdrawId" value="${requisition.id}"/>
                              </core:if>

                              <stripes:submit name="remove" class="btn btn-danger"
                                              onclick="return submitForm(this);"/>

                              <core:if test="${item.sample.isAvailable}">
                                  <stripes:submit name="increase" class="btn btn-default"
                                                  onclick="return submitForm(this);"/>
                              </core:if>

                              <stripes:submit name="decrease" class="btn btn-default"
                                              onclick="return submitForm(this);"/>

                          </stripes:form>
                      </div>
                  </span>
            </td>
        </tr>
    </core:forEach>
    </tbody>
</table>