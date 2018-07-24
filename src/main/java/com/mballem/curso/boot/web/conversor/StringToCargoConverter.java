package com.mballem.curso.boot.web.conversor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.mballem.curso.boot.domein.Cargo;
import com.mballem.curso.boot.service.CargoService;

@Component
public class StringToCargoConverter implements Converter<String, Cargo>{
	
	@Autowired
	private CargoService cargoservice;
	
	@Override
	public Cargo convert(String text) {
		
		if (text.isEmpty()) {
			return null;
		}
		
		Long id = Long.valueOf(text);
		
		return this.cargoservice.buscarPorId(id);
	}

}
