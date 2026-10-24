package com.cibertec.alquiler_vehiculos.service;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Locale;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Service
@RequiredArgsConstructor
public class ReporteService {

    private final JdbcTemplate jdbcTemplate;

    public JasperPrint getJasperPrint(Map<String, Object> params, String reportPath) throws Exception {
        Connection conn = jdbcTemplate.getDataSource().getConnection();

        try {
            params.put(JRParameter.REPORT_LOCALE, Locale.forLanguageTag("es-PE"));
            
            // Cargar y compilar el archivo .jrxml desde resources
            InputStream reportStream = this.getClass().getResourceAsStream(reportPath);
            if (reportStream == null) {
                throw new RuntimeException("No se encontró la plantilla del reporte en la ruta: " + reportPath);
            }
            
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            // Llenar el reporte utilizando la conexión a base de datos y los parámetros
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, conn);

            return jasperPrint;
        } finally {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
    }
}