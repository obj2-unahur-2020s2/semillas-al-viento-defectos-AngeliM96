package ar.edu.unahur.obj2.semillasAlViento

class Parcela(val ancho: Int, val largo: Int, val horasSolPorDia: Int) {
  val plantas = mutableListOf<Planta>()

  fun superficie() = ancho * largo
  fun cantidadMaximaPlantas() =
    if (ancho > largo) this.superficie() / 5 else this.superficie() / 3 + largo

  fun plantar(planta: Planta) {
    if (plantas.size == this.cantidadMaximaPlantas()) {
      throw Exception("Ya no hay lugar en esta parcela")
    } else if (horasSolPorDia > planta.horasDeSolQueTolera() + 2) {
      throw Exception("No se puede plantar esto acá, se va a quemar")
    } else {
      plantas.add(planta)
    }
  }

  fun tieneComplicaciones() =
    plantas.any { it.horasDeSolQueTolera() < horasSolPorDia }

}

class Agricultora(val parcelas: List<Parcela>) {
  fun parcelasSemilleras() =
    parcelas.filter {
      parcela -> parcela.plantas.all {
        planta -> planta.daSemillas()
      }
    }

  fun parcelaConLugar() = parcelas.maxBy { it.cantidadMaximaPlantas() - it.plantas.size }!!

  fun plantarEstrategicamente(planta: Planta) {
    this.parcelaConLugar().plantar(planta)
  }
}
