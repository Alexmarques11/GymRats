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

```kotlin
class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonRegister.setOnClickListener {

            val email = binding.editTextTextEmailAddress.text.toString()
            val password = binding.editTextTextPassword.text.toString()
            val password2 = binding.editTextTextPassword2.text.toString()

            if (password != password2){
                Toast.makeText(
                    baseContext,
                    "Passwords do not match.",
                    Toast.LENGTH_SHORT,
                ).show()
                return@setOnClickListener
            }

            if (!password.isPasswordValid()){
                Toast.makeText(
                    baseContext,
                    "Password must have at least 6 chars.",
                    Toast.LENGTH_SHORT,
                ).show()
                return@setOnClickListener
            }

            if (!email.isValidEmail()){
                Toast.makeText(
                    baseContext,
                    "Email is not valid.",
                    Toast.LENGTH_SHORT,
                ).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.${task.exception}",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
        }
    }
}
```

## Login Activity
A Activity no qual o utilizador introduz os seus dados e caso os mesmos estejam registados na base de dados proporcionada pelo FireBase, o utilizador conseguirá entrar no seu perfil na aplicação. No entanto, caso o utilizador não esteja ainda registado, ainda nesta Activity consegue ser redirecionado para a Activity Register através de um botão designado para tal.

```kotlin
const val TAG = "gymratslist"
class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.buttonLogin.setOnClickListener {

            val email = binding.editTextTextEmailAddress.text.toString()
            val password = binding.editTextTextPassword.text.toString()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {


                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed. ${task.exception}",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }

        }

        binding.buttonRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
```

## Home
O fragment Home é a primeira página que é apresentada ao utilizador após realizar o Login na aplicação. Aqui é possível encontrar algumas frases que pretendem despertar ação e motivação no utilizador, para que continue a sua jornada de autodesenvolvimento da melhor forma possível.

```kotlin 
class HomeFragment : Fragment() {

    private lateinit var greetingTextView: TextView

    private val motivationalMessages = listOf(
        "Acredite em si mesmo e todo o resto virá naturalmente.",
        "O único lugar onde o sucesso vem antes do trabalho é no dicionário.",
        "Nunca é tarde demais para ser o que você poderia ter sido.",
        "Treine muito, mas muito mesmo, e quando estiver bem cansado, treine mais um pouquinho, porque esse pouquinho vai te fazer melhor.",
        "Até hoje, para cada não que recebo, vou atrás de um sim!",
        "Eu não sou o melhor não, mas sou capaz de fazer coisas que muitas pessoas não acreditam.",
        "Não tenha medo de falhar. Esse é o caminho para o sucesso.",
        "Confie em mim, a recompensa não é tão grande sem a luta.",
        "Aquele que não é corajoso o suficiente para correr riscos não conseguirá nada na vida.",
        "Não são as montanhas à frente que te deixam esgotado, mas sim as pedras no sapato.",
        "Habilidade só se desenvolve com horas e horas de trabalho.",
        "Quem disse ‘Não interessa ganhar ou perder’ provavelmente perdeu.",
        "Vencer é algo que se constrói a cada treino e a cada vez que sonho.",
        "Se você vai olhar para trás, que seja para ver o que você trabalhou para chegar onde está.",
        "Os bons reclamam, os melhores se adaptam.",
        "Você não pode colocar um limite em nada. Quanto mais você sonha, mais longe você chega."
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        greetingTextView = view.findViewById(R.id.greetingTextView)

        setGreeting()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val motivationalMessageTextView: TextView = view.findViewById(R.id.motivationalMessageTextView)
        motivationalMessageTextView.text = getRandomMotivationalMessage()
    }

    private fun setGreeting() {
        val calendar = Calendar.getInstance()
        val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)

        val greeting = when (hourOfDay) {
            in 0..11 -> "Bom Dia! É de manhã que começa o dia!"
            in 12..17 -> "Boa Tarde! Ótima hora para treinar"
            else -> "Boa Noite! Já está escuro mas há sempre tempo para um treino"
        }

        greetingTextView.text = greeting
    }

    private fun getRandomMotivationalMessage(): String {
        val randomIndex = (0 until motivationalMessages.size).random()
        return motivationalMessages[randomIndex]
    }
}

```

