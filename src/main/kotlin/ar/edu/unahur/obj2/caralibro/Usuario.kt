package ar.edu.unahur.obj2.caralibro

class Usuario {
  val publicaciones = mutableListOf<Publicacion>()
  val amigos = mutableListOf<Usuario>()
  val buenosAmigos = mutableListOf<Usuario>()

  fun agregarPublicacion(publicacion: Publicacion) {
    publicaciones.add(publicacion)
  }

  fun espacioDePublicaciones() = publicaciones.sumBy { it.espacioQueOcupa() }

  fun agregarAmigo(amigo: Usuario){ amigos.add(amigo)}

  fun esMasAmistosoque(unUsuario: Usuario): Boolean {
      return this.amigos.size > unUsuario.amigos.size
  }

  fun mejoresAmigos(): List<Usuario> {

    for (publicacion in publicaciones){
        for (usuario in publicacion.permitidos){
          buenosAmigos.add(usuario)
        }
    }

    buenosAmigos.toSet()

    for (publicacion in publicaciones){
      for (usuario in publicacion.excluidos){
        buenosAmigos.remove(usuario)}}

    return buenosAmigos
  }
}
