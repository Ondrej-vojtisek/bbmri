<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>


<stripes:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="biobank"
                 ternarymenu="samples">

    <stripes:layout-component name="body">

        <stripes:layout-render name="/webpages/component/detail/patient/ribbon.jsp" record="${actionBean.patient}"/>

        <fieldset>
            <stripes:form beanclass="cz.bbmri.action.sample.CreateSampleActionBean" class="form-horizontal">

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entities.Sample.sampleId" class="control-label"/>
                    <div class="controls">
                        <stripes:text name="sample.sampleIdentification.sampleId"/>
                    </div>
                </div>


                <div class="control-group">
                    <stripes:label for="cz.bbmri.entities.Sample.year" class="control-label"/>
                    <div class="controls">
                        <stripes:text name="sample.sampleIdentification.year"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entities.Sample.number" class="control-label"/>
                    <div class="controls">
                        <stripes:text name="sample.sampleIdentification.number"/>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entities.Sample.retrieved" class="control-label"/>
                    <div class="controls">
                        <stripes:select name="sample.retrieved">
                            <stripes:options-enumeration enum="cz.bbmri.entity.enumeration.Retrieved"/>
                        </stripes:select>
                    </div>
                </div>

                <div class="control-group">
                    <stripes:label for="cz.bbmri.entities.Sample.takingDate" class="control-label"/>
                    <div class="controls">
                        <div class="input-append date" id="dp" data-date="1-1-2014" data-date-format="dd-mm-yyyy">
                            <stripes:text name="sample.takingDate" readonly="true" value=""/>
                            <span class="add-on"><i class="icon-calendar"></i></span>
                        </div>
                    </div>
                </div>

                <div class="form-actions">
                    <stripes:submit name="confirmStep1" class="btn btn-primary btnMargin"/>
                </div>
            </stripes:form>

        </fieldset>

    </stripes:layout-component>

    <stripes:layout-component name="script">
           <script type="text/javascript">
               $(function () {
                   $('#dp').datepicker();
               });
           </script>
       </stripes:layout-component>

</stripes:layout-render>