## Planos
O fragment Planos consiste numa lista, na qual o utilizador pode acrescentar planos de treino que realizou ou que pretenda realizar. Estes planos possuem um título, determinado pelo utilizador, e também uma data, correspondente àquela que o utilizador pretenda realizar ou já tenha realizado o plano. 

```kotlin

class PlanosFragment : Fragment() {

    var planos :List<Planos> = arrayListOf<Planos>()

    val adapter = PlanosAdapter()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_planos, container, false)

        val listView = rootView.findViewById<ListView>(R.id.listViewPlanos)
        listView.adapter = adapter

        rootView.findViewById<Button>(R.id.buttonDone).setOnClickListener {
            val intent = Intent(requireContext(), AddPlanosActivity::class.java)
            startActivity(intent)
        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val planosDao = AppDatabase.getDatabase(requireContext())?.getPlanosDao()
        planosDao?.getAll()?.observe(viewLifecycleOwner, Observer {
            planos = it.sortedBy { plano -> plano.date }
            adapter.notifyDataSetChanged()
        })
    }

    inner class PlanosAdapter : BaseAdapter() {
        override fun getCount(): Int {
            return planos.count()
        }

        override fun getItem(p0: Int): Any {
            return planos[p0]
        }

        override fun getItemId(p0: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rootView = layoutInflater.inflate(R.layout.row_planos, parent, false)
            val textViewTipoTreino = rootView.findViewById<TextView>(R.id.textViewTipoTreino)
            val textViewDataPlano = rootView.findViewById<TextView>(R.id.textViewDataPlano)
            val checkBox = rootView.findViewById<CheckBox>(R.id.checkBox)

            textViewTipoTreino.text = planos[position].trainingType
            textViewDataPlano.text = planos[position].date.toShortDateTime()

            checkBox.setOnClickListener {
                planos[position].isChecked = checkBox.isChecked
            }


            rootView.setOnClickListener {
                val intent = Intent(requireActivity(), AddPlanosActivity::class.java)
                intent.putExtra(AddPlanosActivity.DATA_TRAINING, planos[position].trainingType)
                intent.putExtra(AddPlanosActivity.DATA_DATE, planos[position].date.time)

                intent.putExtra(AddPlanosActivity.DATA_PLAN_ID,  planos[position].id)

                startActivity(intent)
            }

            return rootView
        }
    }
}

```
## AddPlanosActivity

A AddPlanosActivity é uma atividade em um aplicativo Android que permite aos usuários adicionar ou editar planos de treinamento. A interface inclui campos para o tipo de treino e a data planejada, enquanto a interação com o banco de dados local facilita a persistência dos planos. Os usuários podem também selecionar a data por meio de um seletor e excluir planos existentes. Essa funcionalidade proporciona ao usuário flexibilidade na gestão dos seus planos de treino.

```kotlin
class AddPlanosActivity : AppCompatActivity() {

    private var position: Int = -1
    private var timestamp: Long = 0
    private var planId : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_planos)

        val trainingType = findViewById<EditText>(R.id.editTextTrainingType)
        val date = findViewById<EditText>(R.id.editTextDate)

        intent.extras?.let {
            position = it.getInt(DATA_POSITION, -1)
            timestamp = it.getLong(DATA_DATE)
            val training = it.getString(DATA_TRAINING)
            trainingType.setText(training)
            findViewById<EditText>(R.id.editTextDate).setText( Date(timestamp).toShortDateTime())

           planId = it.getString(DATA_PLAN_ID)

        }

        findViewById<Button>(R.id.buttonDone)?.setOnClickListener {
            if (planId == null) {
                val newPlano = Planos(
                    UUID.randomUUID().toString(),
                    trainingType.text.toString(),
                    if (timestamp != 0L) Date(timestamp) else Date(),
                    false
                )

                lifecycleScope.launch(Dispatchers.IO) {
                    val planosDao = AppDatabase.getDatabase(this@AddPlanosActivity)?.getPlanosDao()
                    planosDao?.insert(newPlano)
                    withContext(Dispatchers.Main) {
                        finish()
                    }
                }
            }else{
                val newPlano = Planos(
                    planId!!,
                    trainingType.text.toString(),
                    if (timestamp != 0L) Date(timestamp) else Date(),
                    false
                )

                lifecycleScope.launch(Dispatchers.IO) {
                    val planosDao = AppDatabase.getDatabase(this@AddPlanosActivity)?.getPlanosDao()
                    planosDao?.insert(newPlano)
                    withContext(Dispatchers.Main) {
                        finish()
                    }
                }
            }
        }
        findViewById<ImageButton>(R.id.imageButtonPicKDate).setOnClickListener {
            val newFragment = DatePickerFragment()
            newFragment.show(supportFragmentManager, "datePicker")
            newFragment.onDateSet = {
                this.timestamp = it.time
                findViewById<EditText>(R.id.editTextDate).setText( it.toShortDateTime())
            }
        }

        findViewById<ImageButton>(R.id.imageButtonDelete).setOnClickListener {
            val newPlano = Planos(
                planId!!,
                trainingType.text.toString(),
                if (timestamp != 0L) Date(timestamp) else Date(),
                false
            )

            lifecycleScope.launch(Dispatchers.IO) {
                val planosDao = AppDatabase.getDatabase(this@AddPlanosActivity)?.getPlanosDao()
                planosDao?.delete(newPlano)
                withContext(Dispatchers.Main) {
                    finish()
                }
            }
        }
    }

    companion object {
        const val DATA_TRAINING = "data_training"
        const val DATA_DATE = "data_date"
        const val DATA_POSITION = "data_position"
        const val DATA_PLAN_ID = "data_plan_id"
    }
}

```

