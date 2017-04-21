var Brewer = Brewer || {};

Brewer.EstiloCadastroRapido = (function(){

	function EstiloCadastroRapido(){
		this.modal = $('#modalCadastroRapidoEstilo');
		this.botaoSalvar = this.modal.find('.js-modal-cadastr-estilo-rapido-salvar');
		this.form = this.modal.find('form');
		this.url = this.form.attr('action');
		this.inputNomeEstilo = this.modal.find('#nomeEstilo');
		this.containerMensagemErro = $('.js-mensagem-cadastro-rapido-estilo');
		
	}
	
	EstiloCadastroRapido.prototype.iniciar = function(){
		this.form.on('submit', function(e){
			e.preventDefault();
		});
		this.modal.on('shown.bs.modal', onModalShow.bind(this));
		this.modal.on('hide.bs.modal', onModalClose.bind(this));
		
		this.botaoSalvar.on('click', onBotaoSalvarClick.bind(this))		
	}

	function onModalShow(){
		this.inputNomeEstilo.focus();
	}
	
	function onModalClose(){
		this.inputNomeEstilo.val('');
		this.containerMensagemErro.addClass('hidden');
		this.form.find('.form-group').removeClass('has-error');
	}
	
	function onBotaoSalvarClick(e){
		var nomeEstilo = this.inputNomeEstilo.val().trim();
		$.ajax({
			url: this.url,
			method: 'post',
			contentType: 'application/json',
			data: JSON.stringify({
				nome : nomeEstilo 
			}),
			error: onErrorSalvandoEstilos.bind(this),
			success: onEstiloSalvo.bind(this)
		})
	}
	
	function onErrorSalvandoEstilos(obj) {
		var mensagemErro = obj.responseText;
		this.containerMensagemErro.removeClass('hidden');
		this.containerMensagemErro.html('<span>' + mensagemErro + '</span>');
		this.form.find('.form-group').addClass('has-error');
	}
	
	function onEstiloSalvo(estilo){
		var comboEstilo = $('#estilo');
		comboEstilo.append('<option value="' + estilo.id + '">' + estilo.nome + "</option>" );
		comboEstilo.val(estilo.id);
		this.modal.modal('hide');
	}
	
	
	return EstiloCadastroRapido;
}());

$(function(){
	var estiloCadastroRapido = new Brewer.EstiloCadastroRapido();
	estiloCadastroRapido.iniciar();	
});