package br.com.insightdigital.brewer.storage.local;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.UUIDEditor;
import org.springframework.web.multipart.MultipartFile;

import br.com.insightdigital.brewer.storage.FotoStorage;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

public class FotoStorageLocal implements FotoStorage{

	private static final Logger logger = LoggerFactory.getLogger(FotoStorageLocal.class);
	
	
	private Path local;
	private Path localTemporario;
	
	public FotoStorageLocal() {
		this.local = FileSystems.getDefault().getPath(System.getenv("HOME"), ".brewerfotos");
		this.localTemporario = FileSystems.getDefault().getPath(this.local.toString(), "temp");
		criarPastas();
	}

	@Override
	public String salvarTemporariamente(MultipartFile[] files) {
		String novoNome = null;
		if (null != files && files.length > 0){
			MultipartFile file = files[0];
			novoNome = renomerArquivo(file.getOriginalFilename());
			try {
				file.transferTo(
					new File(
						this.localTemporario.toAbsolutePath().toString() +
						FileSystems.getDefault().getSeparator() + 
						novoNome
					)
				);
			} catch (IOException | IllegalStateException e) {
				throw new RuntimeException("Erro salvando a foto na pasta temporária.", e);
			}
		}
		return novoNome;
	}
	

	@Override
	public byte[] recuperarFotoTemporaria(String nome) {
		try {
			return Files.readAllBytes(localTemporario.resolve(nome));
		} catch (IOException e) {
			throw new RuntimeException("Erro lendo a foto temporária.", e);
		}
	}
	
	@Override
	public void salvar(String foto) {
		try {
			Files.move(this.localTemporario.resolve(foto), this.local.resolve(foto));
			Thumbnails.of(this.local.resolve(foto).toString()).size(40, 68).toFiles(Rename.PREFIX_DOT_THUMBNAIL);
		} catch (IOException e) {
			throw new RuntimeException("Erro movendo foto para o destino final", e);
		}
	}
	
	@Override
	public byte[] recuperar(String nome) {
		try {
			return Files.readAllBytes(local.resolve(nome));
		} catch (IOException e) {
			throw new RuntimeException("Erro lendo a foto.", e);
		}
	}

	private void criarPastas() {
		try {
			Files.createDirectories(local);
			Files.createDirectories(localTemporario);
			
			if (logger.isDebugEnabled()) {
				logger.debug("Sucesso na criação das pasta para armazenar as fotos");
				logger.debug("Pasta padrão: " + this.local.toAbsolutePath());
				logger.debug("Pasta temporária: " + this.localTemporario.toAbsolutePath());
			}
		} catch (IOException e) {
			throw new RuntimeException("Erro ao criar pasta para salvar a foto", e);
		}
	}
	
	private String renomerArquivo(String nomeOriginal){
		String nome = UUID.randomUUID().toString() + "_" + nomeOriginal;
		if (logger.isDebugEnabled()){
			logger.debug(String.format("Nome original: %s, novo nome do arquivo: %s", nomeOriginal, nome));
		}
		return nome;
	}


}