## Calendário
No fragment Calendário podemos visualizar o calendário do mês em que nos encontramos, podendo ainda navegar para outros meses para que o utilizador consiga planear da melhor forma os seus planos de treino. É ainda possível visualizar aqui a lista dos planos de treino já registados. Posteriormente, é pretendido que cada dia que possuir um plano seja notado no próprio calendário.

```kotlin
class CalendarFragment : Fragment() {

    var calendarPlanos :List<Planos> = arrayListOf<Planos>()

    val adapter = CalendarAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_calendar, container, false)

        val listView = rootView.findViewById<ListView>(R.id.listViewCalendar)
        listView.adapter = adapter

        val calendarView = rootView.findViewById<CalendarView>(R.id.calendar_view)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val planosDao = AppDatabase.getDatabase(requireContext())?.getPlanosDao()
        planosDao?.getAll()?.observe(viewLifecycleOwner, Observer {
            calendarPlanos = it.sortedBy { plano -> plano.date }
            adapter.notifyDataSetChanged()
        })
    }

    inner class CalendarAdapter : BaseAdapter() {
        override fun getCount(): Int {
            return calendarPlanos.count()
        }

        override fun getItem(p0: Int): Any {
            return calendarPlanos[p0]
        }

        override fun getItemId(p0: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rootView = layoutInflater.inflate(R.layout.row_calendar, parent, false)
            val textViewTipoTreino = rootView.findViewById<TextView>(R.id.textViewTrainingCalendar)
            val textViewDataPlano = rootView.findViewById<TextView>(R.id.textViewDateCalendar)

            textViewTipoTreino.text = calendarPlanos[position].trainingType
            textViewDataPlano.text = calendarPlanos[position].date.toShortDateTime()

            return rootView
        }
    }

}

```

## Notificações (Inacabada)
No fragment Notificações, pretendemos acrescentar uma lista de notificações que o utilizador receberia à cerca de vários assuntos relacionados ao estilo de vida de prática de musculação e outros tipos de exercício físico e também notificações relativas ao seu progresso conforme a realização de determinado número de planos de treino.

```kotlin
class NotificationsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

}
```

## Perfil
No fragment Perfil é possível verificar e editar o perfil do utilizador. Aqui, o utilizador pode inserir alguns dados pessoais para acompanhar melhor o seu progresso, como a sua idade, género, altura e peso.

