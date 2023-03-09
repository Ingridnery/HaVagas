package br.edu.scl.ifsp.ads.havagas

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.RadioButton
import androidx.annotation.RequiresApi
import br.edu.scl.ifsp.ads.havagas.databinding.ActivityMainBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {
    companion object{
        const val TAG = "CICLO_PDM"
        const val NOME = "NOME"
        const val EMAIL = "EMAIL"
        const val TELEFONE = "TELEFONE"
        const val TIPO_TELEFONE = "TIPOTELEFONE"
        const val SEXO = "SEXO"
        const val DATA_NASCIMENTO = "DATA_NASCIMENTO"
        const val FORMACAO = "FORMACAO"
        const val VAGA_INTERESSE = "VAGA_INTERESSE"
        const val CELULAR = "CELULAR"
        const val ANO_CONCLUSAO="ANO"
        const val INSTITUICAO ="INSTITUICAO"
        const val TITULO="TITULO"
        const val ORIENTADOR="ORIENTADOR"
    }


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

    private fun exibirDados(pessoa: Pessoa){
        val msg : androidx.appcompat.app.AlertDialog.Builder = androidx.appcompat.app.AlertDialog.Builder(this)
        msg.setMessage(pessoa.toString()+pessoa.formacaoInst.toString())
        msg.setTitle("Informações de cadastro:")
        msg.show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val formacao = activityMainBinding.formacaoSp.selectedItem.toString()
        outState.putString(NOME,  activityMainBinding.nomeEt.text.toString())
        outState.putString(EMAIL,  activityMainBinding.emailEt.text.toString())
        outState.putString(TELEFONE,  activityMainBinding.telefoneEt.text.toString())
        outState.putString(TIPO_TELEFONE, activityMainBinding.tipoTelefoneRg.checkedRadioButtonId.toString())
        if(activityMainBinding.adicionarCelularRb.isChecked){
            outState.putString(CELULAR,activityMainBinding.celularEt.text.toString())
        }
        outState.putString(SEXO, activityMainBinding.sexoRg.checkedRadioButtonId.toString())
        outState.putString(DATA_NASCIMENTO,  activityMainBinding.dataNascimentoEt.text.toString())
        outState.putString(VAGA_INTERESSE,  activityMainBinding.vagasEt.text.toString())
        outState.putString(ANO_CONCLUSAO, activityMainBinding.anoFormaturaEt.text.toString())
        outState.putString(FORMACAO, formacao)
        if(formacao == "Graduação" || formacao =="Especialização"){
            outState.putString(INSTITUICAO, activityMainBinding.instituicaoEt.text.toString())
        }
        else if(formacao == "Mestrado" || formacao =="Doutorado"){
            outState.putString(INSTITUICAO, activityMainBinding.instituicaoMestradoEt.text.toString())
            outState.putString(TITULO, activityMainBinding.tituloMestradoEt.text.toString())
            outState.putString(ORIENTADOR, activityMainBinding.orientadorEt.text.toString())

        }

    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val formacao = savedInstanceState.getString(FORMACAO)
        activityMainBinding.nomeEt.setText(savedInstanceState.getString(NOME))
        activityMainBinding.emailEt.setText(savedInstanceState.getString(EMAIL))
        activityMainBinding.telefoneEt.setText(savedInstanceState.getString(TELEFONE))
        if(savedInstanceState.getString(CELULAR) != null){
           activityMainBinding.celularEt.setText(savedInstanceState.getString(CELULAR))
            activityMainBinding.celularEt.visibility= View.VISIBLE

        }
        activityMainBinding.dataNascimentoEt.setText(savedInstanceState.getString(DATA_NASCIMENTO))
        activityMainBinding.vagasEt.setText(savedInstanceState.getString(VAGA_INTERESSE))
        activityMainBinding.anoFormaturaEt.setText(savedInstanceState.getString(ANO_CONCLUSAO))
        if(formacao == "Graduação" || formacao == "Especialização"){
            activityMainBinding.instituicaoEt.setText(savedInstanceState.getString(INSTITUICAO))
        }
        else if(formacao == "Mestrado" || formacao =="Doutorado"){
            activityMainBinding.instituicaoMestradoEt.setText(savedInstanceState.getString(INSTITUICAO))
            activityMainBinding.tituloMestradoEt.setText(savedInstanceState.getString(TITULO))
            activityMainBinding.orientadorEt.setText(savedInstanceState.getString(ORIENTADOR))
        }
    }
    override fun onStart(){
        super.onStart()
        Log.v(TAG,"MainActivity - onStart: Iniciando ciclo de vida visivel")

    }
    override fun onResume(){
        super.onResume()
        Log.v(TAG,"MainActivity - onResume: Iniciando ciclo de vida em primeiro lugar")

    }
    override fun onRestart(){
        super.onRestart()
        Log.v(TAG,"MainActivity - onRestart: Preparando execução do onStart")

    }
    override fun onPause(){
        super.onPause()
        Log.v(TAG,"MainActivity - onPause: Finalizando ciclo de vida em primeiro lugar")
    }

    override fun onStop() {
        super.onStop()
        Log.v(TAG,"MainActivity - onStop: Finalizando ciclo de vida visivel")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v(TAG,"MainActivity - onDestroy: Finalizando ciclo de vida completo")
    }





}