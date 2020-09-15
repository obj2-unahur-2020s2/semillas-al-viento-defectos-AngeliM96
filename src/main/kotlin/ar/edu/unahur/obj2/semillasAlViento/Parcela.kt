package ar.edu.unahur.obj2.semillasAlViento

class Parcela(val ancho: Int, val largo: Int, val horasSolPorDia: Int) {
  val plantas = mutableListOf<Planta>()
  var cantidadPlantas = 0 // Flexibilidad y facilidad de prueba: cantidadPlantas podría evaluar sobre la lista plantas con un size, siendo una variable hardcodeada genera problemas de mantenibilidad y problemas al querer testear el funcionamiento correcto.

  fun superficie() = ancho * largo
  fun cantidadMaximaPlantas() =
    if (ancho > largo) ancho * largo / 5 else ancho * largo / 3 + largo //Redundancia minima: Utilizando this.superficie() se ahorraria repetir tanto ancho*largo

  fun plantar(planta: Planta) {
    if (cantidadPlantas == this.cantidadMaximaPlantas()) {
      // Robustez: Ambos println deberían ser throw Exception o algún otro sistema para manejar errores, no imprimir a pantalla.
      println("Ya no hay lugar en esta parcela") 
    } else if (horasSolPorDia > planta.horasDeSolQueTolera() + 2) {
      println("No se puede plantar esto acá, se va a quemar")
    } else {
      plantas.add(planta)
      // Flexibilidad: Si se hubiera usado un size sobre la colección plantas, no sería necesario estar sumando 1 a la variable, se calcularía por si mismo al agregar la planta nueva.
      cantidadPlantas += 1
    }
  }
}

// Mutaciones controladas: No se pueden vender ni comprar tierras, no tiene sentido que sea una lista MUTABLE, debería ser solo una List.
class Agricultora(val parcelas: MutableList<Parcela>) {
  var ahorrosEnPesos = 20000 // Simplicidad YAGNI: No se pidió poder manejar dinero y compras en el enunciado, esta variable y el metodo comprarParcela(parcela) son innecesarios.

  // Suponemos que una parcela vale 5000 pesos
  fun comprarParcela(parcela: Parcela) {
    if (ahorrosEnPesos >= 5000) {
      parcelas.add(parcela)
      ahorrosEnPesos -= 5000
    }
  }

  fun parcelasSemilleras() =
    parcelas.filter {
      parcela -> parcela.plantas.all {
        planta -> planta.daSemillas()
      }
    }

  fun plantarEstrategicamente(planta: Planta) {
    val laElegida = parcelas.maxBy { it.cantidadMaximaPlantas() - it.cantidadPlantas }!!
    laElegida.plantas.add(planta)
  }
}
