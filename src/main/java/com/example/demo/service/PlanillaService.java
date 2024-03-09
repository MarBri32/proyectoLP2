package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Planilla;
import com.example.demo.repository.PlanillaRepository;

@Service
public class PlanillaService {
	
	@Autowired
	private PlanillaRepository planillaRepository;
	
	public List<Planilla> getAllPlanillas(){
		return planillaRepository.findAll();
	}
	
	public Planilla getPlanillaById(Long idplanilla) {
		return planillaRepository.findById(idplanilla).orElse(null);
	}

}
