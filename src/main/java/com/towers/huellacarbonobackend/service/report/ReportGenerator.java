package com.towers.huellacarbonobackend.service.report;

import com.towers.huellacarbonobackend.dto.CalculateDto;
import com.towers.huellacarbonobackend.dto.StatisticsDataDto;
import com.towers.huellacarbonobackend.dto.StatisticsDto;
import com.towers.huellacarbonobackend.dto.StatisticsReportDataDto;
import com.towers.huellacarbonobackend.exception.ReportGeneratorException;
import com.towers.huellacarbonobackend.service.calculate.CalculoService;
import com.towers.huellacarbonobackend.service.calculate.GraficoService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ReportGenerator {
    private final CalculoService calculoService;
    private final GraficoService graficoService;
    private final ResourceLoader resourceLoader;

    public String generateReport(Long empresa, Integer anio) {
        try {
            //LocalDate date = dateFilter.isBefore(LocalDate.now()) ? dateFilter : dateFilter.minusDays(1);

            Resource reportFile = resourceLoader.getResource("classpath:templates/report/ChcReport.jasper");

            //Resource logo = resourceLoader.getResource("classpath:static/images/logo.png");

            /*ConsumptionDto arConsumption = consumptionService.getConsumption("AR", dateFilter);
            ConsumptionDto cqConsumption = consumptionService.getConsumption("CQ", dateFilter);
            ConsumptionDto dcConsumption = consumptionService.getConsumption("DC", dateFilter);
            ConsumptionDto gqConsumption = consumptionService.getConsumption("GQ", dateFilter);*/

            CalculateDto calculo = calculoService.getCalculos(empresa, anio);
            StatisticsDto grafico = graficoService.getGrafico(empresa, anio);
            /*StatisticsDto cqStatistics = statisticsService.getStatistics("CQ", dateFilter);
            StatisticsDto dcStatistics = statisticsService.getStatistics("DC", dateFilter);
            StatisticsDto gqStatistics = statisticsService.getStatistics("GQ", dateFilter);*/

            JRBeanCollectionDataSource arDataSource = new JRBeanCollectionDataSource(getAnnualStatistics(arStatistics.annualConsumption(), arStatistics.annualConsumptionLastYear()));

            Map<String, Object> reportParameters = new HashMap<>();

            reportParameters.put("logo", logo.getInputStream());

            reportParameters.put("dateFilter", date.toString());

            reportParameters.put("uploadDateOptima", uploadHistoryService.getLastUploadDate("OPTIMA").toLocalDate().toString());
            reportParameters.put("uploadDateDonation", uploadHistoryService.getLastUploadDate("DONATION").toLocalDate().toString());

            reportParameters.put("arDailyConsumption", arConsumption.dailyConsumptionStr());
            reportParameters.put("arWeeklyConsumption", arConsumption.weeklyConsumptionStr());
            reportParameters.put("arMonthlyConsumption", arConsumption.monthlyConsumptionStr());
            reportParameters.put("arAnnualConsumption", arConsumption.annualConsumptionStr());

            reportParameters.put("arCurrentYearLegend", date.getYear());
            reportParameters.put("arLastYearLegend", date.minusYears(1).getYear());

            reportParameters.put("arDataSource", arDataSource);

            reportParameters.put("cqCurrentYearLegend", date.getYear());
            reportParameters.put("cqLastYearLegend", date.minusYears(1).getYear());

            reportParameters.put("cqDataSource", cqDataSource);

            reportParameters.put("dcCurrentYearLegend", date.getYear());
            reportParameters.put("dcLastYearLegend", date.minusYears(1).getYear());

            reportParameters.put("dcDataSource", dcDataSource);

            reportParameters.put("gqCurrentYearLegend", date.getYear());
            reportParameters.put("gqLastYearLegend", date.minusYears(1).getYear());

            reportParameters.put("gqDataSource", gqDataSource);

            JasperReport report = (JasperReport) JRLoader.loadObject(reportFile.getInputStream());

            JasperPrint reportPrint = JasperFillManager.fillReport(report, reportParameters, new JREmptyDataSource());

            byte[] reportPdf = JasperExportManager.exportReportToPdf(reportPrint);

            return Base64.getEncoder().encodeToString(reportPdf);
        } catch (Exception e) {
            throw new ReportGeneratorException("No se puedo generar el reporte. Inténtelo más tarde." + e.getMessage());
        }
    }

    private List<StatisticsReportDataDto> getAnnualStatistics(List<StatisticsDataDto> alcance1, List<StatisticsDataDto> alcance2, List<StatisticsDataDto> alcance3) {
        String[] meses = {"ENE", "FEB", "MAR", "ABR", "MAY", "JUN", "JUL", "AGO", "SEP", "OCT", "NOV", "DIC"};

        List<StatisticsReportDataDto> data = new ArrayList<>();

        for (int i = 0; i < alcance1.size(); i++) {
            data.add(new StatisticsReportDataDto(
                    meses[alcance1.get(i).getTime() - 1],
                    alcance1.get(i).getValue(),
                    meses[alcance2.get(i).getTime() - 1],
                    alcance2.get(i).getValue(),
                    meses[alcance3.get(i).getTime() - 1],
                    alcance3.get(i).getValue()
                    ));
        }

        return data;
    }
}
