package br.com.insightdigital.brewer.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import br.com.insightdigital.brewer.storage.FotosStorageRunnable;

@RestController
@RequestMapping("/fotos")
public class FotosController {

	@PostMapping
	public DeferredResult<String> upload(@RequestParam(name="files[]") MultipartFile[] files){
		DeferredResult<String> result = new DeferredResult<>();
		new Thread(new FotosStorageRunnable(files, result)).start();
		return result;
	}
}
