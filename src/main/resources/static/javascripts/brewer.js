var Brewer = Brewer || {};

Brewer.MaskMoney = (function(){
	function MaskMoney(){
		this.decimal = $('.js-decimal');
		this.integer = $('.js-integer');
	}
	
	MaskMoney.prototype.enable = function(){
		this.decimal.maskMoney({
			decimal: ',',
			thousands: '.',
			allowNegative: false,		
		});
		
		this.integer.maskMoney({
			thousands: '.',
			decimal: ',',
			precision: 0,
			allowNegative: false,
		});		
	}
	
	return MaskMoney;
}());

$(function(){
	var maskMoney = new Brewer.MaskMoney();
	maskMoney.enable();
});