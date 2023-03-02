package br.edu.scl.ifsp.ads.havagas

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.RadioButton
import androidx.annotation.RequiresApi
import br.edu.scl.ifsp.ads.havagas.databinding.ActivityMainBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {


    private val activityMainBinding: ActivityMainBinding by lazy{ActivityMainBinding.inflate(layoutInflater)}
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)

        with(activityMainBinding){

            formacaoSp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (position) {
                        2, 3 -> {
                            graduacaoLl.visibility=View.VISIBLE
                            mestradoLl.visibility=View.INVISIBLE

                        }
                        4, 5 -> {
                            mestradoLl.visibility= View.VISIBLE
                            graduacaoLl.visibility = View.INVISIBLE
                        }
                        else -> {
                            mestradoLl.visibility= View.INVISIBLE
                            graduacaoLl.visibility = View.INVISIBLE

                        }
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
            adicionarCelularRb.setOnClickListener {
                celularEt.visibility=View.VISIBLE
            }

            salvarBt.setOnClickListener {

                val sexo = findViewById<RadioButton>(sexoRg.checkedRadioButtonId).text
                val tipoTelefone = findViewById<RadioButton>(tipoTelefoneRg.checkedRadioButtonId).text

                val string = dataNascimentoEt.text.toString()

                val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.GERMANY)
                val dataNascimento = LocalDate.parse(string,formatter)
                val telefone = Pessoa.Telefone(telefoneEt.text.toString())


                val pessoa = Pessoa(nomeEt.text.toString(), emailEt.text.toString(),telefone,tipoTelefone.toString(),
                    sexo.toString(),dataNascimento,formacaoSp.selectedItem.toString(),vagasEt.text.toString(),null,null)

                if(adicionarCelularRb.isChecked){
                    val celular = Pessoa.Telefone(celularEt.text.toString())
                    pessoa.celular = celular

                }
                if(formacaoSp.selectedItem.toString()== "Graduação" || formacaoSp.selectedItem.toString()=="Especialização"){
                    val formacao = Pessoa.Formacao.GraduacaoEspecializacao(instituicaoEt.text.toString(),anoFormaturaEt.text.toString().toInt())
                    pessoa.formacaoInst=formacao
                }
                else if(formacaoSp.selectedItem.toString()== "Mestrado" || formacaoSp.selectedItem.toString()=="Doutorado"){
                    val formacao = Pessoa.Formacao.MestradoDoutorado(instituicaoMestradoEt.text.toString(),tituloMestradoEt.text.toString(),orientadorEt.text.toString(),anoFormaturaEt.text.toString().toInt())
                    pessoa.formacaoInst=formacao

                }
                else{
                    val formacao = Pessoa.Formacao.Ensino(anoFormaturaEt.text.toString().toInt())
                    pessoa.formacaoInst=formacao
                }

                exibirDados(pessoa)

            }
            limparBt.setOnClickListener {
                nomeEt.setText("")
                emailEt.setText("")
                telefoneEt.setText("")
                celularEt.setText("")
                dataNascimentoEt.setText("")
                vagasEt.setText("")
                anoFormaturaEt.setText("")
                instituicaoEt.setText("")
                instituicaoMestradoEt.setText("")
                tituloMestradoEt.setText("")
                orientadorEt.setText("")
                adicionarCelularRb.visibility = View.INVISIBLE
                graduacaoLl.visibility= View.INVISIBLE
                mestradoLl.visibility= View.INVISIBLE

            }

        }
    }
    fun exibirDados(pessoa: Pessoa){
        val msg : androidx.appcompat.app.AlertDialog.Builder = androidx.appcompat.app.AlertDialog.Builder(this)
        msg.setMessage(pessoa.toString()+pessoa.formacaoInst.toString())
        msg.setTitle("Informações de cadastro:")
        msg.show()
    }
}