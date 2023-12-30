package ipca.study.gymrats.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.ListView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import ipca.study.gymrats.R
import ipca.study.gymrats.toShortDateTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

class PlanosFragment : Fragment() {

    var planos = arrayListOf<Planos>(
        Planos("1","Peito", Date()),
        Planos("2","Costas", Date()),
        Planos("3","Pernas", Date())
    )

    val adapter = PlanosAdapter()

    val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val data = it.data
            data?.let {
                val training = it.getStringExtra(AddPlanosActivity.DATA_TRAINING) ?: ""
                val timestamp = it.getLongExtra(AddPlanosActivity.DATA_DATE, 0)
                val position = it.getIntExtra(AddPlanosActivity.DATA_POSITION, -1)

                if (position == -1) {
                    val newPlano = Planos(
                        UUID.randomUUID().toString(),
                        training,
                        if (timestamp != 0L) Date(timestamp) else Date(),
                        false
                    )

                    GlobalScope.launch(Dispatchers.IO) {
                        AppDatabase.getDatabase(requireContext())?.getPlanosDao()?.insert(newPlano)
                    }
                } else {
                    planos[position].trainingType = training
                    planos[position].date = if (timestamp != 0L) Date(timestamp) else Date()

                    GlobalScope.launch(Dispatchers.IO) {
                        val planosDao = AppDatabase.getDatabase(requireContext())?.getPlanosDao()
                        planosDao?.update(planos[position])
                    }
                }
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_planos, container, false)

        val listView = rootView.findViewById<ListView>(R.id.listViewPlanos)
        listView.adapter = adapter

        rootView.findViewById<Button>(R.id.buttonDone).setOnClickListener {
            val intent = Intent(requireContext(), AddPlanosActivity::class.java)
            resultLauncher.launch(intent)
        }

        return rootView
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
            GlobalScope.launch (Dispatchers.IO) {
                AppDatabase.getDatabase(requireContext())?.getPlanosDao()
                    ?.update(planos[position])
            }

            rootView.setOnClickListener {
                val intent = Intent(requireActivity(), AddPlanosActivity::class.java)
                intent.putExtra(AddPlanosActivity.DATA_TRAINING, planos[position].trainingType)
                intent.putExtra(AddPlanosActivity.DATA_DATE, planos[position].date.time)
                intent.putExtra(AddPlanosActivity.DATA_POSITION, position)
                resultLauncher.launch(intent)
            }

            return rootView
        }
    }
}