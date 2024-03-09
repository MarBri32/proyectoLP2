package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Colaborador;
import com.example.demo.repository.ColaboradorRepository;

@Service
public class ColaboradorService {

	@Autowired
	private ColaboradorRepository colaboradorRepository;
	
	public List<Colaborador> getAllColaboradores(){
		return colaboradorRepository.findAll();
	}
	
	public Colaborador createColaborador(Colaborador colaborador) {
		return colaboradorRepository.save(colaborador);
	}
	
	public Colaborador getColaboradorByID(Long id) {
		return colaboradorRepository.findById(id).orElse(null);
	}
	
	public void deleteColaborador(Long id) {
		colaboradorRepository.deleteById(id);
	}
}
