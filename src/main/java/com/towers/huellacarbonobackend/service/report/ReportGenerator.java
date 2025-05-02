package com.towers.huellacarbonobackend.service.report;

import com.towers.huellacarbonobackend.dto.*;
import com.towers.huellacarbonobackend.entity.data.Empresa;
import com.towers.huellacarbonobackend.exception.ReportGeneratorException;
import com.towers.huellacarbonobackend.service.calculate.CalculoService;
import com.towers.huellacarbonobackend.service.calculate.GraficoService;
import com.towers.huellacarbonobackend.service.data.EmpresaService;
import com.towers.huellacarbonobackend.service.data.LogoService;
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
    private final EmpresaService empresaService;
    private final LogoService logoService;
    private final ResourceLoader resourceLoader;

    public String generateReport(Long empresaId, Integer anio) {
        try {
            Resource reportFile = resourceLoader.getResource("classpath:templates/report/HCReport.jasper");

            CalculateDto calculo = calculoService.getCalculos(empresaId, anio);
            StatisticsDto grafico = graficoService.getGrafico(empresaId, anio);
            Empresa empresa = empresaService.getById(empresaId);

            JRBeanCollectionDataSource graficoDataSource =
                    new JRBeanCollectionDataSource(getAnnualStatistics(grafico.alcance1(), grafico.alcance2(), grafico.alcance3()));

            JRBeanCollectionDataSource pieDataSource = new JRBeanCollectionDataSource(
                    List.of(
                            new PieData("Alcance 1", calculo.alcance1Porcentaje()),
                            new PieData("Alcance 2", calculo.alcance2Porcentaje()),
                            new PieData("Alcance 3", calculo.alcance3Porcentaje())
                    )
            );

            // Log para verificar los datos generados
            //System.out.println(meses[0]);
            System.out.println("Datos generados para el gráfico: " + graficoDataSource.getData());

            Map<String, Object> reportParameters = new HashMap<>();

            reportParameters.put("logo", logoService.getByEmpresa(empresaId).logoFile());
            reportParameters.put("razonSocial", empresa.getRazonSocial());
            reportParameters.put("anio", anio);

            /*reportParameters.put("alcance1Nombre", "Alcance 1");
            reportParameters.put("alcance2Nombre", "Alcance 2");
            reportParameters.put("alcance3Nombre", "Alcance 3");*/

            reportParameters.put("alcance1", calculo.alcance1Str());
            reportParameters.put("alcance2", calculo.alcance2Str());
            reportParameters.put("alcance3", calculo.alcance3Str());
            reportParameters.put("total", calculo.totalStr());
            reportParameters.put("alcance1Porcentaje", calculo.alcance1PorcentajeStr());
            reportParameters.put("alcance2Porcentaje", calculo.alcance2PorcentajeStr());
            reportParameters.put("alcance3Porcentaje", calculo.alcance3PorcentajeStr());

            reportParameters.put("graficoDataSource", graficoDataSource);
            reportParameters.put("pieDataSource", pieDataSource);

            JasperReport report = (JasperReport) JRLoader.loadObject(reportFile.getInputStream());

            JasperPrint reportPrint = JasperFillManager.fillReport(report, reportParameters, new JREmptyDataSource());

            byte[] reportPdf = JasperExportManager.exportReportToPdf(reportPrint);

            return Base64.getEncoder().encodeToString(reportPdf);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ReportGeneratorException("No se puedo generar el reporte. Inténtelo más tarde. " + e.getMessage());
        }
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

