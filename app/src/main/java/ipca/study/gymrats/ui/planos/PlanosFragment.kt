package ipca.study.gymrats.ui.planos

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
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import ipca.study.gymrats.R
import ipca.study.gymrats.repository.AppDatabase
import ipca.study.gymrats.repository.Planos
import ipca.study.gymrats.toShortDateTime

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