package br.com.climb.test.controller.kotlin

import br.com.climb.framework.annotations.RequestMapping
import br.com.climb.framework.annotations.RestController
import br.com.climb.framework.annotations.mapping.GetMapping
import br.com.climb.framework.annotations.param.PathVariable
import br.com.climb.framework.annotations.param.RequestBody
import br.com.climb.test.model.Cliente

@RestController
@RequestMapping("/get/kotlin")
open class TesteGetControllerKt {

    @GetMapping("/id/{id}/idade/{idade}/nome/{nome}/altura/{altura}/peso/{peso}/casado/{casado}/")
    open fun teste2(@PathVariable("id") id: Long,
                    @PathVariable("idade") idade : Int,
                    @PathVariable("nome") nome : String,
                    @PathVariable("altura") altura : Float,
                    @PathVariable("peso") peso : Double,
                    @PathVariable("casado") casado : Boolean,

                    @RequestBody cliente: Cliente): Cliente? {
        var cliente2 = Cliente()
        cliente2.id = id
        cliente2.idade = idade.toLong()
        cliente2.nome = nome
        cliente2.altura = altura
        cliente2.peso = peso
        cliente2.casado = casado


        return cliente2
    }

}