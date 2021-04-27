package ar.edu.unahur.obj2.caralibro

import kotlin.math.ceil

abstract class Publicacion {
  val listaLikes = mutableListOf<Usuario>()
  var privacidad = "publico"
  val permitidos = mutableListOf<Usuario>()
  val excluidos = mutableListOf<Usuario>()

  abstract fun espacioQueOcupa(): Int

  fun agregarLikeDe(unUsuario: Usuario){
    if (!listaLikes.contains(unUsuario)) {
      listaLikes.add(unUsuario)
    } else {
      error("Este usuario ya dio Like")
    }
  }

  fun cantidadDeLikes(): Int {
    return listaLikes.size
  }

  ///////////////////////////////////////////////
  ///////////PRIVACIDAD DE PUBLICACION///////////
  ///////////////////////////////////////////////

  fun agregarAPermitidos(unUsuario: Usuario){ permitidos.add(unUsuario)}
  fun agregarAExcluidos(unUsuario: Usuario){ excluidos.add(unUsuario)}

  fun cambiarPrivacidad(unaPrivacidad: String){
    privacidad = unaPrivacidad
  }

  fun loPuedeVer(unUsuario: Usuario, otroUsuario: Usuario): Boolean{
    /*El primer usuario es la persona dueña de la publicacion, y la segunda es de quien
    quiero averiguar los permisos*/

    var respuesta = false
    if(privacidad == "publico") { respuesta = true }

    if(privacidad == "soloAmigos") {
      if(unUsuario.amigos.contains(otroUsuario)) { respuesta = true }
    }

    if(privacidad == "privadoConPermitidos") {
      if(this.permitidos.contains(otroUsuario)) { respuesta = true }
    }

    if(privacidad == "publicoConExcluidos") {
      if(this.excluidos.contains(otroUsuario)) { respuesta = false }
      else { respuesta = true }
    }
    return respuesta
  }

  //abstract fun mostrarLikes(): Any
}

class Foto(val alto: Int, val ancho: Int) : Publicacion() {
  val factorDeCompresion = 0.7
  override fun espacioQueOcupa() = ceil(alto * ancho * factorDeCompresion).toInt()
}

class Texto(val contenido: String) : Publicacion() {
  override fun espacioQueOcupa() = contenido.length
}

class Video(val segundos : Int) : Publicacion(){
  var calidadActual : String = "SD"

  override fun espacioQueOcupa(): Int {
    var tamanioVideo : Int = segundos

    if (calidadActual == "SD") { tamanioVideo = tamanioVideo }
    if (calidadActual == "HD") { tamanioVideo = tamanioVideo*3}
    if (calidadActual == "HD2") { tamanioVideo = tamanioVideo*6}

    return tamanioVideo
  }

  fun calidad(unaCalidad : String){
    calidadActual = unaCalidad
  }

}