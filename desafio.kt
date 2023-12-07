// [Template no Kotlin Playground](https://pl.kotl.in/WcteahpyN)

enum class Nivel { BASICO, INTERMEDIARIO, AVANCADO }

data class Usuario(val nome: String) {
    val formacoes = mutableListOf<Formacao>()
    val conteudosEducacionis: MutableSet<String> = mutableSetOf()
    
    fun adicionarFormacao(formacao: Formacao) {
        formacoes.add(formacao)
        formacao.conteudos.forEach { adicionarConteudo(it) }
    }
    
    fun adicionarConteudo(conteudo: ConteudoEducacional) {
        conteudosEducacionis.add(conteudo.nome)
    }
}

data class ConteudoEducacional(val nome: String, val duracaoEmMinutos: Int = 60, val nivel: Nivel)

data class Formacao(val nome: String, val conteudos: List<ConteudoEducacional>, val nivel: Nivel) {
    val duracaoEmMinutos: Int = conteudos.fold(0) {acc, cont -> acc + cont.duracaoEmMinutos}
}

data class Curso(val formacao: Formacao) {

    val inscritos = mutableListOf<Usuario>()
    
    fun matricular(vararg usuarios: Usuario) {
        inscritos.addAll(usuarios)
    }
    
    fun desmatricular(vararg usuarios: Usuario) {
        inscritos.removeAll(usuarios)
    }
 
 	fun concluirCurso(usuario: Usuario) {
        usuario.adicionarFormacao(formacao)
        inscritos.remove(usuario)
        println("=== ${usuario.nome} FORMADO(A) ===")
    }
    
    fun concluirConteudo(usuario: Usuario, conteudo: ConteudoEducacional) {
        usuario.adicionarConteudo(conteudo)
        val conteudosDoCurso: List<String> = formacao.conteudos.map { it.nome }
        val conteudosAprendidos: Set<String> = usuario.conteudosEducacionis intersect conteudosDoCurso.toSet()
        if (conteudosAprendidos.size ==  conteudosDoCurso.size) {
            concluirCurso(usuario)
        }
    }
}

fun main() {
    val usuarioAna = Usuario("Ana")
    val usuarioPedro = Usuario("Pedro")
    val usuarioMaria = Usuario("Maria")
    
    val stackGit = ConteudoEducacional("git", 120, Nivel.BASICO)
    val stackKotlin = ConteudoEducacional("kotlin", 4560, Nivel.INTERMEDIARIO)
    val stackJava = ConteudoEducacional("java", 368, Nivel.INTERMEDIARIO)
    val stackCleanCode = ConteudoEducacional("clean code", 220, Nivel.AVANCADO)
    val stackSpringboot = ConteudoEducacional("springboot", 450, Nivel.INTERMEDIARIO)
    
    val kotlinStackList = listOf(stackKotlin, stackGit, stackSpringboot)
    val formacaoKotlin = Formacao("Desenvolvimento kotlin", kotlinStackList, Nivel.INTERMEDIARIO)
    val cursoKotlin = Curso(formacaoKotlin)
    
    val javaStackList = listOf(stackJava, stackGit, stackCleanCode, stackSpringboot)
    val formacaoJava = Formacao("Desenvolvimento java", javaStackList, Nivel.INTERMEDIARIO)
    val cursoJava = Curso(formacaoJava)
    
    cursoKotlin.matricular(usuarioMaria, usuarioPedro)
    cursoJava.matricular(usuarioAna)
    
    println(cursoKotlin)
    println(cursoJava)
    println("----------------------------------------------------------------------------")
    println("$usuarioAna conteudos: ${usuarioAna.conteudosEducacionis} formacoes: ${usuarioAna.formacoes}")
    println("$usuarioPedro conteudos: ${usuarioPedro.conteudosEducacionis} formacoes: ${usuarioPedro.formacoes}")
    println("$usuarioMaria conteudos: ${usuarioMaria.conteudosEducacionis} formacoes: ${usuarioMaria.formacoes}")
    println("----------------------------------------------------------------------------")
    cursoKotlin.concluirConteudo(usuarioMaria, stackKotlin)
    cursoKotlin.concluirConteudo(usuarioMaria, stackGit)
    println("$usuarioMaria conteudos: ${usuarioMaria.conteudosEducacionis} formacoes: ${usuarioMaria.formacoes}")
    println("----------------------------------------------------------------------------")
    cursoKotlin.concluirConteudo(usuarioMaria, stackSpringboot)
    println("$usuarioMaria conteudos: ${usuarioMaria.conteudosEducacionis} formacoes: ${usuarioMaria.formacoes}")
}
