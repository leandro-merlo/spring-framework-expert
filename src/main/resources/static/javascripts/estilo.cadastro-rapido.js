$(function(){
	
	var modal = $('#modalCadastroRapidoEstilo');
	var botaoSalvar = modal.find('.js-modal-cadastr-estilo-rapido-salvar');
	var form = modal.find('form');
	form.on('submit', function(e){
		e.preventDefault();
	});
	var url = form.attr('action');
	var inputNomeEstilo = modal.find('#nomeEstilo');
	var containerMensagemErro = $('.js-mensagem-cadastro-rapido-estilo');
	modal.on('shown.bs.modal', onModalShow);
	modal.on('hide.bs.modal', onModalClose);
	
	botaoSalvar.on('click', onBotaoSalvarClick)
	
	function onModalShow(){
		inputNomeEstilo.focus();
	}
	
	function onModalClose(){
		inputNomeEstilo.val('');
		containerMensagemErro.addClass('hidden');
		form.find('.form-group').removeClass('has-error');
	}
	
	function onBotaoSalvarClick(e){
		var nomeEstilo = inputNomeEstilo.val().trim();
		$.ajax({
			url: url,
			method: 'post',
			contentType: 'application/json',
			data: JSON.stringify({
				nome : nomeEstilo 
			}),
			error: onErrorSalvandoEstilos,
			success: onEstiloSalvo
		})
	}
	
	function onErrorSalvandoEstilos(obj) {
		var mensagemErro = obj.responseText;
		containerMensagemErro.removeClass('hidden');
		containerMensagemErro.html('<span>' + mensagemErro + '</span>');
		form.find('.form-group').addClass('has-error');
	}
	
	function onEstiloSalvo(estilo){
		var comboEstilo = $('#estilo');
		comboEstilo.append('<option value="' + estilo.id + '">' + estilo.nome + "</option>" );
		comboEstilo.val(estilo.id);
		modal.modal('hide');
	}
	
	
});