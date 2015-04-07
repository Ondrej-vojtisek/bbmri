<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:useActionBean beanclass="cz.bbmri.action.BiobankActionBean" var="biobankActionBean"/>
<core:set var="pagination" value="${actionBean.patientPagination}"/>

<stripes:layout-render name="${component.layout.content}"
                 primarymenu="biobank">

    <stripes:layout-component name="body">

        <stripes:layout-render name="${component.menu.biobank}" active="patients"/>

        <table class="table table-hover table-striped">
                   <stripes:layout-render name="${component.header.patient}" pagination="${pagination}"/>

                   <tbody>
                   <stripes:layout-render name="${component.table.emptyTable}" collection="${pagination.myPageList}"/>
                   <core:forEach var="item" items="${pagination.myPageList}">
                       <tr>
                           <stripes:layout-render name="${component.row.patient}" item="${item}"/>
                           <td class="action">
                                      <span class="pull-right">
                                              <div class="tableAction">
                                                  <stripes:link beanclass="cz.bbmri.action.PatientActionBean" event="detail"
                                                          class="btn btn-info btnMargin">
                                                      <stripes:param name="id" value="${item.id}"/>
                                                      <format:message key="detail"/>
                                                  </stripes:link>
                                              </div>
                                      </span>
                           </td>
                       </tr>
                   </core:forEach>
                   </tbody>
               </table>

               <%--show pagination only if list contains some data--%>
               <core:if test="${not empty pagination.myPageList}">
                   <stripes:layout-render name="${component.pager}" pagination="${pagination}"/>
               </core:if>


    </stripes:layout-component>
</stripes:layout-render>
