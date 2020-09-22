package ar.edu.unahur.obj2.semillasAlViento

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
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
        val parcela1 = Parcela(5, 2, 8)
        parcela1.plantar(mentaConSemillas)
        parcela1.plantar(soja3)
        val parcela2 = Parcela(10, 2, 9)
        parcela2.plantar(soja1)
        parcela2.plantar(soja2)
        parcela2.plantar(sojaTransgenica)
        val parcela3 = Parcela(5, 3, 7)
        parcela3.plantar(mentaSinSemillas)
        parcela3.plantar(mentaConSemillas)
        parcela3.plantar(mentaSinSemillas)
        val agricultora = Agricultora(mutableListOf(parcela1, parcela2, parcela3))

        // --- Tests de plantas ---
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
                sojaTransgenica.horasDeSolQuetolera().shouldBe(18)
                sojaTransgenica.esFuerte().shouldBeTrue()
            }
            it("Semillas"){
                sojaTransgenica.daSemillas().shouldBeFalse()
            }
        }
        // --- Tests de parcelas ---
        describe("Parcela 1"){
            it("Superficie"){
                parcela1.superficie().shouldBe()
            }
            it("Cantidad de plantas"){

            }
        }
    }
})