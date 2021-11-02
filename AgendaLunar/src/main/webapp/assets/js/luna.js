window.onload = () => {
    document.querySelector(".contenedor-calendario").style.filter = "blur(2px)";
    const fecha = new Date();
    var calendario1 = document.querySelector("#contenedor-calendario");
    var meses = ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"];
    var dias = ["Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"];
    var dia_actual = fecha.getDate();
    let mes = fecha.getMonth();
    let cantidad_dias;
    var repeticiones = 0;
    var valor1 = -1;
    var entra = false;
    if (mes == 11) {
        cantidad_dias = new Date(fecha.getFullYear() + 1, 0, 0).getDate();
    } else {
        cantidad_dias = new Date(fecha.getFullYear(), fecha.getMonth() + 1, 0).getDate();
    }
    let cantidad_dias_anterior = new Date(fecha.getFullYear(), fecha.getMonth(), 0).getDate();
    var ultimo_dia = new Date(fecha.getFullYear(), mes, cantidad_dias);

    var cantidad_dias_mas = 0;
    if (ultimo_dia.getDay() != 6) {
        cantidad_dias_mas = 7 - (ultimo_dia.getDay() + 1);
    }
    var primer_dia = new Date(fecha.getFullYear(), fecha.getMonth(), 1);
    var fase1 = "";
    if (primer_dia.getDay() != 0) {
        let desde = cantidad_dias_anterior - (primer_dia.getDay() - 1);
        for (let i = desde; i <= cantidad_dias_anterior; i++) {
            var fase = obtenerFaseLunar(fecha.getFullYear(), mes, i);
            var actual = false;
            if (fase.texto != fase1) {
                repeticiones = 0;
                entra = false;
                calendario1.appendChild(crearDiv(i, 0.5, fase.fase, fase.texto, actual, repeticiones));
                fase1 = fase.texto;
            } else {
                repeticiones++;
                if (valor1 == fase.fase && !entra) {
                    calendario1.appendChild(crearDiv(i, 0.5, fase.fase, "", actual, repeticiones));
                } else {
                    valor1 = fase.fase;
                    entra = true;
                    calendario1.appendChild(crearDiv(i, 0.5, fase.fase - 1, "", actual, repeticiones));
                }
            }
        }
    }
    for (let i = 1; i <= cantidad_dias; i++) {
        var fase = obtenerFaseLunar(fecha.getFullYear(), mes + 1, i);
        var actual = (i == dia_actual) ? true : false;
        if (fase.texto != fase1) {
            repeticiones = 0;
            entra = false;
            calendario1.appendChild(crearDiv(i, 1, fase.fase, fase.texto, actual, repeticiones));
            fase1 = fase.texto;
        } else {
            repeticiones++;
            if (valor1 == fase.fase && !entra) {
                calendario1.appendChild(crearDiv(i, 1, fase.fase, "", actual, repeticiones));
            } else {
                valor1 = fase.fase;
                entra = true;
                calendario1.appendChild(crearDiv(i, 1, fase.fase - 1, "", actual, repeticiones));
            }
        }
    }
    if (cantidad_dias_mas != 0) {
        for (let i = 1; i <= cantidad_dias_mas; i++) {
            var fase = ((mes + 2) <= 12) ? obtenerFaseLunar(fecha.getFullYear(), mes + 2, i) : obtenerFaseLunar(fecha.getFullYear() + 1, 0, i);
            var actual = false;
            if (fase.texto != fase1) {
                repeticiones = 0;
                entra = false;
                calendario1.appendChild(crearDiv(i, 0.5, fase.fase, fase.texto, actual, repeticiones));
                fase1 = fase.texto;
            } else {
                repeticiones++;
                if (valor1 == fase.fase && !entra) {
                    calendario1.appendChild(crearDiv(i, 0.5, fase.fase, "", actual, repeticiones));
                } else {
                    valor1 = fase.fase;
                    entra = true;
                    calendario1.appendChild(crearDiv(i, 0.5, fase.fase - 1, "", actual, repeticiones));
                }
            }
        }
    }
    solicitarEventos(mes + 1, fecha.getFullYear());
};

