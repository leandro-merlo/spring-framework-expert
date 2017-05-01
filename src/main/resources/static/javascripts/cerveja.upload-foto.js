var Brewer = Brewer || {};

Brewer.UploadFoto = (function(){
	
	function UploadFoto (){
		this.inputNomeFoto = $('input[name=foto]');
		this.inputContentType = $('input[name=contentType]');
		this.template = Handlebars.compile($("#foto-cerveja").html());
		this.containerFotoCerveja = $(".js-container-foto-cerveja");
		this.uploadDrop = $("#upload-drop");		
	}
	
	UploadFoto.prototype.iniciar = function(){
		var settings = {
			type: "json",
			file: 1,
			allow: '*.(jpg|jpeg|png)',
			action: this.containerFotoCerveja.data('url-fotos'),
			complete: onUploadComplete.bind(this)
		}
		UIkit.uploadSelect($("#upload-select"), settings);
		UIkit.uploadDrop(this.uploadDrop, settings);
		
		if (this.inputNomeFoto.val()){
			onUploadComplete.call(this, {
				nome: this.inputNomeFoto.val(),
				contentType: this.inputContentType.val()
			});
		}
	}		
	
	function onUploadComplete(resposta){
		this.inputNomeFoto.val(resposta.nome);
		this.inputContentType.val(resposta.contentType);
		
		this.uploadDrop.addClass("hidden");
		
		var htmlFotoCerveja = this.template({
			nomeFoto: resposta.nome
		}); 		
		this.containerFotoCerveja.append(htmlFotoCerveja);
		
		$('.js-remove-foto').on("click", onRemoveFoto.bind(this));
		
	}
	
	function onRemoveFoto(){
		$(".js-foto-cerveja").remove();
		this.uploadDrop.removeClass("hidden");
		this.inputNomeFoto.val("");
		this.inputContentType.val("");		
	}

	return UploadFoto;

})();

$(function(){
	var uploadFoto = new Brewer.UploadFoto();
	uploadFoto.iniciar();
});