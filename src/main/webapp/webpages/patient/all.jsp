<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:useActionBean var="patientActionBean" beanclass="cz.bbmri.action.PatientActionBean"/>

<stripes:layout-render name="${component.layout.content}">

    <stripes:layout-component name="body">

        <table class="table table-hover table-striped">
            <stripes:layout-render name="${component.header.patient}" pagination="${actionBean.pagination}"/>

            <tbody>
            <stripes:layout-render name="${component.table.emptyTable}" collection="${actionBean.pagination.myPageList}"/>
            <core:forEach var="item" items="${actionBean.pagination.myPageList}">

                <%--Set authProjectId of AuthotizationActionBean to enable security tag--%>
                <core:set target="${actionBean}" property="authBiobankId" value="${item.biobank.id}"/>

                <tr>
                    <stripes:layout-render name="${component.row.patient}" item="${item}"/>
                    <td class="action">
                                  <span class="pull-right">
                                          <div class="tableAction">
                                              <%--<security:allowed bean="patientActionBean" event="detail">--%>
                                                  <stripes:link beanclass="cz.bbmri.action.PatientActionBean" event="detail"
                                                          class="btn btn-info btnMargin">
                                                      <stripes:param name="id" value="${item.id}"/>
                                                      <format:message key="detail"/>
                                                  </stripes:link>
                                              <%--</security:allowed>--%>
                                          </div>
                                  </span>
                    </td>
                </tr>
            </core:forEach>
            </tbody>
        </table>

        <%--show pagination only if list contains some data--%>
        <core:if test="${not empty actionBean.pagination.myPageList}">
            <stripes:layout-render name="${component.pager}" pagination="${actionBean.pagination}"/>
        </core:if>

    </stripes:layout-component>

</stripes:layout-render>