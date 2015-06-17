<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<core:set var="withdraw" value="${actionBean.withdraw}"/>
<core:set var="biobankId" value="${withdraw.biobank.id}"/>

<stripes:useActionBean var="sampleSearchActionBean" beanclass="cz.bbmri.action.SampleSearchActionBean"/>
<stripes:useActionBean var="withdrawActionBean" beanclass="cz.bbmri.action.WithdrawActionBean"/>

<stripes:layout-render name="${component.layout.content}"
                       primarymenu="biobank">

    <stripes:layout-component name="body">

        <stripes:layout-render name="${component.menu.biobank}" active="withdraws"/>

        <table class="table table-bordered table-striped">
            <tbody>
            <tr>
                <th width="30%"><format:message key="id"/></th>
                <td width="70%">${withdraw.id}</td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.Biobank.biobank"/></th>
                <td>${withdraw.biobank.acronym}</td>
            </tr>
            <tr>
                <th><format:message key="cz.bbmri.entity.Reservation.created"/></th>
                <td><format:formatDate value="${withdraw.created}" type="both"/></td>
            </tr>
            </tbody>
        </table>


        <div id="requests">
            <%@include file="/webpages/request/component/table.jsp" %>
        </div>

        <%--Set authBiobankId of AuthotizationActionBean to enable security tag--%>
        <core:set target="${sampleSearchActionBean}" property="authBiobankId"
                  value="${biobankId}"/>

        <%@include file="/webpages/sample/component/search.jsp" %>

        <div id="searched_samples">
            <%@include file="/webpages/request/component/samples.jsp" %>
        </div>


    </stripes:layout-component>

    <stripes:layout-component name="script">

        <%--URLs to called actionBeans. URL can be defined directly in javaScript but this is less vulnerable to errors--%>
        <%--due to change in actionBean. Second - if url specified directly (e.g. samplesearch) than it is not working --%>
        <%--on live server because there it should be /auth/samplesearch--%>

        <stripes:url var="sample_search_url" beanclass="cz.bbmri.action.SampleSearchActionBean"/>
        <stripes:url var="request_url" beanclass="cz.bbmri.action.RequestActionBean"/>
        <script type="text/javascript">
            var SAMPLE_SEARCH_URL = '${sample_search_url}';
            var REQUEST_URL = '${request_url}';

        </script>

        <script type="text/javascript" src="${context}/libs/my/sample_search.js"></script>

    </stripes:layout-component>

</stripes:layout-render>
