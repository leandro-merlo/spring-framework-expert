$(function(){
	var decimal = $('.js-decimal');
	decimal.maskMoney({
		decimal: ',',
		thousands: '.',
		allowNegative: false,		
	});
	
	var integer = $('.js-integer');
	integer.maskMoney({
		thousands: '.',
		decimal: ',',
		precision: 0,
		allowNegative: false,
	});
})