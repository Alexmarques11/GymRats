package ipca.study.gymrats.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import ipca.study.gymrats.R
import ipca.study.gymrats.toShortDateTime
import java.util.Date

class PlanosFragment : Fragment() {

    var planos = arrayListOf<Planos>(
        Planos("Peito", Date()),
        Planos("Costas", Date()),
        Planos("Pernas", Date())
    )

    val adapter = PlanosAdapter()

    val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                it.data?.let { intent ->
                    val trainingtype = intent.getStringExtra("trainingType")!!
                    val newplan = Planos(trainingtype, Date())
                    planos.add(newplan)
                    adapter.notifyDataSetChanged()
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

            textViewTipoTreino.text = planos[position].trainingType
            textViewDataPlano.text = planos[position].date.toShortDateTime()

            return rootView
        }
    }

}
