package com.towers.huellacarbonobackend.service.report;

import com.towers.huellacarbonobackend.dto.*;
import com.towers.huellacarbonobackend.entity.data.Empresa;
import com.towers.huellacarbonobackend.exception.ReportGeneratorException;
import com.towers.huellacarbonobackend.service.calculate.CalculoService;
import com.towers.huellacarbonobackend.service.calculate.GraficoService;
import com.towers.huellacarbonobackend.service.data.EmpresaService;
import com.towers.huellacarbonobackend.service.data.LogoService;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.chart.Charts;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.*;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

@Service
public class ReportGenerator {
    private final CalculoService calculoService;
    private final GraficoService graficoService;
    private final EmpresaService empresaService;
    private final LogoService logoService;

    // Define constant styles
    private final StyleBuilder titleStyle;
    private final StyleBuilder headerStyle;
    private final StyleBuilder normalTextStyle;
    private final StyleBuilder boldTextStyle;

    // Initialize styles in constructor
    public ReportGenerator(CalculoService calculoService, GraficoService graficoService,
                           EmpresaService empresaService, LogoService logoService) {
        this.calculoService = calculoService;
        this.graficoService = graficoService;
        this.empresaService = empresaService;
        this.logoService = logoService;

        // Initialize styles
        this.titleStyle = DynamicReports.stl.style()
                .setFontSize(14)
                .setBold(true)
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setVerticalTextAlignment(VerticalTextAlignment.MIDDLE);

        this.headerStyle = DynamicReports.stl.style()
                .setBackgroundColor(new Color(167, 233, 0))
                .setForegroundColor(Color.WHITE)
                .setFontSize(10)
                .setBold(true)
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setVerticalTextAlignment(VerticalTextAlignment.MIDDLE);

        this.normalTextStyle = DynamicReports.stl.style()
                .setFontSize(12)
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setVerticalTextAlignment(VerticalTextAlignment.MIDDLE);

        this.boldTextStyle = DynamicReports.stl.style()
                .setFontSize(10)
                .setBold(true)
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setVerticalTextAlignment(VerticalTextAlignment.MIDDLE);
    }

    public String generateReport(Long empresaId, Integer anio) {
        try {
            // Get data
            CalculateDto calculo = calculoService.getCalculos(empresaId, anio);
            StatisticsDto grafico = graficoService.getGrafico(empresaId, anio);
            Empresa empresa = empresaService.getById(empresaId);

            // Prepare data sources
            List<StatisticsReportDataDto> annualStatisticsData = getAnnualStatistics(
                    grafico.alcance1(), grafico.alcance2(), grafico.alcance3());
            List<PieData> pieData = List.of(
                    new PieData("Alcance 1 (" + calculo.alcance1PorcentajeStr() + " %)", calculo.alcance1Porcentaje()),
                    new PieData("Alcance 2 (" + calculo.alcance2PorcentajeStr() + " %)", calculo.alcance2Porcentaje()),
                    new PieData("Alcance 3 (" + calculo.alcance3PorcentajeStr() + " %)", calculo.alcance3Porcentaje())
            );

            JRBeanCollectionDataSource graficoDataSource = new JRBeanCollectionDataSource(annualStatisticsData);
            JRBeanCollectionDataSource pieDataSource = new JRBeanCollectionDataSource(pieData);

            // Create report
            JasperReportBuilder report = createReportDesign(
                    logoService.getByEmpresa(empresaId).logoFile(),
                    empresa.getRazonSocial(),
                    anio,
                    calculo,
                    graficoDataSource,
                    pieDataSource
            );

            // Export to PDF
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            report.toPdf(baos);

            return Base64.getEncoder().encodeToString(baos.toByteArray());

        } catch (Exception e) {
            e.printStackTrace();
            throw new ReportGeneratorException("No se pudo generar el reporte. Inténtelo más tarde. " + e.getMessage());
        }
    }

