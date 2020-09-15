package ar.edu.unahur.obj2.semillasAlViento

abstract class Planta(val anioObtencionSemilla: Int, var altura: Float) { // Mutación controlada: Altura debería ser val siendo que el objetivo es que no cambie nunca.
  fun esFuerte() = this.horasDeSolQueTolera() > 10

  fun parcelaTieneComplicaciones(parcela: Parcela) =
    parcela.plantas.any { it.horasDeSolQueTolera() < parcela.horasSolPorDia } // Cohesión: Este metodo debería ser de la parcela, no de la planta.

  abstract fun horasDeSolQueTolera(): Int
  // Redundancia minima: daSemillas podría ser una open fun que devuelva this.esFuerte() y las funciones que le hacen override llamar a la super() en vez de repetir tantas veces this.esFuerte()
  abstract fun daSemillas(): Boolean  
}

class Menta(anioObtencionSemilla: Int, altura: Float) : Planta(anioObtencionSemilla, altura) {
  override fun horasDeSolQueTolera() = 6
  override fun daSemillas() = this.esFuerte() || altura > 0.4
}

class Soja(anioObtencionSemilla: Int, altura: Float, val esTransgenica: Boolean) : Planta(anioObtencionSemilla, altura) {
  // (Des)acoplamiento y cohesión: Soja Transgénica debería ser una nueva clase que herede soja, que esté todo acoplado en una sola clase genera problemas de cohesión en la misma.
  override fun horasDeSolQueTolera(): Int  {
    // ¡Magia de Kotlin! El `when` es como un `if` pero más poderoso:
    // evalúa cada línea en orden y devuelve lo que está después de la flecha.
    val horasBase = when {
      altura < 0.5  -> 6
      altura < 1    -> 7
      else          -> 9
    }

    return if (esTransgenica) horasBase * 2 else horasBase
  }


  override fun daSemillas(): Boolean  {
    if (this.esTransgenica) {
      return false
    }

    return this.esFuerte() || (this.anioObtencionSemilla > 2007 && this.altura > 1)
  }
}
