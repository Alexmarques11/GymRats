# GymRats - Android

## Introdução
No âmbito desta Unidade Curricular de Desenvolvimento de Jogos para Plataformas Móveis foi apresentada uma proposta de trabalho que visava a criação e o desenvolvimento de um jogo ou aplicação. Para tal, teríamos de utilizar o Android Studio e a linguagem de programação Kotlin na criação da nossa resposta, também como a ferramenta FireBase, que nos permite guardar os dados do jogo ou aplicação numa Cloud.
Em resposta a esta proposta de trabalho, decidimos criar a GymRats, uma aplicação destinada a facilitar a organização e o progresso de todos aqueles que procuram melhorarem-se a si mesmos através da prática de musculação em ginásios.

## Desenvolvimento
No desenvolvimento da GymRats estabelecemos várias activities e fragments essenciais para a construção da resposta que pretendíamos apresentar. Estas activities e fragments estão interligados no código da aplicação de modo a proporcionar a experiência que pretendemos na navegação da aplicação. Algumas das activities e fragments que desenvolvemos estão apresentadas a seguir:

## Main Activity
A Activity que realiza a inicialização da aplicação, começando por abrir a mesma no ecrã de Login e direcionando o utilizador para o fragmento Home após ser realizado o Login.

```kotlin
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_planos,
                R.id.navigation_calendar,
                R.id.navigation_profile,
                R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}
```

## Register Activity
A Activity no qual um novo utilizador pode realizar o seu registo caso ainda não o tenha feito. Aqui, o utilizador deve inserir um email válido e também uma password que deverá confirmar. Através do FireBase, os dados introduzidos são guardados e serão posteriormente verificados quando o utilizador realizar o Login na aplicação.

## Login Activity
A Activity no qual o utilizador introduz os seus dados e caso os mesmos estejam registados na base de dados proporcionada pelo FireBase, o utilizador conseguirá entrar no seu perfil na aplicação. No entanto, caso o utilizador não esteja ainda registado, ainda nesta Activity consegue ser redirecionado para a Activity Register através de um botão designado para tal.

## Home
O fragment Home é a primeira página que é apresentada ao utilizador após realizar o Login na aplicação. Aqui é possível encontrar algumas frases que pretendem despertar ação e motivação no utilizador, para que continue a sua jornada de autodesenvolvimento da melhor forma possível.

## Planos
O fragment Planos consiste numa lista, na qual o utilizador pode acrescentar planos de treino que realizou ou que pretenda realizar. Estes planos possuem um título, determinado pelo utilizador, e também uma data, correspondente àquela que o utilizador pretenda realizar ou já tenha realizado o plano. 

## Calendário
No fragment Calendário podemos visualizar o calendário do mês em que nos encontramos, podendo ainda navegar para outros meses para que o utilizador consiga planear da melhor forma os seus planos de treino. É ainda possível visualizar aqui a lista dos planos de treino já registados. Posteriormente, é pretendido que cada dia que possuir um plano seja notado no próprio calendário.

## Notificações
No fragment Notificações, pretendemos acrescentar uma lista de notificações que o utilizador receberia à cerca de vários assuntos relacionados ao estilo de vida de prática de musculação e outros tipos de exercício físico e também notificações relativas ao seu progresso conforme a realização de determinado número de planos de treino.

## Perfil
No fragment Perfil é possível verificar e editar o perfil do utilizador. Aqui, o utilizador pode inserir alguns dados pessoais para acompanhar melhor o seu progresso, como a sua idade, género, altura e peso.

## Conclusão
Esta proposta de trabalho revelou-se um desafio, até agora, único naquilo que tem sido o nosso progresso enquanto alunos do curso de Engenharia em Desenvolvimento de Jogos Digitais. Esta unicidade deve-se ao facto de a nossa resposta ser a primeira aplicação desenvolvida na sua totalidade por nós, à exceção da utilização do FireBase, e por ser ainda o nosso primeiro projeto que se enquadraria no mercado de aplicações para dispositivos móveis.
Contudo, realçamos algumas dificuldades enfrentadas no desenvolvimento desta nossa resposta, nomeadamente alguma dificuldade na implementação do FireBase, posteriormente ultrapassada com o auxílio do professor, e também a dificuldade na concretização de alguns dos objetivos previamente definidos por nós nos primeiros momentos de definição daquilo que pretendíamos para a nossa aplicação.
Ainda assim, sublinhamos que este projeto se revelou essencial no nosso crescimento enquanto programadores, desenvolvedores de jogos e aplicações e ainda como alunos deste curso. Através do desenvolvimento da GymRats adquirimos novas capacidades, essencialmente no desenvolvimento de aplicações destinadas a dispositivos móveis e também na utilização de novas ferramentas extremamente uteis, caso do FireBase.