function crearDiv(dia, opacidad, fase, fase_texto, actual, repeticiones) {
    var divTarjeta = document.createElement("div");
    divTarjeta.classList = "tarjeta-dia";
    var divDia = document.createElement("div");
    //divDia.style.display = "flex";
    //divDia.style.flexDirection = "row";

    var ahref1 = document.createElement("a");

    ahref1.classList = "agregar";
    ahref1.onclick = function () {
        var padre1 = this.parentNode;
        var day = padre1.querySelectorAll(".dia")[0];
        var fecha = obtener_mes_anio();
        var fecha1 = fecha.anio + "-" + fecha.mes + "-" + day.textContent;
        document.getElementById("dia_crear").textContent = fecha1;
        $("#oculto2").show();
    };
    //spanA1.classList = "num";
    //spanA1.textContent = "+";
    //ahref1.appendChild(spanA1);


    var span = document.createElement("span");
    span.classList = "dia";
    span.textContent = dia;
    span.style.fontSize = "1.5em";
    var spanTexto = document.createElement("span");
    spanTexto.classList = "texto-luna";
    spanTexto.textContent = fase_texto.toUpperCase();
    spanTexto.style.fontSize = "8px";
    var divMoon = document.createElement("div");
    divMoon.classList = "moon";
    var divDisc = document.createElement("div");
    divDisc.classList = "disc";
    var ahref = document.createElement("a");
    var spanA = document.createElement("span");
    ahref.classList = "notif";
    spanA.classList = "num";
    spanA.textContent = "0";
    ahref.appendChild(spanA);
    ahref.style.display = "none";
    pintarFase(fase, divMoon, divDisc, repeticiones);
    divDia.appendChild(span);
    if (opacidad == 0.5) {
        divTarjeta.style.filter = "blur(1px)";
    } else {
        span.classList.add("ok");
        divDia.appendChild(ahref);
        divDia.appendChild(ahref1);
    }
    divDia.classList = "div-dia";
    divTarjeta.appendChild(divDia);
    divMoon.appendChild(divDisc);
    divTarjeta.style.opacity = opacidad;
    divTarjeta.appendChild(divMoon);
    divTarjeta.appendChild(spanTexto);


    if (actual) {
        divTarjeta.style.border = "5px solid orange";
    }
    return divTarjeta;
}

function pintarFase(fase, luna, disco, repeticion) {
    var multiplicador = repeticion * (10 - repeticion);
    var escritura = 0;
    switch (fase) {
        case 0:
            escritura = multiplicador + 0;
            luna.style.transform = "rotateZ(0deg)";
            disco.style.transform = "rotateY(" + escritura + "deg)";
            break;
        case 1:
            escritura = multiplicador + 45;
            luna.style.transform = "rotateZ(0deg)";
            disco.style.transform = "rotateY(" + escritura + "deg)";
            break;
        case 2:
            escritura = multiplicador + 90;
            luna.style.transform = "rotateZ(0deg)";
            disco.style.transform = "rotateY(" + escritura + "deg)";
            break;
        case 3:
            escritura = multiplicador + 135;
            luna.style.transform = "rotateZ(0deg)";
            disco.style.transform = "rotateY(" + escritura + "deg)";
            break;
        case 4:
            escritura = 180 - multiplicador;
            luna.style.transform = "rotateZ(0deg)";
            disco.style.transform = "rotateY(" + escritura + "deg)";
            break;
        case 5:
            escritura = 135 - multiplicador;
            luna.style.transform = "rotateZ(180deg)";
            disco.style.transform = "rotateY(" + escritura + "deg)";
            break;
        case 6:
            escritura = 90 - multiplicador;
            luna.style.transform = "rotateZ(180deg)";
            disco.style.transform = "rotateY(" + escritura + "deg)";
            break;
        case 7:
            escritura = 45 - multiplicador;
            luna.style.transform = "rotateZ(180deg)";
            disco.style.transform = "rotateY(" + escritura + "deg)";
            break;
    }
}

function obtenerFaseLunar(year, month, day) {
    var c = e = jd = b = bc = 0;
    if (month < 3) {
        year--;
        month += 12;
    }

    ++month;

    c = 365.25 * year;

    e = 30.6 * month;

    jd = c + e + day - 694039.09; //jd is total days elapsed

    jd /= 29.5305882; //divide by the moon cycle


    b = parseInt(jd); //int(jd) -> b, take integer part of jd

    jd -= b; //subtract integer part to leave fractional part of original jd

    console.log("---" + jd);
    bc = Math.round(jd * 100); //scale fraction from 0-8 and round
    console.log("---" + bc + "% en dia " + day);
    b = Math.round(jd * 8); //scale fraction from 0-8 and round
    console.log("---" + b);
    //var a = Math.round(jd * 27); //scale fraction from 0-8 and round
    //console.log("---"+b+" day" + day);
    if (b >= 8) {
        b = 0; //0 and 8 are the same so turn 8 into 0
    }
    var txt = "";
    if (bc >= 0 && bc <= 10) {
        txt = "Luna nueva";
    } else if (bc >= 25 && bc <= 35) {
        txt = "Cuarto Creciente";
    } else if (bc >= 50 && bc <= 60) {
        txt = "Luna llena";
    } else if (bc >= 75 && bc <= 85) {
        txt = "Cuarto Menguante";
    }
    // 0 => New Moon
    // 1 => Waxing Crescent Moon
    // 2 => Quarter Moon
    // 3 => Waxing Gibbous Moon
    // 4 => Full Moon
    // 5 => Waning Gibbous Moon
    // 6 => Last Quarter Moon
    // 7 => Waning Crescent Moon

    var compuesta = {fase: b, texto: txt};
    return compuesta;
}

