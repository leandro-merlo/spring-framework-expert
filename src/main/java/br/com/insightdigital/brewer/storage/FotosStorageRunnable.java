package br.com.insightdigital.brewer.storage;

import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

public class FotosStorageRunnable implements Runnable {

	private MultipartFile[] files;
	private DeferredResult result;
	
	public FotosStorageRunnable(MultipartFile[] files, DeferredResult result) {
		this.files = files;
		this.result = result;
	}
	
	@Override
	public void run() {
		System.out.println(">>>>> Tamanho da foto: " + files[0].getSize());
		//TODO: Salvar a foto no sistema de arquivos
		result.setResult("Ok! Foto recebida!");
	}

}
