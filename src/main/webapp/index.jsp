<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="BBMRI_CZ" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.HomepageActionBean"/>
<s:layout-render name="/layout_homepage.jsp" title="${title}">


    <s:layout-component name="body">

        <% ab.redirectHTTP();
            if(ab.isHTTPS()){
        %>
        <jsp:forward page="login.jsp"/>
        <% }
               %>

        <h1>BBMRI</h1>
        <b>
            <p><f:message key="index.BBMRI_CZ"/>, <s:link href="http://www.bbmri.org">www.bbmri.org</s:link></p>
        </b>

        <p class="justify">
            <f:message key="index.about_bbmri"/>
        </p>

    </s:layout-component>

    <s:layout-component name="links">
        <fieldset>
            <legend class="small_legend"><f:message key="index.membership"/></legend>
            <s:link href="http://www.recamo.cz/userfiles/file/BBMRI/AO192_certificate_asof20090911.pdf">AO192_certificate_asof20090911</s:link>
        </fieldset>
        <br>
        <br>
        <fieldset>
            <legend class="small_legend"><f:message key="index.article"/></legend>
            <s:link href="http://www.eonkologie.cz/cs/2012-suppl2/2012-suppl2-holub">http://www.eonkologie.cz/cs/2012-suppl2/2012-suppl2-holub</s:link>
        </fieldset>
        <br>
        <br>
        <fieldset>
            <legend class="small_legend"><f:message key="index.access"/></legend>
            <s:link href="https://index.bbmri.cz">Index BBMRI_CZ</s:link>
        </fieldset>

    </s:layout-component>
</s:layout-render>
