function solicitarEventos(mes, anio) {
    var dias = document.querySelectorAll(".ok");
    var url = '../Calendario?mes=' + mes + "&anio=" + anio;
    fetch(url, {
        method: 'GET' // or 'PUT'
    })
            .then(response => response.json())
            .then(data => {
                for (var i = 0; i < data.length; i++) {
                    var obj = data[i];
                    var value = obj["fechaEvento"];
                    var nombre = obj["nombre"];
                    var fe = new Date(value);
                    var feNueva = new Date(fe.getFullYear(), fe.getMonth(), fe.getDate() + 1);
                    var donde = dias[feNueva.getDate() - 1];
                    var notificacion = donde.parentNode;
                    var not = notificacion.querySelectorAll(".num")[0];
                    var suma = parseInt(not.textContent) + 1;
                    not.textContent = suma;
                    if (not.parentNode.style.display === 'none') {
                        not.parentNode.style.display = '';
                    }
                    if (suma == 1) {
                        var padre = notificacion.parentNode;
                        var nuevo = document.createElement("div");
                        nuevo.classList.add("eventos");
                        nuevo.style.display = "flex";
                        nuevo.style.flexDirection = "column";
                        nuevo.style.width = "95%";
                        var h1 = document.createElement("span");
                        h1.textContent = "EVENTOS +";
                        h1.style.backgroundColor = "black";
                        h1.style.color = "white";
                        h1.style.fontSize = "0.7em";
                        nuevo.appendChild(h1);
                        padre.appendChild(nuevo);
                        h1.onclick = function () {
                            var pad = this.parentNode;
                            var evs = pad.querySelectorAll(".lista_eventos");
                            if (this.textContent === "EVENTOS +") {
                                for (var i = 0; i < evs.length; i++) {
                                    evs[i].style.display = "";
                                }
                                this.textContent = "EVENTOS -";
                            } else {
                                for (var i = 0; i < evs.length; i++) {
                                    evs[i].style.display = "none";
                                }
                                this.textContent = "EVENTOS +";
                            }
                        };
                    }
                    var padre = notificacion.parentNode;
                    var nuevo = padre.querySelectorAll(".eventos")[0];
                    var span = document.createElement("span");
                    var arreglado = (nombre.length > 30) ? nombre.slice(0, 30) + "..." : nombre;
                    span.textContent = arreglado;
                    span.style.backgroundColor = "white";
                    span.style.color = "black";
                    span.style.fontSize = "0.7em";
                    span.style.border = "1px solid #303030";
                    span.style.display = "none";
                    span.classList = "lista_eventos";
                    nuevo.appendChild(span);
                }
                var nuevo = document.querySelectorAll(".eventos");
                for (var i = 0; i < nuevo.length; i++) {
                    var span = document.createElement("span");
                    span.textContent = "VER DETALLADAMENTE";
                    span.style.backgroundColor = "#303030";
                    span.style.color = "white";
                    span.style.fontSize = "0.7em";
                    span.style.border = "1px solid #303030";
                    span.style.display = "none";
                    span.classList = "lista_eventos";
                    span.onclick = function () {
                        var pad = this.parentNode;
                        var pa1 = pad.parentNode;
                        var divdi = pa1.querySelectorAll(".div-dia")[0];
                        var divdia = divdi.querySelectorAll(".dia")[0];
                        var d = parseInt(divdia.textContent);
                        var m = document.getElementById("mes").textContent.toLowerCase();
                        var meses = ["enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"];
                        var ind = meses.indexOf(m);
                        var m1 = document.getElementById("anio").textContent;
                        var a = parseInt(m1);
                        ind = mes;
                        a = anio;
                        var url1 = '../Calendario?mes=' + ind + "&anio=" + a + "&dia=" + d;
                        fetch(url1, {
                            method: "GET"
                        }).then(response => response.json()).then(data2 => {
                            var obtener = document.getElementById("listado_dia_evento");
                            var tbody = document.getElementById("tbody_eventos");
                            tbody.innerHTML = "";
                            obtener.textContent = data2[0]["fechaEvento"];
                            for (var i = 0; i < data2.length; i++) {
                                var objeto = data2[i];
                                var tr = document.createElement("tr");
                                var td1 = document.createElement("td");
                                var td2 = document.createElement("td");
                                var td3 = document.createElement("td");
                                td1.textContent = objeto["nombre"];
                                td2.textContent = objeto["descripcion"];
                                td3.textContent = objeto["tipo"];
                                td1.scope = "row";
                                td2.scope = "row";
                                td3.scope = "row";
                                tr.appendChild(td1);
                                tr.appendChild(td2);
                                tr.appendChild(td3);
                                tbody.appendChild(tr);
                            }
                            document.getElementById("oculto1").style.display = "";
                        });
                    };
                    nuevo[i].appendChild(span);
                }
                setTimeout(() => {
                    document.getElementById("cargando").style.display = "none";
                    document.querySelector(".contenedor-calendario").style.filter = "blur(0px)";
                }, 1000);
            });
}

function toggle_button(toggle) {
    if (toggle.checked) {
        document.getElementById("creacion_siembra").style.display = "flex";
        //document.getElementById("siembra_id").style.display = "flex";
        document.getElementById("evento_id").style.display = "none";
    } else {
        document.getElementById("creacion_siembra").style.display = "none";
        //document.getElementById("siembra_id").style.display = "none";
        document.getElementById("evento_id").style.display = "flex";
    }
}


