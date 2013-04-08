<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>


<f:message key="projects.createProject" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.Project.ProjectActionBean"/>
<s:layout-render name="/layout_content.jsp" title="${title}" logged="${ab.loggedUser.name}">

    <s:layout-component name="primary_menu">
        <li class="active"><s:link href="/project_my_projects.jsp" ><f:message key="projects"/></s:link></li>
        <li><s:link href="/biobank_all.jsp" ><f:message key="biobanks"/></s:link></li>
        <c:if test="${ab.loggedUser.administrator}">
            <li><s:link href="/user_all.jsp" ><f:message key="users"/></s:link></li>
        </c:if>
        <c:if test="${ab.loggedUser.administrator}">
            <li><s:link href="/changeAdministrator.jsp" ><f:message key="change_administrator"/></s:link></li>
        </c:if>
    </s:layout-component>

    <s:layout-component name="secondary_menu">
        <li><s:link href="/project_my_projects.jsp"><f:message key="my_projects"/></s:link></li>
        <li class="active"><s:link  beanclass="bbmri.action.Project.ProjectActionBean" event="createInitial"><f:message key="projects.createProject"/></s:link></li>
        <c:if test="${ab.loggedUser.biobank != null}">
            <li><s:link href="/project_approve.jsp"><f:message key="approve_project"/></s:link></li>
        </c:if>
        <li><s:link href="/samples_my_requests.jsp"><f:message key="my_requests"/></s:link></li>
        <c:if test="${ab.loggedUser.biobank != null || ab.loggedUser.administrator}">
        <li><s:link href="/project_all.jsp"><f:message key="all_projects"/></s:link></li>
        </c:if>
    </s:layout-component>

    <s:layout-component name="body">
        <s:form beanclass="bbmri.action.Project.ProjectActionBean">
            <fieldset>
                <legend><f:message key="project_upload_new"/></legend>
                <li>Osoba, která v systému zakládá projekt, je odpovědná za uvedených správných a pravdivých údajů.</li>
                <li>Zadat lze pouze projekty, které byly schváleny etickou komisí instituce podávajícího</li>
                <li>Součástí založení projektu je nahrání naskenovaného formuláře Material Transfer Agreement (MTA), který je ke stažení zde:   <s:link href="data/mta_form.pdf" target="blank"><f:message key="download_mta_form"/></s:link></li>
                <br>

                <br>
            </fieldset>
            <br>
            <s:link href="/project_create.jsp" style="float: right;"><f:message key="continue"/></s:link>
        </s:form>
    </s:layout-component>
</s:layout-render>