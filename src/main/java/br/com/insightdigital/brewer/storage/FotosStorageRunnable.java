package br.com.insightdigital.brewer.storage;

import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import br.com.insightdigital.brewer.dto.FotoDTO;

public class FotosStorageRunnable implements Runnable {

	private MultipartFile[] files;
	private DeferredResult<FotoDTO> result;
	private FotoStorage fotoStorage;
	
	public FotosStorageRunnable(MultipartFile[] files, DeferredResult<FotoDTO> result, FotoStorage fotoStorage) {
		this.files = files;
		this.result = result;
		this.fotoStorage = fotoStorage;
	}
	
	@Override
	public void run() {
		String novoNome = this.fotoStorage.salvarTemporariamente(files);
		result.setResult(new FotoDTO(novoNome, files[0].getContentType()));
	}

}