function crear_evento() {
    var nom = document.getElementById("nombre_evento").value;
    var dim = document.getElementById("dia_crear").textContent;
    var m = document.getElementById("mes").textContent.toLowerCase();
    var meses = ["enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"];
    var ind = meses.indexOf(m);
    var m1 = document.getElementById("anio").textContent;
    var a = parseInt(m1);
    var des = document.getElementById("descripcion_evento").value;
    var es = document.getElementById("evento_tipo");
    if (nom == "" || des == "") {
        alert("Rellena los campos");
    } else {
        if (es.checked) {
            //acá van los eventos que si son tipo siembra
            var s1 = document.getElementById("siembras").value;
            if (s1 == -1) {
                var cult = document.getElementById("cultivos").value;
                var luga = document.getElementById("lugares").value;
                crear_evento_siembra(nom, des, dim, cult, luga);
            } else {
                var s2 = document.getElementById("evt_siembra").value;
                if (s2==-1){
                    var hacer_siembra = document.getElementById("hacer_siembra").value;
                    if (hacer_siembra==''){
                        alert("Ingresa lo que se le hará a la siembra");
                        return;
                    } else {
                        crear_evento_id_siembra(nom, des, dim, s1, hacer_siembra);
                    }
                } else {
                    crear_evento_id_siembra(nom, des, dim, s1, s2);
                }
            }
        } else {
            var tipo = document.getElementById("tipo_evento").value;
            if (tipo == "") {
                alert("Rellena el campo de tipo de evento");
            } else {
                crear_evento_normal(nom, des, tipo, dim);
            }
        }
    }
}

function cambiando_select(select, divmostrar, otrodiv) {
    if (select.value == -1) {
        document.getElementById(divmostrar).style.display = "flex";
        if (otrodiv != '') {
            document.getElementById(otrodiv).style.display = "none";
        }
    } else {
        document.getElementById(divmostrar).style.display = "none";
        if (otrodiv != '') {
            document.getElementById(otrodiv).style.display = "flex";
        }
    }
}

function crear_evento_normal(nombre, descripcion, tipo1, fecha) {
    var data = {nombre: nombre, descripcion: descripcion, tipo: tipo1, esSiembra: "false", fecha: fecha};
    $.ajax({
        type: "POST",
        url: "../Calendario",
        data: data,
        beforeSend: function () {
            $("#cargando").show();
        },
        complete: function (data) {
            setTimeout(() => {
                document.getElementById("cargando").style.display = "none";
                document.querySelector(".contenedor-calendario").style.filter = "blur(0px)";
                $("#oculto3").show();
            }, 1000);
        },
        success: function (data) {
            if (data.includes("ERROR")) {
                document.getElementById("img_tipo").src = "../assets/img/o6.png";
                $("#tipo_mensaje").text("ERROR");
                $("#mensaje").text("Ha ocurrido un error inesperado");
            } else {
                document.getElementById("img_tipo").src = "../assets/img/o7.png";
                $("#tipo_mensaje").text("INGRESO EXITOSO");
                $("#mensaje").text("Se ha ingresado exitosamente el evento");
            }
        },
        error: function (data) {
            alert("Problemas al tratar de enviar el formulario");
        }
    });
}

function crear_evento_siembra(nombre, descripcion, fecha, cultivo, lugar) {
    console.log(fecha);
    var data = {nombre: nombre, descripcion: descripcion, esSiembra: "true", fecha: fecha, cultivo: cultivo, lugar: lugar};
    $.ajax({
        type: "POST",
        url: "../Calendario",
        data: data,
        beforeSend: function () {
            $("#cargando").show();
        },
        complete: function (data) {
            setTimeout(() => {
                document.getElementById("cargando").style.display = "none";
                document.querySelector(".contenedor-calendario").style.filter = "blur(0px)";
                $("#oculto3").show();
            }, 1000);
        },
        success: function (data) {
            if (data.includes("ERROR")) {
                document.getElementById("img_tipo").src = "../assets/img/o6.png";
                $("#tipo_mensaje").text("ERROR");
                $("#mensaje").text("Ha ocurrido un error inesperado");
            } else {
                document.getElementById("img_tipo").src = "../assets/img/o7.png";
                $("#tipo_mensaje").text("INGRESO EXITOSO");
                $("#mensaje").text("Se ha ingresado exitosamente el evento");
            }
        },
        error: function (data) {
            alert("Problemas al tratar de enviar el formulario");
        }
    });
}

function crear_evento_id_siembra(nombre, descripcion, fecha, id_siembra, tipo) {
    var data = {nombre: nombre, descripcion: descripcion, esSiembra: "true", fecha: fecha, id_siembra:id_siembra, tipo:tipo};
    $.ajax({
        type: "POST",
        url: "../Calendario",
        data: data,
        beforeSend: function () {
            $("#cargando").show();
        },
        complete: function (data) {
            setTimeout(() => {
                document.getElementById("cargando").style.display = "none";
                document.querySelector(".contenedor-calendario").style.filter = "blur(0px)";
                $("#oculto3").show();
            }, 1000);
        },
        success: function (data) {
            if (data.includes("ERROR")) {
                document.getElementById("img_tipo").src = "../assets/img/o6.png";
                $("#tipo_mensaje").text("ERROR");
                $("#mensaje").text("Ha ocurrido un error inesperado");
            } else {
                document.getElementById("img_tipo").src = "../assets/img/o7.png";
                $("#tipo_mensaje").text("INGRESO EXITOSO");
                $("#mensaje").text("Se ha ingresado exitosamente el evento");
            }
        },
        error: function (data) {
            alert("Problemas al tratar de enviar el formulario");
        }
    });
}


function obtener_mes_anio() {
    var m = document.getElementById("mes").textContent.toLowerCase();
    var meses = ["enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"];
    var ind = meses.indexOf(m) + 1;
    var m1 = document.getElementById("anio").textContent;
    var a = parseInt(m1);
    var obj = new Object();
    obj.mes = ind;
    obj.anio = a;
    return obj;
}
