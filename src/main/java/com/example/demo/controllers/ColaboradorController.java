package com.example.demo.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Colaborador;
import com.example.demo.model.Planilla;
import com.example.demo.service.ColaboradorService;
import com.example.demo.service.PlanillaService;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

@Controller
@RequestMapping("/colaborador")
public class ColaboradorController {

	@Autowired
	private ColaboradorService colaboradorService;
	
	@Autowired
	private PlanillaService planillaService;
	
	@GetMapping("/colaboradores")
	public String getAllColaborador(Model model) {
		
		List<Colaborador> lisColaboradores=colaboradorService.getAllColaboradores();
		model.addAttribute("colaboradores",lisColaboradores);
		return "colaboradorList";
	}
	
	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("planillas", planillaService.getAllPlanillas());
		return "colaboradorRegister";
	}
	
	@PostMapping("/register")
	public String createColaborador(@RequestParam("nombre") String nombre,
								@RequestParam("profesion") String profesion,
								@RequestParam("direccion") String direccion,
								@RequestParam("celular") String celular, 
								@RequestParam("correo") String correo,
								@RequestParam("idplanilla") Long idplanilla, Model model) {
		
			Colaborador colaborador = new Colaborador();
			colaborador.nombre= nombre;
			colaborador.profesion= profesion;
			colaborador.direccion= direccion;
			colaborador.celular= celular;
			colaborador.correo= correo;
			
			Planilla planilla = planillaService.getPlanillaById(idplanilla);
			
			colaborador.planilla= planilla;
			
			colaboradorService.createColaborador(colaborador);
			
			model.addAttribute("colaboradores", colaboradorService.getAllColaboradores());
			model.addAttribute("planillas", planillaService.getAllPlanillas());
			
			return "colaboradorList";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Long id, Model model) {		

		Colaborador colaborador = colaboradorService.getColaboradorByID(id);
		
		model.addAttribute("colaborador", colaborador);
		model.addAttribute("planillas", planillaService.getAllPlanillas());

		return "colaboradorEdit";
	}
	
	@PostMapping("/edit")
	public String createColaborador(@RequestParam("id") Long id, @RequestParam("nombre") String nombre,
								@RequestParam("profesion") String profesion,
								@RequestParam("direccion") String direccion,
								@RequestParam("celular") String celular, 
								@RequestParam("correo") String correo,
								@RequestParam("idplanilla") Long idplanilla, Model model) {
		
			Colaborador colaborador = colaboradorService.getColaboradorByID(id);
			colaborador.nombre= nombre;
			colaborador.profesion= profesion;
			colaborador.direccion= direccion;
			colaborador.celular= celular;
			colaborador.correo= correo;
			
			Planilla planilla = planillaService.getPlanillaById(idplanilla);
			
			colaborador.planilla= planilla;
			
			colaboradorService.createColaborador(colaborador);
			
			model.addAttribute("colaboradores", colaboradorService.getAllColaboradores());
			model.addAttribute("planillas", planillaService.getAllPlanillas());
			
			return "colaboradorList";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteColaborador(@PathVariable Long id, Model model) {
		colaboradorService.deleteColaborador(id);
		
		model.addAttribute("colaboradores", colaboradorService.getAllColaboradores());
		model.addAttribute("planillas", planillaService.getAllPlanillas());
		
		return "colaboradorList";
	}
	
	@GetMapping("/report")
	public void report(HttpServletResponse response) throws JRException, IOException {
		
		//1. Acceder al reporte
		InputStream jasperStream= this.getClass().getResourceAsStream("/reportes/reporteproyecto.jasper");
		
		//2. Preparar los datos
		Map<String, Object> params = new HashMap<>();
		params.put("usuario", "IEP Corazon de Jesus");
		
		List<Colaborador> lisColaboradores= colaboradorService.getAllColaboradores(); 
		
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lisColaboradores);
		
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
		
		//3. Configuracion
		response.setContentType("application/x-pdf");
		response.setHeader("Content-disposition", "filename=reporte_ejemplo.pdf");
		
		//4. Exportar reporte
		final OutputStream outputStream = response.getOutputStream();		
		JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
	}
}
