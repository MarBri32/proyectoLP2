package com.example.demo.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="planilla")
public class Planilla {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long idplanilla;
	
	@Column(name="departamento", nullable=false)
	public String departamento;
	
	@Column(name="piso", nullable=false)
	public String piso;
}