    private JasperReportBuilder createReportDesign(
            String logoBase64,
            String razonSocial,
            Integer anio,
            CalculateDto calculo,
            JRBeanCollectionDataSource graficoDataSource,
            JRBeanCollectionDataSource pieDataSource) {

        JasperReportBuilder report = DynamicReports.report();

        // Set page size and margins
        report.setPageMargin(DynamicReports.margin(20))
                .setPageFormat(PageType.A4);

        // Title section
        report.title(
                Components.image(new ByteArrayInputStream(Base64.getDecoder().decode(logoBase64)))
                        .setFixedDimension(130, 50)
                        .setHorizontalAlignment(HorizontalAlignment.RIGHT),
                Components.text("REPORTE HUELLA DE CARBONO").setStyle(titleStyle).setFixedWidth(555).setFixedHeight(20),
                Components.text(razonSocial).setStyle(normalTextStyle).setFixedWidth(555).setFixedHeight(20),
                Components.text(anio.toString()).setStyle(normalTextStyle).setFixedWidth(555).setFixedHeight(20),

                Components.verticalGap(20),

                // Table header row
                Components.horizontalList(
                        Components.text("Alcances").setStyle(headerStyle).setFixedWidth(185).setFixedHeight(20),
                        Components.text("Emisiones de GEI").setStyle(headerStyle).setFixedWidth(185).setFixedHeight(20),
                        Components.text("Participación General (%)").setStyle(headerStyle).setFixedWidth(185).setFixedHeight(20)
                ),

                // Alcance 1 row
                Components.horizontalList(
                        Components.text("Alcance 1 - Emisiones Directas").setStyle(boldTextStyle).setFixedWidth(185).setFixedHeight(20),
                        Components.text(calculo.alcance1Str()).setStyle(boldTextStyle).setFixedWidth(185).setFixedHeight(20),
                        Components.text(calculo.alcance1PorcentajeStr()).setStyle(boldTextStyle).setFixedWidth(185).setFixedHeight(20)
                ),

                // Alcance 2 row
                Components.horizontalList(
                        Components.text("Alcance 2 - Emisiones Indirectas").setStyle(boldTextStyle).setFixedWidth(185).setFixedHeight(20),
                        Components.text(calculo.alcance2Str()).setStyle(boldTextStyle).setFixedWidth(185).setFixedHeight(20),
                        Components.text(calculo.alcance2PorcentajeStr()).setStyle(boldTextStyle).setFixedWidth(185).setFixedHeight(20)
                ),

                // Alcance 3 row
                Components.horizontalList(
                        Components.text("Alcance 3 - Otras Emisiones").setStyle(boldTextStyle).setFixedWidth(185).setFixedHeight(20),
                        Components.text(calculo.alcance3Str()).setStyle(boldTextStyle).setFixedWidth(185).setFixedHeight(20),
                        Components.text(calculo.alcance3PorcentajeStr()).setStyle(boldTextStyle).setFixedWidth(185).setFixedHeight(20)
                ),

                // Total row
                Components.horizontalList(
                        Components.text("Total").setStyle(boldTextStyle).setFixedWidth(185).setFixedHeight(20),
                        Components.text(calculo.totalStr()).setStyle(boldTextStyle).setFixedWidth(185).setFixedHeight(20),
                        Components.text("100").setStyle(boldTextStyle).setFixedWidth(185).setFixedHeight(20)
                ),

                Components.verticalGap(20),

                // Separator line
                Components.line(),

                Components.verticalGap(20),

                // Line chart title
                Components.text("Evolución Mensual de Emisiones").setStyle(normalTextStyle),

                // Line chart for monthly emissions
                Charts.lineChart()
                        .setDataSource(graficoDataSource)
                        .setCategory(Columns.column("a1Mes", DataTypes.stringType()))
                        .addSerie(
                                Charts.serie(Columns.column("a1Valor", DataTypes.doubleType()))
                                        .setLabel("Alcance 1")
                        )
                        .addSerie(
                                Charts.serie(Columns.column("a2Valor", DataTypes.doubleType()))
                                        .setLabel("Alcance 2")
                        )
                        .addSerie(
                                Charts.serie(Columns.column("a3Valor", DataTypes.doubleType()))
                                        .setLabel("Alcance 3")
                        )
                        .setTitle("Emisiones Mensuales de GEI")
                        .setTitleFont(DynamicReports.stl.font().setFontSize(10))
                        .setShowLegend(true),

                Components.verticalGap(20),

                // Y-axis label
                /*Components.text("Toneladas de GEI")
                        .setStyle(DynamicReports.stl.style().setFontSize(8).setBold(true)
                                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER))
                        .setFixedWidth(100),*/

                // Separator line
                Components.line(),

                Components.verticalGap(20),

                // Pie chart title
                Components.text("Distribución de Emisiones por Alcance").setStyle(normalTextStyle),

                // Pie chart for scope distribution
                Charts.pieChart()
                        .setDataSource(pieDataSource)
                        .setKey(Columns.column("alcance", DataTypes.stringType()))
                        .addSerie(Charts.serie(Columns.column("porcentaje", DataTypes.doubleType())))
                        .setTitle("Distribución por Alcance")
                        .setTitleFont(DynamicReports.stl.font().setFontSize(10))
                        .setShowLegend(true),

                Components.verticalGap(40),

                Components.text("Fecha de Impresión: " + LocalDate.now()).setStyle(boldTextStyle)
        );

        return report;
    }

    private List<StatisticsReportDataDto> getAnnualStatistics(List<StatisticsDataDto> alcance1, List<StatisticsDataDto> alcance2, List<StatisticsDataDto> alcance3) {
        String[] meses = {"ENE", "FEB", "MAR", "ABR", "MAY", "JUN", "JUL", "AGO", "SEP", "OCT", "NOV", "DIC"};

        List<StatisticsReportDataDto> data = new ArrayList<>();

        for (int i = 0; i < alcance1.size(); i++) {
            data.add(new StatisticsReportDataDto(
                    meses[alcance1.get(i).getTime() - 1],
                    meses[alcance2.get(i).getTime() - 1],
                    meses[alcance3.get(i).getTime() - 1],
                    alcance1.get(i).getValue() != null ? alcance1.get(i).getValue() : 0.0,
                    alcance2.get(i).getValue() != null ? alcance2.get(i).getValue() : 0.0,
                    alcance3.get(i).getValue() != null ? alcance3.get(i).getValue() : 0.0
            ));
        }

        return data;
    }
}