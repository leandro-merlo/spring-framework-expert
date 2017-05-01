package br.com.insightdigital.brewer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import br.com.insightdigital.brewer.dto.FotoDTO;
import br.com.insightdigital.brewer.storage.FotoStorage;
import br.com.insightdigital.brewer.storage.FotosStorageRunnable;

@RestController
@RequestMapping("/fotos")
public class FotosController {

	@Autowired
	private FotoStorage fotoStorage;
	
	@PostMapping
	public DeferredResult<FotoDTO> upload(@RequestParam(name="files[]") MultipartFile[] files){
		DeferredResult<FotoDTO> result = new DeferredResult<>();
		new Thread(new FotosStorageRunnable(files, result, fotoStorage)).start();
		return result;
	}
	
	@GetMapping("/temp/{nome:.*}")
	public byte[] recuperarFotoTemporaria(@PathVariable String nome){
		return fotoStorage.recuperarFotoTemporaria(nome);
	}
	
	@GetMapping("/{nome:.*}")
	public byte[] recuperar(@PathVariable String nome){
		return fotoStorage.recuperar(nome);
	}
}
