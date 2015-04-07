<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="biobank"
                 ternarymenu="infrastructure">

    <stripes:layout-component name="body">

        <div class="form-actions">

            <stripes:form beanclass="cz.bbmri.action.infrastructure.MonitoringActionBean" method="POST" class="form-inline">
                <stripes:hidden name="biobankId" value="${actionBean.biobankId}"/>
                <stripes:hidden name="containerId" value="${actionBean.containerId}"/>
                <div class="control-group">
                    <format:message key="cz.bbmri.action.infrastructure.MonitoringActionBean.selectMonitoring"/>

                    <div class="controls">
                        <stripes:select name="monitoringId" class="btnMargin">
                            <stripes:options-collection collection="${actionBean.container.monitorings}" value="id"
                                                  label="description"/>
                        </stripes:select>

                        <stripes:submit name="monitoring" class="btn btn-info"/>

                    </div>
                </div>
            </stripes:form>

        </div>

        <core:if test="${empty actionBean.monitoring.records}">
            <format:message key="cz.bbmri.action.infrastructure.MonitoringActionBean.noRecords"/>
        </core:if>

        <core:if test="${not empty actionBean.monitoring}">

            <%--Graph object--%>

            <div class="demo-container">
                <div id="placeholder" class="demo-placeholder"></div>
            </div>

        </core:if>

    </stripes:layout-component>


    <stripes:layout-component name="script">
        <script type="text/javascript">
            //            $(document).ready(function () {
            //                console.log('Pokus1');
            //
            //                $.ajax({
            //                    url: "/pokus",
            //                    method: 'GET',
            //                    dataType: 'json',
            //                    success: onOutboundReceived
            //                });
            //                function onOutboundReceived(series) {
            //                    console.log('Pokus2');
            //                    console.log(series);
            //
            //                    var length = series.length;
            //                    var finalData = series;
            //
            //                    var options = {
            //                        lines: {
            //                            show: true
            //                        },
            //                        points: {
            //                            show: true
            //                        },
            //                        xaxis: {
            //                            tickDecimals: 0,
            //                            tickSize: 1
            //                        }
            //                    };
            //                    console.log('Pokus3');
            //                    $.plot($("#placeholder"), finalData, options);
            //
            //                    console.log('Pokus4');
            //
            //                }
            //            });

            $(function () {
                var d = [];
                <core:forEach var="record" items="${actionBean.monitoring.records}">

                d.push([${record.time.time}, ${record.value}]);

                </core:forEach>
                console.log(d);
                $.plot($("#placeholder"), [
                    {label: "${actionBean.monitoring.measurementType}" + ":" + "${actionBean.monitoring.name}", data: d}
                ],
                        {
                            xaxis: { mode: "time", timeformat: "%m/%d/%H:%M:%S", labelHeight: 30},
                            yaxis: { labelWidth: 30 }
                        });

                var xaxisLabel = $("<div class='axisLabel xaxisLabel'></div>").text("Time").appendTo($('#placeholder'));

                var yaxisLabel = $("<div class='axisLabel yaxisLabel'></div>")
                        .text("${actionBean.monitoring.measurementType.physicalQuantity}" +
                                " [" +
                                "${actionBean.monitoring.measurementType.unit}" + "]").appendTo($('#placeholder'));
                yaxisLabel.css("margin-top", yaxisLabel.width() / 2 - 20);
            });


        </script>
    </stripes:layout-component>

</stripes:layout-render>