package ar.edu.unahur.obj2.caralibro

import io.kotest.core.spec.style.DescribeSpec
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
      //describe("privacidad de una publicacion"){ }
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
        }

      }
    }
  }
})
