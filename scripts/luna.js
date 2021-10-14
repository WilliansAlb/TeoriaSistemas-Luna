window.onload = () => {
	const fecha = new Date();
	var meses = ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"];
	var dias = ["Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"];
	let mes = fecha.getMonth();
	let cantidad_dias = new Date(fecha.getFullYear(), fecha.getMonth() + 1, 0).getDate();
	let cantidad_dias_anterior = new Date(fecha.getFullYear(), fecha.getMonth(), 0).getDate();
	var primer_dia = new Date(fecha.getFullYear(), fecha.getMonth(), 1);
	var sumando = cantidad_dias_anterior - (8 - primer_dia.getDay());
	var lunas = document.querySelectorAll(".moon");
	var discos = document.querySelectorAll(".disc");
	let dia = fecha.getDay();
	var dias = document.querySelectorAll('.dia');
	var contador = 1;
	for (let i = 0; i < dias.length; i++) {
		if (i >= primer_dia.getDay() - 1) {
			dias[i].textContent = contador;
			var fase = getMoonPhase(2021, mes + 1, contador);
			pintarFase(fase,lunas[i],discos[i]);
			contador++;
		} else {
			var con = dias[i].parentNode;
			con.style.opacity = "0.5";
			dias[i].textContent = sumando;
			var fase = getMoonPhase(2021, mes, sumando);
			pintarFase(fase,lunas[i],discos[i]);
			sumando++;
		}
	}
}

function pintarFase(fase, luna, disco){
	switch (fase) {
		case 0:
			console.log("New Moon");
			luna.style.transform = "rotateZ(0deg)";
			disco.style.transform = "rotateY(0deg)";
			break;
		case 1:
			console.log("Waxing Crescent Moon");
			luna.style.transform = "rotateZ(0deg)";
			disco.style.transform = "rotateY(45deg)";
			break;
		case 2:
			console.log("Quarter Moon");
			luna.style.transform = "rotateZ(0deg)";
			disco.style.transform = "rotateY(90deg)";
			break;
		case 3:
			console.log("Waxing Gibbous Moon");
			luna.style.transform = "rotateZ(0deg)";
			disco.style.transform = "rotateY(135deg)";
			break;
		case 4:
			console.log("Full Moon");
			luna.style.transform = "rotateZ(0deg)";
			disco.style.transform = "rotateY(180deg)";
			break;
		case 5:
			console.log("Waning Gibbous Moon");
			luna.style.transform = "rotateZ(180deg)";
			disco.style.transform = "rotateY(135deg)";
			break;
		case 6:
			console.log("Last Quarter Moon");
			luna.style.transform = "rotateZ(180deg)";
			disco.style.transform = "rotateY(90deg)";
			break;
		case 7:
			console.log("Waning Crescent Moon");
			luna.style.transform = "rotateZ(180deg)";
			disco.style.transform = "rotateY(45deg)";
			break;
	}
}

function getMoonPhase(year, month, day) {
	var c = e = jd = b = 0;

	if (month < 3) {
		year--;
		month += 12;
	}

	++month;

	c = 365.25 * year;

	e = 30.6 * month;

	jd = c + e + day - 694039.09; //jd is total days elapsed

	jd /= 29.5305882; //divide by the moon cycle
	
	console.log("---"+jd);

	b = parseInt(jd); //int(jd) -> b, take integer part of jd

	jd -= b; //subtract integer part to leave fractional part of original jd

	b = parseInt(jd); //int(jd) -> b, take integer part of jd
	
	b = Math.round(jd * 8); //scale fraction from 0-8 and round

	var a = Math.round(jd * 27); //scale fraction from 0-8 and round
	console.log("---"+a);
	if (b >= 8) {
		b = 0; //0 and 8 are the same so turn 8 into 0
	}

	// 0 => New Moon
	// 1 => Waxing Crescent Moon
	// 2 => Quarter Moon
	// 3 => Waxing Gibbous Moon
	// 4 => Full Moon
	// 5 => Waning Gibbous Moon
	// 6 => Last Quarter Moon
	// 7 => Waning Crescent Moon

	return b;
}