```kotlin
class ProfileFragment : Fragment() {

    private lateinit var editButton: ImageButton
    private lateinit var nameEditText: EditText
    private lateinit var ageEditText: EditText
    private lateinit var heightEditText: EditText
    private lateinit var weightEditText: EditText
    private lateinit var genderEditText: EditText
    private lateinit var emailEditText: EditText

    private var isEditMode = false
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_profile, container, false)

        editButton = rootView.findViewById(R.id.imageButtonEdit)
        nameEditText = rootView.findViewById(R.id.editTextName)
        ageEditText = rootView.findViewById(R.id.editTextPutAge)
        heightEditText = rootView.findViewById(R.id.editTextHeight)
        weightEditText = rootView.findViewById(R.id.editTextWeight)
        genderEditText = rootView.findViewById(R.id.editTextGender)
        emailEditText = rootView.findViewById(R.id.editTextEmailProfile)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        // Carrega os dados do Firebase Firestore quando a tela é criada
        loadUserData(currentUser?.email)

        editButton.setOnClickListener {
            if (isEditMode) {
                saveData(currentUser?.email)
            } else {
                toggleEditMode()
            }
        }

        return rootView
    }

    private fun toggleEditMode() {
        isEditMode = !isEditMode
        updateView()
    }

    private fun updateView() {
        if (isEditMode) {
            enableEditing()
            editButton.setImageResource(R.drawable.baseline_save_24)
        } else {
            disableEditing()
            editButton.setImageResource(R.drawable.baseline_brush_24)
        }
    }

    private fun enableEditing() {
        nameEditText.isEnabled = true
        ageEditText.isEnabled = true
        heightEditText.isEnabled = true
        weightEditText.isEnabled = true
        genderEditText.isEnabled = true
        emailEditText.isEnabled = false // O email não deve ser editável
    }

    private fun disableEditing() {
        nameEditText.isEnabled = false
        ageEditText.isEnabled = false
        heightEditText.isEnabled = false
        weightEditText.isEnabled = false
        genderEditText.isEnabled = false
        emailEditText.isEnabled = false
    }

    private fun saveData(email: String?) {
        // Verifica se o email no perfil é o mesmo do registro
        if (emailEditText.text.toString() != email) {
            Toast.makeText(requireContext(), "Email no perfil deve ser o mesmo do registro", Toast.LENGTH_SHORT).show()
            return
        }

        val name = nameEditText.text.toString()
        val age = ageEditText.text.toString()
        val height = heightEditText.text.toString()
        val weight = weightEditText.text.toString()
        val gender = genderEditText.text.toString()

        val db = FirebaseFirestore.getInstance()
        val usersCollection = db.collection("users")

        val userData = hashMapOf(
            "name" to name,
            "age" to age,
            "height" to height,
            "weight" to weight,
            "gender" to gender,
            "email" to email
        )

        usersCollection.document(email ?: "").set(userData)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Dados salvos com sucesso", Toast.LENGTH_SHORT).show()
                isEditMode = false
                updateView()
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Falha ao salvar dados: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadUserData(email: String?) {
        val db = FirebaseFirestore.getInstance()
        val usersCollection = db.collection("users")

        usersCollection.document(email ?: "").get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val userData = document.data
                    nameEditText.setText(userData?.get("name").toString())
                    ageEditText.setText(userData?.get("age").toString())
                    heightEditText.setText(userData?.get("height").toString())
                    weightEditText.setText(userData?.get("weight").toString())
                    genderEditText.setText(userData?.get("gender").toString())
                    emailEditText.setText(userData?.get("email").toString())
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Falha ao carregar dados: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}

```

## Conclusão
Esta proposta de trabalho revelou-se um desafio, até agora, único naquilo que tem sido o nosso progresso enquanto alunos do curso de Engenharia em Desenvolvimento de Jogos Digitais. Esta unicidade deve-se ao facto de a nossa resposta ser a primeira aplicação desenvolvida na sua totalidade por nós, à exceção da utilização do FireBase, e por ser ainda o nosso primeiro projeto que se enquadraria no mercado de aplicações para dispositivos móveis.
Contudo, realçamos algumas dificuldades enfrentadas no desenvolvimento desta nossa resposta, nomeadamente alguma dificuldade na implementação do FireBase, posteriormente ultrapassada com o auxílio do professor, e também a dificuldade na concretização de alguns dos objetivos previamente definidos por nós nos primeiros momentos de definição daquilo que pretendíamos para a nossa aplicação.
Ainda assim, sublinhamos que este projeto se revelou essencial no nosso crescimento enquanto programadores, desenvolvedores de jogos e aplicações e ainda como alunos deste curso. Através do desenvolvimento da GymRats adquirimos novas capacidades, essencialmente no desenvolvimento de aplicações destinadas a dispositivos móveis e também na utilização de novas ferramentas extremamente uteis, caso do FireBase.