function obtenerFaseLunar2(year, month, day) {
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


    b = parseInt(jd); //int(jd) -> b, take integer part of jd

    jd -= b; //subtract integer part to leave fractional part of original jd

    console.log("---" + jd);
    b = parseInt(jd); //int(jd) -> b, take integer part of jd

    b = Math.round(jd * 8); //scale fraction from 0-8 and round

    var a = Math.round(jd * 27); //scale fraction from 0-8 and round
    console.log("---" + b + " day" + day);
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

function cambiaMes(arriba) {
    var m = document.getElementById("mes").textContent.toLowerCase();
    var meses = ["enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"];
    if (arriba) {
        var ind = meses.indexOf(m);
        if (ind == 11) {
            document.getElementById("mes").textContent = "ENERO";
        } else {
            document.getElementById("mes").textContent = meses[ind + 1].toUpperCase();
        }
    } else {
        var ind = meses.indexOf(m);
        if (ind == 0) {
            document.getElementById("mes").textContent = "DICIEMBRE";
        } else {
            document.getElementById("mes").textContent = meses[ind - 1].toUpperCase();
        }
    }
    cambioFecha();
}

function cambiaAnio(arriba) {
    var m = document.getElementById("anio").textContent;
    var a = parseInt(m);
    if (arriba) {
        a++;
        document.getElementById("anio").textContent = a;
    } else {
        a--;
        document.getElementById("anio").textContent = a;
    }
    cambioFecha();
}


function cambioFecha() {
    var m = document.getElementById("mes").textContent.toLowerCase();
    var a = document.getElementById("anio").textContent;
    var fec = new Date();
    var anio = parseInt(a);
    var repeticiones = 0;
    var valor1 = -1;
    var entra = false;
    var calendario1 = document.querySelector("#contenedor-calendario");
    var anteriores = document.querySelectorAll(".tarjeta-dia");
    for (var i = 0; i < anteriores.length; i++) {
        calendario1.removeChild(anteriores[i]);
    }
    var meses = ["enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"];
    let mes = meses.indexOf(m);
    var cantidad_dias;
    if (mes == 11) {
        cantidad_dias = new Date(anio + 1, 0, 0).getDate();
    } else {
        cantidad_dias = new Date(anio, mes + 1, 0).getDate();
    }
    let cantidad_dias_anterior = new Date(anio, mes, 0).getDate();
    var primer_dia = new Date(anio, mes, 1);
    var ultimo_dia = new Date(anio, mes, cantidad_dias);

    var cantidad_dias_mas = 0;
    if (ultimo_dia.getDay() != 6) {
        cantidad_dias_mas = 7 - (ultimo_dia.getDay() + 1);
    }

    var fase1 = "";
    if (primer_dia.getDay() != 0) {
        repeticiones = 0;
        let desde = cantidad_dias_anterior - (primer_dia.getDay() - 1);
        for (let i = desde; i <= cantidad_dias_anterior; i++) {
            var fase = obtenerFaseLunar(anio, mes, i);
            var actual = false;
            if (fase.texto != fase1) {
                repeticiones = 0;
                entra = false;
                calendario1.appendChild(crearDiv(i, 0.5, fase.fase, fase.texto, actual, repeticiones));
                fase1 = fase.texto;
            } else {
                repeticiones++;
                if (valor1 == fase.fase && !entra) {
                    calendario1.appendChild(crearDiv(i, 0.5, fase.fase, "", actual, repeticiones));
                } else {
                    valor1 = fase.fase;
                    entra = true;
                    calendario1.appendChild(crearDiv(i, 0.5, fase.fase - 1, "", actual, repeticiones));
                }
            }
        }
    }
    repeticiones = 0;
    for (let i = 1; i <= cantidad_dias; i++) {
        var fase = obtenerFaseLunar(anio, mes + 1, i);
        var actual = (anio == fec.getFullYear() && mes == fec.getMonth() && i == fec.getDate()) ? true : false;
        if (fase.texto != fase1) {
            repeticiones = 0;
            entra = false;
            calendario1.appendChild(crearDiv(i, 1, fase.fase, fase.texto, actual, repeticiones));
            fase1 = fase.texto;
        } else {
            repeticiones++;
            if (valor1 == fase.fase && !entra) {
                calendario1.appendChild(crearDiv(i, 1, fase.fase, "", actual, repeticiones));
            } else {
                valor1 = fase.fase;
                entra = true;
                calendario1.appendChild(crearDiv(i, 1, fase.fase - 1, "", actual, repeticiones));
            }
        }
    }
    repeticiones = 0;
    if (cantidad_dias_mas != 0) {
        for (let i = 1; i <= cantidad_dias_mas; i++) {
            var fase = ((mes + 2) <= 12) ? obtenerFaseLunar(anio, mes + 2, i) : obtenerFaseLunar(anio + 1, 0, i);
            var actual = false;
            if (fase.texto != fase1) {
                repeticiones = 0;
                entra = false;
                calendario1.appendChild(crearDiv(i, 0.5, fase.fase, fase.texto, actual, repeticiones));
                fase1 = fase.texto;
            } else {
                repeticiones++;
                if (valor1 == fase.fase && !entra) {
                    calendario1.appendChild(crearDiv(i, 0.5, fase.fase, "", actual, repeticiones));
                } else {
                    valor1 = fase.fase;
                    entra = true;
                    calendario1.appendChild(crearDiv(i, 0.5, fase.fase - 1, "", actual, repeticiones));
                }
            }
        }
    }

    solicitarEventos(mes + 1, anio);
}