<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:useActionBean var="reservationActionBean" beanclass="cz.bbmri.action.ReservationActionBean"/>

<stripes:layout-render name="${component.layout.content}">

    <stripes:layout-component name="body">

        <security:allowed event="add" bean="reservationActionBean">
            <div class="form-actions">

                <stripes:link beanclass="cz.bbmri.action.ReservationActionBean"
                              event="add"
                              class="btn btn-primary">
                    <format:message key="cz.bbmri.entity.Reservation.add"/>
                </stripes:link>

            </div>
        </security:allowed>

        <table class="table table-hover table-striped">
            <stripes:layout-render name="${component.header.reservation}" pagination="${actionBean.pagination}"/>

            <tbody>
            <stripes:layout-render name="${component.table.emptyTable}"
                                   collection="${actionBean.pagination.myPageList}"/>
            <core:forEach var="item" items="${actionBean.pagination.myPageList}">
                <tr>
                    <stripes:layout-render name="${component.row.reservation}" item="${item}"/>
                    <td class="action">
                                  <span class="pull-right">
                                          <div class="tableAction">

                                                  <%--Set reservation to distinguish whether I can access detail --%>
                                              <core:set target="${reservationActionBean}" property="id"
                                                        value="${item.id}"/>

                                              <security:allowed bean="reservationActionBean" event="detail">
                                                  <stripes:link beanclass="cz.bbmri.action.ReservationActionBean"
                                                                event="detail"
                                                                class="btn btn-info btnMargin">
                                                      <stripes:param name="id" value="${item.id}"/>
                                                      <format:message key="detail"/>
                                                  </stripes:link>
                                              </security:allowed>
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