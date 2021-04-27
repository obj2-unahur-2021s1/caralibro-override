package ar.edu.unahur.obj2.caralibro

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe

class UsuarioTest : DescribeSpec({
  describe("Caralibro") {
    val saludoCumpleanios = Texto("Felicidades Pepito, que los cumplas muy feliz")
    val fotoEnCuzco = Foto(768, 1024)
    val unVideo = Video(120)

    describe("Una publicación") {
      describe("de tipo foto") {
        it("ocupa ancho * alto * compresion bytes") {
          fotoEnCuzco.espacioQueOcupa().shouldBe(550503)
        }
      }
      describe("de tipo texto") {
        it("ocupa tantos bytes como su longitud") {
          saludoCumpleanios.espacioQueOcupa().shouldBe(45)
        }
      }
      describe("de tipo video"){
        it("ocupa segundos de video * calidad"){
          unVideo.espacioQueOcupa().shouldBe(120)
          unVideo.calidad("HD")
          unVideo.espacioQueOcupa().shouldBe(360)
          unVideo.calidad("HD2")
          unVideo.espacioQueOcupa().shouldBe(720)
          unVideo.calidad("SD")
          unVideo.espacioQueOcupa().shouldBe(120)
        }
      }
      describe("me gustas de una publicacion"){
        it("agregar likes a la publicacion"){
          val Pepe = Usuario()
          val Ramon = Usuario()

          saludoCumpleanios.agregarLikeDe(Pepe)
          saludoCumpleanios.agregarLikeDe(Ramon)
          fotoEnCuzco.agregarLikeDe(Pepe)
          unVideo.agregarLikeDe(Ramon)
        }
        it("cantidad de Likes"){
          val Pepe = Usuario()
          val Ramon = Usuario()

          saludoCumpleanios.agregarLikeDe(Pepe)
          saludoCumpleanios.agregarLikeDe(Ramon)
          saludoCumpleanios.cantidadDeLikes().shouldBe(2)

          fotoEnCuzco.agregarLikeDe(Pepe)
          fotoEnCuzco.cantidadDeLikes().shouldBe(1)

          unVideo.agregarLikeDe(Ramon)
          unVideo.cantidadDeLikes().shouldBe(1)
        }

        /*it("personas que dieron Like a una publicacion") {
          val Pepe = Usuario()
          val Ramon = Usuario()

          saludoCumpleanios.agregarLikeDe(Pepe)
          saludoCumpleanios.agregarLikeDe(Ramon)
          saludoCumpleanios.mostrarLikes().shuldBe()

          fotoEnCuzco.agregarLikeDe(Pepe)
          fotoEnCuzco.mostrarLikes().shouldBe()

          unVideo.agregarLikeDe(Ramon)
          unVideo.mostrarLikes().shouldBe()
        }*/
      }
      describe("privacidad de una publicacion"){
        it("si un usuario puede ver una publicacion"){
          /*
          público: cualquier usuario puede ver la publicación,
          sólo amigos: sólo los amigos pueden verla,
          privado con lista de permitidos: el usuario configura una lista que vale para
          esa publicación, y solo los usuarios que pertenezcan a esa lista pueden verla,
          público con lista de excluidos: similar al anterior, pero en este caso todos pueden
          ver la publicación excepto quienes están en la lista.
          */

          val Pepe = Usuario()
          val Ramon = Usuario()
          val Carlos = Usuario()

          Ramon.agregarAmigo(Pepe)
          unVideo.loPuedeVer(Ramon, Pepe).shouldBe(true)
          unVideo.loPuedeVer(Ramon, Carlos).shouldBe(true)
          unVideo.cambiarPrivacidad("soloAmigos")
          unVideo.loPuedeVer(Ramon, Pepe).shouldBe(true)
          unVideo.loPuedeVer(Ramon, Carlos).shouldBe(false)
          fotoEnCuzco.cambiarPrivacidad("privadoConPermitidos")
          fotoEnCuzco.agregarAPermitidos(Carlos)
          fotoEnCuzco.loPuedeVer(Ramon, Carlos).shouldBe(true)
          fotoEnCuzco.loPuedeVer(Ramon, Pepe).shouldBe(false)
          saludoCumpleanios.cambiarPrivacidad("publicoConExcluidos")
          saludoCumpleanios.agregarAExcluidos(Carlos)
          saludoCumpleanios.loPuedeVer(Ramon, Carlos).shouldBe(false)
          saludoCumpleanios.loPuedeVer(Ramon, Pepe).shouldBe(true)

        }
      }
    }

    describe("Un usuario") {
      it("puede calcular el espacio que ocupan sus publicaciones") {
        val Pepe = Usuario()
        val Ramon = Usuario()
        val juana = Usuario()

        juana.agregarPublicacion(fotoEnCuzco)
        juana.agregarPublicacion(saludoCumpleanios)
        juana.espacioDePublicaciones().shouldBe(550548)
        juana.agregarPublicacion(unVideo)
      }

      describe("usuario mas amistoso") {
        it("el usuario con mayor cantidad de amigos es el mas amistoso"){
          val Ramon = Usuario()
          val juana = Usuario()

          juana.agregarAmigo(Ramon)
          juana.esMasAmistosoque(Ramon).shouldBe(true)
          Ramon.esMasAmistosoque(juana).shouldBe(false)
        }
      }

      describe("mejores amigos de un usuario") {
        it("El conjunto de amigos que pueden ver todas sus publicaciones"){
          val Pepe = Usuario()
          val Ramon = Usuario()
          val juana = Usuario()
          val Carlos = Usuario()

          juana.agregarPublicacion(fotoEnCuzco)
          juana.agregarPublicacion(saludoCumpleanios)
          juana.agregarPublicacion(unVideo)

          juana.agregarAmigo(Ramon)
          juana.agregarAmigo(Pepe)
          juana.agregarAmigo(Carlos)

          fotoEnCuzco.cambiarPrivacidad("privadoConPermitidos")
          fotoEnCuzco.agregarAPermitidos(Carlos)
          fotoEnCuzco.agregarAPermitidos(Ramon)
          fotoEnCuzco.agregarAPermitidos(Pepe)

          saludoCumpleanios.cambiarPrivacidad("publicoConExcluidos")
          saludoCumpleanios.agregarAExcluidos(Carlos)


          juana.mejoresAmigos().shouldContain(Pepe)
          juana.mejoresAmigos().shouldContain(Ramon)

        }
      }

      describe("Amigo mas popular"){
        it("el amigo con mas likes entre todas sus publicaciones"){
          val Ramon = Usuario()
          val juana = Usuario()
          val Carlos = Usuario()

          val comentario = Texto("Un comentario cortito")
          val fotoOvelisco = Foto(768, 1024)
          val videoDeMono = Video(120)


          juana.agregarPublicacion(fotoEnCuzco)
          juana.agregarPublicacion(saludoCumpleanios)
          juana.agregarPublicacion(unVideo)

          Carlos.agregarPublicacion(comentario)
          Carlos.agregarPublicacion(fotoOvelisco)
          Carlos.agregarPublicacion(videoDeMono)

          comentario.agregarLikeDe(Ramon)
          comentario.agregarLikeDe(juana)
          fotoOvelisco.agregarLikeDe(Ramon)
          fotoOvelisco.agregarLikeDe(juana)
          videoDeMono.agregarLikeDe(juana)
          videoDeMono.agregarLikeDe(Ramon)

          Ramon.agregarAmigo(juana)
          Ramon.agregarAmigo(Carlos)

          Carlos.cantidadLikesTotales().shouldBe(6)
          juana.cantidadLikesTotales().shouldBe(0)

          Ramon.amigoMasPopular().shouldBe(Carlos)
        }
      }

    }
  }
})
