package ar.edu.unahur.obj2.semillasAlViento

import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.comparables.shouldBeLessThan
import io.kotest.matchers.shouldBe

class SemillasAlVientoTest : DescribeSpec({
    describe("una huerta"){
        val mentaSinSemillas = Menta(2019, 0.3f)
        val mentaConSemillas = Menta(2018, 0.65f)
        val soja1 = Soja(2006, 0.43f)
        val soja2 = Soja(2009, 0.846f)
        val soja3 = Soja(2008, 1.21f)
        val sojaTransgenica = SojaTransgenica(2008, 1.14f)
        // Tuve que hacer cambios a las alturas y horas de sol para poder establecer el escenario inicial sin problemas
        val parcela1 = Parcela(5, 3, 6)
        parcela1.plantar(mentaConSemillas)
        parcela1.plantar(mentaConSemillas)
        val parcela2 = Parcela(10, 2, 8)
        parcela2.plantar(soja1)
        parcela2.plantar(soja2)
        // Modificado el escenario, había dos parcelas testeando las mismas funcionalidades innecesariamente, esta nueva testea el otro caso
        // en que puede haber un throw exception y se utiliza en agricultora.
        val parcela3 = Parcela(10, 5, 20)


        describe("Plantas"){
            describe("Menta"){
                it("Fuerza"){
                    mentaConSemillas.horasDeSolQueTolera().shouldBeLessThan(10)
                    mentaConSemillas.esFuerte().shouldBeFalse()
                    mentaSinSemillas.horasDeSolQueTolera().shouldBeLessThan(10)
                    mentaSinSemillas.esFuerte().shouldBeFalse()
                }
                it("Semillas"){
                    mentaSinSemillas.daSemillas().shouldBeFalse()
                    mentaConSemillas.daSemillas().shouldBeTrue()
                }
            }
            describe("Soja"){
                it("Fuerza"){
                    soja1.horasDeSolQueTolera().shouldBe(6)
                    soja1.esFuerte().shouldBeFalse()
                    soja2.horasDeSolQueTolera().shouldBe(7)
                    soja2.esFuerte().shouldBeFalse()
                    soja3.horasDeSolQueTolera().shouldBe(9)
                    soja3.esFuerte().shouldBeFalse()
                }
                it("Semillas"){
                    soja1.daSemillas().shouldBeFalse()
                    soja2.daSemillas().shouldBeFalse()
                    soja3.daSemillas().shouldBeTrue()
                }
            }
            describe("Soja Transgenica"){
                it("Fuerza"){
                    sojaTransgenica.horasDeSolQueTolera().shouldBe(18)
                    sojaTransgenica.esFuerte().shouldBeTrue()
                }
                it("Semillas"){
                    sojaTransgenica.daSemillas().shouldBeFalse()
                }
            }
        }

        describe("Parcelas"){
            describe("Parcela 1"){
                it("Superficie"){
                    parcela1.superficie().shouldBe(15)
                }
                it("Cantidad de plantas"){
                    parcela1.cantidadMaximaPlantas().shouldBe(3)
                }
                it("Complicaciones"){
                    parcela1.tieneComplicaciones().shouldBeFalse()
                }
                it("Se puede plantar"){
                    // Cambiado cantidadPlantas por plantas.size al borrar la variable por ser innecesaria (redundancia minima)
                    parcela1.plantas.size.shouldBe(2)
                    parcela1.plantar(mentaConSemillas)
                    parcela1.plantas.size.shouldBe(3)
                }
            }

            describe("Parcela 2"){
                it("Superficie"){
                    parcela2.superficie().shouldBe(20)
                }
                it("Cantidad de plantas"){
                    parcela2.cantidadMaximaPlantas().shouldBe(4)
                }
                it("Complicaciones"){
                    parcela2.tieneComplicaciones().shouldBeTrue()
                }
                it("Se puede plantar"){
                    parcela2.plantas.size.shouldBe(2)
                    parcela2.plantar(soja3)
                    parcela2.plantar(soja3)
                    parcela2.plantas.size.shouldBe(4)
                    shouldThrowAny {
                        parcela2.plantar(sojaTransgenica)
                    }
                }
            }

            describe("Parcela 3") {
                it("Superficie") {
                    parcela3.superficie().shouldBe(50)
                }
                it("Cantidad de plantas") {
                    parcela3.cantidadMaximaPlantas().shouldBe(10)
                }
                it("Complicaciones") {
                    parcela3.tieneComplicaciones().shouldBeFalse()
                }
                it("Se puede plantar") {
                    shouldThrowAny {
                        parcela3.plantar(mentaConSemillas)
                    }
                    parcela3.plantas.shouldBeEmpty()
                    parcela3.plantar(sojaTransgenica)
                    parcela3.plantas.size.shouldBe(1)
                }
            }
        }

        describe("Agricultora"){
            val agricultora = Agricultora(mutableListOf(parcela1, parcela2, parcela3))
            it("Parcelas Semilleras"){
                agricultora.parcelasSemilleras().shouldContainAll(listOf(parcela1))
            }
            it("Plantar Estrategicamente"){
                agricultora.plantarEstrategicamente(sojaTransgenica)
                agricultora.parcelas.filter { it.plantas.contains(sojaTransgenica) }.shouldContainAll(listOf(parcela3))
            }
        }
    }
})