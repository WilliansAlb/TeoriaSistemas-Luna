
function eliminarPost(post){
    var cont=post;
    var contenedor_post = document.getElementById('POST_CARD'+cont)
    var data={ idpost: cont }
    contenedor_post.style.display = 'none';
    $.ajax({
                        type: "POST",
                        url: "../EliminarPost",
                        data: data,
                        beforeSend: function () {
                        },
                        complete: function (data) {

                        },
                        success: function (data) {

                            alert('Publicacion Eliminada');
                        },
                        error: function (data) {
                            alert("Error al eliminar comentario intentalo mas tarde");
                        }
                    });
}


