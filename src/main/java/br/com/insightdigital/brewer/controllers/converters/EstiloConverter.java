package br.com.insightdigital.brewer.controllers.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import br.com.insightdigital.brewer.model.Estilo;

public class EstiloConverter implements Converter<String, Estilo> {

	@Override
	public Estilo convert(String id) {
		if (!StringUtils.isEmpty(id)) {
			Estilo e = new Estilo();
			e.setId(Long.valueOf(id));
			return e;
		}
		return null;
	}

}
