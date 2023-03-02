package br.edu.scl.ifsp.ads.havagas

import java.time.LocalDate
import java.util.*

class Pessoa(
    var nome: String, val email: String, val telefone: Telefone, val tipoTelefone:String, val sexo: String,
    val dataNascimento: LocalDate, val formacao: String, val vagaInteresse: String,
    var celular : Telefone? =null, var formacaoInst: Formacao?) {



    class Telefone(
        var numero: String
    )
    sealed class Formacao {

            class Ensino(val anoConclusao: Int): Formacao(){
            override fun toString(): String {
            return "Ano Conclusão: $anoConclusao"
            }
        }

        class GraduacaoEspecializacao(val instituicao: String,val anoConclusao: Int) : Formacao() {
            override fun toString(): String {
                return " Instituição: $instituicao \n Ano Conclusão: $anoConclusao"
            }
        }
        class MestradoDoutorado( val instituicao: String, val tituloMonografia: String, val orientador: String,val anoConclusao: Int) : Formacao() {
            override fun toString(): String {
                return " Instituição: $instituicao, \n Título da monografia: $tituloMonografia \n Orientador: $orientador \n Ano de conclusão $anoConclusao"
            }
        }
    }


    override fun toString(): String {


        return " Nome: $nome \n Email: $email \n Telefone: ${telefone.numero} $tipoTelefone" +
                "\n Sexo: $sexo \n Data de nascimento: $dataNascimento \n" +
                " Interesse em vagas: $vagaInteresse \n Formação: $formacao \n Celular: ${celular?.numero ?:"Não cadastrado"}\n"
    }


}