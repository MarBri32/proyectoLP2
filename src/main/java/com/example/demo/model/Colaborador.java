package com.example.demo.model;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="colaborador")
public class Colaborador {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	public Long id;
	
	@Column(name="nombre", nullable=false)
	public String nombre;
	
	@Column(name="profesion", nullable=false)
	public String profesion;
	
	@Column(name="direccion", nullable=false)
	public String direccion;
	
	@Column(name="celular", nullable=false)
	public String celular;
	
	@Column(name="correo", nullable=false)
	public String correo;
	
	@ManyToOne
	@JoinColumn(name="idplanilla", nullable=false)
	public Planilla planilla;
}
